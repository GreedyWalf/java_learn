package DesignPattern.fasecade;

import java.io.*;

/**
 * 测试：外观模式（门面模式）
 * 为子系统中的字接口提供一个统一的入口。
 * 外观模式定义了一个高层接口，这个接口使得这一子系统更加容易使用。
 */
public class ClientMain {
    public static void main(String[] args) {
        EncryptFacade facade = new EncryptFacade();
        facade.fileEncrypt("src.txt", "des.txt");
    }
}


/**
 * 加密外观类，充当外观类（门面）
 * 将FileReader、FileWriter、CipherMachine三个子系统整合在一个类中，提供给客户端访问，解耦合；
 */
class EncryptFacade {
    private FileReader reader;
    private FileWriter writer;
    private CipherMachine cipher;

    public EncryptFacade() {
        reader = new FileReader();
        cipher = new CipherMachine();
        writer = new FileWriter();
    }

    //调用其他对象的业务方法，整合为一个流程的业务方法
    public void fileEncrypt(String fileNameSrc, String fileNameDes) {
        String plainStr = reader.read(fileNameSrc);
        String encryptStr = cipher.encrpty(plainStr);
        writer.writer(encryptStr, fileNameDes);
    }
}

/**
 * 文件读取类，充当子系统类
 */
class FileReader {

    public String read(String fileName) {
        System.out.println("读取文件，获取明文：");
        StringBuilder fileContent = new StringBuilder();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(new File(fileName));
            int len = 0;
            byte[] buff = new byte[1024];
            while (-1 != (len = (fis.read(buff)))) {
                fileContent.append(new String(buff, 0, len));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return fileContent.toString();
    }
}


/**
 * 数据加密类，充当子系统类
 */
class CipherMachine {

    public String encrpty(String plainText) {
        System.out.println("数据加密，将明文转化为密文");
        StringBuilder es = new StringBuilder();
        char[] chars = plainText.toCharArray();
        for (char ch : chars) {
            String c = String.valueOf(ch % 7);
            es.append(c);
        }

        System.out.println(es);
        return es.toString();
    }

}


/**
 * 文件保存类，充当子系统类
 */
class FileWriter {

    public void writer(String encryStr, String fileName) {
        System.out.println("保存密文，写入文件。");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File(fileName));
            byte[] bytes = encryStr.getBytes();
            fos.write(bytes);
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}