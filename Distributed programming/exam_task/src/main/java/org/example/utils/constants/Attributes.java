package org.example.utils.constants;


import java.io.Serializable;

public enum Attributes implements Serializable {

    TITLE("title"),
    YEAR("year"),
    AUTHOR("author"),
    KEYWORDS("keywords"),
    ;

    private final String value;

    Attributes(String value) {
        this.value = value;
    }
}
