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
import java.util.List;

@Document(collection = "workspaces")
@Data
@NoArgsConstructor
@Getter
@Setter
public class Workspace {

    @Transient
    public static final String SEQUENCE_NAME = "workspaces_sequence";

    @Id
    private long workspaceId;
    private String title;
    private Date creationTime;

    @DBRef
    private AppUser user;

    @DBRef
    private List<Chart> charts;

    @PersistenceConstructor
    public Workspace(long workspaceId, String title, Date creationTime, AppUser user, List<Chart> charts) {
        this.workspaceId = workspaceId;
        this.title = title;
        this.creationTime = creationTime;
        this.user = user;
        this.charts = charts;
    }
}
