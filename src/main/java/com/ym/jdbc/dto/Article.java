package com.ym.jdbc.dto;

import com.ym.jdbc.container.Container;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Article {
  private int id;
  private  String regDate;
  private String updateDate;
  private int memberId;
  private int boardId;
  private String title;
  private String content;
  private int hit;
  private String extra__writerName;

  public Article(Map<String, Object> articleMap) {
    this.id = (int) articleMap.get("id");
    this.regDate = (String) articleMap.get("regDate");
    this.updateDate = (String) articleMap.get("updateDate");
    this.memberId = (int) articleMap.get("memberId");
    this.boardId = (int) articleMap.get("boardId");
    this.title = (String) articleMap.get("title");
    this.content = (String) articleMap.get("content");
    this.hit = (int) articleMap.get("hit");

    if(articleMap.get("extra__writerName") != null) {
      this.extra__writerName = (String) articleMap.get("extra__writerName");
    }
  }
}