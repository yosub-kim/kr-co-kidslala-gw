package kr.co.kmac.pms.sanction.projectexpense.dao;

import java.util.List;

import kr.co.kmac.pms.sanction.projectexpense.data.ExpenseRealTimeResultDetailForSanction;

import org.springframework.dao.DataAccessException;

public interface ProjectExpenseTempForSanctionDao {

	public void insert(ExpenseRealTimeResultDetailForSanction projectExpenseTempForSanction) throws DataAccessException;

	public void delete(String seq) throws DataAccessException;

	public List<ExpenseRealTimeResultDetailForSanction> select(String seq) throws DataAccessException;
	
	public List<ExpenseRealTimeResultDetailForSanction> select(String year, String month) throws DataAccessException;

}
