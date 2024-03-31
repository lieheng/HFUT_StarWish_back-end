package org.example.hfut_starwish_backend.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.example.hfut_starwish_backend.JDBC.JDBC;
import org.example.hfut_starwish_backend.entity.Report;
import org.example.hfut_starwish_backend.entity.ReportParameter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@RestController
@CrossOrigin
public class FileController {
    @PostMapping("/upload")
    public boolean upload(String username, MultipartFile file, HttpServletRequest request) throws IOException {
        String path = request.getServletContext().getRealPath("/upload/");
        if (username.equals("")) {
            path += "TMP\\";
        } else {
            path += username + "\\";
        }
        SaveFile(file, path);
        SaveReport(HandleFile(file), username);
        DeleteFile(file, path);
        return true;
    }

    public void SaveFile(MultipartFile multipartFile, String path) throws IOException {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(path + multipartFile.getOriginalFilename());
        multipartFile.transferTo(file);
    }

    public Report HandleFile(MultipartFile multipartFile) throws IOException {
        //Todo
        ReportParameter reportParameter1 = new ReportParameter("参数1", multipartFile.getOriginalFilename() + "参数1的值");
        ReportParameter reportParameter2 = new ReportParameter("参数2", multipartFile.getOriginalFilename() + "参数2的值");
        ReportParameter reportParameter3 = new ReportParameter("参数3", multipartFile.getOriginalFilename() + "参数3的值");
        ReportParameter[] reportParameters = new ReportParameter[3];
        reportParameters[0] = reportParameter1;
        reportParameters[1] = reportParameter2;
        reportParameters[2] = reportParameter3;
        Report report = new Report(multipartFile.getOriginalFilename(), reportParameters);
        return report;
    }

    public void DeleteFile(MultipartFile multipartFile, String path) throws IOException {
        File dir = new File(path);
        if (!dir.exists()) {
            return;
        }
        File file = new File(path + multipartFile.getOriginalFilename());
        file.delete();
    }

    public boolean SaveReport(Report report, String username) {
        Connection connection = JDBC.getConnection();
        LocalDateTime dateTime = LocalDateTime.now(); // get the current date and time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String sql = "INSERT INTO report (name, parameter1, parameter2, parameter3, owner) VALUES (?, ?, ?, ?, ?);";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, report.getReportName() + '_' + dateTime.format(formatter));
            preparedStatement.setString(2, report.getReportParameters()[0].getParameterVal());
            preparedStatement.setString(3, report.getReportParameters()[1].getParameterVal());
            preparedStatement.setString(4, report.getReportParameters()[2].getParameterVal());
            preparedStatement.setString(5, username);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 1) {
                return true;//注册成功
            } else {
                return false;//注册失败
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
