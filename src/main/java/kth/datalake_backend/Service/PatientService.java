package kth.datalake_backend.Service;

import kth.datalake_backend.Entity.Nodes.*;
import kth.datalake_backend.Payload.Response.MessageResponse;
import kth.datalake_backend.Repository.Nodes.*;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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

      List<Patient> patientList = patientRepository.findAll();
      List<Integer> patientId = new ArrayList<>();
        for(Patient t:patientList)
            patientId.add(t.getSubjectId());

    ArrayList<Treatment> treatmentList = treatmentRepository.findAll();
    ArrayList<String> treatmentName = new ArrayList<>();
      for (Treatment t:treatmentList)
          treatmentName.add(t.getTreatment());

    ArrayList<CauseOfDeath> causeOfDeathList = causeOfDeathRepository.findAll();
    ArrayList<String> causeOfDeathName = new ArrayList<>();
      for (CauseOfDeath t:causeOfDeathList)
          causeOfDeathName.add(t.getCauseOfDeath());

    ArrayList<OverAllSurvivalStatus> overallSurvivalStatusList = overallSurvivalStatusRepository.findAll();
    ArrayList<String> overallSurvivalStatusName = new ArrayList<>();
      for (OverAllSurvivalStatus t:overallSurvivalStatusList)
          overallSurvivalStatusName.add(t.getOverAllSurvivalStatus());

    ArrayList<NewMalignancy> newMalignancyList = newMalignancyRepository.findAll();
    ArrayList<String> newMalignancyName = new ArrayList<>();
      for (NewMalignancy t:newMalignancyList)
          newMalignancyName.add(t.getNewMalignancy());

      HashMap<String, Integer> rowNumbers = null;

    for (int index = 0; index < worksheet.getPhysicalNumberOfRows(); index++) {
    if (index > 0) {
        Patient patient = new Patient();
        Treatment treatment;
        CauseOfDeath causeOfDeath = new CauseOfDeath();
        OverAllSurvivalStatus overallSurvivalStatus = new OverAllSurvivalStatus();
        NewMalignancy newMalignancy = new NewMalignancy();
        XSSFRow row = worksheet.getRow(index);

        if (row.getCell(0) == null) {
            System.out.println("no id found");
            continue;
        }
        if (patientId.contains(Integer.parseInt(row.getCell(rowNumbers.get("id")).toString().replace(".0", ""))))
            continue;

        patient.setSubjectId(Integer.parseInt(row.getCell(rowNumbers.get("id")).toString().replace(".0", "")));

        patient.setDataset(name);


        if (!rowNumbers.containsKey("age") || row.getCell(rowNumbers.get("age")) == null ) patient.setAge(-1);
        else patient.setAge(Integer.parseInt(row.getCell(rowNumbers.get("age")).toString().replace(".0", "")));

        if (!rowNumbers.containsKey("gender") || row.getCell(rowNumbers.get("gender")) == null) patient.setGender("null");
        else patient.setGender(row.getCell(rowNumbers.get("gender")).toString());

        if (!rowNumbers.containsKey("ethnicity") || row.getCell(rowNumbers.get("ethnicity")) == null) patient.setEthnicity("null");
        else patient.setEthnicity(row.getCell(rowNumbers.get("ethnicity")).toString());

        if (!rowNumbers.containsKey("relapse") || row.getCell(rowNumbers.get("relapse")) == null) patient.setRelapse("null");
        else patient.setRelapse(row.getCell(rowNumbers.get("relapse")).toString());

        if (!rowNumbers.containsKey("overall survival time") || row.getCell(rowNumbers.get("overall survival time")) == null) patient.setSurvivalTime(-1);
        else patient.setSurvivalTime(Double.parseDouble(row.getCell(rowNumbers.get("overall survival time")).toString()));

        if (!rowNumbers.containsKey("relapse time") || row.getCell(rowNumbers.get("relapse time")) == null) patient.setRelapseTime(-1);
        else patient.setRelapseTime(Double.parseDouble(row.getCell(rowNumbers.get("relapse time")).toString()));

        if (!rowNumbers.containsKey("failure free survival") || row.getCell(rowNumbers.get("failure free survival")) == null) patient.setFailureFreeSurvivalStatus("null");
        else patient.setFailureFreeSurvivalStatus(row.getCell(rowNumbers.get("failure free survival")).toString());

        if (!rowNumbers.containsKey("failure free survival time") || row.getCell(rowNumbers.get("failure free survival time")) == null) patient.setFailureFreeSurvivalTime(-1);
        else patient.setFailureFreeSurvivalTime(Double.parseDouble(row.getCell(rowNumbers.get("failure free survival time")).toString()));



        if (!rowNumbers.containsKey("treatment drug") || row.getCell(rowNumbers.get("treatment drug")) == null) treatment = new Treatment("Unknown");
        else treatment = new Treatment(row.getCell(rowNumbers.get("treatment drug")).toString());
        treatmentNode(treatmentName, patient, treatment, treatmentList);

        if (!rowNumbers.containsKey("overall survival status") || row.getCell(rowNumbers.get("overall survival status")) == null) overallSurvivalStatus.setOverAllSurvivalStatus("Unknown");
        else overallSurvivalStatus.setOverAllSurvivalStatus(row.getCell(rowNumbers.get("overall survival status")).toString());
        overallSurvivalStatusNode(overallSurvivalStatusName, patient, overallSurvivalStatus, overallSurvivalStatusList);

        if (!overallSurvivalStatus.getOverAllSurvivalStatus().equals("Alive") && !overallSurvivalStatus.getOverAllSurvivalStatus().equals("Unknown")) {
            if (!rowNumbers.containsKey("cause of death") || row.getCell(rowNumbers.get("cause of death")) == null) causeOfDeath.setCauseOfDeath("Unknown");
            else causeOfDeath.setCauseOfDeath(row.getCell(rowNumbers.get("cause of death")).toString());
            causeOfDeathNode(causeOfDeathName, patient, causeOfDeath, causeOfDeathList);
        }

        if (!rowNumbers.containsKey("new malignancy") || row.getCell(rowNumbers.get("new malignancy")) == null) newMalignancy.setNewMalignancy("Unknown");
        else newMalignancy.setNewMalignancy(row.getCell(rowNumbers.get("new malignancy")).toString());
        newMalignancyNode(newMalignancyName, patient, newMalignancy, newMalignancyList);

        patientRepository.save(patient);
        }
        else{
            rowNumbers = setRowNumbers(worksheet,index);
        }
    }
        return ResponseEntity.ok(new MessageResponse("testing"));
    }

    public ResponseEntity<?> loadFile(MultipartFile file, String name) throws IOException {
        ArrayList<Patient> patients = new ArrayList<>();
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(1);

        ArrayList<Treatment> treatmentList = treatmentRepository.findAll();
        ArrayList<String> treatmentName = new ArrayList<>();

        for (Treatment t : treatmentList)
            treatmentName.add(t.getTreatment());

        int previousID = 0;
        for (int index = 0; index < worksheet.getPhysicalNumberOfRows(); index++) {
            if (index > 0) {
                Patient patient = new Patient();
                XSSFRow row = worksheet.getRow(index);

                if (row.getCell(0) == null) {
                    System.out.println("no id found");
                    continue;
                }

                if (index == 1) {
                    previousID = Integer.parseInt(row.getCell(0).toString());
                    insertPatient(row, patient, name, treatmentName, treatmentList, patients);
                } else {
                    if (Integer.parseInt(row.getCell(0).toString()) != previousID) {
                        insertPatient(row, patient,  name, treatmentName, treatmentList, patients);
                        previousID = Integer.parseInt(row.getCell(0).toString());
                        System.out.println(previousID);

                    } else {
                        System.out.println("in else statement");
                        if (!row.getCell(4).toString().equals(worksheet.getRow(index - 1).getCell(4).toString())) {
                            for (Patient p : patients) {
                                System.out.println("in foreach patient");
                                if (p.getSubjectId() == Integer.parseInt(row.getCell(0).toString())) {
                                    p.setTreatment(new Treatment(row.getCell(4).toString()));
                                }
                            }
                        }
                    }
                }
            }
        }
        int i = 0;
        for (Patient p : patients) {
            System.out.println("in save patients to database " + i);
            patientRepository.save(p);
            i++;
        }
        return ResponseEntity.ok(new MessageResponse("testing"));
    }

    private void insertPatient(XSSFRow row, Patient patient, String name, ArrayList<String> treatmentName, ArrayList<Treatment> treatmentList,  ArrayList<Patient> patients) {
        Treatment treatment;
        patient.setSubjectId(Integer.parseInt(row.getCell(0).toString().replace(".0", "")));
        patient.setDataset(name);

        if (row.getCell(1) == null) patient.setAge(-1);
        else patient.setAge(Integer.parseInt(row.getCell(1).toString().replace(".0", "")));

        if (row.getCell(2) == null) patient.setGender("null");
        else patient.setGender(row.getCell(2).toString());

        if (row.getCell(3) == null) patient.setEthnicity("null");
        else patient.setEthnicity(row.getCell(3).toString());

        if (row.getCell(4) == null) treatment = new Treatment("Unknown");
        else treatment = new Treatment(row.getCell(4).toString());
        treatmentNode(treatmentName, patient, treatment, treatmentList);
        patients.add(patient);
    }

    private void treatmentNode(ArrayList<String> treatmentName, Patient patient, Treatment treatment, ArrayList<Treatment> treatmentList) {
        if (!treatmentName.contains(treatment.getTreatment())) {
            treatmentRepository.save(treatment);
            treatmentName.add(treatment.getTreatment());
            treatmentList.add(treatment);
            patient.setTreatment(treatment);
        } else
            for (Treatment a : treatmentList)
                if (a.getTreatment().equals(treatment.getTreatment()))
                    patient.setTreatment(a);
    }

    private void causeOfDeathNode(ArrayList<String> causeOfDeathName, Patient patient, CauseOfDeath causeOfDeath, ArrayList<CauseOfDeath> causeOfDeathsList) {
        if (!causeOfDeathName.contains(causeOfDeath.getCauseOfDeath())) {
            causeOfDeathRepository.save(causeOfDeath);
            causeOfDeathName.add(causeOfDeath.getCauseOfDeath());
            causeOfDeathsList.add(causeOfDeath);
            patient.setCauseOfDeath(causeOfDeath);
        } else
            for (CauseOfDeath a : causeOfDeathsList)
                if (a.getCauseOfDeath().equals(causeOfDeath.getCauseOfDeath()))
                    patient.setCauseOfDeath(a);
    }

    private void overallSurvivalStatusNode(ArrayList<String> overallSurvivalStatusName, Patient patient, OverAllSurvivalStatus overallSurvivalStatus, ArrayList<OverAllSurvivalStatus> overallSurvivalStatusList) {
        if (!overallSurvivalStatusName.contains(overallSurvivalStatus.getOverAllSurvivalStatus())) {
            overallSurvivalStatusRepository.save(overallSurvivalStatus);
            overallSurvivalStatusName.add(overallSurvivalStatus.getOverAllSurvivalStatus());
            overallSurvivalStatusList.add(overallSurvivalStatus);
            patient.setOverAllSurvivalStatus(overallSurvivalStatus);
        } else
            for (OverAllSurvivalStatus a : overallSurvivalStatusList)
                if (a.getOverAllSurvivalStatus().equals(overallSurvivalStatus.getOverAllSurvivalStatus()))
                    patient.setOverAllSurvivalStatus(a);
    }

    private void newMalignancyNode(ArrayList<String> newMalignancyName, Patient patient, NewMalignancy newMalignancy, ArrayList<NewMalignancy> newMalignancyList) {
        if (!newMalignancyName.contains(newMalignancy.getNewMalignancy())) {
            newMalignancyRepository.save(newMalignancy);
            newMalignancyName.add(newMalignancy.getNewMalignancy());
            newMalignancyList.add(newMalignancy);
            patient.setNewMalignancy(newMalignancy);
        } else
            for (NewMalignancy a : newMalignancyList)
                if (a.getNewMalignancy().equals(newMalignancy.getNewMalignancy()))
                    patient.setNewMalignancy(a);
    }

    public ResponseEntity<?> loadSymptoms(MultipartFile file, String name) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);

        List<Patient> patientList = patientRepository.findAll();
        List<Integer> patientId = new ArrayList<>();

        ArrayList<Symptoms> symptomsList = symptomsRepository.findAll();

        for (Patient t : patientList)
          patientId.add(t.getSubjectId());

        HashMap<String, Integer> rowNumbers = null;
        int id = Integer.MAX_VALUE;

        for (int index = 0; index < worksheet.getPhysicalNumberOfRows(); index++) {
          if (index > 0) {
            Patient patient = new Patient();
            Symptoms symptoms = new Symptoms();
            XSSFRow row = worksheet.getRow(index);

            if (row.getCell(rowNumbers.get("id")) == null) {
              System.out.println("no id found");
              continue;
            }

            if(id != Integer.MAX_VALUE && id != Integer.parseInt(row.getCell(rowNumbers.get("id")).toString().replace(".0", ""))){
                for (Patient a: patientList)
                    if(a.getSubjectId() == id)
                        patientRepository.save(a);
            }

            id = Integer.parseInt(row.getCell(rowNumbers.get("id")).toString().replace(".0", ""));

            if (!patientId.contains(id)){
                System.out.println("patient with that id not found");
                continue;
            }

            for (Patient a: patientList)
              if(a.getSubjectId() == id)
                  patient = a;


            if(row.getCell(rowNumbers.get("symptom")) == null || row.getCell(rowNumbers.get("grade")) == null) continue;
            else {
              symptoms.setSymptom(row.getCell(rowNumbers.get("symptom")).toString());
              symptoms.setSeverity(Integer.parseInt(row.getCell(rowNumbers.get("grade")).toString().replace(".0","")));
            }
            symptomsNode(symptoms, patient, symptomsList);
          }
          else{
              rowNumbers = setRowNumbers(worksheet,index);
          }
        }
        return ResponseEntity.ok(new MessageResponse("testing"));
      }

    private void symptomsNode(Symptoms symptoms, Patient patient, ArrayList<Symptoms> symptomsList){
        boolean flag = true;
        for (Symptoms value : symptomsList) {
            if (value.getSymptom().equals(symptoms.getSymptom()) && value.getSeverity() == symptoms.getSeverity()) {
                flag = false;
                patient.setSymptoms(value);
            }
        }
        if(flag){
          symptomsRepository.save(symptoms);
          patient.setSymptoms(symptoms);
          symptomsList.add(symptoms);
        }
   }

    private HashMap<String, Integer> setRowNumbers(XSSFSheet worksheet, int index){
        HashMap<String, Integer> rowNumbers = new HashMap<>();
        XSSFRow row = worksheet.getRow(index);
        for (Cell r:row) {
            switch (r.toString()) {
                case "PHATOM_ID" -> rowNumbers.put("id", r.getColumnIndex());//id
                case "GENDER" -> rowNumbers.put("gender", r.getColumnIndex());//gender
                case "AGE" -> rowNumbers.put("age", r.getColumnIndex());//age (years)
                case "RACE" -> rowNumbers.put("ethnicity", r.getColumnIndex());//race
                case "PD" -> rowNumbers.put("relapse", r.getColumnIndex());//relapse time
                case "OS_TIME" -> rowNumbers.put("overall survival time", r.getColumnIndex());//overall survival time (months)
                case "PD_TIME" -> rowNumbers.put("relapse time", r.getColumnIndex());//relapse time (months)
                case "PFS_STATUS" -> rowNumbers.put("failure free survival", r.getColumnIndex());//failure free survival
                case "PFS_TIME" -> rowNumbers.put("failure free survival time", r.getColumnIndex());//failure free survival time (months)
                case "TRT_ARM_LABEL" -> rowNumbers.put("treatment drug", r.getColumnIndex());//treatment drug
                case "STATUS" -> rowNumbers.put("overall survival status", r.getColumnIndex());//overall survival status
                case "CAUSEDTH" -> rowNumbers.put("cause of death", r.getColumnIndex());//cause of death
                case "NEW_MALIG" -> rowNumbers.put("new malignancy", r.getColumnIndex());//new malignancy
                case "AE_NAME" -> rowNumbers.put("symptom", r.getColumnIndex());//symptom
                case "AE_GRADE" -> rowNumbers.put("grade", r.getColumnIndex());//severity grade
            }
        }
        return rowNumbers;
    }
}
