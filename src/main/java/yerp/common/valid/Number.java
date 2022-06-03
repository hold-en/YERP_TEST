package yerp.common.valid;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Number extends DefaultValidUtil implements Valid {
    private List<String> keyList = new ArrayList<>();

    public Number() {
        keyList = new ArrayList<>();
    }

    public Number(String[] keys) {
        keyList = new ArrayList<String>(Arrays.asList(keys));
    }

    public void add(String key) {
        if (!keyList.contains(key)) {
            keyList.add(key);
        }
    }

    @Override
    public JSONObject valid(JSONObject parameter) throws Exception {
        JSONObject validResult = new JSONObject();
        JSONArray invalidKeyList = new JSONArray();
        Iterator<String> iter = parameter.keySet().iterator();
        for(String key : keyList) {
        	if(parameter.containsKey(key) && !isNull(String.valueOf(parameter.get(key))) && !isNumber(String.valueOf(parameter.get(key)))) {
        		invalidKeyList.add(key);
        	}
        }
      
        if (invalidKeyList.size() > 0) {
            validResult.put("key", invalidKeyList);
            validResult.put("data", parameter);
        }
        return validResult;
    }

}
