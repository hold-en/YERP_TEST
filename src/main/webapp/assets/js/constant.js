var _constant = (function () {
	
    var HS_SajinURL = '/HagSa/Sajin';
    var CommunicationURL = '/ProgramTestFile/CORE';
    var HS_SajinURLFull = "http://testdemo.co.kr/smart-files/HagSa/Sajin";
    var CommonExcelURL = "/ReSMARTSetup/forms";
    var ChaeyongGonggoURL = '/Hagsa/ChaeyongGonggo';
    var ePortfolio2URL = '/Hagsa/ePortfolio2';
    var GieobURL = '/HagSa/Gieob';
    var OnlineBoghagURL = '/HagSa/OnlineBoghag';
    var IpSiDownLoad = '/IpSi/Download';
    var ChaeyongGonggoURL = '/HagSa/ChaeyongGonggo';
    
    var getePortfolio2URL = function(){
    	return ePortfolio2URL;
    }

    var getIpSiDownLoadURL = function(){
    	return IpSiDownLoad;
    }
    	
 	var getHS_SajinURL = function () { 
 		return HS_SajinURL;
 	};
 	
 	var getHS_SajinURLFull = function(){
 		return HS_SajinURLFull;
 	}
 	
 	var getCommunicationURL = function () { 
 		return CommunicationURL;
 	};
 	
 	var getCommonExcelURL = function () { 
 		return CommonExcelURL;
 	};
 	
 	var getChaeyongGonggoURL = function () { 
 		return ChaeyongGonggoURL;
 	};

 	var getGieobURL = function () { 
 		return GieobURL;
 	};
 	var getOnlineBoghagURL = function () { 
 		return OnlineBoghagURL;
 	};
 	var getChaeyongGonggoURL = function () { 
 		return ChaeyongGonggoURL;
 	};
 	
    return {
        getHS_SajinURL: getHS_SajinURL
        , getHS_SajinURLFull : getHS_SajinURLFull
        , getCommunicationURL : getCommunicationURL 
        , getCommonExcelURL : getCommonExcelURL
        , getChaeyongGonggoURL : getChaeyongGonggoURL
        , getePortfolio2URL : getePortfolio2URL
        , getGieobURL : getGieobURL
        , getOnlineBoghagURL : getOnlineBoghagURL
        , getIpSiDownLoadURL : getIpSiDownLoadURL
        , getChaeyongGonggoURL : getChaeyongGonggoURL
    };    
    
}());    