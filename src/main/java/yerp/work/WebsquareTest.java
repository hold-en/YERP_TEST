package yerp.work;

import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import yerp.common.annotation.CommonParam;
import yerp.common.service.CommonService;
import yerp.common.util.APIResponse;
import yerp.common.util.ParameterUtil;

@RestController
@RequestMapping("/test")
public class WebsquareTest {
	@Autowired
	private CommonService commonService;
	
	@GetMapping("/getTestList")
	public ResponseEntity<JSONObject> getTestList(@CommonParam Map<String, Object> parameter) {
		JSONArray sqlResult = null;
		APIResponse response = new APIResponse();
		try {
			JSONObject normal = ParameterUtil.getNormal(parameter);
			JSONArray body = ParameterUtil.getBody(parameter);
			JSONObject common = ParameterUtil.getCommon(parameter);
			
			sqlResult = commonService.selectList("test.getTestList", parameter);
			response.setResponse(sqlResult);
		} catch (Exception e) {
			response.setResponseForError(e);
		}
		return response.getEntity();
	}
}
