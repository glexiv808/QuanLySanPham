package com.BTL.QuanLySanPham.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://localhost:3307/qlsp-db";
        String username = "root";
        String password = "";
        String filePath = "src/main/java/com/BTL/QuanLySanPham/database/data.sql";

        // Kiểm tra xem dữ liệu đã có chưa
        if (!isDataExist(jdbcUrl, username, password)) {
            // Nếu chưa có dữ liệu, chạy file SQL
            try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
                 Statement statement = connection.createStatement()) {

                StringBuilder sb = new StringBuilder();
                BufferedReader reader = new BufferedReader(new FileReader(filePath));
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    sb.append("\n");
                }

                String sql = sb.toString();
                statement.execute(sql); // Thực thi toàn bộ file SQL

                System.out.println("Dữ liệu đã được chèn vào cơ sở dữ liệu thành công!");

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Dữ liệu đã có trong cơ sở dữ liệu, không cần chạy lại file SQL.");
        }
    }

    private static boolean isDataExist(String jdbcUrl, String username, String password) {
        String checkQuery = "SELECT COUNT(*) FROM product";  // Kiểm tra bảng 'product'

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             PreparedStatement statement = connection.prepareStatement(checkQuery);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;  // Nếu có dữ liệu, trả về true
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;  // Nếu không có dữ liệu
    }
}
