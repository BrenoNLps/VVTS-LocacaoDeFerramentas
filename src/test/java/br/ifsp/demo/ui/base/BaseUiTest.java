package br.ifsp.demo.ui.base;

import br.ifsp.demo.ui.pages.LoginPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BaseUiTest {

    protected WebDriver driver;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    protected void registerUser(String name, String lastname, String email, String password) {
        driver.get("http://localhost:5173/register");
        driver.findElement(By.xpath("//input[@name='name']")).sendKeys(name);
        driver.findElement(By.xpath("//input[@name='lastname']")).sendKeys(lastname);
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys(email);
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys(password);
        driver.findElement(By.xpath("//button[text()='Cadastrar']")).click();
        
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(d -> d.getCurrentUrl().contains("/"));
    }

    protected void login(String email, String password) {
        driver.get("http://localhost:5173/");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.fillEmail(email);
        loginPage.fillPassword(password);
        loginPage.clickLogin();
        
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(d -> d.getCurrentUrl().contains("/home"));
    }
}
