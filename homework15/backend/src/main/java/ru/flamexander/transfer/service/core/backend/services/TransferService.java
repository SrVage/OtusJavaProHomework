package ru.flamexander.transfer.service.core.backend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.flamexander.transfer.service.core.api.dtos.ExecuteTransferDtoRequest;
import ru.flamexander.transfer.service.core.backend.entities.Account;
import ru.flamexander.transfer.service.core.backend.errors.AppLogicException;
import ru.flamexander.transfer.service.core.backend.validators.ExecuteTransferValidator;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransferService {
    private final AccountsService accountsService;
    private final ExecuteTransferValidator executeTransferValidator;

    @Transactional
    public void transfer(ExecuteTransferDtoRequest request) {
        executeTransferValidator.validate(request);
        Account source = accountsService.getAccountByAccountNumber(request.getSourceAccount()).orElseThrow(() -> new AppLogicException("TRANSFER_SOURCE_ACCOUNT_NOT_FOUND", "Перевод невозможен поскольку не существует счет отправителя"));
        Account target = accountsService.getAccountByAccountNumber(request.getDestinationAccount()).orElseThrow(() -> new AppLogicException("TRANSFER_TARGET_ACCOUNT_NOT_FOUND", "Перевод невозможен поскольку не существует счет получателя"));
        if (source.getBalance().compareTo(request.getTransferAmount()) < 0){
            throw new AppLogicException("NOT_ENOUGH_MONEY", "Перевод невозможен поскольку у отправителя недостаточно средств");
        }
        source.setBalance(source.getBalance().subtract(request.getTransferAmount()));
        target.setBalance(target.getBalance().add(request.getTransferAmount()));
    }
}
