package pages.elements;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PagePaymentElements {
    public static SelenideElement heading = $(byText("Оплата по карте"));
    public static SelenideElement cardNumber = $("[placeholder= '0000 0000 0000 0000']");
    public static SelenideElement month = $("[placeholder= '08']");
    public static SelenideElement year = $("[placeholder= '22']");
    public static SelenideElement cardholder = $$(".input").find(Condition.exactText("Владелец")).$(".input__control");
    public static SelenideElement cardValidationCode = $("[placeholder= '999']");
    public static SelenideElement continueButton = $(byText("Продолжить"));
    public static SelenideElement successMessage = $(withText("Операция одобрена Банком"));
    public static SelenideElement errorMessage = $(withText("Банк отказал в проведении операции"));
    public static SelenideElement errorMessageAboutWrongFormat = $(byText("Неверный формат"));
    public static SelenideElement errorMessageAboutWrongDateOfExpiry = $(byText("Неверно указан срок действия карты"));
    public static SelenideElement errorMessageWithDateOfExpiry = $(byText("Истёк срок действия карты"));
    public static SelenideElement errorMessageBecauseOfEmptyField = $(byText("Поле обязательно для заполнения"));
}
