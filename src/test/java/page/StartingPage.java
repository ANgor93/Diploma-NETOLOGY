package page;

import io.qameta.allure.Step;

import static elements.StartingPageElements.buyWithCardButton;
import static elements.StartingPageElements.buyWithCreditButton;

public class StartingPage {


    public static PagePayment buyWithCard() {
        buyWithCardButton.click();
        return new PagePayment();
    }

    public static PagePaymentOnCredit buyWithCredit() {
        buyWithCreditButton.click();
        return new PagePaymentOnCredit();
    }
}
