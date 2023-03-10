package tests;

import data.DataHelper;

import data.SQLHelper;
import hooks.WebHooks;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static page.PagePayment.waitSuccessMessage;
import static page.PagePayment.withCardNumber;
import static page.PagePaymentOnCredit.*;
import static page.PagePaymentOnCredit.waitErrorMessageAboutWrongFormat;

import static page.StartingPage.buyWithCredit;

public class PayOnCreditTest extends WebHooks {


    @Test
    @DisplayName("Покупка с APPROVED картой")
    void shouldBuySuccessfullyWithApprovedCard() {
        buyWithCredit();
        var approvedCardNumber = DataHelper.getApprovedCardNumber();
        withCardNumber(approvedCardNumber);
        waitSuccessMessage();
        var paymentWithInfo = SQLHelper.getCreditStatus();
        assertEquals("APPROVED", paymentWithInfo);
    }

    @Test
    @DisplayName("Тест с валидными данными")
    void shouldBeCheckedWithValidData() {
        buyWithCredit();
        var approvedCardNumber = DataHelper.getApprovedCardNumber();
        withCardNumber(approvedCardNumber);
        waitSuccessMessage();
        String paymentWithInfo = SQLHelper.getPaymentAmount();
        assertEquals("45000", paymentWithInfo);
    }

    @Test
    @DisplayName("Покупка с DECLINED картой")
    void shouldNotSellWithDeclinedCard() {
        buyWithCredit();
        var declinedCardNumber = DataHelper.getDeclinedCardNumber();
        withCardNumber(declinedCardNumber);
        waitErrorMessage();
        var paymentWithInfo = SQLHelper.getCreditStatus();
        assertEquals("APPROVED", paymentWithInfo);
    }

    @Test
    @DisplayName("Отправка пустой формы")
    void shouldNotSellWhenAllFieldsAreEmpty() {
        buyWithCredit();
        emptyFields();
        waitErrorMessageAboutWrongFormat();
        waitErrorMessageBecauseOfEmptyField();
    }

    @Test
    @DisplayName("Поле CVC содержит одну цифру")
    void shouldNotSellWhenCardValidationCodeIsTwoDigitsShort() {
        buyWithCredit();
        var cvc = "1";
        withCardValidationCode(cvc);
        waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("Поле CVC содержит две цифры")
    void shouldNotSellWhenCardValidationCodeIsOneDigitShort() {
        buyWithCredit();
        var cvc = "12";
        withCardValidationCode(cvc);
        waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("Поле владелец содержит только Фамилию")
    void shouldNotSellWhenNameOfCardholderIsOnlyLastName() {
        buyWithCredit();
        var nameOfCardHolder = DataHelper.getOnlyUsersLastName();
        withCardholder(nameOfCardHolder);
        waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("Поле владелец содержит только Имя")
    void shouldNotSellWhenNameOfCardholderIsOnlyFirstName() {
        buyWithCredit();
        var nameOfCardHolder = DataHelper.getOnlyUsersFirstName();
        withCardholder(nameOfCardHolder);
        waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("Срок карты истек")
    void shouldNotSellWhenYearNumberIsLowerThanAllowed() {
        buyWithCredit();
        var yearNumber = "22";
        withYear(yearNumber);
        waitErrorMessageWithDateOfExpiry();
    }

    @Test
    @DisplayName("Срок карты слишком большой")
    void shouldNotSellWhenYearNumberExceedsTheAllowed() {
        buyWithCredit();
        var yearNumber = "99";
        withYear(yearNumber);
        waitErrorMessageAboutWrongDateOfExpiry();
    }

    @Test
    @DisplayName("В поле год ввести одну цифру")
    void shouldNotSellWhenYearNumberIsOneDigitalShort() {
        buyWithCredit();
        var yearNumber = "2";
        withYear(yearNumber);
        waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("В поле год ввести 00")
    void shouldNotSellWhenYearNumberIsZeros() {
        buyWithCredit();
        var yearNumber = "00";
        withYear(yearNumber);
        waitErrorMessageWithDateOfExpiry();
    }

    @Test
    @DisplayName("Номер карты состоит из 0")
    void shouldNotSellWhenCardNumberIsZeros() {
        buyWithCredit();
        var number = "0000 0000 0000 0000";
        withCardNumber(number);
        waitErrorMessage();
    }

    @Test
    @DisplayName("В поле номер карты ввести невалидные данные карты")
    void shouldNotSellWhenCardNumberIsUnknown() {
        buyWithCredit();
        var number = "4444 4444 4444 4443";
        withCardNumber(number);
        waitErrorMessage();
    }

    @Test
    @DisplayName("В поле номер карты ввести меньше 16 цифр")
    void shouldNotSellWhenCardNumberIsShort() {
        buyWithCredit();
        var number = "4444 4444 4444 444";
        withCardNumber(number);
        waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("В поле месяц ввести 00")
    void shouldNotSellWhenMonthNumberIsZeros() {
        buyWithCredit();
        var monthNumber = "00";
        withMonth(monthNumber);
        waitErrorMessageAboutWrongDateOfExpiry();
    }

    @Test
    @DisplayName("В поле месяц ввести одну цифру")
    void shouldNotSellWhenMonthNumberIsOneDigitShort() {
        buyWithCredit();
        var monthNumber = "2";
        withMonth(monthNumber);
        waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("В поле месяц ввести несуществующий месяц")
    void shouldNotSellWhenMonthNumberExceedsTheAllowed() {
        buyWithCredit();
        var monthNumber = "13";
        withMonth(monthNumber);
        waitErrorMessageAboutWrongDateOfExpiry();
    }

    @Test
    @DisplayName("В поле владелец ввести одну букву")
    void shouldNotSellWhenNameOfCardholderIsOnlyOneLetter() {
        buyWithCredit();
        var nameOfCardHolder = "R";
        withCardholder(nameOfCardHolder);
        waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("В поле владелец ввести много букв")
    void shouldNotSellWhenNameOfCardholderHasLotsOfLetters() {
        buyWithCredit();
        var nameOfCardHolder = "QWEJVNCMDKDFCVBGAJZNDTMDLMREW QWFTGRYFBSYRHFYTVCPQZMHYNJI ";
        withCardholder(nameOfCardHolder);
        waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("В поле владелец ввести данные строчными буквами")
    void shouldNotSellWhenNameOfCardholderInLowerCaseLetters() {
        buyWithCredit();
        var nameOfCardHolder = DataHelper.getFullUsersNameInLowCaseLetters();
        withCardholder(nameOfCardHolder);
        waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("В поле владелец ввести данные прописными и строчными буквами")
    void shouldNotSellWhenNameOfCardholderInUpperCaseAndLowerCaseLetters() {
        buyWithCredit();
        var nameOfCardHolder = DataHelper.getFullUsersNameInUpperCaseAndLowCaseLetters();
        withCardholder(nameOfCardHolder);
        waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("В поле владелец ввести данные на кириллице")
    void shouldNotSellWhenNameOfCardholderIsInRussian() {
        buyWithCredit();
        var nameOfCardHolder = DataHelper.getFullUsersNameInRussian("ru");
        withCardholder(nameOfCardHolder);
        waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("В поле владелец ввести цифры")
    void shouldNotSellWhenNameOfCardholderInContainsNumbers() {
        buyWithCredit();
        var nameOfCardHolder = "1234567890";
        withCardholder(nameOfCardHolder);
        waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("В поле владелец ввести спецсимволы")
    void shouldNotSellWhenNameOfCardholderInContainsSpecialCharacters() {
        buyWithCredit();
        var nameOfCardHolder = "!@#$%^&*";
        withCardholder(nameOfCardHolder);
        waitErrorMessageAboutWrongFormat();
    }
}