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
public class ESZA_BC_SawonListController {
	@Autowired
	private CommonService commonService;
	
	@GetMapping("/ESZA_BC_SawonList_Select")
	public ResponseEntity<JSONObject> ESZA_BC_SawonList_Select(@CommonParam Map<String, Object> parameter) {
		JSONArray sqlResult = new JSONArray();
		APIResponse response = new APIResponse();
		try {
			String sqlMap = "";
			
			sqlMap = "ES.ESZ.ESZA_MP_SawonList_Select";
			sqlResult.add(commonService.selectList(sqlMap, parameter));
			
			response.setResponse(sqlResult);
		} catch (Exception e) {
			response.setResponseForError(e);
		}
		return response.getEntity();
	}
}
