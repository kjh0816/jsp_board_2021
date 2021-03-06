package com.kjh.exam.http.controller;

import com.kjh.exam.app.App;
import com.kjh.exam.container.Container;
import com.kjh.exam.http.Rq;
import com.kjh.exam.util.Ut;

public class UsrHomeController extends Controller {
	public void init() {
		
	}
	
	@Override
	public void performAction(Rq rq) {
		switch (rq.getActionMethodName()) {
		case "main":
			actionShowMain(rq);
			break;
		case "doSendMail":
			actionDoSendMail(rq);
			break;
		default:
			rq.println("존재하지 않는 페이지 입니다.");
			break;
		}
	}

	private void actionShowMain(Rq rq) {
		rq.jsp("usr/home/main");
	}

	private void actionDoSendMail(Rq rq) {
		App app = Container.app;
		Ut.sendMail(app.getSmtpGmailId(), app.getSmtpGmailPw(), "no-reply@lemon-cm.com", "레몬 커뮤니티 알림", "jangka512@gmail.com", "제목ㅋㅋㅋ", "내용 ㅋㅋㅋ");
	}
}
