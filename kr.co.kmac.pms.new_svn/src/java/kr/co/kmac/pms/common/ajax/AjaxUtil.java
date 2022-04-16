package kr.co.kmac.pms.common.ajax;

import java.io.Writer;
import java.util.HashMap;
import javax.servlet.http.HttpServletResponse;

public class AjaxUtil {

	public static void successWrite(HttpServletResponse response) throws Exception {
		HashMap<String, String> hmData = new HashMap<String, String>();
		response.setContentType("text/html;charset=utf-8");
		Writer w = response.getWriter();
		JSONWriter jw = new JSONWriter();

		hmData.put("result", "SUCCESS");
		w.write(jw.write(hmData));

	}

	public static void successWrite(HttpServletResponse response, HashMap hmData) throws Exception {

		response.setContentType("text/html;charset=utf-8");
		Writer w = response.getWriter();
		JSONWriter jw = new JSONWriter();

		hmData.put("result", "SUCCESS");
		w.write(jw.write(hmData));

	}

	public static void failWrite(HttpServletResponse response) throws Exception {
		HashMap<String, String> hmData = new HashMap<String, String>();
		response.setContentType("text/html;charset=utf-8");
		Writer w = response.getWriter();
		JSONWriter jw = new JSONWriter();

		hmData.put("result", "FAIL");
		w.write(jw.write(hmData));

	}

	public static void failWrite(HttpServletResponse response, HashMap hmData) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		Writer w = response.getWriter();
		JSONWriter jw = new JSONWriter();

		hmData.put("result", "FAIL");
		w.write(jw.write(hmData));

	}
}
