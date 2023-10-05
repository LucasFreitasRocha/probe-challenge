package com.ta2.probechallenge.probe.validation;

import com.ta2.probechallenge.exception.CustomException;
import com.ta2.probechallenge.exception.ErroInformation;
import com.ta2.probechallenge.probe.domain.ProbeDomain;
import com.ta2.probechallenge.probe.repository.ProbeRepositoryAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.ta2.probechallenge.exception.CodeExceptionEnum.CREATION_UNAVAILABLE;

@Component
public class ProbeValidationImp implements PobreValidation {

    @Autowired
    private ProbeRepositoryAdapter repositoryAdapter;
    @Override
    public void CanCreateWithThisCode(String code){
        if(repositoryAdapter.findByCode(code).isPresent()){
            List<ErroInformation> erroInformationList  = new ArrayList<>();
            erroInformationList.add(new ErroInformation(CREATION_UNAVAILABLE.toString(), CREATION_UNAVAILABLE.message));
            throw new CustomException(CREATION_UNAVAILABLE.message, CREATION_UNAVAILABLE.code,erroInformationList);
        }
    }

    @Override
    public void position(ProbeDomain probeDomain) {

    }
}
