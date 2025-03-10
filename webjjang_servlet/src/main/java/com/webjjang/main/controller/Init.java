package com.webjjang.main.controller;

import java.util.HashMap;
import java.util.Map;

import com.webjjang.board.dao.BoardDAO;
import com.webjjang.board.service.BoardDeleteService;
import com.webjjang.board.service.BoardListService;
import com.webjjang.board.service.BoardUpdateService;
import com.webjjang.board.service.BoardViewService;
import com.webjjang.board.service.BoardWriteService;
import com.webjjang.boardreply.dao.BoardReplyDAO;
import com.webjjang.boardreply.service.BoardReplyDeleteService;
import com.webjjang.boardreply.service.BoardReplyListService;
import com.webjjang.boardreply.service.BoardReplyUpdateService;
import com.webjjang.boardreply.service.BoardReplyWriteService;
import com.webjjang.image.dao.ImageDAO;
import com.webjjang.image.service.ImageChangeService;
import com.webjjang.image.service.ImageDeleteService;
import com.webjjang.image.service.ImageListService;
import com.webjjang.image.service.ImageUpdateService;
import com.webjjang.image.service.ImageViewService;
import com.webjjang.image.service.ImageWriteService;
import com.webjjang.main.dao.DAO;
import com.webjjang.main.service.Service;
import com.webjjang.member.dao.MemberDAO;
import com.webjjang.member.service.MemberChangeGradeService;
import com.webjjang.member.service.MemberChangeStatusService;
import com.webjjang.member.service.MemberIdCheckService;
import com.webjjang.member.service.MemberListService;
import com.webjjang.member.service.MemberLoginService;
import com.webjjang.member.service.MemberWriteService;
import com.webjjang.notice.dao.NoticeDAO;
import com.webjjang.notice.service.NoticeDeleteService;
import com.webjjang.notice.service.NoticeListService;
import com.webjjang.notice.service.NoticeUpdateService;
import com.webjjang.notice.service.NoticeViewService;
import com.webjjang.notice.service.NoticeWriteService;

public class Init {
	//uri에 맞는 서비스를 매핑하는 객체
	private static Map<String, Service> serviceMap=new HashMap<String, Service>();	
	//class명에 맞는 dao를 매핑하는 객체
	private static Map<String, DAO> daoMap=new HashMap<String, DAO>();	
	
	//static 초기화 블록: 클래스 호출 시에 한 번만 실행됨
	static{
		//----------일반 게시판 객체 생성과 조립-----------
		//dao 매핑, service 매핑
		daoMap.put("boardDAO", new BoardDAO());
		
		serviceMap.put("/board/list.do", new BoardListService());
		serviceMap.put("/board/view.do", new BoardViewService());
		serviceMap.put("/board/write.do", new BoardWriteService());
		serviceMap.put("/board/update.do", new BoardUpdateService());
		serviceMap.put("/board/delete.do", new BoardDeleteService());
		//SERVICE-DAO 매핑
		serviceMap.get("/board/list.do").setDAO(daoMap.get("boardDAO"));
		serviceMap.get("/board/view.do").setDAO(daoMap.get("boardDAO"));
		serviceMap.get("/board/write.do").setDAO(daoMap.get("boardDAO"));
		serviceMap.get("/board/update.do").setDAO(daoMap.get("boardDAO"));
		serviceMap.get("/board/delete.do").setDAO(daoMap.get("boardDAO"));
		
		//----------공지 게시판 객체 생성과 조립-----------
		//dao 매핑, service 매핑
		daoMap.put("noticeDAO", new NoticeDAO());
		
		serviceMap.put("/notice/list.do", new NoticeListService());
		serviceMap.put("/notice/view.do", new NoticeViewService());
		serviceMap.put("/notice/write.do", new NoticeWriteService());
		serviceMap.put("/notice/update.do", new NoticeUpdateService());
		serviceMap.put("/notice/delete.do", new NoticeDeleteService());
		//SERVICE-DAO 매핑
		serviceMap.get("/notice/list.do").setDAO(daoMap.get("noticeDAO"));
		serviceMap.get("/notice/view.do").setDAO(daoMap.get("noticeDAO"));
		serviceMap.get("/notice/write.do").setDAO(daoMap.get("noticeDAO"));
		serviceMap.get("/notice/update.do").setDAO(daoMap.get("noticeDAO"));
		serviceMap.get("/notice/delete.do").setDAO(daoMap.get("noticeDAO"));
		
		//----------일반 게시판 댓글 객체 생성과 조립-----------
		daoMap.put("boardReplyDAO", new BoardReplyDAO());

		serviceMap.put("/boardreply/list.do", new BoardReplyListService());
		serviceMap.put("/boardreply/write.do", new BoardReplyWriteService());
		serviceMap.put("/boardreply/update.do", new BoardReplyUpdateService());
		serviceMap.put("/boardreply/delete.do", new BoardReplyDeleteService());
		
		serviceMap.get("/boardreply/list.do").setDAO(daoMap.get("boardReplyDAO"));
		serviceMap.get("/boardreply/write.do").setDAO(daoMap.get("boardReplyDAO"));
		serviceMap.get("/boardreply/update.do").setDAO(daoMap.get("boardReplyDAO"));
		serviceMap.get("/boardreply/delete.do").setDAO(daoMap.get("boardReplyDAO"));
		
		
		daoMap.put("MemberDAO", new MemberDAO());
		
		serviceMap.put("/member/login.do", new MemberLoginService());
		serviceMap.put("/member/write.do", new MemberWriteService());
		serviceMap.put("/member/list.do", new MemberListService());
		serviceMap.put("/member/changeGrade.do", new MemberChangeGradeService());
		serviceMap.put("/member/changeStatus.do", new MemberChangeStatusService());
		serviceMap.put("/ajax/idCheck.do", new MemberIdCheckService());
		
		serviceMap.get("/member/login.do").setDAO(daoMap.get("MemberDAO"));
		serviceMap.get("/member/list.do").setDAO(daoMap.get("MemberDAO"));
		serviceMap.get("/member/write.do").setDAO(daoMap.get("MemberDAO"));
		serviceMap.get("/member/changeGrade.do").setDAO(daoMap.get("MemberDAO"));
		serviceMap.get("/member/changeStatus.do").setDAO(daoMap.get("MemberDAO"));
		serviceMap.get("/ajax/idCheck.do").setDAO(daoMap.get("MemberDAO"));
		
		
		daoMap.put("ImageDAO", new ImageDAO());
		
		serviceMap.put("/image/list.do", new ImageListService());
		serviceMap.put("/image/view.do", new ImageViewService());
		serviceMap.put("/image/write.do", new ImageWriteService());
		serviceMap.put("/image/update.do", new ImageUpdateService());
		serviceMap.put("/image/delete.do", new ImageDeleteService());
		serviceMap.put("/image/changeimage.do", new ImageChangeService());
		
		serviceMap.get("/image/list.do").setDAO(daoMap.get("ImageDAO"));
		serviceMap.get("/image/view.do").setDAO(daoMap.get("ImageDAO"));
		serviceMap.get("/image/write.do").setDAO(daoMap.get("ImageDAO"));
		serviceMap.get("/image/update.do").setDAO(daoMap.get("ImageDAO"));
		serviceMap.get("/image/delete.do").setDAO(daoMap.get("ImageDAO"));
		serviceMap.get("/image/changeimage.do").setDAO(daoMap.get("ImageDAO"));
		
		
		System.out.println("Init.static 초기화 블록 마지막 부분 ----------");
	}//end of static
	
	public static Service get(String uri) {
		return serviceMap.get(uri);
	}

}