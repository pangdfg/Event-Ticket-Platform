package com.platform.tickets.domain.dtos;

import java.util.UUID;

import com.platform.tickets.domain.entities.TicketValidationMethodEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketValidationRequestDto {

    private UUID id;
    private TicketValidationMethodEnum method;
}
