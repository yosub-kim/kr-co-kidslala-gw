/*
 * $Id: IEntityDao.java,v 1.1 2009/09/19 11:15:30 cvs3 Exp $
 * created by    : 최인호
 * creation-date : 2005. 10. 26.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.org.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

/**
 * Entity DAO 공통 인터페이스
 * @author 최인호
 * @version $Id: IEntityDao.java,v 1.1 2009/09/19 11:15:30 cvs3 Exp $
 */
public interface IEntityDao extends ICommonDao {
	/**
	 * Entity가 존재하면 true 아니면 false를 리턴한다.
	 * @param id
	 * @return
	 * @throws DataAccessException
	 */
	public boolean entityExists(String id) throws DataAccessException;
	public boolean isEnabled(String id) throws DataAccessException;
	/**
	 * 아이디가 <code>id</code>인 엔티티를 삭제한다. 그룹처럼 자식을 갖는 엔티티는 해당 엔티티의 모든 자식까지 삭제한다.
	 * @param id
	 * @throws DataAccessException
	 */
	public void remove(String id) throws DataAccessException;
	/**
	 * 아이디가 <code>id</code>인 entity의 이름을 리턴한다.
	 * @param id
	 * @return
	 * @throws DataAccessException
	 */
	public String retrieveName(String id) throws DataAccessException;
	/**
	 * Entity의 상태별 갯수를 리턴한다.
	 * @param enabled
	 * @return
	 * @throws DataAccessException
	 */
	public int count(boolean enabled) throws DataAccessException;
	/**
	 * 이름으로 Entity를 찾는다. 이름은 like로 검색되기 때문에 에는 _, % 등 like 검색 신택스를 사용할 수 있다.
	 * @param name
	 * @return
	 * @throws DataAccessException
	 */
	public List findByName(String name) throws DataAccessException;
	/**
	 * 상태별로 검색한다.
	 * @param enabled
	 * @return
	 * @throws DataAccessException
	 */
	public List find(boolean enabled) throws DataAccessException;
}
