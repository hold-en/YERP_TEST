package yerp.common.util;

public class ConstantUtil {
	public final static String SQL = "sql";
	public final static String NORMAL = "normal";
	public final static String BODY = "body";
	public final static String COMMON = "common";
	public final static String CUSTOM = "custom";
	
	public final static String PROC_RESULT_CODE = "RESULT_CODE";
	public final static String PROC_RESULT_MSG = "RESULT_MSG";
	
	public final static String STATUS = "rowStatus";
	public final static String STATUS_NEW = "C";
	public final static String STATUS_MODIFY = "U";
	public final static String STATUS_REMOVE = "D";
	public final static String DIR = "dir";
	public final static String OUT = "out";
	public final static String CNT = "cnt";
	public final static String PROC_RESULT = "procResult";
	public final static String ERROR = "error";
	public final static String INVALID = "invalid";
	
	public final static String SESSION_USER_ID = "SESSION_USER_IDNT";
	public final static String SESSION_USER_NM = "SESSION_USER_KRNM";
	public final static String SESSION_JWT = "SESSION_JWT";
	public final static String SESSION_USER_EMAIL = "SESSION_USER_EMAIL";
	public final static String SESSION_IP = "SESSION_IP";
	
	public final static String C_RESULT = "C_RESULT";
	public final static String OUT_ERR_CD = "OUT_ERR_CD";
	public final static String OUT_ERR_MSG = "OUT_ERR_MSG";
	
	/*
	 * 제외 페이지 선언
	 * */
	public final static String[] PAGE_ID_ARRAY = {"SDL050R","SISO601P","SISO611P","SISO901P","SISJ05PN","SISJ05PN_P", "SISO701P",
										    	  "PASS_SUSI","PASS_TSUSI","PASS_REGULAR","PASS_TRANSFER","PASS_MAJOR", "PASS_RESULT",
										    	  "SISW01P_JEON", "SISW01P_PYEON", "SISWS01PS7", "SISWS02PS6", "SISWS03PS6","SISWS03PH",
										    	  "JIWON_SUSI", "JIWON_REGULAR", "JIWON_TRANSFER", "JIWON_MAJOR", "JIWON_RESULT",
										    	  "SYB031P", "SYB035P","SYB035P1","SYB035P2","SYB035P3",
										    	  "SISG21P", "GUIDE_POPUP", "AIS_MAIN_IPSI", "AIS_UP_FORM",
										    	  "SISO121P", "SISO121P_RESULT", "SISG01P02","SISG01P02_RESULT", "BOOKMARK"};
}