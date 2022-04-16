package kr.co.kmac.pms.project.progress.form;

import java.util.Date;

import kr.co.kmac.pms.attach.form.AttachForm;
import kr.co.kmac.pms.project.progress.data.ProjectProgressContent;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @struts:form name="projectProgressManagementAction"
 */
public class ProjectProgressContentForm extends AttachForm implements ProjectProgressContent {

	private static final long serialVersionUID = -1440792363073816511L;

	private String projectCode;
	private String projectName;
	private String workSeq;
	private String taskName;
	private String contentId;
	private String title;
	private String content;
	private Date createDate;
	private String creatorId;

	public ProjectProgressContentForm() {
		super();
	}

	public ProjectProgressContentForm(String projectCode, String workSeq, String contentId, String title, String content, String creatorId) {
		super();
		this.projectCode = projectCode;
		this.workSeq = workSeq;
		this.contentId = contentId;
		this.title = title;
		this.content = content;
		this.creatorId = creatorId;
	}

	/**
	 * @return the projectCode
	 */
	public String getProjectCode() {
		return projectCode;
	}

	/**
	 * @param projectCode the projectCode to set
	 */
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	/**
	 * @return the projectName
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * @param projectName the projectName to set
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * @return the workSeq
	 */
	public String getWorkSeq() {
		return workSeq;
	}

	/**
	 * @param workSeq the workSeq to set
	 */
	public void setWorkSeq(String workSeq) {
		this.workSeq = workSeq;
	}

	/**
	 * @return the taskName
	 */
	public String getTaskName() {
		return taskName;
	}

	/**
	 * @param taskName the taskName to set
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	/**
	 * @return the contentId
	 */
	public String getContentId() {
		return contentId;
	}

	/**
	 * @param contentId the contentId to set
	 */
	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the creatorId
	 */
	public String getCreatorId() {
		return creatorId;
	}

	/**
	 * @param creatorId the creatorId to set
	 */
	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
