package com.iron.spring.notice.store;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.iron.spring.notice.domain.Notice;
import com.iron.spring.notice.domain.PageInfo;

public interface NoticeStore {

	/**
	 * 공지사항 등록 Store
	 * @param session
	 * @param notice
	 * @return int
	 */
	public int insertNotice(SqlSession session, Notice notice);
	/**
	 * 공지사항 수정 Service
	 * @param session
	 * @param notice
	 * @return int
	 */
	public int updateNotice(SqlSession session, Notice notice);
	/**
	 * 공지사항 목록 조회 Store
	 * @param session
	 * @param pInfo
	 * @return List<Notice>
	 */
	public List<Notice> selectNoticeList(SqlSession session, PageInfo pInfo);
	/**
	 * 공지사항 개수 조회 Store
	 * @param session
	 * @return int
	 */
	public int selectListCount(SqlSession session);
	/**
	 * 전체 공지사항 키워드로 검색 Store
	 * @param session
	 * @param searchKeyword
	 * @return List<Notice>
	 */
	public List<Notice> selectNoticeByAll(SqlSession session, String searchKeyword);
	/**
	 * 공지사항 작성자로 검색 Store
	 * @param session
	 * @param searchKeyword
	 * @return List<Notice>
	 */
	public List<Notice> selectNoticeByWriter(SqlSession session, String searchKeyword);
	/**
	 * 공지사항 제목으로 검색 Store
	 * @param session
	 * @param searchKeyword
	 * @return List<Notice>
	 */
	public List<Notice> selectNoticeByTitle(SqlSession session, String searchKeyword);
	/**
	 * 공지사항 내용으로 검색 Store
	 * @param session
	 * @param searchKeyword
	 * @return List<Notice>
	 */
	public List<Notice> selectNoticeByContent(SqlSession session, String searchKeyword);
	/**
	 * 공지사항 조건에 따른 키워드로 검색 Store
	 * @param session
	 * @param pInfo 
	 * @param searchCondition
	 * @param searchKeyword
	 * @return List<Notice>
	 */
	public List<Notice> selectNoticeByKeyword(SqlSession session, Map<String, String> paramMap, PageInfo pInfo);
	/** 
	 * 공지사항 검색 게시물 전체 개수 Service
	 * @param session
	 * @param paramMap
	 * @return int
	 */
	public int selectListCount(SqlSession session, Map<String, String> paramMap);
	/**
	 * 공지사항 번호로 조회 Service
	 * @param session
	 * @param noticeNo
	 * @return Notice
	 */
	public Notice selectNoticeByNo(SqlSession session, int noticeNo);
	

}
