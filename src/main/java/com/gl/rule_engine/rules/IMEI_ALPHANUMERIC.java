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

public class IMEI_ALPHANUMERIC implements ExecutionInterface {

    static final Logger logger = LogManager.getLogger(IMEI_ALPHANUMERIC.class);

    @Override
    public String executeRule(RuleInfo ruleEngine) {
        String res = "No";
        if (!ruleEngine.actualImei.matches("^[ 0-9 ]+$")) {  // imei.matches("^[ 0-9 ]+$"
            res = "Yes";
        }
        logger.debug("IMEI_ALPHANUMERIC response : if Yes then Fails ::  " + res);
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

//
//
//IMEI — numeric, between 0-9…
//MEID - alphanumeric, between 0-9-A-F…
//Both are 15 digit or 16 digits
//If the number is ESN, the length is either 8 or 11. If length is 11, then all value should be between 0-9.
//if length is 8, then value would be between 0-9,A-F
//In case of IMEI, it is called TAC, in case of MEID it is called Manufacturer code.
