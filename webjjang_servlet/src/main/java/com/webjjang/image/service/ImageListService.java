package com.webjjang.image.service;

import java.util.List;

import com.webjjang.image.dao.ImageDAO;
import com.webjjang.image.vo.ImageVO;
import com.webjjang.main.dao.DAO;
import com.webjjang.main.service.Service;
import com.webjjang.util.page.PageObject;

public class ImageListService implements Service {
	private ImageDAO dao;
	public void setDAO(DAO dao) {
		this.dao=(ImageDAO) dao;
	}
	@Override
	public List<ImageVO> service(Object obj) throws Exception {
		
		PageObject pageObject=(PageObject)obj;
		pageObject.setTotalRow(dao.getTotalrow(pageObject));
		// DB image에서 리스트 쿼리 실행해서 데이터 가져오기 - 리턴
		// DB 처리는 DAO에서 처리 - ImageDAO.list()
		// ImageController - (Execute) - [ImageListService] - ImageDAO.list()
		return dao.list(pageObject);
	}

}
