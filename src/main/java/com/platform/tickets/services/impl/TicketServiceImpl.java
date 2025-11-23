package com.platform.tickets.services.impl;

import java.util.UUID;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.platform.tickets.domain.entities.Ticket;
import com.platform.tickets.repositories.TicketRepository;
import com.platform.tickets.services.TicketService;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    @Override
    public Page<Ticket> listTicketsForUser(UUID userId, Pageable pageable) {
        return ticketRepository.findByPurchaserId(userId,pageable);
    }

    @Override
    public Optional<Ticket> getTicketForUser(UUID userId, UUID ticketId) {
        return ticketRepository.findByIdAndPurchaserId(ticketId,userId);
    }
}
