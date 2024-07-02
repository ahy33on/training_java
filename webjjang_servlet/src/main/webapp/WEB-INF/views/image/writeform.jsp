<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>WRITE FORM</title>

</head>


<body>
<div class="container">
	<h1>[ WRITE FORM ]</h1>
<form action="write.do" method="post" enctype="multipart/form-data">
<input name="perPageNum" value="${param.perPageNum}" type="hidden">
<input type="hidden" id="id" name="id" value="${login.id}">
<table class="table">
	<tr>
		<th>상품명</th>
		<td><input class="form-control" name="title" placeholder="제목을 입력하시오." required></td>
	</tr>
	<tr>
		<th>상품설명</th>
		<td><textarea class="form-control" rows="6" name="content" placeholder="내용을 입력하시오." required></textarea></td>
	</tr>
	<tr>
		<th>이미지</th>
		<td><input class="form-control" name="imgfile" type="file" required></td>
	</tr>
	<tr>
		<td colspan="2">
			<button class="btn btn-primary">등록</button>
			<button class="btn btn-danger" type="reset">초기화</button>
			<button class="btn btn-warning" type="button" onclick="history.back();">취소</button>
		</td>
	</tr>
</table>
</form>
</div>
</body>

</html>