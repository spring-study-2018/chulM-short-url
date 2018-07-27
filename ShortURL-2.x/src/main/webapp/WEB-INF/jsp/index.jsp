<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<body>

	<label>Ur 생성 버튼:</label>
	<input type="text" id="orgUrl" value="" />
	<input type="button"id="createUrlBtn" value="create" />
	
	<div id = "urlField"></div>
	
	<script type="text/javascript">
	   $(document).ready(function(){
	         
		$("#createUrlBtn").click(function(){
		  var regex = "^((http(s?))\:\/\/)([0-9a-zA-Z\-]+\.)+[a-zA-Z]{2,6}(\:[0-9]+)?(\/\S*)?$";
		  var input = $("#orgUrl").val();
			var match = input.match(regex);
			if(match){
			 	
			 $.ajax({
	            type : "GET",
	            url : "/create",
	            data : {"url":input},
	            error : function(){
	                alert('전송 실패!!');
	            },
	            success : function(data){
	                alert("데이터 값 : " + data) ;
	                $('#urlField').append("<br>");
	                $('#urlField').append("create URL: <Strong>"+data+"</Strong>");
	            }  
	        });	 		  
			}else{
				alert('올바른 URL 형식을 입력하세요.');
			}
		 });
	   });
	</script>
</body>
</html>