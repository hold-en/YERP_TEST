<%@ page import="java.util.Enumeration" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="true" %>
<%
// 	Enumeration<String> names = request.getParameterNames();
// 	String fields = "";
	String file = (String) request.getParameter("report");
	String streamdata = (String) request.getParameter("streamdata");
// 	while (names.hasMoreElements()) {
// 		String pName = names.nextElement();
// 		String value = request.getParameter(pName);
// 		value = value == null ? "" : value;
// 		if (pName.equals("report")) {
// 			file = value + ".jrf";
// 		}
// 		else if(pName.equals("streamdata")){
// 			streamdata = value;
// 		}
// 		else {
// 			if (!value.equals("")) {
// 				fields += "<field name='"+pName+"'>"+value+"</field>";
// 			}
// 		}
// 	}
%>
<!DOCTYPE html>
<html>
<head>
	<title>Report</title>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<script type="text/javascript">
		var file = "<%=file%>";
		var streamdata = "<%=streamdata%>";
		
		$(document).ready(function () {
			$("input[name=file]").val(file);
			$("input[name=streamdata]").val(streamdata);
			$("form[name=loadForm]").submit();
		});
	</script>
</head>
<body>
<form name="loadForm" action="http://172.16.11.48:9090/ubi4/openUbiReport.jsp" method="POST" target="frm">
	<input type="hidden" name="file"/>
	<input type="hidden" name="streamdata"/>
</form>
<iframe name="frm"></iframe>
</body>
</html>