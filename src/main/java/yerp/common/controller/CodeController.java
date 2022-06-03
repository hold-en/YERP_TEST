package yerp.common.controller;

import yerp.common.annotation.CommonParam;
import yerp.common.service.CommonService;
import yerp.common.util.APIResponse;
import yerp.common.util.ConstantUtil;
import yerp.common.util.ParameterUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/code")
public class CodeController {
	@Autowired
	private CommonService commonService;
	
	@RequestMapping("/getCommonCode")
	public ResponseEntity<JSONObject> get(@CommonParam Map<String, Object> parameter) {
		JSONArray sqlResult = new JSONArray();
		APIResponse response = new APIResponse();
		try {
			String sqlMap = "system.code.getCommonCode";
			sqlResult = commonService.selectList(sqlMap, parameter);
			response.setResponse(sqlResult);
		} catch (Exception e) {
			response.setResponseForError(e);
		}
		return response.getEntity();
	}
}
