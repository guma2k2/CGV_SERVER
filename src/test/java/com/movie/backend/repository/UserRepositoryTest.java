package com.movie.backend.repository;

import com.movie.backend.TestConfig;
import com.movie.backend.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({TestConfig.class})
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        User user1 = new User();
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setEmail("john.doe@example.com");
        userRepository.save(user1);

        User user2 = new User();
        user2.setFirstName("Jane");
        user2.setLastName("Smith");
        user2.setEmail("jane.smith@example.com");
        userRepository.save(user2);
    }

    @Test
    void listAll_ShouldReturnUsersMatchingKeyword() {

        // Given
        String keyword = "John";
        Pageable pageable = PageRequest.of(0, 10);

        // When
        Page<User> result = userRepository.listAll(keyword, pageable);

        // Then
        assertThat(result).isNotEmpty();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getFirstName()).isEqualTo("John");
    }

    @Test
    void listAll_ShouldReturnUsersMatchingEmail() {
        // Given
        String keyword = "jane.smith@example.com";
        Pageable pageable = PageRequest.of(0, 10);

        // When
        Page<User> result = userRepository.listAll(keyword, pageable);

        // Then
        assertThat(result).isNotEmpty();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getEmail()).isEqualTo("jane.smith@example.com");
    }

    @Test
    void listAll_ShouldReturnEmpty_WhenNoMatches() {
        // Given
        String keyword = "NonExistent";
        Pageable pageable = PageRequest.of(0, 10);

        // When
        Page<User> result = userRepository.listAll(keyword, pageable);

        // Then
        assertThat(result).isEmpty();
    }


}
