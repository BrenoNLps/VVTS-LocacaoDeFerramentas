package br.ifsp.demo.ui.pages;

import br.ifsp.demo.ui.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {
    private final By heading = By.xpath("//h1[text()='Ferramentas disponíveis']");
    private final By endDateLabel = By.xpath("//label[text()='Data de devolução']");
    private final By endDateInput = By.xpath("//input[@type='date']");
    private final By simulateButton = By.xpath("//button[text()='Simular valor']");
    private final By goToRentalsButton = By.xpath("//button[text()='Ir para locações']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public boolean isHeadingVisible() {
        return driver.findElement(heading).isDisplayed();
    }

    public boolean isEndDateInputVisible() {
        return driver.findElement(endDateLabel).isDisplayed() && driver.findElement(endDateInput).isDisplayed();
    }

    public boolean isSimulateButtonVisible() {
        return driver.findElement(simulateButton).isDisplayed();
    }

    public boolean isGoToRentalsButtonVisible() {
        return driver.findElement(goToRentalsButton).isDisplayed();
    }
}
