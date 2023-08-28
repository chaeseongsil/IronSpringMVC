package com.iron.spring.board.service;

import java.util.List;

import com.iron.spring.board.domain.Reply;

public interface ReplyService {

	/**
	 * 댓글 등록 Service
	 * @param reply
	 * @return int
	 */
	public int insertReply(Reply reply);
	/**
	 * 댓글 수정 Service
	 * @param reply
	 * @return int
	 */
	public int updateReply(Reply reply);
	/**
	 * 댓글 삭제 Service
	 * @param replyNo
	 * @return int
	 */
	public int deleteReply(int replyNo);
	/**
	 * 댓글 조회 Service
	 * @param boardNo 
	 * @return List<Reply>
	 */
	public List<Reply> selectReplyList(Integer boardNo);

}
