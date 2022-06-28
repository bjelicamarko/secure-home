package com.asdf.myhomeback.models;

import com.asdf.myhomeback.models.enums.RuleType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.logging.LogLevel;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Rule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name="rule_type", nullable=false)
    RuleType ruleType;

    @Column(name = "regex_pattern", nullable=false)
    private String regexPattern;

    @Column(name = "device_name")
    private String deviceName;

    @Enumerated(EnumType.STRING)
    @Column(name = "log_level")
    private LogLevel logLevel;
}
