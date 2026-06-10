package br.ifsp.demo.ui.pages;

import br.ifsp.demo.ui.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RentalPage extends BasePage {
    private final By heading = By.xpath("//h1[text()='Locações']");
    private final By newRentalTab = By.xpath("//button[text()='Nova locação']");
    private final By activeRentalsTab = By.xpath("//button[contains(text(), 'Locações ativas')]");
    private final By clientLabel = By.xpath("//label[text()='Cliente']");
    private final By guaranteeLabel = By.xpath("//label[text()='Garantia']");
    private final By confirmButton = By.xpath("//button[text()='Confirmar locação']");

    public RentalPage(WebDriver driver) {
        super(driver);
    }

    public boolean isHeadingVisible() {
        return driver.findElement(heading).isDisplayed();
    }

    public boolean isNewRentalTabVisible() {
        return driver.findElement(newRentalTab).isDisplayed();
    }

    public boolean isActiveRentalsTabVisible() {
        return driver.findElement(activeRentalsTab).isDisplayed();
    }

    public boolean isClientLabelVisible() {
        return driver.findElement(clientLabel).isDisplayed();
    }

    public boolean isGuaranteeLabelVisible() {
        return driver.findElement(guaranteeLabel).isDisplayed();
    }

    public boolean isConfirmButtonVisible() {
        return driver.findElement(confirmButton).isDisplayed();
    }
}
