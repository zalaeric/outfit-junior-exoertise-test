package com.outfit7.entity.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class EntityNotFoundExceptionMapper implements ExceptionMapper<EntityNotFoundException> {

    @Override
    public Response toResponse(EntityNotFoundException e) {
        return Response.status(Status.NOT_FOUND)
                .entity(e.getMessage())
                .type(MediaType.TEXT_PLAIN)
                .build();
    }

}
