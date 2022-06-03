/*
 * 작성자 : 안호진(ahj2897@gmail.com)
 * */
var _valid = (function () {
	var validation = function(dataList, option) {
	    var result = {};
		var msgMap = {};
	    msgMap[required] = ' 항목은 필수 입력 항목입니다.';
	    msgMap[number] = ' 항목은 숫자만 입력 가능합니다.';
	    
		var validResult = dataList.getModifiedIndex().some(function(index) {
			var item = dataList.getRowJSON(index);
			for(var key in item) {
				if(option.hasOwnProperty(key)) {
					var validator = option[key];
					if(!validator(item[key], item)) {
						if(msgMap[validator]) _util.alert(dataList.getColumnName(key) + msgMap[validator]);
						result.row = index;
						result.key = key;
						break;
					}
				}
			}
			return Object.keys(result).length > 0? result : false;
		});
		return Object.keys(result).length > 0? result : undefined;;
	};
	
	var required = function(value) {
		value = String(value);
        return !(typeof value == "undefined" || value == null ||
        		value == undefined || value == "null" ||
        		value == "undefined" || value == "");
	};
	
    var number = function (value) {
        return (value != "" && !isNaN(value));
    };
   
    return {
    	validation : validation,
    	required : required,
    	number : number
    };
}());

