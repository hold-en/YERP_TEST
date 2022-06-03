package yerp.common.valid;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Length extends DefaultValidUtil implements Valid {
    private int min = -1;
    private int max = -1;

    private List<String> keyList = new ArrayList<>();

    public Length() {
        keyList = new ArrayList<>();
    }

    public Length(String[] keys) {
        keyList = new ArrayList<String>(Arrays.asList(keys));
    }

    public Length(String[] keys, int min, int max) {
        keyList = new ArrayList<String>(Arrays.asList(keys));
        this.min = min;
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
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
        String value = "";
        if (min > 0 && max > 0) {
            for (String key : keyList) {
                value = String.valueOf(parameter.get(key));
                if (parameter.containsKey(key) && !isNull(value)) {
                    if (value.length() > max || value.length() < min) {
                        invalidKeyList.add(key);
                    }
                }
            }
        }
        if (invalidKeyList.size() > 0) {
            validResult.put("key", invalidKeyList);
            validResult.put("data", parameter);
        }
        return validResult;
    }

}
