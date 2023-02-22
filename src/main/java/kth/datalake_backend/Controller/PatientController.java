package kth.datalake_backend.Controller;

import kth.datalake_backend.Payload.Response.MessageResponse;
import kth.datalake_backend.Service.PatientService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/patient")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {this.patientService = patientService;}

    @PostMapping("/input")
    public ResponseEntity inputData(){

        return ResponseEntity.ok(new MessageResponse("input"));
    }
}
