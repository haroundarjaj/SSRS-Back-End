package com.haroun.ssrs.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Document (collection = "tasks")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserTask implements Serializable {

    @Transient
    public static final String SEQUENCE_NAME = "tasks_sequence";

    @Id
    private long taskId;
    private String title;
    private String description;
    private boolean done;
    private Date creationTime;

    @DBRef
    private AppUser user;
}
