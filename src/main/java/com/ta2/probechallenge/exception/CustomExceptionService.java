package com.ta2.probechallenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomExceptionService {

    public CustomException createNotFound(String resource){
        StringBuilder message  = new StringBuilder();
        message.append("Not found ");
        message.append(resource);
        List<ErroInformation> errors = new ArrayList<>();
        errors.add(new ErroInformation(HttpStatus.NOT_FOUND.name(), message.toString()));
        return  new CustomException(message.toString(), HttpStatus.NOT_FOUND, errors);
    }
}
