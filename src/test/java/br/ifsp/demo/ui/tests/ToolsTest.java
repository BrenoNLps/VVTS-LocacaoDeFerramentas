package br.ifsp.demo.ui.tests;

import br.ifsp.demo.ui.base.BaseUiTest;
import br.ifsp.demo.ui.helpers.UiTestDataFactory;
import br.ifsp.demo.ui.pages.ToolsPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("UiTest")
public class ToolsTest extends BaseUiTest {

    @Test
    @Tag("UiTest")
    @DisplayName("Tools page should render the form and table")
    public void shouldRenderToolsFormAndToolsTable() {
        String email = UiTestDataFactory.createEmail();
        String password = UiTestDataFactory.createPassword();
        registerUser("Admin", "User", email, password);
        login(email, password);

        driver.get("http://localhost:5173/tools");
        
        ToolsPage toolsPage = new ToolsPage(driver);

        assertThat(toolsPage.isHeadingVisible()).isTrue();
        assertThat(toolsPage.isNameInputVisible()).isTrue();
        assertThat(toolsPage.isDailyRateInputVisible()).isTrue();
        assertThat(toolsPage.isWeeklyRateInputVisible()).isTrue();
        assertThat(toolsPage.isMonthlyRateInputVisible()).isTrue();
        assertThat(toolsPage.isRegisterButtonVisible()).isTrue();
        assertThat(toolsPage.areTableHeadersVisible()).isTrue();
    }
}
