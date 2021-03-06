package com.kjh.exam.interceptor;

import com.kjh.exam.http.Rq;

public class NeedLoginInterceptor extends Interceptor {

	public void init() {

	}

	@Override
	public boolean runBeforeAction(Rq rq) {
		if (rq.getControllerTypeName().equals("usr") == false) {
			return true;
		}
//		로그인된 상태에서 접근할 수 있는 페이지
		switch (rq.getActionPath()) {
		case "/usr/article/list":
		case "/usr/article/detail":
		case "/usr/home/main":
		case "/usr/home/doSendMail":
		case "/usr/member/login":
		case "/usr/member/doLogout":
		case "/usr/member/doLogin":
		case "/usr/member/join":
		case "/usr/member/doJoin":
		case "/usr/member/findLoginId":
		case "/usr/member/doFindLoginId":
		case "/usr/member/findLoginPw":
		case "/usr/member/doFindLoginPw":
		case "/usr/member/modify":
		case "/usr/member/doModify":
		case "/usr/member/checkPw":
		case "/usr/member/doCheckPw":
			return true;
		}

		if (rq.isNotLogined()) {
			rq.replace("로그인 후 이용해주세요.", "../member/login?afterLoginUri=" + rq.getEncodedAfterLoginUri());

			return false;
		}

		return true;
	}

}
