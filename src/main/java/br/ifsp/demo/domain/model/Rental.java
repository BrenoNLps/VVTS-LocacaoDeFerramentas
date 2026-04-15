package br.ifsp.demo.domain.model;

import br.ifsp.demo.exception.InvalidDateException;
import br.ifsp.demo.exception.RentalAlreadyCancelledException;
import br.ifsp.demo.exception.RentalAlreadyFinalizedException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static br.ifsp.demo.domain.model.RentalStatus.*;
import static br.ifsp.demo.domain.model.ToolStatus.AVAILABLE;

public class Rental {

    private final String id;
    private final List<Tool> tools;
    private final LocalDate startDate;
    private RentalStatus status;

    public Rental(String id) {
        this.id = id;
        this.tools = new ArrayList<>();
        this.startDate = null;
        this.status = ACTIVE;
    }

    public Rental(String id, List<Tool> tools) {
        this.id = id;
        this.tools = new ArrayList<>(tools);
        this.startDate = null;
        this.status = ACTIVE;
    }

    public Rental(String id, List<Tool> tools, RentalStatus status) {
        this.id = id;
        this.tools = new ArrayList<>(tools);
        this.startDate = null;
        this.status = status;
    }

    public Rental(String id, List<Tool> tools, LocalDate startDate) {
        this.id = id;
        this.tools = new ArrayList<>(tools);
        this.startDate = startDate;
        this.status = ACTIVE;
    }

    public BigDecimal finalize(LocalDate endDate) {
        if (status == FINALIZED) throw new RentalAlreadyFinalizedException(id);
        if (status == CANCELLED) throw new RentalAlreadyCancelledException(id);
        if (endDate.isBefore(startDate)) throw new InvalidDateException("endDate");
        long days = ChronoUnit.DAYS.between(startDate, endDate);
        BigDecimal total = BigDecimal.ZERO;
        for (Tool tool : tools) {
            total = total.add(BigDecimal.valueOf(days)
                    .multiply(tool.getPrices().rateFor(days)));
            tool.markAsAvailable();
        }
        this.status = FINALIZED;
        return total;
    }

    public String getId() {
        return id;
    }

    public RentalStatus getStatus(){
        return status;
    }

    public List<Tool> getTools(){
        return Collections.unmodifiableList(tools);
    }

    public void setStatus(RentalStatus status) {
        this.status = status;
    }

    public void cancel() {
        if (status == CANCELLED) throw new RentalAlreadyCancelledException(id);
        if (status == FINALIZED) throw new RentalAlreadyFinalizedException(id);
        this.status = CANCELLED;
        for(Tool tool : tools){
            tool.markAsAvailable();
        }
    }
}
