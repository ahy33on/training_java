package com.webjjang.image.service;

import com.webjjang.image.dao.ImageDAO;
import com.webjjang.image.vo.ImageVO;
import com.webjjang.main.dao.DAO;
import com.webjjang.main.service.Service;

public class ImageUpdateService implements Service {
	private ImageDAO dao;
	public void setDAO(DAO dao) {
		this.dao=(ImageDAO) dao;
	}
	@Override
	public Integer service(Object obj) throws Exception {
		// DB image에서 수정 쿼리 실행해서 데이터 처리
		// ImageController - (Execute) - [ImageListService] - ImageDAO.update()
		return dao.update((ImageVO)obj);
	}

}
