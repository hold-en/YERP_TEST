package yerp.common.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import yerp.common.util.ConstantUtil;

import java.util.List;
import java.util.Map;

@Repository
public class CommonDAOImpl implements CommonDAO {
    @Autowired
    private SqlSession session;

    @Override
    public List<Map<String, Object>> selectList(String sqlId, Map<String, String> parameter) throws Exception {
        return session.selectList(sqlId, parameter);
    }

    @Override
    public Map<String, Object> selectOne(String sqlId, Map<String, String> parameter) throws Exception {
        return session.selectOne(sqlId, parameter);
    }

    @Override
    public int selectCount(String sqlId, Map<String, String> parameter) throws Exception {
        return session.selectOne(sqlId, parameter);
    }

    @Override
    public int insert(String sqlId, Map<String, String> parameter) throws Exception {
        return session.insert(sqlId, parameter);
    }

    @Override
    public int update(String sqlId, Map<String, String> parameter) throws Exception {
        return session.update(sqlId, parameter);
    }

    @Override
    public int delete(String sqlId, Map<String, String> parameter) throws Exception {
        return session.delete(sqlId, parameter);
    }
}
