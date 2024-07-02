<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login form</title>
</head>

<body>
<div class="container">
<h3>로그인</h3>
  <form action="login.do" method="post">
    <div class="form-group">
      <label for="id">ID:</label>
      <input class="form-control" autocomplete="none" id="id" placeholder="Enter id" name="id">
    </div>
    <div class="form-group">
      <label for="pw">Password:</label>
      <input type="password" class="form-control" placeholder="Enter password" name="pw">
    </div>
    <button type="submit" class="btn btn-primary">Login</button>
  </form>
</div>
</body>
</html>