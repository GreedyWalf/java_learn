package DesignPattern.factory.method;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

/**
 * 测试：使用工厂方法模式开发日志记录器，再满足"开闭原则"前提下，可以动态的添加使用何种方式存储日志（数据库、文件方式等）
 * 在该实例中：
 *  1、定义了抽象工厂（LoggerFactory接口），定义了DatabaseLoggerFactory、FileLoggerFactory作为具体工厂，两者均实现LoggerFactory接口；
 *  2、定义了抽象产品（Logger接口），定义了DatabaseLogger、FileLogger作为具体产品，两者觉实现Logger接口；
 *  3、客户端记录日志，使用"里氏转换原则"，只要选择使用的具体工厂，创建对应具体的产品实例即可（满足"开闭原则"可以在xml中配置具体工厂类路径方式，在不改变代码的前提下，选择具体工厂类，完成产品的实例化）；
 */
public class ClientMain {
    public static void main(String[] args) {
        LoggerFactory loggerFactory;
        //可以使用配置文件方式引入
        //loggerFactory = new DatabaseLoggerFactory();
        loggerFactory = (LoggerFactory) XMLUtil.getBean();
        if (loggerFactory == null) {
            System.out.println("检查config.xml配置文件，获取具体工厂类实例失败！");
            return;
        }

        Logger logger = loggerFactory.createLogger();
        logger.writeLog();
    }
}

/**
 * 日志记录器接口：抽象产品
 */
interface Logger {
    void writeLog();
}

/**
 * 数据库日志记录器：具体产品
 */
class DatabaseLogger implements Logger {

    @Override
    public void writeLog() {
        System.out.println("数据库日志记录。。");
    }
}


/**
 * 文件日志记录器：具体产品
 */
class FileLogger implements Logger {

    @Override
    public void writeLog() {
        System.out.println("文件日志记录。。");
    }
}


/**
 * 日志记录器工厂接口：抽象工厂
 */
interface LoggerFactory {
    Logger createLogger();
}

/**
 * 数据库日志记录器工厂类：具体工厂
 */
class DatabaseLoggerFactory implements LoggerFactory {

    @Override
    public Logger createLogger() {
        Logger logger = new DatabaseLogger();
        return logger;
    }
}

/**
 * 文件日志记录器工厂类：具体工厂
 */
class FileLoggerFactory implements LoggerFactory {

    @Override
    public Logger createLogger() {
        Logger logger = new FileLogger();
        return logger;
    }
}


/**
 * 解析xml配置文件工具类
 */
class XMLUtil {
    public static Object getBean() {
        try {
            //解析config.xml，获取配置的具体工厂类全路径
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            String fullConfigPath = "/Users/qinyupeng/IdeaProjects/MyProjects/java_learn/src/main/java/DesignPattern/factory/method/config.xml";
            Document document = builder.parse(new File(fullConfigPath));
            NodeList nodeList = document.getElementsByTagName("className");
            Node node = nodeList.item(0).getFirstChild();
            String className = node.getNodeValue();

            //反射，根据类名全路径获取字节码对象
            Class clazz = Class.forName(className);
            //通过类的字节码对象，获取该类的实例
            Object obj = clazz.newInstance();
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}