package kth.datalake_backend.Controller;

import kth.datalake_backend.Payload.Response.MessageResponse;
import kth.datalake_backend.Service.PatientService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/patient")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {this.patientService = patientService;}

    @PostMapping("/input")
    public ResponseEntity inputData(@RequestParam("file") MultipartFile files, @RequestParam("name") String name) throws IOException {
        patientService.loadData(files, name);
        return ResponseEntity.ok(new MessageResponse("successfully entered new patients"));
    }

    @PostMapping("/input/symptoms")
    public ResponseEntity inputSymptoms(@RequestParam("file") MultipartFile files, @RequestParam("name") String name) throws IOException{
        patientService.loadSymptoms(files, name);
        return ResponseEntity.ok(new MessageResponse("successfully entered new symptoms"));
    }

}
