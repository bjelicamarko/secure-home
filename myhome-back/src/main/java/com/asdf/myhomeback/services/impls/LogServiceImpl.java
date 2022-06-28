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
import org.kie.api.runtime.ClassObjectFilter;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
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

    @Autowired
    private KieContainer kieContainer;

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
            if(logRule.getLogLevel().equals(LogLevel.ERROR))
                droolsRuleLogError(logRule, log, alarmNotifications);
            else
                // if log contains some string and logLevel is same as in the rule
                if (log.getLogMessage().contains(logRule.getRegexPattern()) && log.getLogLevel().equals(logRule.getLogLevel()))
                    alarmNotifications.add(new AlarmNotification(log.getLogMessage(), AlarmType.LOG, null, "admin"));

        });
        return alarmNotifications;
    }

    private void droolsRuleLogError(Rule logRule, Log log, List<AlarmNotification> alarmNotifications) {
        KieSession kieSession = kieContainer.newKieSession("ExampleSession");
        kieSession.getAgenda().getAgendaGroup("test_agenda").setFocus();

        kieSession.insert(logRule);

        kieSession.insert(log);
        kieSession.fireAllRules();

        Collection<AlarmNotification> results = (Collection<AlarmNotification>) kieSession.getObjects(new ClassObjectFilter(AlarmNotification.class));
        if(results.size() != 0){
            alarmNotifications.add(results.stream().findFirst().get());
        }
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
        return logRepository.findAllByDateTimeIsNotNullOrderByDateTimeDesc(pageable);
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
            return logRepository.findLogsByDateTimeBetweenAndLogLevelInAndLogMessageRegexOrderByDateTimeDesc(startDateVal, endDateVal, selectedLevels, messageRegex, pageable);

        else if (messageRegex.equals(""))
            return logRepository.findLogsByDateTimeBetweenAndLogLevelInAndLoggerNameContainingOrderByDateTimeDesc(startDateVal, endDateVal, selectedLevels, searchValue, pageable);

        else
            return logRepository.findLogsByDateTimeBetweenAndLogLevelInAndLogMessageRegexAndLoggerNameContainingOrderByDateTimeDesc(
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
