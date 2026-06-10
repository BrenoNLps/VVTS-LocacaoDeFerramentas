package br.ifsp.demo.ui.pages;

import br.ifsp.demo.ui.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MaintenancePage extends BasePage {
    private final By heading = By.xpath("//h1[text()='Manutenção']");

    public MaintenancePage(WebDriver driver) {
        super(driver);
    }

    public boolean isHeadingVisible() {
        return driver.findElement(heading).isDisplayed();
    }
}
