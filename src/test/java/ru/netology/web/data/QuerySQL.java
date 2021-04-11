package ru.netology.web.data;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;


public class QuerySQL {
    private final static QueryRunner runner = new QueryRunner();
    private final static Connection conn = getConnect();

    @SneakyThrows
    private static Connection getConnect() {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/appdb", "user", "pass"
        );
    }

    @SneakyThrows
    public static String getVerificationCodeFor(String user) {
        val idSQL = "SELECT id FROM users WHERE login = '" + user + "';";
        String id = runner.query(conn, idSQL, new ScalarHandler<>());
        val code = "SELECT code FROM auth_codes WHERE user_id = '" + id + "' ORDER BY created DESC;";
        return runner.query(conn, code, new ScalarHandler<>());
    }

    @SneakyThrows
    public static void addUser(String user, String password) {
        val addSQL = "INSERT INTO users(id, login, password) VALUES (?, ?, ?);";
        runner.update(conn, addSQL, new Faker().idNumber().valid(), user, password);

    }

    @SneakyThrows
    public static String getStatusFor(String user) {
        val statusSQL = "SELECT status FROM users WHERE login = '" + user + "';";
        return runner.query(conn, statusSQL, new ScalarHandler<>());
    }

    @SneakyThrows
    public static int getCardBalance(String id) {
        val balanceSQL = "SELECT balance_in_kopecks FROM cards WHERE id ='" + id + "';";
        int balance = runner.query(conn, balanceSQL, new ScalarHandler<>());
        return (balance / 100);
    }

    @SneakyThrows
    public static String getCardNumber(String id) {
        val statusSQL = "SELECT number FROM cards WHERE id ='" + id + "';";
        return runner.query(conn, statusSQL, new ScalarHandler<>());
    }

}

