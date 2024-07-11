<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
request.setCharacterEncoding("utf-8");
%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>NOTICE WRITE FORM</title>

<!-- date picker을 사용하기 위해 추가한 link -->
<link rel="stylesheet" href="https://code.jquery.com/ui/1.13.3/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/ui/1.13.3/jquery-ui.js"></script>

<script>
$(function(){
	//date-picker
	$( ".datepicker" ).datepicker({
		minDate:"0D",
		dateFormat:"yy/mm/dd", 
		changeMonth: true, changeYear: true,
		dayNamesMin: [ "일", "월", "화", "수", "목", "금", "토" ],
		monthNamesShort: [ "1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월" ]
	});
	
	$("#startdate").change(function(){
		$("#enddate").datepicker("option", "minDate", $(this).val());
	});
	$("#enddate").change(function(){
		$("#startdate").datepicker("option", "maxDate", $(this).val());
	});
	
	$(".ereset").click(function(){
		$("#enddate").val("");
		$("#startdate").datepicker( "option", "maxDate", null);
	});
	$(".sreset").click(function(){
		$("#startdate").val("");
		$("#enddate").datepicker( "option", "minDate", new Date());
	});
});
</script>

</head>


<body>
<div class="container">
	<h3>[ 공지사항 등록 ]</h3>
<form action="write.do" method="post">
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
		<th>시작일</th>
		<td><input style="width:80%;display:inline" class="form-control datepicker" id="startdate" name="startdate" required>
		<button type="button" class="btn btn-secondary sreset">초기화</button></td>
	</tr>
	<tr>
		<th>종료일</th>
		<td><input style="width:80%;display:inline" class="form-control datepicker" id="enddate" name="enddate" required>
		<button type="button" class="btn btn-secondary ereset">초기화</button></td>
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