package yerp.common.controller;



import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import yerp.common.util.APIResponse;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

	@ExceptionHandler(value = MaxUploadSizeExceededException.class)
    public ResponseEntity<JSONObject> maxUploadSizeExceededException(Exception e) {
        APIResponse response = new APIResponse();
        response.setResponseForError(e, "파일용량이 너무 큽니다.");
        return response.getEntity();
    }   

}
