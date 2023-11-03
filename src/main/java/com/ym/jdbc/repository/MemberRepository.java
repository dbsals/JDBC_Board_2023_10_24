package com.ym.jdbc.repository;

import com.ym.jdbc.container.Container;
import com.ym.jdbc.dto.Member;
import com.ym.jdbc.util.DBUtil;
import com.ym.jdbc.util.SecSql;

import java.util.Map;

public class MemberRepository {
  public boolean isLoginDup(String loginId) {
    SecSql sql = new SecSql();
    sql.append("SELECT COUNT(*) > 0");
    sql.append("FROM `member`");
    sql.append("WHERE loginId = ?", loginId);

    return DBUtil.selectRowBooleanValue(Container.conn, sql);
  }

  public void join(String loginId, String loginPw, String name, String email) {
    SecSql sql = new SecSql();
    sql.append("INSERT INTO `member`");
    sql.append("SET regDate = NOW()");
    sql.append(", updateDate = NOW()");
    sql.append(", loginId = ?", loginId);
    sql.append(", loginPw = ?", loginPw);
    sql.append(", name = ?", name);
    sql.append(", email = ?", email);

    DBUtil.insert(Container.conn, sql);
  }

  public Member getMemberByLoginId(String loginId) {
    SecSql sql = new SecSql();
    sql.append("SELECT *");
    sql.append("FROM `member`");
    sql.append("WHERE loginId = ?", loginId);

    Map<String, Object> memberMap = DBUtil.selectRow(Container.conn, sql);

    if(memberMap.isEmpty()) {
      return null;
    }

    return new Member(memberMap);
  }

  public void update(String newLoginPw, int id) {
    SecSql sql = new SecSql();
    sql.append("UPDATE `member`");
    sql.append("SET loginPw = ?", newLoginPw);
    sql.append("WHERE id = ?", id);

    DBUtil.update(Container.conn, sql);
  }

  public boolean isLoginEmailDup(String email) {
    SecSql sql = new SecSql();
    sql.append("SELECT COUNT(*) > 0");
    sql.append("FROM `member`");
    sql.append("WHERE email = ?", email);

    return DBUtil.selectRowBooleanValue(Container.conn, sql);
  }
}