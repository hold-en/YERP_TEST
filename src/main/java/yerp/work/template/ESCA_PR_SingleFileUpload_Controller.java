package yerp.work.template;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import yerp.common.service.CommonService;
import yerp.common.util.FileOption;
import yerp.common.util.FileUtil;
import yerp.common.util.FileUtil2;
import yerp.common.util.ParameterUtil;

/**
 * <ul>
 * <li>ESCA_PR_SingleFileUpload_Controller</li>
 * <li>설명 : 싱글 파일업로드 컨트롤러</li>
 * <li>작성일 : 2022. 05. 10</li>
 * <li>작성자 : 정준석</li>
 * </ul>
 */
@RestController
@RequestMapping("/template")
public class ESCA_PR_SingleFileUpload_Controller {
	@Autowired
	private CommonService commonService;
	@Autowired
	private PlatformTransactionManager transactionManager;
	
	/** 첨부파일을 저장한다.
	 * @param param 클라이언트에서 전달한 데이터 맵 객체
	 * @return
	 * @throws Exception */
	@RequestMapping("/ESCA_PR_SingleFileUpload_Upload")
	public void ESCA_PR_SingleFileUpload_Upload(MultipartHttpServletRequest multiReq, HttpServletResponse res) throws Exception {
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
		JSONArray resultArray = new JSONArray();
		JSONObject resultObjForHtml = null;
		File resultFile = null;
		
		String fileAcceptExt = req.getParameter("ACCEPT_EXT") == null ? "" : req.getParameter("ACCEPT_EXT");
		String path = req.getParameter("dir") == null ? "" : req.getParameter("dir");
		String DS_Title = req.getParameter("DS_Title") == null ? "" : req.getParameter("DS_Title");
		String DT_Rent = req.getParameter("DT_Rent") == null ? "" : req.getParameter("DT_Rent");
		
		Map<String, Object> parameter = new HashMap<String, Object>();
		JSONObject customParamter = new JSONObject();
		customParamter.put("DS_Title", DS_Title);
		customParamter.put("DT_Rent", DT_Rent);
		System.out.println(customParamter);
		try {
			Iterator<String> fileNames = req.getFileNames();
			String fileName = "";
			while(fileNames.hasNext()) {
				fileName = fileNames.next();
				List<MultipartFile> files = req.getFiles(fileName);
				for(MultipartFile file: files) {
					if(file != null) {
						String originalFileName = new String(file.getOriginalFilename().getBytes("ISO-8859-1"));
						String uploadRoot = FileUtil.UPLOAD_ROOT;
						
						// 한글파일 Encoding 확인용
						// String originalFileName1 = new
						// String(file.getOriginalFilename().getBytes("UTF-8"));
						// String originalFileName2 = new
						// String(file.getOriginalFilename().getBytes("ISO-8859-1"));
						// String originalFileName3 = new
						// String(file.getOriginalFilename().getBytes("EUC-KR"));
						// System.out.println("UTF-8 :::: " +
						// originalFileName1);
						// System.out.println("8859_1 :::: " +
						// originalFileName2);
						// System.out.println("EUC-KR :::: " +
						// originalFileName3);
						//
						
						// 파일 확장자 점검
						if(!isAcceptFileName(fileAcceptExt, originalFileName)) {
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
						
						JSONArray rtn = commonService.selectList("template.ESCA_PR_SingleFileUpload_Upload", parameter);
						JSONObject element = (JSONObject) rtn.get(0);
						fileCode = element.get("SN_File").toString();

						String saveFileName = (String) element.get("DS_Filename");
						resultFile = fileUpload(uploadRoot + path, file, saveFileName);
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

	/** 첨부파일 확장자를 점검한다. */
	public boolean isAcceptFileName(String pfileAcceptExt, String pfileName) {
		String fileAcceptExt = pfileAcceptExt;
		String fileName = pfileName;
		String fileExt = "";
		String notAcceptExt = "js,jsp,php,exe,asp,html";
		if((fileAcceptExt == null) || "".equals(fileAcceptExt.trim())) {
			return false;
		} else {
			fileAcceptExt = fileAcceptExt.toLowerCase();
		}
		int pos = fileName.lastIndexOf(".");
		if(pos < 0) {
			return false;
		}
		fileExt = fileName.substring(pos + 1).trim().toLowerCase();
		if(notAcceptExt.indexOf(fileExt) >= 0) {
			return false;
		}
		if(fileAcceptExt.indexOf(fileExt) < 0) {
			return false;
		}
		return true;
	}

	/** 첨부파일 업로드정보를 가져온다. */
	public File fileUpload(String path, MultipartFile file, String saveFileName) {
		String ext = getFileExt(file);
		File dir = new File(path);
		File resultFile = null;
		String uploadedName = dir.getAbsolutePath() + File.separator + saveFileName;

		if(!dir.exists()) {
			dir.mkdirs();
		}
		try {
			resultFile = new File(uploadedName);
			file.transferTo(resultFile);
		} catch(IllegalStateException | IOException e) {
			 e.printStackTrace();
		}
		return resultFile;
	}

	/** 첨부파일 확장자를 가져온다. */
	public String getFileExt(MultipartFile file) {
		String ext = "";
		if(file != null) {
			String fileName = file.getOriginalFilename();
			ext = fileName.substring(fileName.lastIndexOf(".") + 1);
		}
		return ext;
	}
}
