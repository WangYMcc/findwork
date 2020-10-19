package com.wangyuming.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MysqlTest {
    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            // 加载数据库驱动
            Class.forName("com.mysql.jdbc.Driver");
            // 通过驱动管理类获取数据库链接
            connection = DriverManager.getConnection("jdbc:mysql://39.97.184.136:3306/mybatis?characterEncoding=utf-8", "root", "wym54150812");
            // 定义sql语句？表示占位符
            String sql = "select * from user where username = ?";
            // 获取预处理statement
            preparedStatement = connection.prepareStatement(sql);
            // 设置参数，第一个参数为sql语句中参数的序号(从1开始)，第二个参数为设置的参数值
            preparedStatement.setString(1, "tom");

            // 向数据库发出sql执行查询，查询出结果集
            resultSet = preparedStatement.executeQuery();

            // 遍历查询结果集
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                // 封装User
                System.out.println("id: " + id + "username: " + username);
            }
        }catch (Exception e){

        }
    }
}