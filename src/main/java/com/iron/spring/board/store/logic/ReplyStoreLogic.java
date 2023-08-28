package com.iron.spring.board.store.logic;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.iron.spring.board.domain.Reply;
import com.iron.spring.board.store.ReplyStore;

@Repository
public class ReplyStoreLogic implements ReplyStore{

	@Override
	public int insertReply(SqlSession session, Reply reply) {
		int result = session.insert("ReplyMapper.insertReply", reply);
		return result;
	}

	@Override
	public int updateReply(SqlSession session, Reply reply) {
		int result = session.update("ReplyMapper.updateReply", reply);
		return result;
	}

	@Override
	public int deleteReply(SqlSession session, int replyNo) {
		int result = session.update("ReplyMapper.deleteReply", replyNo);
		return result;
	}

	@Override
	public List<Reply> selectReplyList(SqlSession session, Integer boardNo) {
		List<Reply> rList = session.selectList("ReplyMapper.selectReplyList", boardNo);
		return rList;
	}

}
