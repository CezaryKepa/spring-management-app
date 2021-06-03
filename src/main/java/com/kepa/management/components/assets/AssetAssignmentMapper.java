package com.kepa.management.components.assets;

import com.kepa.management.components.assignment.Assignment;

public class AssetAssignmentMapper {
    static AssetAssignmentDto toDto(Assignment assignment) {
        AssetAssignmentDto dto = new AssetAssignmentDto();
        dto.setId(assignment.getId());
        dto.setStart(assignment.getStart());
        dto.setEnd(assignment.getEnd());
        dto.setUserId(assignment.getUser().getId());
        dto.setFirstName(assignment.getUser().getFirstName());
        dto.setLastName(assignment.getUser().getLastName());
        dto.setPesel(assignment.getUser().getPesel());
        return dto;
    }
}
