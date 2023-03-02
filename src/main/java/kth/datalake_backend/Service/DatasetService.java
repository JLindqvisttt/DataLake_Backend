package kth.datalake_backend.Service;

import kth.datalake_backend.Entity.Nodes.Dataset;
import kth.datalake_backend.Payload.Response.MessageResponse;
import kth.datalake_backend.Repository.Nodes.DatasetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class DatasetService {

    @Autowired
    DatasetRepository datasetRepository;
    public List<Dataset> getAllDatasets() throws IOException {
        return datasetRepository.findAll();
    }


}
