package com.iron.spring.notice.store.logic;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.iron.spring.notice.domain.Notice;
import com.iron.spring.notice.domain.PageInfo;
import com.iron.spring.notice.store.NoticeStore;

@Repository
public class NoticeStoreLogic implements NoticeStore{

	@Override
	public int insertNotice(SqlSession session, Notice notice) {
		int result = session.insert("NoticeMapper.insertNotice", notice);
		return result;
	}

	@Override
	public int updateNotice(SqlSession session, Notice notice) {
		int result = session.update("NoticeMapper.updateNotice", notice);
		return result;
	}

	@Override
	public List<Notice> selectNoticeList(SqlSession session, PageInfo pInfo) {
		int limit = pInfo.getRecordCountPerPage(); // 가져오는 행의 개수
		int currentPage = pInfo.getCurrentPage();
		int offset = (currentPage-1)*limit; // currentPage에 따라 바뀌는 디폴트값
		RowBounds rowBounds = new RowBounds(offset, limit);
		List<Notice> nList = session.selectList("NoticeMapper.selectNoticeList", null, rowBounds);
		return nList;
	}

	@Override
	public int selectListCount(SqlSession session) {
		int totalCount = session.selectOne("NoticeMapper.selectListCount");
		return totalCount;
	}

	@Override
	public List<Notice> selectNoticeByAll(SqlSession session, String searchKeyword) {
		List<Notice> searchList = session.selectList("NoticeMapper.selectNoticeByAll", searchKeyword);
		return searchList;
	}

	@Override
	public List<Notice> selectNoticeByWriter(SqlSession session, String searchKeyword) {
		List<Notice> searchList = session.selectList("NoticeMapper.selectNoticeByWriter", searchKeyword);
		return searchList;
	}

	@Override
	public List<Notice> selectNoticeByTitle(SqlSession session, String searchKeyword) {
		List<Notice> searchList = session.selectList("NoticeMapper.selectNoticeByTitle", searchKeyword);
		return searchList;
	}

	@Override
	public List<Notice> selectNoticeByContent(SqlSession session, String searchKeyword) {
		List<Notice> searchList = session.selectList("NoticeMapper.selectNoticeByContent", searchKeyword);
		return searchList;
	}

	@Override
	public List<Notice> selectNoticeByKeyword(SqlSession session, Map<String, String> paramMap, PageInfo pInfo) {
		int limit = pInfo.getRecordCountPerPage(); // 가져오는 행의 개수
		int currentPage = pInfo.getCurrentPage();
		int offset = (currentPage-1)*limit; // currentPage에 따라 바뀌는 디폴트값
		RowBounds rowBounds = new RowBounds(offset, limit);
		List<Notice> searchList = session.selectList("NoticeMapper.selectNoticeByKeyword", paramMap, rowBounds);
		return searchList;
	}

	@Override
	public int selectListCount(SqlSession session, Map<String, String> paramMap) {
		int totalCount = session.selectOne("NoticeMapper.selectListByKeywordCount", paramMap);
		return totalCount;
	}

	@Override
	public Notice selectNoticeByNo(SqlSession session, int noticeNo) {
		Notice notice = session.selectOne("NoticeMapper.selectOneByNo", noticeNo);
		return notice;
	}

}
