package yerp.common.util;

import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ParameterUtil {
    public static JSONObject getNormal(Map<String, Object> parameter) {
        return convertJSON(String.valueOf(parameter == null? "{}" : parameter.get(ConstantUtil.NORMAL)));
    }

    public static JSONArray getBody(Map<String, Object> parameter) {
        return convertJSONArray(String.valueOf(parameter == null? "[]" : parameter.get(ConstantUtil.BODY)));
    }

    public static JSONObject getCommon(Map<String, Object> parameter) {
        return convertJSON(String.valueOf(parameter == null? "{}" : parameter.get(ConstantUtil.COMMON)));
    }

    public static JSONObject getCustom(Map<String, Object> parameter) {
        return convertJSON(String.valueOf(parameter == null? "{}" : parameter.get(ConstantUtil.CUSTOM)));
    }

    public static JSONObject convertJSON(String str) {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) parser.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return jsonObject == null? new JSONObject() : jsonObject;
    }

    public static JSONArray convertJSONArray(String str) {
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = null;
        try {
            jsonArray = (JSONArray) parser.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    public static void addCustom(Map<String, Object> parameter, JSONObject custom) {
        parameter.put(ConstantUtil.CUSTOM, custom);
    }
    
    public static void addCustom(Map<String, Object> parameter, String key, String value) {
    	JSONObject custom = getCustom(parameter);
    	custom.put(key, value);
        parameter.put(ConstantUtil.CUSTOM, custom);
    }
}
