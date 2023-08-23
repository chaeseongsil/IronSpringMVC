package com.iron.spring.notice.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iron.spring.notice.domain.Notice;
import com.iron.spring.notice.domain.PageInfo;
import com.iron.spring.notice.service.NoticeService;
import com.iron.spring.notice.store.NoticeStore;

@Service
public class NoticeServiceImpl implements NoticeService{
	
	@Autowired
	private NoticeStore nStore;
	@Autowired
	private SqlSession session;
	
	@Override
	public int insertNotice(Notice notice) {
		int result = nStore.insertNotice(session, notice);
		return result;
	}

	@Override
	public List<Notice> selectNoticeList(PageInfo pInfo) {
		List<Notice> nList = nStore.selectNoticeList(session, pInfo);
		return nList;
	}

	@Override
	public int getListCount() {
		int totalCount = nStore.selectListCount(session);
		return totalCount;
	}

	@Override
	public List<Notice> searchNoticeByAll(String searchKeyword) {
		List<Notice> searchList = nStore.selectNoticeByAll(session, searchKeyword);
		return searchList;
	}

	@Override
	public List<Notice> searchNoticeByWriter(String searchKeyword) {
		List<Notice> searchList = nStore.selectNoticeByWriter(session, searchKeyword);
		return searchList;
	}

	@Override
	public List<Notice> searchNoticeByTitle(String searchKeyword) {
		List<Notice> searchList = nStore.selectNoticeByTitle(session, searchKeyword);
		return searchList;
	}

	@Override
	public List<Notice> searchNoticeByContent(String searchKeyword) {
		List<Notice> searchList = nStore.selectNoticeByContent(session, searchKeyword);
		return searchList;
	}

	@Override
	public int getListCount(Map<String, String> paramMap) {
		int totalCount = nStore.selectListCount(session, paramMap);
		return totalCount;
	}

	@Override
	public List<Notice> searchNoticeByKeyword(Map<String, String> paramMap, PageInfo pInfo) {
		List<Notice> searchList = nStore.selectNoticeByKeyword(session, paramMap, pInfo);
		return searchList;
	}

	@Override
	public Notice selectNoticeByNo(int noticeNo) {
		Notice notice = nStore.selectNoticeByNo(session, noticeNo);
		return notice;
	}

	@Override
	public int updateNotice(Notice notice) {
		int result = nStore.updateNotice(session, notice);
		return result;
	}
}
