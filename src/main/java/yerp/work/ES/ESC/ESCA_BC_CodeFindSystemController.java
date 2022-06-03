package yerp.work.ES.ESC;

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
@RequestMapping("/ESCA")
public class ESCA_BC_CodeFindSystemController {
	@Autowired
	private CommonService commonService;
	
	@GetMapping("/ESCA_BC_Codefind_System_Select")
	public ResponseEntity<JSONObject> CodeFindSystemSelect(@CommonParam Map<String, Object> parameter) {
		JSONArray sqlResult = null;
		APIResponse response = new APIResponse();
		try {
			JSONObject normal = ParameterUtil.getNormal(parameter);
			JSONArray body = ParameterUtil.getBody(parameter);
			JSONObject common = ParameterUtil.getCommon(parameter);
			
			String sqlMap = "ESCA.ESCA_SP_Codefind_System";
			sqlResult = commonService.selectList(sqlMap, parameter);
			response.setResponse(sqlResult);
		} catch (Exception e) {
			response.setResponseForError(e);
		}
		return response.getEntity();
	}
	
}