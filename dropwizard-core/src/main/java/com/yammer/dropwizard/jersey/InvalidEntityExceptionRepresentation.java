package com.yammer.dropwizard.jersey;



import com.google.common.collect.ImmutableList;

public class InvalidEntityExceptionRepresentation {
    
    private  ImmutableList<String> errors;

    public void setErrors(ImmutableList<String> errors) {
        this.errors = errors;
    }
    public ImmutableList<String> getErrors() {
        return errors;
    }
}
