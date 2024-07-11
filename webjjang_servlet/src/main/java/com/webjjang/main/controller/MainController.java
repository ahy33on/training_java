package com.webjjang.main.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.webjjang.util.exe.Execute;
import com.webjjang.util.page.PageObject;

// 댓글 등록, 수정, 삭제 처리
public class MainController {
	public String execute(HttpServletRequest request) {
		HttpSession session = request.getSession();	
		
		// 메뉴 입력
			String uri = request.getRequestURI();
			String jsp=null;
			
			try {
				// 메뉴 처리 : CRUD DB 처리 - Controller - Service - DAO
				switch (uri) {
				case "/main/main.do":
					System.out.println("------Main Controller------");
					
					PageObject po=new PageObject();
					po.setPerPageNum(6);
					
					//메인에 표시할 데이터: 일반게시판 / 이미지게시판
					Object boardlist=null;
					Object imglist=null;
					
					// [BoardController] - BoardWriteService - BoardDAO.write(vo)
					boardlist=Execute.execute(Init.get("/board/list.do"), po);
					imglist=Execute.execute(Init.get("/image/list.do"), po);
					
					request.setAttribute("boardlist", boardlist);
					request.setAttribute("imglist", imglist);
					
					//redirect: -> 자동 페이지 전환(forward로 대신할 수 있다.)
					jsp="main/main";
					session.setAttribute("msg", "메인 화면 생성이 완료 되었습니다.");
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
