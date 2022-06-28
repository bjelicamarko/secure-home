package com.asdf.adminback.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.logging.LogLevel;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Getter
@Setter
@Data
@Document(collection = "logs")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private Long dateTime;

    private LogLevel logLevel;

    private String loggerName;

    private String logMessage;

    private String stackTrace;

    public Log() {}

    public Log(Long dateTime, LogLevel logLevel, String loggerName, String logMessage) {
        this.dateTime = dateTime;
        this.logLevel = logLevel;
        this.loggerName = loggerName;
        this.logMessage = logMessage;
    }

    public Log(Long dateTime, LogLevel logLevel, String loggerName, String logMessage, String stackTrace) {
        this.dateTime = dateTime;
        this.logLevel = logLevel;
        this.loggerName = loggerName;
        this.logMessage = logMessage;
        this.stackTrace = stackTrace;
    }

}
