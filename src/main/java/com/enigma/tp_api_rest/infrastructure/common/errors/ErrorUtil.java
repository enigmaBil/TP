package com.enigma.tp_api_rest.infrastructure.common.errors;

import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@UtilityClass
public class ErrorUtil {

    /**
     * Format CDR log by replacing new line characters with empty strings
     * and separating values with semicolons.
     *
     * @param values the values to be formatted
     * @return the formatted CDR log string
     */
    public static String formatCdrLog(Object... values) {
        List<Object> list = Arrays.asList(values);
        list.replaceAll(el -> el != null ? el.toString().replaceAll("\n", "") : "");
        String format = String.join("", Collections.nCopies(list.size(), "%s;"));
        return String.format(format, values);
    }
}
