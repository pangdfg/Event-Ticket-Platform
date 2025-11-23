package com.platform.tickets.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.Mapping;

import com.platform.tickets.domain.entities.Ticket;
import com.platform.tickets.domain.entities.TicketType;
import com.platform.tickets.domain.dtos.GetTicketResponseDto;
import com.platform.tickets.domain.dtos.ListTicketResponseDto;
import com.platform.tickets.domain.dtos.ListTicketTicketTypeResponseDto;

import java.util.Optional;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TicketMapper {

    ListTicketTicketTypeResponseDto toListTicketTicketTypeResponseDto(TicketType ticketType);

    ListTicketResponseDto toListTicketResponseDto(Ticket ticket);

    @Mapping(target = "price",source = "ticketType.price")
    @Mapping(target = "description",source = "ticketType.description")
    @Mapping(target = "eventName",source = "ticketType.event.name")
    @Mapping(target = "eventVenue",source = "ticketType.event.venue")
    @Mapping(target = "eventStart",source = "ticketType.event.start")
    @Mapping(target = "eventEnd",source = "ticketType.event.end")
    GetTicketResponseDto toGetTicketResponseDto(Ticket ticket);
}
