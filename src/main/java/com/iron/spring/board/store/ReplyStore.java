package com.iron.spring.board.store;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.iron.spring.board.domain.Reply;

public interface ReplyStore {
	
	/**
	 * 댓글 등록 Store
	 * @param session
	 * @param reply
	 * @return int
	 */
	public int insertReply(SqlSession session, Reply reply);
	/**
	 * 댓글 수정 Store
	 * @param session
	 * @param reply
	 * @return int
	 */
	public int updateReply(SqlSession session, Reply reply);
	/**
	 * 댓글 조회 Store
	 * @param session
	 * @param boardNo
	 * @return List<Reply>
	 */
	public List<Reply> selectReplyList(SqlSession session, Integer boardNo);

}
