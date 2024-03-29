package com.iron.spring.board.store.logic;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.iron.spring.board.domain.Board;
import com.iron.spring.board.domain.PageInfo;
import com.iron.spring.board.store.BoardStore;

@Repository
public class BoardStoreLogic implements BoardStore{

	@Override
	public int insertBoard(SqlSession session, Board board) {
		int result = session.insert("BoardMapper.insertBoard", board);
		return result;
	}

	@Override
	public int updateBoard(SqlSession session, Board board) {
		int result = session.update("BoardMapper.updateBoard", board);
		return result;
	}

	@Override
	public int deleteBoard(SqlSession session, int boardNo) {
		int result = session.update("BoardMapper.deleteBoard", boardNo);
		return result;
	}

	@Override
	public int selectListCount(SqlSession session) {
		int result = session.selectOne("BoardMapper.selectListCount");
		return result;
	}

	@Override
	public List<Board> selectBoardList(SqlSession session, PageInfo pInfo) {
		int limit = pInfo.getRecordCountPerPage();
		int offset = (pInfo.getCurrentPage()-1)*limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		List<Board> bList = session.selectList("BoardMapper.selectBoardList", null, rowBounds);
		return bList;
	}

	@Override
	public Board selectOneByNo(SqlSession session, int boardNo) {
		Board board = session.selectOne("BoardMapper.selectOneByNo", boardNo);
		return board;
	}

	
}
