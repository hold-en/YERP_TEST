package yerp.work.GE.GEB;

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
public class HealthApplyManageTest {
	@Autowired
	private CommonService commonService;
	
	@GetMapping("/gebd_HealthMaster_F0")
	public ResponseEntity<JSONObject> sample_crud_01_F0(@CommonParam Map<String, Object> parameter) {
		JSONArray sqlResult = null;
		APIResponse response = new APIResponse();
		try {
			JSONObject normal = ParameterUtil.getNormal(parameter);
			JSONArray body = ParameterUtil.getBody(parameter);
			JSONObject common = ParameterUtil.getCommon(parameter);
			
			String sqlMap = "swtest.gebd_HealthMaster_F0";
			sqlResult = commonService.selectList(sqlMap, parameter);
			response.setResponse(sqlResult);
		} catch (Exception e) {
			response.setResponseForError(e);
		}
		return response.getEntity();
	}
	
	@PostMapping("/gebd_HealthMaster_T0")
	public ResponseEntity<JSONObject> sample_crud_01_T0(@CommonParam @RequestBody Map<String, Object> parameter) {
		System.out.println(parameter);
		System.out.println("*******************************\n" + parameter.get("body"));
		
		APIResponse response = new APIResponse();
		
		try {
			JSONArray body = ParameterUtil.getBody(parameter);
			
			Validator validator = new Validator();
			//validator.add(new Required(new String[]{"RoleID"}));
			System.out.println("testbody : " + body);
			JSONArray validResult = validator.run(body);
			if (validator.isPass()) {
				SQLMap sqlMap = new SQLMap();				
				sqlMap.setNew("swtest.gebd_HealthMaster_I0");
				sqlMap.setModify("swtest.gebd_HealthMaster_U0");
				sqlMap.setRemove("swtest.gebd_HealthMaster_D0");
				
				response.setResponse(commonService.bodyProcess(parameter, sqlMap));
			} else {
				response.setResponseForValidation(validResult);
			}
		} catch (Exception e) {
			response.setResponseForError(e);
		}
		return response.getEntity();
	}
}
