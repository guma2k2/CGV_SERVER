package com.movie.backend.service;

import com.movie.backend.dto.RoleDTO;
import com.movie.backend.dto.UserDTO;
import com.movie.backend.entity.Role;
import com.movie.backend.entity.User;
import com.movie.backend.exception.UserException;
import com.movie.backend.repository.RoleRepository;
import com.movie.backend.repository.UserRepository;
import com.movie.backend.ultity.RandomString;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import com.movie.backend.dto.DataContent;
import com.movie.backend.dto.Paginate;
import com.movie.backend.ultity.FileUploadUtil;
import com.movie.backend.ultity.MailUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository ;
    @Autowired SettingService settingService;
    @Value("${application.service.user.userPerPage}")
    public int userPerPage ;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder ;

    @Autowired
    private RoleRepository roleRepository;

    public DataContent getFirstPage() {
        return findAll(1, "desc" , "id" , null ) ;
    }

    public DataContent findAll(int pageNum , String sortDir , String sortField , String keyword) {
        Sort sort = Sort.by(sortField) ;
        sort =  sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNum - 1 , userPerPage , sort );
        Page<User> pages ;
        if(keyword == null) {
            pages  = userRepository.findAll(pageable);
        } else {
            pages = userRepository.listAll(keyword, pageable) ;
        }
        int total = pages.getTotalPages();
        int totalElements = (int) pages.getTotalElements();


        List<UserDTO> users = pages.getContent()
                .stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
        Paginate paginate = Paginate.builder()
                .keyword(keyword)
                .currentPage(pageNum)
                .sortDir(sortDir)
                .sortField(sortField)
                .totalPage(total)
                .totalElements(totalElements)
                .sizePage(userPerPage)
                .build();
        return new DataContent(paginate , users);
    }
    public void savePhoTo(Long userId, MultipartFile multipartFile) throws IOException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserException("User not found")) ;
        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            user.setPhoto(fileName);

            String uploadDir = "user-photos/" + userId;

            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        } else {
            if (user.getPhoto().isEmpty()) user.setPhoto(null);
        }
        userRepository.save(user);
    }
    public List<RoleDTO> getAllRole() {
        return roleRepository
                .findAll()
                .stream()
                .map(role -> modelMapper.map(role, RoleDTO.class))
                .collect(Collectors.toList());
    }
    public User saveUser(UserDTO userDTO, Long userId ) {
        boolean update = userId != null ;
        String requestEmail = userDTO.getEmail() ;
        Optional<User> checkUserExit = userRepository.findByEmail(requestEmail) ;
        if(update) {
            if(checkUserExit.isPresent() ) {
                if (checkUserExit.get().getId() != userId) {
//                    log.info(userDTO.getEmail());
//                    log.info("errUpdate");
                    throw new UserException("Email was not valid") ;
                }
            }
        } else {
            if (checkUserExit.isPresent()) {
//                log.info(userDTO.getEmail());
//                log.info("errCreate");
                throw new UserException("Email was not valid") ;
            }
        }

        String firstName = userDTO.getFirstName();
        String lastName = userDTO.getLastName();
        String email = userDTO.getEmail();
        String password = userDTO.getPassword() ;
        Set<Role> roles = new HashSet<>() ;
        for (RoleDTO role : userDTO.getRoles()) {
            Role oldRole = roleRepository.findById(role.getId()).get();
            roles.add(oldRole);
        }
        if(update) {
            User oldUser = userRepository.findById(userId).orElseThrow(() -> new UserException("User not found"));
            oldUser.setFirstName(firstName);
            oldUser.setLastName(lastName);
            oldUser.setEmail(email);
            oldUser.setRoles(roles);
            oldUser.setStatus(true);
            if(password.equals("")) {
                oldUser.setPassword(oldUser.getPassword());
//                log.info("update" + " empty");
            } else {
                oldUser.setPassword(passwordEncoder.encode(password));
//                log.info("update" + password);
            }
            return  userRepository.save(oldUser) ;
        }
        User newUser = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .roles(roles)
                .status(true)
                .password(passwordEncoder.encode(password))
                .build();
        return userRepository.save(newUser) ;

    }
    public UserDTO get(Long userId) {
       User user = userRepository.findById(userId).orElseThrow(() -> new UserException("User not found")) ;
       return modelMapper.map(user, UserDTO.class) ;
    }

    @Transactional
    public void updateStatus(Long userId , boolean status) {
        userRepository.findById(userId).orElseThrow(() -> new UserException("User not found")) ;
        userRepository.updateStatus(userId, status);
    }
    public void sendVerifyPassword( String email , String link)
            throws MessagingException, UnsupportedEncodingException {
        JavaMailSenderImpl mailSender = MailUtil.prepareMailSender(settingService);
        String toAddress = email;
        String subject = "Confirm and change password" ;
        String content = "<p>Hi,</p>" +
                "<p> You requested to change your password successful </p>" +
                "Please click the link below to change your password:" +
                "<p> <a href =\"" +link+ "\">Change password</a> </p>" +
                "<br>" +
                "<p>Delete this email if you want, after you change your password successful</p>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true, "UTF-8");
        helper.setFrom(settingService.getFromAddress(), settingService.getSenderName());
        helper.setTo(toAddress);
        helper.setSubject(subject);
        helper.setText(content, true);

        mailSender.send(message);
    }

    public void sendVerificationEmail(HttpServletRequest request, User user)
            throws  UnsupportedEncodingException, MessagingException {
        JavaMailSenderImpl mailSender = MailUtil.prepareMailSender(settingService);
        String toAddress = user.getEmail();

        // Lấy đoạn text đã được lưu trong phần setting
        String subject = settingService.getCustomerVerifySubject();

        // Lấy nội dung trong phần setting
        String content = settingService.getCustomerVerifyContent();

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true, "UTF-8");

        helper.setFrom(settingService.getFromAddress(), settingService.getSenderName());
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getFullName());

        String verifyURL = "http://localhost:8000/vincinema" + "/verify?code=" + user.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);
        mailSender.send(message);
    }
    public String updatePasswordCustomer(String email) {
        Optional<User> customer = userRepository.findByEmail(email);
        if(customer.isPresent()){
            String code = RandomString.make(64);
            customer.get().setForgotPassword(code);
            userRepository.save(customer.get());
            return code ;
        }else {
            throw new UserException("Email của bạn đã nhập sai hoặc không tồn tại !!");
        }
    }

    public void updatePasswordReset(String token, String password) {
        User customer = userRepository.findByTokenForgotPassword(token);
        if(customer == null){
            throw new UserException("Lấy lại mật khẩu thất bại");
        }
        String newPassword =   passwordEncoder.encode(customer.getPassword());
        customer.setPassword(newPassword);
        userRepository.save(customer);
    }
}
