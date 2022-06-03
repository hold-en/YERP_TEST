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

public class StringUtil {
    public static boolean isNull(String str) {
        return (str == null || str.equals(""));
    }

    public static boolean isNull(Object object) {
        return object == null;
    }

    public static boolean patternFind(String str, String regexp) {
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }

    public static boolean isNumber(String str) {
        String regex = "\\D";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return !matcher.find();
    }

    public static String isMatch(String str, String pat, int groupNum) {
        Pattern pattern = Pattern.compile(pat);
        Matcher matcher = pattern.matcher(str);
        String result = "";
        if (matcher.find())
            result = matcher.group(groupNum);
        return result;
    }

    public static String getByteString(String str, int start, int bytes) {
        return new String(str.getBytes(), start, bytes);
    }
}
