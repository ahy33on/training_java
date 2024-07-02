package com.webjjang.image.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.webjjang.image.vo.ImageVO;
import com.webjjang.main.controller.DispatcherServlet;
import com.webjjang.main.controller.Init;
import com.webjjang.member.vo.LoginVO;
import com.webjjang.util.exe.Execute;
import com.webjjang.util.page.PageObject;
import com.webjjang.util.page.ReplyPageObject;

// Image Module 에 맞는 메뉴 선택 , 데이터 수집(기능별), 예외 처리
public class ImageController {
	public String execute(HttpServletRequest request) {
			// 메뉴 입력
			String uri = request.getRequestURI();
			String jsp=null;
			Object result = null;
			Long no = 0L;
			long inc = 0L;
			
			HttpSession session=request.getSession();
			LoginVO loginVO=(LoginVO) session.getAttribute("login");
			
			String id=null;
			if(loginVO!=null) id=loginVO.getId();
			
			String imgpath="/upload/image";
			String realimgpath=request.getServletContext().getRealPath(imgpath);
			
			try { // 정상 처리
			
				// 메뉴 처리 : CRUD DB 처리 - Controller - Service - DAO
				switch (uri) {
				case "/image/list.do":
					// [ImageController] - (Execute) - ImageListService - ImageDAO.list()
					System.out.println("======= IMAGE LIST ======");
					// DB에서 데이터 가져오기 - 가져온 데이터는 List<ImageVO>
					PageObject po=PageObject.getInstance(request);
					String strPerPagenum=request.getParameter("perPageNum");
					if(strPerPagenum==null) po.setPerPageNum(6);
					
					result = Execute.execute(Init.get(uri), po);
					// 결과 데이터 출력하기
					request.setAttribute("list", result);
					//pageObject 담기
					request.setAttribute("po", po);
					jsp="image/list";
					break;
				case "/image/view.do":
					System.out.println("======= IMAGE VIEW ======");
					// 1. 조회수 1증가(글보기), 2. 일반게시판 글보기 데이터 가져오기 : 글보기, 글수정
					no=Long.parseLong(request.getParameter("no"));
					inc=Long.parseLong(request.getParameter("inc"));
					System.out.println("no: "+no+", inc: "+inc);
					// 전달 데이터 - 글번호, 조회수 증가 여부(1:증가, 0:증가 안함) : 배열 또는 Map
					result = Execute.execute(Init.get(uri), new Long[]{no, inc});
					//pagination: page, perpagenum, no, replyPage, replyPerPageNum 전달 필요
//					ReplyPageObject replyPageObject=ReplyPageObject.getInstance(request);
//					Object replyresult = Execute.execute(Init.get("/imagereply/list.do"), replyPageObject);
					// 게시판 글보기 출력 : ImagePrint
					request.setAttribute("vo", result);
//					request.setAttribute("replyList", replyresult);
					//댓글 페이지 객체 담기
//					request.setAttribute("replyPageObject", replyPageObject);
					
					jsp="image/view";
					break;
				case "/image/writeform.do":
					System.out.println("======= IMAGE WRITE FORM ======");
					jsp="image/writeform";
					break;
				case "/image/write.do":
					System.out.println("======= IMAGE WRITE ======");
			
					//img: MultipartRequest(request, 저장위치, maxsize, enctype, 중복처리)
					MultipartRequest multi=new MultipartRequest(request, realimgpath, 100*1024*1024, "UTF-8", new DefaultFileRenamePolicy());
					//데이터 수집: name으로 전달된 데이터 수집
					String title=multi.getParameter("title");
					String content=multi.getParameter("content");
					//id는 session에서 받음(상단 참고)
					String fileName=multi.getFilesystemName("imgfile");
					
					// 변수 - vo 저장하고 Service
					ImageVO vo = new ImageVO();
					vo.setTitle(title);
					vo.setContent(content);
					vo.setId(id);
					//file의 위치정보를 추가하여 서버에서 위치를 찾을 수 있도록 해야 한다.
					vo.setFileName(imgpath+"/"+fileName);
					
					// [ImageController] - ImageWriteService - ImageDAO.write(vo)
					Execute.execute(Init.get(uri), vo);
					
					session.setAttribute("msg", "이미지 등록이 완료되었습니다.");
					
					//redirect: -> 자동 페이지 전환(forward로 대신할 수 있다.)
					jsp="redirect:list.do?perPageNum="
							+multi.getParameter("perPageNum");
					
					break;
				case "/image/updateform.do":
					System.out.println("4-1.일반게시판 글 수정 폼");
					no = Long.parseLong(request.getParameter("no"));
					result = Execute.execute(Init.get("/image/view.do"), new Long[]{no, 0L});
					// 게시판 글보기 출력 : ImagePrint
					request.setAttribute("vo", result);
					jsp="image/updateform";
					break;
				case "/image/update.do":
					System.out.println("4-2.일반게시판 글 수정 처리");
					
					no = Long.parseLong(request.getParameter("no"));
					title=request.getParameter("title");
					content=request.getParameter("content");
					
					// 변수 - vo 저장하고 Service
					vo = new ImageVO();
					vo.setNo(no);
					vo.setTitle(title);
					vo.setContent(content);
					
					
					//페이지 정보 받고 uri에 셋팅
					po=PageObject.getInstance(request);
					jsp="redirect:view.do?no="+no+"&inc=0&"
							+po.getPageQuery();
					
					
					// [ImageController] - ImageWriteService - ImageDAO.write(vo)
					Execute.execute(Init.get(uri), vo);
					
					break;
				case "/image/delete.do":
					System.out.println("5.일반게시판 글삭제");
					// 데이터 수집 - DB에서 실행에 필요한 데이터 - 글번호, 비밀번호 - ImageVO
					vo = new ImageVO();
					no = Long.parseLong(request.getParameter("no"));
					vo.setNo(no);
					
					
					// DB 처리
					Execute.execute(Init.get(uri), vo);
					System.out.println();
					System.out.println("***************************");
					System.out.println("**  " + vo.getNo() + "글이 삭제되었습니다.  **");
					System.out.println("***************************");
					
					po=PageObject.getInstance(request);
					jsp="redirect:list.do?perPageNum="
							+request.getParameter("perPageNum");
					
					break;
	
				default:
					System.out.println("####################################");;
					System.out.println("## 잘못된 메뉴를 선택하셨습니다.          ##");;
					System.out.println("## [0~5, 0] 중에서 입력하셔야 합니다.    ##");;
					System.out.println("####################################");;
					break;
				} // end of switch
			} catch (Exception e) {
				// TODO: handle exception
				// e.printStackTrace();
				System.out.println();
				System.out.println("$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@");
				System.out.println("$%@ << 오류 출력 >>                         $%@");
				System.out.println("$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@");
				System.out.println("$%@ 타입 : " + e.getClass().getSimpleName());
				System.out.println("$%@ 내용 : " + e.getMessage());
				System.out.println("$%@ 조치 : 데이터를 확인 후 다시 실행해 보세요.");
				System.out.println("$%@     : 계속 오류가 나면 전산담당자에게 연락하세요.");
				System.out.println("$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@");
			} // end of try~catch
		System.out.println("jsp: "+jsp);
		return jsp;
	} // end of main()
	
} // end of class
