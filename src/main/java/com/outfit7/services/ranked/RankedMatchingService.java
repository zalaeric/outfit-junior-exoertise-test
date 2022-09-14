package com.outfit7.services.ranked;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.outfit7.entity.User;

import com.outfit7.services.UserService;
import com.outfit7.services.ranked.exception.RankedMatchingNotCompleteException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
public class RankedMatchingService {

    @Inject
    UserService userService;

    public List<User> retrieveOpponents(String userId) {
        User currentUser = userService.get(userId);
        log.debug("Found user: '{}'", currentUser);
        return matchOpponents(currentUser);
    }

    private List<User> matchOpponents(User currentUser) {
        List<User> matchedOpponents = userService.getAll().stream()
                .filter(opponent -> !opponent.getId().equals(currentUser.getId()))
                .filter(distinctByKey(User::getPlayerName))
                .filter(filterByPowerLevel(currentUser))
                .filter(filterByRank(currentUser))
                .collect(Collectors.toList());

        if (matchedOpponents.size() < 5)
            throw new RankedMatchingNotCompleteException("Less than 5 opponents found. Try to rematch.");

        Collections.shuffle(matchedOpponents);

        return matchedOpponents.stream()
                .limit(5)
                .collect(Collectors.toList());
    }

    private static Predicate<User> filterByPowerLevel(User currentUser) {
        return opponent ->
                opponent.getPowerLevel() <= currentUser.getPowerLevel() + 15
                        && opponent.getPowerLevel() >= currentUser.getPowerLevel() - 15;
    }

    private static Predicate<User> filterByRank(User currentUser) {
        return opponent ->
                opponent.getRank() <= currentUser.getRank() + 100
                        && opponent.getRank() >= currentUser.getRank() - 100;
    }

    private static Predicate<User> distinctByKey(Function<User, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(t.getPlayerName());
    }
}

