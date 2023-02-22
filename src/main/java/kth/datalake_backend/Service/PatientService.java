package kth.datalake_backend.Service;

import kth.datalake_backend.Entity.Patient;
import kth.datalake_backend.Entity.Treatment;
import kth.datalake_backend.Payload.Response.MessageResponse;
import kth.datalake_backend.Repository.PatientRepository;
import kth.datalake_backend.Repository.TreatmentRepository;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;

@Service
public class PatientService {

  @Autowired
  PatientRepository patientRepository;
  @Autowired
  TreatmentRepository treatmentRepository;

  //https://por-porkaew15.medium.com/how-to-import-excel-by-spring-boot-2624367c8468
  public ResponseEntity<?> loadData(MultipartFile file) throws IOException {
    XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
    XSSFSheet worksheet = workbook.getSheetAt(0);

    ArrayList<Treatment> treatmentList = treatmentRepository.findAll();
    ArrayList<String> treatmentName = new ArrayList<>();
    for (Treatment t:treatmentList)
      treatmentName.add(t.getTreatment());

    for (int index = 0; index < worksheet.getPhysicalNumberOfRows(); index++) {
      if (index > 0) break;

      Patient patient = new Patient();
      Treatment treatment;
      XSSFRow row = worksheet.getRow(index);

      if(row.getCell(0) == null){
        System.out.println("no id found");
        continue;
      }

      patient.setSubjectId(Integer.parseInt(row.getCell(0).toString().replace(".0", "")));

      if (row.getCell(2) == null) patient.setAge(-1);
      else patient.setAge(Integer.parseInt(row.getCell(2).toString().replace(".0", "")));

      if (row.getCell(1) == null) patient.setGender("null");
      else patient.setGender(row.getCell(1).toString().replace(".0", ""));

      if(row.getCell(3) == null) patient.setEthnicity("null");
      else patient.setEthnicity(row.getCell(3).toString().replace(".0", ""));

      if(row.getCell(10) == null) treatment = new Treatment("Unknown");
      else treatment = new Treatment(row.getCell(10).toString());

      treatmentNode(treatmentName, patient, treatment, treatmentList);

      patientRepository.save(patient);

    }

    return ResponseEntity.ok(new MessageResponse("testing"));
  }

  private void treatmentNode(ArrayList<String> treatmentName, Patient patient, Treatment treatment, ArrayList<Treatment> treatmentList ){
    if(!treatmentName.contains(treatment.getTreatment())){
      treatmentRepository.save(treatment);
      treatmentName.add(treatment.getTreatment());
      treatmentList.add(treatment);
      patient.setTreatment(treatment);
    }
    else
      for (Treatment a: treatmentList)
        if(a.getTreatment().equals(treatment.getTreatment()))
          patient.setTreatment(a);
  }
}
