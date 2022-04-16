package kr.co.kmac.pms.customer.dao.impl;

import java.sql.Types;

import javax.sql.DataSource;

import kr.co.kmac.pms.board.exception.BoardException;
import kr.co.kmac.pms.customer.dao.CustomerDao;
import kr.co.kmac.pms.customer.data.CustomerForm;
import kr.co.kmac.pms.customer.data.CustomerPickers;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.SqlUpdate;

public class CustomerDaoImpl extends JdbcDaoSupport implements CustomerDao {

	private CustomerInfoInsertQuery customerInfoInsertQuery;
	private CustomerInfoPickersInsertQuery customerInfoPickersInsertQuery;

	protected void initDao() throws Exception {
		this.customerInfoInsertQuery = new CustomerInfoInsertQuery(getDataSource());
		this.customerInfoPickersInsertQuery = new CustomerInfoPickersInsertQuery(getDataSource());
	}

	public String insert(CustomerForm customerForm) throws DataAccessException {
		int maxIdx = getJdbcTemplate().queryForInt("SELECT ISNULL(MAX(idx),0)+1 FROM customer  WITH(NOLOCK) ");
		customerForm.setIdx(Integer.toString(maxIdx));
		this.customerInfoInsertQuery.insert(customerForm);
		
		String[] pickerSsnArray = customerForm.getPicker_ssn();
		if(pickerSsnArray != null && pickerSsnArray.length > 0){
			for (int i = 0; i < pickerSsnArray.length; i++) {
				CustomerPickers customerPickers = new CustomerPickers();
				customerPickers.setCustomerIdx(String.valueOf(maxIdx));
				customerPickers.setPickerSsn(customerForm.getPicker_ssn()[i]);// 수집자 ssn
				customerPickers.setInfoDate(customerForm.getInfo_dateYyyy() + "-" + customerForm.getInfo_dateMm() + "-" + customerForm.getInfo_dateDd());// 정보수집일
				customerPickers.setEmbbsMethod(customerForm.getEmbbsMethod());// 정보수집방법
				this.customerInfoPickersInsertQuery.insert(customerPickers);
			}
		}
		return Integer.toString(maxIdx);
	}
	
	public String select(String ssn) throws BoardException {
		String jobClass = (String) getJdbcTemplate().queryForObject("select jobClass from expertPool where ssn='" + ssn + "'", String.class);
		return jobClass;
	}
	
	public String select2(String ssn) throws BoardException {
		String jobClass = (String) getJdbcTemplate().queryForObject("select (case when companyPosition in ('01HC','04CI','06CB','08CF','09CJ') then 'T' when dept = '9261' then 'T' else 'F' end) as manager_chk from (select e.ssn, e.name, e.companyPosition, e.dept from expertpool e where ssn='" + ssn + "')p", String.class);
		return jobClass;
	}

	protected class CustomerInfoPickersInsertQuery extends SqlUpdate {
		protected CustomerInfoPickersInsertQuery(DataSource ds) {
			super(ds, " insert into Customer_Pickers (							 "
					+ "	customer_idx, picker_ssn, info_date, embbsMethod		 "
					+ "	) values ( 	?,	?,	?,	?								)"
				);
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.DATE));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		protected int insert(CustomerPickers customerPickers) throws DataAccessException {
			return this.update(new Object[] {
					customerPickers.getCustomerIdx(),	
					customerPickers.getPickerSsn(),
					customerPickers.getInfoDate(),
					customerPickers.getEmbbsMethod()
			});
		}
	}
	
	protected class CustomerInfoInsertQuery extends SqlUpdate {
		protected CustomerInfoInsertQuery(DataSource ds) {
			super(ds, "insert into customer (							"
					+ "							idx, 					"
					+ "							master_idx, 			"
					+ "							com_idx, 				"
					+ "							embbs_idx, 				"
					+ "							embbsName, 				"
					+ "							embbsCompany,			"		 
					+ "							embbsDept, 				"
					+ "							embbsStatus,  			"
					+ "							embbsPhone,  			"
					+ "							embbsEmail,  			"
					+ "							embbsGather,  			"
					+ "							embbsMethod,  			"
					+ "							subject,  				"
					+ "							writer,  				"
					+ "							writerSsn,  			"
					+ "							uid,		  			"
					+ "							content,  				"
					+ "							opinion,  				"
					+ "							info_date,  			"
					+ "							regdate, 				"
					+ "							sanupgubun, 			"
					+ "							readCnt,  				"
					+ "							ip,  					"
					+ "							customerInfoType,		" 
					+ "							receiveGrade,  			"
					+ "							projectName,  			"
					+ "							projectCode, 			"
					+ "							writerDept, 			"
					+ "							writerTeam, 			"
					+ "							writercompanyPosition,	" 
					+ "							writerjobclass, 		"
					+ "							customerName, 			"
					+ "							customerCode,  			"
					+ "							industryTypeCode,		"
					+ "							businessTypeCode, 		"
					+ "							refer_dept, 			"
					+ "							isorg					"
					+ "	) values ( 										"					
					+ "							?,						"//idx	
					+ "							'0',					"//master_idx	
					+ "							'0',					"//com_idx
					+ "							?,						"//embbs_idx
					+ "							?,						"//embbsName
					+ "							?,						"//embbsCompany
					+ "							?,						"//embbsDept
					+ "							?,						"//embbsStatus
					+ "							?,						"//embbsPhone
					+ "							?,						"//embbsEmail
					+ "							?,						"//embbsGather
					+ "							?,						"//embbsMethod
					+ "							?,						"//subject
					+ "							?,						"//writer
					+ "							(select uid from expertPool where ssn = ?),						"//writerSsn
					+ "							?,						"//uid
					+ "							?,						"//content
					+ "							?,						"//opinion
					+ "							cast(? as datetime),	"//info_date
					+ "							getdate(),				"//regdate
					+ "							'',						"//sanupgubun
					+ "							'0',					"//readCnt
					+ "							?,						"//Ip
					+ "							?,						"//customerInfoType
					+ "							'',						"//receiveGrade
					+ "							'',						"//projectName
					+ "							'',						"//projectCode
					+ "							?,						"//writerDept
					+ "							?,						"//writerTeam
					+ "							?,						"//companyPosition
					+ "							?,						"//jobclass
					+ "							?,						"//customerName
					+ "							?,						"//customerCode
					+ "							?,						"//industryTypeCode
					+ "							?,						"//businessTypeCode
					+ "							?,						"//refer_dept
					+ "							''						)"	 
				);

			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR)); 

			compile();
		}
		protected int insert(CustomerForm customerForm) throws DataAccessException {
			return this.update(new Object[] {
					customerForm.getIdx(),	
					customerForm.getEmbbs_idx(),
					customerForm.getEmbbsName(),
					customerForm.getEmbbsCompany(),
					customerForm.getEmbbsDept(),
					customerForm.getEmbbsStatus(),
					customerForm.getEmbbsPhone(),
					customerForm.getEmbbsEmail(),
					customerForm.getEmbbsGather(),
					customerForm.getEmbbsMethod(),
					
					customerForm.getSubject(),					
					customerForm.getWriter(),
					customerForm.getWriterSsn(),
					customerForm.getWriterSsn(),
					customerForm.getContent(),
					customerForm.getOpinion(),
					customerForm.getInfo_dateYyyy() + "-" + customerForm.getInfo_dateMm() + "-" + customerForm.getInfo_dateDd(),
					customerForm.getIp(),
					customerForm.getCustomerInfoType(), 
					customerForm.getWriterDept(),
					
					customerForm.getWriterTeam(),
					customerForm.getCompanyPosition(),
					customerForm.getJobclass(),
					customerForm.getCustomerName(),
					customerForm.getCustomerCode(),
					customerForm.getIndustryTypeCode(),
					customerForm.getBusinessTypeCodeList(),
					customerForm.getRefer_dept()
			});
		}
	}

}
