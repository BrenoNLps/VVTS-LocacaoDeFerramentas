package br.ifsp.demo.domain.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static br.ifsp.demo.domain.model.RentalStatus.*;

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

    public String getId() {
        return id;
    }

    public RentalStatus getStatus(){
        return status;
    }

    public List<Tool> getTools(){
        return Collections.unmodifiableList(tools);
    }
}
