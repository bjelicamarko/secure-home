package com.asdf.myhomeback.dto;

import com.asdf.myhomeback.models.Rule;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class RuleDTO {

    private Long id;
    private String regexPattern;
    private String ruleType;
    private String deviceName;
    private String logLevel;

    public RuleDTO(Rule rule){
        this.id = rule.getId();
        this.regexPattern = rule.getRegexPattern();
        this.ruleType = rule.getRuleType().toString();
        this.deviceName = rule.getDeviceName();
        this.logLevel = (rule.getLogLevel() == null ) ? null : rule.getLogLevel().toString();
    }
}
