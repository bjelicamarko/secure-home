package com.asdf.myhomeback.services;

import com.asdf.myhomeback.exceptions.LogException;
import com.asdf.myhomeback.models.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LogService {
    void generateInfoLog(String logMessage);

    void generateWarnLog(String logMessage);

    void generateErrLog(String logMessage);

    void generateErrLog(String logMessage, String stackTrace);

    Page<Log> getAllLogs(Pageable pageable);

    Page<Log> filterLogs(String startDate, String endDate, String selectedLevel, String searchValue,  Pageable pageable) throws LogException;
}
