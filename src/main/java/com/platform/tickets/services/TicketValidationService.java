package com.platform.tickets.services;

import java.util.UUID;

import com.platform.tickets.domain.entities.TicketValidation;

public interface TicketValidationService {

    TicketValidation validateTicketByQrCode(UUID qrCodeId);

    TicketValidation validateTicketManually(UUID ticketId);
}
