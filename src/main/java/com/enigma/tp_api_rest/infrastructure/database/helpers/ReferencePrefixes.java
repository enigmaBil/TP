package com.enigma.tp_api_rest.infrastructure.database.helpers;

import com.enigma.tp_api_rest.infrastructure.common.helpers.ReferencePrefix;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReferencePrefixes {
    USER(() -> "USR"),
    GROUP(() -> "GRP"),
    TASK(() -> "TSK"),
    ATTACHMENT(() -> "ATC"),
    CATEGORY(() -> "CTG"),
    ;
    private final ReferencePrefix prefix;
}
