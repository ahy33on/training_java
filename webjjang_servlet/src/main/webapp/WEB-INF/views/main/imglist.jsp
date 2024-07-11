<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container">
	<h3>[ I M A G E ]</h3>
	<br>
	<c:if test="${empty imglist}">
	데이터가 존재하지 않습니다.
	</c:if>
	 
	<c:if test="${!empty imglist}">
		<div class="row">	
			<c:forEach items="${imglist}" var="vo" varStatus="status">
				<c:if test="${(status.index!=0)&&(status.index%3==0)}"> <!-- 행의 시작점이면 -->
					</div>
					<div class="row">
				</c:if>
				 <div class="col-md-4 mb-4 datarow img">
				  <div class="card" style="height:500px;">
				  	<input type="hidden" class="no" value="${vo.no}">
				  	<div class="imgdiv" style="height:350px">
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
</div>