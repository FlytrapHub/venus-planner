package com.flytrap.venusplanner.api.access.domain;

import lombok.Getter;

@Getter
public enum Permission {

    NONE(1L, "NONE"),
    EDIT(2L, "EDIT");

    private final Long id;
    private final String name;

    Permission(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
