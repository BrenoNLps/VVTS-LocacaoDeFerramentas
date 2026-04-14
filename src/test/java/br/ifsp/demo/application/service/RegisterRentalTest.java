package br.ifsp.demo.application.service;

import br.ifsp.demo.annotation.TDD;
import br.ifsp.demo.annotation.UnitTest;
import br.ifsp.demo.domain.exception.EntityNotFoundException;
import br.ifsp.demo.domain.model.Customer;
import br.ifsp.demo.domain.model.ProgressivePrices;
import br.ifsp.demo.domain.model.Tool;
import br.ifsp.demo.domain.model.ToolStatus;
import br.ifsp.demo.domain.repository.CustomerRepository;
import br.ifsp.demo.domain.repository.RentalRepository;
import br.ifsp.demo.domain.repository.ToolRepository;
import br.ifsp.demo.exception.ToolUnavailableException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterRentalTest {

    private static final LocalDate TODAY = LocalDate.of(2026, 04, 14);

    @Mock
    private RentalRepository rentalRepository;

    @Mock
    private ToolRepository toolRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private RegisterRental registerRental;

    private Customer customer;

    private Tool buildTool(String id, ToolStatus status){
        return new Tool(id, "Hammer", status,
                new ProgressivePrices(BigDecimal.TEN, BigDecimal.valueOf(8), BigDecimal.valueOf(6)));
    }

    @BeforeEach
    void setUp(){
        customer = new Customer("customer-1", "Thom Yorke");
    }

    @Nested
    @DisplayName("Happy path")
    class HappyPath{

    @Test
    @UnitTest
    @TDD
    @DisplayName("Should create rental and mark Tool as Rented when customer exists and tool is available")
    void shouldCreateRentalAndMarkToolAsRentedWhenCustomerExistsAndToolIsAvailable() {
        var tool = buildTool("tool-1", ToolStatus.AVAILABLE);
        when(customerRepository.findById("customer-1")).thenReturn(customer);
        when(toolRepository.findById("tool-1")).thenReturn(tool);

        String rentalId = registerRental.execute(
                "customer-1", List.of("tool-1"), TODAY, "CASH_DEPOSIT", BigDecimal.TEN, null
        );

        assertThat(rentalId).isNotNull();
        assertThat(tool.getStatus()).isEqualTo(ToolStatus.RENTED);
        verify(rentalRepository).save(any());
    }

    @UnitTest
    @TDD
    @Test
    @DisplayName("should mard all tools as rented when multiple available tools are requested")
    void shouldMardAllToolsAsRentedWhenMultipleAvailableToolsAreRequested() {
        Tool tool1 = buildTool("tool-1", ToolStatus.AVAILABLE);
        var tool2 = buildTool("tool-2", ToolStatus.AVAILABLE);
        when(customerRepository.findById("customer-1")).thenReturn(customer);
        when(toolRepository.findById("tool-1")).thenReturn(tool1);
        when(toolRepository.findById("tool-2")).thenReturn(tool2);

        String rentalId = registerRental.execute(
                "customer-1",
                List.of("tool-1", "tool-2"),
                TODAY,
                "CASH_DEPOSIT",
                BigDecimal.TEN,
                null
        );

        assertThat(rentalId).isNotNull();
        assertThat(tool1.getStatus()).isEqualTo(ToolStatus.RENTED);
        assertThat(tool2.getStatus()).isEqualTo(ToolStatus.RENTED);
        verify(rentalRepository).save(any());
    }
    }

    @Nested
    @DisplayName("Business rule errors")
    class BusinessRuleErrors {

        @Test
        @UnitTest
        @TDD
        @DisplayName("should throw entity not found exception when customer does not exist")
        void shouldThrowEntityNotFoundExceptionWhenCustomerDoesNotExist() {
            when(customerRepository.findById("customer-1")).thenReturn(null);
            assertThatThrownBy(() -> registerRental.execute(
                    "customer-1", List.of("tool-1"), TODAY, "CASH_DEPOSIT", BigDecimal.TEN, null))
                    .isInstanceOf(EntityNotFoundException.class);
        }

        @Test
        @UnitTest
        @TDD
        @DisplayName("should throw EntityNotFoundException when tool does not exist")
        void shouldThrowEntityNotFoundExceptionWhenToolDoesNotExist() {
            when(customerRepository.findById("customer-1")).thenReturn(customer);
            when(toolRepository.findById("tool-1")).thenReturn(null);

            assertThatThrownBy(() -> registerRental.execute(
                    "customer-1", List.of("tool-1"), TODAY, "CASH_DEPOSIT", BigDecimal.TEN, null))
                    .isInstanceOf(EntityNotFoundException.class);
        }

        @ParameterizedTest
        @EnumSource(value = ToolStatus.class, names = {"RENTED", "MAINTENANCE"})
        @UnitTest
        @TDD
        @DisplayName("Should throw ToolUnavailableException when tool is not available")
        void shouldThrowToolUnavailableExceptionWhenToolIsNotAvailable(ToolStatus unavailableStatus) {
            Tool tool = buildTool("tool-1", unavailableStatus);
            when(customerRepository.findById("customer-1")).thenReturn(customer);
            when(toolRepository.findById("tool-1")).thenReturn(tool);

            assertThatThrownBy(() -> registerRental.execute(
                    "customer-1", List.of("tool-1"), TODAY, "CASH_DEPOSIT", BigDecimal.TEN, null))
                    .isInstanceOf(ToolUnavailableException.class);
        }

        @ParameterizedTest
        @EnumSource(value = ToolStatus.class, names = {"RENTED", "MAINTENANCE"})
        @UnitTest
        @TDD
        @DisplayName("Should Throw ToolUnavailableException and leave all tools unchanged when one tool is unavailable in a multi-tool request")
        void shouldThrowToolUnavailableExceptionAndLeaveAllToolsUnchangedWhenOneToolIsUnavailableInAMultiToolRequest(
                ToolStatus unavailableStatus
        ) {
            Tool tool1 = buildTool("tool-1", ToolStatus.AVAILABLE);
            Tool tool2 = buildTool("tool-2", unavailableStatus);
            when(customerRepository.findById("customer-1")).thenReturn(customer);
            when(toolRepository.findById("tool-1")).thenReturn(tool1);
            when(toolRepository.findById("tool-2")).thenReturn(tool2);

            assertThatThrownBy(() -> registerRental.execute(
                    "customer-1", List.of("tool-1", "tool-2"), TODAY, "CASH_DEPOSIT", BigDecimal.TEN, null
            ))
                    .isInstanceOf(ToolUnavailableException.class);

            assertThat(tool1.getStatus()).isEqualTo(ToolStatus.AVAILABLE);
        }
    }
}