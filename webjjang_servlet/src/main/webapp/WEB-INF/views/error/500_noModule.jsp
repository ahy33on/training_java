<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>
<!-- isErrorPage="true": 해당 jsp가 에러 처리 페이지임. Exception이 전달됨. -->
<%
//전달받은 예외 객체를 request e 속성에 담기
request.setAttribute("e", exception);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Error; 500</title>

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
<br>
<div class="container">
  <div class="card">
    <div class="card-header">
    	<h4>${e.getClass().simpleName} <i class="material-icons">error_outline</i></h4>
    	</div>
    <div class="card-body">
    	<div class="row">
	    	<div class="col-md-3" style="text-align:center"><i class="fa fa-check"></i>원인</div>
	    	<div class="col-md-9">${e.message}</div>
    	</div>
    	<div class="row">
	    	<div class="col-md-3" style="text-align:center"><i class="fa fa-check"></i>해결</div>
	    	<div class="col-md-9">
	    		불편을 드려 죄송합니다.<br>
	    		반복 발생시 전산 담당자에게 연락하십시오.
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