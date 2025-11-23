package com.platform.tickets.repositories;

import java.util.UUID;

import com.platform.tickets.domain.entities.TicketValidation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketValidationRepository extends JpaRepository<TicketValidation, UUID> {

}
