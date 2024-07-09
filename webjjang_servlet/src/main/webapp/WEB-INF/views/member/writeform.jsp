<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>WRITE FORM</title>

<!-- date picker을 사용하기 위해 추가한 link -->
<link rel="stylesheet" href="https://code.jquery.com/ui/1.13.3/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/ui/1.13.3/jquery-ui.js"></script>

<script>
$(function(){
	//입력 데이터 유효성 검사
	$("#id").keyup(function(){
		let id=$("#id").val();
		console.log("id: "+id);
		//id 최소 길이 충족시 경고문 색상 변경
		if(id.length>2){
			//데이터 중복 여부 확인
			//서버 데이터 확인: ajax-load(url((, data), function(){}))
			//call-back function(결과, 상태(error/success), 통신객체)
			$("#checkId").load("/ajax/idCheck.do?id="+id, function(result){
				if(result.indexOf("중복")>=0){
					$("#checkId").removeClass("alert-danger alert-success").addClass("alert-danger");
				}else{
					$("#checkId").removeClass("alert-success alert-danger").addClass("alert-success");
				}
			});	 		
		}else{
			$("#checkId").text("ID는 3자 이상 필수 입력 사항입니다.");
			//색상 변경
	 		$("#checkId").removeClass("alert-success");
	 		$("#checkId").addClass("alert-danger");	 		
		}//end of if-else
	});//end of id key-up
	
	$("#pw, #pwd").keyup(function(){
		//비밀번호 길이 체크
		let pw=$("#pw").val();
		let pwd=$("#pwd").val();
		
		console.log("pw: "+pw);
		console.log("pwd: "+pwd);
		
		if(pw.length<4){
			//경고창 빨갛게 만들기
			$("#checkPw").removeClass("alert-danger alert-success").addClass("alert-danger");
			//경고창 문구 변경
			$("#checkPw").text("PW는 6자 이상 필수 입력 사항입니다.");
		}else{
			$("#checkPw").removeClass("alert-danger alert-success").addClass("alert-success");
			//경고창 문구 변경
			$("#checkPw").text("올바른 입력값입니다.");
		}//end of if-else: 비밀번호 길이 체크
		if(pwd.length<4){
			//경고창 빨갛게 만들기
			$("#checkPwd").removeClass("alert-danger alert-success").addClass("alert-danger");
			//경고창 문구 변경
			$("#checkPwd").text("PW 확인은 6자 이상 필수 입력 사항입니다.");
		}else{
			if(pw!=pwd){
				$("#checkPwd").removeClass("alert-danger alert-success").addClass("alert-danger");
				//경고창 문구 변경
				$("#checkPwd").text("PW와 동일한 입력값을 입력해야 합니다.");
			}else{
				$("#checkPwd").removeClass("alert-danger alert-success").addClass("alert-success");
				//경고창 문구 변경
				$("#checkPwd").text("올바른 입력값입니다.");
			}
		}//end of if-else: 비밀번호 확인 길이 및 유효성 확인
	});//end of pw key up
	
	//date-picker
	$( ".datepicker" ).datepicker({ 
		minDate: "-150Y", maxDate: "0D", dateFormat:"yy/mm/dd", 
		changeMonth: true, changeYear: true,
		dayNamesMin: [ "일", "월", "화", "수", "목", "금", "토" ],
		monthNamesShort: [ "1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월" ]
	});
	
});
</script>


</head>


<body>
<div class="container">
	<h2>회원가입 폼</h2>
	<form action="write.do" method="post" enctype="multipart/form-data">
		 <div class="form-group">
		    <label for="id">아이디</label>
			<input id="id" name="id" required autocomplete="off"
				class="form-control" maxlength="20"
				pattern="^[a-zA-Z][a-zA-Z0-9]{2,19}$"
				title="맨앞 글자는 영문자 뒤에는 영숫자 입력. 3~20 이내로 입력"
				placeholder="아이디 입력"
			>
		  </div>
		  <div id="checkId" class="alert alert-danger">
		  	아이디는 필수 입력 입니다. 3글자 이상입니다.
		  </div>
		  
		 <div class="form-group">
		    <label for="pw">비밀번호</label>
			<input id="pw" name="pw" required type="password"
				class="form-control" maxlength="20"
				pattern="^.{4,20}$"
				title="4~20 이내로 입력"
				placeholder="비밀번호 입력"
			>
		  </div>
		  <div id="checkPw" class="alert alert-danger">
		  	비밀번호는 필수 입력 입니다. 4글자 이상 입력하셔야 합니다.
		  </div>
		  
		 <div class="form-group">
		    <label for="pwd">비밀번호 확인</label>
			<input id="pwd" required type="password"
				class="form-control" maxlength="20"
				pattern="^.{4,20}$"
				title="4~20 이내로 입력"
				placeholder="비밀번호 확인 입력"
			>
		  </div>
		  <div id="checkPwd" class="alert alert-danger">
		  	비밀번호 확인는 필수 입력 입니다. 4글자 이상 입력하셔야 합니다.
		  </div>
		  
		 <div class="form-group">
		    <label for="name">이름</label>
			<input id="name" name="name" required
				class="form-control" maxlength="10"
				pattern="^[가-힣]{2,10}$"
				title="한글로 2~10자 이내로 입력"
				placeholder="이름 입력"
			>
		  </div>
		  
		 <div class="form-group">
		    <label>성별</label>
			<div class="form-check-inline">
			  <label class="form-check-label">
			    <input type="radio" class="form-check-input" name="gender"
			     checked value="남자" > 남자
			  </label>
			</div>
			<div class="form-check-inline">
			  <label class="form-check-label">
			    <input type="radio" class="form-check-input" name="gender"
			     value="여자" > 여자
			  </label>
			</div>
		  </div>
		 
		 <div class="form-group">
		    <label for="birth">생년월일</label>
			<input id="birth" name="birth" required autocomplete="off"
				class="form-control datepicker" 
			>
		  </div>
		  
		 <div class="form-group">
		    <label for="img">첨부 이미지</label>
			<input name="img" required 
				class="form-control" type="file"
			>
		  </div>
		  
		  <div class="form-group">
		    <label for="email">이메일</label>
			<input id="email" name="email" required
				class="form-control"
				pattern="^[a-zA-Z0-9]{3,30}$"
				placeholder="E-Mail 입력"
			>
		  </div>
		  
		  <div class="form-group">
		    <label for="tel">전화번호</label>
			<input id="tel" name="tel" required
				class="form-control"
				pattern="^[0-9]{11,13}$"
				placeholder="전화번호 입력"
			>
		  </div>
		  
		<button class="btn btn-primary">등록</button>
		<button type="reset" class="btn btn-secondary">다시입력</button>
		<button type="button" onclick="history.back();" class="btn btn-warning">취소</button>
		
	</form>
</div>
</body>

</html>