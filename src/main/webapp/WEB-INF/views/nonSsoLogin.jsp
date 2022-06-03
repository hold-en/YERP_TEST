<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
	String message = (String)request.getAttribute("message");
	System.out.println("jsp_message :: " + message);
%>
<html>
<head>
	<link rel="stylesheet" href="/assets/nonSsoLogin/bootstrap.css">
	<link rel="stylesheet" href="/assets/nonSsoLogin/login.css">
	<link rel="stylesheet" href="/assets/nonSsoLogin/poizn.common.css">
	<script type="text/javascript">
	
		window.onload = function(){
			var message = '${message}';
			console.log("message :: {}", message);
			if(message == 'Y') {
				alert("아이디와 비밀번호를 확인하여 주십시오.");
			}
		}
	
	 	function formSubmit(){
			var userId = document.getElementById("userId").value.trim();
			var password = document.getElementById("password").value.trim();
			
			if(userId == null || userId == ''){
				alert("아이디를 입력해주세요.");
				return false;
			}
			if(password == null || password == ''){ 
				alert("비밀번호를 입력해주세요.");
				return false;
			}
			
			document.getElementById("userId").value = userId;
			document.getElementById("password").value = password;
			return true;
		}
		
	</script>
	<style>
		.dropdown-menu.open ul {
			max-height: 150px!important;
		}
		.nonSsoLabel {
			margin-top: 10px;
			text-align: center;
			font-size: 16px;
		}
		.loginText {
			font-weight: bold;	
		}
	</style>
</head>
<body>
<div class="site-wrapper">
	<div class="site-wrapper-inner">
		<div class="cover-container">
			<div class="inner cover">
			    <div class="login-content">
			    	<div class="text-center mt30">
				    	<img src="/assets/images/contents/Demo_campus1.png" alt="Demo 대학교 심볼" class="login-symbol">
				    </div>
				    <div class="row mt20">
				        <div class="col-xs-12">
				            <div class="login-body">
				            	<div>
							    	<h1 style="text-align: center; font-weight: bold; margin: 0px 0px 30px 0px; text-decoration: red wavy underline; text-shadow: #7eb037 1px 0 10px;" >관리자 시스템</h1>
							    </div>
				                <div class="row">
				                    <div class="col-xs-12 col-sm-12 login-con">
				                        <div class="login-tab">
				                            <form name="loginFrm" class="form-horizontal" action="/account/nonSsoLogin" method="POST" onsubmit="return formSubmit();"> 
				                                <fieldset> 
				                                <div class="form-group login-input">
												    <label class="sr-only" for="userId">아이디</label>
												    <div class="input-group">
												      <i class="fa fa-user"></i>
												      <input type="text" class="form-control loginText" id="userId" placeholder="학번(학생) / 아이디 또는 사번(교직원)을 입력해주세요" name="userId" />
												      
												      <!--test 용입니다.  -->
												      <!-- <input type="text" id="test" value="${returnUrl}"/>  -->
												      
												      							    
												    </div>
												    <label class="sr-only" for="staffPassword">비밀번호</label>
												    <div class="input-group mt15">
												      <i class="fa fa-lock"></i>
												      <input type="password" class="form-control loginText" id="password" placeholder="비밀번호를 입력하세요" name="password" />
												    </div>
												</div>
				                                <div class="row">
					                                <div class="col-xs-6 col-sm-6 mt5 nonSsoLabel"></div>
				                                    <div class="col-xs-6 col-sm-6">
				                                        <button data-role="login" type="submit" class="btn btn-login btn-block" id="loginButton">LogIn</button>
				                                    </div>
				                                </div>
				                                </fieldset>
				                            </form>
				                        </div>
				                    </div>
				                </div>
				            </div>
				            <div class="login-footer"></div>
				           	<div class="text-center mt10">
				           		<p class="login-copy">COPYRIGHT&copy; Demo COLLEGE.<br class="visible-xs" /> ALL RIGHTS RESERVED.</p>
				           	</div>
				        </div>
				    </div>
			    </div>
			    <div class="login-backdrop"><div class="backdrop-bg"></div></div>
			</div>
		</div>
	</div>
</div>
</body>
</html>