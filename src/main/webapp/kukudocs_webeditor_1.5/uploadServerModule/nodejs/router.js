/**
 * < 필수 선언 사용 >
 * 
 * express : NodeJS express 사용
 * router : express router 사용
 * fs : File을 관리하기 위해 사용
 * formidable : 서버에 전달된 Form 정보를 처리하기 위해 사용
 * path : Server의 Path처리를 위해 사용
 * 
 * Define : Client 에서 Upload할 때 사용하는 form Field의 name 목록 
 */

/* NodeJS express 사용 */
var express = require('express');
var router = express.Router();

/* File을 관리하기 위해 사용 */
var fs = require('fs');

/* 파일을 포함한 Form 정보를 처리하기 위해 사용 */
var formidable = require("formidable");

/* Server의 Path처리를 위해 사용  */
var path = require("path");

/**
 * < 사용자 정의 사용 >
 *
 * UPLOAD_FOLDER_PATH : 파일이 저장될 물리적인 Path (절대 경로를 지정)
 * UPLOADDIR : Upload Folder Name
 *
 * .... etc : 추가로 사용할 Upload Folder Name 을 정의해서 사용.
 */

//FullPath 지정하여 사용 가능
var UPLOAD_FOLDER_PATH = path.resolve(__dirname + '/../public') + '/uploads';
var URL_PATH = "http://192.168.55.93:3001/Upload"; // ex) "http://www.kukudocs/upload"

/* Upload Type별 Define 목록 */
var UPLOAD_IMAGE_TYPE = 'image_type';
var UPLOAD_IMAGE_BASE64_TYPE = 'image_base64_type';
var UPLOAD_VIDEO_TYPE = 'video_type';
var UPLOAD_FILE_TYPE = 'file_type';
var UPLOAD_FLASH_TYPE = 'flash_type';

//Base64Image일 경우 Type과 Data를 분리시키는 Function
var decodeBase64Image = function (dataString) {
    var matches = dataString.match(/^data:([A-Za-z-+\/]+);base64,(.+)$/);
    var response = {};

    if (matches.length !== 3) {
        return new Error('Invalid input string');
    }

    response.type = matches[1];
    response.data = new Buffer(matches[2], 'base64');

    return response;
};

//upload directory 가 없을시 새로 생성 해줌.
fs.exists(UPLOAD_FOLDER_PATH, function(exists){
    if (! exists){
        fs.mkdirSync(UPLOAD_FOLDER_PATH, 0766, function(err){
            if(err){
                console.log(err);
            }else{
                console.log('made upload folder.');
            }
        });
    }
});


/**
 * Upload 처리 Sever Module
 *
 * Method Type : POST
 * URL Name : 사용자 지정
 * 
 * response 구조
 * {
 *     //Error 발생시
 *     isError : true or false [Boolean]
 *     msg : 'Error Message'   [String]
 *     
 *     //Upload 성공시
 *     url : 'Upload 성공시 URL' [String]
 * }
 * 
 */
router.post('/fileUpload', function(req, res) {

    var form = new formidable.IncomingForm();

    form.parse(req);

    var time = new Date().getTime();
    var fileName = null;
    var type = '';
    var imageTypeRegularExpression = /\/(.*?)$/;

    //Field Data로 전송된경우 처리 (Base64처리 기준)
    form.on('field', function(name, field){
        type = name;

        if (type === UPLOAD_IMAGE_BASE64_TYPE) {
            var imageBuffer = decodeBase64Image(field);
            var imageTypeDetected = imageBuffer.type.match(imageTypeRegularExpression);

            fileName = time + '.' + imageTypeDetected[1];
            var path = UPLOAD_FOLDER_PATH + '/' + fileName;

            try {
                fs.writeFile(path, imageBuffer.data, function() {
                    console.log('DEBUG - feed:message: Saved to disk image attached by user:', path);
                });
            } catch(error) {
                console.log('ERROR:', error);
            }
        }
    });

    //File Data로 전송된경우 처리 
    form.on("fileBegin", function (name, file) {
        var filenameSplit = file.name.split('.');

        var ext = filenameSplit[filenameSplit.length - 1];

        if (!ext) {
            var imageTypeDetected = file.type.match(imageTypeRegularExpression);

            if (imageTypeDetected[0] == 'video' && imageTypeDetected[1] == 'x-ms-wmv') {
                ext = 'wmv';
            } else {
                ext = imageTypeDetected[1];
            }
        }

        fileName = time + '.' + ext;

        type = name;

        //TODO : Type별로 분기처리하서 폴더별 저장 싶을 경우에는 아래 소스코드 수정
        if (type === UPLOAD_IMAGE_TYPE
            || type === UPLOAD_VIDEO_TYPE
            || type === UPLOAD_FILE_TYPE
            || type === UPLOAD_FLASH_TYPE
        ) {
            file.path = UPLOAD_FOLDER_PATH + '/' + fileName;
        }
    });

    //Error 발생 대응
    form.on("error", function (err) {
        console.log(err);

        res.end(JSON.stringify({
            isError : true,
            msg : 'Unknown Server Error'
        }));
    });

    form.on("end", function (fields, files) {

        var returnObj = {
            isError : true,
            msg : 'Unknown Server Error'
        };

        var path = '';

        //TODO : Type별로 분기처리하서 URL을 생성하고 싶을 경우에는 아래 소스코드 수정
        if (type === UPLOAD_IMAGE_TYPE
            || type === UPLOAD_VIDEO_TYPE
            || type === UPLOAD_FILE_TYPE
            || type === UPLOAD_FLASH_TYPE
        ) {
            path = URL_PATH + '/' + fileName

        } else if (type === UPLOAD_IMAGE_BASE64_TYPE) {
            path = URL_PATH + '/' + fileName
        }

        if (path) {
            returnObj = {
                url : path
            }
        }

        res.end(JSON.stringify(returnObj));
    });
});

module.exports = router;
