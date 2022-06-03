package yerp.common.util;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import websquare.logging.Logger;

public class APIResponse {
	private JSONObject responseForJSON;
	private HttpStatus status;
	
	public APIResponse() {
	    responseForJSON = new JSONObject();
	    status = HttpStatus.OK;
	}
	
	public APIResponse(JSONObject jsonObject) {
	    responseForJSON = jsonObject;
	    status = HttpStatus.OK;
	}
	
	public void add(String key, Object value) {
	    responseForJSON.put(key, value);
	}
	
	public JSONObject getResponseForJSON() {
	    return responseForJSON;
	}
	
	public void setResponseForJSON(JSONObject responseForJSON) {
	    this.responseForJSON = responseForJSON;
	}
	
	public HttpStatus getStatus() {
	    return status;
	}
	
	public void setStatus(HttpStatus status) {
	    this.status = status;
	}
	
	public ResponseEntity<JSONObject> getEntity() {
	    return new ResponseEntity(this.getResponseForJSON(), this.getStatus());
	}
	
	public void setResponseForValidation(JSONArray validResult) {
	    if(validResult.size() > 0) {
	        add(ConstantUtil.ERROR, ConstantUtil.INVALID);
	        add(ConstantUtil.INVALID, validResult);
	        setStatus(HttpStatus.FORBIDDEN);
	    }
	}
	
	public void setResponseForError(Exception exception) {
	    exception.printStackTrace();
		/*수정 보안사항 자세한 오류메세지 처리
		* 수정일시 : 2020-01-30
		* 수정자 : 정병우
		*/
	    String exceptionInfo = "java.sql.SQLException:";
	    String exceptionMsg = exception.getMessage();
	    String alertMsg = exceptionMsg.substring(exceptionMsg.indexOf(exceptionInfo)+exceptionInfo.length(), exceptionMsg.indexOf("###", exceptionMsg.indexOf(":")));
	
	    
	    if(exception.getMessage().contains("PRIMARY")) {
	        add(ConstantUtil.ERROR, "중복되는 데이터가 존재합니다.");
	    } else {
	        //AS-IS 공통에서 만들어줌
	    	//add(ConstantUtil.ERROR, "에러가 발생하였습니다.");
	    	add(ConstantUtil.ERROR, alertMsg);
	    	//add(ConstantUtil.ERROR, exceptionMsg);
	    	
	    }
	
	    setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	public void setResponseForError(Exception exception, String message) {
	    exception.printStackTrace();
	    add(ConstantUtil.ERROR, message);
	    setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	public void setResponseForError(Exception exception, JSONObject object) {
	    exception.printStackTrace();
	    add(ConstantUtil.ERROR, object);
	    setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	public void setResponse(Object result) {
	    add(ConstantUtil.OUT, result);
	}
	
	public void setResponse(String key, Object result) {
	    add(key, result);
	}
}
