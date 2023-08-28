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
	 * 댓글 삭제 Store
	 * @param session
	 * @param replyNo
	 * @return int
	 */
	public int deleteReply(SqlSession session, int replyNo);
	/**
	 * 댓글 조회 Store
	 * @param session
	 * @param boardNo
	 * @return List<Reply>
	 */
	public List<Reply> selectReplyList(SqlSession session, Integer boardNo);

}
