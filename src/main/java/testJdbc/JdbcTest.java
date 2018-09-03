package testJdbc;

import util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcTest {
    public static void main(String[] args) throws SQLException {
//        Connection connection = ConnectionUtil.getConnection();
//        PreparedStatement preparedStatement = connection.prepareStatement("Select * from t_customer");
//        ResultSet resultSet = preparedStatement.executeQuery();
//        while (resultSet.next()) {
//            String name = resultSet.getString("name");
//            String id = resultSet.getString("id");
//            System.out.println("id=" + id + ",name=" + name);
//        }


//        System.out.println(getCustomerList());
//        System.out.println(getCustomer("小红"));
    }

    /**
     * 使用Statement查询数据库
     */
    private static List<Customer> getCustomerList() throws SQLException {
        Connection connection = ConnectionUtil.getConnection();
        Statement statement = connection.createStatement();
        String sql = "select * from t_customer";
        ResultSet resultSet = statement.executeQuery(sql);
        List<Customer> customerList = new ArrayList<>();
        while (resultSet.next()) {
            String name = resultSet.getString("name");
            String id = resultSet.getString("id");
            Customer customer = new Customer();
            customer.setId(id);
            customer.setName(name);
            customerList.add(customer);
        }

        return customerList;
    }


    /**
     * 使用PreparedStatement预编译sql，查询数据库
     */
    private static Customer getCustomer(String name) throws SQLException {
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from t_customer where name=?");
        preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();
        Customer customer = new Customer();
        while (resultSet.next()) {
            customer.setName(resultSet.getString("name"));
            customer.setId(resultSet.getString("id"));
        }

        return customer;
    }
}
