package com.ym.jdbc.repository;

import com.ym.jdbc.container.Container;
import com.ym.jdbc.dto.Article;
import com.ym.jdbc.util.DBUtil;
import com.ym.jdbc.util.SecSql;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArticleRepository {
  public int write(int memberId, String title, String content) {
    SecSql sql = new SecSql();
    sql.append("INSERT INTO article");
    sql.append("SET regDate = NOW()");
    sql.append(", updateDate = NOW()");
    sql.append(", memberId = ?", memberId);
    sql.append(", title = ?", title);
    sql.append(", `content` = ?", content);

    int id = DBUtil.insert(Container.conn, sql);

    return id;
  }

  public List<Article> getForPrintArticles(Map<String, Object> args) {

    String searchKeyword = "";

    if(args.containsKey("searchKeyword")) {
      searchKeyword = (String) args.get("searchKeyword");
    }

    int limitFrom = -1;
    int limitTake = -1;

    if(args.containsKey("limitFrom")) {
      limitFrom = (int) args.get("limitFrom");
    }

    if(args.containsKey("limitTake")) {
      limitTake = (int) args.get("limitTake");
    }

    SecSql sql = new SecSql();
    sql.append("SELECT A.*, M.name AS extra__writerName");
    sql.append("FROM article AS A");
    sql.append("INNER JOIN `member` AS M");
    sql.append("ON A.memberId = M.id");
    if(searchKeyword.length() > 0) {
      sql.append("WHERE A.title LIKE CONCAT('%', ?, '%')", searchKeyword);
    }

    sql.append("ORDER BY id DESC");

    if(limitFrom != -1) {
      sql.append("LIMIT ?, ?", limitFrom, limitTake);
    }

    List<Article> articles = new ArrayList<>();

    List<Map<String, Object>> articlesMap = DBUtil.selectRows(Container.conn, sql);

    for(Map<String, Object> articleMap : articlesMap) {
      articles.add(new Article(articleMap));
    }

    return articles;
  }

  public int getArticleCount(int id) {
    SecSql sql = new SecSql();
    sql.append("SELECT COUNT(*) AS cnt");
    sql.append("FROM article");
    sql.append("WHERE id = ?", id);

    return DBUtil.selectRowIntValue(Container.conn, sql);
  }

  public void update(int id, String title, String content) {
    SecSql sql = new SecSql();
    sql.append("UPDATE article");
    sql.append("set updateDate = NOW()");
    sql.append(", title = ?", title);
    sql.append(", `content` = ?", content);
    sql.append("WHERE id = ?", id);

    DBUtil.update(Container.conn, sql);
  }

  public void delete(int id) {
    SecSql sql = new SecSql();
    sql.append("DELETE FROM article");
    sql.append(" WHERE id = ?", id);

    DBUtil.delete(Container.conn, sql);
  }

  public Article getArticleById(int id) {
    SecSql sql = new SecSql();
    sql.append("SELECT A.*");
    sql.append(", M.name AS extra__writerName");
    sql.append("FROM article AS A");
    sql.append("INNER JOIN `member` AS M");
    sql.append("ON A.memberId = M.id");
    sql.append("WHERE A.id = ?", id);

    Map<String, Object> articleMap = DBUtil.selectRow(Container.conn, sql);

    if(articleMap.isEmpty()) {
      return null;
    }

    return new Article(articleMap);
  }

  public void increaseHit(int id) {
    SecSql sql = new SecSql();
    sql.append("UPDATE article");
    sql.append("SET hit = hit + 1");
    sql.append("WHERE id = ?", id);

    DBUtil.update(Container.conn, sql);
  }
}