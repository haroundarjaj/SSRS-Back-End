package com.haroun.ssrs.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "WSsources")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WebServiceSource  implements Serializable  {

    @Transient
    public static final String SEQUENCE_NAME = "webservicesource_sequence";

    @Id
    private long webServiceSourceId;

    private String type;
    private String username;
    private String password;
    private String host;

    @DBRef
    private AppUser user;
}
