package com.movie.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.backend.api.UserRestController;
import com.movie.backend.dto.DataContent;
import com.movie.backend.dto.Paginate;
import com.movie.backend.dto.RoleDTO;
import com.movie.backend.dto.UserDTO;
import com.movie.backend.entity.ERole;
import com.movie.backend.entity.User;
import com.movie.backend.security.config.JwtService;
import com.movie.backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = UserRestController.class,
        excludeAutoConfiguration = {
                UserDetailsServiceAutoConfiguration.class, SecurityAutoConfiguration.class
        }
)
@Slf4j
public class UserControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserDetailsService userDetailsService;



    @Test
    void getUser_ShouldReturnPagedUsers_WhenValidParams() throws Exception {
        // Given
        int pageNum = 1;
        int pageSize = 10;
        String keyword = "test";
        DataContent<UserDTO> pageableData = new DataContent<>();
        Paginate paginate = new Paginate();
        paginate.setSizePage(pageSize);
        paginate.setCurrentPage(pageNum);
        paginate.setTotalPage(2);
        paginate.setTotalElements(10);
        paginate.setSortDir("asc");
        paginate.setSortField("id");
        paginate.setKeyword(keyword);

        pageableData.setPaginate(paginate);
        Set<RoleDTO> roles1 = new HashSet<>();
        roles1.add(new RoleDTO(1, "CLIENT"));

        UserDTO user1 = new UserDTO(1L, "John", "Doe", "John Doe", "john.doe@example.com", "password123", true,
                "john_photo.jpg","/images/john.jpg", "0141412141" ,"verif123", "forgot123", ERole.ADMIN.name());
        pageableData.setResults(Collections.singletonList(user1));

        log.info(pageableData.toString());

        when(userService.findAll(pageNum, paginate.getSortDir(), paginate.getSortField(), paginate.getKeyword())).thenReturn(pageableData);

        // When & Then
        mockMvc.perform(get("/api/v1/admin/user/paginate")
                        .param("pageNum", String.valueOf(pageNum))
                        .param("sortDir", paginate.getSortDir())
                        .param("sortField", paginate.getSortField())
                        .param("keyword", keyword))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paginate.currentPage").value(pageNum))
                .andExpect(jsonPath("$.paginate.totalPage").value(paginate.getTotalPage()))
                .andExpect(jsonPath("$.paginate.totalElements").value(paginate.getTotalElements()))
                .andExpect(jsonPath("$.results[0].id").value(1))
                .andExpect(jsonPath("$.results[0].email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.results[0].firstName").value("John"))
                .andExpect(jsonPath("$.results[0].lastName").value("Doe"))
                .andExpect(jsonPath("$.results[0].status").value(true));
    }

    @Test
    void updateUser_ShouldReturnUpdatedUser_WhenValidRequest() throws Exception {
        // Given
        Long userId = 1L;
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("UpdatedFirst");
        userDTO.setLastName("UpdatedLast");
        userDTO.setRole(ERole.ADMIN.name());
        userDTO.setEmail("updated@example.com");

        User updatedUser = new User();
        updatedUser.setId(userId);
        updatedUser.setFirstName("UpdatedFirst");
        updatedUser.setLastName("UpdatedLast");
        updatedUser.setEmail("updated@example.com");
        updatedUser.setRole(ERole.ADMIN);

        when(userService.saveUser(any(UserDTO.class), eq(userId))).thenReturn(updatedUser);

        // When & Then
        mockMvc.perform(put("/api/v1/admin/user/update/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO))) // Convert DTO to JSON
                .andExpect(status().isOk()) // Expect HTTP 200 OK
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.firstName").value("UpdatedFirst"))
                .andExpect(jsonPath("$.lastName").value("UpdatedLast"))
                .andExpect(jsonPath("$.email").value("updated@example.com"));
    }


    @Test
    void saveUser_ShouldReturnCreatedUser_WhenValidRequest() throws Exception {
        // Given
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");
        userDTO.setRole(ERole.ADMIN.name());
        userDTO.setEmail("john.doe@example.com");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setFirstName("John");
        savedUser.setLastName("Doe");
        savedUser.setEmail("john.doe@example.com");
        savedUser.setRole(ERole.ADMIN);

        when(userService.saveUser(any(UserDTO.class), any())).thenReturn(savedUser);

        // When & Then
        mockMvc.perform(post("/api/v1/admin/user/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO))) // Convert DTO to JSON
                .andExpect(status().isOk()) // Expect HTTP 200 OK
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }



}
