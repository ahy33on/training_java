package com.webjjang.board.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.webjjang.board.service.BoardDeleteService;
import com.webjjang.board.service.BoardListService;
import com.webjjang.board.service.BoardUpdateService;
import com.webjjang.board.service.BoardViewService;
import com.webjjang.board.service.BoardWriteService;
import com.webjjang.board.vo.BoardVO;
import com.webjjang.main.controller.Init;
import com.webjjang.util.exe.Execute;
import com.webjjang.util.io.BoardPrint;
import com.webjjang.util.io.In;
import com.webjjang.util.page.PageObject;
import com.webjjang.util.page.ReplyPageObject;

// Board Module 에 맞는 메뉴 선택 , 데이터 수집(기능별), 예외 처리
public class BoardController {
	public String execute(HttpServletRequest request) {
			// 메뉴 입력
			String uri = request.getRequestURI();
			String jsp=null;
			Object result = null;
			Long no = 0L;
			long inc = 0L;
			try { // 정상 처리
			
				// 메뉴 처리 : CRUD DB 처리 - Controller - Service - DAO
				switch (uri) {
				case "/board/list.do":
					// [BoardController] - (Execute) - BoardListService - BoardDAO.list()
					System.out.println("1.일반게시판 리스트");
					// DB에서 데이터 가져오기 - 가져온 데이터는 List<BoardVO>
					PageObject po=PageObject.getInstance(request);
					result = Execute.execute(Init.get(uri), po);
					// 결과 데이터 출력하기
					request.setAttribute("list", result);
					//pageObject 담기
					request.setAttribute("po", po);
					jsp="board/list";
					break;
				case "/board/view.do":
					System.out.println("2.일반게시판 글보기");
					// 1. 조회수 1증가(글보기), 2. 일반게시판 글보기 데이터 가져오기 : 글보기, 글수정
					no = Long.parseLong(request.getParameter("no"));
					inc=Long.parseLong(request.getParameter("inc"));
					// 전달 데이터 - 글번호, 조회수 증가 여부(1:증가, 0:증가 안함) : 배열 또는 Map
					result = Execute.execute(Init.get(uri), new Long[]{no, inc});
					//pagination: page, perpagenum, no, replyPage, replyPerPageNum 전달 필요
					ReplyPageObject replyPageObject=ReplyPageObject.getInstance(request);
					Object replyresult = Execute.execute(Init.get("/boardreply/list.do"), replyPageObject);
					// 게시판 글보기 출력 : BoardPrint
					request.setAttribute("vo", result);
					request.setAttribute("replyList", replyresult);
					//댓글 페이지 객체 담기
					request.setAttribute("replyPageObject", replyPageObject);
					
					jsp="board/view";
					break;
				case "/board/writeform.do":
					System.out.println("3-1.일반게시판 글 등록 폼");
					jsp="board/writeform";
					break;
				case "/board/write.do":
					System.out.println("3-2.일반게시판 글 등록 처리");
					
					//데이터 수집: name으로 전달된 데이터 수집
					String title=request.getParameter("title");
					String content=request.getParameter("content");
					String writer=request.getParameter("writer");
					String pw=request.getParameter("pw");
					
					// 변수 - vo 저장하고 Service
					BoardVO vo = new BoardVO();
					vo.setTitle(title);
					vo.setContent(content);
					vo.setWriter(writer);
					vo.setPw(pw);
					
					// [BoardController] - BoardWriteService - BoardDAO.write(vo)
					Execute.execute(Init.get(uri), vo);
					
					//redirect: -> 자동 페이지 전환(forward로 대신할 수 있다.)
					jsp="redirect:list.do?perPageNum="
							+request.getParameter("perPageNum");
					
					break;
				case "/board/updateform.do":
					System.out.println("4-1.일반게시판 글 수정 폼");
					no = Long.parseLong(request.getParameter("no"));
					result = Execute.execute(Init.get("/board/view.do"), new Long[]{no, 0L});
					// 게시판 글보기 출력 : BoardPrint
					request.setAttribute("vo", result);
					jsp="board/updateform";
					break;
				case "/board/update.do":
					System.out.println("4-2.일반게시판 글 수정 처리");
					
					no = Long.parseLong(request.getParameter("no"));
					title=request.getParameter("title");
					content=request.getParameter("content");
					writer=request.getParameter("writer");
					pw=request.getParameter("pw");
					
					// 변수 - vo 저장하고 Service
					vo = new BoardVO();
					vo.setNo(no);
					vo.setTitle(title);
					vo.setContent(content);
					vo.setWriter(writer);
					vo.setPw(pw);
					
					//페이지 정보 받고 uri에 셋팅
					po=PageObject.getInstance(request);
					jsp="redirect:view.do?no="+no+"&inc=0&"
							+po.getPageQuery();
					
					
					// [BoardController] - BoardWriteService - BoardDAO.write(vo)
					Execute.execute(Init.get(uri), vo);
					
					break;
				case "/board/delete.do":
					System.out.println("5.일반게시판 글삭제");
					// 데이터 수집 - DB에서 실행에 필요한 데이터 - 글번호, 비밀번호 - BoardVO
					vo = new BoardVO();
					no = Long.parseLong(request.getParameter("no"));
					pw=request.getParameter("pw");
					vo.setNo(no);
					vo.setPw(pw);
					
					// DB 처리
					Execute.execute(Init.get(uri), vo);
					System.out.println();
					System.out.println("***************************");
					System.out.println("**  " + vo.getNo() + "글이 삭제되었습니다.  **");
					System.out.println("***************************");
					
					po=PageObject.getInstance(request);
					jsp="redirect:list.do?perPageNum="
							+request.getParameter("perPageNum");
					
					break;
	
				default:
					//404 error
					jsp="error/404";
					break;
				} // end of switch
			} catch (Exception e) {
				jsp="error/500";
				request.setAttribute("e", e);
				
				// e.printStackTrace();
				System.out.println();
				System.out.println("$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$");
				System.out.println("$%@            << 오류 출력 >>            $%@");
				System.out.println("$%@ 타입 : " + e.getClass().getSimpleName());
				System.out.println("$%@ 내용 : " + e.getMessage());
				System.out.println("$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$");
			} // end of try~catch
		System.out.println("jsp: "+jsp);
		return jsp;
	} // end of main()
	
} // end of class
