package com.ym.jdbc.container;

import com.ym.jdbc.controller.ArticleController;
import com.ym.jdbc.controller.MemberController;

import java.util.Scanner;

public class Container {
  public static Scanner scanner;

  public static MemberController memberController;
  public static ArticleController articleController;

  static {
    scanner = new Scanner(System.in);

    memberController = new MemberController();
    articleController = new ArticleController();
  }
}
