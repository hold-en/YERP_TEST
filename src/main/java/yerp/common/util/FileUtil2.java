package yerp.common.util;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.web.multipart.MultipartFile;

public class FileUtil2 {
    // public static final String UPLOAD_ROOT = "/home/hjahn/Develop/upload";
    //public static final String UPLOAD_ROOT = "/Users/administrator/Desktop/upload";
    //public static final String UPLOAD_ROOT = ConstantUtil.HJ_SajinDir;
    public static final String UPLOAD_ROOT = "P:/group-filesNew/FileData/1/Users"; //local
	//public static final String UPLOAD_ROOT = "Q:/smart-files";  //test
	//public static final String UPLOAD_ROOT = ConstantUtil.HS_SajinDir;
    public static List<String> readLines(MultipartFile file) {
        BufferedReader br = null;
        List<String> result = new ArrayList<String>();
        try {
            /*
            String content = new String(file.getBytes(), "UTF-8");
            String[] arr = content.split("\n");
            for(String line : arr) {
                System.out.println(line);
            }*/
            String line = null;
            InputStream is = file.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                result.add(line);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static File convert(MultipartFile file) {
        File convFile = null;
        FileOutputStream fos = null;

        try {
            convFile = new File(UUID.randomUUID().toString() + '.' + FileUtil2.getFileExt(file));
            convFile.createNewFile();
            fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) try {
                fos.close();
            } catch (Exception e) {
            }
        }
        return convFile;
    }

    public static JSONArray search(String dirStr) {
        JSONArray resultList = new JSONArray();
        JSONObject fileInfo = null;
        File dir = new File(UPLOAD_ROOT + File.separator + dirStr);
        if (dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles();
            for (File file : files) {
                fileInfo = new JSONObject();
                fileInfo.put("DIR", dirStr);
                fileInfo.put("NAME", file.getName());
                fileInfo.put("SIZE", readableFileSize(file.length()));

                resultList.add(fileInfo);
            }
        }
        return resultList;
    }

    public static File searchOne(String dirStr, String name) {
        if (dirStr.equals("") || name.equals(""))
            return null;

        File searchFile = null;
        File dir = new File(UPLOAD_ROOT + File.separator + dirStr);
        if (dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles();
            for (File file : files) {
                if (file.getName().equals(name))
                    searchFile = file;
            }
        }

        return searchFile;
    }

    public static boolean deleteDir(String dirStr) {
        if (dirStr.equals("")) return false;
        dirStr = dirStr.replace("../", "").replace("./", "");

        File dir = new File(UPLOAD_ROOT + File.separator + dirStr);
        if (!dir.exists()) {
            return false;
        }
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                deleteDir(file.getName());
            } else {
                file.delete();
            }
        }
        return dir.delete();
    }

    public static JSONObject delete(String dirStr, String name) {
        JSONObject result = new JSONObject();
        dirStr = dirStr == null ? "" : dirStr;
        dirStr = dirStr.replace("../", "").replace("./", "");
        name = name == null ? "" : name;
        name = name.replace("../", "").replace("./", "");

        if (dirStr.equals("") || name.equals("")) return result;
        File dir = new File(UPLOAD_ROOT + File.separator + dirStr);
        System.out.println("dir : " + dir);
        if (dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles();
            for (File file : files) {
                if (file.getName().equals(name)) {
                    result.put("FILE_LAST_PATH", file.getParentFile().getName());
                    result.put("FILE_NAME", file.getName());
                    result.put("isDeleted", file.delete());
                    break;
                }
            }
        }

        return result;
    }

    public static JSONArray store(FileOption option, MultipartFile[] files) {
        JSONArray resultList = new JSONArray();
        JSONObject result = null;
        String uploadName = null;
        String[] uploadableExtension = option.getUploadableExtension();
        String filePath = option.getFilePath();
       
        for (MultipartFile file : files) {
            result = new JSONObject();
            String ext = getFileExt(file);

            uploadName = file.getOriginalFilename().toLowerCase();
            if(uploadName.indexOf(File.separator) != -1) uploadName = uploadName.split("\\"+File.separator)[uploadName.split("\\"+File.separator).length-1];
            boolean isManipulated = uploadName.indexOf(0x0000) != -1;
            boolean isNotUploadable = false;
            if (uploadableExtension.length > 0) {
                isNotUploadable = !new ArrayList<>(Arrays.asList(uploadableExtension)).contains(ext);
            }
            try {
                // UUID.randomUUID().toString()
                if (isNotUploadable || isManipulated) {
                    result.put("error", uploadName + " is non-uploadable file");
                } else {
                    File dir = new File(UPLOAD_ROOT + File.separator + option.getFilePath());
                    String uploadedName = uploadName;
                    /*
                    int num = 0;
                    while (true) {
                        if (!new File(dir.getAbsolutePath() + File.separator + uploadedName).exists())
                            break;
                        uploadedName = uploadName.substring(0, uploadName.lastIndexOf(".")) + "_" + (++num) + "."
                                + ext;
                    }*/
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }

                    File trans = new File(dir.getAbsolutePath() + File.separator + uploadedName);
                    file.transferTo(trans);
                    result.put("FILE_PATH", option.getFilePath());
                    result.put("FILE_LAST_PATH", option.getFilePath().split("/")[option.getFilePath().split("/").length - 1]);
                    result.put("ORGN_NAME", uploadName);
                    result.put("SAVE_NAME", uploadedName);
                    result.put("FILE_SIZE", file.getSize());
                    result.put("FILE_EXTS", ext);
                    option.setUploadedCount(option.getUploadedCount() + 1);
                }
            } catch (Exception e) {
                e.printStackTrace();
                result.put("error", uploadName + " error : " + e.getMessage());
            } finally {
                resultList.add(result);
            }
        }
        return resultList;
    }

    public static String getFileExt(MultipartFile file) {
        String ext = "";
        if (file != null) {
            String fileName = file.getOriginalFilename();
            if (fileName.indexOf(".") != -1)
                ext = fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        return ext;
    }

    public static String readableFileSize(long size) {
        if (size <= 0)
            return "0";
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }
}
