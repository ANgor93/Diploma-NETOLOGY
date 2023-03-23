package tests;

import data.DataHelper;
import data.SQLHelper;
import pages.StartingPage;
import tests.hooks.BaseTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;


public class PayOnCreditTest extends BaseTest {


    @Test
    @DisplayName("Покупка с APPROVED картой")
    void shouldBuySuccessfullyWithApprovedCard() {
        var startingPage = new StartingPage();
        var buyWithCreditPage = startingPage.buyWithCredit();
        var approvedCardNumber = DataHelper.getApprovedCardNumber();
        buyWithCreditPage.withCardNumber(approvedCardNumber);
        buyWithCreditPage.waitSuccessMessage();
        var paymentWithInfo = SQLHelper.getLatestCreditRequest().getStatus();
        assertEquals("APPROVED", paymentWithInfo);
    }

    @Test
    @DisplayName("Тест с валидными данными")
    void shouldBeCheckedWithValidData() {
        var startingPage = new StartingPage();
        var buyWithCreditPage = startingPage.buyWithCredit();
        var approvedCardNumber = DataHelper.getApprovedCardNumber();
        buyWithCreditPage.withCardNumber(approvedCardNumber);
        buyWithCreditPage.waitSuccessMessage();
        var paymentWithInfo = SQLHelper.getLatestCreditRequest().getAmount();
        assertEquals("45000", paymentWithInfo);
    }

    @Test
    @DisplayName("Покупка с DECLINED картой")
    void shouldNotSellWithDeclinedCard() {
        var startingPage = new StartingPage();
        var buyWithCreditPage = startingPage.buyWithCredit();
        var declinedCardNumber = DataHelper.getDeclinedCardNumber();
        buyWithCreditPage.withCardNumber(declinedCardNumber);
        buyWithCreditPage.waitErrorMessage();
        var paymentWithInfo = SQLHelper.getLatestCreditRequest().getStatus();
        assertEquals("APPROVED", paymentWithInfo);
    }

    @Test
    @DisplayName("Отправка пустой формы")
    void shouldNotSellWhenAllFieldsAreEmpty() {
        var startingPage = new StartingPage();
        var buyWithCreditPage = startingPage.buyWithCredit();
        buyWithCreditPage.emptyFields();
        buyWithCreditPage.waitErrorMessageAboutWrongFormat();
        buyWithCreditPage.waitErrorMessageAboutWrongFormat();
        buyWithCreditPage.waitErrorMessageAboutWrongFormat();
        buyWithCreditPage.waitErrorMessageBecauseOfEmptyField();
        buyWithCreditPage.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("Поле CVC содержит одну цифру")
    void shouldNotSellWhenCardValidationCodeIsTwoDigitsShort() {
        var startingPage = new StartingPage();
        var buyWithCreditPage = startingPage.buyWithCredit();
        var cvc = "1";
        buyWithCreditPage.withCardValidationCode(cvc);
        buyWithCreditPage.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("Поле CVC содержит две цифры")
    void shouldNotSellWhenCardValidationCodeIsOneDigitShort() {
        var startingPage = new StartingPage();
        var buyWithCreditPage = startingPage.buyWithCredit();
        var cvc = "12";
        buyWithCreditPage.withCardValidationCode(cvc);
        buyWithCreditPage.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("Поле владелец содержит только Фамилию")
    void shouldNotSellWhenNameOfCardholderIsOnlyLastName() {
        var startingPage = new StartingPage();
        var buyWithCreditPage = startingPage.buyWithCredit();
        var nameOfCardHolder = DataHelper.getOnlyUsersLastName();
        buyWithCreditPage.withCardholder(nameOfCardHolder);
        buyWithCreditPage.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("Поле владелец содержит только Имя")
    void shouldNotSellWhenNameOfCardholderIsOnlyFirstName() {
        var startingPage = new StartingPage();
        var buyWithCreditPage = startingPage.buyWithCredit();
        var nameOfCardHolder = DataHelper.getOnlyUsersFirstName();
        buyWithCreditPage.withCardholder(nameOfCardHolder);
        buyWithCreditPage.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("Срок карты истек")
    void shouldNotSellWhenYearNumberIsLowerThanAllowed() {
        var startingPage = new StartingPage();
        var buyWithCreditPage = startingPage.buyWithCredit();
        var yearNumber = "22";
        buyWithCreditPage.withYear(yearNumber);
        buyWithCreditPage.waitErrorMessageWithDateOfExpiry();
    }

    @Test
    @DisplayName("Срок карты слишком большой")
    void shouldNotSellWhenYearNumberExceedsTheAllowed() {
        var startingPage = new StartingPage();
        var buyWithCreditPage = startingPage.buyWithCredit();
        var yearNumber = "99";
        buyWithCreditPage.withYear(yearNumber);
        buyWithCreditPage.waitErrorMessageAboutWrongDateOfExpiry();
    }

    @Test
    @DisplayName("В поле год ввести одну цифру")
    void shouldNotSellWhenYearNumberIsOneDigitalShort() {
        var startingPage = new StartingPage();
        var buyWithCreditPage = startingPage.buyWithCredit();
        var yearNumber = "2";
        buyWithCreditPage.withYear(yearNumber);
        buyWithCreditPage.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("В поле год ввести 00")
    void shouldNotSellWhenYearNumberIsZeros() {
        var startingPage = new StartingPage();
        var buyWithCreditPage = startingPage.buyWithCredit();
        var yearNumber = "00";
        buyWithCreditPage.withYear(yearNumber);
        buyWithCreditPage.waitErrorMessageWithDateOfExpiry();
    }

    @Test
    @DisplayName("Номер карты состоит из 0")
    void shouldNotSellWhenCardNumberIsZeros() {
        var startingPage = new StartingPage();
        var buyWithCreditPage = startingPage.buyWithCredit();
        var number = "0000 0000 0000 0000";
        buyWithCreditPage.withCardNumber(number);
        buyWithCreditPage.waitErrorMessage();
    }

    @Test
    @DisplayName("В поле номер карты ввести невалидные данные карты")
    void shouldNotSellWhenCardNumberIsUnknown() {
        var startingPage = new StartingPage();
        var buyWithCreditPage = startingPage.buyWithCredit();
        var number = "4444 4444 4444 4443";
        buyWithCreditPage.withCardNumber(number);
        buyWithCreditPage.waitErrorMessage();
    }

    @Test
    @DisplayName("В поле номер карты ввести меньше 16 цифр")
    void shouldNotSellWhenCardNumberIsShort() {
        var startingPage = new StartingPage();
        var buyWithCreditPage = startingPage.buyWithCredit();
        var number = "4444 4444 4444 444";
        buyWithCreditPage.withCardNumber(number);
        buyWithCreditPage.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("В поле месяц ввести 00")
    void shouldNotSellWhenMonthNumberIsZeros() {
        var startingPage = new StartingPage();
        var buyWithCreditPage = startingPage.buyWithCredit();
        var monthNumber = "00";
        buyWithCreditPage.withMonth(monthNumber);
        buyWithCreditPage.waitErrorMessageAboutWrongDateOfExpiry();
    }

    @Test
    @DisplayName("В поле месяц ввести одну цифру")
    void shouldNotSellWhenMonthNumberIsOneDigitShort() {
        var startingPage = new StartingPage();
        var buyWithCreditPage = startingPage.buyWithCredit();
        var monthNumber = "2";
        buyWithCreditPage.withMonth(monthNumber);
        buyWithCreditPage.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("В поле месяц ввести несуществующий месяц")
    void shouldNotSellWhenMonthNumberExceedsTheAllowed() {
        var startingPage = new StartingPage();
        var buyWithCreditPage = startingPage.buyWithCredit();
        var monthNumber = "13";
        buyWithCreditPage.withMonth(monthNumber);
        buyWithCreditPage.waitErrorMessageAboutWrongDateOfExpiry();
    }

    @Test
    @DisplayName("В поле владелец ввести одну букву")
    void shouldNotSellWhenNameOfCardholderIsOnlyOneLetter() {
        var startingPage = new StartingPage();
        var buyWithCreditPage = startingPage.buyWithCredit();
        var nameOfCardHolder = "R";
        buyWithCreditPage.withCardholder(nameOfCardHolder);
        buyWithCreditPage.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("В поле владелец ввести много букв")
    void shouldNotSellWhenNameOfCardholderHasLotsOfLetters() {
        var startingPage = new StartingPage();
        var buyWithCreditPage = startingPage.buyWithCredit();
        var nameOfCardHolder = "QWEJVNCMDKDFCVBGAJZNDTMDLMREW QWFTGRYFBSYRHFYTVCPQZMHYNJI ";
        buyWithCreditPage.withCardholder(nameOfCardHolder);
        buyWithCreditPage.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("В поле владелец ввести данные строчными буквами")
    void shouldNotSellWhenNameOfCardholderInLowerCaseLetters() {
        var startingPage = new StartingPage();
        var buyWithCreditPage = startingPage.buyWithCredit();
        var nameOfCardHolder = DataHelper.getFullUsersNameInLowCaseLetters();
        buyWithCreditPage.withCardholder(nameOfCardHolder);
        buyWithCreditPage.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("В поле владелец ввести данные прописными и строчными буквами")
    void shouldNotSellWhenNameOfCardholderInUpperCaseAndLowerCaseLetters() {
        var startingPage = new StartingPage();
        var buyWithCreditPage = startingPage.buyWithCredit();
        var nameOfCardHolder = DataHelper.getFullUsersNameInUpperCaseAndLowCaseLetters();
        buyWithCreditPage.withCardholder(nameOfCardHolder);
        buyWithCreditPage.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("В поле владелец ввести данные на кириллице")
    void shouldNotSellWhenNameOfCardholderIsInRussian() {
        var startingPage = new StartingPage();
        var buyWithCreditPage = startingPage.buyWithCredit();
        var nameOfCardHolder = DataHelper.getFullUsersNameInRussian("ru");
        buyWithCreditPage.withCardholder(nameOfCardHolder);
        buyWithCreditPage.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("В поле владелец ввести цифры")
    void shouldNotSellWhenNameOfCardholderInContainsNumbers() {
        var startingPage = new StartingPage();
        var buyWithCreditPage = startingPage.buyWithCredit();
        var nameOfCardHolder = "1234567890";
        buyWithCreditPage.withCardholder(nameOfCardHolder);
        buyWithCreditPage.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("В поле владелец ввести спецсимволы")
    void shouldNotSellWhenNameOfCardholderInContainsSpecialCharacters() {
        var startingPage = new StartingPage();
        var buyWithCreditPage = startingPage.buyWithCredit();
        var nameOfCardHolder = "!@#$%^&*";
        buyWithCreditPage.withCardholder(nameOfCardHolder);
        buyWithCreditPage.waitErrorMessageAboutWrongFormat();
    }
}