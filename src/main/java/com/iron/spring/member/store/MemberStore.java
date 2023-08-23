package com.iron.spring.member.store;

import org.apache.ibatis.session.SqlSession;

import com.iron.spring.member.domain.Member;

public interface MemberStore {

	public int insertMember(SqlSession session, Member member);

	public int updateMember(SqlSession session, Member member);

	public int deleteMember(SqlSession session, String memberId);

	public Member selectCheckLogin(SqlSession session, Member member);

	public Member selectOneById(SqlSession session, String memberId);

}
