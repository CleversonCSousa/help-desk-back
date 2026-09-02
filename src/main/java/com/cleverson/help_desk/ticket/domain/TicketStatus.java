package com.cleverson.help_desk.ticket.domain;

import lombok.Getter;

@Getter
public enum TicketStatus {
    OPEN("open"),
    IN_PROGRESS("in_progress"),
    CLOSED("closed");

    private final String status;

    TicketStatus(String status) {
        this.status = status;
    }
}