<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Error; 500</title>
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