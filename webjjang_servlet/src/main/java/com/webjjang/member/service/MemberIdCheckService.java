package com.webjjang.member.service;

import com.webjjang.member.dao.MemberDAO;
import com.webjjang.main.dao.DAO;
import com.webjjang.main.service.Service;

public class MemberIdCheckService implements Service {
	private MemberDAO dao;
	public void setDAO(DAO dao) {
		this.dao=(MemberDAO) dao;
	}
	@Override
	public String service(Object obj) throws Exception {
		// DB 처리는 DAO에서 처리 - MemberDAO.list()
		// MemberController - (Execute) - [MemberListService] - MemberDAO.view()
		return dao.idCheck((String)obj);
	}

}
