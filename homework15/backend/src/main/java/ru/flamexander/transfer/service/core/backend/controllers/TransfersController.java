package ru.flamexander.transfer.service.core.backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.flamexander.transfer.service.core.api.dtos.AccountDto;
import ru.flamexander.transfer.service.core.api.dtos.ExecuteTransferDtoRequest;
import ru.flamexander.transfer.service.core.api.dtos.ExecuteTransferDtoResult;
import ru.flamexander.transfer.service.core.backend.entities.Account;
import ru.flamexander.transfer.service.core.backend.services.TransferService;

import java.util.function.Function;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transfers")
@Tag(name = "Переводы", description = "Методы переводов между счетами")
public class TransfersController {
    private final TransferService transferService;

    @PostMapping("/execute")
    @Operation(summary = "Выполнение перевода между счетами")
    public ExecuteTransferDtoResult executeTransfer(@RequestBody ExecuteTransferDtoRequest request) {
        return transferService.transfer(request);
    }
}
