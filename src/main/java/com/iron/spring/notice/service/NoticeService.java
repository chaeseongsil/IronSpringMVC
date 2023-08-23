package com.iron.spring.notice.service;

import java.util.List;
import java.util.Map;

import com.iron.spring.notice.domain.Notice;
import com.iron.spring.notice.domain.PageInfo;

public interface NoticeService {
	/**
	 * 공지사항 등록 Service
	 * @param notice
	 * @return int
	 */
	public int insertNotice(Notice notice);
	/**
	 * 공지사항 목록 조회 Service
	 * @param pInfo 
	 * @param currentPage
	 * @return List<Notice>
	 */
	public List<Notice> selectNoticeList(PageInfo pInfo);
	/**
	 * 공지사항 전체 개수 조회 Service
	 * @return int
	 */
	public int getListCount();
	/**
	 * 전체 공지사항 키워드로 검색 Service
	 * @param searchKeyword
	 * @return List<Notice>
	 */
	public List<Notice> searchNoticeByAll(String searchKeyword);
	/**
	 * 공지사항 작성자로 검색 Service
	 * @param searchKeyword
	 * @return List<Notice>
	 */
	public List<Notice> searchNoticeByWriter(String searchKeyword);
	/**
	 * 공지사항 제목으로 검색 Service
	 * @param searchKeyword
	 * @return
	 */
	public List<Notice> searchNoticeByTitle(String searchKeyword);
	/** 
	 * 공지사항 내용으로 검색 Service
	 * @param searchKeyword
	 * @return List<Notice>
	 */
	public List<Notice> searchNoticeByContent(String searchKeyword);
	/**
	 * 공지사항 조건에 따른 키워드로 검색 Service
	 * @param pInfo 
	 * @param searchCondition
	 * @param searchKeyword
	 * @return List<Notice>
	 */
	public List<Notice> searchNoticeByKeyword(Map<String, String> paramMap, PageInfo pInfo);
	/**
	 * 공지사항 조건에 따른 키워드로 검색한 결과의 총 개수 Service
	 * @param paramMap
	 * @return int
	 */
	public int getListCount(Map<String, String> paramMap);
	/**
	 * 공지사항 번호로 조회 Service
	 * @param noticeNo
	 * @return Notice
	 */
	public Notice selectNoticeByNo(int noticeNo);
	/**
	 * 공지사항 수정 Service
	 * @param notice
	 * @return int
	 */
	public int updateNotice(Notice notice);

}
