package ru.flamexander.transfer.service.core.backend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.flamexander.transfer.service.core.backend.repositories.AccountsRepository;

@RequiredArgsConstructor
@Service
public class NumberGeneratorService {
    private final AccountsRepository accountsRepository;

    public String generateNumber(){
        long generated = (long)(Math.random()*10000000000000000L);
        String number = String.valueOf(generated);

        if (number.length() != 16){
            return generateNumber();
        }

        var checkAccount = accountsRepository.findByAccountNumber(number);
        if (checkAccount.isPresent()){
            return generateNumber();
        }

        return number;
    }
}
