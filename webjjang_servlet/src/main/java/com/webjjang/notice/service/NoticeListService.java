package com.webjjang.notice.service;

import java.util.List;

import com.webjjang.notice.dao.NoticeDAO;
import com.webjjang.notice.vo.NoticeVO;
import com.webjjang.util.page.PageObject;
import com.webjjang.main.dao.DAO;
import com.webjjang.main.service.Service;

public class NoticeListService implements Service {

	private NoticeDAO dao=new NoticeDAO();
	@Override
	public void setDAO(DAO dao) {
		this.dao=(NoticeDAO) dao;
	}
	
	@Override
	public List<NoticeVO> service(Object obj) throws Exception {
		// NoticeController - (Execute) - [NoticeListService] - NoticeDAO.list()
		PageObject po=(PageObject)obj;
		po.setTotalRow((dao).getTotalrow(po));
		return dao.list(po);
	}

}
