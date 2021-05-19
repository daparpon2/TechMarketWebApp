/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.conselleria.daparpon.techmarket.utils;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Yo mismo
 */
public class UtilityFunctions {
    
    public static Map<String, String> processInputParameters(String body) {
        Map<String, String> result = new HashMap();
        String[] params = body.split("&");
        for (String param : params) {
            String[] keyValuePair = param.split("=");
            result.put(keyValuePair[0], keyValuePair[1]);
        }
        return result;
    }
    
}
