package com.kjh.exam.interceptor;

import com.kjh.exam.http.Rq;

public class NeedLogoutInterceptor extends Interceptor {

	public void init() {

	}


	@Override
	public boolean runBeforeAction(Rq rq) {
//		로그인된 상태로 접근이 불가한 페이지들
		switch (rq.getActionPath()) {
		case "/usr/member/login":
		case "/usr/member/doLogin":
		case "/usr/member/join":
		case "/usr/member/doJoin":
		case "/usr/member/findLoginId":
		case "/usr/member/doFindLoginId":
		case "/usr/member/findLoginPw":
		case "/usr/member/doFindLoginPw":
			if (rq.isLogined()) {
				rq.replace("로그아웃 후 이용해주세요.", "../home/main");

				return false;
			}
		}

		return true;
	}

}
