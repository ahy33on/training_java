package com.webjjang.ajax.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.webjjang.member.vo.LoginVO;
import com.webjjang.main.controller.Init;
import com.webjjang.util.exe.Execute;

// Member Module 에 맞는 메뉴 선택 , 데이터 수집(기능별), 예외 처리
public class AjaxController {
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
			
			try { // 정상 처리
				// 메뉴 처리 : CRUD DB 처리 - Controller - Service - DAO
				switch (uri) {
				case "/ajax/idCheck.do":
					System.out.println("---------ajax id check---------");
					
					id=request.getParameter("id");
					System.out.println(id);
					
					result = Execute.execute(Init.get(uri), id);
					
					request.setAttribute("id", result);
					
					jsp="ajax/idCheck";
					break;
				default:
					jsp="error/404_noModule";
					break;
				} // end of switch
			} catch (Exception e) {
				// TODO: handle exception
				// e.printStackTrace();
				request.setAttribute("e", e);
				
				jsp="error/500_noModule";
				
				System.out.println("$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@");
				System.out.println("$%@ 타입 : " + e.getClass().getSimpleName());
				System.out.println("$%@ 내용 : " + e.getMessage());
				System.out.println("$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@");
			} // end of try~catch
		System.out.println("jsp: "+jsp);
		return jsp;
	} // end of main()
	
} // end of class
