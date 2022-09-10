package com.outfit7.rest.ranked;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.outfit7.entity.User;
import com.outfit7.services.classic.OpponentsService;
import com.outfit7.services.ranked.RankedOpponentsService;

@Path("/matching")
public class RankedMatchingFacade {

    @Inject
    RankedOpponentsService rankedOpponentsService;

    @GET
    @Path("/ranked/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> matchOpponents(@PathParam("userId") String userId) {
        return rankedOpponentsService.matchOpponents(userId);
    }

}