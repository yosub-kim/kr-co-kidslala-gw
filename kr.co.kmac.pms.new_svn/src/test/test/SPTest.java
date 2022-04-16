package test;

import java.sql.Types;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.object.StoredProcedure;

import com.sun.org.apache.xpath.internal.axes.HasPositionalPredChecker;

public class SPTest {

	public static void main(String[] args) {
		SPTest t = new SPTest();
		t.test();
		System.out.println("Done!");
	}

	void test() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName("net.sourceforge.jtds.jdbc.Driver");
		ds.setUrl("jdbc:jtds:sqlserver://172.16.0.203:1433/kmac");
		ds.setUsername("sa");
		ds.setPassword("kmnet98z");
		MyStoredProcedure sproc = new MyStoredProcedure(ds);

		Map results = sproc.execute();
		printMap(results);
	}

	private class MyStoredProcedure extends StoredProcedure {
		private static final String SQL = "sp_customerInfoList";

		public MyStoredProcedure(DataSource ds) {
			setDataSource(ds);
			setFunction(true);
			setSql(SQL);
			declareParameter(new SqlParameter("comCode", Types.VARCHAR));
			declareParameter(new SqlParameter("sort1", Types.VARCHAR));
			declareParameter(new SqlParameter("function_type", Types.VARCHAR));
			declareParameter(new SqlParameter("writer", Types.VARCHAR));
			declareParameter(new SqlParameter("comCode", Types.VARCHAR));
			declareParameter(new SqlParameter("comCode", Types.VARCHAR));
			declareParameter(new SqlParameter("comCode", Types.VARCHAR));
			declareParameter(new SqlParameter("comCode", Types.VARCHAR));
			declareParameter(new SqlParameter("comCode", Types.VARCHAR));
			declareParameter(new SqlParameter("comCode", Types.VARCHAR));
			declareParameter(new SqlParameter("comCode", Types.VARCHAR));
			declareParameter(new SqlParameter("comCode", Types.VARCHAR));
			declareParameter(new SqlParameter("comCode", Types.VARCHAR));
			declareParameter(new SqlParameter("comCode", Types.VARCHAR));
			declareParameter(new SqlParameter("comCode", Types.VARCHAR));
			declareParameter(new SqlParameter("comCode", Types.VARCHAR));
			declareParameter(new SqlParameter("comCode", Types.VARCHAR));
			declareParameter(new SqlParameter("comCode", Types.VARCHAR));
			declareParameter(new SqlParameter("comCode", Types.VARCHAR));
			declareParameter(new SqlParameter("comCode", Types.VARCHAR));
			compile();
		}

		public Map execute() {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("comCode", "1");
			map.put("sort1", "");
			map.put("function_type", "");
			map.put("writer", "");
			map.put("customerName", "");
			map.put("refer_dept", "");
			map.put("subject", "");
			map.put("regdate1", "");
			map.put("regdate2", "");
			map.put("embbsName", "");
			map.put("customerinfo_type", "");
			map.put("embbsdept", "");
			map.put("industry_type", "");
			map.put("business_type", "");
			map.put("content", "");
			map.put("introws", "");
			map.put("intpage", "");
			map.put("ppYn", "");
			map.put("tgubun", "");
			map.put("custChk", "");
			map.put("date1", "");
			map.put("date2", "");
			return execute(map);
		}
	}

	private static void printMap(Map results) {
		for (Iterator it = results.entrySet().iterator(); it.hasNext();) {
			System.out.println(it.next());
		}
	}
}
