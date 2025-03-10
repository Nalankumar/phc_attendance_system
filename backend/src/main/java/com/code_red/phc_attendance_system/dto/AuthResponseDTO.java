package com.code_red.phc_attendance_system.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDTO {
	private String token;
	private List<String> roles;
}
