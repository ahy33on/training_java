<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="no" value="${request.no}" scope="page"/>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>VIEW</title>

<script type="text/javascript">
$(function(){
	$("#dltbtn").click(function(){
		$("#pw").val("");
		$("#dltModal").modal("show");
	});
	$("#ccbtn").click(function(){
		$("#dltModal").modal("hide");
	});
});
</script>

</head>


<body>
<div class="container">
<table class="table">
	<tr>
		<th colspan="3"><h1>[ V I E W ]</h1></th>
	</tr>
	<tr>
		<th style="width:100px">${vo.no}번</th>
		<th>작성일 : ${vo.writeDate}</th>
		<th>조회수 : ${vo.hit}</th>
	</tr>
	<tr>
		<tr>
		<th>제목</th>
		<td colspan="2">${vo.title}</td>
	</tr>
	<tr>
		<th>내용</th>
		<td colspan="2">${vo.content}</td>
	</tr>
	<tr>
		<th>작성자</th>
		<td colspan="2">${vo.writer}</td>
	</tr>
	<tr>
		<td colspan="5" id="write">
			<a href="updateform.do?no=${param.no}&key=${param.key}&page=${param.page}&word=${param.word}&perPageNum=${param.perPageNum}">
			<button type="button" class="btn btn-primary">수정</button></a>
			
			<a href="list.do?key=${param.key}&page=${param.page}&word=${param.word}&perPageNum=${param.perPageNum}">
			<button type="button" class="btn btn-secondary">이전</button></a>
			
			<button id="dltbtn" type="button" class="btn btn-danger">삭제</button>
			
		</td>
	</tr>
</table>

<!-- 댓글 처리 시작 -->
<jsp:include page="reply.jsp"></jsp:include>
<!-- 댓글 처리 끝 -->

</div> <!-- end of container -->

<!-- deleteModal -->
<div class="modal fade" id="dltModal">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
      	<form action="delete.do" method="post">
        <!-- Modal Header -->
        <div class="modal-header">
          <h4 class="modal-title">본인 확인용 비밀번호 입력</h4>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
        </div>
        
        <!-- Modal body -->
        <div class="modal-body">
          <input type="hidden" name="no" value="${param.no}">
		  <input type="hidden" name="perPageNum" value="${param.perPageNum}">
		  <input id="pw" style="width:300px" type="password" name="pw" placeholder="비밀번호를 입력하시오.">
        </div>
        
        <!-- Modal footer -->
        <div class="modal-footer">
          <button class="btn btn-danger">삭제확인</button>
		  <button id="ccbtn" type="button" class="btn btn-secondary">취소</button>
        </div>
        
        </form>
      </div>
    </div>
  </div>


</body>

</html>