<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
request.setCharacterEncoding("utf-8");
%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>WRITE FORM</title>

</head>


<body>
<div class="container">
	<h1>[ WRITE FORM ]</h1>
<form action="write.do" method="post">
<input name="perPageNum" value="${param.perPageNum}" type="hidden">
<table class="table">
	<tr>
		<th>제목</th>
		<td><input class="form-control" name="title" placeholder="제목을 입력하시오." required></td>
	</tr>
	<tr>
		<th>내용</th>
		<td><textarea class="form-control" rows="6" name="content" placeholder="내용을 입력하시오." required></textarea></td>
	</tr>
	<tr>
		<th>작성자</th>
		<td><input class="form-control" name="writer" placeholder="작성자를 입력하시오(2자~10자의 한글 또는 영문 이름)." required></td>
	</tr>
	<tr>
		<th>비밀번호</th>
		<td><input class="form-control" name="pw" type="password" placeholder="비밀번호를 입력하시오(6자~20자의 영문, 숫자, 특수문자로 조합)." required></td>
	</tr>
	<tr>
		<th>비밀번호 확인</th>
		<td><input class="form-control" placeholder="비밀번호를 입력하시오." type="password" required></td>
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