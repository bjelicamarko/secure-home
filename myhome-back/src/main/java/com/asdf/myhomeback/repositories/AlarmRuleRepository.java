package com.asdf.myhomeback.repositories;

import com.asdf.myhomeback.models.AlarmRule;
import com.asdf.myhomeback.models.enums.AlarmType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlarmRuleRepository extends JpaRepository<AlarmRule, Long> {

    List<AlarmRule> findAlarmRulesByAlarmType(AlarmType alarmType);

    Boolean existsAlarmRuleByRulePatternAndAlarmTypeAndDeviceName(String rulePattern, AlarmType alarmType, String deviceName);
}
