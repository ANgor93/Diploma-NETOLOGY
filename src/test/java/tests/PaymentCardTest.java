package tests;

import data.SQLHelper;
import data.DataHelper;
import pages.StartingPage;
import tests.hooks.BaseTest;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class PaymentCardTest extends BaseTest {


    @Test
    @DisplayName("Покупка с APPROVED картой")
    void shouldBuySuccessfullyWithApprovedCard() {
        var startingPage = new StartingPage();
        var buyWithCard = startingPage.buyWithCard();
        var approvedCardNumber = DataHelper.getApprovedCardNumber();
        buyWithCard.withCardNumber(approvedCardNumber);
        buyWithCard.waitSuccessMessage();
        var paymentWithInfo = SQLHelper.getLatestPaymentRequest().getStatus();
        assertEquals("APPROVED", paymentWithInfo);
    }

    @Test
    @DisplayName("Тест с валидными данными")
    void shouldBeCheckedWithValidData() {
        var startingPage = new StartingPage();
        var buyWithCard = startingPage.buyWithCard();
        var approvedCardNumber = DataHelper.getApprovedCardNumber();
        buyWithCard.withCardNumber(approvedCardNumber);
        buyWithCard.waitSuccessMessage();
        var paymentWithInfo = SQLHelper.getLatestPaymentRequest().getAmount();
        assertEquals("45000", paymentWithInfo);
    }

    @Test
    @DisplayName("Покупка с DECLINED картой")
    void shouldNotSellWithDeclinedCard() {
        var startingPage = new StartingPage();
        var buyWithCard = startingPage.buyWithCard();
        var declinedCardNumber = DataHelper.getDeclinedCardNumber();
        buyWithCard.withCardNumber(declinedCardNumber);
        buyWithCard.waitErrorMessage();
        var paymentWithInfo = SQLHelper.getLatestPaymentRequest().getStatus();
        assertEquals("DECLINED", paymentWithInfo);
    }

    @Test
    @DisplayName("Отправка пустой формы")
    void shouldNotSellWhenAllFieldsAreEmpty() {
        var startingPage = new StartingPage();
        var buyWithCard = startingPage.buyWithCard();
        buyWithCard.emptyFields();
        buyWithCard.waitErrorMessageAboutWrongFormat();
        buyWithCard.waitErrorMessageAboutWrongFormat();
        buyWithCard.waitErrorMessageAboutWrongFormat();
        buyWithCard.waitErrorMessageBecauseOfEmptyField();
        buyWithCard.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("Поле CVC содержит одну цифру")
    void shouldNotSellWhenCardValidationCodeIsTwoDigitsShort() {
        var startingPage = new StartingPage();
        var buyWithCard = startingPage.buyWithCard();
        var cvc = "1";
        buyWithCard.withCardValidationCode(cvc);
        buyWithCard.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("Поле CVC содержит две цифры")
    void shouldNotSellWhenCardValidationCodeIsOneDigitShort() {
        var startingPage = new StartingPage();
        var buyWithCard = startingPage.buyWithCard();
        var cvc = "12";
        buyWithCard.withCardValidationCode(cvc);
        buyWithCard.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("Поле владелец содержит только Фамилию")
    void shouldNotSellWhenNameOfCardholderIsOnlyLastName() {
        var startingPage = new StartingPage();
        var buyWithCard = startingPage.buyWithCard();
        var nameOfCardHolder = DataHelper.getOnlyUsersLastName();
        buyWithCard.withCardholder(nameOfCardHolder);
        buyWithCard.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("Поле владелец содержит только Имя")
    void shouldNotSellWhenNameOfCardholderIsOnlyFirstName() {
        var startingPage = new StartingPage();
        var buyWithCard = startingPage.buyWithCard();
        var nameOfCardHolder = DataHelper.getOnlyUsersFirstName();
        buyWithCard.withCardholder(nameOfCardHolder);
        buyWithCard.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("Срок карты истек")
    void shouldNotSellWhenYearNumberIsLowerThanAllowed() {
        var startingPage = new StartingPage();
        var buyWithCard = startingPage.buyWithCard();
        var yearNumber = "22";
        buyWithCard.withYear(yearNumber);
        buyWithCard.waitErrorMessageWithDateOfExpiry();
    }

    @Test
    @DisplayName("Срок карты слишком большой")
    void shouldNotSellWhenYearNumberExceedsTheAllowed() {
        var startingPage = new StartingPage();
        var buyWithCard = startingPage.buyWithCard();
        var yearNumber = "99";
        buyWithCard.withYear(yearNumber);
        buyWithCard.waitErrorMessageAboutWrongDateOfExpiry();
    }

    @Test
    @DisplayName("В поле год ввести одну цифру")
    void shouldNotSellWhenYearNumberIsOneDigitalShort() {
        var startingPage = new StartingPage();
        var buyWithCard = startingPage.buyWithCard();
        var yearNumber = "2";
        buyWithCard.withYear(yearNumber);
        buyWithCard.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("В поле год ввести 00")
    void shouldNotSellWhenYearNumberIsZeros() {
        var startingPage = new StartingPage();
        var buyWithCardPage = startingPage.buyWithCard();
        var yearNumber = "00";
        buyWithCardPage.withYear(yearNumber);
        buyWithCardPage.waitErrorMessageWithDateOfExpiry();
    }

    @Test
    @DisplayName("Номер карты состоит из 0")
    void shouldNotSellWhenCardNumberIsZeros() {
        var startingPage = new StartingPage();
        var buyWithCardPage = startingPage.buyWithCard();
        var number = "0000 0000 0000 0000";
        buyWithCardPage.withCardNumber(number);
        buyWithCardPage.waitErrorMessage();
    }

    @Test
    @DisplayName("В поле номер карты ввести невалидные данные карты")
    void shouldNotSellWhenCardNumberIsUnknown() {
        var startingPage = new StartingPage();
        var buyWithCardPage = startingPage.buyWithCard();
        var number = "4444 4444 4444 4443";
        buyWithCardPage.withCardNumber(number);
        buyWithCardPage.waitErrorMessage();
    }

    @Test
    @DisplayName("В поле номер карты ввести меньше 16 цифр")
    void shouldNotSellWhenCardNumberIsShort() {
        var startingPage = new StartingPage();
        var buyWithCardPage = startingPage.buyWithCard();
        var number = "4444 4444 4444 444";
        buyWithCardPage.withCardNumber(number);
        buyWithCardPage.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("В поле месяц ввести 00")
    void shouldNotSellWhenMonthNumberIsZeros() {
        var startingPage = new StartingPage();
        var buyWithCardPage = startingPage.buyWithCard();
        var monthNumber = "00";
        buyWithCardPage.withMonth(monthNumber);
        buyWithCardPage.waitErrorMessageAboutWrongDateOfExpiry();
    }

    @Test
    @DisplayName("В поле месяц ввести одну цифру")
    void shouldNotSellWhenMonthNumberIsOneDigitShort() {
        var startingPage = new StartingPage();
        var buyWithCardPage = startingPage.buyWithCard();
        var monthNumber = "2";
        buyWithCardPage.withMonth(monthNumber);
        buyWithCardPage.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("В поле месяц ввести несуществующий месяц")
    void shouldNotSellWhenMonthNumberExceedsTheAllowed() {
        var startingPage = new StartingPage();
        var buyWithCardPage = startingPage.buyWithCard();
        var monthNumber = "13";
        buyWithCardPage.withMonth(monthNumber);
        buyWithCardPage.waitErrorMessageAboutWrongDateOfExpiry();
    }

    @Test
    @DisplayName("В поле владелец ввести одну букву")
    void shouldNotSellWhenNameOfCardholderIsOnlyOneLetter() {
        var startingPage = new StartingPage();
        var buyWithCardPage = startingPage.buyWithCard();
        var nameOfCardHolder = "R";
        buyWithCardPage.withCardholder(nameOfCardHolder);
        buyWithCardPage.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("В поле владелец ввести много букв")
    void shouldNotSellWhenNameOfCardholderHasLotsOfLetters() {
        var startingPage = new StartingPage();
        var buyWithCardPage = startingPage.buyWithCard();
        var nameOfCardHolder = "QWEJVNCMDKDFCVBGAJZNDTMDLMREW QWFTGRYFBSYRHFYTVCPQZMHYNJI ";
        buyWithCardPage.withCardholder(nameOfCardHolder);
        buyWithCardPage.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("В поле владелец ввести данные строчными буквами")
    void shouldNotSellWhenNameOfCardholderInLowerCaseLetters() {
        var startingPage = new StartingPage();
        var buyWithCardPage = startingPage.buyWithCard();
        var nameOfCardHolder = DataHelper.getFullUsersNameInLowCaseLetters();
        buyWithCardPage.withCardholder(nameOfCardHolder);
        buyWithCardPage.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("В поле владелец ввести данные прописными и строчными буквами")
    void shouldNotSellWhenNameOfCardholderInUpperCaseAndLowerCaseLetters() {
        var startingPage = new StartingPage();
        var buyWithCardPage = startingPage.buyWithCard();
        var nameOfCardHolder = DataHelper.getFullUsersNameInUpperCaseAndLowCaseLetters();
        buyWithCardPage.withCardholder(nameOfCardHolder);
        buyWithCardPage.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("В поле владелец ввести данные на кириллице")
    void shouldNotSellWhenNameOfCardholderIsInRussian() {
        var startingPage = new StartingPage();
        var buyWithCardPage = startingPage.buyWithCard();
        var nameOfCardHolder = DataHelper.getFullUsersNameInRussian("ru");
        buyWithCardPage.withCardholder(nameOfCardHolder);
        buyWithCardPage.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("В поле владелец ввести цифры")
    void shouldNotSellWhenNameOfCardholderInContainsNumbers() {
        var startingPage = new StartingPage();
        var buyWithCardPage = startingPage.buyWithCard();
        var nameOfCardHolder = "1234567890";
        buyWithCardPage.withCardholder(nameOfCardHolder);
        buyWithCardPage.waitErrorMessageAboutWrongFormat();
    }

    @Test
    @DisplayName("В поле владелец ввести спецсимволы")
    void shouldNotSellWhenNameOfCardholderInContainsSpecialCharacters() {
        var startingPage = new StartingPage();
        var buyWithCardPage = startingPage.buyWithCard();
        var nameOfCardHolder = "!@#$%^&*";
        buyWithCardPage.withCardholder(nameOfCardHolder);
        buyWithCardPage.waitErrorMessageAboutWrongFormat();
    }
}
