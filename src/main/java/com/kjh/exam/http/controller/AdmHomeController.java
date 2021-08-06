package com.kjh.exam.http.controller;

import com.kjh.exam.http.Rq;
import com.kjh.exam.util.Ut;

public class AdmHomeController extends Controller {
	public void init() {

	}

	@Override
	public void performAction(Rq rq) {
		switch (rq.getActionMethodName()) {
		case "main":
			actionShowMain(rq);
			break;
		default:
			rq.println("존재하지 않는 페이지 입니다.");
			break;
		}
	}

	private void actionShowMain(Rq rq) {
		rq.print("관리자 페이지 입니다.");
	}
}
