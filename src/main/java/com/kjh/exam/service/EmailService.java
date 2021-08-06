package com.kjh.exam.service;

import com.kjh.exam.app.App;
import com.kjh.exam.container.Container;
import com.kjh.exam.container.ContainerComponent;
import com.kjh.exam.util.Ut;

public class EmailService implements ContainerComponent {
	@Override
	public void init() {
		
	}

	public int notify(String to, String title, String body) {
		App app = Container.app;
		String smtpGmailId = app.getSmtpGmailId();
		String smtpGmailPw = app.getSmtpGmailPw();
		String from = "no-reply@no-reply.com";
		String fromName = app.getNotifyEmailFromName();
		
		return Ut.sendMail(smtpGmailId, smtpGmailPw, from, fromName, to, title, body);
	}
}
