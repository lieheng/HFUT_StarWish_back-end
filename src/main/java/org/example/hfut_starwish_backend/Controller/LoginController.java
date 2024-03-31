package org.example.hfut_starwish_backend.Controller;

import org.example.hfut_starwish_backend.JDBC.JDBC;
import org.springframework.web.bind.annotation.*;

import java.sql.*;


@RestController
@CrossOrigin
public class LoginController {
    @PostMapping(value = "/login")
    public int login(String username, String password) throws Exception {
        Connection connection = JDBC.getConnection();
        String sql = "SELECT * FROM user WHERE username = ?;";//查询语句
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String passwordInDatabase = resultSet.getString("password");
                if (passwordInDatabase.equals(password)) {
                    return 0; // 登录成功
                } else {
                    return 1; // 密码错误
                }
            } else {
                return 2;// 账号不存在
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
