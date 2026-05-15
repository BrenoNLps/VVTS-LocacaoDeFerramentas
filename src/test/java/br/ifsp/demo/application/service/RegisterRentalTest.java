package br.ifsp.demo.application.service;

import br.ifsp.demo.annotation.Functional;
import br.ifsp.demo.annotation.TDD;
import br.ifsp.demo.annotation.UnitTest;
import br.ifsp.demo.domain.exception.EntityNotFoundException;
import br.ifsp.demo.domain.exception.InvalidArgumentException;
import br.ifsp.demo.domain.model.Customer;
import br.ifsp.demo.domain.model.GuaranteeType;
import br.ifsp.demo.domain.model.ProgressivePrices;
import br.ifsp.demo.domain.model.Tool;
import br.ifsp.demo.domain.model.ToolStatus;
import br.ifsp.demo.domain.repository.CustomerRepository;
import br.ifsp.demo.domain.repository.RentalRepository;
import br.ifsp.demo.domain.repository.ToolRepository;
import br.ifsp.demo.domain.exception.InvalidDateException;
import br.ifsp.demo.domain.exception.MissingGuaranteeException;
import br.ifsp.demo.domain.exception.ToolUnavailableException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterRentalTest {

    private static final LocalDate TODAY = LocalDate.now();

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
                "customer-1", List.of("tool-1"), TODAY, GuaranteeType.CASH_DEPOSIT
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
                GuaranteeType.CASH_DEPOSIT
        );

        assertThat(rentalId).isNotNull();
        assertThat(tool1.getStatus()).isEqualTo(ToolStatus.RENTED);
        assertThat(tool2.getStatus()).isEqualTo(ToolStatus.RENTED);
        verify(rentalRepository).save(any());
    }
    }

    @Nested
    @DisplayName("Input validation errors")
    class InputValidationErrors{

        static Stream<Arguments> invalidDateProvider(){
            return Stream.of(
                    Arguments.of(LocalDate.now().minusDays(1)), //past date
                            Arguments.of(LocalDate.now().plusDays(1)) //future date
            );
        }

        @ParameterizedTest
        @MethodSource("invalidDateProvider")
        @UnitTest
        @TDD
        @DisplayName("Should throw invalid date Exception  when start date is not today")
        void shouldThrowInvalidDateExceptionWhenStartDateIsNotToday(LocalDate invalidDate) {
            assertThatThrownBy(() -> registerRental.execute(
                    "customer-1", List.of("tool-1"), invalidDate, GuaranteeType.CASH_DEPOSIT))
                    .isInstanceOf(InvalidDateException.class);
        }

        static Stream<Arguments> nullInputsProvider(){
            return Stream.of(
                    Arguments.of(null, List.of("tool-1"), TODAY, GuaranteeType.CASH_DEPOSIT), //#42 null customerid
                    Arguments.of("customer-1", Arrays.asList((String) null), TODAY, GuaranteeType.CASH_DEPOSIT), //#44 null toolId
                    Arguments.of("customer-1", Arrays.asList(("tool-1"), null), TODAY, GuaranteeType.CASH_DEPOSIT), // #64 null toolId in multi-tool
                    Arguments.of("customer-1", List.of("tool-1"), null, GuaranteeType.CASH_DEPOSIT) //#67 null start date
            );
        }

        @ParameterizedTest
        @MethodSource("nullInputsProvider")
        @UnitTest
        @TDD
        @DisplayName("Should throw NullPointerException when required field is null")
        void shouldThrowNullPointerExceptionWhenRequiredFieldIsNull(
                String customerId, List<String> toolIds, LocalDate startDate,
                GuaranteeType guaranteeType
        ) {
            assertThatThrownBy(() -> registerRental.execute(
                    customerId, toolIds, startDate, guaranteeType))
                    .isInstanceOf(NullPointerException.class);
        }

        static Stream<Arguments> blankInputsProvider(){
            return Stream.of(
                    Arguments.of("", List.of("tool-1"), TODAY, GuaranteeType.CASH_DEPOSIT), //#43 blank customer id
                    Arguments.of("    ", List.of("tool-1"), TODAY, GuaranteeType.CASH_DEPOSIT), // #43 whitespace customerid
                    Arguments.of("customer-1", Arrays.asList(""), TODAY, GuaranteeType.CASH_DEPOSIT), // #45 blank toolId
                    Arguments.of("customer-1", Arrays.asList("  "), TODAY, GuaranteeType.CASH_DEPOSIT), //# 63 white space tool id
                    Arguments.of("customer-1", List.of(), TODAY, GuaranteeType.CASH_DEPOSIT),
                    Arguments.of("customer-1", Arrays.asList("tool-1", ""), TODAY, GuaranteeType.CASH_DEPOSIT) //#65 blank  toolId in multi-tool
                    );
        }

        @ParameterizedTest
        @MethodSource("blankInputsProvider")
        @UnitTest
        @TDD
        @DisplayName("Should throw InvalidArgumentException when required field is blank")
        void shouldThrowInvalidArgumentExceptionWhenRequiredFieldIsBlank(
                String customerId, List<String> toolIds, LocalDate startDate,
                GuaranteeType guaranteeType
        ){
            assertThatThrownBy(() -> registerRental.execute(
                    customerId, toolIds, startDate, guaranteeType))
                    .isInstanceOf(InvalidArgumentException.class);

        }

        @Test
        @UnitTest
        @Functional
        @DisplayName("Should throw InvalidArgumentException when toolIds list contains duplicates")
        void shouldThrowInvalidArgumentExceptionWhenToolIdsContainsDuplicates() {
            assertThatThrownBy(() -> registerRental.execute(
                    "customer-1", Arrays.asList("tool-1", "tool-1"), TODAY, GuaranteeType.CASH_DEPOSIT)).isInstanceOf(InvalidArgumentException.class);
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
                    "customer-1", List.of("tool-1"), TODAY, GuaranteeType.CASH_DEPOSIT))
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
                    "customer-1", List.of("tool-1"), TODAY, GuaranteeType.CASH_DEPOSIT))
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
                    "customer-1", List.of("tool-1"), TODAY, GuaranteeType.CASH_DEPOSIT))
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
                    "customer-1", List.of("tool-1", "tool-2"), TODAY, GuaranteeType.CASH_DEPOSIT
            ))
                    .isInstanceOf(ToolUnavailableException.class);

            assertThat(tool1.getStatus()).isEqualTo(ToolStatus.AVAILABLE);
        }

        @Test
        @UnitTest
        @TDD
        @DisplayName("Should Throw missing GuaranteeException when guarantee is not provided")
        void shouldThrowMissingGuaranteeExceptionWhenGuaranteeIsNotProvided() {

            assertThatThrownBy(() -> registerRental.execute(
                    "customer-1", List.of("tool-1"), TODAY, null
            ))
                    .isInstanceOf(MissingGuaranteeException.class);
        }

    }
}