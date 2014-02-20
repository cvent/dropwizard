package com.yammer.dropwizard.jersey;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.core.MediaType;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    private static final int UNPROCESSABLE_ENTITY = 422;

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        final ValidationErrorMessage message = new ValidationErrorMessage(exception.getConstraintViolations());
        //have constraint violations default to JSON
        return Response.status(UNPROCESSABLE_ENTITY)
                       .entity(message).type(MediaType.APPLICATION_JSON)
                       .build();
    }
}
