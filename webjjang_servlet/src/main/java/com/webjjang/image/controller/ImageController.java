package com.webjjang.image.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.webjjang.image.vo.ImageVO;
import com.webjjang.main.controller.Init;
import com.webjjang.member.vo.LoginVO;
import com.webjjang.util.exe.Execute;
import com.webjjang.util.page.PageObject;

// Image Module 에 맞는 메뉴 선택 , 데이터 수집(기능별), 예외 처리
public class ImageController {
	public String execute(HttpServletRequest request) {
			// 메뉴 입력
			String uri = request.getRequestURI();
			String jsp=null;
			Object result = null;
			Long no = 0L;
			
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
					System.out.println("no: "+no);
					// 전달 데이터 - 글번호, 조회수 증가 여부(1:증가, 0:증가 안함) : 배열 또는 Map
					result = Execute.execute(Init.get(uri), no);
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
					/*multipartrequest: writeform의 input(file) name에 대하여 하나만 처리할 수 있다.
					 다시 말해, 여러 파일을 올리고 싶으면 input(file)의 name을 서로 다르게 해야 한다.
					 이때, getFilesystemName() 처리시에도 변수명을 서로 다르게 처리해야 한다.
					*/
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
					System.out.println("========IMAGE UPDATE FORM=======");
					no = Long.parseLong(request.getParameter("no"));
					result = Execute.execute(Init.get("/image/view.do"), no);
					// 게시판 글보기 출력 : ImagePrint
					request.setAttribute("vo", result);
					jsp="image/updateform";
					break;
				case "/image/update.do":
					System.out.println("========IMAGE UPDATE=======");
					
					no = Long.parseLong(request.getParameter("no"));
					title=request.getParameter("title");
					content=request.getParameter("content");
					id=request.getParameter("id");
					
					// 변수 - vo 저장하고 Service
					vo = new ImageVO();
					vo.setNo(no);
					vo.setId(id);
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
					System.out.println("========IMAGE DELETE=======");
					// 데이터 수집 - DB에서 실행에 필요한 데이터 - 글번호, 비밀번호 - ImageVO
					
					vo = new ImageVO();
					no = Long.parseLong(request.getParameter("no"));
					vo.setNo(no);
					vo.setId(id);
					
					
					// DB 처리
					Execute.execute(Init.get(uri), vo);
					System.out.println();
					System.out.println("***************************");
					System.out.println("**"+ vo.getNo() + "번 글이 삭제되었습니다.**");
					System.out.println("***************************");
					
					po=PageObject.getInstance(request);
					jsp="redirect:list.do?perPageNum="
							+request.getParameter("perPageNum");
					
					//삭제할 파일명 불러오기
					String dltfileName=request.getParameter("dltfile");
					
					File dltfile=new File(request.getServletContext().getRealPath(dltfileName));
					if(dltfile.exists()) dltfile.delete();
					
					session.setAttribute("msg", "이미지 삭제가 완료되었습니다.");
					
					break;
				case "/image/changeimage.do":
					System.out.println("=======IMAGE CHANGE======");
					
					po=PageObject.getInstance(request);
					
					multi=new MultipartRequest(request, realimgpath, (100*1024*1024), "UTF-8", new DefaultFileRenamePolicy());
					fileName=multi.getFilesystemName("changefile");
					no = Long.parseLong(multi.getParameter("no"));
					dltfileName=multi.getParameter("dltfile");
					
					// 변수 - vo 저장하고 Service
					vo = new ImageVO();
					vo.setNo(no);
					vo.setFileName(imgpath+"/"+fileName);
					
					//view_fileName=/upload/image/carlfriedrik.jpg
					//change_fileName=palissybriefcase4.jpg
					
					//페이지 정보 받고 uri에 셋팅
					jsp="redirect:view.do?no="+no+"&"+po.getPageQuery();
					
					// [ImageController] - ImageWriteService - ImageDAO.write(vo)
					Execute.execute(Init.get(uri), vo);
					
					//변경 전 이미지 파일 지우기
					
					dltfile=new File(request.getServletContext().getRealPath(dltfileName));
					if(dltfile.exists()) dltfile.delete();
					session.setAttribute("msg", "이미지 변경이 완료되었습니다.");
					
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
