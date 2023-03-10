package page;

import com.codeborne.selenide.Condition;

import data.DataHelper;


import static elements.PagePaymentElements.*;

public class PagePayment {


    public PagePayment() {
        heading.shouldBe(Condition.visible);
    }

    public static void withCardNumber(String number) {
        cardNumber.setValue(number);
        month.setValue(DataHelper.generateMonthNumber());
        year.setValue(DataHelper.generateYearNumber());
        cardholder.setValue(DataHelper.getFullUsersName());
        cardValidationCode.setValue(String.valueOf(DataHelper.getCVCNumber()));
        continueButton.click();
    }

    public static void withMonth(String monthNumber) {
        cardNumber.setValue(DataHelper.getApprovedCardNumber());
        month.setValue(monthNumber);
        year.setValue(DataHelper.generateYearNumber());
        cardholder.setValue(DataHelper.getFullUsersName());
        cardValidationCode.setValue(String.valueOf(DataHelper.getCVCNumber()));
        continueButton.click();
    }

    public static void withYear(String yearNumber) {
        cardNumber.setValue(DataHelper.getApprovedCardNumber());
        month.setValue(DataHelper.generateMonthNumber());
        year.setValue(yearNumber);
        cardholder.setValue(DataHelper.getFullUsersName());
        cardValidationCode.setValue(String.valueOf(DataHelper.getCVCNumber()));
        continueButton.click();
    }

    public static void withCardholder(String nameOfCardholder) {
        cardNumber.setValue(DataHelper.getApprovedCardNumber());
        month.setValue(DataHelper.generateMonthNumber());
        year.setValue(DataHelper.generateYearNumber());
        cardholder.setValue(nameOfCardholder);
        cardValidationCode.setValue(String.valueOf(DataHelper.getCVCNumber()));
        continueButton.click();
    }

    public static void withCardValidationCode(String cvc) {
        cardNumber.setValue(DataHelper.getApprovedCardNumber());
        month.setValue(DataHelper.generateMonthNumber());
        year.setValue(DataHelper.generateYearNumber());
        cardholder.setValue(DataHelper.getFullUsersName());
        cardValidationCode.setValue(cvc);
        continueButton.click();
    }

    public static void emptyFields() {
        continueButton.click();
    }

    public static void waitSuccessMessage() {
        successMessage.waitUntil(Condition.visible, 20000);
    }

    public static void waitErrorMessage() {

        errorMessage.waitUntil(Condition.visible, 20000);
    }

    public static void waitErrorMessageAboutWrongFormat() {

        errorMessageAboutWrongFormat.waitUntil(Condition.visible, 10000);
    }

    public static void waitErrorMessageAboutWrongDateOfExpiry() {
        errorMessageAboutWrongDateOfExpiry.waitUntil(Condition.visible, 10000);
    }

    public static void waitErrorMessageWithDateOfExpiry() {

        errorMessageWithDateOfExpiry.waitUntil(Condition.visible, 10000);
    }

    public static void waitErrorMessageBecauseOfEmptyField() {
        errorMessageBecauseOfEmptyField.waitUntil(Condition.visible, 15000);
    }
}
