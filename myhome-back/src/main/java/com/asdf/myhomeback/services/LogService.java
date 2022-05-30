package com.asdf.myhomeback.services;

public interface LogService {
    void generateInfoLog(String logMessage);

    void generateWarnLog(String logMessage);

    void generateErrLog(String logMessage);

    void generateErrLog(String logMessage, String stackTrace);
}
