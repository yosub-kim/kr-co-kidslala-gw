package kr.co.kmac.pms.project.progress.data;

import java.util.Date;

public interface ProjectProgressContent {

	public String getProjectCode();

	public void setProjectCode(String projectCode);

	public String getProjectName();

	public void setProjectName(String projectName);

	public String getWorkSeq();

	public void setWorkSeq(String workSeq);

	public String getTaskName();

	public void setTaskName(String taskName);

	public String getContentId();

	public void setContentId(String contentId);

	public String getTitle();

	public void setTitle(String title);

	public String getContent();

	public void setContent(String content);

	public Date getCreateDate();

	public void setCreateDate(Date createDate);

	public String getCreatorId();

	public void setCreatorId(String creatorId);

}
