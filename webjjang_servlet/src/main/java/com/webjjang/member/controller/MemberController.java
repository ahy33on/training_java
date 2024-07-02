package com.webjjang.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.webjjang.member.vo.LoginVO;
import com.webjjang.member.vo.MemberVO;
import com.webjjang.main.controller.Init;
import com.webjjang.util.exe.Execute;
import com.webjjang.util.page.PageObject;
import com.webjjang.util.page.ReplyPageObject;

// Member Module 에 맞는 메뉴 선택 , 데이터 수집(기능별), 예외 처리
public class MemberController {
	public String execute(HttpServletRequest request) {
			//로그인 처리를 위해 session을 불러온다.
			HttpSession session=request.getSession();
			
			//loginVO의 id 정보를 가져오자.
			String id=null;
			LoginVO vo=(LoginVO)session.getAttribute("loginvo");
			if(vo!=null)
				id=vo.getId();
			
			//uri
			String uri = request.getRequestURI();
			String jsp=null;
			Object result = null;
			Long no = 0L;
			
			try { // 정상 처리
			
				// 메뉴 처리 : CRUD DB 처리 - Controller - Service - DAO
				switch (uri) {
				case "/member/loginform.do":
					System.out.println("---------member Loginform---------");
					jsp="member/loginform";
					break;
				case "/member/login.do":
					System.out.println("---------member Login---------");
					
					//데이터 수집: name으로 전달된 데이터 수집
					id=request.getParameter("id");
					String pw=request.getParameter("pw");
					
					// 변수 - vo 저장하고 Service
					LoginVO loginvo = new LoginVO();
					loginvo.setId(id);
					loginvo.setPw(pw);
					
					System.out.println("id: "+id);
					System.out.println("pw: "+pw);
					
					// [MemberController] - MemberWriteService - MemberDAO.write(vo)
					loginvo=(LoginVO) Execute.execute(Init.get(uri), loginvo);
					session.setAttribute("login", loginvo);
					
					//redirect: -> 자동 페이지 전환(forward로 대신할 수 있다.)
					//로그인의 경우, main이나 실행하려고 했던 uri로 이동해야 함.
					jsp="redirect:/board/list.do";
					
					session.setAttribute("msg", "로그인 처리가 완료되었습니다.");
					break;
				case "/member/logout.do":
					System.out.println("---------member Logout---------");
					session.removeAttribute("login");
					session.setAttribute("msg", "로그아웃 처리가 완료되었습니다.");
					jsp="redirect:/board/list.do";
					break;
				case "/member/list.do":
					// [MemberController] - (Execute) - MemberListService - MemberDAO.list()
					System.out.println("1.일반게시판 리스트");
					// DB에서 데이터 가져오기 - 가져온 데이터는 List<MemberVO>
					PageObject po=PageObject.getInstance(request);
					result = Execute.execute(Init.get(uri), po);
//					PageObject 데이터 확인
					System.out.println("MemberController.execute().pageObject: "+po);
					// 가져온 데이터 출력하기
					request.setAttribute("list", result);
					//pageObject 담기
					request.setAttribute("po", po);
					jsp="member/list";
					break;
				case "/member/view.do":
					System.out.println("2.일반게시판 글보기");
					// 1. 조회수 1증가(글보기), 2. 일반게시판 글보기 데이터 가져오기 : 글보기, 글수정
					no = Long.parseLong(request.getParameter("no"));
					// 전달 데이터 - 글번호, 조회수 증가 여부(1:증가, 0:증가 안함) : 배열 또는 Map
//					result = Execute.execute(Init.get(uri), new Long[]{no, inc});
					//pagination: page, perpagenum, no, replyPage, replyPerPageNum 전달 필요
					ReplyPageObject replyPageObject=ReplyPageObject.getInstance(request);
					Object replyresult = Execute.execute(Init.get("/Memberreply/list.do"), replyPageObject);
					// 게시판 글보기 출력 : MemberPrint
					request.setAttribute("vo", result);
					request.setAttribute("replyList", replyresult);
					//댓글 페이지 객체 담기
					request.setAttribute("replyPageObject", replyPageObject);
					
					jsp="member/view";
					break;
				case "/member/updateform.do":
					System.out.println("4-1.일반게시판 글 수정 폼");
					no = Long.parseLong(request.getParameter("no"));
					result = Execute.execute(Init.get("/Member/view.do"), new Long[]{no, 0L});
					// 게시판 글보기 출력 : MemberPrint
					request.setAttribute("vo", result);
					jsp="member/updateform";
					break;
				case "/Member/update.do":
					System.out.println("4-2.일반게시판 글 수정 처리");
					
					no = Long.parseLong(request.getParameter("no"));
//					title=request.getParameter("title");
//					content=request.getParameter("content");
//					writer=request.getParameter("writer");
					pw=request.getParameter("pw");
					
					// 변수 - vo 저장하고 Service
//					vo = new MemberVO();
//					vo.setNo(no);
//					vo.setTitle(title);
//					vo.setContent(content);
//					vo.setWriter(writer);
					vo.setPw(pw);
					
					//페이지 정보 받고 uri에 셋팅
					po=PageObject.getInstance(request);
					jsp="redirect:view.do?no="+no+"&inc=0&"
							+po.getPageQuery();
					
					
					// [MemberController] - MemberWriteService - MemberDAO.write(vo)
					Execute.execute(Init.get(uri), vo);
					
					break;
				case "/member/delete.do":
					System.out.println("5.일반게시판 글삭제");
					// 데이터 수집 - DB에서 실행에 필요한 데이터 - 글번호, 비밀번호 - MemberVO
//					vo = new MemberVO();
					no = Long.parseLong(request.getParameter("no"));
					pw=request.getParameter("pw");
//					vo.setNo(no);
					vo.setPw(pw);
					
					// DB 처리
					Execute.execute(Init.get(uri), vo);
					System.out.println();
					System.out.println("***************************");
//					System.out.println("**  " + vo.getNo() + "글이 삭제되었습니다.  **");
					System.out.println("***************************");
					
					po=PageObject.getInstance(request);
					jsp="redirect:list.do?perPageNum="
							+request.getParameter("perPageNum");
					
					break;
	
				default:
					System.out.println("####################################");;
					System.out.println("## 잘못된 메뉴를 선택하셨습니다.          ##");;
					System.out.println("## [0~5, 0] 중에서 입력하셔야 합니다.    ##");;
					System.out.println("####################################");;
					break;
				} // end of switch
			} catch (Exception e) {
				// TODO: handle exception
				// e.printStackTrace();
				System.out.println();
				System.out.println("$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@");
				System.out.println("$%@ << 오류 출력 >>                         $%@");
				System.out.println("$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@");
				System.out.println("$%@ 타입 : " + e.getClass().getSimpleName());
				System.out.println("$%@ 내용 : " + e.getMessage());
				System.out.println("$%@ 조치 : 데이터를 확인 후 다시 실행해 보세요.");
				System.out.println("$%@     : 계속 오류가 나면 전산담당자에게 연락하세요.");
				System.out.println("$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@");
			} // end of try~catch
		System.out.println("jsp: "+jsp);
		return jsp;
	} // end of main()
	
} // end of class
