package com.webjjang.notice.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.webjjang.main.controller.Init;
import com.webjjang.notice.vo.NoticeVO;
import com.webjjang.util.exe.Execute;
import com.webjjang.util.page.PageObject;

// Notice Module 에 맞는 메뉴 선택 , 데이터 수집(기능별), 예외 처리
public class NoticeController {
	public String execute(HttpServletRequest request) {
		String uri=request.getRequestURI();
		String jsp=null;
		
		Object result = null;
		
		HttpSession session=request.getSession();
		
		//글 번호 변수
		Long no = 0L;
			
		try { // 정상 처리

			// 메뉴 처리 : CRUD DB 처리 - Controller - Service - DAO
			switch (uri) {
			case "/notice/list.do":
				System.out.println("~~~~NOTICE LIST~~~~");

				// 페이지 네이션을 위한 페이지 오브젝트 생성 및 파라미터로 전달하기
				PageObject po = PageObject.getInstance(request);
				result = Execute.execute(Init.get(uri), po);

				// request에 속성으로 전달하여 jsp 파일에서 뿌리기
				request.setAttribute("list", result);
				request.setAttribute("po", po);

				jsp = "notice/list";

				break;
			case "/notice/view.do":
				System.out.println("~~~~NOTICE VIEW~~~~");
				// 1. 조회수 1증가(글보기), 2. 공지사항 글보기 데이터 가져오기 : 글보기, 글수정
				// 사용자가 보고 싶은 글번호를 입력한다.
				no=Long.parseLong(request.getParameter("no"));
				
				result = Execute.execute(Init.get(uri), no);
				
				request.setAttribute("vo", result);
				po=PageObject.getInstance(request);
				request.setAttribute("po", po);
				
				jsp="notice/view";
				break;
			case "/notice/writeform.do":
				System.out.println("~~~~NOTICE WRITE FORM~~~~");
				jsp="notice/writeform";
				break;
			case "/notice/write.do":
				System.out.println("~~~~NOTICE WRITE~~~~");

				// 데이터 수집 - 사용자 : 제목, 내용, 작성자, 비밀번호
				String title = request.getParameter("title");
				String content = request.getParameter("content");
				String startDate = request.getParameter("startdate");
				String endDate = request.getParameter("enddate");

				// 변수 - vo 저장하고 Service
				NoticeVO vo = new NoticeVO();
				vo.setTitle(title);
				vo.setContent(content);
				vo.setStartDate(startDate);
				vo.setEndDate(endDate);

				// [NoticeController] - NoticeWriteService - NoticeDAO.write(vo)
				Execute.execute(Init.get(uri), vo);
				
				jsp="redirect:list.do";
				session.setAttribute("msg", "공지 등록이 완료되었습니다.");
				
				break;
			case "/notice/updateform.do":
				System.out.println("~~~~NOTICE UPDATE FORM~~~~");
				
				no=Long.parseLong(request.getParameter("no"));
				result=Execute.execute(Init.get("/notice/view.do"), no);
				
				request.setAttribute("vo", result);
				
				jsp="notice/updateform";
				break;
			case "/notice/update.do":
				System.out.println("~~~~NOTICE UPDATE~~~~");

				// 수정할 글번호를 받는다. - 데이터 수집
				no = Long.parseLong(request.getParameter("no"));
				title=request.getParameter("title");
				content=request.getParameter("content");
				startDate=request.getParameter("startdate");
				endDate=request.getParameter("enddate");
				
				vo=new NoticeVO();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContent(content);
				vo.setStartDate(startDate);
				vo.setEndDate(endDate);

				// 수정할 데이터 가져오기 - 글보기 - NoticeViewService
				Execute.execute(Init.get(uri), vo);

				jsp="redirect:view.do?no="+no;
				
				session.setAttribute("msg", "공지 수정이 완료되었습니다.");
				
				break;
			case "/notice/delete.do":
				System.out.println("~~~~NOTICE DELETE~~~~");
				// 데이터 수집 - DB에서 실행에 필요한 데이터 - 글번호
				no = Long.parseLong(request.getParameter("no"));

				// DB 처리
				Execute.execute(Init.get(uri), no);
				
				jsp="redirect:list.do";
				
				session.setAttribute("msg", "공지 삭제가 완료되었습니다.");
				
				break;
			default:
				System.out.println("####################################");
				;
				System.out.println("## 잘못된 메뉴를 선택하셨습니다.          ##");
				;
				System.out.println("## [0~5, 0] 중에서 입력하셔야 합니다.    ##");
				;
				System.out.println("####################################");
				;
				break;
			} // end of switch
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
			System.out.println();
			System.out.println("$%@ 타입 : " + e.getClass().getSimpleName());
			System.out.println("$%@ 내용 : " + e.getMessage());
			
			//예외 전달
			request.setAttribute("e", e);
			jsp="error/500";
		} // end of try~catch
		return jsp;
	} // end of main()
} // end of class
