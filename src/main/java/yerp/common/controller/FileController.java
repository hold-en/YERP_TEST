package yerp.common.controller;

import java.io.File;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.springframework.web.multipart.MultipartFile;

/**
 * <ul>
 * <li>FileController</li>
 * <li>설명 : 파일 컨트롤러</li>
 * <li>작성일 : 2022. 05. 12</li>
 * <li>작성자 : 정준석</li>
 * </ul>
 */
public class FileController {
	/** 첨부파일 확장자를 점검한다. */
	public static boolean isAcceptFileName(String pfileAcceptExt, String pfileName) {
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
	public static File fileUpload(String path, MultipartFile file, String saveFileName) {
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
	public static String getFileExt(MultipartFile file) {
		String ext = "";
		if(file != null) {
			String fileName = file.getOriginalFilename();
			ext = fileName.substring(fileName.lastIndexOf(".") + 1);
		}
		return ext;
	}
	
	public static JSONObject delete(String dirStr, String name) {
		JSONObject result = new JSONObject();
		dirStr = dirStr == null ? "" : dirStr;
		dirStr = dirStr.replace("../", "").replace("./", "");
		name = name == null ? "" : name;
		name = name.replace("../", "").replace("./", "");
		
		if (dirStr.equals("") || name.equals("")) return result;
		File dir = new File(dirStr);
		System.out.println("dir : " + dir);
		if (dir.exists() && dir.isDirectory()) {
			File[] files = dir.listFiles();
			for (File file : files) {
				if (file.getName().equals(name)) {
					result.put("FILE_LAST_PATH", file.getParentFile().getName());
					result.put("FILE_NAME", file.getName());
					result.put("isDeleted", file.delete());
					break;
				}
			}
		}
		
		return result;
	}
	
	public static File searchOne(String dirStr, String name) {
		if (dirStr.equals("") || name.equals(""))
			return null;
		
		File searchFile = null;
		File dir = new File(dirStr);
		if (dir.exists() && dir.isDirectory()) {
			File[] files = dir.listFiles();
			for (File file : files) {
				if (file.getName().equals(name))
					searchFile = file;
			}
		}
		
		return searchFile;
	}
}
