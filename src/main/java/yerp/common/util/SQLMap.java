package yerp.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

public class SQLMap {

	public final String conditionKey = ConstantUtil.STATUS;
	public JSONObject sqlMap = new JSONObject();
	public Map<String, Object> afterMap = new HashMap<String, Object>();

	public SQLMap() {
		afterMap.put(ConstantUtil.STATUS_NEW, new NewAfter());
		afterMap.put(ConstantUtil.STATUS_MODIFY, new ModifyAfter());
		afterMap.put(ConstantUtil.STATUS_REMOVE, new RemoveAfter());
	}

	public interface After {
		After add(String id);
		ArrayList<String> getList();
	}
	public class NewAfter implements After{
		ArrayList<String> list = new ArrayList<String>();		
		ArrayList<String[]> conditionList = new ArrayList<String[]>();		
		public NewAfter add(String id) {
			list.add(id);
			return this;
		}
		public ArrayList<String> getList() {
			return list;
		}
	}
	public class ModifyAfter implements After{
		ArrayList<String> list = new ArrayList<String>();	
		public ModifyAfter add(String id) {
			list.add(id);
			return this;
		}
		public ArrayList<String> getList() {
			return list;
		}
	}
	public class RemoveAfter implements After{
		ArrayList<String> list = new ArrayList<String>();	
		public RemoveAfter add(String id) {
			list.add(id);
			return this;
		}
		public ArrayList<String> getList() {
			return list;
		}
	}
	public String getConditionKey() {
		return conditionKey;
	}
	
	public NewAfter setNew(String id) {
		sqlMap.put(ConstantUtil.STATUS_NEW, id);
		return (NewAfter) afterMap.get(ConstantUtil.STATUS_NEW);
	}
	public ModifyAfter setModify(String id) {
		sqlMap.put(ConstantUtil.STATUS_MODIFY, id);
		return (ModifyAfter) afterMap.get(ConstantUtil.STATUS_MODIFY);
	}
	
	public RemoveAfter setRemove(String id) {
		sqlMap.put(ConstantUtil.STATUS_REMOVE, id);
		return (RemoveAfter) afterMap.get(ConstantUtil.STATUS_REMOVE);
	}	
	public JSONObject getMap() {
		return sqlMap;
	}
	public Map getAfterMap() {
		return afterMap;
	}
}
