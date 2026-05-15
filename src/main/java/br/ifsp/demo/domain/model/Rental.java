package br.ifsp.demo.domain.model;

import br.ifsp.demo.domain.exception.InvalidDateException;
import br.ifsp.demo.domain.exception.RentalAlreadyCancelledException;
import br.ifsp.demo.domain.exception.RentalAlreadyFinalizedException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static br.ifsp.demo.domain.model.RentalStatus.*;

public class Rental {

    private final String id;
    private final List<Tool> tools;
    private final LocalDate startDate;
    private RentalStatus status;

    private Rental(String id, List<Tool> tools, LocalDate startDate, RentalStatus status) {
        this.id = id;
        this.tools = new ArrayList<>(tools);
        this.startDate = startDate;
        this.status = status;
    }

    public static Rental create(String id, List<Tool> tools, LocalDate startDate) {
        return new Rental(id, tools, startDate, ACTIVE);
    }

    public static Rental reconstitute(String id, List<Tool> tools, LocalDate startDate, RentalStatus status) {
        return new Rental(id, tools, startDate, status);
    }

    public BigDecimal finalize(LocalDate endDate) {
        validateActive();
        if (endDate.isBefore(startDate)) throw new InvalidDateException("endDate");
        long days = Math.max(1, ChronoUnit.DAYS.between(startDate, endDate));
        BigDecimal total = BigDecimal.ZERO;
        for (Tool tool : tools) {
            total = total.add(BigDecimal.valueOf(days)
                    .multiply(tool.getPrices().rateFor(days)));
            tool.markAsAvailable();
        }
        this.status = FINALIZED;
        return total;
    }

    private void validateActive() {
        if (status == FINALIZED) throw new RentalAlreadyFinalizedException(id);
        if (status == CANCELLED) throw new RentalAlreadyCancelledException(id);
    }

    public String getId() { return id; }
    public RentalStatus getStatus() { return status; }
    public List<Tool> getTools() { return Collections.unmodifiableList(tools); }
    public LocalDate getStartDate() { return startDate; }

    public void setStatus(RentalStatus status) { this.status = status; }

    public void cancel() {
        validateActive();
        this.status = CANCELLED;
        for (Tool tool : tools) {
            tool.markAsAvailable();
        }
    }
}