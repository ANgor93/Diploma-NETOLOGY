package data;

import models.CreditRequest;
import models.PaymentRequest;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

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
        try (InputStream inputStream = SQLHelper.class.getClassLoader().getResourceAsStream("application.properties")) {
            props.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        URL = props.getProperty("spring.datasourceMySql.url");
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

    public static String getPaymentStatus() {
        var runner = new QueryRunner();
        var payStatus = "SELECT status FROM payment_entity";

        try (var connect = getConnection()) {
            var paymentStatus = runner.query(connect, payStatus, new BeanHandler<>(PaymentRequest.class));
            return paymentStatus.getStatus();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }

    public static String getPaymentAmount() {
        var runner = new QueryRunner();
        var payAmount = "SELECT amount FROM payment_entity";

        try (var connect = getConnection()) {
            var paymentAmount = runner.query(connect, payAmount, new BeanHandler<>(PaymentRequest.class));
            return paymentAmount.getAmount();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }

    public static String getCreditStatus() {
        var runner = new QueryRunner();
        var cStatus = "SELECT status FROM credit_request_entity";

        try (var connect = getConnection()) {
            var creditStatus = runner.query(connect, cStatus, new BeanHandler<>(CreditRequest.class));
            return creditStatus.getStatus();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
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
