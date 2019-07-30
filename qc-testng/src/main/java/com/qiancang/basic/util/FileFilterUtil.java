package com.qiancang.basic.util;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Description: 文件名过滤器工具类
 */
public class FileFilterUtil implements FilenameFilter {

    public boolean isGif(String file) {
        if (file.toLowerCase().endsWith(".gif")){
            return true;
        }else{
            return false;
        }
    }
    public boolean isJpg(String file){
        if (file.toLowerCase().endsWith(".exe")){
            return true;
        }else{
            return false;
        }
    }

    public boolean isPng(String file){
        if (file.toLowerCase().endsWith(".")){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean accept(File dir, String name) {
        return (isGif(name) || isJpg(name) || isPng(name));
    }


    public static String[] listFileName(File dir,String filter){
        String[] filenames = dir.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                String filename = name.toLowerCase();
                return filename.contains(filter);
            }
        });
    return filenames;
    }

    //根据目录路径查找指定的符合条件的文件
    public static String[] listFileName(String dirPath,String filter){
        File dir = new File(dirPath);
        return listFileName(dir,filter);
    }
    /**
     * @Description  查找指定目录下是否匹配过滤器的所有文件对象
     * @Param  dir 要查找的文件目录
     * @param filter 过滤字段
     * @return java.io.File[] 符合过滤后的文件对象列表
     **/
    public static File[] listFile(File dir,String filter){
        File[] files = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                String filename = name.toLowerCase();
                return filename.contains(filter);
            }
        });
        return files;
    }

    public static File[] listFile(String dirPath,String filter){
        File dir = new File(dirPath);
        return listFile(dir,filter);
    }

    public static void main(String[] args) {
        File directory=new File("C:\\Users\\EDZ\\Downloads");
//        String[] images = directory.list(new FileFilterUtil());
//        System.out.println("size="+images.length);

        String[] exes = FileFilterUtil.listFileName(directory, "exe");
        for (int i = 0; i < exes.length; i++) {
            String ex = exes[i];
            System.out.println(ex);

        }

    }


}


