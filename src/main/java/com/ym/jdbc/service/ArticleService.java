package com.ym.jdbc.service;

import com.ym.jdbc.container.Container;
import com.ym.jdbc.dto.Article;
import com.ym.jdbc.repository.ArticleRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArticleService {
  private ArticleRepository articleRepository;

  public ArticleService() {
    articleRepository = Container.articleRepository;
  }

  public int write(int memberId, String title, String content) {
    return articleRepository.write(memberId, title, content);
  }

  public List<Article> getForPrintArticles(int page, int pageItemCount, String searchKeyword) {
    int limitFrom = (page - 1) * pageItemCount;
    int limitTake = pageItemCount;

    Map<String, Object> args = new HashMap<>();
    args.put("searchKeyword", searchKeyword);
    args.put("limitFrom", limitFrom);
    args.put("limitTake", limitTake);

    return articleRepository.getForPrintArticles(args);
}

  public int getArticleCount(int id) {
    return  articleRepository.getArticleCount(id);
  }

  public void update(int id, String title, String content) {
    articleRepository.update(id, title, content);
  }

  public void delete(int id) {
    articleRepository.delete(id);
  }

  public Article getArticleById(int id) {
    return articleRepository.getArticleById(id);
  }

  public void increaseHit(int id) {
    articleRepository.increaseHit(id);
  }
}