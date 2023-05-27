package com.movie.backend.entity;

public enum Language {
    EN("English"),
    VN("Vietnamese"),
    KR("Korean"),
    JP("Japanese"),
    CN("Chinese"),
    IN("Hindu"),
    SP("Spainish"),
    GM("German"),
    FR("French");

    public final String label;
    private Language(String label) {
        this.label = label;
    }
}
