package kth.datalake_backend.Service;

import kth.datalake_backend.Payload.Response.MessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    public ResponseEntity<?> loadData(){
        return ResponseEntity.ok(new MessageResponse("testing"));
    }
}
