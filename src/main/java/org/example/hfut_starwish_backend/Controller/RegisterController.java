package org.example.hfut_starwish_backend.Controller;

import org.example.hfut_starwish_backend.JDBC.JDBC;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@RestController
@CrossOrigin
public class RegisterController {
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public int register(String username, String password, String confirmPassword) {
        Connection connection = JDBC.getConnection();
        String sql = "SELECT * FROM user WHERE username = ?;";//查询语句
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return 3;// 账号已存在
            } else {
                if (password.equals(confirmPassword)) {
                    sql = "INSERT INTO user (username, password) VALUES (?, ?);";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, password);
                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected == 1) {
                        return 0;//注册成功
                    } else {
                        return 5;//注册失败
                    }
                } else {
                    return 4;//密码不一致
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
