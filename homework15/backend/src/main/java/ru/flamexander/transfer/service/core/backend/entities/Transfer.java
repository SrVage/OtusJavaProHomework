package ru.flamexander.transfer.service.core.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import ru.flamexander.transfer.service.core.backend.enums.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "transfers")
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "source_client_id")
    private Long sourceClientId;

    @Column(name = "source_client_number")
    private String sourceClientNumber;

    @Column(name = "destination_client_id")
    private Long destinationClientId;

    @Column(name = "destination_client_number")
    private String destinationClientNumber;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "status")
    private Status status;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Transfer(Long sourceClientId, String sourceClientNumber,
                    Long destinationClientId, String destinationClientNumber,
                    BigDecimal amount){

        this.sourceClientId = sourceClientId;
        this.sourceClientNumber = sourceClientNumber;
        this.destinationClientId = destinationClientId;
        this.destinationClientNumber = destinationClientNumber;
        this.amount = amount;
        status = Status.CREATED;
    }
}
