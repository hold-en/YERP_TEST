<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="java.io.File"%>
<%@ page import="java.io.FileOutputStream"%>
<%@ page import="java.io.IOException"%>

<%-- https://commons.apache.org/index.html에서 fileupload(1.3.3) / io(2.5) / Codec(1.10) 다운로드 및 library 등록 필요 --%>
<%@ page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>
<%@ page import="org.apache.commons.fileupload.disk.DiskFileItemFactory"%>
<%@ page import="org.apache.commons.fileupload.FileItem"%>
<%@ page import="org.apache.commons.codec.binary.Base64"%>

<%@ page trimDirectiveWhitespaces="true" %>
<%
    /*
        UPLOAD_FOLDER_PATH : 파일이 저장될 물리적인 Path (절대 경로를 지정)
        URL_PATH : 외부에서 URL로 접근하는 Upload의 Path (URL 주소를 지정
     */

    // Window Path Ex) "C:/workspace/upload/image"
    // Linux & Mac etc Ex) "/home/upload/image"
    String UPLOAD_FOLDER_PATH = "/Users/jaime/Development/git/jspProject/out/artifacts/jspProject_war_exploded/Upload"; // ex) "C:/workspace/upload/image"
    String URL_PATH = "http://192.168.1.125:8080/Upload"; // ex) "http://www.kukudocs/upload"

    // Client에서 전달되는 Form Data의 Name들 정의
    String UPLOAD_IMAGE_TYPE = "image_type";
    String UPLOAD_IMAGE_BASE64_TYPE = "image_base64_type";
    String UPLOAD_VIDEO_TYPE = "video_type";
    String UPLOAD_FILE_TYPE = "file_type";
    String UPLOAD_FLASH_TYPE = "flash_type";

    boolean isError = false;
    String msg = "";
    String result = "";

    //저장 될 파일의 경로 (절대 경로만 사용가능)
    boolean isMultipart = ServletFileUpload.isMultipartContent(request);

    // Multipart/Form-Data 로 전송 되었을 경우 수행
    if (isMultipart) {
        File directory = new File(UPLOAD_FOLDER_PATH);

        if (!directory.exists()) {
            directory.mkdirs();
        }

        DiskFileItemFactory factory = new DiskFileItemFactory();

        //메모리에 저장 될 파일의 크기 (1024 * 1000 * 메가수) = 10 MB
        factory.setSizeThreshold(1024 * 1000 * 10);
        factory.setRepository(directory);

        ServletFileUpload upload = new ServletFileUpload(factory);

        //업로드 할 파일의 최대의 크기 (1024 * 1000 * 메가수) = 10 MB
        upload.setSizeMax(1024 * 1000 * 10);

        //실제 업로드 부분(파일 생성)
        List<FileItem> items = upload.parseRequest(request);

        Iterator iter = items.iterator();

        while(iter.hasNext()){
            FileItem fileItem = (FileItem) iter.next();

            String type = fileItem.getFieldName();

            if(fileItem.isFormField()){
                //일반 Field 값 처리 설정

                if (type.equals(UPLOAD_IMAGE_BASE64_TYPE)) {
                    try {
                        String completeImageData = fileItem.getString();
                        String imageDataBytes = completeImageData.substring(completeImageData.indexOf(",")+1);

                        long millisecond = (new Date()).getTime();
                        Random r = new Random();
                        int randomValue = r.nextInt(10000);

                        String fileName = millisecond + randomValue + ".png";
                        FileOutputStream fos = new FileOutputStream(UPLOAD_FOLDER_PATH + "/" + fileName); //change path of image according to you
                        byte[] byteArray = Base64.decodeBase64(imageDataBytes);
                        fos.write(byteArray);
                        fos.flush();
                        fos.close();

                        msg = URL_PATH + "/" + fileName;
                    } catch (Exception e) {
                        isError = true;
                        msg = "Base64 String to Image Convert Error";
                    }
                }
            } else {
                //File Type일 경우 하단 Logic 진행
                if(fileItem.getSize() > 0){

                    //TODO : Type과 FieldName 등을 이용해서 Upload 타입을 구분해려면 하단에 소스코드를 변경하세요.
                    String fieldName = fileItem.getFieldName();

                    if (fieldName.equals(UPLOAD_IMAGE_TYPE)
                            || fieldName.equals(UPLOAD_VIDEO_TYPE)
                            || fieldName.equals(UPLOAD_FILE_TYPE)
                            || fieldName.equals(UPLOAD_FLASH_TYPE)) {

                        //File Information
                        String fileName = fileItem.getName();
                        String contentType = fileItem.getContentType();
                        boolean isInMemory = fileItem.isInMemory();
                        long sizeInBytes = fileItem.getSize();

                        try{
                            //File Rename
                            String[] fileNameSplit = fileName.split("\\.");
                            String fileExtension = fileNameSplit[fileNameSplit.length - 1];

                            long millisecond = (new Date()).getTime();
                            Random r = new Random();
                            int randomValue = r.nextInt(10000);

                            fileName = millisecond + randomValue + "." + fileExtension;

                            //File Save
                            File uploadedFile = new File(UPLOAD_FOLDER_PATH, fileName);
                            fileItem.write(uploadedFile);

                            //Temp File Remove
                            fileItem.delete();

                            //URL Create
                            msg = URL_PATH + "/" + fileName;

                        } catch(IOException ex) {
                            isError = true;
                            msg = "Server Upload Pasing Error";

                        } catch(Exception ex) {
                            isError = true;
                            msg = "Unknown Error";
                        }
                    }
                }
            }
        }
    } else {
        isError = true;
        msg = "인코딩 타입이 multipart/form-data 가 아닙니다.";
    }

    if (msg.equals("")) {
        isError = true;
        msg = "Empty Data Upload";
    }

    if (isError) {
        result = "{ \"isError\" : true, \"msg\" : \"" + msg + "\" }";

    } else {
        result = "{ \"url\" : \"" + msg + "\" }";
    }

    response.getWriter().print(result);
%>