package com.asdf.myhomeback.models;

import lombok.*;
import org.springframework.boot.logging.LogLevel;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter

@Data
@Document(collection = "logs")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @NonNull
    private Long dateTime;
    @NonNull
    private LogLevel logLevel;
    @NonNull
    private String loggerName;
    @NonNull
    private String logMessage;
}
