package com.movie.backend.repository;

import com.movie.backend.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findByEmail(String email);
    @Query("SELECT u FROM User u WHERE  u.verificationCode = :verificationCode ")
    public User findByVerificationCode(@Param("verificationCode") String verificationCode);
    public Page<User> findAll(Pageable pageable) ;

    @Query("SELECT u FROM User u WHERE CONCAT(u.id, ',' , u.firstName, ',' , u.lastName, ',' , u.email) LIKE %:keyword%")
    public Page<User> listAll(@Param("keyword") String keyword , Pageable pageable) ;


    @Modifying
    @Query("UPDATE User u SET u.status = :status WHERE u.id = :userId")
    public void updateStatus(@Param("userId") Long userId , @Param("status") boolean status) ;

    @Query("SELECT u FROM User u WHERE u.forgotPassword = :code")
    User findByTokenForgotPassword(@Param("code") String code);

}
