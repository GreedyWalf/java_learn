package testStream;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class StreamUtils {

    /**
     * 在指定文本路径文件中，查找word字符串出现的次数
     *
     * @param fileName 文件路径
     * @param word     查找的字符串
     * @return 返回word在文本中出现的次数
     */
    public static int countWordInFile(String fileName, String word) {
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                int index = -1;
                while (line.length() >= word.length() && (index = line.indexOf(word)) > -1) {
                    count++;
                    line = line.substring(index + word.length());
                }
            }

        } catch (Exception e) {
            e.getStackTrace();
        }

        return count;
    }

    /**
     * 使用JDK新特性NIO的API遍历文件夹中的文件
     *
     * @param filePath 指定文件目录
     * @return 返回该文件目录中的所有文件
     */
    public static List<String> getFileNameFromPath(String filePath) throws IOException {
        List<String> fileNames = new ArrayList<>();
        Path initPath = Paths.get(filePath);
        Files.walkFileTree(initPath, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println(file.getFileName().toString());
                fileNames.add(file.getFileName().toString());
                return FileVisitResult.CONTINUE;
            }
        });

        return fileNames;
    }
}


class TestMain {
    public static void main(String[] args) throws IOException {
        int count = StreamUtils.countWordInFile("/Users/qinyupeng/Downloads/ceshi.txt", "ai");
        System.out.println(count);

        List<String> fileNames = StreamUtils.getFileNameFromPath("/Users/qinyupeng/Downloads");
        System.out.println(fileNames);
    }
}
