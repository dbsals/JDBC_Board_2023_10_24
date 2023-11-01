package com.ym.jdbc.controller;

import com.ym.jdbc.Article;
import com.ym.jdbc.Rq;
import com.ym.jdbc.container.Container;
import com.ym.jdbc.service.ArticleService;
import com.ym.jdbc.util.DBUtil;
import com.ym.jdbc.util.SecSql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ArticleController extends Controller {
  private ArticleService articleService;

  public ArticleController() {
    articleService = Container.articleService;
    scanner = Container.scanner;
  }

  public void doWrite() {
    System.out.println("== 게시물 등록 ==");

    System.out.printf("제목 : ");
    String title = scanner.nextLine();

    System.out.printf("내용 : ");
    String content = scanner.nextLine();

    int id = articleService.write(title, content);

    System.out.printf("%d번 게시물을 작성하였습니다.\n", id);
  }

  public void showList() {
    List<Article> articles = new ArrayList<>();

    List<Map<String, Object>> articlesListMap = articleService.getArticlesListMap();

    for (Map<String, Object> articleMap : articlesListMap) {
      articles.add(new Article(articleMap));
    }

    System.out.println("== 게시물 리스트 ==");

    if (articles.isEmpty()) {
      System.out.println("게시물이 존재하지 않습니다.");
      return;
    }

    System.out.println("번호 / 제목");

    for (Article article : articles) {
      System.out.printf("%d / %s\n", article.id, article.title);
    }
  }

  public void showDetail(Rq rq) {
    int id = rq.getIntParam("id", 0);

    System.out.println("== 게시물 상세보기 ==");

    Map<String, Object> articleMap = articleService.getArticleMap(id);

    if (articleMap.isEmpty()) {
      System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
      return;
    }

    Article article = new Article(articleMap);

    System.out.printf("번호 : %d\n", article.id);
    System.out.printf("작성날짜 : %s\n", article.regDate);
    System.out.printf("제목 : %s\n", article.title);
    System.out.printf("내용 : %s\n", article.content);
  }

  public void doModify(Rq rq) {
    int id = rq.getIntParam("id", 0);

    System.out.println("== 게시물 수정 ==");

    int articleCount = articleService.getArticleCount(id);

    if (articleCount == 0) {
      System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
      return;
    }

    System.out.printf("새 제목 : ");
    String title = scanner.nextLine();
    System.out.printf("새 내용 : ");
    String content = scanner.nextLine();

    articleService.update(id, title, content);

    System.out.printf("%d번 게시물이 수정되었습니다.\n", id);
  }

  public void doDelete(Rq rq) {
    int id = rq.getIntParam("id", 0);

    System.out.println("== 게시물 삭제 ==");

    int articleCount = articleService.getArticleCount(id);

    if (articleCount == 0) {
      System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
      return;
    }

    articleService.delete(id);

    System.out.printf("%d번 게시물이 삭제되었습니다.\n", id);
  }
}