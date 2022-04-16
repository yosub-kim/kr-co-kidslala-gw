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
	// 업로드 허용 확장자
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
	 * 파일 삭제
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
	 * 파일 존재 확인
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
	 * 파일 확장자를 구하는 메소드파일 확장자를 구하는 메소드
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
	 * 업로드 허용 확장자인지 검사한다.
	 * 
	 * @param fileName 업로드 파일명
	 * @return 업로드 허용 여부
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
	 * 지정한 경로에 유일한 파일명으로 바꾼다.
	 * 
	 * @param path 업로드 경로
	 * @param originFileName 업로드 파일명
	 * @return 리네임된 파일명
	 * @throws Exception 리네임에 실패하는 경우
	 */
	public static String rename(String path, String originFileName) throws Exception {
		// 리네임 성공 플래그
		boolean renamed = false;

		// 업로드 디렉토리 확인
		File dir = new File(path);
		if (!dir.exists() || !dir.isDirectory()) {
			throw new Exception("해당 디렉토리가 없습니다.");
		}

		File file = new File(dir, originFileName);

		int i = 0;
		while (i < 1000) {
			// 파일명이 중복되는 경우 파일명을 바꾼다.
			// 파일명을 xxx_i.xxx로 바꾼다.
			if (i > 0) {
				String fileName = originFileName;

				int dotIndex = fileName.lastIndexOf('.');
				String pre = fileName.substring(0, dotIndex);
				pre += "_" + i;
				String dotAndExt = fileName.substring(dotIndex);
				fileName = pre + dotAndExt;

				file = new File(path, fileName);
			}

			// 파일명이 유일한 경우, 리네임 성공으로 체크하고 루프를 빠져나온다.
			if (!file.exists()) {
				renamed = true;
				break;
			}

			i++;
		}

		if (!renamed) {
			throw new Exception("파일 리네임에 실패했습니다.");
		}

		return file.getName();
	}

	/**
	 * 파일을 지정한 경로에 쓴다.
	 * 
	 * @param file 업로드 파일
	 * @param path 업로드 디렉토리
	 * @param newFileName 리네임된 파일명
	 * @return 업로드된 파일정보
	 * @throws IOException 스트림을 읽고 쓸 때 예외가 발생되는 경우
	 */
	public static void writeFile(UploadFile file) throws IOException {
		// 입출력 스트림
		InputStream is = null;
		OutputStream os = null;

		try {
			File uploadDir = new File(file.getFilePath());
			if (!uploadDir.exists()) {
				uploadDir.mkdirs();
			}
			// 입출력 스트림을 연다.
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
	 * 파일첨부시 AttachForm을 상속 받지 못하는 Action에서 사용
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
