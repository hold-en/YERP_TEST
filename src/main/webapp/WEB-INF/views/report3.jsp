<%@ page import="java.util.Enumeration" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="true" %>
<!DOCTYPE html>
<html>
<head>
	<title>Report</title>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<script type="text/javascript">
		var file = "";
		var streamdata = "";
		
		function setFile(fileString) {
			file = fileString;
		};
		
		function setdata(dataString) {
			streamdata = dataString;
		};
		
		function submitForm(fileString, dataString) {
			console.log(fileString);
			$("input[name=file]").val(fileString);
			$("input[name=streamdata]").val(dataString);
			$("form[name=loadForm]").submit();
		};
		
// 		$(document).ready(function () {
// 			$("input[name=file]").val(file);
// 			$("input[name=streamdata]").val(streamdata);
// 			$("form[name=loadForm]").submit();
// 		});
	</script>
</head>
<body>
<form name="loadForm" action="http://localhost:9090/ubi4/openUbiReport.jsp" method="POST" target="frm">
	<input type="hidden" name="file"/>
	<input type="hidden" name="streamdata"/>
</form>
<iframe name="frm"></iframe>
</body>
</html>