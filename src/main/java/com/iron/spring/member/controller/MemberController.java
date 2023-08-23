package com.iron.spring.member.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.iron.spring.member.domain.Member;
import com.iron.spring.member.service.MemberService;

@Controller
public class MemberController {
	
	@Autowired
	private MemberService service;
	
	@RequestMapping(value="/member/register.kh", method=RequestMethod.GET)
	public String showRegisterForm() {
		return "member/register";
	}
	
	@RequestMapping(value="/member/register.kh", method=RequestMethod.POST)
	public String registerMember(
//			@RequestParam("memberId") String memberId
			@ModelAttribute Member member
			// Member의 멤버면수와 input태그의 name이 같으면 알아서 받아줌
			, Model model
			) {
		try {
			int result = service.insertMember(member);
			if(result > 0) {
				// home.jsp가 로그인할 수 있는 페이지가 되게 하기
				return "redirect:/index.jsp";
			}else {
				model.addAttribute("msg", "회원가입이 완료되지 않았습니다.");
				model.addAttribute("error", "회원가입 실패");
				model.addAttribute("url", "/member/register.kh");
				return "common/errorPage";
			}
		} catch (Exception e) {
			model.addAttribute("msg", "관리자에게 문의해주세요.");
			model.addAttribute("error", e.getMessage());
			model.addAttribute("url", "/member/register.kh");
			return "common/errorPage";
		}
	}
	
	@RequestMapping(value="/member/modify.kh", method=RequestMethod.GET)
	public String modifyViewMember(
			@RequestParam("memberId") String memberId
			, Model model
			) {
		Member member = service.selectOneById(memberId);
		model.addAttribute("member", member);
		return "member/modify";
	}

	@RequestMapping(value="/member/modify.kh", method=RequestMethod.POST)
	public String modifyMember(
			@ModelAttribute Member member
			, Model model
			) {
		try {
			int result = service.updateMember(member);
			if(result > 0) {
				return "redirect:/member/mypage.kh?memberId="+member.getMemberId();
			}else {
				model.addAttribute("error", "회원 정보를 수정하지 못했습니다.");
				model.addAttribute("msg", "회원 정보 수정 실패");
				model.addAttribute("url", "/member/mypage.kh?memberId="+member.getMemberId());
				return "common/errorPage";				
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "관리자에게 문의하세요.");
			model.addAttribute("error", e.getMessage());
			model.addAttribute("url", "/member/mypage.kh?memberId="+member.getMemberId());
			return "common/errorPage";
		}
	}

	@RequestMapping(value="/member/delete.kh", method=RequestMethod.GET)
	public String deleteMember(
			@RequestParam("memberId") String memberId
			, Model model
			) {
		try {
			int result = service.deleteMember(memberId);
			if(result > 0) {
				return "redirect:/member/logout.kh";
			}else {
				model.addAttribute("error", "탈퇴를 성공하지 못했습니다.");
				model.addAttribute("msg", "회원 탈퇴 실패");
				model.addAttribute("url", "/member/mypage.kh?memberId="+memberId);
				return "common/errorPage";		
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "관리자에게 문의하세요.");
			model.addAttribute("error", e.getMessage());
			model.addAttribute("url", "/member/mypage.kh?memberId="+memberId);
			return "common/errorPage";
		}
	}

	@RequestMapping(value="/member/login.kh", method=RequestMethod.POST)
	public String memberLogin(
			@ModelAttribute Member member
			, HttpSession session
			, Model model
			) {
		try {
			Member mOne = service.selectCheckLogin(member);
			if(mOne != null) {
				session.setAttribute("memberId", mOne.getMemberId());
				session.setAttribute("memberName", mOne.getMemberName());
				return "redirect:/index.jsp";
			}else {
				model.addAttribute("msg", "로그인 실패ㅠ");
				model.addAttribute("error", "로그인 실패라능");
				model.addAttribute("url", "/index.jsp");
				return "common/errorPage";
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "관리자에게 문의하세요.");
			model.addAttribute("error", e.getMessage());
			model.addAttribute("url", "/member/register.kh");
			return "common/errorPage";
		}
	}
	
	@RequestMapping(value="/member/logout.kh", method=RequestMethod.GET)
	public String memberLogout(
			HttpSession session
			, Model model
			) {
		if(session != null) {
			session.invalidate();
			return "redirect:/index.jsp";
		}else {
			model.addAttribute("error", "로그아웃을 완료하지 못하였습니다.");
			model.addAttribute("msg", "로그아웃 실패");
			model.addAttribute("url", "/index.jsp");
			return "common/errorPage";
		}
	}
	
	@RequestMapping(value="/member/mypage.kh", method=RequestMethod.GET)
	public String showMypageView(
			@RequestParam("memberId") String memberId
			, Model model
			) {
		try {
			Member member = service.selectOneById(memberId);
			if(member != null) {
				model.addAttribute("member", member);
				return "member/mypage";
			}else {
				model.addAttribute("error", "회원 정보를 가져오지 못했습니다.");
				model.addAttribute("msg", "회원 정보 조회 실패");
				model.addAttribute("url", "/index.jsp");
				return "common/errorPage";
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "관리자에게 문의하세요.");
			model.addAttribute("error", e.getMessage());
			model.addAttribute("url", "/index.jsp");
			return "common/errorPage";
		}
	}
}
