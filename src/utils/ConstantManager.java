/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.HashMap;

/**
 * contains global constant for octopus
 * @author ahmed
 */
public class ConstantManager {

    /**
     * Return Octopus name and version
     */
    public final static String SCORPION_VERSION = "Octopus V1.4";
    /**
     * Return Octopus icon
     */
    public final static String SCORPION_ICON_PATH = "scorpion.png";
    /**
     * Return driver name to run using Class.forName()
     */
    public final static String ORACLE_DRIVER = "oracle.jdbc.OracleDriver";
    /**
     * Return all tables on schema
     */
    public final static String USER_TABLES = "select object_name from user_objects where object_type = 'TABLE'";
   
    /**
     * Return the allowed file extensions allowed in Octopus
     */
    public final static String[] ALLOWED_FILE_EXTENTIONS = {".dmp", ".log", ".sql",".png",".java"};


}
