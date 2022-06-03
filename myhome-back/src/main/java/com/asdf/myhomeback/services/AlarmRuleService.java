package com.asdf.myhomeback.services;

import com.asdf.myhomeback.dto.AlarmRuleDTO;
import com.asdf.myhomeback.exceptions.AlarmRuleException;
import com.asdf.myhomeback.models.AlarmRule;
import com.asdf.myhomeback.models.enums.AlarmType;

import java.util.List;

public interface AlarmRuleService {

    AlarmRule findOne(Long id);

    void save(AlarmRuleDTO alarmRule) throws Exception;

    List<AlarmRule> findAllByType(AlarmType alarmRuleType);

    List<AlarmRule> findAll();

    void delete(Long id) throws Exception;
}
