package com.iron.spring.board.store;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.iron.spring.board.domain.Board;
import com.iron.spring.board.domain.PageInfo;

public interface BoardStore {
	
	/**
	 * 게시글 등록 Store
	 * @param session
	 * @param board
	 * @return int
	 */
	public int insertBoard(SqlSession session, Board board);
	/**
	 * 전체 게시물 수 조회 Store
	 * @param session
	 * @return int
	 */
	public int selectListCount(SqlSession session);
	/**
	 * 전체 게시물 조회 Store
	 * @param session
	 * @param pInfo
	 * @return List<Board>
	 */
	public List<Board> selectBoardList(SqlSession session, PageInfo pInfo);
	/**
	 * 게시물 번호로 조회 Store
	 * @param session
	 * @param boardNo
	 * @return Board
	 */
	public Board selectOneByNo(SqlSession session, int boardNo);

}
