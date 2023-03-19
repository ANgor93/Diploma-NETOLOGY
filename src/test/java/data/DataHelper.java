package data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {

    private DataHelper() {
    }

    public static String getCurrentMonth() {
        LocalDate date = LocalDate.now();
        String currentMonth = date.format(DateTimeFormatter.ofPattern("MM"));
        return currentMonth;
    }
    public static String getCurrentYear() {
        LocalDate date = LocalDate.now();
        String currentYear = date.format(DateTimeFormatter.ofPattern("yy"));
        return currentYear;
    }


    public static String getApprovedCardNumber() {
        return "4444 4444 4444 4441";
    }

    public static String getDeclinedCardNumber() {
        return "4444 4444 4444 4442";
    }

    public static String getFullUsersName() {
        Faker faker = new Faker();
        return faker.name().name().toUpperCase();
    }

    public static String getFullUsersNameInLowCaseLetters() {
        Faker faker = new Faker();
        return faker.name().name().toLowerCase();
    }

    public static String getFullUsersNameInUpperCaseAndLowCaseLetters() {
        Faker faker = new Faker();
        return faker.name().name();
    }

    public static String getFullUsersNameInRussian(String locale) {
        Faker faker = new Faker(new Locale("ru"));
        return faker.name().name().toUpperCase(Locale.ROOT);
    }

    public static String getOnlyUsersFirstName() {
        Faker faker = new Faker();
        return faker.name().firstName().toUpperCase();
    }

    public static String getOnlyUsersLastName() {
        Faker faker = new Faker();
        return faker.name().lastName().toUpperCase();
    }

    public static int getCVCNumber() {
        Faker faker = new Faker();
        return faker.number().numberBetween(000, 999);
    }
}