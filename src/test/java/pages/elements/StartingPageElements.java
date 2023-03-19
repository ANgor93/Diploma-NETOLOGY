package pages.elements;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class StartingPageElements {

    public static SelenideElement buyWithCardButton = $(byText("Купить"));
    public static SelenideElement buyWithCreditButton = $(byText("Купить в кредит"));
}
