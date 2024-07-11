package com.webjjang.member.dao;

import java.util.ArrayList;
import java.util.List;

import com.webjjang.main.dao.DAO;
import com.webjjang.member.vo.LoginVO;
import com.webjjang.member.vo.MemberVO;
import com.webjjang.util.db.DB;
import com.webjjang.util.page.PageObject;

public class MemberDAO extends DAO{

	// 필요한 객체 선언 - 상속
	// 접속 정보 - DB 사용 - connection을 가져오게 하는 메서드만 이용
	
	// 1. 회원 리스트 처리
	// MemberController - (Execute) - MemberListService - [MemberDAO.list()]
	public List<MemberVO> list(PageObject po) throws Exception{
		// 결과를 저장할 수 있는 변수 선언.
		List<MemberVO> list = null;
		
		try {
			// 1. 드라이버 확인 - DB
			// 2. 연결
			con = DB.getConnection();
			// 3. sql - 아래 LIST
			// 4. 실행 객체 & 데이터 세팅
			pstmt = con.prepareStatement(LIST);
			//id(accepter에 id 정보 저장), rnum1, rnum2
			pstmt.setString(1, po.getAccepter());
			pstmt.setLong(2, po.getStartRow());
			pstmt.setLong(3, po.getEndRow());
			// 5. 실행
			rs = pstmt.executeQuery();
			// 6. 표시 또는 담기
			if(rs != null) {
				while(rs.next()) {
					// rs - > vo -> list
					// list가 null이면 생성해서 저장할 수 있게 해줘야 한다.
					if(list == null) list = new ArrayList<>();
					// rs -> vo
					MemberVO vo = new MemberVO();
					vo.setId(rs.getString("id"));
					vo.setName(rs.getString("name"));
					vo.setBirth(rs.getString("birth"));
					vo.setTel(rs.getString("tel"));
					vo.setGender(rs.getString("gender"));
					vo.setPhoto(rs.getString("photo"));
					vo.setGradeNo(rs.getLong("gradeNo"));
					vo.setGradeName(rs.getString("gradeName"));
					vo.setStatus(rs.getString("status"));
					
					// vo -> list
					list.add(vo);
				} // end of while
			} // end of if
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			// 7. 닫기
			DB.close(con, pstmt, rs);
		} // end of try ~ catch ~ finally
		
		// 결과 데이터를 리턴해 준다.
		return list;
	} // end of list()
	
	// 2. 회원정보보기 처리
	// MemberController - (Execute) - MemberListService - [MemberDAO.view()]
	public MemberVO view(String id) throws Exception{
		// 결과를 저장할 수 있는 변수 선언.
		MemberVO vo = null;
		try {
			// 1. 드라이버 확인 - DB
			// 2. 연결
			con = DB.getConnection();
			// 3. sql - 아래 VIEW
			// 4. 실행 객체 & 데이터 세팅
			pstmt = con.prepareStatement(VIEW);
			pstmt.setString(1, id);
			// 5. 실행
			rs = pstmt.executeQuery();
			// 6. 표시 또는 담기
			if(rs != null && rs.next()) {
				// rs -> vo
				vo = new MemberVO();
				vo.setId(rs.getString("id"));
				vo.setName(rs.getString("name"));
				vo.setGender(rs.getString("gender"));
				vo.setBirth(rs.getString("birth"));
				vo.setTel(rs.getString("tel"));
				vo.setEmail(rs.getString("email"));
				vo.setRegDate(rs.getString("regDate"));
				vo.setConDate(rs.getString("conDate"));
				vo.setPhoto(rs.getString("photo"));
				vo.setGradeNo(rs.getLong("gradeNo"));
				vo.setGradeName(rs.getString("gradeName"));
			} // end of if
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			// 7. 닫기
			DB.close(con, pstmt, rs);
		} // end of try ~ catch ~ finally

		// 결과 데이터를 리턴해 준다.
		return vo;
	} // end of view()
	
	// 3. 회원가입 처리
	// MemberController - (Execute) - MemberViewService - [MemberDAO.write()]
	public int write(MemberVO vo) throws Exception{
		// 결과를 저장할 수 있는 변수 선언.
		int result = 0;
		
		try {
			// 1. 드라이버 확인 - DB
			// 2. 연결
			con = DB.getConnection();
			// 3. sql - 아래 LIST
			// 4. 실행 객체 & 데이터 세팅
			pstmt = con.prepareStatement(WRITE);
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPw());
			pstmt.setString(3, vo.getName());
			pstmt.setString(4, vo.getGender());
			pstmt.setString(5, vo.getBirth());
			pstmt.setString(6, vo.getTel());
			pstmt.setString(7, vo.getEmail());
			pstmt.setString(8, vo.getPhoto());
			// 5. 실행 - update : executeUpdate() -> int 결과가 나옴.
			result = pstmt.executeUpdate();
			// 6. 표시 또는 담기
			System.out.println();
			System.out.println("*** 회원가입이 완료 되었습니다.");
			System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
			// 그외 처리 중 나타나는 오류에 대해서 사용자가 볼수 있는 예외로 만들어 전달한다.
			throw new Exception("예외 발생 : 회원 가입 DB 처리 중 예외가 발생했습니다.");
		} finally {
			// 7. 닫기
			DB.close(con, pstmt);
		}
		
		// 결과 데이터를 리턴해 준다.
		return result;
	} // end of write()
	
	// 3-1. 아이디 중복 처리
	// MemberController - (Execute) - MemberListService - [MemberDAO.view()]
	public String idCheck(String id) throws Exception{
		// 결과를 저장할 수 있는 변수 선언.
		String result=null;
		try {
			// 1. 드라이버 확인 - DB
			// 2. 연결
			con = DB.getConnection();
			// 3. sql - 아래 VIEW
			// 4. 실행 객체 & 데이터 세팅
			pstmt = con.prepareStatement(IDCHECK);
			pstmt.setString(1, id);
			// 5. 실행
			rs = pstmt.executeQuery();
			// 6. 표시 또는 담기
			if(rs != null && rs.next()) {
				// rs -> vo
				result=rs.getString("id");
			} // end of if
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			// 7. 닫기
			DB.close(con, pstmt, rs);
		} // end of try ~ catch ~ finally

		// 결과 데이터를 리턴해 준다.
		return result;
	} // end of view()
	
	// 4. 회원정보 수정 처리
	// NoticeController - (Execute) - NoticeViewService - [NoticeDAO.update()]
	public int update(MemberVO vo) throws Exception{
			// 결과를 저장할 수 있는 변수 선언.
			int result = 0;
			
			try {
				// 1. 드라이버 확인 - DB
				// 2. 연결
				con = DB.getConnection();
				// 3. sql - 아래 UPDATE
				// 4. 실행 객체 & 데이터 세팅
				pstmt = con.prepareStatement(UPDATE);
//				pstmt.setString(1, vo.getTitle());
//				pstmt.setString(2, vo.getContent());
//				pstmt.setString(3, vo.getStartDate());
//				pstmt.setString(4, vo.getEndDate());
//				pstmt.setLong(5, vo.getNo());
				// 5. 실행 - update : executeUpdate() -> int 결과가 나옴.
				result = pstmt.executeUpdate();
				// 6. 표시 또는 담기
				if(result == 0) { // 글번호가 존재하지 않는다. -> 예외로 처리한다.
					throw new Exception("예외 발생 : 글번호가 맞지 않습니다. 정보를 확인해 주세요.");
				}
			} catch (Exception e) {
				e.printStackTrace();
				// 특별한 예외는 그냥 전달한다.
				if(e.getMessage().indexOf("예외 발생") >= 0) throw e;
				// 그외 처리 중 나타나는 오류에 대해서 사용자가 볼수 있는 예외로 만들어 전달한다.
				else throw new Exception("예외 발생 : 회원 등급 변경 처리 중 예외가 발생했습니다.");
			} finally {
				// 7. 닫기
				DB.close(con, pstmt);
			}
			
			// 결과 데이터를 리턴해 준다.
			return result;
		} // end of changeGrade()
		
	
	// 4-1. 회원 등급 수정 처리
	// NoticeController - (Execute) - NoticeViewService - [NoticeDAO.update()]
	public int changeGrade(MemberVO vo) throws Exception{
		// 결과를 저장할 수 있는 변수 선언.
		int result = 0;
		
		try {
			// 1. 드라이버 확인 - DB
			// 2. 연결
			con = DB.getConnection();
			// 3. sql - 아래 UPDATE
			// 4. 실행 객체 & 데이터 세팅
			pstmt = con.prepareStatement(CHANGEGRADE);
			pstmt.setLong(1, vo.getGradeNo());
			pstmt.setString(2, vo.getId());
			// 5. 실행 - update : executeUpdate() -> int 결과가 나옴.
			result = pstmt.executeUpdate();
			// 6. 표시 또는 담기
			if(result == 0) { // 글번호가 존재하지 않는다. -> 예외로 처리한다.
				throw new Exception("예외 발생 : ID가 맞지 않거나 등급번호가 올바르지 않습니다. 정보를 확인해 주세요.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 특별한 예외는 그냥 전달한다.
			if(e.getMessage().indexOf("예외 발생") >= 0) throw e;
			// 그외 처리 중 나타나는 오류에 대해서 사용자가 볼수 있는 예외로 만들어 전달한다.
			else throw new Exception("예외 발생 : 공지 수정 DB 처리 중 예외가 발생했습니다.");
		} finally {
			// 7. 닫기
			DB.close(con, pstmt);
		}
		
		// 결과 데이터를 리턴해 준다.
		return result;
	} // end of update()
	
	// 4-2. 회원 상태 수정 처리
	// NoticeController - (Execute) - NoticeViewService - [NoticeDAO.update()]
	public int changeStatus(MemberVO vo) throws Exception{
		// 결과를 저장할 수 있는 변수 선언.
		int result = 0;
		
		try {
			// 1. 드라이버 확인 - DB
			// 2. 연결
			con = DB.getConnection();
			// 3. sql - 아래 UPDATE
			// 4. 실행 객체 & 데이터 세팅
			pstmt = con.prepareStatement(CHANGESTATUS);
			pstmt.setString(1, vo.getStatus());
			pstmt.setString(2, vo.getId());
			// 5. 실행 - update : executeUpdate() -> int 결과가 나옴.
			result = pstmt.executeUpdate();
			// 6. 표시 또는 담기
			if(result == 0) { // 글번호가 존재하지 않는다. -> 예외로 처리한다.
				throw new Exception("예외 발생 : ID가 맞지 않거나 상태가 올바르지 않습니다. 정보를 확인해 주세요.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 특별한 예외는 그냥 전달한다.
			if(e.getMessage().indexOf("예외 발생") >= 0) throw e;
			// 그외 처리 중 나타나는 오류에 대해서 사용자가 볼수 있는 예외로 만들어 전달한다.
			else throw new Exception("예외 발생 : 공지 수정 DB 처리 중 예외가 발생했습니다.");
		} finally {
			// 7. 닫기
			DB.close(con, pstmt);
		}
		
		// 결과 데이터를 리턴해 준다.
		return result;
	} // end of update()
	
	
	// 5. 회원탈퇴 처리
	// NoticeController - (Execute) - NoticeDeleteService - [NoticeDAO.delete()]
	public int delete(Long no) throws Exception{
		// 결과를 저장할 수 있는 변수 선언.
		int result = 0;
		
		try {
			// 1. 드라이버 확인 - DB
			// 2. 연결
			con = DB.getConnection();
			// 3. sql - 아래 UPDATE
			// 4. 실행 객체 & 데이터 세팅
			pstmt = con.prepareStatement(DELETE);
			pstmt.setLong(1, no);
			// 5. 실행 - update : executeUpdate() -> int 결과가 나옴.
			result = pstmt.executeUpdate();
			// 6. 표시 또는 담기
			if(result == 0) { // 글번호가 존재하지 않거나 비번 틀림. -> 예외로 처리한다.
				throw new Exception("예외 발생 : 글번호가 맞지 않습니다. 정보를 확인해 주세요.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 특별한 예외는 그냥 전달한다.
			if(e.getMessage().indexOf("예외 발생") >= 0) throw e;
			// 그외 처리 중 나타나는 오류에 대해서 사용자가 볼수 있는 예외로 만들어 전달한다.
			else throw new Exception("예외 발생 : 공지 삭제 DB 처리 중 예외가 발생했습니다.");
		} finally {
			// 7. 닫기
			DB.close(con, pstmt);
		}
		
		// 결과 데이터를 리턴해 준다.
		return result;
	} // end of delete()
	
	
	// 6. 로그인 처리
	// MemberController - (Execute) - MemberLoginService - [MemberDAO.login()]
	public LoginVO login(LoginVO loginvo) throws Exception{
		// 결과를 저장할 수 있는 변수 선언.
		LoginVO vo = null;
		try {
			// 1. 드라이버 확인 - DB
			// 2. 연결
			con = DB.getConnection();
			// 3. sql - 아래 LIST
			// 4. 실행 객체 & 데이터 세팅
			pstmt = con.prepareStatement(LOGIN);
			pstmt.setString(1, loginvo.getId());
			pstmt.setString(2, loginvo.getPw());
			// 5. 실행
			rs = pstmt.executeQuery();
			// 6. 표시 또는 담기
			if(rs != null && rs.next()) {
				// rs -> vo
				vo = new LoginVO();
				vo.setId(rs.getString("id"));
				vo.setName(rs.getString("name"));
				vo.setGradeNo(rs.getInt("gradeNo"));
				vo.setGradeName(rs.getString("gradeName"));
				vo.setPhoto(rs.getString("photo"));
				vo.setNewMsgCnt(rs.getLong("newMsgCnt"));
			} // end of if
			if(vo == null) { // 아이디와 비밀번호가 맞지 않는다. -> 예외로 처리한다.
				throw new Exception("예외 발생 : 아이디나 비밀번호가 맞지 않습니다. 정보를 확인해 주세요.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 특별한 예외는 그냥 전달한다.
			if(e.getMessage().indexOf("예외 발생") >= 0) throw e;
			// 그외 처리 중 나타나는 오류에 대해서 사용자가 볼수 있는 예외로 만들어 전달한다.
			else throw new Exception("예외 발생 : 로그인 DB 처리 중 예외가 발생했습니다.");
		} finally {
			// 7. 닫기
			DB.close(con, pstmt, rs);
		} // end of try ~ catch ~ finally

		// 결과 데이터를 리턴해 준다.
		return vo;
	} // end of login()
	
	// 실행할 쿼리를 정의해 놓은 변수 선언.
	final String LIST = " select id, name, birth, gender, tel, "
			+ " gradeNo, gradeName, status, photo "
			+ " from ("
				+ " select rownum rnum, id, name, birth, gender, tel, "
				+ " gradeNo, gradeName, status, photo "
				+ " from ("
					+ " select m.id, m.name, "
					+ " to_char(m.birth, 'yyyy-mm-dd') birth, "
					+ " m.gender, m.tel, "
					+ " m.gradeNo, g.gradeName, m.status, m.photo "
					+ " from member m, grade g "
					+ " where 1=1 and (id != ?) and (m.gradeNo = g.gradeNo) "
					+ " order by id asc"
				+ " ) "
			+ " ) "
			+ " where rnum between ? and ? ";
	final String VIEW= "select m.id, m.name, gender, "
			+ " to_char(m.birth, 'yyyy-mm-dd') birth, m.tel, "
			+ " email,  to_char(m.regDate, 'yyyy-mm-dd') regDate, "
			+ " to_char(m.conDate, 'yyyy-mm-dd') conDate, "
			+ " m.photo, m.gradeNo, g.gradeName "
			+ " from member m, grade g "
			+ " where (id = ?) and (m.gradeNo = g.gradeNo) ";
	final String IDCHECK="select id from member where id=?";
	final String WRITE = "insert into member "
			+ " (id, pw, name, gender, birth, tel, email, photo) "
			+ " values(?, ?, ?, ?, ?, ?, ?, ?)"; 
	final String UPDATE= "update notice "
			+ " set title = ?, content = ?, startDate = ?, endDate = ? "
			+ " where no = ?"; 
	final String CHANGEGRADE= "update member set gradeno = ? where id = ?";
	final String CHANGESTATUS= "update member set status = ? where id = ?";
	final String DELETE= "delete from notice "
			+ " where no = ?"; 
	final String LOGIN = "select m.id, m.name, m.gradeNo, "
			+ " g.gradeName, m.photo, m.newMsgCnt "
			+ " from member m, grade g "
			+ " where (id = ? and pw = ? and status = '정상') "
			+ " and (g.gradeNo = m.gradeNo) ";
}
