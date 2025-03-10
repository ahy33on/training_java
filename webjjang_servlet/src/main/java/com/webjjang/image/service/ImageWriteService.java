package com.webjjang.image.service;

import com.webjjang.image.dao.ImageDAO;
import com.webjjang.image.vo.ImageVO;
import com.webjjang.main.dao.DAO;
import com.webjjang.main.service.Service;

public class ImageWriteService implements Service {
	private ImageDAO dao;
	public void setDAO(DAO dao) {
		this.dao=(ImageDAO) dao;
	}
	@Override
	public Integer service(Object obj) throws Exception {
		// DB image에서 리스트 쿼리 실행해서 데이터 가져오기 - 리턴
		// DB 처리는 DAO에서 처리 - ImageDAO.write()
		// ImageController - (Execute) - [ImageListService] - ImageDAO.list()
		return dao.write((ImageVO)obj);
	}

}
