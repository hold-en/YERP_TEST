package yerp.common.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import yerp.common.dao.CommonDAO;
import yerp.common.util.ConstantUtil;
import yerp.common.util.FileOption;
import yerp.common.util.FileUtil;
import yerp.common.util.ParameterUtil;
import yerp.common.util.SQLMap;
import yerp.common.util.SQLMap.After;

@Service
public class CommonServiceImpl extends EgovAbstractServiceImpl implements CommonService {
	@Autowired
	private JdbcTemplate template;
	
	@Autowired
	private CommonDAO dao;
	
	@Autowired
	private PlatformTransactionManager transactionManager;
	
	@Override
	public JSONArray selectList(String sqlId, Map<String, Object> parameter) throws Exception {
		JSONObject normal = ParameterUtil.getNormal(parameter);
		JSONObject common = ParameterUtil.getCommon(parameter);
		JSONObject custom = ParameterUtil.getCustom(parameter);
		
		normal.putAll(common);
		normal.putAll(custom);
		
		List<Map<String, Object>> sqlResult = null;
		JSONArray result = new JSONArray();
		try {
			sqlResult = dao.selectList(sqlId, normal);
			if (sqlResult != null)
				result.addAll(sqlResult);
		} catch (Exception e) {
			throw e;
		}
		return result;
	}
	
	@Override
	public JSONObject selectOne(String sqlId, Map<String, Object> parameter) throws Exception {
		JSONObject normal = ParameterUtil.getNormal(parameter);
		JSONObject common = ParameterUtil.getCommon(parameter);
		JSONObject custom = ParameterUtil.getCustom(parameter);
		
		normal.putAll(common);
		normal.putAll(custom);
		
		Map<String, Object> sqlResult = null;
		JSONObject result = new JSONObject();
		try {
			sqlResult = dao.selectOne(sqlId, normal);
			if (sqlResult != null)
				result.putAll(sqlResult);
		} catch (Exception e) {
			throw e;
		}
		return result;
	}
	
	@Override
	public JSONObject normalProcess(String sqlId, Map<String, Object> parameter) throws Exception {
		JSONObject normal = ParameterUtil.getNormal(parameter);
		JSONObject common = ParameterUtil.getCommon(parameter);
		JSONObject custom = ParameterUtil.getCustom(parameter);
		JSONObject result = new JSONObject();
		JSONArray resultForProc = new JSONArray();
		int successCnt = 0;
		if (normal != null) {
			try {
				normal.putAll(common);
				normal.putAll(custom);
				successCnt += dao.insert(sqlId, normal);
				if (hasProcResult(normal)) {
					resultForProc.add(normal);
				}
			} catch (Exception e) {
				throw e;
			}
			result.put(ConstantUtil.CNT, successCnt);
			result.put(ConstantUtil.PROC_RESULT, resultForProc);
		}
		return result;
	}
	
	public boolean hasProcResult(JSONObject data) {
		return data.containsKey(ConstantUtil.PROC_RESULT_CODE) || data.containsKey(ConstantUtil.PROC_RESULT_MSG);
	}
	
	@Override
	public JSONObject bodyProcess(Map<String, Object> parameter, SQLMap sqlMap) throws Exception {
		JSONObject normal = ParameterUtil.getNormal(parameter);
		JSONObject custom = ParameterUtil.getCustom(parameter);
		JSONArray body = ParameterUtil.getBody(parameter);
		JSONObject common = ParameterUtil.getCommon(parameter);
		JSONObject sql = sqlMap.getMap();
		Map<String, ArrayList<String>> after = sqlMap.getAfterMap();
		TransactionDefinition def = null;
		TransactionStatus transactionStatus = null;
		
		JSONObject result = new JSONObject();
		JSONArray resultForProc = new JSONArray();
		
		int successCnt = 0;
		if (body != null) {
			for (Object item : body) {
				JSONObject data = (JSONObject) item;
				String status = String.valueOf(data.get(sqlMap.getConditionKey()));
				After afterSql = (After) after.get(status);
				try {
					data.putAll(normal);
					data.putAll(common);
					data.putAll(custom);
					if (sql.get(status) != null) {
						if(afterSql != null) {
							def = new DefaultTransactionDefinition();
							transactionStatus = transactionManager.getTransaction(def);
						}
						successCnt += dao.insert(String.valueOf(sql.get(status)), data);
						if (hasProcResult(data)) {
							resultForProc.add(data);
						}
						if (afterSql != null) {
							for(String sqlId : afterSql.getList()) {
								dao.insert(sqlId, data);
								if (hasProcResult(data)) {
									resultForProc.add(data);
								}
							}
							transactionManager.commit(transactionStatus);
						}
					}
				} catch (Exception e) {
					if(transactionStatus != null) transactionManager.rollback(transactionStatus);
					throw e;
				}
			}
			result.put(ConstantUtil.CNT, successCnt);
			result.put(ConstantUtil.PROC_RESULT, resultForProc);
		}
		return result;
	}
	
	
	@Override
	public JSONObject bodyProcessDeleteFile(Map<String, Object> parameter, SQLMap sqlMap, FileOption file) throws Exception {
		JSONObject normal = ParameterUtil.getNormal(parameter);
		JSONObject custom = ParameterUtil.getCustom(parameter);
		JSONArray body = ParameterUtil.getBody(parameter);
		JSONObject common = ParameterUtil.getCommon(parameter);
		JSONObject sql = sqlMap.getMap();
		Map<String, ArrayList<String>> after = sqlMap.getAfterMap();
		String[] directoryForDeleteFile = file.getDirectoryColumn();
		TransactionDefinition def = null;
		TransactionStatus transactionStatus = null;
		
		JSONObject result = new JSONObject();
		JSONArray resultForProc = new JSONArray();
		
		int successCnt = 0;
		if (body != null) {
			for (Object item : body) {
				JSONObject data = (JSONObject) item;
				String status = String.valueOf(data.get(sqlMap.getConditionKey()));
				boolean isDelete = status.equals(ConstantUtil.STATUS_REMOVE);
				After afterSql = (After) after.get(status);
				
				try {
					data.putAll(normal);
					data.putAll(common);
					data.putAll(custom);
					if (sql.get(status) != null) {
						if(afterSql != null) {
							def = new DefaultTransactionDefinition();
							transactionStatus = transactionManager.getTransaction(def);
						}
						
						successCnt += dao.insert(String.valueOf(sql.get(status)), data);
						if (hasProcResult(data)) {
							resultForProc.add(data);
						}
						
						if (isDelete) {
							for (String column : directoryForDeleteFile) {
								if (data.get(column) != null) {
									FileUtil.delete(file.getFilePath(), (String) data.get(column));
								}
							}
						}
						
						if (afterSql != null) {
							for(String sqlId : afterSql.getList()) {
								dao.insert(sqlId, data);
								if (hasProcResult(data)) {
									resultForProc.add(data);
								}
							}
							transactionManager.commit(transactionStatus);
						}					
					}
				} catch (Exception e) {
					if(transactionStatus != null) transactionManager.rollback(transactionStatus);
					throw e;
				}
			}
			result.put(ConstantUtil.CNT, successCnt);
			result.put(ConstantUtil.PROC_RESULT, resultForProc);
		}
		return result;
	}
	
	@Override
	public int transactionProcess(TransactionService service) throws Exception {
		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		int result = 0;
		try {
			result = service.process();
			transactionManager.commit(status);
		} catch (Exception e) {
			transactionManager.rollback(status);
			throw e;
		}
		return result;
	}
	
	@Override
	public JSONObject bodyProcess(Map<String, Object> parameter, String sql) throws Exception {
		JSONObject normal = ParameterUtil.getNormal(parameter);
		JSONObject custom = ParameterUtil.getCustom(parameter);
		JSONArray body = ParameterUtil.getBody(parameter);
		JSONObject common = ParameterUtil.getCommon(parameter);		
	
		JSONObject result = new JSONObject();
		JSONArray resultForProc = new JSONArray();
	
		int successCnt = 0;
		if (body != null) {
			for (Object item : body) {
				JSONObject data = (JSONObject) item;
				try {
					data.putAll(normal);
					data.putAll(common);
					data.putAll(custom);
					
					successCnt += dao.insert(sql, data);
					if (hasProcResult(data)) {
						resultForProc.add(data);
					}
					
				} catch (Exception e) {
					throw e;
				}
			}
			result.put(ConstantUtil.CNT, successCnt);
			result.put(ConstantUtil.PROC_RESULT, resultForProc);
		}
		return result;
	}

	@Override
	public JSONObject selectProcess(String sqlId, Map<String, Object> parameter) throws Exception {
		// TODO Auto-generated method stub
		JSONObject normal = ParameterUtil.getNormal(parameter);
		JSONObject common = ParameterUtil.getCommon(parameter);
		JSONObject custom = ParameterUtil.getCustom(parameter);
		
		normal.putAll(common);
		normal.putAll(custom);
		
		Map<String, Object> sqlResult = null;
		JSONObject result = new JSONObject();
//		JSONArray resultForProc = new JSONArray();
		try {
			sqlResult = dao.selectOne(sqlId, normal);
//			resultForProc.add(normal.get(ConstantUtil.C_RESULT));
			if (sqlResult != null) {
				result.putAll(sqlResult);
			}
			result.put(ConstantUtil.PROC_RESULT, normal.get(ConstantUtil.C_RESULT));
//			System.out.println(normal);
//			System.out.println(result);
		} catch (Exception e) {
			throw e;
		}
		return result;
	}
	
	@Override
	public JSONObject bodyProcessNonTransaction(Map<String, Object> parameter, SQLMap sqlMap) throws Exception {
		JSONObject normal = ParameterUtil.getNormal(parameter);
		JSONObject custom = ParameterUtil.getCustom(parameter);
		JSONArray body = ParameterUtil.getBody(parameter);
		JSONObject common = ParameterUtil.getCommon(parameter);
		JSONObject sql = sqlMap.getMap();
		Map<String, ArrayList<String>> after = sqlMap.getAfterMap();
		
		JSONObject result = new JSONObject();
		JSONArray resultForProc = new JSONArray();
		
		int successCnt = 0;
		if (body != null) {
			for (Object item : body) {
				JSONObject data = (JSONObject) item;
				String status = String.valueOf(data.get(sqlMap.getConditionKey()));
				After afterSql = (After) after.get(status);
				try {
					data.putAll(normal);
					data.putAll(common);
					data.putAll(custom);
					if (sql.get(status) != null) {
						successCnt += dao.insert(String.valueOf(sql.get(status)), data);
						if (hasProcResult(data)) {
							resultForProc.add(data);
						}
						if (afterSql != null) {
							for(String sqlId : afterSql.getList()) {
								dao.insert(sqlId, data);
								if (hasProcResult(data)) {
									resultForProc.add(data);
								}
							}
						}
					}
				} catch (Exception e) {
					throw e;
				}
			}
			result.put(ConstantUtil.CNT, successCnt);
			result.put(ConstantUtil.PROC_RESULT, resultForProc);
		}
		return result;
	}
	
	@Override
	public JSONObject bodyProcessSQLs(Map<String, Object> parameter, String sqlI, String sqlU, String sqlD, String sqlN) throws Exception {
		JSONObject normal = ParameterUtil.getNormal(parameter);
		JSONObject custom = ParameterUtil.getCustom(parameter);
		JSONArray body = ParameterUtil.getBody(parameter);
		JSONObject common = ParameterUtil.getCommon(parameter);
		
		JSONObject result = new JSONObject();
		JSONArray resultForProc = new JSONArray();
		
		int successCnt = 0;
		if (body != null) {
			for (Object item : body) {
				JSONObject data = (JSONObject) item;
				String status = String.valueOf(data.get(ConstantUtil.STATUS));
				try {
					data.putAll(normal);
					data.putAll(common);
					data.putAll(custom);
					if(status.equals("C")) {
						successCnt += dao.insert(sqlI, data);
//						sqlSession.insert(sqlMapNmI, item);
					} else if(status.equals("U")) {
						successCnt += dao.update(sqlU, data);
//						sqlSession.update(sqlMapNmU, item);
					} else if(status.equals("D")) {
						successCnt += dao.delete(sqlD, data);
//						sqlSession.delete(sqlMapNmD, item);
					}
					else if(status.equals("R")) {
						successCnt += dao.update(sqlN, data);
					}
//					if (sql.get(status) != null) {
//						successCnt += dao.insert(String.valueOf(sql.get(status)), data);
//						if (hasProcResult(data)) {
//							resultForProc.add(data);
//						}
//					}
				} catch (Exception e) {
					throw e;
				}
			}
			result.put(ConstantUtil.CNT, successCnt);
			result.put(ConstantUtil.PROC_RESULT, resultForProc);
		}
		return result;
	}
	
	@Override
	public JSONObject normalProcessWithTransaction(String sqlId, Map<String, Object> parameter) throws Exception {
		JSONObject normal = ParameterUtil.getNormal(parameter);
		JSONObject common = ParameterUtil.getCommon(parameter);
		JSONObject custom = ParameterUtil.getCustom(parameter);
		
		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus transactionStatus = transactionManager.getTransaction(def);
		
		JSONObject result = new JSONObject();
		JSONArray resultForProc = new JSONArray();
		int successCnt = 0;
		
		if (normal != null) {
			try {
				normal.putAll(common);
				normal.putAll(custom);
				
				successCnt += dao.insert(sqlId, normal);
				if (hasProcResult(normal)) {
					resultForProc.add(normal);
				}
				transactionManager.commit(transactionStatus);
			}
			catch (Exception e) {
				if(transactionStatus != null) transactionManager.rollback(transactionStatus);
				throw e;
			}
			result.put(ConstantUtil.CNT, successCnt);
			result.put(ConstantUtil.PROC_RESULT, resultForProc);
		}
		return result;
	}
}
