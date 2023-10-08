package com.ta2.probechallenge.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResourceName{

        PROBE("probe"),
        PLANET("planet");

        private final String value;

}
