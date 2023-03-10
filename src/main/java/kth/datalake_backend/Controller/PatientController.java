package kth.datalake_backend.Controller;

import kth.datalake_backend.Entity.Nodes.Patient;
import kth.datalake_backend.Service.PatientService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * RestController for patients
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/patient")
public class PatientController {
    private final PatientService patientService;

    /**
     * Class constructor
     *
     * @param patientService specify the patientservice to use
     */
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    /**
     * Input data from dataset to database
     *
     * @param files the dataset to add to database
     * @param name  the name of the dataset
     * @return a ResponseEntity
     * @throws IOException
     */
    @PostMapping("/input")
    public ResponseEntity<?> inputData(@RequestParam("file") MultipartFile files, @RequestParam("name") String name) throws IOException {
        return patientService.loadData(files, name);
    }

    /**
     * Input symptoms from dataset to database
     *
     * @param files the dataset to add to the database
     * @param name  the name of the dataset
     * @return a ResponseEntity
     * @throws IOException
     */
    @PostMapping("/input/symptoms")
    public ResponseEntity<?> inputSymptoms(@RequestParam("file") MultipartFile files, @RequestParam("name") String name) throws IOException {
        return patientService.loadSymptoms(files, name);
    }

    /**
     * Get available dataset
     *
     * @return a list of datasets
     */
    @GetMapping("/getAllDatasets")
    public List<String> getDatasets() {
        return patientService.getDataSets();
    }

    /**
     * Get all patients in a specific dataset
     *
     * @param name the name of the dataset
     * @return a list of patients
     */
    @GetMapping("/getPatientsByDataset")
    public List<Patient> getPatientsAsJson(@RequestParam("name") String name) {
        return patientService.getPatientsAsJson(name);
    }

    /**
     * Get all patients in database
     *
     * @return a list of patients
     */
    @GetMapping("/getAllPatients")
    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }
}
