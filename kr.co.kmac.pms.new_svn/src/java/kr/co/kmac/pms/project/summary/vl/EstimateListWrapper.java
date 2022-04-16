package kr.co.kmac.pms.project.summary.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.project.summary.data.EstimateData;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

/**
 * @author hyunseong
 * @jobDate &2008. 6. 30.
 * @description : 
 */
public class EstimateListWrapper implements ObjectWrapper {
	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();
	/* (non-Javadoc)
	 * @see net.mlw.vlh.adapter.util.ObjectWrapper#getWrappedRecord(java.lang.Object)
	 */
	public Object getWrappedRecord(Object objectToBeWrapped) {
		// TODO Auto-generated method stub
		ResultSet rs = (ResultSet) objectToBeWrapped;
		try {
			EstimateData estimateData = new EstimateData();
			estimateData.setProjectCode(rs.getString("projectCode"));
			try{estimateData.setAnswer3(rs.getString("answer3"));}catch(Exception e){}
			try{estimateData.setAnswer4(rs.getString("answer4"));}catch(Exception e){}
			try{estimateData.setAnswer5(rs.getString("answer5"));}catch(Exception e){}
			try{estimateData.setAnswer6(rs.getString("answer6"));}catch(Exception e){}
			try{estimateData.setAnswer8(rs.getString("answer8"));}catch(Exception e){}
			try{estimateData.setAnswer9(rs.getString("answer9"));}catch(Exception e){}
			try{estimateData.setAnswer10(rs.getString("answer10"));}catch(Exception e){}
			try{estimateData.setAnswer12(rs.getString("answer12"));}catch(Exception e){}
			try{estimateData.setAnswer13(rs.getString("answer13"));}catch(Exception e){}
			try{estimateData.setEstimateP(rs.getString("estimateP"));}catch(Exception e){}
			try{estimateData.setEstimateC(rs.getString("estimateC"));}catch(Exception e){}
			return estimateData;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("EstimateListWrapper fails",
					null,
					e);
		}
	}

	/* (non-Javadoc)
	 * @see net.mlw.vlh.adapter.util.ObjectWrapper#setValueListInfo(net.mlw.vlh.ValueListInfo)
	 */
	public void setValueListInfo(ValueListInfo info) {
		// TODO Auto-generated method stub

	}

}
