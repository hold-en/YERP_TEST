package yerp.common.valid;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;

import yerp.common.util.APIResponse;
import yerp.common.util.ConstantUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Validator {
    private List<Valid> validList = new ArrayList<>();
    private boolean isPassed = false;

    public Validator() {
        validList = new ArrayList<>();
    }

    public Validator(Valid[] keys) {
        validList = new ArrayList<Valid>(Arrays.asList(keys));
    }

    public void add(Valid valid) {
        if (!validList.contains(valid)) {
            validList.add(valid);
        }
    }
    
    public boolean isPass() {
    	return isPassed;
    }

    public JSONArray run(Object target) throws Exception {
        JSONArray response = new JSONArray();
        JSONArray validTarget = new JSONArray();
        JSONObject tempJson = null;
        JSONObject validResult = null;

        if (target.getClass() != JSONObject.class && target.getClass() != JSONArray.class) {
            return response;
        }

        if(target.getClass() == JSONObject.class) validTarget.add(target);
        else validTarget = (JSONArray) target;

        for (Valid valid : validList) {

            for(Object object : validTarget) {
                JSONObject row = (JSONObject) object;
                validResult = valid.valid(row);

                if (validResult != null && validResult.size() > 0) {
                    tempJson = new JSONObject();
                    tempJson.put(valid.getClass().getSimpleName(), validResult);
                    response.add(tempJson);
                }
            }


        }
        
        if(response != null && response.size() == 0) {
        	isPassed = true;
        } else {
        	isPassed = false;
        }
        return response;
    }
}
