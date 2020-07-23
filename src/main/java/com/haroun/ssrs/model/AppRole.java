package com.haroun.ssrs.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "roles")
@Data
@Getter
@Setter
@NoArgsConstructor
public class AppRole {

    @Transient
    public static final String SEQUENCE_NAME = "roles_sequence";

    @Id
    private long roleId;
    private String role;

    @PersistenceConstructor

    public AppRole(long roleId, String role) {
        this.roleId = roleId;
        this.role = role;
    }
}
