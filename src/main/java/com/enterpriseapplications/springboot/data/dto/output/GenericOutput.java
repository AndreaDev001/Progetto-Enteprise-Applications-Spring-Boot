package com.enterpriseapplications.springboot.data.dto.output;

import org.springframework.hateoas.RepresentationModel;

public abstract class GenericOutput<T extends RepresentationModel<? extends T>> extends RepresentationModel<T> {

    public GenericOutput() {
        this.addLinks();
    }
    abstract void addLinks();
}
