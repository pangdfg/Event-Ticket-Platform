package com.platform.tickets.services;

import java.util.Optional;
import java.util.UUID;

import com.platform.tickets.domain.entities.Ticket;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TicketService {

    Page<Ticket> listTicketsForUser(UUID userId, Pageable pageable);

    Optional<Ticket> getTicketForUser(UUID userId,UUID ticketId);
}
