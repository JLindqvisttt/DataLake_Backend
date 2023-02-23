package kth.datalake_backend.Service;

import jdk.dynalink.linker.LinkerServices;
import kth.datalake_backend.Entity.Nodes.*;
import kth.datalake_backend.Payload.Response.MessageResponse;
import kth.datalake_backend.Repository.Nodes.*;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PatientService {

  @Autowired
  PatientRepository patientRepository;
  @Autowired
  TreatmentRepository treatmentRepository;
  @Autowired
  CauseOfDeathRepository causeOfDeathRepository;
  @Autowired
  OverallSurvivalStatusRepository overallSurvivalStatusRepository;
  @Autowired
  NewMalignancyRepository newMalignancyRepository;

  @Autowired
  SymptomsRepository symptomsRepository;

  //https://por-porkaew15.medium.com/how-to-import-excel-by-spring-boot-2624367c8468
  public ResponseEntity<?> loadData(MultipartFile file, String name) throws IOException {
    XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
    XSSFSheet worksheet = workbook.getSheetAt(0);

    ArrayList<Treatment> treatmentList = treatmentRepository.findAll();
    ArrayList<String> treatmentName = new ArrayList<>();

    ArrayList<CauseOfDeath> causeOfDeathList = causeOfDeathRepository.findAll();
    ArrayList<String> causeOfDeathName = new ArrayList<>();

    ArrayList<OverAllSurvivalStatus> overallSurvivalStatusList = overallSurvivalStatusRepository.findAll();
    ArrayList<String> overallSurvivalStatusName = new ArrayList<>();

    ArrayList<NewMalignancy> newMalignancyList = newMalignancyRepository.findAll();
    ArrayList<String> newMalignancyName = new ArrayList<>();

    for (Treatment t:treatmentList)
      treatmentName.add(t.getTreatment());
    //TODO bör det inte finnas en for each för varje node?

    for (int index = 0; index < worksheet.getPhysicalNumberOfRows(); index++) {

      if (index > 0) {
        Patient patient = new Patient();
        Treatment treatment;
        CauseOfDeath causeOfDeath = new CauseOfDeath();
        OverAllSurvivalStatus overallSurvivalStatus = new OverAllSurvivalStatus();
        NewMalignancy newMalignancy = new NewMalignancy();
        XSSFRow row = worksheet.getRow(index);

        if(row.getCell(0) == null){
          System.out.println("no id found");
          continue;
        }

        patient.setSubjectId(Integer.parseInt(row.getCell(0).toString().replace(".0", "")));

        patient.setDataset(name);

        if (row.getCell(2) == null) patient.setAge(-1);
        else patient.setAge(Integer.parseInt(row.getCell(2).toString().replace(".0", "")));

        if (row.getCell(1) == null) patient.setGender("null");
        else patient.setGender(row.getCell(1).toString());

        if(row.getCell(3) == null) patient.setEthnicity("null");
        else patient.setEthnicity(row.getCell(3).toString());

        if (row.getCell(17) == null) patient.setRelapse("null");
        else patient.setRelapse(row.getCell(17).toString());

        if (row.getCell(19) == null) patient.setSurvivalTime(-1);
        else patient.setSurvivalTime(Double.parseDouble(row.getCell(19).toString()));

        if (row.getCell(20) == null) patient.setRelapseTime(-1);
        else patient.setRelapseTime(Double.parseDouble(row.getCell(20).toString()));

        if (row.getCell(21) == null) patient.setFailureFreeSurvivalStatus("null");
        else patient.setFailureFreeSurvivalStatus(row.getCell(21).toString());

        if (row.getCell(22) == null) patient.setFailureFreeSurvivalTime(-1);
        else patient.setFailureFreeSurvivalTime(Double.parseDouble(row.getCell(22).toString()));


        //nodes
        if(row.getCell(10) == null) treatment = new Treatment("Unknown");
        else treatment = new Treatment(row.getCell(10).toString());
        treatmentNode(treatmentName, patient, treatment, treatmentList);

        if(row.getCell(15) == null) overallSurvivalStatus.setOverAllSurvivalStatus("Unknown");
        else overallSurvivalStatus.setOverAllSurvivalStatus(row.getCell(15).toString());
        overallSurvivalStatusNode(overallSurvivalStatusName, patient, overallSurvivalStatus, overallSurvivalStatusList);

        if(!overallSurvivalStatus.getOverAllSurvivalStatus().equals("Alive")){
          if(row.getCell(16) == null) causeOfDeath.setCauseOfDeath("Unknown");
          else causeOfDeath.setCauseOfDeath(row.getCell(16).toString());
          causeOfDeathNode(causeOfDeathName, patient, causeOfDeath, causeOfDeathList);
        }

        if(row.getCell(18) == null) newMalignancy.setNewMalignancy("Unknown");
        else newMalignancy.setNewMalignancy(row.getCell(18).toString());
        newMalignancyNode(newMalignancyName, patient, newMalignancy, newMalignancyList);

        patientRepository.save(patient);
      }
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

  private void causeOfDeathNode(ArrayList<String> causeOfDeathName, Patient patient, CauseOfDeath causeOfDeath, ArrayList<CauseOfDeath> causeOfDeathsList ){
    if(!causeOfDeathName.contains(causeOfDeath.getCauseOfDeath())){
      causeOfDeathRepository.save(causeOfDeath);
      causeOfDeathName.add(causeOfDeath.getCauseOfDeath());
      causeOfDeathsList.add(causeOfDeath);
      patient.setCauseOfDeath(causeOfDeath);
    }
    else
      for (CauseOfDeath a: causeOfDeathsList)
        if(a.getCauseOfDeath().equals(causeOfDeath.getCauseOfDeath()))
          patient.setCauseOfDeath(a);
  }

  private void overallSurvivalStatusNode(ArrayList<String> overallSurvivalStatusName, Patient patient, OverAllSurvivalStatus overallSurvivalStatus, ArrayList<OverAllSurvivalStatus> overallSurvivalStatusList ){
    if(!overallSurvivalStatusName.contains(overallSurvivalStatus.getOverAllSurvivalStatus())){
      overallSurvivalStatusRepository.save(overallSurvivalStatus);
      overallSurvivalStatusName.add(overallSurvivalStatus.getOverAllSurvivalStatus());
      overallSurvivalStatusList.add(overallSurvivalStatus);
      patient.setOverAllSurvivalStatus(overallSurvivalStatus);
    }
    else
      for (OverAllSurvivalStatus a: overallSurvivalStatusList)
        if(a.getOverAllSurvivalStatus().equals(overallSurvivalStatus.getOverAllSurvivalStatus()))
          patient.setOverAllSurvivalStatus(a);
  }

  private void newMalignancyNode(ArrayList<String> newMalignancyName, Patient patient, NewMalignancy newMalignancy, ArrayList<NewMalignancy> newMalignancyList) {
    if(!newMalignancyName.contains(newMalignancy.getNewMalignancy())){
      newMalignancyRepository.save(newMalignancy);
      newMalignancyName.add(newMalignancy.getNewMalignancy());
      newMalignancyList.add(newMalignancy);
      patient.setNewMalignancy(newMalignancy);
    }
    else
      for (NewMalignancy a: newMalignancyList)
        if(a.getNewMalignancy().equals(newMalignancy.getNewMalignancy()))
          patient.setNewMalignancy(a);
  }



  public ResponseEntity<?> loadSymptoms(MultipartFile file, String name) throws IOException {
    XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
    XSSFSheet worksheet = workbook.getSheetAt(0);

    List<Patient> patientList = patientRepository.findAll();
    List<Integer> patientId = new ArrayList<>();

    ArrayList<Symptoms> symptomsList = symptomsRepository.findAll();
    ArrayList<String> symptomsName = new ArrayList<>();

    for (Patient t : patientList)
      patientId.add(t.getSubjectId());

    /*
    for (Symptoms t:symptomsList)
      symptomsName.add(t.getSymptom());
    */

    for (int index = 0; index < worksheet.getPhysicalNumberOfRows(); index++) {
      if (index > 0) {
        Patient patient = new Patient();
        Symptoms symptoms = new Symptoms();
        XSSFRow row = worksheet.getRow(index);

        if (row.getCell(0) == null) {
          System.out.println("no id found");
          continue;
        }

        //probs hitta en bättre lösning
        if (!patientId.contains(Integer.parseInt(row.getCell(0).toString().replace(".0", "")))){
          System.out.println("patient with that id not found");
        }
        int id = Integer.parseInt(row.getCell(0).toString().replace(".0", ""));
        for (Patient a: patientList)
          if(a.getSubjectId() == id)
            patient = a;

        if(row.getCell(1) == null || row.getCell(2) == null) continue;
        else {
          symptoms.setSymptom(row.getCell(1).toString());
          symptoms.setSeverity(Integer.parseInt(row.getCell(2).toString().replace(".0","")));
        }

        symptomsNode(symptoms, patient, symptomsList);


      }
    }
    return ResponseEntity.ok(new MessageResponse("testing"));
  }

  private void symptomsNode(Symptoms symptoms, Patient patient, ArrayList<Symptoms> symptomsList){
    System.out.println("test");
    boolean flag = true;
    for(int i = 0; i < symptomsList.size(); i++){
      if(symptomsList.get(i).getSymptom().equals(symptoms.getSymptom()) && symptomsList.get(i).getSeverity() == symptoms.getSeverity()){
        System.out.println("exist");
        flag = false;
        patient.setSymptoms(symptomsList.get(i));
      }
    }
    if(flag){
      symptomsRepository.save(symptoms);
      patient.setSymptoms(symptoms);
      symptomsList.add(symptoms);
    }
  }
}
