package com.webjjang.util.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.webjjang.member.vo.LoginVO;

/**
 * Servlet Filter implementation class AuthFilter
 */
//@WebFilter("/AuthFilter")
public class AuthFilter extends HttpFilter implements Filter {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//URI와 권한 MAPPING
	private static Map<String, Integer> authMap = new HashMap<String, Integer>();

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		System.out.println("AuthFilter.doFilter()----------------");
		
		//권한 처리
		HttpServletRequest req=(HttpServletRequest)request;
		HttpServletResponse res=(HttpServletResponse)response;
		String uri=req.getRequestURI();
		System.out.println("auth_uri: "+uri);
		
		//로그인이 필요한 uri에 대하여 권한 처리
		//접속하고자 하는 페이지(uri)에 할당된 권한(gradeno) 가져오기
		Integer pgradeno=authMap.get(uri);
		LoginVO loginVO=null;
		//session에 있는 login 속성을 이용하기 위해 request에서 session 가져오기
		HttpSession session=req.getSession();
		
		if(pgradeno!=null) {
			loginVO=(LoginVO) session.getAttribute("login");
			if(loginVO==null) { //로그인 페이지로 이동
				session.setAttribute("msg", "로그인 상태가 아닙니다. 로그인하세요.");
				res.sendRedirect("/member/loginform.do");
				return;
			}//로그아웃 상태 처리의 끝
			
			//관리자 권한 처리
			Integer ugradeno=loginVO.getGradeNo();
			if(pgradeno==9) {
				if(pgradeno>ugradeno) {
					req.getRequestDispatcher("/WEB-INF/views/error/authority.jsp").forward(req, res);
					return;
				}
			}
		}
		
		// pass the request along the filter chain
		// 필터를 거쳐 dispatcherServlet에 도달하도록 하는 코드
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		//서버 구동시 최초 1회에 대해 실행됨
		System.out.println(" 메롱메롱 auth filter init()이랍니다 ");
		
		//URI-권한 MAPPING
		authMap.put("/image/writeform.do", 1);
		authMap.put("/image/write.do", 1);
		authMap.put("/image/updateform.do", 1);
		authMap.put("/image/update.do", 1);
		authMap.put("/image/delete.do", 1);
		authMap.put("/image/list.do", 9);
		
		authMap.put("/member/list.do", 9);
		authMap.put("/member/changeGrade.do", 9);
	}

}
