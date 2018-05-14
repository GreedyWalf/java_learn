package util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class SqlUtil {

    private static String password;
    private static String userName;
    private static String connectUrl;


    static {
        try {
            Properties properties = new Properties();
            InputStream inputStream = SqlUtil.class.getClassLoader().getResourceAsStream("db.properties");
            properties.load(inputStream);

            password = (String) properties.get("jdbc.password");
            userName = (String) properties.get("jdbc.username");
            String driverClass = (String) properties.get("jdbc.driverClassName");
            connectUrl = (String) properties.get("jdbc.url");

            //加载数据库连接驱动
            Class.forName(driverClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static Connection getConnection(){
        try {
            //创建数据库连接
            return DriverManager.getConnection(connectUrl, userName, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}