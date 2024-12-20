/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gl.BlackList.Dao;

import com.gl.BlackList.model.BlacklistTacDb;
import com.gl.BlackList.model.BlacklistTacDeviceHistoryDb;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;


public class BlacklistServiceDao {

    static final Logger logger = LogManager.getLogger(BlacklistServiceDao.class);

    public String getValueFromSysParam(Connection conn, String tag) {
        String query = "  select value  from sys_param  where tag = '" + tag + "' ";
        String response = null;
        try (Statement stmt = conn.createStatement(); ResultSet rs0 = stmt.executeQuery(query);) {
            while (rs0.next()) {
                response = rs0.getString(1);
            }
            logger.debug("Value from sys_Param for tag " + tag + " is " + response);
        } catch (Exception e) {
            logger.debug("Error while getting details from sys_Param" + e);
        }
        return response;
    }

    public String getBlacklistStatus(Connection conn, String Imei) {
        String status = null;
        try {
            try (Statement stmt = conn.createStatement()) {
                String query = " select blocklist_status from blacklist_imei_details  where device_id = '" + Imei + "'   ";
                logger.debug("Query [" + query + "]");
                try (ResultSet rs = stmt.executeQuery(query)) {
                    while (rs.next()) {
                        status = rs.getString("blocklist_status");
                    }
                }
            }
            logger.debug("  Status  " + status);
        } catch (Exception e) {
            logger.error("Error " + e);
        }
        return status;
    }

    public String databaseMapper(BlacklistTacDb blacklistTacDb, Connection conn) {
        String rslt = null;
        try {
            rslt = blacklistTacDb.getBlockliststatus();
            int id = insertBlacklistTacDbAndGetId(conn, blacklistTacDb);
            List<BlacklistTacDeviceHistoryDb> blacklistTacDeviceHistoryDb = blacklistTacDb.getImeihistory();
            insertBlacklistTacDeviceHistory(conn, id, blacklistTacDeviceHistoryDb);
        } catch (Exception ex) {
            logger.debug(" .." + ex);
        }
        return rslt;
    }

    private int insertBlacklistTacDbAndGetId(Connection conn, BlacklistTacDb blacklistTacDb) {
        int generatedKey = 0;
        String query = " insert into blacklist_imei_details (ref_code, response_status, device_id, partner_id, blocklist_status, generallist_status,"
                + "manufacturer, brand_name, marketing_name, model_name , band , operating_sys , nfc , bluetooth , wlan , device_type, "
                + "created_on , modified_on)"
                + "values ('" + blacklistTacDb.getRefcode() + "', '" + blacklistTacDb.getResponsestatus() + "' , '" + blacklistTacDb.getDeviceid() + "' ,'" + blacklistTacDb.getPartnerid() + "' , '" + blacklistTacDb.getBlockliststatus() + "', '" + blacklistTacDb.getGeneralliststatus() + "' , "
                + "  '" + blacklistTacDb.getManufacturer() + "' ,  '" + blacklistTacDb.getBrandname() + "', '" + blacklistTacDb.getMarketingname() + "' , '" + blacklistTacDb.getModelname() + "', "
                + "  '" + blacklistTacDb.getBand() + "' ,  '" + blacklistTacDb.getOperatingsys() + "', '" + blacklistTacDb.getNfc() + "' , '" + blacklistTacDb.getBluetooth() + "', '" + blacklistTacDb.getWLAN() + "' , '" + blacklistTacDb.getDevicetype() + "', "
                + "  CURRENT_TIMESTAMP , CURRENT_TIMESTAMP )";
        logger.debug("query .." + query);
        try {
            PreparedStatement ps = null;
            if (conn.toString().contains("oracle")) {
                ps = conn.prepareStatement(query, new String[]{"ID"});
            } else {
                ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            }
            ps.execute();
            logger.debug("Going for getGenerated key  ");
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }
            logger.info("Inserted record's ID: " + generatedKey);
            rs.close();
        } catch (Exception e) {
            logger.error("Failed  " + e);
        }
        return generatedKey;
    }

    private void insertBlacklistTacDeviceHistory(Connection conn, int id, List<BlacklistTacDeviceHistoryDb> blacklistTacDeviceHistoryDb) {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            for (BlacklistTacDeviceHistoryDb btr : blacklistTacDeviceHistoryDb) {
                var query = " insert  into blacklist_imei_block_history ( action, reasoncode, reasoncodedesc, action_taken_by, country ,blacklist_imei_db_id , created_on) "
                        + "values( '" + btr.getAction() + "', '" + btr.getReasoncode() + "',  '" + btr.getReasoncodedesc() + "',  '" + btr.getBy() + "',  '" + btr.getCountry() + "', '" + id + "'  , CURRENT_TIMESTAMP  )";
                logger.debug(" Query:: " + query);
                stmt.executeUpdate(query);
            }
            stmt.close();
        } catch (SQLException ex) {
            logger.error(" " + ex);
        }
    }

}
