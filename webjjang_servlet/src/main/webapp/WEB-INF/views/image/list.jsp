<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="pageNav" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>IMAGE</title>
<script type="text/javascript">
$(function(){
	
	$(".card").click(function(){
		//화면이동: 글 번호
		let no=$(this).find(".no").val();
		location="view.do?no="+no;
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
.card:hover{
cursor: pointer;
background: #EEE;
}
.imgdiv{
	display: flex;
    justify-content: center; /* 수평 중앙 정렬 */
    align-items: center; /* 수직 중앙 정렬 */
    overflow: hidden; /* 넘치는 부분 숨김 */
}
.card-text{
	display: -webkit-box;
    -webkit-box-orient: vertical;
    overflow: hidden;
    text-overflow: ellipsis;
    -webkit-line-clamp: 3; /* 표시할 최대 줄 수 */
}
</style>
</head>


<body>
<div class="container">
<h1>[ I M A G E ]</h1>
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
	      				<option value="t">상품명</option>
	      				<option value="c">상품설명</option>
	      				<option value="f">파일명</option>
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
			    	<option>6</option>
			    	<option>9</option>
			    	<option>12</option>
			    </select>
			  </div>
		  </div>
		</div>
	<!-- end of md-5: 한 페이지당 보여줄 글 개수 설정 및 표시 -->
	</div>
</form>
<br>


<c:if test="${empty list}">
데이터가 존재하지 않습니다.
</c:if>

 
<c:if test="${!empty list}">
	<div class="row">	
		<c:forEach items="${list}" var="vo" varStatus="status">
			<c:if test="${(status.index!=0)&&(status.index%3==0)}"> <!-- 행의 시작점이면 -->
				</div>
				<div class="row">
			</c:if>
			 <div class="col-md-4 mb-3">
			  <div class="card" style="height:550px;">
			  	<input type="hidden" class="no" value="${vo.no}">
			  	<div class="imgdiv" style="height:370px">
			   		<img style="object-fit: contain;max-width: 100%;max-height: 100%;" 
			   		class="card-img-top" src="${vo.fileName}" alt="carlfriedrik">
			    </div>
			    <div class="card-body">
			      <h4 class="card-title">${vo.title}</h4>
			      <p class="card-text">${vo.content}</p>
			    </div>
			  </div>
			 </div>
		</c:forEach>
	</div>
</c:if>

<div>
<br>
<pageNav:pageNav listURI="list.do" pageObject="${po}"></pageNav:pageNav>
</div>

<c:if test="${!empty login}">
	<a href="writeform.do?perPageNum=${po.perPageNum}" class="btn btn-primary">등록</a>
</c:if>

</div>
</body>

</html>