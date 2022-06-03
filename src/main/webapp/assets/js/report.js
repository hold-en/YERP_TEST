/*
 * 작성자 : 안호진(ahj2897@gmail.com)
 * */

var _report = (function () {
    var commonParameters = {};
    var setCommonParameters = function (key, value) {
        commonParameters[key] = value;
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
    
    var appendForm = function() {
    	var form = document.querySelector('#reportForm');
        form = form || document.createElement('form');
        form.style.display = "none";
        document.body.appendChild(form);
        return form;
    };
    
    var addFormData = function(form, option) {
        if (option.parameter) {
            option.parameter.report = option.report + ".jrf";
            var assignedParameter = assign({}, option.parameter, commonParameters);

            for (var key in assignedParameter) {
                var param = document.createElement('input');
                param.type = "hidden";
                
                param.name = key;
                param.value = assignedParameter[key];
                form.appendChild(param);
            }
        }
    }

    var openForFrame = function (option) {
    	var form = appendForm();
        var frame = option.element.render;
        frame.src = "/page/report";

        var report = option.report;
        if(!report) {
            _util.alert('출력물 경로를 입력해주세요.');
            return;
        }
        form.innerHTML = '';
        form.target = frame.name;
        form.action = '/page/report';

        addFormData(form, option);
        form.submit();
    };

    var openForPopup = function (option) {
    	var form = appendForm();
        
        var popupId = 'reportPopup';
        var report = option.report;
        if(!report) {
            _util.alert('출력물 경로를 입력해주세요.');
            return;
        }
        var popWidth = option.width || 600;  // 창의 가로 길이
        var popHeight = option.height || 430;  // 창의 세로 길이
        var winHeight = document.body.clientHeight;	// 현재창의 높이
        var winWidth = document.body.clientWidth;	// 현재창의 너비
        var winX = window.screenLeft;	// 현재창의 x좌표
        var winY = window.screenTop;	// 현재창의 y좌표
        var popX = winX + (winWidth - popWidth) / 2;
        var popY = winY + (winHeight - popHeight) / 2;
        var full = option.fullscreen;
        //수정자 : 이원학(2020.07.01) - 맨끝 전체화면기능 추가
        var popup = window.open("", popupId, "width=" + popWidth + "px,height=" + popHeight + "px,top=" + popY + ",left=" + popX + ",menubar=no, toolbar=no, location=no, status=no, resizable=yes");
        form.innerHTML = "";
        form.charset = "UTF-8";
        form.method = "POST";
        form.target = popupId;
//        form.action = '/page/report';
        form.action = 'http://172.16.11.48:9090/ubi4/openUbiReport.jsp';
        addFormData(form, option);
        form.submit();

        return popup;
    };

    return {
        openForPopup: openForPopup,
        openForFrame : openForFrame,
        setCommonParameters : setCommonParameters
    };
}());

