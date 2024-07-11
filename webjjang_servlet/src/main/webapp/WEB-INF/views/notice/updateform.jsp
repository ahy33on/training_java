<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>NOTICE UPDATE FORM</title>

<!-- date picker을 사용하기 위해 추가한 link -->
<link rel="stylesheet" href="https://code.jquery.com/ui/1.13.3/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/ui/1.13.3/jquery-ui.js"></script>

<script>
$(function(){
	$( ".datepicker" ).datepicker({
		dateFormat:"yy/mm/dd", 
		changeMonth: true, changeYear: true,
		dayNamesMin: [ "일", "월", "화", "수", "목", "금", "토" ],
		monthNamesShort: [ "1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월" ]
	});
});
</script>
</head>


<body>
<div class="container">
<form action="update.do" method="post">
<h1>[ 공지사항 수정 ]</h1>
<input type="hidden" name="no" value="${vo.no}">
<table class="table">
	<tr>
		<th style="width:15%">${vo.no}번</th>
		<th>[ ${vo.writeDate} ]에 작성된 공지</th>
	</tr>
	<tr>
		<th>제목</th>
		<td><input class="form-control" name="title" required value="${vo.title}"></td>
	</tr>
	<tr>
		<th>내용</th>
		<td><textarea class="form-control" cols="100" rows="6" name="content" required>${vo.content}</textarea></td>
	</tr>
	<tr>
		<th>시작일</th>
		<td><input class="form-control datepicker" name="startdate" required value="${vo.startDate}"></td>
	</tr>
	<tr>
		<th>종료일</th>
		<td><input class="form-control datepicker" name="enddate" required value="${vo.endDate}"></td>
	</tr>
	<tr>
		<td colspan="5" id="update">
			<button class="btn btn-primary">적용</button>
			<button class="btn btn-secondary" type="button" onclick="history.back();">취소</button>
			<button class="btn btn-danger" type="reset">초기화</button>
		</td>
	</tr>
</table>
</form>
</div>
</body>

</html>