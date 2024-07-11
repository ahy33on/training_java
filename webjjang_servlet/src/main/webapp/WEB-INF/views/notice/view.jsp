<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>NOTICE VIEW</title>

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
		<th colspan="3"><h3>[ 공지사항 상세보기 ]</h3></th>
	</tr>
	<tr>
		<th style="width:100px">${vo.no}번</th>
		<th>시작일 : ${vo.startDate}</th>
		<th>종료일 : ${vo.endDate}</th>
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
		<th>작성일</th>
		<td colspan="2">${vo.writeDate}</td>
	</tr>
	<tr>
		<th>수정일</th>
		<td colspan="2">${vo.updateDate}</td>
	</tr>
	<tr>
		<td colspan="5" id="write">
		<div class="float-right">
			<a href="updateform.do?no=${param.no}&${po.pageQuery}">
			<button type="button" class="btn btn-primary">수정</button></a>
			
			<a href="list.do?key=${param.key}&page=${param.page}&word=${param.word}&perPageNum=${param.perPageNum}">
			<button type="button" class="btn btn-secondary">이전</button></a>
			
			<button id="dltbtn" type="button" class="btn btn-danger">삭제</button>
		</div>
		</td>
	</tr>
</table>

</div> <!-- end of container -->

<!-- deleteModal -->
<div class="modal fade" id="dltModal">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
      	<form action="delete.do" method="post">
        <!-- Modal Header -->
        <div class="modal-header">
          <h4 class="modal-title">정말 삭제하시겠습니까?</h4>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
        </div>
        
        <!-- Modal body -->
        <div class="modal-body">
          <input type="hidden" name="no" value="${param.no}">
		  <button class="btn btn-danger">삭제확인</button>
		  <button id="ccbtn" type="button" class="btn btn-secondary">취소</button>
        </div>
        
        </form>
      </div>
    </div>
  </div>


</body>

</html>