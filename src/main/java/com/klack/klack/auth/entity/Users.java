package com.klack.klack.auth.entity;

import com.klack.klack.auth.enums.Role;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users {

    @Id
    private String id;

    private String username;

    private String email;

    private String password;

    private Role role;

    private String companyId;


}
