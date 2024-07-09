<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
if(session.getAttribute("uri")==null) //dispatcher servlet을 거치지 않은 경우
	session.setAttribute("uri", request.getRequestURI());
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>404_noModule</title>

  <!-- Bootstrap 4 + jquery 라이브러리 등록 - CDN 방식 -->
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
  <script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.slim.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>

<!-- icon 라이브러리 등록 - Font Awesome 4 / google -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

<style>
.row{
border-bottom:1px #ddd dotted;
padding:7px;
}
</style>

</head>


<body>
	<div class="container">
	  <div class="card">
	    <div class="card-header">
	    	<h4>잘못된 자원 요청 <i class="material-icons">error_outline</i></h4>
	    	</div>
	    <div class="card-body">
	    	<div class="row">
		    	<div class="col-md-3" style="text-align:center"><i class="fa fa-check"></i>요청 URI</div>
		    	<div class="col-md-9">${uri}</div>
	    	</div>
	    	<div class="row">
		    	<div class="col-md-3" style="text-align:center"><i class="fa fa-check"></i>오류 메시지</div>
		    	<div class="col-md-9">
		    		불편을 드려 죄송합니다.<br>
		    		요청하신 페이지의 접근 권한이 없습니다.
		    	</div>
	    	</div>
	    	<div class="row">
		    	<div class="col-md-3" style="text-align:center"><i class="fa fa-check"></i>해결</div>
		    	<div class="col-md-9">
		    		로그인 정보의 등급을 확인해주세요.<br>
		    		오류가 있는 경우, 관리자에게 연락해주십시오.
		    	</div>
	    	</div>
	    </div> 
	    <div class="card-footer">
	    	<a href="/board/list.do" class="btn btn-primary">돌아가기</a>
	    </div>
	  </div>
	</div>
</body>
</html>