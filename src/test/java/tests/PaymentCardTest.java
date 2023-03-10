package tests;

import data.DataHelper;

import data.SQLHelper;
import hooks.WebHooks;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static page.PagePayment.*;
import static page.StartingPage.buyWithCard;

public class PaymentCardTest extends WebHooks {


    @Test
    @DisplayName("Покупка с APPROVED картой")
    void shouldBuySuccessfullyWithApprovedCard() {
        buyWithCard();
        var approvedCardNumber = DataHelper.getApprovedCardNumber();
        withCardNumber(approvedCardNumber);
        waitSuccessMessage();
        var paymentWithInfo = SQLHelper.getPaymentStatus();
        assertEquals("APPROVED", paymentWithInfo);
    }

    @Test
    @DisplayName("Тест с валидными данными")
    void shouldBeCheckedWithValidData() {
        buyWithCard();
        var approvedCardNumber = DataHelper.getApprovedCardNumber();
        withCardNumber(approvedCardNumber);
        waitSuccessMessage();
        String paymentWithInfo = SQLHelper.getPaymentAmount();
        assertEquals("45000", paymentWithInfo);
    }

    @Test
    @DisplayName("Покупка с DECLINED картой")
    void shouldNotSellWithDeclinedCard() {
        buyWithCard();
        var declinedCardNumber = DataHelper.getDeclinedCardNumber();
        withCardNumber(declinedCardNumber);
        waitErrorMessage();
        var paymentWithInfo = SQLHelper.getPaymentStatus();
        assertEquals("DECLINED", paymentWithInfo);
    }

    @Test
    @DisplayName("Отправка пустой формы")
    void shouldNotSellWhenAllFieldsAreEmpty() {
        buyWithCard();
        emptyFields();
        waitErrorMessageAboutWrongFormat();
        waitErrorMessageBecauseOfEmptyField();
    }

    @Test
    @DisplayName("Поле CVC содержит одну цифру")
    void shouldNotSellWhenCardValidationCodeIsTwoDigitsShort() {
        buyWithCard();
        var cvc = "1";
        withCardValidationCode(cvc);
        waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("Поле CVC содержит две цифры")
    void shouldNotSellWhenCardValidationCodeIsOneDigitShort() {
        buyWithCard();
        var cvc = "12";
        withCardValidationCode(cvc);
        waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("Поле владелец содержит только Фамилию")
    void shouldNotSellWhenNameOfCardholderIsOnlyLastName() {
        buyWithCard();
        var nameOfCardHolder = DataHelper.getOnlyUsersLastName();
        withCardholder(nameOfCardHolder);
        waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("Поле владелец содержит только Имя")
    void shouldNotSellWhenNameOfCardholderIsOnlyFirstName() {
        buyWithCard();
        var nameOfCardHolder = DataHelper.getOnlyUsersFirstName();
        withCardholder(nameOfCardHolder);
        waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("Срок карты истек")
    void shouldNotSellWhenYearNumberIsLowerThanAllowed() {
        buyWithCard();
        var yearNumber = "22";
        withYear(yearNumber);
        waitErrorMessageWithDateOfExpiry();
    }

    @Test
    @DisplayName("Срок карты слишком большой")
    void shouldNotSellWhenYearNumberExceedsTheAllowed() {
        buyWithCard();
        var yearNumber = "99";
        withYear(yearNumber);
        waitErrorMessageAboutWrongDateOfExpiry();
    }

    @Test
    @DisplayName("В поле год ввести одну цифру")
    void shouldNotSellWhenYearNumberIsOneDigitalShort() {
        buyWithCard();
        var yearNumber = "2";
        withYear(yearNumber);
        waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("В поле год ввести 00")
    void shouldNotSellWhenYearNumberIsZeros() {
        buyWithCard();
        var yearNumber = "00";
        withYear(yearNumber);
        waitErrorMessageWithDateOfExpiry();
    }

    @Test
    @DisplayName("Номер карты состоит из 0")
    void shouldNotSellWhenCardNumberIsZeros() {
        buyWithCard();
        var number = "0000 0000 0000 0000";
        withCardNumber(number);
        waitErrorMessage();
    }

    @Test
    @DisplayName("В поле номер карты ввести невалидные данные карты")
    void shouldNotSellWhenCardNumberIsUnknown() {
        buyWithCard();
        var number = "4444 4444 4444 4443";
        withCardNumber(number);
        waitErrorMessage();
    }

    @Test
    @DisplayName("В поле номер карты ввести меньше 16 цифр")
    void shouldNotSellWhenCardNumberIsShort() {
        buyWithCard();
        var number = "4444 4444 4444 444";
        withCardNumber(number);
        waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("В поле месяц ввести 00")
    void shouldNotSellWhenMonthNumberIsZeros() {
        buyWithCard();
        var monthNumber = "00";
        withMonth(monthNumber);
        waitErrorMessageAboutWrongDateOfExpiry();
    }

    @Test
    @DisplayName("В поле месяц ввести одну цифру")
    void shouldNotSellWhenMonthNumberIsOneDigitShort() {
        buyWithCard();
        var monthNumber = "2";
        withMonth(monthNumber);
        waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("В поле месяц ввести несуществующий месяц")
    void shouldNotSellWhenMonthNumberExceedsTheAllowed() {
        buyWithCard();
        var monthNumber = "13";
        withMonth(monthNumber);
        waitErrorMessageAboutWrongDateOfExpiry();
    }

    @Test
    @DisplayName("В поле владелец ввести одну букву")
    void shouldNotSellWhenNameOfCardholderIsOnlyOneLetter() {
        buyWithCard();
        var nameOfCardHolder = "R";
        withCardholder(nameOfCardHolder);
        waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("В поле владелец ввести много букв")
    void shouldNotSellWhenNameOfCardholderHasLotsOfLetters() {
        buyWithCard();
        var nameOfCardHolder = "QWEJVNCMDKDFCVBGAJZNDTMDLMREW QWFTGRYFBSYRHFYTVCPQZMHYNJI ";
        withCardholder(nameOfCardHolder);
        waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("В поле владелец ввести данные строчными буквами")
    void shouldNotSellWhenNameOfCardholderInLowerCaseLetters() {
        buyWithCard();
        var nameOfCardHolder = DataHelper.getFullUsersNameInLowCaseLetters();
        withCardholder(nameOfCardHolder);
        waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("В поле владелец ввести данные прописными и строчными буквами")
    void shouldNotSellWhenNameOfCardholderInUpperCaseAndLowerCaseLetters() {
        buyWithCard();
        var nameOfCardHolder = DataHelper.getFullUsersNameInUpperCaseAndLowCaseLetters();
        withCardholder(nameOfCardHolder);
        waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("В поле владелец ввести данные на кириллице")
    void shouldNotSellWhenNameOfCardholderIsInRussian() {
        buyWithCard();
        var nameOfCardHolder = DataHelper.getFullUsersNameInRussian("ru");
        withCardholder(nameOfCardHolder);
        waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("В поле владелец ввести цифры")
    void shouldNotSellWhenNameOfCardholderInContainsNumbers() {
        buyWithCard();
        var nameOfCardHolder = "1234567890";
        withCardholder(nameOfCardHolder);
        waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("В поле владелец ввести спецсимволы")
    void shouldNotSellWhenNameOfCardholderInContainsSpecialCharacters() {
        buyWithCard();
        var nameOfCardHolder = "!@#$%^&*";
        withCardholder(nameOfCardHolder);
        waitErrorMessageAboutWrongFormat();
    }
}
