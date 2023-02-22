package kth.datalake_backend.Service;

import kth.datalake_backend.Payload.Response.MessageResponse;
import kth.datalake_backend.Repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

  @Autowired
  PatientRepository patientRepository;

  public ResponseEntity<?> loadData() {
    return ResponseEntity.ok(new MessageResponse("testing"));
  }
}
