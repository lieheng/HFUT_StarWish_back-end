package org.example.hfut_starwish_backend.Controller;

import org.example.hfut_starwish_backend.JDBC.JDBC;
import org.example.hfut_starwish_backend.entity.Report;
import org.example.hfut_starwish_backend.entity.ReportParameter;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

@RestController
@CrossOrigin
public class HistoryController {
    @RequestMapping(value = "/history", method = RequestMethod.POST)
    public ArrayList<Report> history(String username) {
        ArrayList reports = new ArrayList<Report>();
        Connection connection = JDBC.getConnection();
        String sql = "SELECT * FROM report WHERE owner = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String reportName = resultSet.getString("name");
                String parameter1 = resultSet.getString("parameter1");
                String parameter2 = resultSet.getString("parameter2");
                String parameter3 = resultSet.getString("parameter3");
                ReportParameter reportParameter1 = new ReportParameter("参数1", parameter1);
                ReportParameter reportParameter2 = new ReportParameter("参数2", parameter2);
                ReportParameter reportParameter3 = new ReportParameter("参数3", parameter3);
                Report report = new Report(reportName, new ReportParameter[]{reportParameter1, reportParameter2, reportParameter3});
                reports.add(report);
            }
            Collections.reverse(reports);
            if (username.equals("")) {
                sql = "delete from report where owner = '';";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.executeUpdate();
            }
            return reports;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
