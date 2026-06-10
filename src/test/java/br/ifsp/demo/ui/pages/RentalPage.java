package br.ifsp.demo.ui.pages;

import br.ifsp.demo.ui.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

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

    public void clickFirstToolRow() {
        driver.findElement(By.className("tool-row")).click();
    }

    public void searchCustomer(String name) {
        driver.findElement(By.xpath("//input[@placeholder='Buscar pelo nome...']")).sendKeys(name);
    }

    public void selectCustomerFromDropdown(String name) {
        By item = By.xpath("//li[contains(@class, 'customer-dropdown__item') and text()='" + name + "']");
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(d -> d.findElement(item).isDisplayed());
        driver.findElement(item).click();
    }

    public void selectGuarantee(String label) {
        driver.findElement(By.xpath("//label[contains(@class, 'radio-label') and contains(text(), '" + label + "')]")).click();
    }

    public void clickConfirm() {
        driver.findElement(confirmButton).click();
    }

    public String getSuccessMessage() {
        return driver.findElement(By.className("success-text")).getText();
    }

    public boolean isSuccessMessageVisible() {
        return !driver.findElements(By.className("success-text")).isEmpty();
    }

    public String getErrorMessage() {
        return driver.findElement(By.className("error-text")).getText();
    }

    public boolean isErrorMessageVisible() {
        return !driver.findElements(By.className("error-text")).isEmpty();
    }

    public boolean areToolsSelected() {
        return !driver.findElements(By.className("tool-row--selected")).isEmpty();
    }

    public boolean isToolTableVisible() {
        return !driver.findElements(By.cssSelector("tbody tr")).isEmpty();
    }
}
