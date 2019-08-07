package io.cronica.api.pdfgenerator.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DocumentUtils {

    public static Map<String, Object> modifyParameters(Map<String, Object> initialMap) {
        final Map<String, Object> modifiedMap = new HashMap<>();
        for (String key : initialMap.keySet()) {
            // if parameter in JSON is mapped to collection - skip it
            if (initialMap.get(key) instanceof Collection<?>) {
                modifiedMap.put(key, initialMap.get(key));
                continue;
            }
            modifiedMap.put("@@@" + key + "@@@", initialMap.get(key));
        }

        return modifiedMap;
    }

}
