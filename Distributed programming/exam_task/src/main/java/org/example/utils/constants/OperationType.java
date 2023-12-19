package org.example.utils.constants;

import java.io.Serializable;

public enum OperationType implements Serializable {

    FIND_PUBLICATIONS_BY_AUTHOR("find publications by author"),
    FIND_PUBLICATIONS_AFTER_YEAR("find publications after year"),
    FIND_PUBLICATIONS_BY_KEYWORDS("find publications by keywords"),
    FIND_AND_SORT_PUBLICATIONS("find publications and sort"),
    CLOSE("close")
    ;

    private final String value;

    OperationType(String value) {
        this.value = value;
    }

}
