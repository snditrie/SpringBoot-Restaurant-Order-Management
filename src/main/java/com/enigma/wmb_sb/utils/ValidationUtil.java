package com.enigma.wmb_sb.utils;

import com.enigma.wmb_sb.constant.ResponseMessage;
import com.enigma.wmb_sb.model.dto.request.BillRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class ValidationUtil {
    private final Validator validator;

    public void validate(Object o) {
        Set<ConstraintViolation<Object>> violations = validator.validate(o);
        if(!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    public void validateTableRestoId(BillRequest request) {
        if("DI".equalsIgnoreCase(request.getTransTypeId())) {
            if(request.getTableRestoId() == null || request.getTableRestoId().isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "tableRestoId is required for dine-in transactions");
            }
        }
    }


}
