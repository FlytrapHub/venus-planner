package com.flytrap.venusplanner.api.access.domain;

import lombok.Getter;

@Getter
public enum Roll {

    LEADER(1L, "LEADER"),
    MEMBER(2L, "MEMBER");

    private final Long id;
    private final String name;

    Roll(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
