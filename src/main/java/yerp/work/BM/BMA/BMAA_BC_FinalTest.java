package yerp.work.BM.BMA;

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

import com.beust.jcommander.internal.Console;

import yerp.common.annotation.CommonParam;
import yerp.common.service.CommonService;
import yerp.common.util.APIResponse;
import yerp.common.util.ParameterUtil;
import yerp.common.util.SQLMap;
import yerp.common.valid.Validator;

@RestController
@RequestMapping("/BMA")
public class BMAA_BC_FinalTest {
	@Autowired
	private CommonService commonService;
	
	@GetMapping("/BMAA_BC_FinalTest_Select")
	
	public ResponseEntity<JSONObject> BMAA_BC_FinalTest_Select(@CommonParam Map<String, Object> parameter) {
		JSONArray sqlResult = new JSONArray();
		APIResponse response = new APIResponse();
		try {
			String sqlMap = "";
			
			// SP한개에서 여러개 테이블 반환
//			sqlMap = "BM.BMA.BMAA_MP_FinalTest_x3_Select";
//			sqlResult = commonService.selectList(sqlMap, parameter);
			
			// SP하나에 테이블 하나씩..
			sqlMap = "BM.BMA.BMAA_MP_FinalTest1_Select";
			sqlResult.add(commonService.selectList(sqlMap, parameter));
			
			sqlMap = "BM.BMA.BMAA_MP_FinalTest2_Select";
			sqlResult.add(commonService.selectList(sqlMap, parameter));
			
			sqlMap = "BM.BMA.BMAA_MP_FinalTest3_Select";  
			sqlResult.add(commonService.selectList(sqlMap, parameter));

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
