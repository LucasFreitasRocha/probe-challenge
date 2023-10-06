package com.ta2.probechallenge.probe.validation;

import com.ta2.probechallenge.probe.domain.ProbeDomain;

public interface PobreValidation {

    void CanCreateWithThisCode(String code);

    void position(ProbeDomain probeDomain);

    void command(String command);
}
