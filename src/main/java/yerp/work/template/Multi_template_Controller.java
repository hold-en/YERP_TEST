package yerp.work.template;

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

/**
 * <ul>
 * <li>Multi_template_Controller</li>
 * <li>설명 : 멀티 화면 컨트롤러</li>
 * <li>작성일 : 2022. 04. 22</li>
 * <li>작성자 : 정준석</li>
 * </ul>
 */
@RestController
@RequestMapping("/template")
public class Multi_template_Controller {
	@Autowired
	private CommonService commonService;
	
	@GetMapping("/multi_template_01_F0")
	public ResponseEntity<JSONObject> multi_template_01_F0(@CommonParam Map<String, Object> parameter) {
		JSONArray sqlResult = new JSONArray();
		APIResponse response = new APIResponse();
		try {
			String sqlMap01 = "template.multi_template_01_F0";
			String sqlMap02 = "template.multi_template_02_F0";
			String sqlMap05 = "template.multi_template_05_F0";
			String sqlMap09 = "template.multi_template_09_F0";
			JSONObject result = new JSONObject();
			result.put("dlt_01", commonService.selectList(sqlMap01, parameter));
			result.put("dlt_02", commonService.selectList(sqlMap02, parameter));
			result.put("dlt_05", commonService.selectList(sqlMap05, parameter));
			result.put("dlt_09", commonService.selectList(sqlMap09, parameter));
//			sqlResult = commonService.selectList(sqlMap, parameter);
//			sqlResult.add("{dlt_01 : " + commonService.selectList(sqlMap01, parameter) + "}");
//			sqlResult.add("{dlt_02 : " + commonService.selectList(sqlMap02, parameter) + "}");
//			sqlResult.add("{dlt_05 : " + commonService.selectList(sqlMap05, parameter) + "}");
//			sqlResult.add("{dlt_09 : " + commonService.selectList(sqlMap09, parameter) + "}");
			sqlResult.add(result);
			response.setResponse(sqlResult);
			/*
			JSONObject normal = ParameterUtil.getNormal(parameter);
            JSONArray param = (JSONArray) normal.get("param");
            JSONArray paramArray;
            JSONObject custom;
            for(Object outerArray : param) {
                paramArray = (JSONArray) outerArray;
                custom = new JSONObject();
                for(int i=1; i<=7; i++) {
                    String value = "";
                    try {
                        value = (String) paramArray.get(i-1);
                    } catch(IndexOutOfBoundsException iobe) {}

                    custom.put("param"+i, value);
                    ParameterUtil.addCustom(parameter, custom);
                }
                sqlResult.add(commonService.selectList(sqlMap, parameter));
            }

            response.setResponse(sqlResult);
			 * */
		} catch (Exception e) {
			response.setResponseForError(e);
		}
		return response.getEntity();
	}
	
	@PostMapping("/multi_template_01_T0")
	public ResponseEntity<JSONObject> grid_template_01_T0(@CommonParam @RequestBody Map<String, Object> parameter) {
		APIResponse response = new APIResponse();
		
		try {
			JSONArray body = ParameterUtil.getBody(parameter);
			
			Validator validator = new Validator();
			//validator.add(new Required(new String[]{"RoleID"}));
			
			JSONArray validResult = validator.run(body);
			if (validator.isPass()) {
				SQLMap sqlMap = new SQLMap();
				sqlMap.setNew("template.multi_template_01_I0");
				sqlMap.setModify("template.multi_template_01_U0");
				sqlMap.setRemove("template.multi_template_01_D0");
				
				response.setResponse(commonService.bodyProcess(parameter, sqlMap));
			} else {
				response.setResponseForValidation(validResult);
			}
		} catch (Exception e) {
			response.setResponseForError(e);
		}
		return response.getEntity();
	}
	
	@GetMapping("/multi_template_02_F0")
	public ResponseEntity<JSONObject> multi_template_02_F0(@CommonParam Map<String, Object> parameter) {
		JSONArray sqlResult = null;
		APIResponse response = new APIResponse();
		try {
			String sqlMap = "template.multi_template_02_F0";
			sqlResult = commonService.selectList(sqlMap, parameter);
			response.setResponse(sqlResult);
		} catch (Exception e) {
			response.setResponseForError(e);
		}
		return response.getEntity();
	}
	
	@PostMapping("/multi_template_02_T0")
	public ResponseEntity<JSONObject> multi_template_02_T0(@CommonParam @RequestBody Map<String, Object> parameter) {
		APIResponse response = new APIResponse();
		
		try {
			JSONArray body = ParameterUtil.getBody(parameter);
			
			Validator validator = new Validator();
			//validator.add(new Required(new String[]{"RoleID"}));
			
			JSONArray validResult = validator.run(body);
			if (validator.isPass()) {
				SQLMap sqlMap = new SQLMap();
				sqlMap.setNew("template.multi_template_02_I0");
				sqlMap.setModify("template.multi_template_02_U0");
				sqlMap.setRemove("template.multi_template_02_D0");
				
				response.setResponse(commonService.bodyProcess(parameter, sqlMap));
			} else {
				response.setResponseForValidation(validResult);
			}
		} catch (Exception e) {
			response.setResponseForError(e);
		}
		return response.getEntity();
	}
	
	@GetMapping("/multi_template_05_F0")
	public ResponseEntity<JSONObject> multi_template_05_F0(@CommonParam Map<String, Object> parameter) {
		JSONArray sqlResult = null;
		APIResponse response = new APIResponse();
		try {
			String sqlMap = "template.multi_template_05_F0";
			sqlResult = commonService.selectList(sqlMap, parameter);
			response.setResponse(sqlResult);
		} catch (Exception e) {
			response.setResponseForError(e);
		}
		return response.getEntity();
	}
	
	@GetMapping("/multi_template_09_F0")
	public ResponseEntity<JSONObject> multi_template_09_F0(@CommonParam Map<String, Object> parameter) {
		JSONArray sqlResult = null;
		APIResponse response = new APIResponse();
		try {
			String sqlMap = "template.multi_template_09_F0";
			sqlResult = commonService.selectList(sqlMap, parameter);
			response.setResponse(sqlResult);
		} catch (Exception e) {
			response.setResponseForError(e);
		}
		return response.getEntity();
	}
}
