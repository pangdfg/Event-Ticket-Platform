package com.platform.tickets.repositories;

import java.util.Optional;
import java.util.UUID;

import com.platform.tickets.domain.entities.QrCode;
import com.platform.tickets.domain.entities.QrCodeStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QrCodeRepository extends JpaRepository<QrCode, UUID> {

    Optional<QrCode> findByTicketIdAndTicketPurchaserId(UUID ticketId,UUID ticketPurchaserId);

    Optional<QrCode> findByIdAndStatus(UUID id, QrCodeStatusEnum status);
}