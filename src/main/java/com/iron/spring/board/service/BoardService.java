package com.iron.spring.board.service;

import java.util.List;

import com.iron.spring.board.domain.Board;
import com.iron.spring.board.domain.PageInfo;

public interface BoardService {
	
	/**
	 * 게시글 등록 Service
	 * @param board
	 * @return int
	 */
	public int insertBoard(Board board);
	/**
	 * 게시글 삭제 Service
	 * @param boardNo
	 * @return int
	 */
	public int deleteBoard(int boardNo);
	/**
	 * 전체 게시물 수 Service
	 * @return int
	 */
	public int getListCount();
	/**
	 * 전체 게시물 조회 Service
	 * @param pInfo
	 * @return List<Board>
	 */
	public List<Board> selectBoardList(PageInfo pInfo);
	/**
	 * 게시물 번호로 조회 Service
	 * @param boardNo
	 * @return Board
	 */
	public Board selectOneByNo(int boardNo);

}
