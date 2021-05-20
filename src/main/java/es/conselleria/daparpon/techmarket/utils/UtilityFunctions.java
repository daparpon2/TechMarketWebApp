package es.conselleria.daparpon.techmarket.utils;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Daniel Pardo Pont
 */
public class UtilityFunctions {
    
    public static Map<String, String> processInputParameters(String body) {
        Map<String, String> result = new HashMap();
        String[] params = body.split("&");
        for (String param : params) {
            String[] keyValuePair = param.split("=");
            if(keyValuePair.length > 1) {
                result.put(keyValuePair[0], keyValuePair[1]);
            } else {
                result.put(keyValuePair[0], null);
            }
        }
        return result;
    }
    
}
