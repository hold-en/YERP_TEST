package yerp.common.handler;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

@MappedTypes(String.class)
@MappedJdbcTypes(JdbcType.TIMESTAMP)
public class DateTypeHandler implements TypeHandler<String> {

	@Override
	public void setParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
		// TODO Auto-generated method stub
		
	}

    @Override
    public String getResult(ResultSet rs, String columnName) throws SQLException {
		Timestamp sqlTimestamp = rs.getTimestamp(columnName);
		if(sqlTimestamp != null) {
			Date date = new Date(sqlTimestamp.getTime());
			String formattedDate = new SimpleDateFormat("yyyyMMdd HH:mm:ss").format(date);	
			if (formattedDate != null) {
				return formattedDate;
			}			
		}

		return null;    	
    }

    @Override
    public String getResult(ResultSet rs, int columnIndex) throws SQLException {
		Timestamp sqlTimestamp = rs.getTimestamp(columnIndex);
		if(sqlTimestamp != null) {
			Date date = new Date(sqlTimestamp.getTime());
			String formattedDate = new SimpleDateFormat("yyyyMMdd HH:mm:ss").format(date);		
			if (formattedDate != null) {
				return formattedDate;
			}			
		}
		return null;    
    }

    @Override
    public String getResult(CallableStatement cs, int columnIndex) throws SQLException {
		Timestamp sqlTimestamp = cs.getTimestamp(columnIndex);
		if(sqlTimestamp != null) {
			Date date = new Date(sqlTimestamp.getTime());
			String formattedDate = new SimpleDateFormat("yyyyMMdd HH:mm:ss").format(date);		
			if (formattedDate != null) {
				return formattedDate;
			}			
		}
		return null;  
    }

}

