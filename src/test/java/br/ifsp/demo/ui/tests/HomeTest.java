package br.ifsp.demo.ui.tests;

import br.ifsp.demo.ui.base.BaseUiTest;
import br.ifsp.demo.ui.helpers.UiTestDataFactory;
import br.ifsp.demo.ui.pages.HomePage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
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
        
        createTool("Furadeira", 10.0, 50.0, 150.0);
        
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

    @Test
    @Tag("UiTest")
    @DisplayName("Selecting a tool should toggle its selected state")
    public void shouldToggleToolSelectionInTheTable() {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(d -> homePage.isToolTableVisible());
        
        homePage.clickFirstToolRow();
        assertThat(homePage.isFirstToolRowSelected()).isTrue();
        
        homePage.clickFirstToolRow();
        assertThat(homePage.isFirstToolRowSelected()).isFalse();
    }

    @Test
    @Tag("UiTest")
    @DisplayName("Simulation should require at least one selected tool")
    public void shouldShowValidationWhenSimulatingWithoutSelectingAnyTool() {
        homePage.setEndDate("2026-12-31");
        homePage.clickSimulate();
        
        assertThat(homePage.getErrorMessage()).isEqualTo("Selecione ao menos uma ferramenta.");
        assertThat(homePage.isSimulationValueVisible()).isFalse();
    }

    @Test
    @Tag("UiTest")
    @DisplayName("Simulation should require a return date")
    public void shouldShowValidationWhenSimulatingWithoutReturnDate() {
        homePage.clickFirstToolRow();
        homePage.clickSimulate();
        
        assertThat(homePage.getErrorMessage()).isEqualTo("Informe a data de devolução.");
        assertThat(homePage.isSimulationValueVisible()).isFalse();
    }
}
