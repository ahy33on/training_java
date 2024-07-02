package com.webjjang.main.controller;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.webjjang.board.controller.BoardController;
import com.webjjang.boardreply.controller.BoardReplyController;
import com.webjjang.image.controller.ImageController;
import com.webjjang.member.controller.MemberController;

/**
 * Servlet implementation class DispatcherServlet
 */
//@WebServlet(urlPatterns="*.do", loadOnStartup = 1)
/* 톰캣 서버에 의해 단 한 번만 생성된다.
프로그램은 url을 통해 서블릿과 연결되는데, 연결 위해 서블릿이 등록되어 있어야 한다.
서블릿 등록을 위해서는 어노테이션(@WebServlet)을 지정하거나
web.xml에 등록해야 한다.
 */
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private MemberController mc=new MemberController();
	private BoardController bc=new BoardController();
	private ImageController imgc=new ImageController();
	private BoardReplyController brc=new BoardReplyController();
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		//드라이버 확인(getConnection) / 객체 생성 처리(forname)
		System.out.println("DispatcherServlet.init()-----------------");
		
		try {
			//객체 생성, 초기화 및 조립
			Class.forName("com.webjjang.main.controller.Init");
			System.out.println("Init class 초기화 완료");
			//오라클 드라이버 확인+로딩
			Class.forName("com.webjjang.util.db.DB");
			System.out.println("DB 초기화 완료");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String uri=request.getRequestURI();
		System.out.println("uri: "+uri);
		
		int pos=uri.indexOf("/", 1);
		if(pos==-1) {
			request.getRequestDispatcher("/WEB-INF/views/error/404.jsp").forward(request, response);
			return;
		}
				
		String module=uri.substring(0, pos);
		System.out.println("module: "+module);
		
		//선택된 메뉴 처리를 위해 modul을 속성으로 담기
		request.setAttribute("module", module);
		
		String jsp=null;
		
		switch(module) {
		case "/member":
			jsp=mc.execute(request);
			break;
		case "/board":
			jsp=bc.execute(request);
			break;
		case "/boardreply":
			jsp=brc.execute(request);
			break;
		case "/image":
			jsp=imgc.execute(request);
			break;
		default:
			request.getRequestDispatcher("/WEB-INF/views/error/404.jsp").forward(request, response);
			return;
		}
		System.out.println("/WEB-INF/views/"+jsp+".jsp"+"---------------");
		//jsp 앞에 redirect:가 붙어있으면, redirect
		if(jsp.indexOf("redirect:")==0) {
			response.sendRedirect(jsp.substring("redirect:".length()));
		}else //jsp 앞에 redirect:가 없으면, forward
			request.getRequestDispatcher("/WEB-INF/views/"+jsp+".jsp").forward(request, response);
	}

}
