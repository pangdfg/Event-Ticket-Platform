package com.platform.tickets.services;

import com.platform.tickets.domain.entities.QrCode;
import com.platform.tickets.domain.entities.Ticket;

import java.util.UUID;

public interface QrCodeService {

    QrCode generateQrCode(Ticket ticket);

    byte[] getQrCodeImageForUserAndTicket(UUID userId,UUID ticketId);
}
