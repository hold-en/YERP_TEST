package yerp.work.HR.HRB;

import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import yerp.common.annotation.CommonParam;
import yerp.common.service.CommonService;
import yerp.common.util.APIResponse;
import yerp.common.util.ParameterUtil;
import yerp.common.util.SQLMap;
import yerp.common.valid.Validator;

@RestController
@RequestMapping("/swtest")
public class HRBA_PR_Personal_Year_Status_Controller {
	@Autowired
	private CommonService commonService;
	
//	@GetMapping("/hrbaTest")
//	public ResponseEntity<JSONObject> sample_crud_01_F0(@CommonParam Map<String, Object> parameter) {
//		JSONArray sqlResult = null;
//		APIResponse response = new APIResponse();
//		try {
//			JSONObject normal = ParameterUtil.getNormal(parameter);
//			JSONArray body = ParameterUtil.getBody(parameter);
//			JSONObject common = ParameterUtil.getCommon(parameter);
//			
//			String sqlMap = "swtest.hrbaTest";
//			sqlResult = commonService.selectList(sqlMap, parameter);
//			response.setResponse(sqlResult);
//		} catch (Exception e) {
//			response.setResponseForError(e);
//		}
//		return response.getEntity();
//	}
	
	@GetMapping("/hrbaTest")
	public ResponseEntity<JSONObject> HRAA_BC_CEO_Select(@CommonParam Map<String, Object> parameter) {
		JSONArray sqlResult = new JSONArray();
		APIResponse response = new APIResponse();
		try {
			String sqlMap = "swtest.hrbaTest";
			sqlResult = commonService.selectList(sqlMap, parameter);
			System.out.println(sqlResult);
			response.setResponse(sqlResult);
		} catch (Exception e) {
			response.setResponseForError(e);
		}
		return response.getEntity();
	}	
	
	
	
}
