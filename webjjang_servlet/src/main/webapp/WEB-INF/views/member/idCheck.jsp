<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- id가 중복이 아닌 경우 -->
<c:if test="${empty id}">
사용 가능한 아이디입니다.
</c:if>

<!-- id가 중복인 경우 경우 -->
<c:if test="${!empty id}">
중복된 아이디입니다. 다른 아이디를 입력하십시오.
</c:if>