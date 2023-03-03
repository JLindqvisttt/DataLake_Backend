package kth.datalake_backend.Controller;


import kth.datalake_backend.Payload.Request.PatientRequest.InputDataRequest;
import kth.datalake_backend.Service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/patient")
public class PatientController {
  private final PatientService patientService;

  public PatientController(PatientService patientService) {
    this.patientService = patientService;
  }

//  @PostMapping("/input")
//  public ResponseEntity inputData(@RequestBody InputDataRequest inputDataRequest) throws IOException {
//    return patientService.loadData(inputDataRequest.getFiles(), inputDataRequest.getName());
//  }

  @PostMapping("/input")
  public ResponseEntity inputData(@RequestParam("file") MultipartFile files, @RequestParam("name") String name) throws IOException {
    return patientService.loadData(files, name);
  }

  @PostMapping("/input/symptoms")
  public ResponseEntity inputSymptoms(@RequestParam("file") MultipartFile files, @RequestParam("name") String name) throws IOException {
    return patientService.loadSymptoms(files, name);
  }

  @GetMapping("/getAllDatasets")
  public List<String> getDatasets() throws IOException {
    return patientService.getDataSets();
  }

  @GetMapping("/getPatientsByDataset")
  public List<Patient> getPatientsAsJson( @RequestParam("name") String name) throws IOException {
    return patientService.getPatientsAsJson(name);
  }

  @GetMapping("/getAllPatients")
  public List<Patient> getAllPatients(){
    return patientService.getAllPatients();
  }
}
