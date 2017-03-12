package dao;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.*;
import java.util.List;

/*
 * Класс для создания и наполнения БД
 */
public class DBCreation {

    private static final String URL0 = "jdbc:mysql://localhost:3306?useSSL=false";
    private static final String URL = "jdbc:mysql://localhost:3306/test?useSSL=false";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "root";

    private Connection connection;

    private Connection getConnection() {
        try {
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
        } catch (SQLException e) {
            this.createDB();
            this.getConnection();
        }
        return connection;
    }

    //Создаём базу test и таблицу test.user
    public void createDB() {

        try {
            connection = DriverManager.getConnection(URL0, LOGIN, PASSWORD);
            Statement statement = connection.createStatement();
            statement.addBatch("create schema if not exists test;");
            statement.addBatch("CREATE TABLE IF NOT EXISTS test.user (\n" +
                    "  id INT(8) PRIMARY KEY NOT NULL AUTO_INCREMENT,\n" +
                    "  name VARCHAR(25) NOT NULL ,\n" +
                    "  age INT(3) NOT NULL,\n" +
                    "  admin BIT(1) NOT NULL,\n" +
                    "  date TIMESTAMP(1) NOT NULL);");
            //заполняем таблицу данными
            statement.executeBatch();
            ResultSet rs = statement.executeQuery("select id from test.user");
            int allRows = 0;
            while (rs.next()) {
                allRows++;
            }
            if (allRows > 0) return;
            List<String> list = Files.readAllLines(new File("src/main/resources/fill.sql").toPath());
            String sql = "";
            for (String s : list) {
                sql += s;
            }
            statement.execute(sql);
            statement.close();
            connection.close();
        } catch (SQLException e) {
            getConnection();
            System.out.println("Ошибка создания БД " + e);
        } catch (IOException e) {
            System.out.println("Не найден файл с запросом " + e);
        }
    }
}
