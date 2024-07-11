<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="pageNav" tagdir="/WEB-INF/tags" %>
<div class="container">
<h3>[ B O A R D ]</h3>
<br>
<table class="table">
	<tr>
		<th>번호</th>
		<th>제목</th>
		<th>작성자</th>
		<th>작성일</th>
		<th>조회수</th>
	</tr>
	<c:forEach items="${boardlist}" var="vo">
		<tr class="datarow board boardlink">
			<td class="no">${vo.no}</td>
			<td>${vo.title}</td>
			<td>${vo.writer}</td>
			<td>${vo.writeDate}</td>
			<td>${vo.hit}</td>
		</tr>
	</c:forEach>
</table>
</div>