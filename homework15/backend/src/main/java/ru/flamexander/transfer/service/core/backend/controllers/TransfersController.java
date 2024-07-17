package ru.flamexander.transfer.service.core.backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.flamexander.transfer.service.core.api.dtos.ExecuteTransferDtoRequest;
import ru.flamexander.transfer.service.core.backend.services.TransferService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transfers")
public class TransfersController {
    private final TransferService transferService;

    @PostMapping("/execute")
    public void executeTransfer(@RequestBody ExecuteTransferDtoRequest request) {
        transferService.transfer(request);
    }
}
