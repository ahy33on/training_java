<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>UPDATE FORM</title>
</head>


<body>
<div class="container">
<form action="update.do" method="post">
<h1>[ UPDATE FORM ]</h1>
<input type="hidden" name="no" value="${vo.no}">
<input type="hidden" name="id" value="${vo.id}">
<input type="hidden" name="key" value="${param.key}">
<input type="hidden" name="word" value="${param.word}">
<input type="hidden" name="page" value="${param.page}">
<input type="hidden" name="perPageNum" value="${param.perPageNum}">

<br>
    <div class="row">
      <div class="col-sm-6">
        <img src="${vo.fileName}" style="width:100%">
        <div class="alert alert-danger">
		  이미지 수정을 원하면 이미지 변경을 이용하시오.
		</div>
      </div>
      <div class="col-sm-6">
        <input class="form-control" name="title" value="${vo.title}" required>
	    <hr>
	    <textarea class="form-control" rows="6" name="content" required>${vo.content}</textarea>
      </div>
    </div>

<br>

	<button class="btn btn-primary">적용</button>
	<button class="btn btn-secondary" type="button" onclick="history.back();">취소</button>
	<button class="btn btn-danger" type="reset">초기화</button>

</form>
</div>
</body>

</html>