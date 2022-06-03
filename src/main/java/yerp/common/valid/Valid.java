package yerp.common.valid;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@FunctionalInterface
public interface Valid {
    JSONObject valid(JSONObject parameter) throws Exception;
}
