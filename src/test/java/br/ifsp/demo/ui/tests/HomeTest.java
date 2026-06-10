package br.ifsp.demo.ui.tests;

import br.ifsp.demo.ui.base.BaseUiTest;
import br.ifsp.demo.ui.helpers.UiTestDataFactory;
import br.ifsp.demo.ui.pages.HomePage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("UiTest")
public class HomeTest extends BaseUiTest {
    private HomePage homePage;

    @BeforeEach
    public void setup() {
        String email = UiTestDataFactory.createEmail();
        String password = UiTestDataFactory.createPassword();
        registerUser("Admin", "User", email, password);
        login(email, password);
        homePage = new HomePage(driver);
    }

    @Test
    @Tag("UiTest")
    @DisplayName("Home page should render available tools and simulation controls")
    public void shouldRenderHomePageWithAvailableToolsSection() {
        assertThat(homePage.isHeadingVisible()).isTrue();
        assertThat(homePage.isEndDateInputVisible()).isTrue();
        assertThat(homePage.isSimulateButtonVisible()).isTrue();
        assertThat(homePage.isGoToRentalsButtonVisible()).isTrue();
    }
}
