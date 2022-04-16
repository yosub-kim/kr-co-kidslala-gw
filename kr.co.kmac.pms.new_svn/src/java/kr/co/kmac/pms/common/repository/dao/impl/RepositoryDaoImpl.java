/*
 * $Id: RepositoryDaoImpl.java,v 1.1 2009/09/19 11:15:43 cvs3 Exp $
 * created by    : jiwoong
 * creation-date : 2008. 9. 10.
 * =========================================================
 * Copyright (c) 2008 Maninsoft, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.repository.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import kr.co.kmac.pms.common.repository.dao.IRepositoryDao;
import kr.co.kmac.pms.common.repository.data.UploadFile;
import kr.co.kmac.pms.common.repository.exception.RepositoryException;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.BatchSqlUpdate;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

/**
 * RepositoryDaoImpl
 * 
 * @author Administrator
 * @version $Id: RepositoryDaoImpl.java,v 1.1 2009/09/19 11:15:43 cvs3 Exp $
 */
public class RepositoryDaoImpl extends JdbcDaoSupport implements IRepositoryDao {
	private String serverIp;
	private String serverPort;

	protected RepositoryInsertQuery repositoryInsertQuery;
	protected RepositoryUpdateQuery repositoryUpdateQuery;
	protected RepositorySelectQuery1 repositorySelectQuery1;
	protected RepositorySelectQuery2 repositorySelectQuery2;
	protected RepositorySelectQuery3 repositorySelectQuery3;
	protected RepositoryDeleteQuery1 repositoryDeleteQuery1;
	protected RepositoryDeleteQuery2 repositoryDeleteQuery2;
	protected RepositoryDeleteQuery3 repositoryDeleteQuery3;

	protected void initDao() throws Exception {
		this.repositoryInsertQuery = new RepositoryInsertQuery(getDataSource());
		this.repositoryUpdateQuery = new RepositoryUpdateQuery(getDataSource());
		this.repositorySelectQuery1 = new RepositorySelectQuery1(getDataSource());
		this.repositorySelectQuery2 = new RepositorySelectQuery2(getDataSource());
		this.repositorySelectQuery3 = new RepositorySelectQuery3(getDataSource());
		this.repositoryDeleteQuery1 = new RepositoryDeleteQuery1(getDataSource());
		this.repositoryDeleteQuery2 = new RepositoryDeleteQuery2(getDataSource());
		this.repositoryDeleteQuery3 = new RepositoryDeleteQuery3(getDataSource());
	}

	protected class RepositoryInsertQuery extends BatchSqlUpdate {
		protected RepositoryInsertQuery(DataSource ds) {
			super(ds, " INSERT INTO FileRepository (                                             "
					+ "			groupId, fileId, fileInputName, originalFileName, fileSize,       "
					+ "         contentType, filePath, downloadURL, attachUserSsn, attachUserName, attachDate)"
					+ " VALUES ( ?, ?, ?, ?, ?,	 ?, ?, ?, ?, ?, getDate() )");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.NUMERIC));

			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected String insertFiles(List<UploadFile> list) {
			String groupId = null;
			for (UploadFile uploadFile : list) {
				this.update(new Object[] { uploadFile.getGroupId(), uploadFile.getFileId(), uploadFile.getFileInputName(),
						uploadFile.getOrginalFileName(), uploadFile.getFileSize(), uploadFile.getContentType(), uploadFile.getFilePath(),
						uploadFile.getDownloadUrl(), uploadFile.getUserId(), uploadFile.getUserName() });
				groupId = uploadFile.getGroupId();
			}
			this.flush();
			return groupId;
		}
	}

	protected class RepositoryUpdateQuery extends BatchSqlUpdate {
		protected RepositoryUpdateQuery(DataSource ds) {
			super(ds, " UPDATE FileRepository                                                                   "
					+ " SET fileInputName=?, originalFileName=?, fileSize=?, contentType=?,                     "
					+ "     filePath=?, downloadURL=?, attachUserSsn=?, attachUserName=?, attachDate=getDate()  "
					+ "	WHERE fileId=?                                                                          ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.NUMERIC));
			declareParameter(new SqlParameter(Types.VARCHAR));

			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));

			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected String updateFiles(List<UploadFile> list) {
			String groupId = null;
			for (UploadFile uploadFile : list) {
				this.update(new Object[] { uploadFile.getFileInputName(), uploadFile.getOrginalFileName(), uploadFile.getFileSize(),
						uploadFile.getContentType(), uploadFile.getFilePath(), uploadFile.getDownloadUrl(), uploadFile.getUserId(),
						uploadFile.getUserName(), uploadFile.getCreateDate(), uploadFile.getGroupId(), uploadFile.getFileId() });
				groupId = uploadFile.getGroupId();
			}
			this.flush();
			return groupId;
		}
	}

	protected class RepositorySelectQuery1 extends MappingSqlQuery {
		protected RepositorySelectQuery1(DataSource ds) {
			super(ds, "SELECT * FROM FileRepository WHERE fileId=?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected RepositorySelectQuery1(DataSource ds, String query) {
			super(ds, query);
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			UploadFile file = new UploadFile();
			file.setGroupId(rs.getString("groupId"));
			file.setFileId(rs.getString("fileId"));
			file.setFileInputName(rs.getString("fileInputName"));
			file.setOrginalFileName(rs.getString("originalFileName"));
			file.setFileSize(rs.getLong("fileSize"));
			file.setContentType(rs.getString("contentType"));
			file.setFilePath(rs.getString("filePath"));
			file.setDownloadUrl("http://" + getServerIp() + ":" + getServerPort() + "/servlet/RepositoryDownLoad?fileId=" + rs.getString("fileId"));
			file.setUserId(rs.getString("attachUserSsn"));
			file.setUserName(rs.getString("attachUserName"));
			file.setCreateDate(rs.getDate("attachDate"));
			return file;
		}
	}

	protected class RepositorySelectQuery2 extends RepositorySelectQuery1 {
		protected RepositorySelectQuery2(DataSource ds) {
			super(ds, "SELECT * FROM FileRepository WHERE originalFileName LIKE ?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}

	protected class RepositorySelectQuery3 extends RepositorySelectQuery1 {
		protected RepositorySelectQuery3(DataSource ds) {
			super(ds, "SELECT * FROM FileRepository WHERE groupId=?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}

	protected class RepositoryDeleteQuery1 extends SqlUpdate {
		protected RepositoryDeleteQuery1(DataSource ds) {
			super(ds, "DELETE FROM FileRepository WHERE fileId=?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
			setRequiredRowsAffected(1);
		}

		protected int deleteFile(String fileId) {
			return this.update(new Object[] { fileId });
		}
	}

	protected class RepositoryDeleteQuery2 extends SqlUpdate {
		protected RepositoryDeleteQuery2(DataSource ds) {
			super(ds, "DELETE FROM FileRepository WHERE groupId=?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int deleteFile(String groupId) {
			return this.update(new Object[] { groupId });
		}
	}
	
	protected class RepositoryDeleteQuery3 extends SqlUpdate {
		protected RepositoryDeleteQuery3(DataSource ds) {
			super(ds, "DELETE FROM ProjectTaskFormAttachData WHERE attachFileId=?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int deleteProjectTaskFormAttachData(String fileId) {
			return this.update(new Object[] { fileId });
		}
	}

	public int deleteFile(String groupId, String fileId) throws RepositoryException {
		return this.repositoryDeleteQuery1.deleteFile(fileId);
	}

	public int deleteFile(String fileId) throws RepositoryException {
		return this.repositoryDeleteQuery1.deleteFile(fileId);
	}
	
	public int deleteProjectTaskFormAttachData(String fileId) throws RepositoryException {
		return this.repositoryDeleteQuery3.deleteProjectTaskFormAttachData(fileId);
	}

	public int deleteFileList(String groupId) throws RepositoryException {
		return this.repositoryDeleteQuery2.deleteFile(groupId);
	}

	@SuppressWarnings("unchecked")
	public List<UploadFile> getFileByFileName(String fileName) throws RepositoryException {
		return this.repositorySelectQuery2.execute(new Object[] { "%" + fileName + "%" });
	}

	public UploadFile getFileByFileId(String fileId) throws RepositoryException {
		return (UploadFile) this.repositorySelectQuery1.findObject(new Object[] { fileId });
	}

	@SuppressWarnings("unchecked")
	public List<UploadFile> getFileList(String groupId) throws RepositoryException {
		return this.repositorySelectQuery3.execute(new Object[] { groupId });
	}

	public String saveFile(List<UploadFile> list) throws RepositoryException {
		return this.repositoryInsertQuery.insertFiles(list);
	}

	public String saveFile(UploadFile uploadFile) throws RepositoryException {
		List<UploadFile> list = new ArrayList<UploadFile>();
		list.add(uploadFile);
		return this.repositoryInsertQuery.insertFiles(list);
	}

	public String updateFile(List<UploadFile> list) throws RepositoryException {
		return this.repositoryUpdateQuery.updateFiles(list);
	}

	public String updateFile(UploadFile uploadFile) throws RepositoryException {
		List<UploadFile> list = new ArrayList<UploadFile>();
		list.add(uploadFile);
		return this.repositoryUpdateQuery.updateFiles(list);
	}

	/**
	 * @return Returns the serverIp.
	 */
	public String getServerIp() {
		return serverIp;
	}

	/**
	 * @param serverIp The serverIp to set.
	 */
	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	/**
	 * @return Returns the serverPort.
	 */
	public String getServerPort() {
		return serverPort;
	}

	/**
	 * @param serverPort The serverPort to set.
	 */
	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}

}
