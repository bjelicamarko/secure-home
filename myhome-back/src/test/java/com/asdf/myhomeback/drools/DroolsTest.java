package com.asdf.myhomeback.drools;

import com.asdf.myhomeback.dto.IdDTO;
import com.asdf.myhomeback.models.AlarmNotification;
import com.asdf.myhomeback.models.Log;
import com.asdf.myhomeback.models.Rule;
import com.asdf.myhomeback.models.drools.IpAddress;
import com.asdf.myhomeback.models.enums.RuleType;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.boot.logging.LogLevel;
import org.kie.api.runtime.ClassObjectFilter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DroolsTest {

    private static KieContainer kieContainer;

    @Test
    public void testLogError() {

        kieContainer = KieServices.Factory.get().getKieClasspathContainer();
        KieSession kieSession = kieContainer.newKieSession("ExampleSession");

        kieSession.getAgenda().getAgendaGroup("test_agenda").setFocus();

        Log log = new Log(System.currentTimeMillis(), LogLevel.ERROR, "createAuthenticationToken", "Login failed with bad credentials for username: 'admin'");
        Rule logRule = new Rule();
        logRule.setRuleType(RuleType.LOG);
        logRule.setLogLevel(LogLevel.ERROR);
        logRule.setRegexPattern("bad credentials");

        kieSession.insert(logRule);
        kieSession.insert(log);
        kieSession.fireAllRules();

        Collection<AlarmNotification> results = (Collection<AlarmNotification>) kieSession.getObjects(new ClassObjectFilter(AlarmNotification.class));

        AlarmNotification[] alarms = results.toArray(new AlarmNotification[results.size()]);

        long min_timeStamp = Long.MAX_VALUE;
        AlarmNotification lastAlarmNotification = new AlarmNotification();
        for (AlarmNotification alarmNotification : alarms) {
            if (alarmNotification.getTimestamp() < min_timeStamp) {
                min_timeStamp = alarmNotification.getTimestamp();
                lastAlarmNotification = alarmNotification;
            }
        }

        assertEquals(1, results.size());
        assertEquals(log.getLogMessage(), lastAlarmNotification.getMessage());

    }

    @Test
    public void testMaliciousIPAddress() {

        kieContainer = KieServices.Factory.get().getKieClasspathContainer();
        KieSession kieSession = kieContainer.newKieSession("ExampleSession");

        kieSession.getAgenda().getAgendaGroup("test_agenda").setFocus();

        IpAddress remoteAddress = new IpAddress("0:0:0:0:0:0:0:1");

        List<IpAddress> maliciousAddresses = loadMaliciousIpAddresses();

        kieSession.insert(remoteAddress);
        kieSession.insert(maliciousAddresses);

        kieSession.fireAllRules();

        Collection<AlarmNotification> results = (Collection<AlarmNotification>) kieSession.getObjects(new ClassObjectFilter(AlarmNotification.class));

        AlarmNotification[] alarms = results.toArray(new AlarmNotification[results.size()]);

        assertEquals(1, results.size());
        assertEquals("Spring application was hit from malicious address '0:0:0:0:0:0:0:1'", (Arrays.stream(alarms).findFirst()).get().getMessage());

    }

    public List<IpAddress> loadMaliciousIpAddresses() {

        File file = new File("src/main/resources/malicious_ip_address.txt");
        BufferedReader br = null;
        List<IpAddress> ipAddresses = new ArrayList<>();
        try {
            br = new BufferedReader(new FileReader(file));

            String str;
            while ((str = br.readLine()) != null)
                ipAddresses.add(new IpAddress(str));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return ipAddresses;
    }
}
