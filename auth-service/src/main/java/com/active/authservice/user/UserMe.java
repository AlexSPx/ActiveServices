package com.active.authservice.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserMe {
    private String id;
    private String gid;
    private String username;
    private String email;
    private String firstname;
    private String lastname;
}
