<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="pageNav" tagdir="/WEB-INF/tags" %>
<div class="card">
    <div class="card-header">
    	<h3 style="display:inline">REPLY</h3>
    	<span class="btn btn-primary float-right" id="replyWriteBtn">등록</span>
    </div>
    <div class="card-body">
	<!-- 데이터 존재 여부 확인 -->
    <c:if test="${empty replyList}">등록된 댓글이 없습니다.</c:if>
    <c:if test="${!empty replyList}">
    <!-- 댓글 수만큼 반복 처리 -->
    	<c:forEach items="${replyList}" var="replyVO">
	    	<div class="card replyDataRow" data-rno="${replyVO.rno}"
	    	style="margin:5px 0;">
			    <div class="card-header">
			    	<b class="replyWriter">${replyVO.writer}</b>
			    	<span class="float-right">${replyVO.writeDate}</span>
			    </div>
			    <div class="card-body">
			    	<pre class="replyContent">${replyVO.content}</pre>
			    </div> 
			    <div class="card-footer">
			    	<button class="btn btn-success replyUpdateBtn">수정</button>
			    	<button class="btn btn-secondary">삭제</button>
			    </div>
			</div>
		</c:forEach>
	</c:if>
    </div>
    <div class="card-footer">
    	<pageNav:replayPageNav listURI="view.do" pageObject="${replyPageObject}" query="&inc=0"/>
    </div>
</div>

<!-- 댓글 등록, 수정, 삭제를 위한 모달창 위치 -->
<!-- 댓글 등록 모달창 -->
  <div class="modal fade" id="boardReplyModal">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
      
        <!-- Modal Header -->
        <div class="modal-header">
        <!-- 버튼에 따라서 <h4>의 텍스트를 수정하여 사용 -->
          <h4 class="modal-title">Reply Write</h4>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
        </div>
        
        <form id="boardReplyFrom" method="post">
	        <!-- Modal body -->
	        <div class="modal-body">
	          <!-- hidden: rno / unhidden: writer, content, pw -->
	          <input type="hidden" id="rno" name="rno">
	          <input type="hidden" name="no" value="${param.no}">
	          
	          <div class="form-group">
				  <label for="content">Comment:</label>
				  <textarea class="form-control" rows="3" id="content" name="content"></textarea>
			  </div>
	          <div class="form-group">
				  <label for="writer">Writer:</label>
				  <input type="text" class="form-control" id="writer" name="writer">
			  </div>
			  <div class="form-group">
				  <label for="pw">Password:</label>
				  <input type="password" class="form-control" id="pw" name="pw">
			  </div>
			  
	        </div>
	        
	        <!-- Modal footer -->
	        <div class="modal-footer">
	          <button class="btn btn-primary" type="button"
		      	 id="replyModalWriteBtn">등록</button>
		      <button class="btn btn-success" type="button"
		      	 id="replyModalUpdateBtn">수정</button>
		      <button class="btn btn-danger" type="button"
		      	 id="replyModalDeleteBtn">삭제</button>
	        </div>
        </form>
        
      </div>
    </div>
  </div>
  
  <script>
  $(function(){ 
	 $("#replyWriteBtn").click(function(){
		 //댓글 등록 처리
		 $("#boardReplyModal").find(".modal-title").val("Reply Write");
		 //input, textarea 보이도록
		 $("#boardReplyFrom").find(".form-group").show();
		 //input, textarea의 data 비우기
		 $("#boardReplyFrom").find(".form-group>input", ".form-group>textarea").val("");
		 
		 //modal의 버튼이 보이도록
		 $("#boardReplyFrom button").show();
		 //update, delete 버튼 보이지 않도록
		 $("#replyModalUpdateBtn", "#replyModalDeleteBtn").hide();
		 
		 //모달창 띄우기
		 $("#boardReplyModal").modal("show");
	 });
	 $(".replyUpdateBtn").click(function(){
		 //댓글 수정 처리
		 $("#boardReplyModal").find(".modal-title").text("Reply Update");
		 //input, textarea 보이도록
		 $("#boardReplyFrom").find(".form-group").show();
		 //input, textarea의 data 비우기
		 $("#boardReplyFrom").find(".form-group>input", ".form-group>textarea").val("");
		 
		 let replyDataRow=$(this).closest(".replyDataRow");
		 let rno=replyDataRow.data("rno");
		 let content=replyDataRow.find(".replyContent").text();
		 let writer=replyDataRow.find(".replyWriter").text();
		 
		 //모달 창의 if를 통해 데이터 세팅
		 $("#rno").val(rno);
		 $("#content").val(content);
		 $("#writer").val(writer);
		 
		 $("#boardReplyForm").find("input, textarea").show();
		 
		 //modal의 버튼이 보이도록
		 $("#boardReplyFrom button").show();
		 //update, delete 버튼 보이지 않도록
		 $("#replyModalWriteBtn", "#replyModalDeleteBtn").hide();
		 
		 //모달창 띄우기
		 $("#boardReplyModal").modal("show");
	 });
  });
  </script>