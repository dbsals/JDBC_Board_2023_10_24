package com.ym.jdbc.service;

import com.ym.jdbc.container.Container;
import com.ym.jdbc.dto.Member;
import com.ym.jdbc.repository.MemberRepository;

public class MemberService {
  public MemberRepository memberRepository;

  public MemberService() {
    memberRepository = Container.memberRepository;
  }

  public boolean isLoginIdDup(String loginId) {
    return memberRepository.isLoginIdDup(loginId);
  }

  public boolean isLoginEmailDup(String email) {
    return memberRepository.isLoginEmailDup(email);
  }

  public void join(String loginId, String loginPw, String name, String email) {
    memberRepository.join(loginId, loginPw, name, email);
  }


  public Member getMemberLoginId(String loginId) {
    return memberRepository.getMemberByLoginId(loginId);
  }

  public void update(String newLoginPw, int id) {
    memberRepository.update(newLoginPw, id);
  }
}