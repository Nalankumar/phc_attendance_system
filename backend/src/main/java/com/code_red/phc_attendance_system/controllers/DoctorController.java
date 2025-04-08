package com.code_red.phc_attendance_system.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.code_red.phc_attendance_system.dto.DoctorDTO;
import com.code_red.phc_attendance_system.dto.FingerprintDTO;
import com.code_red.phc_attendance_system.dto.ShiftDTO;
import com.code_red.phc_attendance_system.entities.AppUser;
import com.code_red.phc_attendance_system.entities.Doctor;
import com.code_red.phc_attendance_system.entities.Facility;
import com.code_red.phc_attendance_system.entities.Shift;
import com.code_red.phc_attendance_system.enums.ShiftStatus;
import com.code_red.phc_attendance_system.repositories.ShiftRepository;
import com.code_red.phc_attendance_system.services.DoctorService;
import com.code_red.phc_attendance_system.services.FacilityService;

import io.jsonwebtoken.lang.Collections;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

	@Autowired
	private DoctorService doctorService;
	
	@Autowired
	private FacilityService facilityService;
	
	@Autowired
	private ShiftRepository shiftRepository;
	
	@PostMapping("/register")
	public ResponseEntity<Doctor> registerDoctor(@RequestBody DoctorDTO doctordto) {	
		return new ResponseEntity<>(doctorService.register(doctordto), HttpStatus.CREATED);
	}
	
	@GetMapping("/")
	public List<Doctor> getAllDoctors(){
		return doctorService.getAllDoctors();
	}
	

	
	@PutMapping("/{doctorId}/update-shift")
	public ResponseEntity<Doctor> updateDoctorShift(@PathVariable Long doctorId, 
	                                                @RequestBody ShiftDTO newShift) { 
	    if (doctorId == null) {
	        throw new IllegalArgumentException("Doctor ID cannot be null");
	    }

	    
	    Doctor updatedDoctor = doctorService.updateDoctorShift(doctorId, newShift);
	    
	    return ResponseEntity.ok(updatedDoctor);
	}


	
	@GetMapping("/{facilityId}/doctors")
	public ResponseEntity<List<DoctorDTO>> getDoctorsByBlock(@PathVariable Long facilityId) {  
	    Facility facility = facilityService.findById(facilityId);
	    
	    if (facility == null) {
	        return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NOT_FOUND);
	    }
	    
	    List<DoctorDTO> doctors = doctorService.getByFacility(facility);
	   
	    return new ResponseEntity<>(doctors, HttpStatus.OK);
	}

	@GetMapping("/fingerprints/{facilityId}")
	public ResponseEntity<List<FingerprintDTO>> getFingerprints(@PathVariable Long facilityId){
		Facility facility = facilityService.findById(facilityId);
	    
	    if (facility == null) {
	        return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NOT_FOUND);
	    }
	    List<FingerprintDTO> fingerprints = doctorService.getIdAndFingerprintByFacility(facility);
	    return new ResponseEntity<>(fingerprints, HttpStatus.OK);
	}
}
