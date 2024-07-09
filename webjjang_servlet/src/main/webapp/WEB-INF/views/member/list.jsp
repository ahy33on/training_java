<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="pageNav" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>MEMBER LIST</title>
<style>
.datarow:hover{
background:#eee;
cursor:pointer;
}
</style>

<script type="text/javascript">
$(function(){
	
	function datarowClick(){
		alert("data-row click");
	}
	
	// 이벤트처리
	$(".dataRow").on("click", function(){
		datarowClick();
	});
	
	//등급, 상태에 마우스 올리면 datarow 클릭 이벤트가 발생하지 않음.
	$(".grade, .status").parent().on("mouseover", function(){
		$(".datarow").off("click");
	})
	//등급, 상태에서 마우스를 떼면 datarow 클릭 이벤트가 발생함.
	.on("mouseout", function(){
		$(".datarow").on("click", function(){
			datarowClick();
		})
	});
	
	//ajax 등으로 나중에 태그가 추가되는 경우, 이벤트가 지원되지 않기도 하는데,
	//이럴 때 사용하는 키워드가 on이다. on("event", "대상", function(){});
	$(".datarow").on("change", ".grade, .status", function(){
		let change=$(this).val();
		let origin=$(this).data("data");
		console.log("원래 데이터: "+origin+", 변경 데이터: "+change)
		//next(): 뒤에 오는 element를 가리킨다
		//prop(): property의 약자로, 태그 내의 속성을 다룰 수 있다.
		if(change==origin)
			$(this).next().find(".btn").prop("disabled", true);
		else {
			let status=$(".status").data("data");
			if(status=="정상"||change=="정상"){
				$(this).next().find(".btn").prop("disabled", false);
			}
		}
	});
});
</script>

<style>
.dataRow:hover{
cursor: pointer;
background: #EEE;
}
</style>
</head>


<body>
<div class="container">
<h1>[ MEMBER LIST ]</h1>
<br>

<table class="table">
	<tr>
		<th>사진</th>
		<th>ID</th>
		<th>이름</th>
		<th>성별</th>
		<th>생년월일</th>
		<th>연락처</th>
		<th>등급</th>
		<th>상태</th>
	</tr>
	
	<c:forEach items="${list}" var="vo">
		<tr class="datarow">
			<td>
				<c:if test="${!empty vo.photo}">
					<img src="${vo.photo }" style="width:30px;height:30px">
				</c:if>
				<c:if test="${empty vo.photo}">
					<i class="fa fa-user-circle" style="font-size:30px;"></i>
				</c:if>
			</td>
			<td class="id">${vo.id}</td>
			<td>${vo.name}</td>
			<td>${vo.gender}</td>
			<td>${vo.birth}</td>
			<td>${vo.tel}</td>
			<td>
				<form action="changeGrade.do">
					<input name="id" value="${vo.id }" type="hidden">
					<div class="input-group mb-3">
						<select class="form-control grade" name="gradeNo" data-data="${vo.gradeNo}">
							<option value="1" ${(vo.gradeNo==1)?"selected":""}>일반회원</option>
							<option value="9" ${(vo.gradeNo==9)?"selected":""}>관리자</option>
						</select>
						<div class="input-group-append">
							<button class="btn btn-primary" disabled>변경</button>
						</div>
					</div>	
				</form>
			</td>
			<td>
				<form action="changeStatus.do">
					<input name="id" value="${vo.id }" type="hidden">
					<div class="input-group mb-3">
						<select class="form-control status" name="status" data-data="${vo.status}">
							<option value="정상" ${(vo.status=='정상')?"selected":""}>정상</option>
							<option value="탈퇴" ${(vo.status=='탈퇴')?"selected":""}>탈퇴</option>
							<option value="휴면" ${(vo.status=='휴면')?"selected":""}>휴면</option>
							<option value="강퇴" ${(vo.status=='강퇴')?"selected":""}>강퇴</option>
						</select>
						<div class="input-group-append">
							<button class="btn btn-primary" disabled>변경</button>
						</div>
					</div>	
				</form>
			</td>
		</tr>
	</c:forEach>
</table>
	<a href="writeform.do?perPageNum=${pageObject.perPageNum}"><button class="btn btn-primary" type="button">등록</button></a>
	<div><pageNav:pageNav listURI="list.do" pageObject="${po}"></pageNav:pageNav></div>
</div>
</body>

</html>