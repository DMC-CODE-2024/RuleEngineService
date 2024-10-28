/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gl.rule_engine;

import com.gl.rule_engine.rules.*;

import java.util.List;

public interface RulesList {

    static List<Object> getItems() {
        return List.of(
                new IMEI_LENGTH(),
                new EXISTS_IN_GSMA_DETAILS_DB(),
                new EXISTS_IN_GREYLIST_DB(),
                new EXISTS_IN_ALL_ACTIVE_DB(),
                new IMEI_LUHN_CHECK(),
                new EXIST_IN_BLACKLIST_DB(),
                new IMEI_NULL(),
                new TEST_IMEI(),
                new EXISTS_IN_ALL_EDR_ACTIVE_DB(),
                new MDR(),
                new IMEI_PAIRING(),
                new DUPLICATE_DEVICE(),
                new NWL_VALIDITYFLAG(),
                new STOLEN(),
                new TRC(),
                new CUSTOM_LOCAL_MANUFACTURER(),
                new NATIONAL_WHITELISTS(),
                new CUSTOM_GDCE(),
                new LOCAL_MANUFACTURER(),
                new GSMA_TYPE_APPROVED(),
                new INVALID_IMEI(),
                new NWL_CUSTOM_VALIDITY_FLAG(),
                new GREYLIST_BY_LOST_STOLEN(),
                new BLOCKED_TAC(),
                new BLACKLIST_EIRSADMIN(),
                new BLACKLIST_STOLEN(),
                new BLACKLIST_PAIREXHAUST(),
                new IMEI_ALPHANUMERIC()

        );
    }
}
