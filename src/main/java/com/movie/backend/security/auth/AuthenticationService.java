package com.movie.backend.security.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.backend.dto.UserDTO;
import com.movie.backend.entity.Role;
import com.movie.backend.entity.User;
import com.movie.backend.exception.HeaderNotFoundException;
import com.movie.backend.exception.JwtException;
import com.movie.backend.exception.UserException;
import com.movie.backend.repository.RoleRepository;
import com.movie.backend.repository.UserRepository;

import com.movie.backend.security.config.JwtService;
import com.movie.backend.security.token.Token;
import com.movie.backend.security.token.TokenRepository;
import com.movie.backend.security.token.TokenType;
import com.movie.backend.service.UserService;
import com.movie.backend.ultity.RandomString;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.JMException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    private ModelMapper modelMapper;

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    private UserService userService;

    public String register(RegisterRequest request, HttpServletRequest servletRequest) throws Exception {
        log.info(request.getEmail());
        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw  new UserException("The email was exited");
        }
        Role role = roleRepository.findByName("CLIENT");
        // 1 : ADMIN // 2 : CLIENT
        String randomCode = RandomString.make(64);
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .verificationCode(randomCode)
                .password(passwordEncoder.encode(request.getPassword()))
                .status(false)
                .build();
        user.addRole(role);
        userRepository.save(user);
        userService.sendVerificationEmail(servletRequest, user);
        return "Send request success" ;
    }
    @Transactional
    public AuthenticationResponse verify(String verification){
        User customer = userRepository.findByVerificationCode(verification);

        // Kiểm tra xem khách hàng này đã tồn tại chưa và trạng thái ban đầu phải bằng false
        if(customer == null || customer.isStatus()){
            throw new UserException("This user was available");
        }else {
            // Nếu true cập nhật trạng thái của người dùng về true
            userService.updateStatus(customer.getId() , true);
            UserDTO userDTO = modelMapper.map(customer , UserDTO.class);
            var jwtToken = jwtService.generateToken(customer);
            var refreshToken = jwtService.generateRefreshToken(customer);
            saveUserToken(customer, jwtToken);
            return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .user(userDTO)
                    .build();
        }

    }

    public AuthenticationResponse confirmRegister(RegisterRequest request) throws Exception {
        Role role = roleRepository.findByName("CLIENT");
        String randomCode = RandomString.make(64);
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .verificationCode(randomCode)
                .password(passwordEncoder.encode(request.getPassword()))
                .status(false)
                .build();
        userRepository.save(user);
        UserDTO userDTO = modelMapper.map(user , UserDTO.class);
        user.addRole(role);
        var savedUser = repository.save(user);
        System.out.println(savedUser.getId());
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .user(userDTO)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserException("user not found"));
        UserDTO userDTO = modelMapper.map(user , UserDTO.class);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .user(userDTO)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .type(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
    public AuthenticationResponse refreshToken(
            HttpServletRequest request
    )  {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            throw new HeaderNotFoundException("Header was not valid");
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUserName(refreshToken);
        AuthenticationResponse authResponse = null;
        log.info(refreshToken);
        if (userEmail != null) {
            var user = this.repository.findByEmail(userEmail)
                    .orElseThrow(() -> new UserException("user not found"));
            UserDTO userDTO = modelMapper.map(user , UserDTO.class);
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .user(userDTO)
                        .build();
            }else {
                log.info("userEmail is null");
                throw new JwtException("Jwt was not valid");
            }
        }
        return  authResponse;
    }

    public String resetPassword(String email) throws MessagingException, UnsupportedEncodingException {
        String code = userService.updatePasswordCustomer(email);
        String link = "http://localhost:8000/vincinema" +  "/reset_password?token=" + code;
        userService.sendVerifyPassword(email, link);
        return "Send request success";
    }

    public String verifyPassword(String code) {
        User user = userRepository.findByTokenForgotPassword(code);
        if(user != null) {
            return code ;
        } else {
            throw new UserException("Reset password failed");
        }
    }

    public String confirmPassword(String password, String token) {
        userService.updatePasswordReset(token,password);
        return "Success";
    }
}
