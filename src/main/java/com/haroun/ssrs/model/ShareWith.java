package com.haroun.ssrs.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Document(collection = "shared")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShareWith implements Serializable {
    @Id
    private String shareId;
    private Date sharedAt;
    private Date lastUpdate;
    @DBRef
    private Workspace workspace;
    private String emails;
}
