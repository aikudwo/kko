package com.aikudwo.ccy.reflcet;

import java.io.*;

/**
 * @author wls
 * @date 2019-06-04 20:07
 */
public class MyClassLoader extends ClassLoader {
    private String path;
    private  String classLoaderName;
    public MyClassLoader(String path,String classLoaderName){
        this.classLoaderName = classLoaderName;
        this.path = path;
    }

    //用于寻找类文件
    @Override
    protected Class findClass(String name) throws ClassNotFoundException {
        byte[] b = loadClassData(name);
        return defineClass(name,b,0,b.length);
    }

    //加载类文件
    private byte[] loadClassData(String name) {
        name = path + name + ".Class";
        InputStream in = null;
        ByteArrayOutputStream out = null;
        try {
             in = new FileInputStream(new File(name));
             out = new ByteArrayOutputStream();
             int i = 0 ;
             while ((i = in.read())!= -1){
                 out.write(i);
             }
        }catch (Exception e){
                e.printStackTrace();
        }finally {
            try {
                out.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return out.toByteArray();
    }
}
