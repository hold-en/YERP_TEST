package yerp.common.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

public interface CommonDAO {
    List<Map<String, Object>> selectList(String sqlId, Map<String, String> parameter) throws Exception;
    Map<String, Object> selectOne(String sqlId, Map<String, String> parameter) throws Exception;
    int selectCount(String sqlId, Map<String, String> parameter) throws Exception;
    int insert(String sqlId, Map<String, String> parameter) throws Exception;
    int update(String sqlId, Map<String, String> parameter) throws Exception;
    int delete(String sqlId, Map<String, String> parameter) throws Exception;
}
