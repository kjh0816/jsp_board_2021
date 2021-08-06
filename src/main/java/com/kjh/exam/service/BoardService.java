package com.kjh.exam.service;

import com.kjh.exam.container.Container;
import com.kjh.exam.container.ContainerComponent;
import com.kjh.exam.dto.Board;
import com.kjh.exam.repository.BoardRepository;

public class BoardService implements ContainerComponent {
	private BoardRepository boardRepository;
	
	public void init() {
		boardRepository = Container.boardRepository;
	}

	public Board getBoardById(int id) {
		return boardRepository.getBoardById(id);
	}
}
