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
<input type="hidden" name="key" value="${param.key}">
<input type="hidden" name="word" value="${param.word}">
<input type="hidden" name="page" value="${param.page}">
<input type="hidden" name="perPageNum" value="${param.perPageNum}">
<table class="table">
	<tr>
		<th>${vo.no}번</th>
		<th>${vo.writeDate}</th>
	</tr>
	<tr>
		<tr>
		<th>제목</th>
		<td><input class="form-control" name="title" required value="${vo.title}"></td>
	</tr>
	<tr>
		<th>내용</th>
		<td><textarea class="form-control" cols="100" rows="6" name="content" required>${vo.content}</textarea></td>
	</tr>
	<tr>
		<th>작성자</th>
		<td><input class="form-control" name="writer" required value="${vo.writer}"></td>
	</tr>
	<tr>
		<th>비밀번호</th>
		<td><input class="form-control" name="pw" required type="password"></td>
	</tr>
	<tr>
		<td colspan="5" id="update">
			<button class="btn btn-primary">등록</button>
			<button class="btn btn-secondary" type="button" onclick="history.back();">취소</button>
			<button class="btn btn-danger" type="reset">초기화</button>
		</td>
	</tr>
</table>
</form>
</div>
</body>

</html>