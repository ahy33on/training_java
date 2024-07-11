package com.webjjang.notice.service;

import com.webjjang.notice.dao.NoticeDAO;
import com.webjjang.main.dao.DAO;
import com.webjjang.main.service.Service;

public class NoticeDeleteService implements Service {
	private NoticeDAO dao=new NoticeDAO();
	@Override
	public void setDAO(DAO dao) {
		this.dao=(NoticeDAO) dao;
	}
	
	@Override
	public Integer service(Object obj) throws Exception {
		// DB 처리는 DAO에서 처리 - NoticeDAO.delete()
		// NoticeController - (Execute) - [NoticeListService] - NoticeDAO.delete()
		return dao.delete((Long)obj);
	}

}
