/*
 * 작성자 : 안호진(ahj2897@gmail.com)
 * 작성일 : 2019.11.07
 * */
var _request = (function() {
	var dummy;
	var errorCallback = {};
    var commonParameters = {};
    var setCommonParameters = function (key, value) {
        commonParameters[key] = value;
    };
    var setCommonParameterInObject = function (object) {
        for (var key in commonParameters) {
            object[key] = commonParameters[key];
        }
    };
    var setErrorCallback = function(code, callback) {
    	errorCallback[code] =  callback;
    };
    var defaultNotify = function(res, option) {
        if (res.out) {
            if (option.method && option.method.toLowerCase() == 'get') {
                //조회시
            } else {
                //저장시
                _util.alert(Math.abs(res.out.cnt) + '건 완료.');
            }

        }      
    };
    var assign = function() {
        var assigned = [].reduce.call(arguments, function(prev, item, index, arr) {
            Object.keys(item).forEach(function(key) {
                prev[key] = item[key];
            });
            return prev;
        }, {});
        return assigned;
    };

    var serialize = function(obj) {
        var str = [];
        for (var key in obj)
            if (obj.hasOwnProperty(key)) {
                str.push(encodeURIComponent(key) + "=" + encodeURIComponent(obj[key]));
            }
        return str.join("&");
    };

    var run = function(url, option) {
        if (!url) new Error('parameters are not enough');

        var defaultOption = {
            action: url,
            mode: "asynchronous",
            cache : false,
            mediatype: "application/json; charset=UTF-8",
            requestHeader: {
                type: 'ajax'
            },
            success: function(e) {
            	_request.requestCount--;
                if (option.success && option.success instanceof Function) {
                	var useDefaultNotify = option.defaultNotify == false ? false : true;
                	if(useDefaultNotify) defaultNotify(e.responseJSON, option);
                    option.success(e.responseJSON);
                } else {
                	console.log(e);
                }
            },
            error: function(e) {
            	_request.requestCount--;
            	var responseBody = e.responseBody.substr(5);
            	var responseJSON = JSON.parse(responseBody);
            	if(responseJSON.error) {
            		if(errorCallback[responseJSON.error]) {
            			errorCallback[responseJSON.error](responseJSON);
            		} else {
            			if (option.error && option.error instanceof Function) {
            				option.error(responseJSON.error);
            			} else if(responseJSON.error.indexOf("Exception") != -1) {
        		            var errorArr = responseJSON.error.split('###');
        		            alert(errorArr[errorArr.length-1].match(/(Exception\s*:\s*)[^\r\n]*/))[0].split(":")[1].trim();
        		        } else {
        		            alert(responseJSON.error);
        		        }
        		                        			
            		}                		
            	} else {
            		console.log(e);
            		throw e;	
            	}        	
            }
        };
        var assigned = assign({}, defaultOption);
        var exclude = ['success', 'error'];
        for(var key in option) {
        	if(exclude.indexOf(key) == -1) assigned[key] = option[key];
        }
        //var assigned = assign(defaultOption, option);
        _request.requestCount++;
        $w.ajax(assigned);
    };
    
    var get = function(url, option) {
    	dummy = Math.random();
        var assigned = assign({}, option);
        assigned.data = assigned.data || {};
        assigned.data.normal = assigned.data.normal || {};
        if (Object.keys(commonParameters).length > 0) setCommonParameterInObject(assigned.data.normal);
        assigned.data.normal.dummy = dummy;
        
        assigned.method = "get";
        url += '?' + serialize(assigned.data.normal);
        run(url, assigned);
    };
    var postMsg = function(url, option) {
        var assigned = assign({
        	confirm : true
        }, option);
        assigned.method = "post";
        assigned.data = assigned.data || {};
        assigned.data.normal = assigned.data.normal || {};
        assigned.data.body = assigned.data.body || [];
        if (Object.keys(commonParameters).length > 0) setCommonParameterInObject(assigned.data.normal);
        assigned.requestData = JSON.stringify(assigned.data);
        if(assigned.confirm && assigned.confirm == true) {
        	_util.confirm(assigned.message, function() {
        		run(url, assigned);
        	});
        } else {
        	run(url, assigned);	
        }
    };    
    var post = function(url, option) {
        var assigned = assign({
        	confirm : true
        }, option);
        assigned.method = "post";
        assigned.data = assigned.data || {};
        assigned.data.normal = assigned.data.normal || {};
        assigned.data.body = assigned.data.body || [];
        if (Object.keys(commonParameters).length > 0) setCommonParameterInObject(assigned.data.normal);
        assigned.requestData = JSON.stringify(assigned.data);
        if(assigned.confirm && assigned.confirm == true) {
        	_util.confirm('실행하시겠습니까?', function() {
        		run(url, assigned);
        	});
        } else {
        	run(url, assigned);	
        }
    };
    
    var post1 = function(url, option) {
        var assigned = assign({
        	confirm : true
        }, option);
        assigned.method = "post";
        assigned.data = assigned.data || {};
        assigned.data.normal = assigned.data.normal || {};
        assigned.data.body = assigned.data.body || [];
        if (Object.keys(commonParameters).length > 0) setCommonParameterInObject(assigned.data.normal);
        assigned.requestData = JSON.stringify(assigned.data);
        if(assigned.confirm && assigned.confirm == true) {
        	run(url, assigned);	
        }
    };
    
    var getSync = function(url, option) {
        var assigned = assign({}, option);
        assigned.mode = "synchronous";
        get(url, assigned);
    };
    
    var postSync = function(url, option) {
        var assigned = assign({}, option);
        assigned.mode = "synchronous";
        post(url, assigned);
    };
    
    return {
    	get : get,
    	post : post,
    	postMsg : postMsg,
    	post1 : post1,
    	getSync : getSync,
    	postSync : postSync,    	
    	setCommonParameters : setCommonParameters,
    	setErrorCallback : setErrorCallback
    };
}());


Object.defineProperty(_request, 'requestCount', {
    get: function () {
        return this.__requestCount || 0;
    },
    set: function (count) {
        this.__requestCount = count;
        var overlay = document.querySelector('.overlay');
        if(overlay) {
        	if (this.__requestCount) {
	        	overlay.style.display="block";
	        	//$p.showModal();
	        } else {
	        	overlay.style.display="none";
	        	//$p.hideModal();
	        }
        }
    }
});

