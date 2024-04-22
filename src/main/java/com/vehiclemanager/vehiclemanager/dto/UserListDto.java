package com.vehiclemanager.vehiclemanager.dto;

import java.util.List;

public record UserListDto(List<UserDto> userDtos,
                          int page,
                          int pageSize,
                          int totalPages,
                          long totalElements) {
}
