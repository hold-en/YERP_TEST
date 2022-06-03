package yerp.common.controller;

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

@RestController
@RequestMapping("/component")
public class ComponentController {
	@Autowired
	private CommonService commonService;
		//수정
	@RequestMapping("/getJangHakCd")
	public ResponseEntity<JSONObject> getJangHakCd(@CommonParam Map<String, Object> parameter) {
		JSONArray sqlResult = new JSONArray();
		APIResponse response = new APIResponse();
		try {
			response.setResponse(commonService.selectList("system.component.getJangHakCd", parameter));
		} catch (Exception e) {
			response.setResponseForError(e);
		}
		return response.getEntity();
	}
	
	@RequestMapping("/getJangHakCdCorez19_1")
	public ResponseEntity<JSONObject> getJangHakCdCorez19_1(@CommonParam Map<String, Object> parameter) {
		//CORZ19 도있음 필요할때 생성
		JSONArray sqlResult = new JSONArray();
		APIResponse response = new APIResponse();
		try {
			response.setResponse(commonService.selectList("system.component.getJangHakCdCorez19_1", parameter));
		} catch (Exception e) {
			response.setResponseForError(e);
		}
		return response.getEntity();
	}
	
	@GetMapping("/getPsDeptCode") 
	public ResponseEntity<JSONObject> getPsDeptCode(@CommonParam Map<String, Object> parameter) {
		APIResponse response = new APIResponse();
		try {
			response.setResponse(commonService.selectList("system.component.getPsDeptCode", parameter));
		} catch (Exception e) {
			response.setResponseForError(e);
		}
		return response.getEntity();
	}//평생 학과 찾기
	
	
	@RequestMapping("/getJHJaeDan")
	public ResponseEntity<JSONObject> getJHJaeDan(@CommonParam Map<String, Object> parameter) {
		JSONArray sqlResult = new JSONArray();
		APIResponse response = new APIResponse();
		try {
			response.setResponse(commonService.selectList("system.component.getJHJaeDan", parameter));
		} catch (Exception e) {
			response.setResponseForError(e);
		}
		return response.getEntity();
	}
	
	@RequestMapping("/getCompany")
	public ResponseEntity<JSONObject> getCompany(@CommonParam Map<String, Object> parameter) {
		JSONArray sqlResult = new JSONArray();
		APIResponse response = new APIResponse();
		try {
			response.setResponse(commonService.selectList("system.component.getCompany", parameter));
		} catch (Exception e) {
			response.setResponseForError(e);
		}
		return response.getEntity();
	}
	
	@GetMapping("/getPsStudents") 
	public ResponseEntity<JSONObject> getPsStudents(@CommonParam Map<String, Object> parameter) {
		APIResponse response = new APIResponse();
		try {
			response.setResponse(commonService.selectList("system.component.getPsStudents", parameter));
		} catch (Exception e) {
			response.setResponseForError(e);
		}
		return response.getEntity();
	}
	
	@GetMapping("/getPsHjStudents") 
	public ResponseEntity<JSONObject> getPsHjStudents(@CommonParam Map<String, Object> parameter) {
		APIResponse response = new APIResponse();
		try {
			response.setResponse(commonService.selectList("system.component.getPsHjStudents", parameter));
		} catch (Exception e) {
			response.setResponseForError(e);
		}
		return response.getEntity();
	}
	

	@GetMapping("/getPsStudentsForArrow") 
	public ResponseEntity<JSONObject> getPsStudentsForArrow(@CommonParam Map<String, Object> parameter) {
		APIResponse response = new APIResponse();
		try {
			response.setResponse(commonService.selectList("system.component.getPsStudentsForArrow", parameter));
		} catch (Exception e) {
			response.setResponseForError(e);
		}
		return response.getEntity();
	}
	
	@GetMapping("/getStudentsInfo2") 
	public ResponseEntity<JSONObject> getStudentsInfo2(@CommonParam Map<String, Object> parameter) {
		APIResponse response = new APIResponse();
		try {
			response.setResponse(commonService.selectList("system.component.getStudentsInfo2", parameter));
		} catch (Exception e) {
			response.setResponseForError(e);
		}
		return response.getEntity();
	}
	
	@GetMapping("/getPsSdStudentsInfo") 
	public ResponseEntity<JSONObject> getPsSdStudentsInfo(@CommonParam Map<String, Object> parameter) {
		APIResponse response = new APIResponse();
		try {
			response.setResponse(commonService.selectList("system.component.getPsSdStudentsInfo", parameter));
		} catch (Exception e) {
			response.setResponseForError(e);
		}
		return response.getEntity();
	} // 평생 인포 최종학력포함
	@GetMapping("/getPsSdExtraInfo") 
	public ResponseEntity<JSONObject> getPsSdExtraInfo(@CommonParam Map<String, Object> parameter) {
		APIResponse response = new APIResponse();
		try {
			response.setResponse(commonService.selectList("system.component.getPsSdExtraInfo", parameter));
		} catch (Exception e) {
			response.setResponseForError(e);
		}
		return response.getEntity();
	}
	@GetMapping("/getExtraInfo") 
	public ResponseEntity<JSONObject> getExtraInfo(@CommonParam Map<String, Object> parameter) {
		APIResponse response = new APIResponse();
		try {
			response.setResponse(commonService.selectList("system.component.getExtraInfo", parameter));
		} catch (Exception e) {
			response.setResponseForError(e);
		}
		return response.getEntity();
	}
	
	@GetMapping("/getJiStudents") 
	public ResponseEntity<JSONObject> getJiStudents(@CommonParam Map<String, Object> parameter) {
		APIResponse response = new APIResponse();
		try {
			response.setResponse(commonService.selectList("system.component.getJiStudents", parameter));
		} catch (Exception e) {
			response.setResponseForError(e);
		}
		return response.getEntity();
	}
	
	@GetMapping("/getMulpoom") 
	public ResponseEntity<JSONObject> getMulpoom(@CommonParam Map<String, Object> parameter) {
		APIResponse response = new APIResponse();
		try {
			response.setResponse(commonService.selectList("system.component.getMulpoom", parameter));
		} catch (Exception e) {
			response.setResponseForError(e);
		}
		return response.getEntity();
	}
	
	@GetMapping("/getDcGwamog") 
	public ResponseEntity<JSONObject> getDcGwamog(@CommonParam Map<String, Object> parameter) {
		APIResponse response = new APIResponse();
		try {
			response.setResponse(commonService.selectList("system.component.getDcGwamog", parameter));
		} catch (Exception e) {
			response.setResponseForError(e);
		}
		return response.getEntity();
	}
	
		//====================
	
	@RequestMapping("/getYearHakgi")
	public ResponseEntity<JSONObject> getYearHakgi(@CommonParam Map<String, Object> parameter) {
		JSONArray sqlResult = new JSONArray();
		APIResponse response = new APIResponse();
		try {
			response.setResponse(commonService.selectList("system.component.getYearHakgi", parameter));
		} catch (Exception e) {
			response.setResponseForError(e);
		}
		return response.getEntity();
	}

	@GetMapping("/getDeptCode") 
	public ResponseEntity<JSONObject> getDeptCode(@CommonParam Map<String, Object> parameter) {
		APIResponse response = new APIResponse();
		try {
			response.setResponse(commonService.selectList("system.component.getDeptCode", parameter));
		} catch (Exception e) {
			response.setResponseForError(e);
		}
		return response.getEntity();
	}
	
	@GetMapping("/getStudents") 
	public ResponseEntity<JSONObject> getStudents(@CommonParam Map<String, Object> parameter) {
		APIResponse response = new APIResponse();
		try {
			response.setResponse(commonService.selectList("system.component.getStudents", parameter));
		} catch (Exception e) {
			response.setResponseForError(e);
		}
		return response.getEntity();
	}
	
	@GetMapping("/getStudents3") 
	public ResponseEntity<JSONObject> getStudents3(@CommonParam Map<String, Object> parameter) {
		APIResponse response = new APIResponse();
		try {
			response.setResponse(commonService.selectList("system.component.getStudents3", parameter));
		} catch (Exception e) {
			response.setResponseForError(e);
		}
		return response.getEntity();
	}
	
	@GetMapping("/getPsSdStudents") 
	public ResponseEntity<JSONObject> getPsSdStudents(@CommonParam Map<String, Object> parameter) {
		APIResponse response = new APIResponse();
		try {
			response.setResponse(commonService.selectList("system.component.getPsSdStudents", parameter));
		} catch (Exception e) {
			response.setResponseForError(e);
		}
		return response.getEntity();
	}//평생상담
	
	
	@GetMapping("/getDeptPsSdCode") 
	public ResponseEntity<JSONObject> getDeptPsSdCode(@CommonParam Map<String, Object> parameter) {
		APIResponse response = new APIResponse();
		try {
			response.setResponse(commonService.selectList("system.component.getDeptPsSdCode", parameter));
		} catch (Exception e) {
			response.setResponseForError(e);
		}
		return response.getEntity();
	}//상담 학과
	
	@GetMapping("/getSdStudents") 
	public ResponseEntity<JSONObject> getSdStudents(@CommonParam Map<String, Object> parameter) {
		APIResponse response = new APIResponse();
		try {
			response.setResponse(commonService.selectList("system.component.getSdStudents", parameter));
		} catch (Exception e) {
			response.setResponseForError(e);
		}
		return response.getEntity();
	}//상담
	@GetMapping("/getDeptSdCode") 
	public ResponseEntity<JSONObject> getDeptSdCode(@CommonParam Map<String, Object> parameter) {
		APIResponse response = new APIResponse();
		try {
			response.setResponse(commonService.selectList("system.component.getDeptSdCode", parameter));
		} catch (Exception e) {
			response.setResponseForError(e);
		}
		return response.getEntity();
	}//상담 학과	
	@GetMapping("/getStudentsForArrow") 
	public ResponseEntity<JSONObject> getStudentsForArrow(@CommonParam Map<String, Object> parameter) {
		APIResponse response = new APIResponse();
		try {
			response.setResponse(commonService.selectList("system.component.getStudentsForArrow", parameter));
		} catch (Exception e) {
			response.setResponseForError(e);
		}
		return response.getEntity();
	}
	
	@GetMapping("/getClassRoom") 
	public ResponseEntity<JSONObject> getClassRoom(@CommonParam Map<String, Object> parameter) {
		APIResponse response = new APIResponse();
		try {
			response.setResponse(commonService.selectList("system.component.getClassRoom", parameter));
		} catch (Exception e) {
			response.setResponseForError(e);
		}
		return response.getEntity();
	}	
	
	@GetMapping("/getSearchSugang") 
	public ResponseEntity<JSONObject> getSearchSugang(@CommonParam Map<String, Object> parameter) {
		APIResponse response = new APIResponse();
		try {
			response.setResponse(commonService.selectList("system.component.getSearchSugang", parameter));
		} catch (Exception e) {
			response.setResponseForError(e);
		}
		return response.getEntity();
	}	
	
	@GetMapping("/getDongAri") 
	public ResponseEntity<JSONObject> getDongAri(@CommonParam Map<String, Object> parameter) {
		APIResponse response = new APIResponse();
		try {
			response.setResponse(commonService.selectList("system.component.getDongAri", parameter));
		} catch (Exception e) {
			response.setResponseForError(e);
		}
		return response.getEntity();
	}		
	
	@GetMapping("/getGroupPopup") 
	public ResponseEntity<JSONObject> getGroupPopup(@CommonParam Map<String, Object> parameter) {
		APIResponse response = new APIResponse();
		try {
			response.setResponse(commonService.selectList("system.component.getGroupPopup", parameter));
		} catch (Exception e) {
			response.setResponseForError(e);
		}
		return response.getEntity();
	}		

	@GetMapping("/getSearchPsGwamog") 
	public ResponseEntity<JSONObject> getSearchPsGwamog(@CommonParam Map<String, Object> parameter) {
		APIResponse response = new APIResponse();
		try {
			response.setResponse(commonService.selectList("system.component.getSearchPsGwamog", parameter));
		} catch (Exception e) {
			response.setResponseForError(e);
		}
		return response.getEntity();
	}	
	
	@GetMapping("/getHoewonCd") 
	public ResponseEntity<JSONObject> getHoewonCd(@CommonParam Map<String, Object> parameter) {
		APIResponse response = new APIResponse();
		try {
			response.setResponse(commonService.selectList("system.component.getHoewonCd", parameter));
		} catch (Exception e) {
			response.setResponseForError(e);
		}
		return response.getEntity();
	}	
	
}
