package yerp.work.ES.ESZ;

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
@RequestMapping("/ESZ")
public class ESZA_BC_CodeFind_AccountCodeController {
	@Autowired
	private CommonService commonService;
	
	@GetMapping("/ESZA_BC_CodeFind_AccountCode")
	public ResponseEntity<JSONObject> ESZA_BC_CodeFind_AccountCode(@CommonParam Map<String, Object> parameter) {
		JSONArray sqlResult = new JSONArray();
		APIResponse response = new APIResponse();
		try {
			String sqlMap = "";
			
			if(parameter.get("parCD_Acnt") != null) {
				System.out.println("=====================\n"+parameter.get("parCD_Acnt"));
			}
			
			sqlMap = "ES.ESZ.ESZA_MP_CodeFind_AccountCode";
			sqlResult.add(commonService.selectList(sqlMap, parameter));
			
			response.setResponse(sqlResult);
		} catch (Exception e) {
			response.setResponseForError(e);
		}
		return response.getEntity();
	}
}
