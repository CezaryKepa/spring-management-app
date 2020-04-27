package com.kepa.equipy.components.assignment;

import com.kepa.equipy.components.assets.Asset;
import com.kepa.equipy.components.user.User;

class AssignmentMapper {

    static AssignmentDto toDto(Assignment assignment) {
        AssignmentDto dto = new AssignmentDto();
        User user = assignment.getUser();
        dto.setId(assignment.getId());
        dto.setStart(assignment.getStart());
        dto.setEnd(assignment.getEnd());
        dto.setUserId(user.getId());
        Asset asset = assignment.getAsset();
        dto.setAssetId(asset.getId());
        return dto;
    }

}