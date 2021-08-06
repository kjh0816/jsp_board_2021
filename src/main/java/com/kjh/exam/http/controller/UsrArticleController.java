package com.kjh.exam.http.controller;

import java.util.List;

import com.kjh.exam.container.Container;
import com.kjh.exam.dto.Article;
import com.kjh.exam.dto.Board;
import com.kjh.exam.dto.ResultData;
import com.kjh.exam.http.Rq;
import com.kjh.exam.service.ArticleService;
import com.kjh.exam.service.BoardService;
import com.kjh.exam.util.Ut;

public class UsrArticleController extends Controller {
	private ArticleService articleService;
	private BoardService boardService;

	public void init() {
		articleService = Container.articleService;
		boardService = Container.boardService;
	}

	@Override
	public void performAction(Rq rq) {
		switch (rq.getActionMethodName()) {
		case "list":
			actionShowList(rq);
			break;
		case "detail":
			actionShowDetail(rq);
			break;
		case "write":
			actionShowWrite(rq);
			break;
		case "doWrite":
			actionDoWrite(rq);
			break;
		case "modify":
			actionShowModify(rq);
			break;
		case "doModify":
			actionDoModify(rq);
			break;
		case "doDelete":
			actionDoDelete(rq);
			break;
		default:
			rq.println("존재하지 않는 페이지 입니다.");
			break;
		}
	}

	private void actionDoDelete(Rq rq) {
		int id = rq.getIntParam("id", 0);
		String redirectUri = rq.getParam("redirectUri", "../article/list");

		if (id == 0) {
			rq.historyBack("id를 입력해주세요.");
			return;
		}

		Article article = articleService.getForPrintArticleById(rq.getLoginedMember(), id);

		if (article == null) {
			rq.historyBack(Ut.f("%d번 게시물이 존재하지 않습니다.", id));
			return;
		}

		ResultData actorCanDeleteRd = articleService.actorCanDelete(rq.getLoginedMember(), article);

		if (actorCanDeleteRd.isFail()) {
			rq.historyBack(actorCanDeleteRd.getMsg());
			return;
		}
		
		
//		관리자인 경우, 
		if(actorCanDeleteRd.getResultCode().equals("S-1")) {
			articleService.delete(id);
			
			rq.replace(Ut.f("%d번 게시물을 관리자 권한으로 삭제하였습니다.", id), redirectUri);
			return;
		}

		articleService.delete(id);

		rq.replace(Ut.f("%d번 게시물을 삭제하였습니다.", id), redirectUri);
	}

	private void actionShowDetail(Rq rq) {
		int id = rq.getIntParam("id", 0);

		if (id == 0) {
			rq.historyBack("id를 입력해주세요.");
			return;
		}

		Article article = articleService.getForPrintArticleById(rq.getLoginedMember(), id);

		if (article == null) {
			rq.historyBack(Ut.f("%d번 게시물이 존재하지 않습니다.", id));
			return;
		}

		rq.setAttr("article", article);
		rq.jsp("usr/article/detail");
	}

	private void actionShowList(Rq rq) {
		String searchKeywordTypeCode = rq.getParam("searchKeywordTypeCode", "title,body");
		String searchKeyword = rq.getParam("searchKeyword", "");

		int itemsCountInAPage = 10;
		int page = rq.getIntParam("page", 1);
		int boardId = rq.getIntParam("boardId", 0);

		int totalItemsCount = articleService.getArticlesCount(boardId, searchKeywordTypeCode, searchKeyword);
		List<Article> articles = articleService.getForPrintArticles(rq.getLoginedMember(), boardId,
				searchKeywordTypeCode, searchKeyword, itemsCountInAPage, page);

		int totalPage = (int) Math.ceil((double) totalItemsCount / itemsCountInAPage);

		Board board = boardService.getBoardById(boardId);

		rq.setAttr("board", board);

		rq.setAttr("searchKeywordTypeCode", searchKeywordTypeCode);
		rq.setAttr("page", page);
		rq.setAttr("boardId", boardId);
		rq.setAttr("totalPage", totalPage);
		rq.setAttr("totalItemsCount", totalItemsCount);
		rq.setAttr("articles", articles);
		rq.jsp("usr/article/list");
	}

	private void actionDoWrite(Rq rq) {
		int boardId = rq.getIntParam("boardId", 0);
		int memberId = rq.getLoginedMemberId();
		String title = rq.getParam("title", "");
		String body = rq.getParam("body", "");
		String redirectUri = rq.getParam("redirectUri", "../article/list");

		if (boardId == 0) {
			rq.historyBack("boardId를 입력해주세요.");
			return;
		}

		if (title.length() == 0) {
			rq.historyBack("title을 입력해주세요.");
			return;
		}

		if (body.length() == 0) {
			rq.historyBack("body를 입력해주세요.");
			return;
		}
		
//		관리자가 아닌 회원이 파라미터 변조를 통해 공지사항 게시판에 글을 쓰려하는 경우
		if(rq.getLoginedMember().getAuthLevel() != 7 && boardId == 1) {
			
			rq.historyBack("잘못된 접근입니다.");
			return;
		}

		ResultData writeRd = articleService.write(boardId, memberId, title, body);

		int id = (int) writeRd.getBody().get("id");

		redirectUri = redirectUri.replace("[NEW_ID]", id + "");

		rq.replace(writeRd.getMsg(), redirectUri);
	}

	private void actionShowWrite(Rq rq) {
		rq.jsp("usr/article/write");
	}

	private void actionDoModify(Rq rq) {
		int id = rq.getIntParam("id", 0);
		String title = rq.getParam("title", "");
		String body = rq.getParam("body", "");
		String redirectUri = rq.getParam("redirectUri", Ut.f("../article/detail?id=%d", id));

		if (id == 0) {
			rq.historyBack("id를 입력해주세요.");
			return;
		}

		if (title.length() == 0) {
			rq.historyBack("title을 입력해주세요.");
			return;
		}

		if (body.length() == 0) {
			rq.historyBack("body를 입력해주세요.");
			return;
		}

		Article article = articleService.getForPrintArticleById(rq.getLoginedMember(), id);

		if (article == null) {
			rq.historyBack(Ut.f("%d번 게시물이 존재하지 않습니다.", id));
			return;
		}

		ResultData actorCanModifyRd = articleService.actorCanModify(rq.getLoginedMember(), article);

		if (actorCanModifyRd.isFail()) {
			rq.historyBack(actorCanModifyRd.getMsg());
			return;
		}

		ResultData modifyRd = articleService.modify(id, title, body);

		rq.replace(modifyRd.getMsg(), redirectUri);
	}

	private void actionShowModify(Rq rq) {
		int id = rq.getIntParam("id", 0);

		if (id == 0) {
			rq.historyBack("id를 입력해주세요.");
			return;
		}

		Article article = articleService.getForPrintArticleById(rq.getLoginedMember(), id);

		ResultData actorCanModifyRd = articleService.actorCanModify(rq.getLoginedMember(), article);

		if (actorCanModifyRd.isFail()) {
			rq.historyBack(actorCanModifyRd.getMsg());
			return;
		}

		if (article == null) {
			rq.historyBack(Ut.f("%d번 게시물이 존재하지 않습니다.", id));
			return;
		}

		rq.setAttr("article", article);
		rq.jsp("usr/article/modify");
	}
}
