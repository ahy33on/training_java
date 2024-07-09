package com.webjjang.member.service;

import com.webjjang.member.dao.MemberDAO;
import com.webjjang.member.vo.LoginVO;
import com.webjjang.member.vo.MemberVO;
import com.webjjang.util.page.PageObject;

import java.util.List;

import com.webjjang.main.dao.DAO;
import com.webjjang.main.service.Service;

public class MemberListService implements Service {
	private MemberDAO dao;
	public void setDAO(DAO dao) {
		this.dao=(MemberDAO) dao;
	}
	@Override
	public List<MemberVO> service(Object obj) throws Exception {
		// DB 처리는 DAO에서 처리 - MemberDAO.list()
		// MemberController - (Execute) - [MemberListService] - MemberDAO.view()
		return dao.list((PageObject)obj);
	}

}
