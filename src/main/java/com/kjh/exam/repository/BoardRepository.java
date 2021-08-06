package com.kjh.exam.repository;

import com.kjh.exam.container.ContainerComponent;
import com.kjh.exam.dto.Board;
import com.kjh.mysqliutil.MysqlUtil;
import com.kjh.mysqliutil.SecSql;

public class BoardRepository implements ContainerComponent {
	public void init() {

	}

	public Board getBoardById(int id) {
		SecSql sql = new SecSql();
		sql.append("SELECT B.*");
		sql.append("FROM board AS B");
		sql.append("WHERE B.id = ?", id);

		return MysqlUtil.selectRow(sql, Board.class);
	}

}
