package com.asdf.myhomeback.dto;

import com.asdf.myhomeback.models.AlarmRule;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AlarmRuleDTO {

    private Long id;
    private String rulePattern;
    private String alarmType;
    private String deviceName;

    public AlarmRuleDTO(AlarmRule alarmRule){
        this.id = alarmRule.getId();
        this.rulePattern = alarmRule.getRulePattern();
        this.alarmType = alarmRule.getAlarmType().toString();
        this.deviceName = alarmRule.getDeviceName();
    }
}
