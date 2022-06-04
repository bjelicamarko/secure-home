package com.asdf.myhomeback.services.impls;

import com.asdf.myhomeback.dto.AlarmRuleDTO;
import com.asdf.myhomeback.exceptions.AlarmRuleException;
import com.asdf.myhomeback.models.AlarmRule;
import com.asdf.myhomeback.models.enums.AlarmType;
import com.asdf.myhomeback.repositories.AlarmRuleRepository;
import com.asdf.myhomeback.services.AlarmRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlarmRuleServiceImpl implements AlarmRuleService {

    @Autowired
    private AlarmRuleRepository alarmRuleRepository;

    public AlarmRule findOne(Long id){
        return alarmRuleRepository.findById(id).orElse(null);
    }

    @Override
    public void save(AlarmRuleDTO alarmRuleDTO) throws Exception {
        AlarmRule alarmRule = new AlarmRule();
        if (alarmRuleDTO.getRulePattern() == null || alarmRuleDTO.getRulePattern().equals(""))
            throw new Exception("Rule has no string pattern.");
        alarmRule.setRulePattern(alarmRuleDTO.getRulePattern());
        if (alarmRuleDTO.getDeviceName().equals("") || (alarmRuleDTO.getDeviceName() == null) || alarmRuleDTO.getDeviceName().equals("LOG"))
            alarmRule.setAlarmType(AlarmType.LOG);
        else {
            alarmRule.setDeviceName(alarmRuleDTO.getDeviceName());
            alarmRule.setAlarmType(AlarmType.DEVICE);
        }
        if (checkIfAlarmRuleExist(alarmRule))
            throw new Exception("Alarm rule already exist.");
        alarmRuleRepository.save(alarmRule);
    }

    private Boolean checkIfAlarmRuleExist(AlarmRule alarmRule) {
        return alarmRuleRepository.existsAlarmRuleByRulePatternAndAlarmTypeAndDeviceName(
                alarmRule.getRulePattern(), alarmRule.getAlarmType(), alarmRule.getDeviceName());
    }

    @Override
    public List<AlarmRule> findAllByType(AlarmType alarmRuleType) {
        return alarmRuleRepository.findAlarmRulesByAlarmType(alarmRuleType);
    }

    @Override
    public List<AlarmRule> findAll() {
        return alarmRuleRepository.findAll();
    }

    @Override
    public void delete(Long id) throws Exception {
        if (id == null){
            throw new Exception("Alarm rule id is null.");
        }
        alarmRuleRepository.delete(findOne(id));
    }
}
