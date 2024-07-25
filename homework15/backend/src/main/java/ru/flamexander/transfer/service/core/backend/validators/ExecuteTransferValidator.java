package ru.flamexander.transfer.service.core.backend.validators;

import org.springframework.stereotype.Component;
import ru.flamexander.transfer.service.core.api.dtos.ExecuteTransferDtoRequest;
import ru.flamexander.transfer.service.core.backend.errors.FieldValidationError;
import ru.flamexander.transfer.service.core.backend.errors.FieldsValidationException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class ExecuteTransferValidator {
    public void validate(ExecuteTransferDtoRequest request) {
        List<FieldValidationError> errorFields = new ArrayList<>();
        if (request.getSourceAccount().isEmpty()){
            errorFields.add(new FieldValidationError("sourceAccount", "Is empty"));
        }
        if (request.getDestinationAccount().isEmpty()){
            errorFields.add(new FieldValidationError("destinationAccount", "Is empty"));
        }
        if (request.getTransferAmount().compareTo(BigDecimal.valueOf(0)) <= 0){
            errorFields.add(new FieldValidationError("transferAmount", "Is null or negative"));
        }
        if (!errorFields.isEmpty()) {
            throw new FieldsValidationException(errorFields);
        }
    }
}
