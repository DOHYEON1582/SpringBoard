package com.itwillbs.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.itwillbs.domain.BoardVO;
import com.itwillbs.domain.Criteria;
import com.itwillbs.domain.PageVO;
import com.itwillbs.service.BoardService;

@Controller
@RequestMapping(value = "/board/*")
public class BoardController {
	
	
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	// 서비스 객체 주입
	@Inject
	private BoardService bService;
	
	
	// 글쓰기GET : /board/register
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public void registerGET() throws Exception{
		logger.debug(" /board/registerGET() 호출");
		logger.debug("/board/register.jsp 뷰 연결");
		
	}
	
	//글쓰기POST : /board/register
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerPOST(BoardVO vo) throws Exception{
		logger.debug("/board/register.jsp (submit) -> registerPOST() 호출 ");
		
		// 한글처리(필터) 생략
		// 전달정보 (글 정보) 저장
		logger.debug("전달정보 : "+vo);
		
		// 서비스 -> DAO 글쓰기 동작 호출
		bService.regist(vo);
		logger.debug(" 글쓰기 완료! -> 리스트 페이지로 이동");
		
		// 페이지 이동 (list)
		return "redirect:/board/listCri";
	}
	
	// 리스트GET : /board/list
	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public void listGET(Model model, HttpSession session) throws Exception{
		logger.debug("/board/list -> listGET() 실행");
		logger.debug("/board/list.jsp 연결");
		
		// 서비스 -> DAO 게시판 글 목록을 가져오기
		List<BoardVO> boardList = bService.getList();
		logger.debug("list.size : "+boardList.size());
		// 연결된 뷰페이지에 정보 전달
		model.addAttribute("boardList", boardList);
		
		// 조회수 상태 0 : 조회수 증가X , 1 : 조회수 증가 가능 
		session.setAttribute("viewUpdateStatus", 1 );
		
	}
	
	// 본문읽기GET : /board/read?bno=000
	// 본문읽기GET : /board/read?bno=000&page=00&pageSize=00
	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public void readGET(Criteria cri ,@RequestParam("bno") int bno, Model model, HttpSession session) throws Exception{
		// @ModelAttribute : 파라메터 저장 + 영역저장 (1:N관계 ) 
		// @RequestParam : 파라메터 저장 (1:1관계)
		
		logger.debug("/board/read -> readGET() 실행");
		
		// 전달 정보 저장
		logger.debug(" bno : "+bno);
		
		int status = (Integer)session.getAttribute("viewUpdateStatus");
		if(status == 1) {
			// 서비스 -> DAO 게시판 글 조회수 1 증가
			bService.updateViewCnt(bno);
			session.setAttribute("viewUpdateStatus", 0);
		}
		// 서비스 - DAO 게시판 글정보 조회 동작
		BoardVO vo = bService.getBoard(bno);
		
		// 해당 정보를 저장 -> 연결된 뷰 페이지로 전달
		model.addAttribute("vo", vo);
		
		model.addAttribute("cri", cri);
		//model.addAttribute(bService.getBoard(bno));
		
		// 뷰페이지로 이동(/board/read.jsp)
		
	}
	
	// 본문수정GET : /board/modify?bno=000
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public void modifyGET(Criteria cri ,@RequestParam("bno") int bno, Model model) throws Exception{
		logger.debug(" /board/modify -> modifyGET() 호출");
		
		// 전달 받은 정보(bno) 저장
		logger.debug("bno : "+bno);
		// 서비스 -> DAO 특정 글정보 조회 동작
		
		
		// 연결된 뷰페이지에 전달(Model)
		model.addAttribute(bService.getBoard(bno));
		
		model.addAttribute("cri", cri);
		// 이름이없으면 타입으로 저장.
		
		// 연결된 뷰페이지 (/board/modify.jsp)
	}
	
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modifyPOST(Criteria cri, BoardVO vo,Model model) throws Exception{
		logger.debug("modifyPOST() 호출");
		
		// 한글처리 인코딩
		
		// 전달 정보 저장
		logger.debug("BoardVO : "+vo);
		// 서비스 -> DAO 게시판 글 정보 수정
		bService.updateBoard(vo);
		
		
		// 수정완료 후에 리스트 페이지로 이동(redirect)
		
		
		return "redirect:/board/listCri?page="+cri.getPage()+"&pageSize="+cri.getPageSize();
	}
	
	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public String removePOST(RedirectAttributes rttr,Criteria cri ,@RequestParam("bno") int bno) throws Exception{
		logger.debug("removePOST() 호출");
		
		bService.deleteBoard(bno);
		
		//rttr.addFlashAttribute("cri", cri);
		//rttr.addFlashAttribute("page", cri.getPage());
		//rttr.addFlashAttribute("pageSize", cri.getPageSize());
		
		return "redirect:/board/listCri?page="+cri.getPage()+"&pageSize="+cri.getPageSize();
		//return "redirect:/board/listCri";
	}
	
	// 리스트GET : /board/listCri
	// 리스트GET : /board/listCri?page=2
	@RequestMapping(value = "/listCri",method = RequestMethod.GET)
	public void listCriGET(Criteria cri ,Model model, HttpSession session) throws Exception{
		logger.debug("/board/listCri -> listCriGET() 실행");
		logger.debug("/board/list.jsp 연결");
			
		// 페이징 처리 객체 
		//Criteria cri = new Criteria();
		PageVO pageVO = new PageVO();
		pageVO.setCri(cri);
		pageVO.setTotalCount(bService.getBoardListCount()); // 총 개수 직접 계산
		
			
		// 서비스 -> DAO 게시판 글 목록을 가져오기
		List<BoardVO> boardList = bService.getListCri(cri);
		logger.debug("list.size : "+boardList.size());
		// 연결된 뷰페이지에 정보 전달
		model.addAttribute("boardList", boardList);
		// 페이징 처리 정보
		model.addAttribute("cri", cri);

		model.addAttribute("pageVO", pageVO);
		
		// 조회수 상태 0 : 조회수 증가X , 1 : 조회수 증가 가능 
		session.setAttribute("viewUpdateStatus", 1 );
			
	}
	
	
	

}// controller
