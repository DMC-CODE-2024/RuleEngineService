/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gl.rule_engine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RuleEngineApplication {

    static final Logger logger = LogManager.getLogger(RuleEngineApplication.class);

    public static String startRuleEngine(RuleInfo ruleEngine) {

        return RulesList.getItems()
                .stream()
              //  .filter(a -> a.getClass().getName().toString().contains(ruleEngine.ruleName))  //.getSimpleName()
                .filter(a -> a.getClass().getName().toString().endsWith("."+ruleEngine.ruleName))
                .map(ExecutionInterface.class::cast)
                .reduce(new String(), (result, ruleNode) -> {
                    String key = ruleEngine.executeRuleAction;
                    if (key.contains("executeRule")) {
                        result = ruleNode.executeRule(ruleEngine);
                    } else {
                        result = ruleNode.executeAction(ruleEngine);
                    }
                    return result;
                }, (cumulative, intermediate) -> {
                    return intermediate;
                });
    }
}