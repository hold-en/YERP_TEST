package yerp.work.template;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import yerp.common.annotation.CommonParam;
import yerp.common.controller.FileController;
import yerp.common.service.CommonService;
import yerp.common.util.APIResponse;
import yerp.common.util.FileUtil;
import yerp.common.util.ParameterUtil;
import yerp.common.util.SQLMap;
import yerp.common.valid.Validator;

/**
 * <ul>
 * <li>ESCA_PR_MultiFileUpload_Controller</li>
 * <li>설명 : 멀티 파일업로드 컨트롤러</li>
 * <li>작성일 : 2022. 05. 12</li>
 * <li>작성자 : 정준석</li>
 * </ul>
 */
@RestController
@RequestMapping("/template")
public class ESCA_PR_MultiFileUpload_Controller {
	@Autowired
	private CommonService commonService;
	@Autowired
	private PlatformTransactionManager transactionManager;
	
	@GetMapping("/ESCA_PR_MultiUpdate_01_F0")
	public ResponseEntity<JSONObject> ESCA_PR_MultiUpdate_01_F0(@CommonParam Map<String, Object> parameter) {
		JSONArray sqlResult = null;
		APIResponse response = new APIResponse();
		try {
			String sqlMap = "template.ESCA_PR_MultiUpdate_01_F0";
			sqlResult = commonService.selectList(sqlMap, parameter);
			response.setResponse(sqlResult);
		} catch (Exception e) {
			response.setResponseForError(e);
		}
		return response.getEntity();
	}
	
	/** 첨부파일을 저장한다.
	 * @param param 클라이언트에서 전달한 데이터 맵 객체
	 * @return
	 * @throws Exception */
	@RequestMapping("/ESCA_PR_MultiFileUpload_Upload")
	public void ESCA_PR_MultiFileUpload_Upload(MultipartHttpServletRequest multiReq, HttpServletResponse res) throws Exception {
		JSONArray result = multiUpload(multiReq);
		JSONObject jsonObj = new JSONObject();
		if(!result.isEmpty()) {
			jsonObj = (JSONObject) result.get(0);
		}
		String fileCode = (String) (jsonObj.get("FILE_CODE") == null ? "" : jsonObj.get("FILE_CODE"));
		String key = fileCode;
		
		StringBuilder rtnStr = new StringBuilder();
		rtnStr.append("<script>");
		rtnStr.append("parent.multiUploadCallBack(\"<ret>");
		rtnStr.append("<key>" + key + "</key>"); // uploadPath
		rtnStr.append("<storedFileList>" + (String) (jsonObj.get("REAL_NAME") == null ? "" : jsonObj.get("REAL_NAME"))
		        + "</storedFileList>"); // saveFileName
		rtnStr.append("<localfileList><![CDATA["
		        + (String) (jsonObj.get("DISPLAY_NAME") == null ? "" : jsonObj.get("DISPLAY_NAME"))
		        + "]]></localfileList>"); // OriginalfileName
		rtnStr.append("<fileSizeList>"
		        + (jsonObj.get("FILE_SIZE") == null ? "" : Long.valueOf((long) jsonObj.get("FILE_SIZE")).toString())
		        + "</fileSizeList>"); // uploadFileSize
		rtnStr.append("<maxUploadSize></maxUploadSize>");
		rtnStr.append("<deniedList></deniedList>");
		rtnStr.append("<deniedCodeList></deniedCodeList>");
		rtnStr.append("</ret>\");");
		rtnStr.append("</script>");
		
		HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper(res);
		wrapper.setContentType("text/html;charset=UTF-8");
		
		res.getWriter().print(rtnStr.toString());
	}
	
	/** 첨부파일 정보를 JSONArray로 return한다. */
	public JSONArray multiUpload(MultipartHttpServletRequest req) throws Exception {
		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
//		JSONParser parser = new JSONParser();
		JSONArray resultArray = new JSONArray();
		JSONObject resultObjForHtml = null;
		File resultFile = null;
		
		String fileAcceptExt = req.getParameter("ACCEPT_EXT") == null ? "" : req.getParameter("ACCEPT_EXT");
		String path = req.getParameter("dir") == null ? "" : req.getParameter("dir");
		String fileJsonStr = req.getParameter("fileJson") == null ? "" : req.getParameter("fileJson");
		System.out.println(fileJsonStr);
		System.out.println(req.getParameter("fileJson"));
		JSONArray fileJson = new JSONArray();
		if(fileJsonStr != null) {
//			fileJson = (JSONArray) parser.parse(fileJsonStr);
			fileJson = ParameterUtil.convertJSONArray(fileJsonStr);
		}
		
		Map<String, Object> parameter = new HashMap<String, Object>();
		JSONObject customParamter = new JSONObject();
		try {
			Iterator<String> fileNames = req.getFileNames();
			String fileName = "";
			Integer idx = 0;
			while(fileNames.hasNext()) {
				fileName = fileNames.next();
				List<MultipartFile> files = req.getFiles(fileName);
				JSONObject fileElement = (JSONObject) fileJson.get(idx);
				customParamter.put("DS_Title", fileElement.get("DS_Title"));
				customParamter.put("DT_Rent", fileElement.get("DT_Rent"));
				for(MultipartFile file: files) {
					if(file != null) {
						String originalFileName = new String(file.getOriginalFilename().getBytes("UTF-8"));
						String uploadRoot = FileUtil.UPLOAD_ROOT;
						System.out.println(originalFileName);
						// 파일 확장자 점검
						if(!FileController.isAcceptFileName(fileAcceptExt, originalFileName)) {
							continue;
						}
						
						resultObjForHtml = new JSONObject();
						
						// FILE_CODE 구하기
						String fileCode;
						
						customParamter.put("DS_Filename", originalFileName);
						customParamter.put("AM_Size", file.getSize());
						
						System.out.println(customParamter);
						ParameterUtil.addCustom(parameter, customParamter);
						System.out.println(parameter);
						
						JSONArray rtn = commonService.selectList("template.ESCA_PR_MultiUpdate_01_I0", parameter);
						JSONObject element = (JSONObject) rtn.get(0);
						fileCode = element.get("SN_File").toString();

						String saveFileName = (String) element.get("DS_Filename");
						resultFile = FileController.fileUpload(uploadRoot + path, file, saveFileName);
						String realName = resultFile.getName();

						resultObjForHtml.put("REAL_NAME", realName);
						resultObjForHtml.put("REAL_PATH", path);
						resultObjForHtml.put("DISPLAY_NAME", originalFileName);
						resultObjForHtml.put("FILE_SIZE", file.getSize());
						resultObjForHtml.put("FILE_TYPE", file.getContentType());
						resultObjForHtml.put("FILE_CODE", fileCode);

						resultArray.add(resultObjForHtml);
					}
				}
			}
			transactionManager.commit(status);
		} catch(MultipartException e) {
			e.printStackTrace();
			if(resultFile != null) {
				if(resultFile.exists() && resultFile.isFile()) {
					resultFile.delete();
				}
			}
			transactionManager.rollback(status);
		} catch(Exception e) {
			e.printStackTrace();
			// System.err.println("에러발생! 잠시후 다시 접속해 주세요!");
			if(resultFile != null) {
				if(resultFile.exists() && resultFile.isFile()) {
					resultFile.delete();
				}
			}
			transactionManager.rollback(status);
		}
		return resultArray;
	}
	
	@PostMapping("/ESCA_PR_MultiUpdate_01_D0")
	public ResponseEntity<JSONObject> ESCA_PR_MultiUpdate_01_D0(@CommonParam @RequestBody Map<String, Object> parameter) {
		APIResponse response = new APIResponse();
		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		
		try {
			JSONObject normal = ParameterUtil.getNormal(parameter);
			String fileName = normal.get("DS_Filename") == null ? "" : normal.get("DS_Filename").toString();
			String path = normal.get("dir") == null ? "" : normal.get("dir").toString();
			String uploadRoot = FileUtil.UPLOAD_ROOT;
			
			String sqlMap = "template.ESCA_PR_MultiUpdate_01_D0";
			
			response.setResponse(commonService.normalProcess(sqlMap, parameter));
			
			FileController.delete(uploadRoot + path, fileName);
			
			transactionManager.commit(status);
		} catch (Exception e) {
			response.setResponseForError(e);
			transactionManager.rollback(status);
		}
		return response.getEntity();
	}
	
	@RequestMapping("/ESCA_PR_MultiUpdate_Download")
	public void ESCA_PR_MultiUpdate_Download(HttpServletResponse response, String dir, String name) {
		dir = dir == null ? "" : dir;
		dir = dir.replace("../", "").replace("./", "");
		name = name == null ? "" : name;
		name = name.replace("../", "").replace("./", "");
		String uploadRoot = FileUtil.UPLOAD_ROOT;
		
		File file = FileController.searchOne(uploadRoot + dir, name);
		try {
			if (file != null && file.exists() && file.isFile()) {
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Content-Disposition",
						"attachment; filename=" + new String(file.getName().getBytes("UTF-8"), "ISO-8859-1"));
				
				FileCopyUtils.copy(new FileInputStream(file), response.getOutputStream());
				response.flushBuffer();
			} else {
//				response.sendRedirect("/");
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/html; charset=UTF-8");
				response.getWriter().write("<script>alert('파일이 존재하지 않습니다')</script>");
				response.getWriter().close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/ESCA_PR_MultiUpdate_PdfView")
	public void ESCA_PR_MultiUpdate_PdfView(HttpServletResponse response, String dir, String name) {
		dir = dir == null ? "" : dir;
		dir = dir.replace("../", "").replace("./", "");
		name = name == null ? "" : name;
		name = name.replace("../", "").replace("./", "");
		String uploadRoot = FileUtil.UPLOAD_ROOT;
		
		File file = FileController.searchOne(uploadRoot + dir, name);
		try {
			if (file != null && file.exists() && file.isFile()) {
				response.setCharacterEncoding("UTF-8");
				//클라이언트 브라우져에서 바로 보는 방법(헤더 변경)
				response.setContentType("application/pdf");
//				response.setHeader("Content-Disposition",
//						"attachment; filename=" + new String(file.getName().getBytes("UTF-8"), "ISO-8859-1"));
				
				FileCopyUtils.copy(new FileInputStream(file), response.getOutputStream());
				response.flushBuffer();
			} else {
//				response.sendRedirect("/");
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/html; charset=UTF-8");
				response.getWriter().write("<script>alert('파일이 존재하지 않습니다')</script>");
				response.getWriter().close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
