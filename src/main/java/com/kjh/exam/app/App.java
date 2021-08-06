package com.kjh.exam.app;

import com.kjh.exam.container.Container;
import com.kjh.exam.container.ContainerComponent;
import com.kjh.exam.util.Ut;
import com.kjh.mysqliutil.MysqlUtil;

import lombok.Getter;

public class App implements ContainerComponent {
	@Getter
	private boolean ready = false;
	private String smtpGmailPw;

	@Override
	public void init() {
		// 필수적으로 로딩되어야 하는 내용 불러오기
		smtpGmailPw = Ut.getFileContents("c:/work/kjh/SmtpGmailPw.txt");

		if (smtpGmailPw != null && smtpGmailPw.trim().length() > 0) {
			ready = true;
		}
	}

	public static boolean isDevMode() {
		// 웹루트에 따라 나뉘며, 로컬 개발 환경이다.
		return true;
	}
	
	private static boolean isProductMode() {
		// 웹루트에 따라 나뉘며, 서버 개발 환경이다.
		return isDevMode() == false;
	}

	// 정적 요소 세팅
	public static void start() {
		// DB 세팅
		MysqlUtil.setDBInfo("localhost", "sbsst", "1234", "jsp_board");
		MysqlUtil.setDevMode(isDevMode());

		// 공용 객체 세팅
		Container.init();
	}

	public String getSmtpGmailId() {
		return "readshot2@gmail.com";
	}

	public String getSmtpGmailPw() {
		return smtpGmailPw;
	}

	public String getSiteName() {
		return "레몬 커뮤니티";
	}
	
	public String getBaseUri() {
		String appUri = getSiteProtocol() + "://" + getSiteDomain();

		if (getSitePort() != 80 && getSitePort() != 443) {
			appUri += ":" + getSitePort();
		}

		if (getContextName().length() > 0) {
			appUri += "/" + getContextName();
		}

		return appUri;
	}

	private String getContextName() {
		if (isProductMode()) {
			return "";
		}

		return "2021_jsp_board";
	}

	private int getSitePort() {
		return 8082;
	}

	private String getSiteDomain() {
		return "localhost";
	}

	private String getSiteProtocol() {
		return "http";
	}

	public String getLoginUri() {
		return getBaseUri() + "/usr/member/login";
	}

	public String getNotifyEmailFromName() {
		return "레몬 커뮤니티 알림봇";
	}
}
