package kth.datalake_backend.Controller;

import kth.datalake_backend.Entity.Nodes.Dataset;
import kth.datalake_backend.Service.AuthService;
import kth.datalake_backend.Service.DatasetService;
import kth.datalake_backend.Service.PatientService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/dataset")
public class DatasetController {
    private final DatasetService datasetService;

    public DatasetController(DatasetService datasetService) {
        this.datasetService = datasetService;
    }

    @GetMapping("/getAllDatasets")
    public List<Dataset> getUserAllUsers() throws IOException {
        return datasetService.getAllDatasets();
    }
}
