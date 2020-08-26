package com.haroun.ssrs.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Document(collection = "shared")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ShareWith implements Serializable {

    @Transient
    public static final String SEQUENCE_NAME = "shared_sequence";

    @Id
    private long shareId;
    private Date sharedAt;
    private Date lastUpdate;
    @DBRef
    private Workspace workspace;
    private String emails;
}
