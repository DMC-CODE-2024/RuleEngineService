/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gl.rule_engine.rules;

import com.gl.rule_engine.ExecutionInterface;
import com.gl.rule_engine.RuleInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IMEI_LENGTH implements ExecutionInterface {

    static final Logger logger = LogManager.getLogger(IMEI_LENGTH.class);

    @Override
    public String executeRule(RuleInfo ruleEngine) {
        String res = "No";
        if (ruleEngine.actualImei.length() == 14 || ruleEngine.actualImei.length() == 15 || ruleEngine.actualImei.length() == 16) {
            res = "Yes";
        }
   //     logger.info("IMEI_LENGTH " + ruleEngine.actualImei + "::" + ruleEngine.actualImei.length() + "::RESPONSE  if NO then fail ::" + res);
        return res;
    }

    @Override
    public String executeAction(RuleInfo ruleEngine) {

        try {
            switch (ruleEngine.action) {
                case "Allow": {
                    logger.debug("Action is Allow");
                }
                break;
                case "Skip": {
                    logger.debug("Action is Skip");
                }
                break;
                case "Reject": {
                    logger.debug("Action is Reject");
                }
                break;
                case "Block": {
                    logger.debug("Action is Block");
                }
                break;
                case "Report": {
                    logger.debug("Action is Report");
                }
                break;
                case "SYS_REG": {
                    logger.debug("Action is SYS_REG");
                }
                break;
                case "USER_REG": {
                    logger.debug("Action is USER_REG");
                }
                break;
                default:
                    logger.debug(" The Action " + ruleEngine.action + "  is Not Defined  ");
            }

            return "Success";
        } catch (Exception e) {
            logger.debug(" Error " + e);
            return "Failure";
        }
    }

}