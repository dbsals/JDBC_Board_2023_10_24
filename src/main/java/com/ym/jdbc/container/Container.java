package com.ym.jdbc.container;

import com.ym.jdbc.controller.ArticleController;
import com.ym.jdbc.controller.MemberController;
import com.ym.jdbc.repository.ArticleRepository;
import com.ym.jdbc.repository.MemberRepository;
import com.ym.jdbc.service.ArticleService;
import com.ym.jdbc.service.MemberService;

import java.sql.Connection;
import java.util.Scanner;

public class Container {
  public static Scanner scanner;
  public static MemberRepository memberRepository;
  public static ArticleRepository articleRepository;

  public static MemberService memberService;
  public static ArticleService articleService;

  public static MemberController memberController;
  public static ArticleController articleController;

  public static Connection conn;

  static {
    scanner = new Scanner(System.in);

    memberRepository = new MemberRepository();
    articleRepository = new ArticleRepository();

    memberService = new MemberService();
    articleService = new ArticleService();

    memberController = new MemberController();
    articleController = new ArticleController();
  }
}