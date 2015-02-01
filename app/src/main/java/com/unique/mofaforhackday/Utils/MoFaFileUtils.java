package com.unique.mofaforhackday.Utils;

import android.util.Log;

import com.unique.mofaforhackday.Config;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by ldx on 2015/1/8.
 * utils to handle the Files in sdcard/mofa
 */
public class MoFaFileUtils {
    public static final String TAG = "MoFaFileUtils";
    /**
     * delete the folder with the given parameter
     * all files in it will also be deleted
     * @param folderPath the folder
     */
    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); //删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            myFilePath.delete(); //删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * delete all files in folder
     * @param path indicate the folder
     * @return whether the files be deleted
     */
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (String aTempList : tempList) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + aTempList);
            } else {
                temp = new File(path + File.separator + aTempList);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
//                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                delFolder(path + "/" + aTempList);//再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

    /**
     * delete All the Recommended Files in Config.File
     * @return whether the files be deleted
     */
    public static boolean delRecommendedFiles(){
        File file = new File(Config.SDCARD_MOFA);
        if (!file.exists()){
            Log.i(TAG,"file/folder not found");
            return false;
        }

        if (!file.isDirectory()){
            Log.e(TAG,"not directory error");
            return false;
        }

        File[] fileList = file.listFiles();//will not return directories under given directory
        File[] RecommendedPhotos = filterRecommendedFiles(fileList);
        for (File afile :RecommendedPhotos){
            if (afile.isFile()){
                Log.i(TAG,"File "+ afile.getName()+ " has been deleted");
                afile.delete();
            }
        }
        return true;
    }

    /**
     * filter the given fileList to the list of Recommended Photos
     * @param fileList all files under
     * @return File[] recommended photos
     */
    private static File[] filterRecommendedFiles(File[] fileList){
        ArrayList<File> files = new ArrayList<File>(fileList.length);
        for (File aFile : fileList){
            if (aFile.getName().startsWith("mofa")){
                files.add(aFile);
                Log.i(TAG,"File "+ aFile.getName()+ " has been filtered out");
            }
        }
        File[] result = new File[files.size()];
        return files.toArray(result);
    }
}

