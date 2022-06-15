package com.asdf.myhomeback.services;

import com.asdf.myhomeback.dto.RuleDTO;
import com.asdf.myhomeback.models.Rule;
import com.asdf.myhomeback.models.enums.RuleType;

import java.util.List;

public interface RuleService {

    Rule findOne(Long id);

    void save(RuleDTO ruleDTO) throws Exception;

    List<Rule> findAll();

    List<Rule> findAll(RuleType ruleType);

    void delete(Long id) throws Exception;
}
