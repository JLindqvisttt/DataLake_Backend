package kth.datalake_backend.Controller;

import kth.datalake_backend.Entity.Nodes.Patient;
import kth.datalake_backend.Payload.Response.MessageResponse;
import kth.datalake_backend.Service.PatientService;

import org.json.JSONArray;
import org.json.JSONObject;
import org.neo4j.driver.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/patient")
public class PatientController {
  private final PatientService patientService;

  public PatientController(PatientService patientService) {
    this.patientService = patientService;
  }

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
