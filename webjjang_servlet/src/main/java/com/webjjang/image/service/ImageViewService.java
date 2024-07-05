package com.webjjang.image.service;

import com.webjjang.board.dao.BoardDAO;
import com.webjjang.board.vo.BoardVO;
import com.webjjang.image.dao.ImageDAO;
import com.webjjang.image.vo.ImageVO;
import com.webjjang.main.dao.DAO;
import com.webjjang.main.service.Service;

public class ImageViewService implements Service {
	private ImageDAO dao;
	public void setDAO(DAO dao) {
		this.dao=(ImageDAO) dao;
	}
	@Override
	public ImageVO service(Object obj) throws Exception {
		// DB board에서 조회수 1증가 하고 글보기 데이터 가져와서 리턴
		// BoardController - (Execute) - [BoardListService] - BoardDAO.view()
		return dao.view((Long)obj);
	}

}
