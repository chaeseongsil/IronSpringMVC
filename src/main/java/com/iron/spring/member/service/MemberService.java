package com.iron.spring.member.service;

import com.iron.spring.member.domain.Member;

public interface MemberService {

	public int insertMember(Member member);

	public int updateMember(Member member);

	public int deleteMember(String memberId);

	public Member selectCheckLogin(Member member);

	public Member selectOneById(String memberId);

}
