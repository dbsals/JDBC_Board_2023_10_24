package com.ym.jdbc.service;

import com.ym.jdbc.container.Container;
import com.ym.jdbc.repository.MemberRepository;

public class MemberService {
  public MemberRepository memberRepository;

  public MemberService() {
    memberRepository = Container.memberRepository;
  }

  public boolean isLoginDup(String loginId) {
    return memberRepository.isLoginDup(loginId);
  }

  public void join(String loginId, String loginPw, String name) {
    memberRepository.join(loginId, loginPw, name);
  }
}