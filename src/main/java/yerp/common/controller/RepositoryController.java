package yerp.common.controller;

import java.io.File;
import java.io.FileInputStream;

import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import yerp.common.service.CommonService;
import yerp.common.util.APIResponse;
import yerp.common.util.FileOption;
import yerp.common.util.FileUtil;
import yerp.common.util.FileUtil2;

@RequestMapping("/repository")
@Controller
public class RepositoryController {
	@Autowired
	private CommonService commonService;
	
	@RequestMapping("/upload")
	public void upload(@RequestPart MultipartFile[] FileData, String dir, String root, HttpServletResponse httpResponse) {
		String resultStr = "<script>";
		try {
			String dirStr = dir == null ? "" : dir;
			FileOption option = new FileOption();
			option.setFilePath(dirStr);
			
			//option.setUploadableExtension(new String[] {"png" ,"jpg" ,"JPG"});
			JSONArray uploadedFiles = new JSONArray();
			if(root == null) {
				uploadedFiles = FileUtil.store(option, FileData);
			} else {
				uploadedFiles = FileUtil2.store(option, FileData);
			}
//			System.out.println(uploadedFiles);
			if(uploadedFiles.size() > 0) {
				JSONObject file = (JSONObject)uploadedFiles.get(0);
				String error = (String) file.get("error");System.out.println(error);
				if(error == null) {
					resultStr += "window.onload = doInit;";
					resultStr += "function doInit() {";
					resultStr += "parent.upload.callback('";
					resultStr += "<ret>";
					resultStr += "<key>"+option.getFilePath()+"</key>";
					resultStr += "<storedFileList>"+file.get("SAVE_NAME")+"</storedFileList>";
					resultStr += "<localfileList>"+file.get("ORGN_NAME")+"</localfileList>";
					resultStr += "<fileSizeList>"+file.get("FILE_SIZE")+"</fileSizeList>";
					resultStr += "<maxUploadSize></maxUploadSize>";
					resultStr += "<subSize></subSize>";
					resultStr += "<deniedList></deniedList>";
					resultStr += "<deniedCodeList></deniedCodeList>";
					resultStr += "</ret>";
					resultStr += "');";
					resultStr += "}";
					resultStr += "</script>";
					httpResponse.setStatus(HttpServletResponse.SC_OK);
				} else {
					resultStr += "window.onload = doInit;";
					resultStr += "function doInit() {";
					resultStr += "parent.upload.callback('";
					resultStr += "<ret>";
					resultStr += "<key></key>";
					resultStr += "<storedFileList></storedFileList>";
					resultStr += "<localfileList></localfileList>";
					resultStr += "<fileSizeList></fileSizeList>";
					resultStr += "<maxUploadSize></maxUploadSize>";
					resultStr += "<subSize></subSize>";
					resultStr += "<deniedList>"+file.get("error")+"</deniedList>";
					resultStr += "<deniedCodeList>500</deniedCodeList>";
					resultStr += "</ret>";
					resultStr += "');";
					resultStr += "}";
					resultStr += "</script>";
					httpResponse.setStatus(HttpServletResponse.SC_OK);
				}
			} else {
				httpResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
			
			httpResponse.setHeader("Content-Type", "text/html; charset=UTF-8");
			httpResponse.getWriter().write(resultStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/download")
	public void download(HttpServletResponse response, String dir, String name) {
		dir = dir == null ? "" : dir;
		dir = dir.replace("../", "").replace("./", "");
		name = name == null ? "" : name;
		name = name.replace("../", "").replace("./", "");
		
		File file = FileUtil.searchOne(dir, name);
		try {
			if (file != null && file.exists() && file.isFile()) {
				response.setCharacterEncoding("EUC-KR");
				response.setHeader("Content-Disposition",
						"attachment; filename=" + new String(file.getName().getBytes("EUC-KR"), "ISO-8859-1"));
				
				FileCopyUtils.copy(new FileInputStream(file), response.getOutputStream());
				response.flushBuffer();
			} else {
				response.sendRedirect("/");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/delete")
	public ResponseEntity<JSONObject> delete(String dir, String name) {
		APIResponse response = new APIResponse();
		try {
			dir = dir == null ? "" : dir;
			dir = dir.replace("../", "").replace("./", "");
			name = name == null ? "" : name;
			name = name.replace("../", "").replace("./", "");
			
			response.setResponse(FileUtil.delete(dir, name));
		} catch (Exception e) {
			response.setResponseForError(e);
		}
		return response.getEntity();
	}
}
