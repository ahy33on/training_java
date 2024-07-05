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
		//경고창1-alert: 일반경고
		//경고창2-confirm: 확인/취소
		//경고창3-prompt: key in
		if(!confirm("정말 삭제하시겠습니까?")) return false;
	});
	
	
	$("#imgbtn").click(function(){
		$("#pw").val("");
		$("#imgModal").modal("show");
	});
	
	$('[data-toggle="tooltip"]').tooltip();   
});
</script>

</head>


<body>
<div class="container">
<br>
    <div class="row">
      <div class="col-sm-6">
        <img src="${vo.fileName}" style="width:100%">
      </div>
      <div class="col-sm-6">
        <h3>${vo.title}</h3>
	    <hr>
	    ${vo.content}
      </div>
    </div>

<br>


<div class="button" style="float:right">
<c:if test="${login.id==vo.id}">
	<a href="updateform.do?no=${param.no}&key=${param.key}&page=${param.page}&word=${param.word}&perPageNum=${param.perPageNum}">
	<button id="udtbtn" type="button" data-toggle="tooltip" data-placement="top" title="상품명, 상품설명 변경" class="btn btn-primary">수정</button></a>
	
	<a href="delete.do?no=${vo.no}&dltfile=${vo.fileName}&perPageNum=${param.perPageNum}" 
	class="btn btn-danger" id="dltbtn">삭제</a>
	
	<button id="imgbtn" type="button" class="btn btn-warning">이미지 변경</button>
	
	<a href="${vo.fileName}" class="btn btn-outline-primary" download>다운로드</a>
</c:if>
	<a href="list.do?key=${param.key}&page=${param.page}&word=${param.word}&perPageNum=${param.perPageNum}">
	<button type="button" class="btn btn-secondary">이전</button></a>
</div>

<br>

<!-- 댓글 처리 -->
<%-- <jsp:include page="reply.jsp"></jsp:include> --%>

</div> <!-- end of container -->


<!-- 이미지 변경 모달 -->
<div class="modal fade" id="imgModal">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <!-- Modal Header -->
        <div class="modal-header">
          <h4 class="modal-title">이미지 변경</h4>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
        </div>
        
        <form action="changeimage.do" method="post" enctype="multipart/form-data">
        <!-- Modal body -->
        <div class="modal-body">
          <input type="hidden" name="no" value="${vo.no}">
          <!-- 페이지 정보 넘기기 -->
          <input type="hidden" name="page" value="${param.page}">
          <input type="hidden" name="perPageNum" value="${param.perPageNum}">
          <input type="hidden" name="key" value="${param.key}">
          <input type="hidden" name="word" value="${param.word}">
		  <input type="hidden" name="dltfile" value="${vo.fileName}">
		  <input id="changefile" type="file" name="changefile" required>
        </div>
        
        <!-- Modal footer -->
        <div class="modal-footer">
          <button class="btn btn-outline-primary">변경</button>
		  <button id="ccbtn" type="button" class="btn btn-secondary">취소</button>
        </div>
    	</form>
      </div>
    </div>
</div>

</body>

</html>