package com.haroun.ssrs.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "dbsources")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DatabaseSource implements Serializable {

    @Transient
    public static final String SEQUENCE_NAME = "databasesources_sequence";

    @Id
    private long databaseSourceId;

    private String type;
    private String username;
    private String password;
    private String host;
    private String port;
    private String databaseName;

    @DBRef
    private AppUser user;
}
