package kr.co.kmac.pms.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ResourceBundle;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import kr.co.kmac.pms.attach.form.AttachForm;
import kr.co.kmac.pms.common.repository.data.UploadFile;

public class RepositoryUtil {
	// ���ε� ��� Ȯ����
	public static final String[] PERMITTED_EXTS = new String[] { "JPG", "GIF", "DOC", "XLS", "PPT", "HWP", "TXT", "PDF", "ZIP", "ALZ", "AI", "AVI",
			"MOV", "MPG", "MPEG", "RM", "ASF", "SWF", "WMV", "BMP", "PNG", "MP3", };
	public static final String MULTIPART = "multipart/";

	public static boolean isMultiPart(HttpServletRequest request) {
		if (!"post".equals(request.getMethod().toLowerCase())) {
			return false;
		}
		String contentType = request.getContentType();
		if (contentType == null) {
			return false;
		}
		if (contentType.toLowerCase().startsWith(MULTIPART)) {
			return true;
		}
		return false;
	}

	/**
	 * ���� ����
	 * 
	 * @param filePathStr
	 * @param fileNm
	 */
	public static void deleteFile(String filePathStr, String fileNm) {
		try {
			ResourceBundle rb = ResourceBundle.getBundle("system");
			String fileUrl = rb.getString(filePathStr);

			File aFile = new File(fileUrl, fileNm);
			if (aFile.exists()) {
				aFile.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ���� ���� Ȯ��
	 * 
	 * @param filePathStr
	 * @param fileNm
	 * @return
	 */
	public static boolean existFile(String filePathStr, String fileNm) {
		boolean result = false;
		try {
			ResourceBundle rb = ResourceBundle.getBundle("system");
			String fileUrl = rb.getString(filePathStr);

			File aFile = new File(fileUrl, fileNm);
			if (aFile.exists()) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * ���� Ȯ���ڸ� ���ϴ� �޼ҵ����� Ȯ���ڸ� ���ϴ� �޼ҵ�
	 * 
	 * @param str
	 * @return
	 */
	public static String getExtension(String str) {
		String ext = null;
		String fileNm = str;
		int i = fileNm.lastIndexOf('.');
		if (i > 0 && i < fileNm.length() - 1) {
			ext = fileNm.substring(i).toLowerCase();
		}
		return ext;
	}

	/**
	 * ���ε� ��� Ȯ�������� �˻��Ѵ�.
	 * 
	 * @param fileName ���ε� ���ϸ�
	 * @return ���ε� ��� ����
	 */
	public static boolean isPermittedExt(String fileName) {
		boolean permittedExt = false;

		int dotIndex = fileName.lastIndexOf('.');
		String ext = fileName.substring(dotIndex + 1);

		for (int i = 0; i < PERMITTED_EXTS.length; i++) {
			if (ext.equalsIgnoreCase(PERMITTED_EXTS[i])) {
				permittedExt = true;
				break;
			}
		}

		return permittedExt;
	}

	/**
	 * ������ ��ο� ������ ���ϸ����� �ٲ۴�.
	 * 
	 * @param path ���ε� ���
	 * @param originFileName ���ε� ���ϸ�
	 * @return �����ӵ� ���ϸ�
	 * @throws Exception �����ӿ� �����ϴ� ���
	 */
	public static String rename(String path, String originFileName) throws Exception {
		// ������ ���� �÷���
		boolean renamed = false;

		// ���ε� ���丮 Ȯ��
		File dir = new File(path);
		if (!dir.exists() || !dir.isDirectory()) {
			throw new Exception("�ش� ���丮�� �����ϴ�.");
		}

		File file = new File(dir, originFileName);

		int i = 0;
		while (i < 1000) {
			// ���ϸ��� �ߺ��Ǵ� ��� ���ϸ��� �ٲ۴�.
			// ���ϸ��� xxx_i.xxx�� �ٲ۴�.
			if (i > 0) {
				String fileName = originFileName;

				int dotIndex = fileName.lastIndexOf('.');
				String pre = fileName.substring(0, dotIndex);
				pre += "_" + i;
				String dotAndExt = fileName.substring(dotIndex);
				fileName = pre + dotAndExt;

				file = new File(path, fileName);
			}

			// ���ϸ��� ������ ���, ������ �������� üũ�ϰ� ������ �������´�.
			if (!file.exists()) {
				renamed = true;
				break;
			}

			i++;
		}

		if (!renamed) {
			throw new Exception("���� �����ӿ� �����߽��ϴ�.");
		}

		return file.getName();
	}

	/**
	 * ������ ������ ��ο� ����.
	 * 
	 * @param file ���ε� ����
	 * @param path ���ε� ���丮
	 * @param newFileName �����ӵ� ���ϸ�
	 * @return ���ε�� ��������
	 * @throws IOException ��Ʈ���� �а� �� �� ���ܰ� �߻��Ǵ� ���
	 */
	public static void writeFile(UploadFile file) throws IOException {
		// ����� ��Ʈ��
		InputStream is = null;
		OutputStream os = null;

		try {
			File uploadDir = new File(file.getFilePath());
			if (!uploadDir.exists()) {
				uploadDir.mkdirs();
			}
			// ����� ��Ʈ���� ����.
			is = new BufferedInputStream(file.getInputStream());
			os = new BufferedOutputStream(new FileOutputStream(file.getFilePath() + "/" + file.getFileId()));

			int b = 0;
			while ((b = is.read()) != -1) {
				os.write(b);
			}
		} finally {
			if (is != null) {
				try {
					is.close();
				} finally {
				}
			}

			if (os != null) {
				try {
					os.flush();
				} finally {
				}
				try {
					os.close();
				} finally {
				}
			}
		}
	}

	public static String getFileId() {
		return UUID.randomUUID().toString();
	}
	
	/**
	 * ����÷�ν� AttachForm�� ��� ���� ���ϴ� Action���� ���
	 * 
	 * @param HttpServletRequest
	 * @return AttachForm
	 */
	public static AttachForm initAttachForm(HttpServletRequest request) throws Exception {
		String[] attachFileId = request.getParameterValues("attachFileId");
		AttachForm attachForm = new AttachForm();
		if(attachFileId.length > 0) {
			String[] attachSeq = new String[attachFileId.length];
			for(int i = 0 ; i < attachFileId.length ; i++){
				attachSeq[i] = Integer.toString(i);
			}
			attachForm.setAttachSeq(attachSeq);
			attachForm.setAttachFileId(attachFileId);
			
			attachForm.setAttachFileName(request.getParameterValues("attachFileName"));
			attachForm.setAttachFilePath(request.getParameterValues("attachFilePath"));

		}
		return attachForm;
	}
}
