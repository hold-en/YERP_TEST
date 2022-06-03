package yerp.common.service;

import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import yerp.common.util.FileOption;
import yerp.common.util.SQLMap;

public interface CommonService {
	JSONArray selectList(String sqlId, Map<String, Object> parameter) throws Exception;
	JSONObject selectOne(String sqlId, Map<String, Object> parameter) throws Exception;
	JSONObject selectProcess(String sqlId, Map<String, Object> parameter) throws Exception;
	JSONObject normalProcess(String sqlId, Map<String, Object> parameter) throws Exception;
	JSONObject bodyProcess(Map<String, Object> parameter, SQLMap sqlMap) throws Exception;
	JSONObject bodyProcess(Map<String, Object> parameter, String sql) throws Exception;
	JSONObject bodyProcessDeleteFile(Map<String, Object> parameter, SQLMap sqlMap, FileOption file) throws Exception;
	int transactionProcess(TransactionService service) throws Exception;
	JSONObject bodyProcessNonTransaction(Map<String, Object> parameter, SQLMap sqlMap) throws Exception;
	JSONObject bodyProcessSQLs(Map<String, Object> parameter, String sqlI, String sqlU, String sqlD, String sqlN) throws Exception;
	JSONObject normalProcessWithTransaction(String sqlId, Map<String, Object> parameter) throws Exception;
}
