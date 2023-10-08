package com.ta2.probechallenge.probe.validation;

import com.ta2.probechallenge.enums.ResourceName;
import com.ta2.probechallenge.exception.CustomException;
import com.ta2.probechallenge.exception.ErroInformation;
import com.ta2.probechallenge.probe.domain.ProbeDomain;
import com.ta2.probechallenge.probe.repository.ProbeRepositoryAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.ta2.probechallenge.exception.CodeExceptionEnum.CREATION_OR_UPDATE_UNAVAILABLE;
import static com.ta2.probechallenge.exception.CodeExceptionEnum.INVALID_COMMAND;

@Component
public class ProbeValidationImp implements PobreValidation {

    @Autowired
    private ProbeRepositoryAdapter repositoryAdapter;
    @Override
    public void CanCreateWithThisCode(String code){
        if(repositoryAdapter.findByCode(code).isPresent()){
           throw  CustomException.buildBy(CREATION_OR_UPDATE_UNAVAILABLE, ResourceName.PROBE.getValue(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public void position(ProbeDomain probeDomain) {

    }

    @Override
    public void command(String command) {
        Pattern pattern = Pattern.compile("[^mrl]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(command);
        if(matcher.find()){
            List<ErroInformation> erros = new ArrayList<>();
            erros.add(new ErroInformation(INVALID_COMMAND.code, INVALID_COMMAND.message));
            throw  new CustomException(INVALID_COMMAND.message, HttpStatus.BAD_REQUEST, erros);
        }
    }
}
