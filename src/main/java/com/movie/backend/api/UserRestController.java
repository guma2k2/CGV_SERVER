package com.movie.backend.api;

import com.movie.backend.dto.RoleDTO;
import com.movie.backend.dto.UserDTO;
import com.movie.backend.entity.User;
import com.movie.backend.dto.DataContent;
import com.movie.backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.List;

@RequestMapping("/api/v1/admin/user")
@RestController
@CrossOrigin("*")
@Slf4j
public class UserRestController {
    @Autowired
    private UserService userService;
    @GetMapping
    public DataContent findAll() {
        return userService.getFirstPage();
    }

    @GetMapping("/paginate/firstPage")
    public DataContent findPaginate() {
        return userService.getFirstPage() ;
    }

    @GetMapping("/paginate")
    public DataContent findPaginate(@RequestParam("pageNum")int pageNum ,
                                    @RequestParam(value = "sortDir", required = false) String sortDir,
                                    @RequestParam(value = "sortField", required = false) String sortField,
                                    @RequestParam("keyword") String keyword ) {
        return userService.findAll(pageNum, sortDir, sortField, keyword) ;
    }

    @GetMapping("/roles")
    public List<RoleDTO> getRoles() {
        return userService.getAllRole() ;
    }

    @PostMapping("/save")
    public User saveUser(@RequestBody UserDTO userDTO) {
        return userService.saveUser(userDTO, null);
    }
    @PostMapping("/save/photo/{userId}")
    public String savePhoto(@RequestParam("image") MultipartFile file,
                            @PathVariable("userId") Long userId ) {
        try {
            userService.savePhoTo(userId, file);
            return "Save photo success" ;
        } catch (IOException e) {
            return e.getMessage();
        }
    }
    @PutMapping("/update/{userId}")
    public User saveUser(@RequestBody UserDTO userDTO,
                         @PathVariable("userId")Long userId) {
        return userService.saveUser(userDTO, userId);
    }
    @PutMapping("/update/status/{userId}/{status}")
    public void updateStatusOfUser(@PathVariable("userId")Long userId,
                                   @PathVariable("status")boolean status) {
        userService.updateStatus(userId, status);
    }

    @GetMapping("/{userId}")
    public UserDTO get(@PathVariable("userId")Long userId) {
        return userService.get(userId) ;
    }
}
