package com.outfit7.services.ranked.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class RankedMatchingNotCompleteExceptionMapper implements ExceptionMapper<RankedMatchingNotCompleteException> {

    @Override
    public Response toResponse(RankedMatchingNotCompleteException e) {
        return Response.status(Status.NOT_FOUND)
                .entity(e.getMessage())
                .type(MediaType.TEXT_PLAIN)
                .build();
    }

}
