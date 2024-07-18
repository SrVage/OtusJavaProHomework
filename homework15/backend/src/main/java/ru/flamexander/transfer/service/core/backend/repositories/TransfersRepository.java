package ru.flamexander.transfer.service.core.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.flamexander.transfer.service.core.backend.entities.Transfer;

@Repository
public interface TransfersRepository extends JpaRepository<Transfer, Long> {

}
