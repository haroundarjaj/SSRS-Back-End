package com.haroun.ssrs.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Document(value = "users")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AppUser implements Serializable {

    @Transient
    public static final String SEQUENCE_NAME = "users_sequence";

    @Id
    private long userId;

    private String username;
    private String email;
    private String password;
    private String idCardNumber;
    private String address;
    private Date birthday;
    private String image;
    private Date creationTime;
    private boolean activated;

    @DBRef
    private AppRole role;
}
