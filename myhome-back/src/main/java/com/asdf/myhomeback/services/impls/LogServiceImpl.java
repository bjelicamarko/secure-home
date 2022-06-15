package com.asdf.myhomeback.services.impls;

import com.asdf.myhomeback.exceptions.LogException;
import com.asdf.myhomeback.models.AlarmNotification;
import com.asdf.myhomeback.models.Log;
import com.asdf.myhomeback.models.Rule;
import com.asdf.myhomeback.models.enums.AlarmType;
import com.asdf.myhomeback.models.enums.RuleType;
import com.asdf.myhomeback.repositories.mongo.LogRepository;
import com.asdf.myhomeback.services.AlarmNotificationService;
import com.asdf.myhomeback.services.LogService;
import com.asdf.myhomeback.services.RuleService;
import com.asdf.myhomeback.utils.LogUtils;
import com.asdf.myhomeback.websocket.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private RuleService ruleService;

    @Autowired
    private AlarmNotificationService alarmNotificationService;

    @Autowired
    private WebSocketService webSocketService;

    @Override
    public void saveLog(Log log){
        // check if some log rule will trigger notification
        List<AlarmNotification> alarmNotifications = getAlarmNotifications(log);

        // save all notifications
        alarmNotificationService.saveAll(alarmNotifications);

        // send all notifications
        webSocketService.sendNotifications(alarmNotifications, AlarmType.LOG);

        logRepository.save(log);
    }

    private List<AlarmNotification> getAlarmNotifications(Log log) {
        List<Rule> logRules = ruleService.findAll(RuleType.LOG);
        List<AlarmNotification> alarmNotifications = new ArrayList<>();
        logRules.forEach(logRule -> {
            // if log contains some string or logLevel is same as in the rule
            if (log.getLogMessage().contains(logRule.getRegexPattern()) && log.getLogLevel().equals(logRule.getLogLevel())){
                alarmNotifications.add(new AlarmNotification(log.getLogMessage(), AlarmType.LOG, null, "admin"));
            }
        });
        return alarmNotifications;
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
    public Page<Log> filterLogs(String startDate, String endDate, String selectedLevel, String searchValue, String messageRegex, Pageable pageable) throws LogException {

        byte[] decodedBytes = Base64.getDecoder().decode(messageRegex);
        messageRegex = new String(decodedBytes);

        startDate = this.checkAllFromField(startDate);
        endDate = this.checkAllFromField(endDate);
        selectedLevel = this.checkAllFromField(selectedLevel);
        searchValue = searchValue == null ? "" : searchValue;

        LogUtils.checkLogLevel(selectedLevel);
        String[] selectedLevels = new String[] {"INFO", "WARN", "ERROR", "FATAL"};
        if (!selectedLevel.equals(""))
            selectedLevels = new String[] {selectedLevel};

        long startDateVal = LogUtils.checkStartDate(startDate);
        long endDateVal = LogUtils.checkEndDate(endDate);

        if (searchValue.equals(""))
            return logRepository.findLogsByDateTimeBetweenAndLogLevelInAndLogMessageRegex(startDateVal, endDateVal, selectedLevels, messageRegex, pageable);

        else if (messageRegex.equals(""))
            return logRepository.findLogsByDateTimeBetweenAndLogLevelInAndLoggerNameContaining(startDateVal, endDateVal, selectedLevels, searchValue, pageable);

        else
            return logRepository.findLogsByDateTimeBetweenAndLogLevelInAndLogMessageRegexAndLoggerNameContaining(
                    startDateVal, endDateVal, selectedLevels, messageRegex, searchValue, pageable);
    }

    @Override
    public void generateFatalLog(String logMessage) {
        Long dateTime = new Date().getTime();
        String loggerName = Thread.currentThread().getStackTrace()[2].getMethodName();
        Log log = new Log(dateTime, LogLevel.FATAL, loggerName, logMessage);

        saveLog(log);
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
