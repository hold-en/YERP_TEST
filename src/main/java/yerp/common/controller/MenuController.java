package yerp.common.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import yerp.common.annotation.CommonParam;
import yerp.common.service.CommonService;
import yerp.common.util.APIResponse;
import yerp.common.util.ConstantUtil;
import yerp.common.util.ParameterUtil;

@RestController
@RequestMapping("/menu")
public class MenuController {
	@Autowired
	private CommonService commonService;
	
	@RequestMapping("/getTopMenuList")
	public ResponseEntity<JSONObject> getTopMenuList(@CommonParam Map<String, Object> parameter, HttpSession session) {
		APIResponse response = new APIResponse();
//		JSONObject sqlResult = null;
		try {
			ParameterUtil.addCustom(parameter, "ROLE_ID", (String) session.getAttribute("R_ROLEID"));
			
//			System.out.println(parameter);
			JSONObject result = commonService.selectProcess("system.menu.getTopMenuList", parameter);
			List<Map> resultMap = (List) result.get(ConstantUtil.PROC_RESULT);
			
			JSONArray resultArray = ParameterUtil.convertJSONArray(String.valueOf(resultMap));
//			System.out.println(resultArray);
//			response.setResponse(commonService.selectList("system.menu.getTopMenuList", parameter));
			response.setResponse(resultArray);
		} catch (Exception e) {
			response.setResponseForError(e);
		}
		return response.getEntity();
	}
	
	@RequestMapping("/getLeftMenuList")
	public ResponseEntity<JSONObject> getLeftMenuList(@CommonParam Map<String, Object> parameter, HttpSession session) {
		APIResponse response = new APIResponse();
		try {
//			ParameterUtil.addCustom(parameter, "SOSOG_CD", (String) session.getAttribute("R_SOSOGCD"));
			
//			JSONObject result = commonService.selectProcess("system.menu.getLeftMenuList", parameter);
//			List<Map> resultMap = (List) result.get(ConstantUtil.PROC_RESULT);
//			JSONArray resultArray = ParameterUtil.convertJSONArray(String.valueOf(resultMap));
//			System.out.println(resultMap);
			
			response.setResponse(commonService.selectList("system.menu.getLeftMenuList", parameter));
//			response.setResponse(resultMap);
		} catch (Exception e) {
			response.setResponseForError(e);
		}
		return response.getEntity();
	}
	
	@RequestMapping("/getMyMenuList")
	public ResponseEntity<JSONObject> getMyMenuList(@CommonParam Map<String, Object> parameter, HttpSession session) {
		APIResponse response = new APIResponse();
		try {
			response.setResponse(commonService.selectList("system.menu.getMyMenuList", parameter));
		} catch (Exception e) {
			response.setResponseForError(e);
		}
		return response.getEntity();
	}
	
	@RequestMapping("/getLeftMenuList2")
	public ResponseEntity<JSONObject> getLeftMenuList2(@CommonParam Map<String, Object> parameter, HttpSession session) {
		APIResponse response = new APIResponse();
		try {
			response.setResponse(commonService.selectList("system.menu.getLeftMenuList2", parameter));
		} catch (Exception e) {
			response.setResponseForError(e);
		}
		return response.getEntity();
	}
	
	@RequestMapping("/getMenuPermission")
	public ResponseEntity<JSONObject> getMenuPermission(@CommonParam Map<String, Object> parameter, HttpSession session) {
		APIResponse response = new APIResponse();
		try {
			JSONObject result = commonService.selectProcess("system.menu.getMenuPermission", parameter);
			List<Map> resultMap = (List) result.get(ConstantUtil.PROC_RESULT);
			System.out.println(resultMap);
//			response.setResponse(commonService.selectList("system.menu.getMenuPermission", parameter));
			response.setResponse(resultMap);
		} catch (Exception e) {
			response.setResponseForError(e);
		}
		return response.getEntity();
	}	
	@RequestMapping("/getMenuPermission2")
	public ResponseEntity<JSONObject> getMenuPermission2(@CommonParam Map<String, Object> parameter, HttpSession session) {
		APIResponse response = new APIResponse();
		try {
			JSONObject result = commonService.selectProcess("system.menu.getMenuPermission2", parameter);
			List<Map> resultMap = (List) result.get(ConstantUtil.PROC_RESULT);
			System.out.println(resultMap);
//			response.setResponse(commonService.selectList("system.menu.getMenuPermission2", parameter));
			response.setResponse(resultMap);
		} catch (Exception e) {
			response.setResponseForError(e);
		}
		return response.getEntity();
	}
}
