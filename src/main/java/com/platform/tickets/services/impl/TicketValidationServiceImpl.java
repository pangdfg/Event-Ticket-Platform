package com.platform.tickets.services.impl;

import java.util.UUID;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.platform.tickets.domain.entities.QrCode;
import com.platform.tickets.domain.entities.Ticket;
import com.platform.tickets.domain.entities.TicketValidation;
import com.platform.tickets.domain.entities.QrCodeStatusEnum;
import com.platform.tickets.domain.entities.TicketValidationMethodEnum;
import com.platform.tickets.domain.entities.TicketValidationStatusEnum;

import com.platform.tickets.exceptions.QrCodeNotFoundException;
import com.platform.tickets.exceptions.TicketNotFoundException;

import com.platform.tickets.repositories.QrCodeRepository;
import com.platform.tickets.repositories.TicketRepository;
import com.platform.tickets.repositories.TicketValidationRepository;

import com.platform.tickets.services.TicketValidationService;


@Service
@RequiredArgsConstructor
@Transactional
public class TicketValidationServiceImpl implements TicketValidationService {

    private final QrCodeRepository qrCodeRepository;
    private final TicketValidationRepository ticketValidationRepository;
    private final TicketRepository ticketRepository;

    @Override
    public TicketValidation validateTicketByQrCode(UUID qrCodeId) {
        QrCode qrCode = qrCodeRepository.findByIdAndStatus(qrCodeId, QrCodeStatusEnum.ACTIVE)
                .orElseThrow(() -> new QrCodeNotFoundException(
                        String.format("QR code with id %s was not found", qrCodeId)));

        return validateTicket(qrCode.getTicket(), TicketValidationMethodEnum.QR_SCAN);
    }

    private TicketValidation validateTicket(Ticket ticket, TicketValidationMethodEnum method) {
        TicketValidation ticketValidation = new TicketValidation();
        ticketValidation.setTicket(ticket);
        ticketValidation.setValidationMethod(method);

        TicketValidationStatusEnum ticketValidationStatus = ticket.getTicketValidations()
                .stream()
                .filter(v -> TicketValidationStatusEnum.VALID.equals(v.getStatus()))
                .findFirst()
                .map(v -> TicketValidationStatusEnum.INVALID)
                .orElse(TicketValidationStatusEnum.VALID);

        ticketValidation.setStatus(ticketValidationStatus);

        return ticketValidationRepository.save(ticketValidation);
    }


    @Override
    public TicketValidation validateTicketManually(UUID ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(TicketNotFoundException::new);

        return validateTicket(ticket, TicketValidationMethodEnum.MANUAL);
    }
}