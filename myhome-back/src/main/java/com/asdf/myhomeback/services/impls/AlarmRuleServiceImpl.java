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
    public void save(AlarmRule alarmRule) {
        if (alarmRule.getDeviceName().equals("LOG"))
            alarmRule.setAlarmType(AlarmType.LOG);
        else
            alarmRule.setAlarmType(AlarmType.DEVICE);
        alarmRuleRepository.save(alarmRule);
    }

    @Override
    public void update(AlarmRuleDTO alarmRuleDTO) throws AlarmRuleException {
        if (alarmRuleDTO.getId() == null)
            throw new AlarmRuleException("AlarmRule id is null.");
        AlarmRule alarmRule = findOne(alarmRuleDTO.getId());
        alarmRule.setRulePattern(alarmRuleDTO.getRulePattern());
        alarmRuleRepository.save(alarmRule);
    }

    @Override
    public List<AlarmRule> findAllByType(AlarmType alarmRuleType) {
        return alarmRuleRepository.findAlarmRulesByAlarmType(alarmRuleType);
    }
}
