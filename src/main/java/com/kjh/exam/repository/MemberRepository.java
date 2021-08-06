package com.kjh.exam.repository;

import com.kjh.exam.container.ContainerComponent;
import com.kjh.exam.dto.Member;
import com.kjh.mysqliutil.MysqlUtil;
import com.kjh.mysqliutil.SecSql;

public class MemberRepository implements ContainerComponent {
	public void init() {

	}

	public Member getMemberByLoginId(String loginId) {
		SecSql sql = new SecSql();
		sql.append("SELECT M.*");
		sql.append("FROM member AS M");
		sql.append("WHERE M.loginId = ?", loginId);

		return MysqlUtil.selectRow(sql, Member.class);
	}

	public Member getMemberByNameAndEmail(String name, String email) {
		SecSql sql = new SecSql();
		sql.append("SELECT M.*");
		sql.append("FROM member AS M");
		sql.append("WHERE M.name = ?", name);
		sql.append("AND M.email = ?", email);
		sql.append("LIMIT 1");

		return MysqlUtil.selectRow(sql, Member.class);
	}

	public int join(String loginId, String loginPw, String name, String nickname, String cellphoneNo, String email) {
		SecSql sql = new SecSql();
		sql.append("INSERT INTO `member`");
		sql.append("SET regDate = NOW()");
		sql.append(", updateDate = NOW()");
		sql.append(", loginId = ?", loginId);
		sql.append(", loginPw = ?", loginPw);
		sql.append(", name = ?", name);
		sql.append(", nickname = ?", nickname);
		sql.append(", cellphoneNo = ?", cellphoneNo);
		sql.append(", email = ?", email);

		int id = MysqlUtil.insert(sql);

		return id;
	}

	public void modifyPassword(int id, String loginPw) {
		SecSql sql = new SecSql();
		sql.append("UPDATE `member`");
		sql.append("SET updateDate = NOW()");
		sql.append(", loginPw = ?", loginPw);
		sql.append("WHERE id = ?", id);

		MysqlUtil.update(sql);
	}

	public void modify(int loginedMemberId, String loginPw, String name, String nickname, String cellphoneNo,
			String email) {
		SecSql sql = new SecSql();
		sql.append("UPDATE `member`");
		sql.append("SET updateDate = NOW()");
		sql.append(", loginPw = ?", loginPw);
		sql.append(", name = ?", name);
		sql.append(", nickname = ?", nickname);
		sql.append(", cellphoneNo = ?", cellphoneNo);
		sql.append(", email = ?", email);
		sql.append("WHERE id = ?", loginedMemberId);

		MysqlUtil.update(sql);
		
	}
}
