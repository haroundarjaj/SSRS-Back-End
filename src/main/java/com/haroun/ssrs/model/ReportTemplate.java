package com.haroun.ssrs.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Document(value = "reportTemplate")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReportTemplate implements Serializable {

    @Transient
    public static final String SEQUENCE_NAME = "reportTelmplates_sequence";

    @Id
    private long reportTemplateId;
    private String name;
    private String templateParam;
    private Date creationTime;

    @DBRef
    private AppUser user;
}
