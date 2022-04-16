package kr.co.kmac.pms.project.master.dao;

import java.util.List;

import kr.co.kmac.pms.project.master.data.ProjectMember;

import org.springframework.dao.DataAccessException;

public interface ErpProjectMemberDao {

	public List<ProjectMember> select(String entNo) throws DataAccessException;

}
