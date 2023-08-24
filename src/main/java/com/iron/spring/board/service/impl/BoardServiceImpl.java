package com.iron.spring.board.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iron.spring.board.domain.Board;
import com.iron.spring.board.domain.PageInfo;
import com.iron.spring.board.service.BoardService;
import com.iron.spring.board.store.BoardStore;

@Service
public class BoardServiceImpl implements BoardService{
	
	@Autowired
	private SqlSession session;
	@Autowired
	private BoardStore bStore;
	
	@Override
	public int insertBoard(Board board) {
		int result = bStore.insertBoard(session, board);
		return result;
	}

	@Override
	public int getListCount() {
		int result = bStore.selectListCount(session);
		return result;
	}

	@Override
	public List<Board> selectBoardList(PageInfo pInfo) {
		List<Board> bList = bStore.selectBoardList(session, pInfo);
		return bList;
	}

	@Override
	public Board selectOneByNo(int boardNo) {
		Board board = bStore.selectOneByNo(session, boardNo);
		return board;
	}

}
