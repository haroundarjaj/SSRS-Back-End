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

@Document (collection = "tasks")
@Data
@NoArgsConstructor
@Getter
@Setter
public class UserTask {

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

    @PersistenceConstructor
    public UserTask(long taskId, String title, String description, boolean done, Date creationTime, AppUser user) {
        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.done = done;
        this.creationTime = creationTime;
        this.user = user;
    }
}
