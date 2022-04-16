/*
 * $Id: IGroupConstants.java,v 1.1 2009/09/19 11:15:25 cvs3 Exp $
 * created by    : 최인호
 * creation-date : 2005. 10. 24.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.org.data;

/**
 * 그룹 모델에서 사용하는 상수
 * @author 최인호
 * @version $Id: IGroupConstants.java,v 1.1 2009/09/19 11:15:25 cvs3 Exp $
 */
public interface IGroupConstants {
	public static final int TYPE_MAX_VALUE = 10000;
	public static final int LEVEL1_STEP = 1000;
	public static final int LEVEL2_STEP = 100;
	public static final int LEVEL3_STEP = 10;
	public static final int LEVEL4_STEP = 1;

	public static final int GROUP_GENERAL = 0;
	public static final int GROUP_USER_GROUP = 1000;
	public static final int GROUP_ORG_UNIT = 2000;
	public static final int GROUP_ORG_COMPANY = GROUP_ORG_UNIT + 100;
	public static final int GROUP_ORG_DEPARTMENT = GROUP_ORG_UNIT + 200;
	public static final int GROUP_ORG_BRANCH = GROUP_ORG_UNIT + 300;
	public static final int GROUP_ORG_COORPERATION = GROUP_ORG_UNIT + 400;
	public static final int GROUP_ORG_TEMPORARY = GROUP_ORG_UNIT + 500;
	public static final int[] GROUP_TYPES = {GROUP_GENERAL,
			GROUP_USER_GROUP,
			GROUP_ORG_UNIT,
			GROUP_ORG_BRANCH,
			GROUP_ORG_COMPANY,
			GROUP_ORG_COORPERATION,
			GROUP_ORG_DEPARTMENT,
			GROUP_ORG_TEMPORARY};

	public static final String PATH_SEPARATOR = "/";
}
