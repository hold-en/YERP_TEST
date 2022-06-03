/*
 * 작성자 : 안호진
 * */
var _util = (function () {
	var openPopup = function(option) {
		var hasAllOption = option.url &&  option.id && option.title;
		if(!hasAllOption) return;
		
		var dataObject = { 
			"type" : "json", // 데이터 타입. "json" 권장  
			"name" : "parameter",  // 데이터를 가져오기 위한 key 값. popup에서 $p.getParameter("parameter")로 깨내올 수 있음. 
			"data" : option.param || {} // 실제 전달할 데이터 
		};
		
		var controlObject = {
			"minimize" : false,
			"maximize" : false,
			"close" : (option.closeBtn ? option.closeBtn : false)
		};
		
		var options = {
			id : option.id,
			type : "iframePopup",
			top : (option.top ? option.top : getScrollTop()),
			left : (option.left ? option.left : 50),
			width: (option.width ? option.width : 300) + "px",
			height: (option.height ? option.height : 250) + "px",
			popupName : option.title,
			modal : true,
			resizable : true,
			status : true,
			menubar : false,
			scrollbars : false,
			title : (option.title ? true : false),
			dataObject : dataObject,
			useControl : (option.useControl ? option.useControl : false),
			controls : controlObject
//			,
//			image : option.image,
//			hagbeon : option.hagbeon
		};
		$p.openPopup(option.url, options);
	};
	var getPopupParameter = function() {
		return $p.getParameter("parameter");
	};
	var getParameter = function (str) {
		var strHref = location.href;
		var aryHref = strHref.split('?');
		if (!aryHref[1]) {
			aryHref[1] = '';
		}
		var aryHrefParam = aryHref[1].split('&');
		var aryTemp = {};
		
		if (aryHref[1]) {
			var intLength = aryHrefParam.length;
			for (var i = 0; i < intLength; i++) {
				var temp = aryHrefParam[i].split('=');
				aryTemp[temp[0]] = decodeURIComponent(temp[1]);
			}
		}
		return !str ? aryTemp : aryTemp[str];
	};
	
	var getMenuInfo = function() {
		return $p.getParameter("menuInfo");
	};
	
	var getScrollTop = function (){
		if(typeof pageYOffset!= 'undefined'){
			//most browsers except IE before #9
			return pageYOffset;
		} else {
			var B= document.body; //IE 'quirks'
			var D= document.documentElement; //IE with doctype
			D= (D.clientHeight)? D: B;
			return D.scrollTop;
		}
	};
	
	var openNormalPopup = function (option) {
		var popWidth = option.width || 600;  // 창의 가로 길이
		var popHeight = option.height || 430;  // 창의 세로 길이
		var winHeight = document.body.clientHeight;	// 현재창의 높이
		var winWidth = document.body.clientWidth;	// 현재창의 너비
		var winX = window.screenLeft;	// 현재창의 x좌표
		var winY = window.screenTop;	// 현재창의 y좌표
		
		var popX = winX + (winWidth - popWidth) / 2;
		var popY = winY + (winHeight - popHeight) / 2;
		var pathInfo = option.path.split("?");
		var parameter;
		
		option.parameter = option.parameter || {};
		option.parameter.popupId = option.id;
		option.parameter.popupTitle = option.title || '';
		
		if (pathInfo[1]) {
			parameter = (option.parameter ? "&" + serialize(option.parameter) : "");
		} else {
			parameter = (option.parameter ? "?" + serialize(option.parameter) : "");
		}
		var popup = window.open((option.path) + parameter, option.id, "width=" + popWidth + "px,height=" + popHeight + "px,top=" + popY + ",left=" + popX + ",menubar=no, toolbar=no, location=no, status=no, scrollbars=1");
		popup.focus();
		return popup;
	};
	
	var openLoginPopup = function() {
		var url = window.location.href;
		var arr = url.split("/");
		var result = arr[0] + "//" + arr[2];
		openNormalPopup({
			id : 'ssoLogin',
			//    path : 'https://login.shingu.ac.kr/index?ReturnURL='+result+'/page/ssoLoginPopup',
			path : 'http://localhost:8080/page/nonSsoLogin',
			width : 650,
			height : 750
		});
	};
	
	var jsonArrayGroupBy = function (array, key) {
		var result = array.reduce(function (p, v, i, arr) {
			(p[v[key]] = p[v[key]] || []).push(v);
			return p;
		}, {});
		return result;
	};
	
	var getSessionInfo = function () {
		var info;
		_request.getSync("/account/sessionInfo", {
			success : function(res) {
				info = res;
			}
		});
		return info;
	};
	
	var serialize = function (obj) {
		var str = [];
		for (var key in obj)
			if (obj.hasOwnProperty(key)) {
				str.push(encodeURIComponent(key) + "=" + encodeURIComponent(obj[key]));
			}
		return str.join("&");
	};
	
	var closePopup = function () {
		open(location, '_self').close();
	};
	
	var addCallbackListener = function (mapping) {
		window.addEventListener('message', function (res) {
			if (mapping) {
				var div = res.data.div;
				var item = res.data.item;
				var callback = mapping[div];
				if (callback) {
					callback(item);
				}
			}
		});
	};
	var runCallbackForPopup = function (div, data) {
		if (parent) {
			parent.postMessage({div: div, item: data}, '*');
			$p.closePopup();
		}
	};
	
	var addEnterEventUnderElement = function(element, callback) {
		element.bind('keydown', function(e) {
			if(e.keyCode == 13) {
				e.preventDefault();
				callback();
			}
		});
	};
	
	var getCommonCode = function(optionArr) {
		var codeMap = {};
		optionArr.forEach(function(arr) {
			if(arr[1]) {
				codeMap[arr[1]] = []
			}
		});
		_request.postSync('/code/getCommonCode', {
			defaultNotify : false,
			confirm : false,
			data : {
				normal: {param : optionArr}	
			},
			success : function(res) {
				res.out.forEach(function(arr, index) {
					codeMap[Object.keys(codeMap)[index]] = arr;
				})
			}
		});
		return codeMap;
	};
	
	var setDataForSelectbox = function(option) {
		var label = option.label;
		var value = option.value;
		var data = option.data;
		var elem = option.elem;
		var allOption = option.allOption || "전체";
		var hasBlank = elem.getItemArray().some(function(item) {
			return item.value == "";
		});
		
		elem.removeAll();
		
		if(hasBlank) elem.addItem("", allOption);
		
		option.data.forEach(function(item) {
			elem.addItem(item[value], item[label]);
		});
		elem.setSelectedIndex(0);
	};
	var openUploadPopup = function(option) {
		_util.openPopup({
			id : option? option.id : 'uploadPopup',
			title : '업로드',
			url : '/GATE/COMMON/uploadPopup.xml',
			width : 500,
			height : 160,
			param : {
				dir : option? option.dir : ''
			}
		});
	};
	
	var openUploadPopup2 = function(option) {
		_util.openPopup({
			id : option? option.id : 'uploadPopup2',
			title : '업로드',
			url : '/GATE/COMMON/uploadPopup.xml',
			width : 500,
			height : 160,
			param : {
				dir : option? option.dir : ''
			}
		});
	};
	
	var openUploadPopupSajin = function(option) {
		_util.openPopup({
			id : option? option.id : 'uploadPopupSajin',
			title : '업로드',
			url : '/GATE/COMMON/uploadPopupSajin.xml',
			width : 670,
			height : 530,
			param : {
				dir : option? option.dir : '',
				image : option? option.image : '',
				hagbeon : option? option.hagbeon : ''
			},
			
		});
	};
	
	var openUploadPopupSajin2 = function(option) {
		_util.openPopup({
			id : option? option.id : 'uploadPopupSajin2',
			title : '업로드',
			url : '/GATE/COMMON/uploadPopupSajin2.xml',
			width : 585,
			height : 500,
			param : {
				dir : option? option.dir : '',
				image : option? option.image : '',
				Yy : option? option.Yy : ''
			},
			
		});
	};
	
	var openUploadPopupSajin3 = function(option) {
		_util.openPopup({
			id : option? option.id : 'uploadPopupSajin3',
			title : '사진 업로드',
			url : '/GATE/COMMON/uploadPopupSajin3.xml',
			width : 670,
			height : 530,
			param : {
				UserID : option? option.UserID : '',
				oldImg : option? option.oldImg : ''
			},
			
		});
	};
	
	
	var findAddress = function (id) {
		openNormalPopup({
			id: id,
			title : '주소검색',
			path: '/GATE/COMMON/findAddress.jsp',
			width: 600,
			height: 450
		});
	};
	
	var download = function(dir, name) {
		$p.download('/repository/download?dir='+dir+'&name='+encodeURIComponent(name));
	};
	
	var openSearchStudent = function(popupID) {
		_util.openPopup({
			id : popupID,
			title : '학생검색',
			width : 700,
			height : 500,
			url : '/udc/searchStudentPopup.xml',
		});
	};
	
	var setOverlayForRequest = function() {
		var elem = document.createElement('div');
		elem.innerHTML = '<div class="overlay">'+
		  '<img src="/assets/images/contents/process.gif">' +
		  '<div class="text">처리중입니다.</div>'+
		'</div>';
		var overlay = elem.firstChild;
		document.body.appendChild(overlay);
		return overlay;
	};
	
	var alert = function(message, type) {
		window.alert(message);
		/*
		Swal.fire({
			  title: 'Error!',
			  text: message,
			  icon: 'error',
			  confirmButtonText: 'Cool'
			});*/
//		Swal.fire({
//			  //position: 'top-end',
//			  icon: type || 'success',
//			  title: message,
//			  showConfirmButton: true,
//			  timer: 2500
//			})
	};
	
	var alert3 = function(message, callback, type) {
		
		Swal.fire({
			  title: message || '',
			  text: "",
			  icon: type || 'success',
			  showCancelButton: false,
			 // cancelButtonText : '취소',
			  confirmButtonColor: '#3085d6',
			 //cancelButtonColor: '#d33',
			  confirmButtonText: '확인'
			}).then(function(result) {
			  if (result.value && callback) {
				  callback(result.value);
			  }  else {
				  callback(false);				  
			  }
		});		
	};
	
	var confirm = function(message, callback) {
		var result = window.confirm(message);
		if(result){
			callback();
		}
//		Swal.fire({
//			  title: message || '실행하시겠습니까?',
//			  text: "실행 시 되돌릴 수 없습니다.",
//			  icon: 'warning',
//			  showCancelButton: true,
//			  cancelButtonText : '취소',
//			  confirmButtonColor: '#3085d6',
//			  cancelButtonColor: '#d33',
//			  confirmButtonText: '확인'
//			}).then(function(result) {
//			  if (result.value && callback) {
//				  callback(result.value);
//			  }
//		});		
	};
	
	var confirm1 = function(message, callback) {
		var result = window.confirm(message);
		if(result){
			callback();
		}
//		Swal.fire({
//			  title: message || '',
//			  text: "",
//			  icon: 'warning',
//			  showCancelButton: true,
//			  cancelButtonText : '취소',
//			  confirmButtonColor: '#3085d6',
//			  cancelButtonColor: '#d33',
//			  confirmButtonText: '확인'
//			}).then(function(result) {
//			  if (result.value && callback) {
//				  callback(result.value);
//			  }  else {
//				  callback(false);				  
//			  }
//		});		
	};
	

	var confirm2 = function(message, callback) {
		Swal.fire({
			  title: message || '실행하시겠습니까?',
			  text: '',
			  icon: 'warning',
			  showCancelButton: true,
			  cancelButtonText : '취소',
			  confirmButtonColor: '#3085d6',
			  cancelButtonColor: '#d33',
			  confirmButtonText: '확인'
			}).then(function(result) {
			  if (result.value && callback) {
				  callback();
			  }
			});
	};	
	
	var returnMessage = function(res) {
		if(res.out.procResult && res.out.procResult.length > 0) {
			_util.alert(res.out.procResult[0].RESULT_MSG || '처리되었습니다.');
		}
	};
	
	var iljungPopup = function (id) {
		_util.openPopup({
			id: id,
			title : '일괄복사',
			url: '/GATE/COMMON/iljungPopup.xml',
			width: 570,
			height: 270
		});
	};
	
	var searchEmCodePopup = function (id) {
		_util.openPopup({
			id: id,
			title : '사원검색',
			url: '/GATE/COMMON/searchEmCodePopup.xml',
			width: 700,
			height: 500
		});
	};
	
	var GyosuPopup = function (id) {
		_util.openPopup({
			id: id,
			title : '교수검색',
			url: '/GATE/COMMON/GyosuPopup.xml',
			width: 700,
			height: 500
		});
	};
	var sangdamPopup = function (id) {
		_util.openPopup({
			id: id,
			title : '상담자검색',
			url: '/GATE/COMMON/sangdamPopup.xml',
			width: 700,
			height: 500
		});
	};
	var jangbiPopup = function (id) {
		_util.openPopup({
			id: id,
			title : '물품검색',
			url: '/GATE/COMMON/jangbiPopup.xml',
			width: 700,
			height: 500
		});
	};
	
	var runCallerForPopup = function (div, data) {
		if (parent) {
			parent.postMessage({div: div, item: data}, '*');
		}
	};
	
	var programPermission = function(menuInfo) {
		if(!menuInfo.permission || menuInfo.permission.toLowerCase() == "n") {
			document.body.innerHTML = "권한이 없습니다.";
			result  = menuInfo.permission;
		} 
		result  = menuInfo.permission;
		return result;
	};
	
	var addTabMenu = function(mainCd, sysCd, pgId, proUrl, parameter){
		
		var pgId = pgId;
		var proUrl = proUrl;
		var mainCd = mainCd;
		var sysCd = sysCd;
		var appendedEobmuNmTmp = [];
		
		for( var idx = parent.tabControl.getTabCount()-1 ; idx >= 0 ; idx--){
			if (parent.tabControl.getTabID(idx) == pgId) {
				parent.tabControl.deleteTab( idx );
			}
		}
		
		_request.get("/menu/getLeftMenuList", {
			data : {
				normal : {
					MAIN_CD : mainCd,
					SYSTEM_CD : sysCd
				}
			},
			success : function(data) {	
				//  parent.tempMenuList.removeAll();
				appendedEobmuNmTmp.length = 0;
				var group = _util.groupBy(data.out, 'SubsystemNm');
				for(var key in group) {
					var insertedIndex = parent.tempMenuList.insertRow();
					parent.tempMenuList.setCellData(insertedIndex, 'ProgramNm', group[key][0].SubsystemNm);
					parent.tempMenuList.setCellData(insertedIndex, 'depth', 1); 
					
					group[key].forEach(function(menu, index) {
						if(appendedEobmuNmTmp.indexOf(menu.SubsystemCd + menu.EobmuNm) == -1) {
							var insertedIndex = parent.tempMenuList.insertRow();
							parent.tempMenuList.setCellData(insertedIndex, 'ProgramNm', menu.EobmuNm);
							parent.tempMenuList.setCellData(insertedIndex, 'depth', 2);
							appendedEobmuNmTmp.push(menu.SubsystemCd + menu.EobmuNm);
						}
						
						if(menu.Program.indexOf("HIDDEN") == -1) {
							var insertedIndex = parent.tempMenuList.insertRow();
							var name = menu.ProgramNm.match(/[^a-zA-Z가-힣]/) && menu.ProgramNm.match(/[^a-zA-Z가-힣]/).index == 0 ? menu.ProgramNm.substr(1) : menu.ProgramNm;
							parent.tempMenuList.setCellData(insertedIndex, 'Program', menu.Program);
							parent.tempMenuList.setCellData(insertedIndex, 'ProgramNm', name);
							parent.tempMenuList.setCellData(insertedIndex, 'ProgramId', menu.ProgramId);
							parent.tempMenuList.setCellData(insertedIndex, 'MainNm', menu.MainNm);
							parent.tempMenuList.setCellData(insertedIndex, 'MainCd', menu.MainCd);
							parent.tempMenuList.setCellData(insertedIndex, 'SystemNm', menu.SystemNm);
							parent.tempMenuList.setCellData(insertedIndex, 'SystemCd', menu.SystemCd);
							parent.tempMenuList.setCellData(insertedIndex, 'SubsystemNm', menu.SubsystemNm);
							parent.tempMenuList.setCellData(insertedIndex, 'EobmuNm', menu.EobmuNm);
							parent.tempMenuList.setCellData(insertedIndex, 'depth', 3);
							parent.tempMenuList.setCellData(insertedIndex, 'seq', menu.seq);
						}
					
					});
					
				}
				
			var pgIdx = parent.tempMenuList.getMatchedIndex("ProgramId", pgId);
			
			
			var programInfo = parent.tempMenuList.getRowJSON(pgIdx);
			
			var allData = Object.assign(programInfo, parameter)
			
			parent.scwin.addTab(parent.tempMenuList.getCellData(pgIdx,"ProgramNm"), pgId, proUrl, allData);
			
			parent.tempMenuList.removeAll();
			
			}
		});
	}
	
	/**
	 * 브라우저 Back, Forward 발생 시 onPopState 이벤트를 등록한다.
	 *
	 * @date 2022.05.09
	 * @author 
	 * @example _util.setHistoryBackEvent();
	 */
	var setHistoryBackEvent = function() {
		if (window.addEventListener) {
			window.addEventListener("popstate", _util.changePageState());
		} else {
			window.attachEvent("popstate", _util.changePageState());
		}
	};
	
	/**
	 * 브라우저 Back, Forward 발생 시 onPopState 이벤트 처리를 수행한다.
	 *
	 * @date 2022.05.09
	 * @author 
	 * @example _util.changePageState();
	 */
	var changePageState = function() {
		if(_valid.required(history.state) && _valid.required(history.state.data) && _valid.required(history.state.data.menuInfo)){
//			var options = {};
//			options.isHistory = false;
			var data = history.state.data;
//			$p.top().wf_side.getWindow().tw_menu.findNodeByValue(data.menuInfo.menuCode, true);
			$p.top().tabc_layout.addTab(
				data.menuInfo.menuCode,
				{
					label : data.menuInfo.menuNm,
					openAction : "select", // exist 는 기존 탭을 갱신, new 는 항상 새로, select는 동일 id 가 존재하면 선택
					closable : "true"
				},
				{
					src : data.menuInfo.src,	//탭콘테이너의 url은 treeview의 value에서 가져온다.
					frameMode : "wframe",
					scope : true,
					alwaysDraw : "true"
				}
			);
			_util.pushState(data);
//			gcm.win.openMenu($p.top().$p, data.menuInfo.menuNm, data.menuInfo.src, data.menuInfo.menuCode, data.param, options);
		}
	};
	
	/**
	 * history.pushState API를 호출해서 브라우저에서 History 상태를 기록한다.
	 *
	 * @date 2022.05.19
	 * @author 
	 * @example _util.pushState(option.dataObject.data);
	 */
	var pushState = function(data) {
		if (data.menuInfo.menuCode === "MAIN") {
			history.pushState({ "data" : data }, data.menuInfo.menuNm, "");
		} else {
			/* 주소창 관리 */
			history.pushState({ "data" : data }, data.menuInfo.menuNm, "?menuCd=" + data.menuInfo.menuCode);
		}
	};
	
	return {
		jangbiPopup : jangbiPopup,
		sangdamPopup : sangdamPopup,
		GyosuPopup : GyosuPopup,
		searchEmCodePopup : searchEmCodePopup,
		iljungPopup : iljungPopup,
		returnMessage : returnMessage,
		alert : alert,
		alert3 : alert3,
		confirm : confirm,
		confirm1 : confirm1, 
		confirm2 : confirm2,
		setOverlayForRequest : setOverlayForRequest,
		openSearchStudent : openSearchStudent,
		getScrollTop : getScrollTop,
		findAddress : findAddress,
		download : download,
		openUploadPopup : openUploadPopup,
		openUploadPopup2 : openUploadPopup2,
		openUploadPopupSajin : openUploadPopupSajin,
		openUploadPopupSajin2 : openUploadPopupSajin2,
		openUploadPopupSajin3 : openUploadPopupSajin3,
		groupBy : jsonArrayGroupBy,
		getSessionInfo : getSessionInfo,
		openPopup : openPopup,
		getPopupParameter : getPopupParameter,
		getMenuInfo : getMenuInfo,
		openNormalPopup : openNormalPopup,
		openLoginPopup : openLoginPopup,
		closePopup : closePopup,
		addCallbackListener : addCallbackListener,
		runCallbackForPopup : runCallbackForPopup,
		addEnterEventUnderElement : addEnterEventUnderElement,
		getCommonCode : getCommonCode,
		serialize : serialize,
		getParameter : getParameter,
		setDataForSelectbox : setDataForSelectbox,
		runCallerForPopup : runCallerForPopup,
		programPermission : programPermission,
		addTabMenu : addTabMenu,
		setHistoryBackEvent : setHistoryBackEvent,
		changePageState : changePageState,
		pushState : pushState
	};
}());

