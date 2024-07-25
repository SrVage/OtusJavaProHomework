package ru.flamexander.transfer.service.core.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Описание счета клиента")
public class AccountDto {
    @Schema(description = "Внутренний ID счета", required = true)
    private Long id;

    @Schema(description = "Номер счета", required = true, pattern = "\\d{16}", example = "1234123412341234")
    private String accountNumber;

    @Schema(description = "ID клиента владельца", required = true)
    private Long clientId;

    @Schema(description = "Баланс на счету", required = true, minimum = "0", example = "1000.99")
    private BigDecimal balance;
}