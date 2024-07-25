package ru.flamexander.transfer.service.core.backend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.flamexander.transfer.service.core.api.dtos.ExecuteTransferDtoRequest;
import ru.flamexander.transfer.service.core.api.dtos.ExecuteTransferDtoResult;
import ru.flamexander.transfer.service.core.backend.entities.Account;
import ru.flamexander.transfer.service.core.backend.entities.Transfer;
import ru.flamexander.transfer.service.core.backend.enums.Status;
import ru.flamexander.transfer.service.core.backend.errors.AppLogicException;
import ru.flamexander.transfer.service.core.backend.repositories.TransfersRepository;
import ru.flamexander.transfer.service.core.backend.validators.ExecuteTransferValidator;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransferService {
    private final AccountsService accountsService;
    private final ExecuteTransferValidator executeTransferValidator;
    private final TransfersRepository transfersRepository;

    @Transactional
    public ExecuteTransferDtoResult transfer(ExecuteTransferDtoRequest request) {
        ExecuteTransferDtoResult result = new ExecuteTransferDtoResult(request.getSourceAccount(), request.getDestinationAccount(), request.getTransferAmount());

        executeTransferValidator.validate(request);
        
        Account source = accountsService.getAccountByAccountNumber(request.getSourceAccount()).orElseThrow(() -> new AppLogicException("TRANSFER_SOURCE_ACCOUNT_NOT_FOUND", "Перевод невозможен поскольку не существует счет отправителя"));
        Account target = accountsService.getAccountByAccountNumber(request.getDestinationAccount()).orElseThrow(() -> new AppLogicException("TRANSFER_TARGET_ACCOUNT_NOT_FOUND", "Перевод невозможен поскольку не существует счет получателя"));

        checkMoneyForEnough(request, source);

        var transfer = new Transfer(source.getClientId(), source.getAccountNumber(),
                target.getClientId(), target.getAccountNumber(), request.getTransferAmount());
        transfersRepository.save(transfer);

        source.setBalance(source.getBalance().subtract(request.getTransferAmount()));
        target.setBalance(target.getBalance().add(request.getTransferAmount()));
        setTransferStatus(transfer, Status.COMPLETED);

        return result;
    }

    public List<Transfer> getAllTransfers(Long clientId) {
        return transfersRepository.findAllBySourceOrDestinationClientId(clientId);
    }

    private void checkMoneyForEnough(ExecuteTransferDtoRequest request, Account source) {
        if (source.getBalance().compareTo(request.getTransferAmount()) < 0){
            throw new AppLogicException("NOT_ENOUGH_MONEY", "Перевод невозможен поскольку у отправителя недостаточно средств");
        }
    }

    private void setTransferStatus(Transfer transfer, Status status){
        transfer.setStatus(status);
        transfersRepository.save(transfer);
    }
}
