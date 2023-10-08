package com.ta2.probechallenge.probe.validation;

import com.ta2.probechallenge.probe.domain.ProbeDomain;
import com.ta2.probechallenge.probe.dto.in.CreateProbeDto;

public interface PobreValidation {

    String canUseThisCode(String code);


    void command(String command);

    ProbeDomain create(CreateProbeDto createProbeDto);

    void position(ProbeDomain probeDomain);
}
