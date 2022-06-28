package com.asdf.myhomeback.dto;

import com.asdf.myhomeback.models.Log;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LogDTO {
    private Long dateTime;
    private String logLevel;
    private String loggerName;
    private String logMessage;
    private String stackTrace;

    public LogDTO(Log log){
        this.dateTime = log.getDateTime();
        this.logLevel = log.getLogLevel().toString();
        this.loggerName = log.getLoggerName();
        this.logMessage = log.getLogMessage();
        this.stackTrace = log.getStackTrace();
    }
}
