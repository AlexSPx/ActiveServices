package com.active.authservice.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("user")
@Data
@Builder
@AllArgsConstructor
public class UserModel {
    @Id
    private String id;
    private String gid;
    private String username;
    private String email;
    private String firstname;
    private String lastname;
    private String password;
    @CreatedDate
    private LocalDateTime createdAt;
    private boolean isConfirmed;
}
