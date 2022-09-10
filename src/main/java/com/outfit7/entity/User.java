package com.outfit7.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class User {

    String id;

    String playerName;

    Long powerLevel;

    ////////////////
    Long rank;

    Hero hero;

    List<Champion> champions;

    @Value
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(force = true)
    public static class Champion {

        String id;

        Long level;

        String name;

    }

    @Value
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(force = true)
    public static class Hero {

        String id;

        Long level;

        String name;

    }

}
