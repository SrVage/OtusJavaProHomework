package ru.flamexander.transfer.service.core.backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.flamexander.transfer.service.core.api.dtos.*;
import ru.flamexander.transfer.service.core.backend.entities.Transfer;
import ru.flamexander.transfer.service.core.backend.services.TransferService;

import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transfers")
@Tag(name = "Переводы", description = "Методы переводов между счетами")
public class TransfersController {
    private final TransferService transferService;
    private final Function<Transfer, TransferDto> entityToDto = transfer ->
            new TransferDto(transfer.getSourceClientNumber(), transfer.getDestinationClientNumber(),
            transfer.getAmount(), transfer.getStatus().toString(), transfer.getUpdatedAt());

    @PostMapping("/execute")
    @Operation(summary = "Выполнение перевода между счетами")
    public ExecuteTransferDtoResult executeTransfer(@RequestBody ExecuteTransferDtoRequest request) {
        return transferService.transfer(request);
    }

    @Operation(summary = "Получение информации о всех переводах пользователя")
    @GetMapping
    public TransfersPageDto getAllTransfers(@RequestHeader Long clientId) {
        return new TransfersPageDto(transferService.getAllTransfers(clientId).stream().map(entityToDto).collect(Collectors.toList()));
    }
}
