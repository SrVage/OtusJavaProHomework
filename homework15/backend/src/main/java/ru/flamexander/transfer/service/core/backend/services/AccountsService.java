package ru.flamexander.transfer.service.core.backend.services;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.flamexander.transfer.service.core.api.dtos.CreateAccountDto;
import ru.flamexander.transfer.service.core.backend.entities.Account;
import ru.flamexander.transfer.service.core.backend.errors.AppLogicException;
import ru.flamexander.transfer.service.core.backend.repositories.AccountsRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountsService {
    private final AccountsRepository accountsRepository;
    private final NumberGeneratorService numberGeneratorService;

    private static final Logger logger = LoggerFactory.getLogger(AccountsService.class);

    public List<Account> getAllAccounts(Long clientId) {
        return accountsRepository.findAllByClientId(clientId);
    }

    public Optional<Account> getAccountById(Long clientId, Long id) {
        return accountsRepository.findByIdAndClientId(id, clientId);
    }

    public Optional<Account> getAccountByAccountNumber(String accountNumber){
        return accountsRepository.findByAccountNumber(accountNumber);
    }

    public Account createNewAccount(Long clientId, CreateAccountDto createAccountDto) {
        BigDecimal initialBalance = Optional.ofNullable(createAccountDto.getInitialBalance())
                .orElseThrow(() -> new AppLogicException("VALIDATION_ERROR", "Создаваемый счет не может иметь null баланс"));

        Account account = new Account(clientId, createAccountDto.getInitialBalance());
        account.setAccountNumber(numberGeneratorService.generateNumber());
        account = accountsRepository.save(account);
        logger.info("Account id = {} created from {}", account.getId(), createAccountDto);
        return account;
    }
}
