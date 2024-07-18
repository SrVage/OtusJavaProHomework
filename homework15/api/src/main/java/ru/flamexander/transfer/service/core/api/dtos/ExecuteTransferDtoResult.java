package ru.flamexander.transfer.service.core.api.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@Schema(description = "ДТО результат выполнения перевода")
public class ExecuteTransferDtoResult {
    private String sourceAccount;
    private String destinationAccount;
    private BigDecimal transferAmount;
    private String status;
    private LocalDateTime dateTime;

    public ExecuteTransferDtoResult(String sourceAccount, String destinationAccount,
                                    BigDecimal transferAmount) {

        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
        this.transferAmount = transferAmount;
        dateTime = LocalDateTime.now();
    }
}
