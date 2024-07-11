<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MAIN</title>

<link rel="stylesheet" href="/css/main.css">
<script type="text/javascript" src="/js/main.js"></script>

</head>

<body>
<div class="container">
  <h2>WEBJJANG.COM</h2> 
    <div class="row ">
      <div class="col-md-12">
      	<jsp:include page="imglist.jsp"/>
      </div>
    </div>
    <br>
    <div class="row">
      <div class="col-md-6 module">
      	<jsp:include page="boardlist.jsp"/>
      </div>
      <div class="col-md-6 module">
      공지사항
      </div>
    </div>
</div>

</body>
</html>