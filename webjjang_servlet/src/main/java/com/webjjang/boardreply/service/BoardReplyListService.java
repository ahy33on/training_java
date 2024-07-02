package com.webjjang.boardreply.service;

import java.util.List;

import com.webjjang.boardreply.dao.BoardReplyDAO;
import com.webjjang.boardreply.vo.BoardReplyVO;
import com.webjjang.main.dao.DAO;
import com.webjjang.main.service.Service;
import com.webjjang.util.page.ReplyPageObject;

public class BoardReplyListService implements Service {
	private BoardReplyDAO dao;
	public void setDAO(DAO dao) {
		this.dao=(BoardReplyDAO) dao;
	}
	@Override
	public List<BoardReplyVO> service(Object obj) throws Exception {
		
		ReplyPageObject pageObject=(ReplyPageObject)obj;
		//전체 데이터 개수 구하기
		pageObject.setTotalRow((dao).getTotalrow(pageObject));
		
		// DB 처리는 DAO에서 처리 - BoardReplyDAO.list()
		// BoardController - (Execute) - [BoardListService] - BoardDAO.list()
		return dao.list(pageObject);
	}

}
