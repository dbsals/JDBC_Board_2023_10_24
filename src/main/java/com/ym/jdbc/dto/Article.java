package com.ym.jdbc.dto;

import com.ym.jdbc.container.Container;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Article {
  public int id;
  public  String regDate;
  public String updateDate;
  public String title;
  public String content;

  public Article(int id, String regDate, String updateDate, String title, String content) {
    this.id = id;
    this.regDate = regDate;
    this.updateDate = updateDate;
    this.title = title;
    this.content = content;
  }

  public Article(int id, String title, String content) {
    this.id = id;
    this.title = title;
    this.content = content;
  }

  public Article(Map<String, Object> articleMap) {
    this.id = (int) articleMap.get("id");
    this.regDate = (String) articleMap.get("regDate");
    this.updateDate = (String) articleMap.get("updateDate");
    this.title = (String) articleMap.get("title");
    this.content = (String) articleMap.get("content");
  }

  @Override
  public String toString() {
    return "Article{" +
        "id=" + id +
        ", regDate='" + regDate + '\'' +
        ", updateDate='" + updateDate + '\'' +
        ", title='" + title + '\'' +
        ", content='" + content + '\'' +
        '}';
  }
}