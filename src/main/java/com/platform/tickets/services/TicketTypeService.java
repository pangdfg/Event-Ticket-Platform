package com.platform.tickets.services;

import com.platform.tickets.domain.entities.Ticket;

import java.util.UUID;

public interface TicketTypeService {

    Ticket purchaseTicket(UUID userId,UUID ticketTypeId);
}
