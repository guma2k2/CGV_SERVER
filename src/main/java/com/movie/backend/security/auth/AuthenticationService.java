package com.movie.backend.security.auth;

import com.movie.backend.dto.*;
import com.movie.backend.entity.Role;
import com.movie.backend.entity.User;
import com.movie.backend.exception.HeaderNotFoundException;
import com.movie.backend.exception.JwtException;
import com.movie.backend.exception.UserException;
import com.movie.backend.repository.RoleRepository;
import com.movie.backend.repository.UserRepository;

import com.movie.backend.security.config.JwtService;
import com.movie.backend.service.UserService;
import com.movie.backend.ultity.RandomString;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    private ModelMapper modelMapper;

    private final RestTemplate restTemplate;

    private static final  String EXCHANGE_TOKEN_URL = "https://oauth2.googleapis.com/token";
    private static final String EXCHANGE_USER_INFO_URL = "https://www.googleapis.com/oauth2/v1/userinfo";

    @Value("${outbound.identity.client-id}")
    protected String CLIENT_ID;

    @Value("${outbound.identity.client-secret}")
    protected String CLIENT_SECRET;

    @Value("${outbound.identity.redirect-uri}")
    protected String REDIRECT_URI;


    private static final String GRANT_TYPE = "authorization_code";
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    private UserService userService;

    public String register(RegisterRequest request) {
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
                .photo("")
                .phone_number("")
                .verificationCode(randomCode)
                .password(passwordEncoder.encode(request.getPassword()))
                .status(false)
                .build();
        user.addRole(role);
        userRepository.save(user);
        userService.sendVerificationEmail(user);
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
            return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .user(userDTO)
                    .build();
        }

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
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .user(userDTO)
                .build();
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
        String link = "http://localhost:8000" +  "/reset_password?token=" + code;
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

    public ProfileResponse getProfile() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info(email);
        User user = userRepository.findByEmail(email).orElseThrow();
        return ProfileResponse.fromUser(user);
    }

    public AuthenticationResponse updateProfile(ProfileUpdateRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info(email);
        User user = userRepository.findByEmail(email).orElseThrow();
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setPhone_number(request.phoneNumber());
        if (!request.password().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.password()));
        }
        User updatedUser = userRepository.saveAndFlush(user);
        UserDTO userDTO = modelMapper.map(updatedUser , UserDTO.class);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .user(userDTO)
                .build();
    }

    public AuthenticationResponse outboundAuthenticate(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("code", code);
        map.add("client_id", CLIENT_ID);
        map.add("client_secret", CLIENT_SECRET);
        map.add("redirect_uri", REDIRECT_URI);
        map.add("grant_type", GRANT_TYPE);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
        ResponseEntity<ExchangeTokenResponse> exchange = restTemplate.exchange(EXCHANGE_TOKEN_URL, HttpMethod.POST, entity, ExchangeTokenResponse.class);

        ExchangeTokenResponse response = exchange.getBody();
        if (response != null) {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(EXCHANGE_USER_INFO_URL)
                    .queryParam("alt", "json")
                    .queryParam("access_token", response.accessToken());

            // Perform the GET request directly without HttpEntity
            OutboundUserResponse userInfo = restTemplate.getForObject(builder.toUriString(), OutboundUserResponse.class);
            log.info(userInfo.email());
            if (userInfo != null) {
                User user = userRepository.findByEmail(userInfo.email()).orElseGet(
                        () -> {
                            User newUser = User.builder()
                                    .status(true)
                                    .firstName(userInfo.familyName())
                                    .lastName(userInfo.givenName())
                                    .email(userInfo.email())
                                    .photo(userInfo.picture())
                                    .build();
                            Role role = roleRepository.findByName("CLIENT");
                            newUser.addRole(role);
                            return userRepository.saveAndFlush(newUser);
                        });
                UserDTO userDTO = modelMapper.map(user , UserDTO.class);
                var jwtToken = jwtService.generateToken(user);
                var refreshToken = jwtService.generateRefreshToken(user);
                return AuthenticationResponse.builder()
                        .accessToken(jwtToken)
                        .refreshToken(refreshToken)
                        .user(userDTO)
                        .build();
            }
        }
        return null;
    }
}
