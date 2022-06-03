<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="true" %>
<%@ page language="java" import="java.net.InetAddress" %>
<%
InetAddress inet= InetAddress.getLocalHost();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns='http://www.w3.org/1999/xhtml' xmlns:ev='http://www.w3.org/2001/xml-events' xmlns:w2='http://www.inswave.com/websquare' xmlns:xf='http://www.w3.org/2002/xforms'>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<!-- 		<meta name="viewport" content="width=device-width, initial-scale=1.0"> -->
		<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<!-- 		<link rel="shortcut icon" type="image⁄x-icon" href="/assets/images/contents/Demo_campus.ico"> -->
		<title>YERP</title>
<%
// 	String w2xPath = request.getParameter("w2xPath");
	String userId = (String) session.getAttribute("SESSION_USER_IDNT");
	String movePage = (String) request.getAttribute("movePage");
	boolean redirectLogin = true;
// 	if(w2xPath == null) response.sendRedirect("/?w2xPath=/index.xml");
// 	if("/login.xml".equals(w2xPath)) {
// 		if((null != userId && !"".equals(userId))) {
// 			response.sendRedirect("/?w2xPath=/index.xml");
// 		}
// 		else {
// 			redirectLogin = false;
// 		}
// 	}
// 	if(redirectLogin) {
// 		if((null == userId || "".equals(userId))) {
// 			response.sendRedirect("/?w2xPath=/login.xml");
// 		}
// 	}
// 	if(movePage == null) response.sendRedirect("/index");
// 	if("/login.xml".equals(movePage)) {
// 		if((null != userId && !"".equals(userId))) {
// 			response.sendRedirect("/index");
// 		}
// 		else {
// 			redirectLogin = false;
// 		}
// 	}
// 	if(redirectLogin) {
// 		if((null == userId || "".equals(userId))) {
// 			response.sendRedirect("/login");
// 		}
// 	}
%>
		<script type="text/javascript">
			var WebSquareExternal = {"baseURI": "/websquare/", "w2xPath":"<%=movePage%>" };
// 			console.log(WebSquareExternal.w2xPath);
			if(parent.WebSquare){
				if((WebSquareExternal.w2xPath).toLowerCase().indexOf("login") < 0){
					WebSquareExternal.w2xPath = "";
				}
			}
		</script>
		<script type="text/javascript" src="/websquare/javascript.wq?q=/bootloader"></script>
		<script type="text/javascript">
			window.onload = init;
			function init() {
				try{
// 					gcm.CONTEXT_PATH = "${pageContext.request.contextPath}";
					WebSquare.startApplication(WebSquareExternal.w2xPath);
				} catch(e) {
					alert(e.message);
				}
			}
		</script>
	</head>
<body></body>
</html>