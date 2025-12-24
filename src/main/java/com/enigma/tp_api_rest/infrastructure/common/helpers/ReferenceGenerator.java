package com.enigma.tp_api_rest.infrastructure.common.helpers;


import com.github.f4b6a3.ksuid.Ksuid;
import com.github.f4b6a3.ksuid.KsuidCreator;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * A utility class for generating unique reference identifiers.
 */
public final class ReferenceGenerator {
    private static final String SEPARATOR = "#";
    private static final String DEFAULT_PREFIX = "REF";

    /**
     * Generates a unique reference identifier using the default prefix.
     *
     * @return A unique reference identifier.
     */
    public static ReferenceGenerator getInstance() {
        return new ReferenceGenerator();
    }

    /**
     * Generates a unique reference identifier with an optional prefix.
     *
     * @param prefix An optional ReferencePrefix object containing the desired prefix.
     * @return A unique reference identifier.
     */
    public String next(@Nullable ReferencePrefix prefix) {
        String prefx = "";
        if (!Objects.isNull(prefix) && StringUtils.isNoneBlank(prefix.getPrefix())) {
            prefx = prefix.getPrefix() + SEPARATOR;
        } else {
            prefx = DEFAULT_PREFIX + SEPARATOR;
        }

        Ksuid ksuid = KsuidCreator.getSubsecondKsuid();
        return String.format("%s%s", prefx, ksuid);
    }

    /**
     * Generates a unique reference identifier using the default prefix.
     *
     * @return A unique reference identifier.
     */
    public String next() {
        return next(null);
    }
}
