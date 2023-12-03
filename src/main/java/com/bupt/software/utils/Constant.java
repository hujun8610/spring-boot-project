package com.bupt.software.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Constant {

    private Constant() {
        throw new IllegalStateException("Utility class");
    }

    public static final String RESULT_OK = "OK";
    public static final String RESULT_KO = "KO";

    public static final String GENERIC_RESPONSE_CODE = "GENERIC-ERROR";

    public static final String LOG_RESPONSE_CODE_PREFIX = "LOG-";

    public static Map<String, String> getLogResponseHashMap() {
        Map<String, String> logResponseHashMap = new HashMap<>();
        logResponseHashMap.put(LOG_RESPONSE_CODE_PREFIX + "1", "Log not found");
        logResponseHashMap.put(LOG_RESPONSE_CODE_PREFIX + "2", "Log already exists");
        logResponseHashMap.put(LOG_RESPONSE_CODE_PREFIX + "3", "Log not created");
        logResponseHashMap.put(LOG_RESPONSE_CODE_PREFIX + "4", "Log not updated");
        logResponseHashMap.put(LOG_RESPONSE_CODE_PREFIX + "5", "Log not deleted");
        logResponseHashMap.put(LOG_RESPONSE_CODE_PREFIX + "6", "Log found");
        logResponseHashMap.put(LOG_RESPONSE_CODE_PREFIX + "7", "Log created");
        logResponseHashMap.put(LOG_RESPONSE_CODE_PREFIX + "8", "Log updated");
        logResponseHashMap.put(LOG_RESPONSE_CODE_PREFIX + "9", "Log deleted");
        logResponseHashMap.put(LOG_RESPONSE_CODE_PREFIX + "10", "Log list found");
        return Collections.unmodifiableMap(logResponseHashMap);
    }
}
