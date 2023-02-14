package ru.bobcody.domain;

import lombok.Getter;

@Getter
public enum RecordType {

    ABYSS("бездна"),
    QUOTE("цитата"),
    CAPS("капс");
    private String name;

    RecordType(String name) {
        this.name = name;
    }
}
