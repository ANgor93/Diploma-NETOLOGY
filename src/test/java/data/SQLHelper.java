package data;

import models.CreditRequest;
import models.PaymentRequest;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class SQLHelper {
    private static Connection connect;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;


    static {
        Properties props = new Properties();
        try (InputStream inputStream = new FileInputStream("./application.properties")) {
            props.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        URL = props.getProperty("spring.datasource.url");
        USERNAME = props.getProperty("spring.datasource.username");
        PASSWORD = props.getProperty("spring.datasource.password");
    }


    private static Connection getConnection() {
        try {
            connect = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return connect;
    }

    public static PaymentRequest getLatestPaymentRequest() {
        var runner = new QueryRunner();
        var payRequest = "SELECT * FROM payment_entity ORDER BY id DESC LIMIT 1";

        try (var connect = getConnection()) {
            var paymentRequest = runner.query(connect, payRequest, new BeanHandler<>(PaymentRequest.class));
            return paymentRequest;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }

    public static CreditRequest getLatestCreditRequest() {
        var runner = new QueryRunner();
        var cRequest = "SELECT * FROM credit_request_entity ORDER BY id DESC LIMIT 1";

        try (var connect = getConnection()) {
            var creditRequest = runner.query(connect, cRequest, new BeanHandler<>(CreditRequest.class));
            return creditRequest;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }

    public static void clearTables() {
        var runner = new QueryRunner();
        var payClear = "DELETE FROM payment_entity";
        var cClear = "DELETE FROM credit_request_entity";

        try (var connect = getConnection()) {
            runner.update(connect, payClear);
            runner.update(connect, cClear);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public static void closeConnection() {
        try {
            if (connect != null) {
                connect.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}