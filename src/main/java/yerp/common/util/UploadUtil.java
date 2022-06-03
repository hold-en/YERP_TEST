package yerp.common.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.multipart.MultipartFile;

public class UploadUtil {

	public static int UNCFileLogin(String UploadKey) {

        if (UploadKey == "UploadDir")
        {
            return UNCFileLogin("shingu.0721!", "Administrator", UploadKey);
        }
        else if (UploadKey == "TransDir")
        {
            return UNCFileLogin("shingu.0721!", "Administrator", UploadKey);
        }
        else if (UploadKey == "FileServerDir")
        {
            return UNCFileLogin("shingu.0721!", "Administrator", UploadKey);
        }
        else if (UploadKey == "TransDir37")
        {
            return UNCFileLogin("shingu.0721!", "Administrator", UploadKey);
        }
        else if (UploadKey == "BPHUpDir")
        {
            return UNCFileLogin("shingu.0721!", "Administrator", UploadKey);
        }

        return 0;
	}

	public static int UNCFileLogin(String password, String userid, String UploadKey) {
    	
    	String uploadDir = ""; //업로드위치
    	
        //uploadDir = ConstantUtil.getData(UploadKey);
        //return CLEANCOM.WebCommon.WebUtil.UNCFileLogin(uploadDir, password, userid);
        
    	//result  = menuInfo.permission;
    	return 0;
    };
    
}
