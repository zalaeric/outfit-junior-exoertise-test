package com.outfit7.services.ranked;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.outfit7.entity.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
public class RankedOpponentsService {

    @Inject
    RankedMatchingService rankedMatchingService;

    public List<User> matchOpponents(String userId) {
        log.info("Running ranked matcher for userId: '{}'", userId);
        return rankedMatchingService.retrieveOpponents(userId);
    }

}
