package com.asdf.myhomeback.repositories;

import com.asdf.myhomeback.models.Rule;
import com.asdf.myhomeback.models.enums.RuleType;
import org.springframework.boot.logging.LogLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RuleRepository extends JpaRepository<Rule, Long> {

    Boolean existsRuleByRegexPatternAndDeviceNameAndRuleType(String regexPattern, String deviceName, RuleType ruleType);

    @Query("select r from Rule r where r.regexPattern = ?1 and r.logLevel = ?2")
    Boolean existsByRegexPatternAndLogLevel(String regexPattern, LogLevel logLevel);

    Boolean existsRuleByRegexPatternAndLogLevel(String regexPattern, LogLevel logLevel);

    List<Rule> findAllByRuleType(RuleType ruleType);
}