package com.kjh.exam.container;

import java.util.ArrayList;
import java.util.List;

import com.kjh.exam.app.App;
import com.kjh.exam.http.controller.AdmHomeController;
import com.kjh.exam.http.controller.UsrArticleController;
import com.kjh.exam.http.controller.UsrHomeController;
import com.kjh.exam.http.controller.UsrMemberController;
import com.kjh.exam.interceptor.BeforeActionInterceptor;
import com.kjh.exam.interceptor.NeedAdminInterceptor;
import com.kjh.exam.interceptor.NeedLoginInterceptor;
import com.kjh.exam.interceptor.NeedLogoutInterceptor;
import com.kjh.exam.repository.ArticleRepository;
import com.kjh.exam.repository.BoardRepository;
import com.kjh.exam.repository.MemberRepository;
import com.kjh.exam.service.ArticleService;
import com.kjh.exam.service.BoardService;
import com.kjh.exam.service.EmailService;
import com.kjh.exam.service.MemberService;

public class Container {
	private static List<ContainerComponent> containerComponents;

	public static App app;

	public static BeforeActionInterceptor beforeActionInterceptor;
	public static NeedLoginInterceptor needLoginInterceptor;
	public static NeedLogoutInterceptor needLogoutInterceptor;
	public static NeedAdminInterceptor needAdminInterceptor;

	public static ArticleRepository articleRepository;
	public static ArticleService articleService;
	public static UsrArticleController usrArticleController;

	public static MemberRepository memberRepository;
	public static MemberService memberService;
	public static UsrMemberController usrMemberController;

	public static UsrHomeController usrHomeController;

	public static BoardRepository boardRepository;
	public static BoardService boardService;

	public static AdmHomeController admHomeController;
	
	public static EmailService emailService;

	public static void init() {
		containerComponents = new ArrayList<>();

		// 의존성 세팅 시작
		app = addContainerComponent(new App());
		memberRepository = addContainerComponent(new MemberRepository());
		boardRepository = addContainerComponent(new BoardRepository());
		articleRepository = addContainerComponent(new ArticleRepository());

		memberService = addContainerComponent(new MemberService());
		boardService = addContainerComponent(new BoardService());
		articleService = addContainerComponent(new ArticleService());

		beforeActionInterceptor = addContainerComponent(new BeforeActionInterceptor());
		needLoginInterceptor = addContainerComponent(new NeedLoginInterceptor());
		needLogoutInterceptor = addContainerComponent(new NeedLogoutInterceptor());
		needAdminInterceptor = addContainerComponent(new NeedAdminInterceptor());

		usrMemberController = addContainerComponent(new UsrMemberController());
		usrArticleController = addContainerComponent(new UsrArticleController());
		usrHomeController = addContainerComponent(new UsrHomeController());

		admHomeController = addContainerComponent(new AdmHomeController());
		
		emailService = addContainerComponent(new EmailService());

		// 객체 초기화
		// 초기화
		for (ContainerComponent containerComponent : containerComponents) {
			containerComponent.init();
		}
	}

	private static <T> T addContainerComponent(ContainerComponent containerComponent) {
		containerComponents.add(containerComponent);

		return (T) containerComponent;
	}
}
