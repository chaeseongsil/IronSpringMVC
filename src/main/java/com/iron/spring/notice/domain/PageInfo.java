package com.iron.spring.notice.domain;

// 페이징 처리 관련 VO
public class PageInfo {
	private int currentPage;
	private int recordCountPerPage;
	private int naviCountPerPage;
	private int naviTotalCount;
	private int startNavi;
	private int endNavi;
	private int totalCount;
	
	public PageInfo() {}
	
	public PageInfo(int currentPage, int recordCountPerPage, int naviCountPerPage, int naviTotalCount, int startNavi,
			int endNavi, int totalCount) {
		super();
		this.currentPage = currentPage;
		this.recordCountPerPage = recordCountPerPage;
		this.naviCountPerPage = naviCountPerPage;
		this.naviTotalCount = naviTotalCount;
		this.startNavi = startNavi;
		this.endNavi = endNavi;
		this.totalCount = totalCount;
	}
	
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getRecordCountPerPage() {
		return recordCountPerPage;
	}
	public void setRecordCountPerPage(int recordCountPerPage) {
		this.recordCountPerPage = recordCountPerPage;
	}
	public int getNaviCountPerPage() {
		return naviCountPerPage;
	}
	public void setNaviCountPerPage(int naviCountPerPage) {
		this.naviCountPerPage = naviCountPerPage;
	}
	public int getNaviTotalCount() {
		return naviTotalCount;
	}
	public void setNaviTotalCount(int naviTotalCount) {
		this.naviTotalCount = naviTotalCount;
	}
	public int getStartNavi() {
		return startNavi;
	}
	public void setStartNavi(int startNavi) {
		this.startNavi = startNavi;
	}
	public int getEndNavi() {
		return endNavi;
	}
	public void setEndNavi(int endNavi) {
		this.endNavi = endNavi;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	@Override
	public String toString() {
		return "PageInfo [currentPage=" + currentPage + ", recordCountPerPage=" + recordCountPerPage
				+ ", naviCountPerPage=" + naviCountPerPage + ", naviTotalCount=" + naviTotalCount + ", startNavi="
				+ startNavi + ", endNavi=" + endNavi + ", totalCount=" + totalCount + "]";
	}
	
	
}
