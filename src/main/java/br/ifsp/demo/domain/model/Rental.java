package br.ifsp.demo.domain.model;

import br.ifsp.demo.exception.RentalAlreadyCancelledException;
import br.ifsp.demo.exception.RentalAlreadyFinalizedException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static br.ifsp.demo.domain.model.RentalStatus.*;
import static br.ifsp.demo.domain.model.ToolStatus.AVAILABLE;

public class Rental {

    private final String id;
    private final List<Tool> tools;
    private RentalStatus status;

    public Rental(String id) {
        this.id = id;
        this.tools = new ArrayList<>();
        this.status = ACTIVE;
    }

    public Rental(String id, List<Tool> tools) {
        this.id = id;
        this.tools = tools;
        this.status = ACTIVE;
    }

    public Rental(String id, List<Tool> tools, RentalStatus status) {
        this.id = id;
        this.tools = tools;
        this.status = status;
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
