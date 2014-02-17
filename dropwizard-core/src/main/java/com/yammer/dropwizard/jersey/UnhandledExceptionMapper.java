package com.yammer.dropwizard.jersey;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A generic exception mapper that converts exception messages into JSON format
 * 
 * @author bryan
 */
@Provider
public class UnhandledExceptionMapper implements ExceptionMapper<Exception> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UnhandledExceptionMapper.class);

    private static final transient ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public Response toResponse(final Exception exception) {
        LOGGER.error("Unhandled exception", exception);
        ResponseBuilder builder = Response.status(Status.INTERNAL_SERVER_ERROR)
                .entity(defaultJSON(exception))
                .type(MediaType.APPLICATION_JSON);
        return builder.build();
    }

    private String defaultJSON(final Exception exception) {
        ErrorInfo errorInfo = new ErrorInfo(exception.getMessage());

        try {
            return MAPPER.writeValueAsString(errorInfo);
        } catch (JsonProcessingException e) {
            LOGGER.error("Failed to serialize error", e);
            return "{\"message\":\"An internal error occurred\"}";
        }
    }

    private static class ErrorInfo {

        private ErrorInfo(String message) {
            this.message = message;
        }

        private String message;

        /**
         * Get the value of message
         *
         * @return the value of message
         */
        public String getMessage() {
            return message;
        }

        /**
         * Set the value of message
         *
         * @param message new value of message
         */
        public void setMessage(String message) {
            this.message = message;
        }

    }
}
