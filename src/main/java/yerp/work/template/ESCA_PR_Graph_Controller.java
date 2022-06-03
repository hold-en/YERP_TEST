package yerp.work.template;

import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import yerp.common.annotation.CommonParam;
import yerp.common.service.CommonService;
import yerp.common.util.APIResponse;

/**
 * <ul>
 * <li>ESCA_PR_Graph_Controller</li>
 * <li>설명 : 그래프 화면 컨트롤러</li>
 * <li>작성일 : 2022. 04. 28</li>
 * <li>작성자 : 정준석</li>
 * </ul>
 */
@RestController
@RequestMapping("/template")
public class ESCA_PR_Graph_Controller {
	@Autowired
	private CommonService commonService;
	
	@GetMapping("/ESCA_PR_Graph_01_F0")
	public ResponseEntity<JSONObject> ESCA_PR_Graph_01_F0(@CommonParam Map<String, Object> parameter) {
		JSONArray sqlResult = null;
		APIResponse response = new APIResponse();
		try {
			String sqlMap = "template.ESCA_PR_Graph_01_F0";
			sqlResult = commonService.selectList(sqlMap, parameter);
			response.setResponse(sqlResult);
		} catch (Exception e) {
			response.setResponseForError(e);
		}
		return response.getEntity();
	}
}
