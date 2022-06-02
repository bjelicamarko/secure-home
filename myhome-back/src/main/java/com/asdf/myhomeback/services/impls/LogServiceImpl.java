package com.asdf.myhomeback.services.impls;

import com.asdf.myhomeback.exceptions.LogException;
import com.asdf.myhomeback.models.AlarmNotification;
import com.asdf.myhomeback.models.AlarmRule;
import com.asdf.myhomeback.models.Log;
import com.asdf.myhomeback.models.enums.AlarmType;
import com.asdf.myhomeback.repositories.mongo.LogRepository;
import com.asdf.myhomeback.services.AlarmNotificationService;
import com.asdf.myhomeback.services.AlarmRuleService;
import com.asdf.myhomeback.services.LogService;
import com.asdf.myhomeback.utils.LogUtils;
import com.asdf.myhomeback.websocket.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private AlarmRuleService alarmRuleService;

    @Autowired
    private AlarmNotificationService alarmNotificationService;

    @Autowired
    private WebSocketService webSocketService;

    @Override
    public void saveLog(Log log){
        // check if some alarm rule will trigger notification
        List<AlarmRule> alarmRules = alarmRuleService.findAllByType(AlarmType.LOG);
        List<AlarmNotification> alarmNotifications = new ArrayList<>();
        alarmRules.forEach(alarmRule -> {
            if (log.getLogMessage().contains(alarmRule.getRulePattern())){
                alarmNotifications.add(new AlarmNotification(log.getLogMessage(), AlarmType.LOG, null, "admin"));
            }
        });

        // save all notifications
        alarmNotificationService.saveAll(alarmNotifications);

        // send all notifications
        webSocketService.sendNotifications(alarmNotifications, AlarmType.LOG);

        logRepository.save(log);
    }

    @Override
    public void generateInfoLog(String logMessage) {
        Long dateTime = new Date().getTime();
        String loggerName = Thread.currentThread().getStackTrace()[2].getMethodName();
        Log log = new Log(dateTime, LogLevel.INFO, loggerName, logMessage);

        saveLog(log);
    }

    @Override
    public void generateWarnLog(String logMessage) {
        Long dateTime = new Date().getTime();
        String loggerName = Thread.currentThread().getStackTrace()[2].getMethodName();
        Log log = new Log(dateTime, LogLevel.WARN, loggerName, logMessage);

        saveLog(log);
    }

    @Override
    public void generateErrLog(String logMessage) {
        Long dateTime = new Date().getTime();
        String loggerName = Thread.currentThread().getStackTrace()[2].getMethodName();
        Log log = new Log(dateTime, LogLevel.ERROR, loggerName, logMessage);

        saveLog(log);
    }

    @Override
    public void generateErrLog(String logMessage, String stackTrace) {
        Long dateTime = new Date().getTime();
        String loggerName = Thread.currentThread().getStackTrace()[2].getMethodName();
        Log log = new Log(dateTime, LogLevel.ERROR, loggerName, logMessage, stackTrace);

        saveLog(log);
    }

    @Override
    public Page<Log> getAllLogs(Pageable pageable) {
        return logRepository.findAll(pageable);
    }

    @Override
    public Page<Log> filterLogs(String startDate, String endDate, String selectedLevel, String searchValue, Pageable pageable) throws LogException {
        startDate = this.checkAllFromField(startDate);
        endDate = this.checkAllFromField(endDate);
        selectedLevel = this.checkAllFromField(selectedLevel);
        searchValue = searchValue == null ? "" : searchValue;

        LogUtils.checkLogLevel(selectedLevel);
        String[] selectedLevels = new String[] {"INFO", "WARN", "ERROR"};
        if (!selectedLevel.equals(""))
            selectedLevels = new String[] {selectedLevel};

        long startDateVal = LogUtils.checkStartDate(startDate);
        long endDateVal = LogUtils.checkEndDate(endDate);
        return logRepository
                .findLogsByDateTimeBetweenAndLogLevelInAndLogMessageContainingOrDateTimeBetweenAndLogLevelInAndLoggerNameContaining(
                        startDateVal, endDateVal, selectedLevels, searchValue,
                        startDateVal, endDateVal, selectedLevels, searchValue,
                        pageable);
    }

    private String checkAllFromField(String field) {
        if (field == null){
            field = "";
        }else if (field.equalsIgnoreCase("all")){
            field = "";
        }
        return field;
    }
}
