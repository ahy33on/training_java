package com.webjjang.image.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.webjjang.image.vo.ImageVO;
import com.webjjang.main.dao.DAO;
import com.webjjang.util.db.DB;
import com.webjjang.util.page.PageObject;

public class ImageDAO extends DAO{

	// 필요한 객체 선언 - 상속
	// 접속 정보 - DB 사용 - connection을 가져오게 하는 메서드만 이용
	
	// 1. 리스트 처리
	// ImageController - (Execute) - ImageListService - [ImageDAO.list()]
	public List<ImageVO> list(PageObject pageObject) throws Exception{
		// 결과를 저장할 수 있는 변수 선언.
		List<ImageVO> list = null;
		
		try {
			// 1. 드라이버 확인 - DB / 연결 - getConnection()
			con = DB.getConnection();
			// 2. 실행 객체 초기화 및 데이터 세팅
//			pstmt = con.prepareStatement(getListSQL(pageObject));
			System.out.println("list를 위한 dao 진입 및 드라이버 연결 완료");
			pstmt = con.prepareStatement(getListSQL(pageObject));
			
			System.out.println("ImageDAO.list().sql = " + getListSQL(pageObject));
			
			//검색어 관련 데이터 세팅
			int idx=0; //pstmt의 순서 번호로 사용한다.
			idx=setSearchData(pageObject, pstmt, idx);
			//기본값/ start-1, end-10
			pstmt.setLong(++idx, pageObject.getStartRow());
			pstmt.setLong(++idx, pageObject.getEndRow());
			
			// 3. 실행
			rs = pstmt.executeQuery();
			// 4. 담기
			if(rs != null) {
				int cnt=0;
				while(rs.next()) {
					// rs - > vo -> list
					// list가 null이면 생성해서 저장할 수 있게 해줘야 한다.
					if(list == null) list = new ArrayList<ImageVO>();
					// rs -> vo
					//no, title, id, name, content, writeDate, fileName
					ImageVO vo = new ImageVO();
					vo.setNo(rs.getLong("no"));
					vo.setTitle(rs.getString("title"));
					vo.setContent(rs.getString("content"));
					vo.setId(rs.getString("id"));
					vo.setName(rs.getString("name"));
					vo.setWriteDate(rs.getString("writeDate"));
					vo.setFileName(rs.getString("fileName"));
					
					// vo -> list
					list.add(vo);
					System.out.println((++cnt)+"번째 항목이 추가되었습니다.");
				} // end of while
			} // end of if
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			// 5. 닫기
			DB.close(con, pstmt, rs);
		} // end of try ~ catch ~ finally
		
		// 결과 데이터를 리턴해 준다.
		return list;
	} // end of list()
	
	// 1-2. 전체 데이터 개수 가져오기
			// ImageController - (Execute) - ImageListService - [ImageDAO.view()]
			public Long getTotalrow(PageObject pageObject) throws Exception{
				// 결과를 저장할 수 있는 변수 선언.
				Long totalrow=null;
				try {
					// 1. 드라이버 확인 - DB
					// 2. 연결
					con = DB.getConnection();
					// 3. sql - 아래 LIST
					// 4. 실행 객체 & 데이터 세팅
					pstmt = con.prepareStatement(TOTALROW+getSearch(pageObject, true));
					//getTotalRow이면서 검색 사항이 존재하는 경우, 
					//getSearch()에서 where을 붙어야 한다.
					System.out.println("=====getTotalRow()=====");
					System.out.println(TOTALROW+getSearch(pageObject, true));
					int idx=0;
					setSearchData(pageObject, pstmt, idx);
					// 5. 실행
					rs = pstmt.executeQuery();
					// 6. 표시 또는 담기
					if(rs != null && rs.next())
						totalrow=rs.getLong(1);
				} catch (Exception e) {
					e.printStackTrace();
					throw e;
				} finally {
					// 7. 닫기
					DB.close(con, pstmt, rs);
				} // end of try ~ catch ~ finally
				// 결과 데이터를 리턴해 준다.
				return totalrow;
			} // end of view()
	
	// 2. 글보기 처리
	// ImageController - (Execute) - ImageListService - [ImageDAO.view()]
	public ImageVO view(Long no) throws Exception{
		// 결과를 저장할 수 있는 변수 선언.
		ImageVO vo = null;
		try {
			// 1. 드라이버 확인 - DB
			// 2. 연결
			con = DB.getConnection();
			// 3. sql - 아래 LIST
			// 4. 실행 객체 & 데이터 세팅
			pstmt = con.prepareStatement(VIEW);
			pstmt.setLong(1, no);
			System.out.println("=====dao view: 데이터 세팅 완료=====");
			// 5. 실행
			rs = pstmt.executeQuery();
			// 6. 표시 또는 담기
			if(rs != null && rs.next()) {
				// rs -> vo
				vo = new ImageVO();
				vo.setNo(rs.getLong("no"));
				vo.setTitle(rs.getString("title"));
				vo.setContent(rs.getString("content"));
				vo.setId(rs.getString("id"));
				vo.setWriteDate(rs.getString("writeDate"));
				vo.setFileName(rs.getString("fileName"));
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
	
	// 3. 글등록 처리
	// ImageController - (Execute) - ImageViewService - [ImageDAO.increase()]
	public int write(ImageVO vo) throws Exception{
		// 결과를 저장할 수 있는 변수 선언.
		int result = 0;
		
		try {
			// 1. 드라이버 확인 - DB
			// 2. 연결
			con = DB.getConnection();
			// 3. sql - 아래 LIST
			// 4. 실행 객체 & 데이터 세팅
			pstmt = con.prepareStatement(WRITE);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setString(3, vo.getId());
			pstmt.setString(4, vo.getFileName());
			// 5. 실행 - update : executeUpdate() -> int 결과가 나옴.
			result = pstmt.executeUpdate();
			// 6. 표시 또는 담기
		} catch (Exception e) {
			e.printStackTrace();
			// 그외 처리 중 나타나는 오류에 대해서 사용자가 볼수 있는 예외로 만들어 전달한다.
			throw new Exception("예외 발생 : 게시판 글등록 DB 처리 중 예외가 발생했습니다.");
		} finally {
			// 7. 닫기
			DB.close(con, pstmt);
		}
		
		// 결과 데이터를 리턴해 준다.
		return result;
	} // end of increase()
	
	
	// 4. 글수정 처리
	// ImageController - (Execute) - ImageViewService - [ImageDAO.update()]
	public int update(ImageVO vo) throws Exception{
		// 결과를 저장할 수 있는 변수 선언.
		int result = 0;
		
		try {
			// 1. 드라이버 확인 - DB
			// 2. 연결
			con = DB.getConnection();
			// 3. sql - 아래 UPDATE
			// 4. 실행 객체 & 데이터 세팅
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setLong(3, vo.getNo());
			pstmt.setString(4, vo.getId());
			
			// 5. 실행 - update : executeUpdate() -> int 결과가 나옴.
			result = pstmt.executeUpdate();
			// 6. 표시 또는 담기
			if(result == 0) { // 글번호가 존재하지 않는다. -> 예외로 처리한다.
				throw new Exception("예외 발생 : 글번호가 맞지 않거나 본인 글이 아닙니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 특별한 예외는 그냥 전달한다.
			if(e.getMessage().indexOf("예외 발생") >= 0) throw e;
			// 그외 처리 중 나타나는 오류에 대해서 사용자가 볼수 있는 예외로 만들어 전달한다.
			else throw new Exception("예외 발생 : 게시판 글등록 DB 처리 중 예외가 발생했습니다.");
		} finally {
			// 7. 닫기
			DB.close(con, pstmt);
		}
		
		// 결과 데이터를 리턴해 준다.
		return result;
	} // end of update()
	
	
	// 5. 글삭제 처리
	// ImageController - (Execute) - ImageDeleteService - [ImageDAO.delete()]
	public int delete(ImageVO vo) throws Exception{
		// 결과를 저장할 수 있는 변수 선언.
		int result = 0;
		
		try {
			// 1. 드라이버 확인 - DB
			// 2. 연결
			con = DB.getConnection();
			// 3. sql - 아래 UPDATE
			// 4. 실행 객체 & 데이터 세팅
			pstmt = con.prepareStatement(DELETE);
			pstmt.setLong(1, vo.getNo());
			pstmt.setString(2, vo.getId());
			// 5. 실행 - update : executeUpdate() -> int 결과가 나옴.
			result = pstmt.executeUpdate();
			System.out.println("result: "+result);
			// 6. 표시 또는 담기
			if(result == 0) { // 글번호가 존재하지 않거나 비번 틀림. -> 예외로 처리한다.
				throw new Exception("예외 발생 : 본인 글이 아닙니다. 정보를 확인해 주세요.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 특별한 예외는 그냥 전달한다.
			if(e.getMessage().indexOf("예외 발생") >= 0) throw e;
			// 그외 처리 중 나타나는 오류에 대해서 사용자가 볼수 있는 예외로 만들어 전달한다.
			else throw new Exception("예외 발생 : 게시판 글삭제 DB 처리 중 예외가 발생했습니다.");
		} finally {
			// 7. 닫기
			DB.close(con, pstmt);
		}
		
		// 결과 데이터를 리턴해 준다.
		return result;
	} // end of delete()
	
	// 6. 이미지 변경 처리
		// ImageController - (Execute) - ImageViewService - [ImageDAO.update()]
		public int changeImg(ImageVO vo) throws Exception{
			// 결과를 저장할 수 있는 변수 선언.
			int result = 0;
			
			try {
				// 1. 드라이버 확인 - DB
				// 2. 연결
				con = DB.getConnection();
				// 3. sql - 아래 UPDATE
				// 4. 실행 객체 & 데이터 세팅
				pstmt = con.prepareStatement(CHANGEIMG);
				pstmt.setString(1, vo.getFileName());
				pstmt.setLong(2, vo.getNo());
				// 5. 실행 - update : executeUpdate() -> int 결과가 나옴.
				result = pstmt.executeUpdate();
				// 6. 표시 또는 담기
				if(result == 0) { // 글번호가 존재하지 않는다. -> 예외로 처리한다.
					throw new Exception("예외 발생 : 글번호나 비밀번호가 맞지 않습니다. 정보를 확인해 주세요.");
				}
			} catch (Exception e) {
				e.printStackTrace();
				// 특별한 예외는 그냥 전달한다.
				if(e.getMessage().indexOf("예외 발생") >= 0) throw e;
				// 그외 처리 중 나타나는 오류에 대해서 사용자가 볼수 있는 예외로 만들어 전달한다.
				else throw new Exception("예외 발생 : 게시판 글등록 DB 처리 중 예외가 발생했습니다.");
			} finally {
				// 7. 닫기
				DB.close(con, pstmt);
			}
			
			// 결과 데이터를 리턴해 준다.
			return result;
		} // end of changeimg()
		
		
	
	// 실행할 쿼리를 정의해 놓은 변수 선언.
	final String LIST = " select no, title, id, name, content, writeDate, fileName "
		+ " from ( "
			+ " select rownum rnum, no, title, id, name, content, "
			+ " 	writeDate, fileName "
			+ " from ( "
				+ " select i.no, i.title, i.id, m.name, i.content, "
				+ " to_char(i.writeDate, 'yyyy-mm-dd') writeDate, "
				+ " i.fileName "
				+ " from image i , member m "
					//where 1=0 and: or
					//where 1=1 and: and
					+ " where 1=1";
	
	
	//검색이 있는 경우, TOTALROW+getSearch
	final String TOTALROW="select count(*) from image";
	
	//list.jsp에서 검색시 처리해야 할 기능
	private String getListSQL(PageObject po) {
		String sql=LIST;
		String word=po.getWord();
		sql+=getSearch(po, false);
		sql+=" and i.id = m.id order by no desc"
			+" ) "
			+" ) where rnum between ? and ?";
		return sql;
	}
	//list(), getTotalRow()에서 사용한다. list는 where가 항상 존재한다.
	//단, getTotalRow()는 항상 위치 하지 않기 때문에 검색이 있는 경우, where을 추가해야 한다.
	private String getSearch(PageObject po, boolean isWhere) {
		//list 검색을 처리하는 실질적인 메서드
		String sql="";
		String key=po.getKey();
		String word=po.getWord();
		
		
		if(word!=null && !word.equals("")) {
			if(isWhere) sql+=" where 1=1 ";
			sql+=" and ( 1=0 ";
			if(key.indexOf("t")>-1) sql+="or title like ? ";
			if(key.indexOf("c")>-1) sql+="or content like ? ";
			if(key.indexOf("f")>-1) sql+="or fileName like ? ";
			if(key.indexOf("w")>-1) {
				if(!isWhere) sql+="or i.id like ? ";
				else sql+="or id like ? ";
			}
			sql+=" ) ";
		}
		return sql;
	}
	//pstmt로 ? 데이터 세팅하는 메서드
	private int setSearchData(PageObject po, PreparedStatement pstmt, int idx) throws SQLException {
		String key=po.getKey();
		String word=po.getWord();
		if(word!=null && !word.equals("")) {
			if(key.indexOf("t")>-1) pstmt.setString(++idx, "%"+word+"%");
			if(key.indexOf("c")>-1) pstmt.setString(++idx, "%"+word+"%");
			if(key.indexOf("w")>-1) pstmt.setString(++idx, "%"+word+"%");
			if(key.indexOf("f")>-1) pstmt.setString(++idx, "%"+word+"%");
		}
		return idx;
	}
	final String CHANGEIMG="update image set fileName=? where no=?";
	final String INCREASE = "update image set hit = hit + 1 "
			+ " where no = ?"; 
	final String VIEW= "select i.no, i.title, i.content, i.id, m.name, "
			+ " to_char(i.writeDate, 'yyyy-mm-dd') writeDate, i.fileName "
			+ " from image i, member m "
			+ " where (i.no = ?) and (m.id = i.id)";
	final String WRITE = "insert into image "
			+ " (no, title, content, id, fileName) "
			+ " values(image_seq.nextval, ?, ?, ?, ?)"; 
	final String UPDATE= "update image "
			+ " set title = ?, content = ?"
			+ " where no = ? and id=?"; 
	final String DELETE= "delete from image "
			+ " where no = ? and id = ?";
	
}
