package com.asdf.myhomeback.models;

import com.asdf.myhomeback.dto.AlarmRuleDTO;
import com.asdf.myhomeback.models.enums.AlarmType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class AlarmRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", unique=true, nullable=false)
    private Long id;

    @Column(name = "rule_pattern", nullable=false)
    private String rulePattern;

    @Enumerated(EnumType.STRING)
    @Column(name = "alarm_type", nullable = false)
    private AlarmType alarmType;

    @Column(name = "device_name")
    private String deviceName;

    public AlarmRule(AlarmRuleDTO alarmRuleDTO){
        this.rulePattern = alarmRuleDTO.getRulePattern();
        this.alarmType = AlarmType.valueOf(alarmRuleDTO.getAlarmType());
        this.deviceName = alarmRuleDTO.getDeviceName();
    }

}
