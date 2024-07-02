package com.webjjang.boardreply.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.webjjang.boardreply.vo.BoardReplyVO;
import com.webjjang.main.controller.Init;
import com.webjjang.util.exe.Execute;
import com.webjjang.util.page.ReplyPageObject;

// 댓글 등록, 수정, 삭제 처리
public class BoardReplyController {
	public String execute(HttpServletRequest request) {
		HttpSession session = request.getSession();	
		
		// 메뉴 입력
			String uri = request.getRequestURI();
			String jsp=null;
			Object result = null;
			Long no = 0L;
			long inc = 0L;
			
			try { // 정상 처리
				ReplyPageObject rpo=ReplyPageObject.getInstance(request);
				// 메뉴 처리 : CRUD DB 처리 - Controller - Service - DAO
				switch (uri) {
				case "/boardreply/write.do":
					System.out.println("------BoardReply 등록 처리------");
					
					//데이터 수집: name으로 전달된 데이터 수집
					String content=request.getParameter("content");
					String writer=request.getParameter("writer");
					String pw=request.getParameter("pw");
					
					// 변수 - vo 저장하고 Service
					BoardReplyVO vo = new BoardReplyVO();
					vo.setNo(rpo.getNo());
					vo.setContent(content);
					vo.setWriter(writer);
					vo.setPw(pw);
					
					// [BoardController] - BoardWriteService - BoardDAO.write(vo)
					Execute.execute(Init.get(uri), vo);
					
					//redirect: -> 자동 페이지 전환(forward로 대신할 수 있다.)
					jsp="redirect:/board/view.do?no="+rpo.getNo()+"&inc=0&"
							+rpo.getPageObject().getPageQuery() //일반 게시판 페이지 정보
							+"&"+rpo.getPageQuery(); //일반 게시판 댓글 페이지 정보
					session.setAttribute("msg", "댓글 등록이 성공적으로 되었습니다.");
					break;
				case "/boardreply/update.do":
					System.out.println("------BoardReply 수정 처리------");
					
					no = Long.parseLong(request.getParameter("no"));
					content=request.getParameter("content");
					writer=request.getParameter("writer");
					pw=request.getParameter("pw");
					
					// 변수 - vo 저장하고 Service
					vo = new BoardReplyVO();
					vo.setNo(no);
					vo.setContent(content);
					vo.setWriter(writer);
					vo.setPw(pw);
					
					//페이지 정보 받고 uri에 셋팅
					jsp="redirect:list.do?no="+no+"&inc=0&"
							+rpo.getPageQuery();
					
					
					// [BoardController] - BoardWriteService - BoardDAO.write(vo)
					Execute.execute(Init.get(uri), vo);
					
					break;
				case "/boardreply/delete.do":
					System.out.println("------BoardReply 삭제 처리------");
					// 데이터 수집 - DB에서 실행에 필요한 데이터 - 글번호, 비밀번호 - BoardVO
					vo = new BoardReplyVO();
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
					
					rpo=ReplyPageObject.getInstance(request);
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
