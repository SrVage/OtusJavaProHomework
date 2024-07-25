package ru.flamexander.transfer.service.core.backend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.flamexander.transfer.service.core.backend.repositories.AccountsRepository;

@RequiredArgsConstructor
@Service
public class NumberGeneratorService {
    private static final int ACCOUNT_NUMBER_LENGTH = 16;
    private final AccountsRepository accountsRepository;

    public String generateNumber(){
        String number;
        do{
            long generated = getGeneratedNumber();
            number = String.valueOf(generated);
        }
        while(number.length() != ACCOUNT_NUMBER_LENGTH || accountsRepository.findByAccountNumber(number).isPresent());
        return number;
    }

    private static long getGeneratedNumber() {
        return (long) (Math.random() * 10000000000000000L);
    }
}
