package io.cronica.api.pdfgenerator.utils;

import io.cronica.api.pdfgenerator.exception.InvalidRequestException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.web3j.crypto.Hash;
import org.web3j.utils.Numeric;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class DocumentUtils {

    public static String getSha256(final byte[] bytes) {
        return Numeric.toHexStringNoPrefix(Hash.sha256(bytes));
    }

    public static String readDocumentAddress(final String documentID) {
        if (StringUtils.isEmpty(documentID) || !documentID.substring(0, 2).equals("0x")) {
            log.info("[UTILITY] sent string is not ID of document");
            throw new InvalidRequestException("Sent string is not ID of document");
        }
        return StringUtils.substring(documentID, 0, 42);
    }

    public static Map<String, Object> modifyParameters(final Map<String, Object> initialMap) {
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
