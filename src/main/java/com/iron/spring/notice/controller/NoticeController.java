package com.iron.spring.notice.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.iron.spring.notice.domain.Notice;
import com.iron.spring.notice.domain.PageInfo;
import com.iron.spring.notice.service.NoticeService;

@Controller
public class NoticeController {
	
	@Autowired
	private NoticeService service;
	
	@RequestMapping(value="/notice/insert.kh", method=RequestMethod.GET)
	public String showInsertForm() {
		return "notice/insert";
	}
	
	@RequestMapping(value="/notice/insert.kh", method=RequestMethod.POST)	
	public String insertNotice(
			@ModelAttribute Notice notice
			, @RequestParam(value="uploadFile", required=false) MultipartFile uploadFile 
			, HttpServletRequest request
			, Model model
			) {
		try {
			if(uploadFile != null && !uploadFile.getOriginalFilename().equals("")) {
				Map<String, Object> nMap = this.saveFile(uploadFile, request);
				String fileName = (String)nMap.get("fileName");
				String fileRename = (String)nMap.get("fileRename");
				String savePath = (String)nMap.get("filePath");
				long fileLength = (long)nMap.get("fileLength");
				// DB에 저장하기 위해 notice에 데이터를 set하는 부분
				notice.setNoticeFilename(fileName);
				notice.setNoticeFileRename(fileRename);
				notice.setNoticeFilepath(savePath);
				notice.setNoticeFilelength(fileLength);
			}
			int result = service.insertNotice(notice);
			if(result > 0) {
				return "redirect:/notice/list.kh";
			}else {
				model.addAttribute("msg", "공지사항 등록이 완료되지 않았습니다.");
				model.addAttribute("error", "공지사항 등록");
				model.addAttribute("url", "/notice/insert.kh");
				return "common/errorPage";
			}
		} catch (Exception e) {
			model.addAttribute("msg", "관리자에게 문의해주세요.");
			model.addAttribute("error", e.getMessage());
			model.addAttribute("url", "/notice/insert.kh");
			return "common/errorPage";
		}
	}
	
	@RequestMapping(value="/notice/modify.kh", method=RequestMethod.GET)
	public String showModifyForm(
			@RequestParam("noticeNo") Integer noticeNo
			, Model model
			) {
		Notice notice = service.selectNoticeByNo(noticeNo);
		model.addAttribute("notice", notice);
		return "notice/modify";
	}
	
	@RequestMapping(value="/notice/modify.kh", method=RequestMethod.POST)
	public String updateNotice(
			@ModelAttribute Notice notice
			, @RequestParam(value="uploadFile", required=false) MultipartFile uploadFile
			, HttpServletRequest request
			, Model model
			) {
		try {
			if(uploadFile != null && !uploadFile.isEmpty()) {
				// !isEmpty() 먼저 동작시키면 nullPointException 발생함
				// 수정
				// 1. 대체, 2. 삭제 후 등록
				// 기존 업로드된 파일 존재 여부 체크 후 
				String fileReName = notice.getNoticeFileRename();
				if(fileReName != null) {
					// 있으면 기존 파일 삭제
					this.deleteFile(request, fileReName);
				}
				// 없으면 새로 업로드하려는 파일 저장
				Map<String, Object> infoMap = this.saveFile(uploadFile, request);
				String noticeFilename = (String)infoMap.get("fileName");
				String fileRename = (String)infoMap.get("fileRename");
				long noticeFilelength = (long)infoMap.get("fileLength");
				notice.setNoticeFilename(noticeFilename);
				notice.setNoticeFileRename(fileRename);
				notice.setNoticeFilepath((String)infoMap.get("filePath")); // 변수없이 함
				notice.setNoticeFilelength(noticeFilelength);
			}
			int result = service.updateNotice(notice);
			if(result > 0) {
				return "redirect:/notice/detail.kh?noticeNo="+notice.getNoticeNo();
			}else {
				model.addAttribute("msg", "공지사항 수정이 완료되지 않습니다.");
				model.addAttribute("error", "공지사항 수정 실패");
				model.addAttribute("url", "/notice/detail.kh?noticeNo="+notice.getNoticeNo());
				return "common/errorPage";
			}
		} catch (Exception e) {
			model.addAttribute("msg", "관리자에게 문의해주세요.");
			model.addAttribute("error", e.getMessage());
			model.addAttribute("url", "/index.jsp");
			return "common/errorPage";
		}
	}
	
	@RequestMapping(value="/notice/list.kh", method=RequestMethod.GET)
	public String showNoticeList(
			@RequestParam(value="page", required=false, defaultValue = "1") Integer currentPage
			// int 대신 null 체크가 가능한 Integer를 씀
			, Model model
			) {
		try {
//			int currentPage = page != 0 ? page : 1;
			int totalCount = service.getListCount();
			PageInfo pInfo = this.getPageInfo(currentPage, totalCount);
			List<Notice> nList = service.selectNoticeList(pInfo);
			// List 데이터의 유효성 체크 방법 2가지
			// 1. isEmpty() 
			// 2. size() > 0
			if(!nList.isEmpty()) {
				model.addAttribute("pInfo", pInfo);
				model.addAttribute("nList", nList);
				return "notice/list";
			}else {
				model.addAttribute("msg", "데이터가 조회되지 않습니다.");
				model.addAttribute("error", "공지사항 목록 조회 실패");
				model.addAttribute("url", "/index.jsp");
				return "common/errorPage";
			}
		} catch (Exception e) {
			model.addAttribute("msg", "관리자에게 문의해주세요.");
			model.addAttribute("error", e.getMessage());
			model.addAttribute("url", "/index.jsp");
			return "common/errorPage";
		}
	}
	
	public PageInfo getPageInfo(int currentPage, int totalCount) {
		PageInfo pi = null;
		int recordCountPerPage = 10;
		int naviCountPerPage = 10;
		int naviTotalCount;
		int startNavi;
		int endNavi;
		naviTotalCount = (int)((double)totalCount/recordCountPerPage + 0.9);
		// if(totalCount % recordCountPerPage > 0) 을 사용할 필요 없이 0.9를 더해주면 됨
		// 나누어 떨어지지 않으면 자동으로 페이지를 하나 더 맨들어주는 계산식
		// 둘 중 하나만 double로 바꿔서 자동형변환 시킨다음에 int로 강제형변환해서 소수점 떨궈주기
		// Math.ceil 해도 됨
		startNavi = (((int)((double)currentPage / naviCountPerPage + 0.9))-1)*naviCountPerPage + 1;
		// currentPage값이 1~5일 때 startNavi가 1로 고정되도록 구해주는 식
		endNavi = startNavi + naviCountPerPage - 1;
		// endNavi는 startNavi에 무조건 naviCountPerPage 값을 더하므로 
		// 실제 최대값보다 커질 수 있음
		if(endNavi > naviTotalCount) {
			endNavi = naviTotalCount;
		}
		pi = new PageInfo(currentPage, recordCountPerPage, naviCountPerPage, naviTotalCount, startNavi, endNavi, naviTotalCount);
		return pi;
	}
	
	@RequestMapping(value="/notice/search.kh", method=RequestMethod.GET)
	public String searchNoticeList(
			@RequestParam("searchCondition") String searchCondition
			, @RequestParam("searchKeyword") String searchKeyword
			, @RequestParam(value="page", required=false, defaultValue="1") Integer currentPage 
			, Model model
			) {
		// 2개의 값을 하나의 변수로 다루는 방법
		// 1. VO 클래스 만드는 방법
		// 2. HashMap 사용하는 방법
		Map<String, String> paramMap = new HashMap<String, String>();
		// put() 메소드를 사용해서 key-value 설정을 하는데
		// key값(파란색)이 mapper.xml에서 사용됨
		paramMap.put("searchCondition", searchCondition);
		paramMap.put("searchKeyword", searchKeyword);
		int totalCount = service.getListCount(paramMap);
		PageInfo pInfo = this.getPageInfo(currentPage, totalCount);
		List<Notice> searchList = service.searchNoticeByKeyword(paramMap, pInfo);
//		switch (searchCondition) {
//		case "all":	
//			searchList = service.searchNoticeByAll(searchKeyword);
//			break;
//		case "writer":	
//			searchList = service.searchNoticeByWriter(searchKeyword);
//			break;
//		case "title":	
//			searchList = service.searchNoticeByTitle(searchKeyword);
//			break;
//		case "content":	
//			searchList = service.searchNoticeByContent(searchKeyword);
//			break;
//		}
		if(!searchList.isEmpty()) {
			model.addAttribute("sList", searchList);
			model.addAttribute("pInfo", pInfo);
			model.addAttribute("searchCondition", searchCondition);
			model.addAttribute("searchKeyword", searchKeyword);
			return "notice/search";
		}else {
			model.addAttribute("msg", "데이터가 조회되지 않습니다.");
			model.addAttribute("error", "공지사항 제목으로 조회 실패");
			model.addAttribute("url", "/notice/list.kh");
			return "common/errorPage";
		}
	}
	
	@RequestMapping(value="/notice/detail.kh", method=RequestMethod.GET)
	public String showNoticeDetail(
			@RequestParam("noticeNo") int noticeNo
			, Model model
			) {
		try {
			Notice notice = service.selectNoticeByNo(noticeNo);
			if(notice != null) {
				model.addAttribute("notice", notice);
				return "notice/detail";
			}else {
				model.addAttribute("msg", "데이터가 조회되지 않았습니다.");
				model.addAttribute("error", "공지사항 상세조회 실패");
				model.addAttribute("url", "/notice/list.kh");
				return "common/errorPage";
			}
		} catch (Exception e) {
			model.addAttribute("msg", "관리자에게 문의해주세요.");
			model.addAttribute("error", e.getMessage());
			model.addAttribute("url", "/notice/list.kh");
			return "common/errorPage";
		}
	}

	public Map<String, Object> saveFile(MultipartFile uploadFile, HttpServletRequest request) throws Exception {
		// 넘겨야 하는 값이 여러개일 때 사용하는 방법
		// 1. vo 클래스 만드는 방법
		// 2. Hashmap을 사용하는 방법
		Map<String, Object> infoMap = new HashMap<String, Object>();
		// ========== 파일 이름 =================
		String fileName = uploadFile.getOriginalFilename();
		// (내가 저장한 후 그 경로를 DB에 저장하도록 준비하는 것)
		String root = request.getSession().getServletContext().getRealPath("resources");
		// 폴더가 없을 경우 자동 생성(내가 업로드한 파일을 저장할 폴더)
		String saveFolder = root + "\\nuploadFiles";
		File folder = new File(saveFolder); // 해당 경로된 파일 객체를 만듦
		if(!folder.exists()) {
			// 폴더가 존재하지 않으면
			folder.mkdir(); // 만들어줘라
		}
		// ========== 파일 경로 =================
//		Random rand = new Random();
//		String strResult = "N";
//		for(int i = 0; i < 7; i++) {
//			int result = rand.nextInt(20)+1; // 1 ~ 100
//			strResult += result + "";
//		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSSS");
		String strResult = sdf.format(new Date(System.currentTimeMillis()));
		String ext = fileName.substring(fileName.lastIndexOf(".")+1); // .을 포함하지 않고 자름+1
		String fileRename = strResult + "." +ext;
		String savePath = saveFolder + "\\" + fileRename;
		File file = new File(savePath);
		// ============= 파일 저장 ======================
		uploadFile.transferTo(file);
		// =========== 파일 크기 =====================
		long fileLength = uploadFile.getSize();
		// 파일 이름, 경로, 크기를 넘겨주기 위해 Map에 정보를 저장한 후 return
		// 왜 return 하는가? ==> DB에 저장하기 위해서 필요한 정보임~
		infoMap.put("fileName", fileName);
		infoMap.put("fileRename", fileRename);
		infoMap.put("filePath", savePath);
		infoMap.put("fileLength", fileLength);
		return infoMap;
	}

	private void deleteFile(HttpServletRequest request, String fileReName) {
		String root = request.getSession().getServletContext().getRealPath("resources");
		String delFilepath = root+"\\nuploadFiles\\"+fileReName;
		File file = new File(delFilepath);
		if(file.exists()) { // 파일이 존재하면 삭제
			file.delete();
		}
	}
	
}
