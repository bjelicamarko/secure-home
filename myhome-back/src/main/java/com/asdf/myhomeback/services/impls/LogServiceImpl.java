package com.asdf.myhomeback.services.impls;

import com.asdf.myhomeback.models.Log;
import com.asdf.myhomeback.repositories.LogRepository;
import com.asdf.myhomeback.services.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogRepository logRepository;

    @Override
    public void generateInfoLog(String logMessage) {
        Long dateTime = new Date().getTime();
        String loggerName = Thread.currentThread().getStackTrace()[2].getMethodName();
        Log log = new Log(dateTime, LogLevel.INFO, loggerName, logMessage);

        logRepository.save(log);
    }

    @Override
    public void generateWarnLog(String logMessage) {
        Long dateTime = new Date().getTime();
        String loggerName = Thread.currentThread().getStackTrace()[2].getMethodName();
        Log log = new Log(dateTime, LogLevel.WARN, loggerName, logMessage);

        logRepository.save(log);
    }

    @Override
    public void generateErrLog(String logMessage) {
        Long dateTime = new Date().getTime();
        String loggerName = Thread.currentThread().getStackTrace()[2].getMethodName();
        Log log = new Log(dateTime, LogLevel.ERROR, loggerName, logMessage);

        logRepository.save(log);
    }

    @Override
    public void generateErrLog(String logMessage, String stackTrace) {
        Long dateTime = new Date().getTime();
        String loggerName = Thread.currentThread().getStackTrace()[2].getMethodName();
        Log log = new Log(dateTime, LogLevel.ERROR, loggerName, logMessage, stackTrace);

        logRepository.save(log);
    }
}
