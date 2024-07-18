package ru.flamexander.transfer.service.core.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.flamexander.transfer.service.core.backend.entities.Account;
import ru.flamexander.transfer.service.core.backend.entities.Transfer;

import java.util.List;

@Repository
public interface TransfersRepository extends JpaRepository<Transfer, Long> {
    List<Transfer> findAllBySourceClientId(Long clientId);
    List<Transfer> findAllByDestinationClientId(Long clientId);
}
