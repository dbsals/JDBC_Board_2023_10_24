package com.ym.jdbc.controller;

import com.ym.jdbc.container.Container;
import com.ym.jdbc.controller.Controller;
import com.ym.jdbc.dto.Member;
import com.ym.jdbc.service.MemberService;
import com.ym.jdbc.util.DBUtil;
import com.ym.jdbc.util.SecSql;

import static com.ym.jdbc.container.Container.*;

public class MemberController extends Controller {

  private MemberService memberService;

  public MemberController() {
    memberService = Container.memberService;
    scanner = Container.scanner;
  }

  public void join() {
    String loginId;
    String loginPw;
    String loginPwConfirm;
    String name;

    System.out.println("== 회원 가입 ==");

    while (true) {
      System.out.printf("로그인 아이디 : ");
      loginId = scanner.nextLine().trim();


      boolean isLoginDup = memberService.isLoginDup(loginId);

      if (isLoginDup) {
        System.out.printf("\"%s\"(은)는 이미 사용중인 아이디입니다.\n", loginId);
        continue;
      }

      if (loginId.length() == 0) {
        System.out.println("로그인 아이디를 입력해주세요.");
        continue;
      }

      break;
    }

    while (true) {
      System.out.printf("로그인 비번 : ");
      loginPw = scanner.nextLine().trim();

      if (loginPw.length() == 0) {
        System.out.println("로그인 비번을 입력해주세요.");
        continue;
      }

      boolean loginPwConfirmIsSame = true;

      while (true) {
        System.out.printf("로그인 비번확인 : ");
        loginPwConfirm = scanner.nextLine().trim();

        if (loginPwConfirm.length() == 0) {
          System.out.println("로그인 비번확인을 입력해주세요.");
          continue;
        }

        if (loginPw.equals(loginPwConfirm) == false) {
          System.out.println("로그인 비번이 일치하지 않습니다. 다시 입력해주세요.");
          loginPwConfirmIsSame = false;
          break;
        }

        break;
      }

      if (loginPwConfirmIsSame) {
        break;
      }
    }

    // 이름 입력
    while (true) {
      System.out.printf("이름 : ");
      name = scanner.nextLine().trim();

      if (name.length() == 0) {
        System.out.println("이름를 입력해주세요.");
        continue;
      }

      break;
    }

    memberService.join(loginId, loginPw, name);

    System.out.printf("\"%s\"님 회원 가입을 환영합니다.\n", name);
  }

  public void login() {
    String loginId;
    String loginPw;

    if(Container.session.isLogined()) {
      System.out.println("현재 로그인 되어 있습니다.");
      return;
    }

    System.out.println("== 로그인 ==");

    System.out.printf("로그인 아이디 : ");
    loginId = scanner.nextLine().trim();

    if (loginId.length() == 0) {
      System.out.println("로그인 아이디를 입력해주세요.");
      return;
    }

    Member member = memberService.getMemberLoginId(loginId);

    if (member == null) {
      System.out.println("입력하신 아이디는 존재하지 않습니다.");
      return;
    }

    int loginPwtryMaxcount = 3;
    int loginPwtrycount = 0;

    while (true) {
      if(loginPwtrycount >= loginPwtryMaxcount) {
        System.out.println("비밀번호 확인 후 다음에 다시 시도해주세요.");
        break;
      }

      System.out.printf("로그인 비번 : ");
      loginPw = scanner.nextLine().trim();

      if (loginPw.length() == 0) {
        System.out.println("로그인 비번을 입력해주세요.");
        continue;
      }

      if (member.getLoginPw().equals(loginPw) == false) {
        System.out.println("비밀번호가 일치하지 않습니다.");
        loginPwtrycount++;
        continue;
      }

      System.out.printf("\"%s\"님 로그인 되었습니다.\n", member.getName());
      Container.session.login(member);

      break;
    }
  }

  public void whoami() {
    if(Container.session.loginedMember == null) {
      System.out.println("로그인 상태가 아닙니다.");
    } else {
      String loginId = Container.session.loginedMember.getLoginId();
      System.out.printf("현재 로그인 회원은 \"%s\"님 입니다.\n", loginId);
    }
  }

  public void logout() {
    if (Container.session.isLogined() == false) {
      System.out.println("이미 로그아웃 상태입니다.");
      return;
    }

    Container.session.logout();
    System.out.println("로그아웃 되었습니다.");
  }
}