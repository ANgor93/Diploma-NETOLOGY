package pages;

import com.codeborne.selenide.Condition;

import data.DataHelper;

import java.time.Duration;

import static pages.elements.PagePaymentOnCreditElem.*;


public class PagePaymentOnCredit {


    public PagePaymentOnCredit() {
        heading.shouldBe(Condition.visible);
    }

    public void withCardNumber(String number) {
        cardNumber.setValue(number);
        month.setValue(DataHelper.getCurrentMonth());
        year.setValue(DataHelper.getCurrentYear());
        cardholder.setValue(DataHelper.getFullUsersName());
        cardValidationCode.setValue(String.valueOf(DataHelper.getCVCNumber()));
        continueButton.click();
    }

    public void withMonth(String monthNumber) {
        cardNumber.setValue(DataHelper.getApprovedCardNumber());
        month.setValue(monthNumber);
        year.setValue(DataHelper.getCurrentYear());
        cardholder.setValue(DataHelper.getFullUsersName());
        cardValidationCode.setValue(String.valueOf(DataHelper.getCVCNumber()));
        continueButton.click();
    }

    public void withYear(String yearNumber) {
        cardNumber.setValue(DataHelper.getApprovedCardNumber());
        month.setValue(DataHelper.getCurrentMonth());
        year.setValue(yearNumber);
        cardholder.setValue(DataHelper.getFullUsersName());
        cardValidationCode.setValue(String.valueOf(DataHelper.getCVCNumber()));
        continueButton.click();
    }

    public void withCardholder(String nameOfCardholder) {
        cardNumber.setValue(DataHelper.getApprovedCardNumber());
        month.setValue(DataHelper.getCurrentMonth());
        year.setValue(DataHelper.getCurrentYear());
        cardholder.setValue(nameOfCardholder);
        cardValidationCode.setValue(String.valueOf(DataHelper.getCVCNumber()));
        continueButton.click();
    }

    public void withCardValidationCode(String cvc) {
        cardNumber.setValue(DataHelper.getApprovedCardNumber());
        month.setValue(DataHelper.getCurrentMonth());
        year.setValue(DataHelper.getCurrentYear());
        cardholder.setValue(DataHelper.getFullUsersName());
        cardValidationCode.setValue(cvc);
        continueButton.click();
    }

    public void emptyFields() {
        continueButton.click();
    }

    public void waitSuccessMessage() {

        successMessage.should(Condition.visible, Duration.ofSeconds(20));
    }

    public void waitErrorMessage() {

        errorMessage.should(Condition.visible, Duration.ofSeconds(20));
    }

    public void waitErrorMessageAboutWrongFormat() {

        errorMessageAboutWrongFormat.should(Condition.visible, Duration.ofSeconds(10));
    }

    public void waitErrorMessageAboutWrongDateOfExpiry() {
        errorMessageAboutWrongDateOfExpiry.should(Condition.visible, Duration.ofSeconds(10));
    }

    public void waitErrorMessageWithDateOfExpiry() {
        errorMessageWithDateOfExpiry.should(Condition.visible, Duration.ofSeconds(10));
    }

    public void waitErrorMessageBecauseOfEmptyField() {
        errorMessageBecauseOfEmptyField.should(Condition.visible, Duration.ofSeconds(10));
    }
}
