package yerp.work;

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
@RequestMapping("/sample")
public class Sample_crud_controller {
	@Autowired
	private CommonService commonService;
	
	@GetMapping("/sample_crud_01_F0")
	public ResponseEntity<JSONObject> sample_crud_01_F0(@CommonParam Map<String, Object> parameter) {
		JSONArray sqlResult = null;
		APIResponse response = new APIResponse();
		try {
//			JSONObject result = new JSONObject();
			
			String sqlMap = "sample.sample_crud_01_F0";
			sqlResult = commonService.selectList(sqlMap, parameter);
//			result = commonService.normalProcess(sqlMap, parameter);
			
//			System.out.println(result);
			
			response.setResponse(sqlResult);
		} catch (Exception e) {
			response.setResponseForError(e);
		}
		return response.getEntity();
	}
	
	@PostMapping("/sample_crud_01_T0")
	public ResponseEntity<JSONObject> sample_crud_01_T0(@CommonParam @RequestBody Map<String, Object> parameter) {
		APIResponse response = new APIResponse();
		
		try {
			JSONArray body = ParameterUtil.getBody(parameter);
			
			Validator validator = new Validator();
			//validator.add(new Required(new String[]{"RoleID"}));
			
			JSONArray validResult = validator.run(body);
			if (validator.isPass()) {
				SQLMap sqlMap = new SQLMap();
				sqlMap.setNew("sample.sample_crud_01_I0");
				sqlMap.setModify("sample.sample_crud_01_U0");
				sqlMap.setRemove("sample.sample_crud_01_D0");
				
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
