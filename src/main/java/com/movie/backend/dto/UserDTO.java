package com.movie.backend.dto;

import com.movie.backend.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id ;
    private String firstName ;
    private String lastName;
    private String fullName ;
    private String email ;
    private String password ;
    private boolean status;
    private String photo;
    private String photosImagePath;
    private String phone_number;
    private String verificationCode;
    private String forgotPassword ;
    private Set<RoleDTO> roles ;
}
