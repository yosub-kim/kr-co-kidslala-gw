package kr.co.kmac.pms.support.certificate.action;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.mlw.vlh.ValueList;
import net.mlw.vlh.ValueListHandler;
import net.mlw.vlh.ValueListInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import whois.whoisSMS;
import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.repository.action.RepositoryDispatchActionSupport;
import kr.co.kmac.pms.common.util.DateTime;
import kr.co.kmac.pms.common.util.SessionUtils;
import kr.co.kmac.pms.common.util.StringUtil;
import kr.co.kmac.pms.expertpool.data.ExpertPool;
import kr.co.kmac.pms.expertpool.exception.ExpertPoolException;
import kr.co.kmac.pms.expertpool.manager.ExpertPoolManager;
import kr.co.kmac.pms.support.certificate.data.CertificateData;
import kr.co.kmac.pms.support.certificate.manager.CertificateManager;

/**
 * Provide description for "BoardAction"
 * @author halberd
 * @version $Id: MobileBoardAction.java,v 1.9 2015/05/18 00:53:02 cvs Exp $
 */

/**
 * @struts.action name="certificateAction"
 *                path="/action/MobileCertificateAction"
 *                parameter="mode" scope="request"
 * @struts.action-forward name="mobileCertificateList"
 *                        path="/m/web/support/certificateList.jsp"
 *                        redirect="false"
 * @struts.action-forward name="mobileCertificateForm"
 *                        path="/m/web/support/certificateForm.jsp"
 *                        redirect="false"
 */

public class MobileCertificateAction extends RepositoryDispatchActionSupport {

    private static final Log logger = LogFactory.getLog(MobileCertificateAction.class);
    private CertificateManager certificateManager;
    private ExpertPoolManager expertPoolManager;

    public CertificateManager getCertificateManager() {
        return certificateManager;
    }

    public void setCertificateManager(CertificateManager certificateManager) {
        this.certificateManager = certificateManager;
    }

    public ExpertPoolManager getExpertPoolManager() {
        return expertPoolManager;
    }

    public void setExpertPoolManager(ExpertPoolManager expertPoolManager) {
        this.expertPoolManager = expertPoolManager;
    }

    public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        int pageSize = ServletRequestUtils.getIntParameter(request, "pageSize", 10);
        String pg = ServletRequestUtils.getStringParameter(request, "pg", "");
        String ctype = ServletRequestUtils.getStringParameter(request, "ctype", "");
        String status = ServletRequestUtils.getStringParameter(request, "status", "");
        String userId = SessionUtils.getUserObjext().getLoginId();

        try {
            WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
            ValueListHandler vlh = (ValueListHandler) wc.getBean("supportValueList", ValueListHandler.class);
            ValueListInfo info = new ValueListInfo();
            Map<String, String> filters = new HashMap<String, String>();

            filters.put(ValueListInfo.PAGING_PAGE, pg);
            filters.put(ValueListInfo.PAGING_NUMBER_PER,
                    String.valueOf(pageSize == -1 ? Integer.MAX_VALUE - 1 : pageSize));
            filters.put("ctype", ctype);
            filters.put("status", status);
            filters.put("userId", userId);

            info.setFilters(filters);
            ValueList valueList = vlh.getValueList("certificateList", info);
            request.setAttribute("result", valueList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        request.setAttribute("pg", pg);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("ctype", ctype);
        request.setAttribute("status", status);
        request.setAttribute("userId", userId);

        return mapping.findForward("mobileCertificateList");
    }

    public ActionForward inputForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        return mapping.findForward("mobileCertificateForm");
    }

    public void saveCertificate(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        HashMap<String, Serializable> map = new HashMap<String, Serializable>();
        String userSsn = SessionUtils.getUsername(request);
        ExpertPool expertPool = this.getExpertPoolManager().retrieve(userSsn);

        String mobileNo = StringUtil.replace(expertPool.getMobileNo(), "-", "");
        String userName = SessionUtils.getUserObjext().getName();

        String gubun = "1";
        String writerId = SessionUtils.getUserObjext().getLoginId();
        String approvedate = DateTime.getTimeStampString();
        String status = ServletRequestUtils.getStringParameter(request, "status", "1");
        String ctype = ServletRequestUtils.getStringParameter(request, "ctype", "");
        String use_kind = ServletRequestUtils.getStringParameter(request, "use_kind", "");
        String use_place = ServletRequestUtils.getStringParameter(request, "use_place", "");
        String userDept = ServletRequestUtils.getStringParameter(request, "userDept", "");
        String start_date = ServletRequestUtils.getStringParameter(request, "start_date", "");

        CertificateData certificateData = new CertificateData();

        try {
            certificateData.setWriterId(writerId);
            certificateData.setUserId(writerId);
            certificateData.setStatus(status);
            certificateData.setCtype(ctype);
            certificateData.setUse_kind(use_kind);
            certificateData.setUse_place(use_place);
            certificateData.setGubun(gubun);
            certificateData.setWriterDept(userDept);
            certificateData.setUserDept(userDept);
            certificateData.setStart_date(start_date);
            certificateData.setApprovedate(approvedate);
            certificateData.setManager_approvedate(approvedate);

            String certify_num = getCertificateManager().insert(certificateData);

            switch (ctype) {
                case "1":
                    ctype = "재직즘명서";
                    break;
                case "2":
                    ctype = "경력확인서";
                    break;
                case "3":
                    ctype = "갑근세 원천징수증명서";
                    break;
                case "5":
                    ctype = "학위확인서";
                    break;
                case "6":
                    ctype = "근로소득원천진수영수증";
                    break;
            }

            whoisSMS sms = new whoisSMS();
            sms.login("kmac4u", "kmac123");
            String sms_msg = "[KMAC]\n" + userName + "님께서 " + ctype + "를 [" + use_place + "]제출로 신청하셨습니다.";
            sms.setParams(mobileNo, "02-3786-0521", new String(sms_msg.getBytes("EUC-KR"), "8859_1"),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA).format(new Date()), "L");
            sms.emmaSend();

            int retCode = sms.getRetCode();
            String retMessage = sms.getRetMessage();
            int retLastPoint = sms.getLastPoint();

            logger.info(retCode + ":" + retMessage + ":" + retLastPoint);

            map.put("result", true);
            map.put("resultMsg", "처리되었습니다");
            AjaxUtil.successWrite(response, map);
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            map.put("result", false);
            map.put("resultMsg", "처리도중 에러 발생");
            AjaxUtil.failWrite(response, map);
        }

    }

}
