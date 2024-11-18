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

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {

            StringBuilder sb = new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }

            // Xử lý lệnh SQL
            String[] sqlCommands = sb.toString().split(";");

            for (String sqlCommand : sqlCommands) {
                sqlCommand = sqlCommand.trim();
                if (!sqlCommand.isEmpty() && sqlCommand.toLowerCase().startsWith("insert")) {
                    processInsertCommand(connection, sqlCommand);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void processInsertCommand(Connection connection, String sqlCommand) {
        try {
            // Lấy phần VALUES trong câu lệnh INSERT
            String valuesPart = extractValuesPart(sqlCommand);

            // Tách các hàng dữ liệu trong VALUES
            String[] rows = valuesPart.split("\\),\\s*\\(");

            // Xử lý từng hàng
            for (String row : rows) {
                row = row.replace("(", "").replace(")", "").trim(); // Loại bỏ dấu ngoặc
                String[] columns = row.split(",\\s*"); // Tách các giá trị

                // Kiểm tra dữ liệu có trùng lặp không
                if (!isDuplicateDataExist(connection, columns)) {
                    String tableName = extractTableName(sqlCommand);
                    String columnNames = extractColumnNames(sqlCommand);

                    // Tạo và thực thi lệnh INSERT cho hàng này
                    String insertQuery = "INSERT INTO " + tableName + " " + columnNames + " VALUES (" + row + ")";
                    try (Statement statement = connection.createStatement()) {
                        statement.execute(insertQuery);
                        System.out.println("Thực thi: " + insertQuery);
                    }
                } else {
                    System.out.println("Bỏ qua (trùng lặp): " + row);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean isDuplicateDataExist(Connection connection, String[] columns) {
        // Kiểm tra dữ liệu trùng lặp dựa trên cột "name" (hoặc tùy chỉnh theo logic riêng)
        String checkQuery = "SELECT COUNT(*) FROM product WHERE name = ?";
        try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
            checkStmt.setString(1, columns[0].replace("'", "")); // Lấy giá trị của cột 'name'

            ResultSet resultSet = checkStmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0; // Trả về true nếu dữ liệu trùng lặp
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static String extractTableName(String sqlCommand) {
        String lowerCaseCommand = sqlCommand.toLowerCase();
        if (lowerCaseCommand.contains("insert into")) {
            int start = lowerCaseCommand.indexOf("insert into") + 11;
            int end = lowerCaseCommand.indexOf("(", start);
            return sqlCommand.substring(start, end).trim();
        }
        return null;
    }

    private static String extractColumnNames(String sqlCommand) {
        int start = sqlCommand.indexOf("(");
        int end = sqlCommand.indexOf(")", start);
        return sqlCommand.substring(start, end + 1).trim();
    }

    private static String extractValuesPart(String sqlCommand) {
        int start = sqlCommand.toLowerCase().indexOf("values") + 6;
        return sqlCommand.substring(start).trim();
    }

}
