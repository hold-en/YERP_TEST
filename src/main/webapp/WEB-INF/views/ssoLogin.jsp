<!DOCTYPE html>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<script>
	function loadXHR(url) {
	    try {
	    	var dest = '/';
	        const xhr = new XMLHttpRequest();
	        xhr.open("POST", url);
	        xhr.onerror = function() {
	            alert('error');
	        };
	        xhr.onload = function() {
	            if (xhr.status === 200) {
	            	var responseJSON = eval("("+xhr.response+")");
	                if (responseJSON.out === 'success') {
	                    location.href= dest;
	                } else if(responseJSON.fail){
	                    if(responseJSON.fail.error_message) {
	                        alert(responseJSON.fail.error_message)
	                    }
	                    location.href= '/';
	                }          	
	            } else {
	            	alert("error : " + xhr.statusText);
	            }
	        };
	        xhr.send();
	    } catch (err) {
	        alert(err.message);
	    }
	}
    window.addEventListener('load', function() {
    	loadXHR('/account/ssoLogin');
        
    });
</script>