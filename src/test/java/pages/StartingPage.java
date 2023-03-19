package pages;


import static pages.elements.StartingPageElements.buyWithCardButton;
import static pages.elements.StartingPageElements.buyWithCreditButton;

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
