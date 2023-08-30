package com.iron.spring.board.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.iron.spring.board.domain.Board;
import com.iron.spring.board.domain.PageInfo;
import com.iron.spring.board.domain.Reply;
import com.iron.spring.board.service.BoardService;
import com.iron.spring.board.service.ReplyService;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService service;
	@Autowired
	private ReplyService rService;
	
	@RequestMapping(value="/board/list.kh", method=RequestMethod.GET)
	public ModelAndView showBoardList(
			ModelAndView mv
			, @RequestParam(value="page", required=false, defaultValue="1") Integer currentPage
			) {
		Integer totalCount  = service.getListCount();
		PageInfo pInfo = this.getPageInfo(currentPage, totalCount);
		List<Board> bList = service.selectBoardList(pInfo);
		mv.addObject("pInfo", pInfo).addObject("bList", bList).setViewName("board/list");
		// 메소드 체이닝 기법 사용 -> 다 붙여서 한줄로 코딩 가능
		return mv;
	}
	
	@RequestMapping(value="/board/detail.kh", method=RequestMethod.GET)
	public ModelAndView showBoardDetail(
			@RequestParam("boardNo") Integer boardNo
			, ModelAndView mv
			) {
		try {
			Board board = service.selectOneByNo(boardNo);
			if(board != null) {
				List<Reply> rList = rService.selectReplyList(boardNo);
				if(rList.size() > 0) {
					mv.addObject("rList", rList);
				}
				mv.addObject("board", board).setViewName("board/detail");
			}else {
				mv.addObject("msg", "게시글 데이터 조회를 실패하였습니다.");
				mv.addObject("error", "게시글 조회 실패");
				mv.setViewName("common/errorPage");
			}
		} catch (Exception e) {
			mv.addObject("msg", "관리자에게 문의해주세요.");
			mv.addObject("error", e.getMessage());
			mv.addObject("url", "/board/list.kh");
			mv.setViewName("common/errorPage");
		}
		return mv;
	}
	
	@RequestMapping(value="/board/write.kh", method=RequestMethod.GET)
	public ModelAndView showWriteForm(ModelAndView mv) {
		mv.setViewName("board/write");
		return mv;
	}
	
	@RequestMapping(value="/board/write.kh", method=RequestMethod.POST)
	public ModelAndView boardRegister(
			@ModelAttribute Board board
			, @RequestParam(value="uploadFile", required=false) MultipartFile uploadFile
			, ModelAndView mv
			, HttpServletRequest request
			, HttpSession session
			) {
		try {
			String boardWriter = (String) session.getAttribute("memberId");
			if(boardWriter != null && !boardWriter.equals("")) {
				board.setBoardWriter(boardWriter);
				if(uploadFile != null && !uploadFile.getOriginalFilename().equals("")) {
					// 파일정보(이름, 리네임, 경로, 크기) 및 파일 저장
					Map<String, Object> bMap = this.saveFile(request, uploadFile);
					board.setBoardFilename((String)bMap.get("fileName"));
					board.setBoardFileRename((String)bMap.get("fileRename"));
					board.setBoardFilepath((String)bMap.get("filePath"));
					board.setBoardFileLength((long)bMap.get("fileLength"));
				}
				int result = service.insertBoard(board);
				mv.setViewName("redirect:/board/list.kh");
			}else {
				mv.addObject("msg", "게시글 등록을 실패하였습니다.");
				mv.addObject("error", "게시글 등록 실패");
				mv.addObject("url", "/board/write.kh");
				mv.setViewName("common/errorPage");
			}
		} catch (Exception e) {
			mv.addObject("msg", "관리자에게 문의해주세요.");
			mv.addObject("error", e.getMessage());
			mv.addObject("url", "/board/write.kh");
			mv.setViewName("common/errorPage");
		}
		return mv;
	}
	
	@RequestMapping(value="/board/modify.kh", method=RequestMethod.GET)
	public ModelAndView showModifyForm(
			ModelAndView mv
			, @RequestParam("boardWriter") String boardWriter
			, @RequestParam("boardNo") int boardNo
			, HttpSession session
			) {
		String memberId = (String) session.getAttribute("memberId");
		if(boardWriter != null && boardWriter.equals(memberId)) {
			Board board = service.selectOneByNo(boardNo);
			mv.addObject("board", board).setViewName("board/modify");
		}else {
			mv.addObject("msg", "본인이 작성한 글만 수정할 수 있습니다.");
			mv.addObject("error", "게시글 수정 불가");
			mv.addObject("url", "/board/detail.kh?boardNo="+boardNo);
			mv.setViewName("common/errorPage");
		}
		return mv;
	}
	
	@RequestMapping(value="/board/modify.kh", method=RequestMethod.POST)
	public ModelAndView modifyBoard(
			ModelAndView mv
			, @ModelAttribute Board board
			, @RequestParam(value="uploadFile", required=false) MultipartFile uploadFile
			, HttpServletRequest request
			) {
		String url = "";
		try {
			if(uploadFile != null && !uploadFile.isEmpty()) {
				String fileRename = board.getBoardFileRename();
				if(fileRename != null) {
					this.deleteFile(request, fileRename);
				}
				Map<String, Object> bMap = this.saveFile(request, uploadFile);
				board.setBoardFilename((String)bMap.get("fileName"));
				board.setBoardFileRename((String)bMap.get("fileRename"));
				board.setBoardFilepath((String)bMap.get("filePath"));
				board.setBoardFileLength((long)bMap.get("fileLength"));
			}
			int result = service.updateBoard(board);
			url = "/board/detail.kh?boardNo="+board.getBoardNo();
			if(result > 0) {
				mv.setViewName("redirect:"+url);
			}else {
				mv.addObject("msg", "게시글 수정이 완료되지 않습니다.");
				mv.addObject("error", "게시글 수정 실패");
				mv.addObject("url", url);
				mv.setViewName("common/errorPage");
			}
		} catch (Exception e) {
			mv.addObject("msg", "관리자에게 문의해주세요.");
			mv.addObject("error", e.getMessage());
			mv.addObject("url", url);
			mv.setViewName("common/errorPage");
		}
		return mv;
	}
	
	@RequestMapping(value="/board/delete.kh", method=RequestMethod.GET)
	public ModelAndView deleteBoard(
			ModelAndView mv
			, @RequestParam("boardWriter") String boardWriter
			, @RequestParam("boardNo") int boardNo
			, HttpSession session
			) {
		String url = "";
		try {
			String memberId = (String) session.getAttribute("memberId");
			url =  "/board/detail.kh?boardNo="+boardNo;
			if(boardWriter != null && boardWriter.equals(memberId)) {
				int result = service.deleteBoard(boardNo);
				if(result > 0) {
					mv.setViewName("redirect:/board/list.kh");
				}else {
					mv.addObject("msg", "게시글 삭제를 실패하였습니다.");
					mv.addObject("error", "게시글 삭제 실패");
					mv.addObject("url", url);
					mv.setViewName("common/errorPage");
				}
			}else {
				mv.addObject("msg", "본인이 작성한 글만 삭제할 수 있습니다.");
				mv.addObject("error", "게시글 삭제 불가");
				mv.addObject("url", url);
				mv.setViewName("common/errorPage");
			}
		} catch (Exception e) {
			mv.addObject("msg", "관리자에게 문의해주세요.");
			mv.addObject("error", e.getMessage());
			mv.addObject("url", url);
			mv.setViewName("common/errorPage");
		}
		return mv;
	}

	private PageInfo getPageInfo(Integer currentPage, Integer totalCount) {
		PageInfo pInfo = null;
		int recordCountPerPage = 10;
		int naviCountPerPage = 10;
		int naviTotalCount;
		naviTotalCount = (int)Math.ceil((double)totalCount/recordCountPerPage);
		int startNavi = ((int)((double)currentPage/naviCountPerPage+0.9)-1) * naviCountPerPage + 1;
		int endNavi = startNavi + naviCountPerPage - 1;
		if(endNavi > naviTotalCount) {
			endNavi = naviTotalCount;
		}
		pInfo = new PageInfo(currentPage, totalCount, naviTotalCount, recordCountPerPage, naviCountPerPage, startNavi, endNavi);
		return pInfo;
	}

	private Map<String, Object> saveFile(HttpServletRequest request, MultipartFile uploadFile) throws Exception {
		Map<String, Object> fileMap = new HashMap<String, Object>();
		// 파일 이름 구하기
		String fileName = uploadFile.getOriginalFilename();
		// resources 경로 구하기
		String root = request.getSession().getServletContext().getRealPath("resources");
		// 파일 저장경로 구하기
		String saveFolder = root + "\\buploadFiles";
		// 파일 저장 전 폴더 만들기
		File folder = new File(saveFolder);
		if(!folder.exists()) {
			folder.mkdir();
		}
		// 파일 확장자 구하기
		String extension = fileName.substring(fileName.lastIndexOf(".")+1);
		// 시간으로 파일 리네임 하기
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSSS");
		String fileRename = sdf.format(new Date(System.currentTimeMillis()))+"."+extension;
		// 파일 저장
		String savePath = saveFolder + "\\" + fileRename;
		File file = new File(savePath);
		uploadFile.transferTo(file);
		// 파일 크기 구하기
		long fileLength = uploadFile.getSize();
		// Map에 넣어주기
		fileMap.put("fileName", fileName);
		fileMap.put("fileRename", fileRename);
		fileMap.put("filePath", "../resources/buploadFiles/"+fileRename);
		fileMap.put("fileLength", fileLength);
		// Map 리턴
		return fileMap;
	}
	private void deleteFile(HttpServletRequest request, String fileReName) {
		String root = request.getSession().getServletContext().getRealPath("resources");
		String delFilepath = root+"\\buploadFiles\\"+fileReName;
		File file = new File(delFilepath);
		if(file.exists()) { // 파일이 존재하면 삭제
			file.delete();
		}
	}
}
