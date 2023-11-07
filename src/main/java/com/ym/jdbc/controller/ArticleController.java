package com.ym.jdbc.controller;

import com.ym.jdbc.dto.Article;
import com.ym.jdbc.Rq;
import com.ym.jdbc.container.Container;
import com.ym.jdbc.service.ArticleService;

import java.util.List;

public class ArticleController extends Controller {
  private ArticleService articleService;

  public ArticleController() {
    articleService = Container.articleService;
    scanner = Container.scanner;
  }

  public void doWrite() {
    if (Container.session.isLogined() == false) {
      System.out.println("로그인 후 이용해주세요.");
      return;
    }

    System.out.println("== 게시물 등록 ==");

    System.out.printf("제목 : ");
    String title = scanner.nextLine();

    System.out.printf("내용 : ");
    String content = scanner.nextLine();

    int memberId = Container.session.loginedMember.getId();
    int id = articleService.write(memberId, title, content);

    System.out.printf("%d번 게시물을 작성하였습니다.\n", id);
  }

  public void showList(Rq rq) {
    int page = rq.getIntParam("page", 1);
    String searchKeyword = rq.getParam("searchKeyword", "");

    int pageItemCount = 10;

    List<Article> articles = articleService.getForPrintArticles(page, pageItemCount, searchKeyword);

    if (articles.isEmpty()) {
      System.out.println("게시물이 존재하지 않습니다.");
      return;
    }

    System.out.println("== 게시물 리스트 ==");

    System.out.println("번호 / 작성날짜 / 제목 / 작성자");

    for (Article article : articles) {
      System.out.printf("%d / %s / %s / %s\n", article.getId(), article.getRegDate(), article.getTitle(), article.getExtra__writerName());
    }
  }

  public void showDetail(Rq rq) {
    int id = rq.getIntParam("id", 0);

    System.out.println("== 게시물 상세보기 ==");

    articleService.increaseHit(id);
    Article article = articleService.getArticleById(id);

    if(article == null) {
      System.out.printf("%d번 게시물은 존재하지 않습니다.", id);
      return;
    }

    System.out.printf("번호 : %d\n", article.getId());
    System.out.printf("작성날짜 : %s\n", article.getRegDate());
    System.out.printf("수정날짜 : %s\n", article.getUpdateDate());
    System.out.printf("작성자 : %s\n", article.getExtra__writerName());
    System.out.printf("조회수 : %d\n", article.getHit());
    System.out.printf("제목 : %s\n", article.getTitle());
    System.out.printf("내용 : %s\n", article.getContent());
  }

  public void doModify(Rq rq) {
    if (Container.session.isLogined() == false) {
      System.out.println("로그인 후 이용해주세요.");
      return;
    }

    int id = rq.getIntParam("id", 0);

    System.out.println("== 게시물 수정 ==");

    Article article = articleService.getArticleById(id);

    if (article == null) {
      System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
      return;
    }

    if(article.getMemberId() != Container.session.loginedMember.getId()) {
      System.out.println("권한이 없습니다.");
      return;
    }

    System.out.println("== 게시물 수정 ==");

    System.out.printf("새 제목 : ");
    String title = scanner.nextLine();
    System.out.printf("새 내용 : ");
    String content = scanner.nextLine();

    articleService.update(id, title, content);

    System.out.printf("%d번 게시물이 수정되었습니다.\n", id);
  }

  public void doDelete(Rq rq) {
    if (Container.session.isLogined() == false) {
      System.out.println("로그인 후 이용해주세요.");
      return;
    }

    int id = rq.getIntParam("id", 0);

    Article article = articleService.getArticleById(id);

    if (article == null) {
      System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
      return;
    }

    if(article.getMemberId() != Container.session.loginedMember.getId()) {
      System.out.println("권한이 없습니다.");
      return;
    }

    System.out.println("== 게시물 삭제 ==");

    articleService.delete(id);

    System.out.printf("%d번 게시물이 삭제되었습니다.\n", id);
  }
}