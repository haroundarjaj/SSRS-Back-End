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

@Document(value = "reportTemplate")
@Data
@NoArgsConstructor
@Getter
@Setter
public class ReportTemplate {

    @Transient
    public static final String SEQUENCE_NAME = "reportTelmplates_sequence";

    @Id
    private long reportTemplateId;
    private String templateParam;
    private Date creationTime;

    @DBRef
    private AppUser user;

    @PersistenceConstructor
    public ReportTemplate(long reportTemplateId, String templateParam, Date creationTime, AppUser user) {
        this.reportTemplateId = reportTemplateId;
        this.templateParam = templateParam;
        this.creationTime = creationTime;
        this.user = user;
    }
}
