package com.cleverson.help_desk.technician.domain;

import java.util.List;

public interface TechnicianRepository {
    List<TechnicianSummary> findAllWithWorkingHours();
}