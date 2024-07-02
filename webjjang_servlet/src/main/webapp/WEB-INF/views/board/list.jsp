<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="pageNav" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>BOARD</title>
<script type="text/javascript">
$(function(){
	$(".dataRow").click(function(){
		//화면이동: 글 번호
		let no=$(this).find(".no").text();
		location="view.do?no="+no+"&inc=1&${po.pageQuery}";
	});
	
	//검색 데이터 세팅
	$("#key").val("${(empty po.key)?'t':'po.key'}");
	$("#perPageNum").val("${(empty po.perPageNum)?'10':'po.perPageNum'}");
	//perpagenum 처리
	$("#perPageNum").change(function(){
		//page는 1페이지로 변경, 검색 데이터를 전부 보내기
		$("#searchForm").submit();
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
<h1>[ B O A R D ]</h1>
<br>

<form action="list.do" id="searchForm">
<!-- PageObject.class에서 현재페이지를 page로 지정함. -->
	<input name="page" value="1" type="hidden">
	<div class="row">
	<!-- start of md-7: 글 검색 -->
		<div class="col-md-7">
			<div class="input-group mb-3">
				<div class="input-group-prepend">
				    <select name="key" id="key" class="form-control">
	      				<option value="t">제목</option>
	      				<option value="c">내용</option>
	      				<option value="w">작성자</option>
	      			</select>
	    		</div>
  				<input name="word" value="${po.word}" type="text" class="form-control form-control-xl" placeholder="검색어를 입력하시오">
  				<button class="btn btn-outline-primary" id="wrbtn"><i class="fa fa-search"></i></button>
			</div>
		</div>
	<!-- end of md-7: 글 검색 -->
		<div class="col-md-2"></div>
	<!-- 간격 조정을 위한 추가: div md-n으로 조정 -->
		<div class="col-md-3">
	<!-- start of mb-3: 한 페이지당 보여줄 글 개수 설정 및 표시 -->
		  <div style="width:180px;" class="float-right">
			  <div class="input-group mb-3">
			    <div class="input-group-prepend">
			      <span class="input-group-text">Rows/page</span>
			    </div>
			    <select id="perPageNum" name="perPageNum" class="form-control">
			    	<option>10</option>
			    	<option>15</option>
			    	<option>20</option>
			    </select>
			  </div>
		  </div>
		</div>
	<!-- end of md-5: 한 페이지당 보여줄 글 개수 설정 및 표시 -->
	</div>
</form>
<br>

<table class="table">
	<tr>
		<th>번호</th>
		<th>제목</th>
		<th>작성자</th>
		<th>작성일</th>
		<th>조회수</th>
	</tr>
	<c:forEach items="${list}" var="vo">
		<tr class="dataRow">
			<td class="no">${vo.no}</td>
			<td>${vo.title}</td>
			<td>${vo.writer}</td>
			<td>${vo.writeDate}</td>
			<td>${vo.hit}</td>
		</tr>
	</c:forEach>
	<tr>
		<td colspan="5" id="write">
			<a href="writeform.do?perPageNum=${pageObject.perPageNum}"><button class="btn btn-primary" type="button">등록</button></a>
		</td>
	</tr>
</table>
	<div><pageNav:pageNav listURI="list.do" pageObject="${po}"></pageNav:pageNav></div>
</div>
</body>

</html>