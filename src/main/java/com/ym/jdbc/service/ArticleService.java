package com.ym.jdbc.service;

import com.ym.jdbc.container.Container;
import com.ym.jdbc.dto.Article;
import com.ym.jdbc.repository.ArticleRepository;

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

public List<Article> getArticles() {
    return articleRepository.getArticles();
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
}