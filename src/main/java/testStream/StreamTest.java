package testStream;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StreamTest {
    public static void main(String[] args) throws Exception {
//        corpFile();

        copyDir();
    }

    private static void testString() {
        byte[] bytes = "aaa".getBytes();
        System.out.println(bytes.length);
        String str = new String(bytes);

        byte b = 1;
        System.out.println(b);
        b = 'a';
        System.out.println(b);

        System.out.println("和客家话可炬华科技".getBytes().length);
        System.out.println(new String("测试".getBytes(), 0, "测试".getBytes().length));
    }


    /**
     * 复制文件
     */
    public static void copyFile() throws IOException {
        String sourceFile = "D:/ceshi.txt";
        String destFile = "D:/ceshi_copy.txt";

        InputStream inputStream = new FileInputStream(sourceFile);
        OutputStream outputStream = new FileOutputStream(destFile);
        byte[] buffer = new byte[1024];
        int len = 0;
        while (-1 != (len = inputStream.read(buffer))) {
            System.out.println(len);
            System.out.println(buffer.length);
            outputStream.write(buffer, 0, len);
            outputStream.flush();
        }

        inputStream.close();
        outputStream.close();
    }


    /**
     * 复制文件夹及文件夹中的内容
     */
    public static void copyDir() throws Exception {
        String sourceDirPath = "D:\\cert";
        String destDirPath = "D:\\copy\\";

        File sourceDir = new File(sourceDirPath);
        File destDir = new File(destDirPath);

        if (!destDir.exists()) {
            destDir.mkdir();
        }

        //复制的文件夹对象集合
        List<File> copyFileList = new ArrayList<>();

        //复制的文件对象集合
        List<File> copyDirList = new ArrayList<>();

        //将源目录下的文件对象和文件夹对象分别取出来
        getFileList(copyFileList, copyDirList, sourceDir);

        //文件夹不存在，新建文件夹
        for (File dir : copyDirList) {
            String dirPtah = dir.getAbsolutePath().replace("D:\\", destDirPath);
            File destDirectory = new File(dirPtah);
            if(!destDirectory.exists()){
                destDirectory.mkdirs();
                System.out.println(destDirectory.getAbsolutePath());
            }
        }

        //文件夹复制完成后，使用字节流复制文件夹中文件
        InputStream fis = null;
        OutputStream fos = null;
        for (File copyFile : copyFileList) {
            String copyFilePath = copyFile.getAbsolutePath().replace("D:\\", destDirPath);
            File newFile = new File(copyFilePath);
            fis = new FileInputStream(copyFile);
            fos = new FileOutputStream(newFile);
            byte[] buff = new byte[1024 * 1024];
            int len = 0;
            while (-1 != (len = fis.read(buff))) {
                fos.write(buff, 0, len);
                fos.flush();
            }

            fis.close();
            fos.close();
        }
    }

    //递归调用获取源目录中的文件夹对象和文件对象列表
    private static void getFileList(List<File> copyFileList, List<File> copyDirList, File sourceFile) {
        File[] files = sourceFile.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                copyDirList.add(file);
                getFileList(copyFileList, copyDirList, file);
            } else {
                copyFileList.add(file);
            }
        }
    }
}


