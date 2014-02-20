package com.yammer.dropwizard.jersey;

import com.yammer.dropwizard.jetty.UnbrandedErrorHandler;
import com.yammer.dropwizard.validation.InvalidEntityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.StringWriter;

import com.google.common.collect.ImmutableList;

@Provider
public class InvalidEntityExceptionMapper implements ExceptionMapper<InvalidEntityException> {
    private static final Logger LOGGER = LoggerFactory.getLogger(InvalidEntityExceptionMapper.class);
    private static final int UNPROCESSABLE_ENTITY = 422;

    @Context
    private HttpServletRequest request;

    private final UnbrandedErrorHandler errorHandler = new UnbrandedErrorHandler();

    public class InvalidEntityExceptionRepresentation {
        private  ImmutableList<String> errors;
        public void setErrors(ImmutableList<String> errors) {
            this.errors = errors;
        }
        public ImmutableList<String> getErrors() {
            return errors;
        }
    }

    @Override
    public Response toResponse(InvalidEntityException exception) {
        //final StringWriter writer = new StringWriter(4096);
        //try {
            //errorHandler.writeValidationErrorPage(request, writer, exception);
        //} catch (IOException e) {
         //   LOGGER.warn("Unable to generate error page", e);
        //}
        InvalidEntityExceptionRepresentation r = new InvalidEntityExceptionRepresentation();
        r.setErrors(exception.getErrors());
        return Response.status(UNPROCESSABLE_ENTITY)
                       .type(MediaType.APPLICATION_JSON)
                       .entity(r)
                       .build();
    }
}
