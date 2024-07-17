package ru.flamexander.transfer.service.core.api.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
@Schema(description = "ДТО запроса выполнения перевода")
public class ExecuteTransferDtoRequest {
    private final String sourceAccount;
    private final String destinationAccount;
    private final BigDecimal transferAmount;
}
