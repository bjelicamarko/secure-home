package com.asdf.myhomeback.services.impls;

import com.asdf.myhomeback.dto.RuleDTO;
import com.asdf.myhomeback.models.Rule;
import com.asdf.myhomeback.models.enums.RuleType;
import com.asdf.myhomeback.repositories.RuleRepository;
import com.asdf.myhomeback.services.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleServiceImpl implements RuleService {

    @Autowired
    private RuleRepository ruleRepository;

    @Override
    public Rule findOne(Long id) {
        return ruleRepository.getById(id);
    }

    @Override
    public void save(RuleDTO ruleDTO) throws Exception {
        Rule rule = new Rule();

        if (ruleDTO.getRegexPattern() == null || ruleDTO.getRegexPattern().equals(""))
            throw new Exception("Rule has no string pattern.");

        rule.setRegexPattern(ruleDTO.getRegexPattern());

        if(ruleDTO.getRuleType() == null || ruleDTO.getRuleType().equals(""))
            throw new Exception("Rule is missing type.");

        rule.setRuleType(RuleType.valueOf(ruleDTO.getRuleType()));

        checkRuleType(rule, ruleDTO.getDeviceName(), ruleDTO.getLogLevel());

        checkDuplicate(rule);

        rule.setRuleType(RuleType.valueOf(ruleDTO.getRuleType()));

        ruleRepository.save(rule);
    }

    private void checkRuleType(Rule rule, String deviceName, String logLevel) throws Exception {
        if(rule.getRuleType().equals(RuleType.ALARM) || rule.getRuleType().equals(RuleType.DATABASE))
            if (deviceName == null  || deviceName.equals(""))
                throw new Exception("Rule has not device name.");
            else
                rule.setDeviceName(deviceName);
        else
        if (logLevel == null || logLevel.equals(""))
            throw new Exception("Rule has not log level.");
        else
            rule.setLogLevel(LogLevel.valueOf(logLevel));
    }

    private void checkDuplicate(Rule rule) throws Exception {
        if (rule.getRuleType().equals(RuleType.ALARM)) {
            if (checkIfRuleExist(rule))
                throw new Exception("Alarm rule already exist.");
        }
        else if (rule.getRuleType().equals(RuleType.LOG)) {
            if (checkIfLogRuleExist(rule))
                throw new Exception("Log rule already exist");
        }
        else if (rule.getRuleType().equals(RuleType.DATABASE)) {
            if (checkIfRuleExist(rule))
                throw new Exception("Database rule already exist.");
        }
    }

    private boolean checkIfRuleExist(Rule rule) {
        return ruleRepository.existsRuleByRegexPatternAndDeviceNameAndRuleType(rule.getRegexPattern(),  rule.getDeviceName(), rule.getRuleType());
    }

    private boolean checkIfLogRuleExist(Rule rule) {
        return ruleRepository.existsRuleByRegexPatternAndLogLevel(rule.getRegexPattern(),  rule.getLogLevel());
    }

    @Override
    public List<Rule> findAll() {
        return ruleRepository.findAll();
    }

    @Override
    public List<Rule> findAll(RuleType ruleType) {
        return ruleRepository.findAllByRuleType(ruleType);
    }

    @Override
    public void delete(Long id) throws Exception {
        if (id == null){
            throw new Exception("Rule id is null.");
        }
        Rule rule = findOne(id);
        if (rule == null) {
            throw new Exception("Rule doesnt exist.");
        }
        ruleRepository.delete(rule);
    }
}
