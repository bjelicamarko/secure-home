package com.asdf.adminback.services;

import com.asdf.adminback.models.Log;

public interface LogService {

    void saveLog(Log log);

    void generateInfoLog(String logMessage);

    void generateWarnLog(String logMessage);

    void generateErrLog(String logMessage);

    void generateErrLog(String logMessage, String stackTrace);

    void generateFatalLog(String logMessage);
}
