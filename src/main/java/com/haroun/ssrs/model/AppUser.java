package com.haroun.ssrs.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(value = "users")
@Data
@NoArgsConstructor
@Getter
@Setter
public class AppUser {

    @Transient
    public static final String SEQUENCE_NAME = "users_sequence";

    @Id
    private long userId;
    private String username;
    private String email;
    private String password;
    private Date creationTime;
    private boolean activated;

    @DBRef
    private AppRole role;

    @PersistenceConstructor
    public AppUser(long userId, String username, String email, String password, Date creationTime, boolean activated, AppRole role) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.creationTime = creationTime;
        this.activated = activated;
        this.role = role;
    }
}
