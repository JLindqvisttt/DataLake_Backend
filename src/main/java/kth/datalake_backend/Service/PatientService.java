package kth.datalake_backend.Service;

import com.epam.parso.SasFileReader;
import com.epam.parso.impl.SasFileReaderImpl;
import kth.datalake_backend.Entity.Nodes.*;
import kth.datalake_backend.Payload.Response.MessageResponse;
import kth.datalake_backend.Repository.Nodes.*;

import kth.datalake_backend.Service.Util.SasToXlsxConverter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
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
  DatasetRepository datasetRepository;
  @Autowired
  SymptomsRepository symptomsRepository;

  //https://por-porkaew15.medium.com/how-to-import-excel-by-spring-boot-2624367c8468
  public ResponseEntity<?> loadData(MultipartFile file, String name) throws IOException {
    XSSFSheet worksheet;
    if (file.getOriginalFilename().contains(".sas7bdat")) {
      SasToXlsxConverter converter = new SasToXlsxConverter();
      InputStream inputStream = file.getInputStream();
      SasFileReader sasFileReader = new SasFileReaderImpl(inputStream);
      worksheet = converter.convertSasToXlsx(sasFileReader);
    } else if (file.getOriginalFilename().contains(".xlsx")) {
      XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
      worksheet = workbook.getSheetAt(0);
    } else return ResponseEntity.badRequest().body(new MessageResponse("File type not supported"));


    //Get patients in database
    List<Patient> patientList = patientRepository.findAllByDataset(name);
    // Get treatment list
    ArrayList<Treatment> treatmentList = treatmentRepository.findAll();
    ArrayList<String> treatmentName = new ArrayList<>();
    // Add treatments from list to treatmentName
    for (Treatment t : treatmentList)
      treatmentName.add(t.getTreatment());

    ArrayList<CauseOfDeath> causeOfDeathList = causeOfDeathRepository.findAll();
    ArrayList<String> causeOfDeathName = new ArrayList<>();
    for (CauseOfDeath t : causeOfDeathList)
      causeOfDeathName.add(t.getCauseOfDeath());

    ArrayList<OverAllSurvivalStatus> overallSurvivalStatusList = overallSurvivalStatusRepository.findAll();
    ArrayList<String> overallSurvivalStatusName = new ArrayList<>();
    for (OverAllSurvivalStatus t : overallSurvivalStatusList)
      overallSurvivalStatusName.add(t.getOverAllSurvivalStatus());

    ArrayList<NewMalignancy> newMalignancyList = newMalignancyRepository.findAll();
    ArrayList<String> newMalignancyName = new ArrayList<>();
    for (NewMalignancy t : newMalignancyList)
      newMalignancyName.add(t.getNewMalignancy());

    ArrayList<Dataset> datasetList = datasetRepository.findAll();
    ArrayList<String> datasetsName = new ArrayList<>();
    for (Dataset t : datasetList)
      datasetsName.add(t.getDatasetName());


    HashMap<String, Integer> rowNumbers = null;
    Treatment treatment;
    List<Patient> patients = new ArrayList<>();

    HashMap<Integer, List<String>> Patientmap = new HashMap<>();
    Dataset dataset = new Dataset();
    int previousID = 0;

    for (int index = 0; index < worksheet.getPhysicalNumberOfRows(); index++) {

      if (index > 0) {
        CauseOfDeath causeOfDeath = new CauseOfDeath();
        OverAllSurvivalStatus overallSurvivalStatus = new OverAllSurvivalStatus();
        NewMalignancy newMalignancy = new NewMalignancy();
        XSSFRow row = worksheet.getRow(index);

        if (row.getCell(0) == null) {
          System.out.println("no id found");
          continue;
        }
        int currentID = Integer.parseInt(row.getCell(rowNumbers.get("id")).toString().replace(".0", ""));
        if (previousID != currentID) {
          Patient patient = new Patient();
          for (Patient a : patientList) {
            if (a.getSubjectId() == currentID) {
              patient = a;
            }
          }

          patient.setSubjectId(currentID);
          dataset.setDatasetName(name);
          datasetNode(datasetsName,patient, dataset,datasetList);

          if (!rowNumbers.containsKey("age") || row.getCell(rowNumbers.get("age")) == null)
            patient.setAge(-1);
          else
            patient.setAge(Integer.parseInt(row.getCell(rowNumbers.get("age")).toString().replace(".0", "")));

          if (!rowNumbers.containsKey("gender") || row.getCell(rowNumbers.get("gender")) == null)
            patient.setGender("null");
          else patient.setGender(row.getCell(rowNumbers.get("gender")).toString());

          if (!rowNumbers.containsKey("ethnicity") || row.getCell(rowNumbers.get("ethnicity")) == null)
            patient.setEthnicity("null");
          else patient.setEthnicity(row.getCell(rowNumbers.get("ethnicity")).toString());

          if (!rowNumbers.containsKey("relapse") || row.getCell(rowNumbers.get("relapse")) == null)
            patient.setRelapse("null");
          else patient.setRelapse(row.getCell(rowNumbers.get("relapse")).toString());

          if (!rowNumbers.containsKey("overall survival time") || row.getCell(rowNumbers.get("overall survival time")) == null)
            patient.setSurvivalTime(-1);
          else
            patient.setSurvivalTime(Double.parseDouble(row.getCell(rowNumbers.get("overall survival time")).toString()));

          if (!rowNumbers.containsKey("relapse time") || row.getCell(rowNumbers.get("relapse time")) == null || row.getCell(rowNumbers.get("relapse time")).toString().equals(""))
            patient.setRelapseTime(-1);
          else
            patient.setRelapseTime(Double.parseDouble(row.getCell(rowNumbers.get("relapse time")).toString()));

          if (!rowNumbers.containsKey("failure free survival") || row.getCell(rowNumbers.get("failure free survival")) == null)
            patient.setFailureFreeSurvivalStatus("null");
          else
            patient.setFailureFreeSurvivalStatus(row.getCell(rowNumbers.get("failure free survival")).toString());

          if (!rowNumbers.containsKey("failure free survival time") || row.getCell(rowNumbers.get("failure free survival time")) == null)
            patient.setFailureFreeSurvivalTime(-1);
          else
            patient.setFailureFreeSurvivalTime(Double.parseDouble(row.getCell(rowNumbers.get("failure free survival time")).toString()));

          if (!rowNumbers.containsKey("overall survival status") || row.getCell(rowNumbers.get("overall survival status")) == null)
            overallSurvivalStatus.setOverAllSurvivalStatus("Unknown", name);
          else
            overallSurvivalStatus.setOverAllSurvivalStatus(row.getCell(rowNumbers.get("overall survival status")).toString(), name);
          overallSurvivalStatusNode(overallSurvivalStatusName, patient, overallSurvivalStatus, overallSurvivalStatusList);

          if (!overallSurvivalStatus.getOverAllSurvivalStatus().equals("Alive") && !overallSurvivalStatus.getOverAllSurvivalStatus().equals("Unknown")) {
            if (!rowNumbers.containsKey("cause of death") || row.getCell(rowNumbers.get("cause of death")) == null)
              causeOfDeath.setCauseOfDeath("Unknown");
            else causeOfDeath.setCauseOfDeath(row.getCell(rowNumbers.get("cause of death")).toString());
            causeOfDeathNode(causeOfDeathName, patient, causeOfDeath, causeOfDeathList);
          }

          if (!rowNumbers.containsKey("new malignancy") || row.getCell(rowNumbers.get("new malignancy")) == null)
            newMalignancy.setNewMalignancy("Unknown");
          else newMalignancy.setNewMalignancy(row.getCell(rowNumbers.get("new malignancy")).toString());
          newMalignancyNode(newMalignancyName, patient, newMalignancy, newMalignancyList);

          if (rowNumbers.containsKey("treatment drug") && row.getCell(rowNumbers.get("treatment drug")) != null){
              addToMap(Patientmap, Integer.parseInt(row.getCell(rowNumbers.get("id")).toString().replace(".0", "")),
                      row.getCell(rowNumbers.get("treatment drug")).toString().toUpperCase());
          }


          patients.add(patient);
          previousID = currentID;
        } else {
          if (Patientmap.containsKey(currentID)) {
            if (rowNumbers.containsKey("treatment drug") && row.getCell(rowNumbers.get("treatment drug")) != null){
                String med = row.getCell(rowNumbers.get("treatment drug")).toString();
                if (!Patientmap.get(currentID).contains(med))
                  addToMap(Patientmap, currentID, med);
            }
          }
        }


      } else {
        rowNumbers = setRowNumbers(worksheet, index);
      }
    }
    for (Patient p : patients) {
      if(!Patientmap.isEmpty()) {
        List<String> treatmentsInHashMap = Patientmap.get(p.getSubjectId());
        Collections.sort(treatmentsInHashMap);
        treatment = new Treatment(treatmentsInHashMap.toString());
        treatmentNode(treatmentName, p, treatment, treatmentList);
      }
      patientRepository.save(p);
    }

    return ResponseEntity.ok(new MessageResponse("Successfully added new patients"));
  }

  public ResponseEntity<?> loadSymptoms(MultipartFile file, String name) throws IOException {

    XSSFSheet worksheet;
    if (file.getOriginalFilename().contains(".sas7bdat")) {
      SasToXlsxConverter converter = new SasToXlsxConverter();
      // Get the input stream from the MultipartFile object
      InputStream inputStream = file.getInputStream();

      // Create a SasFileReader object and read the file
      SasFileReader sasFileReader = new SasFileReaderImpl(inputStream);
      worksheet = converter.convertSasToXlsx(sasFileReader);
    } else {
      XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
      worksheet = workbook.getSheetAt(0);
    }

    List<Patient> patientList = patientRepository.findAllByDataset(name);
    List<Integer> patientId = new ArrayList<>();

    ArrayList<Symptoms> symptomsList = symptomsRepository.findAll();
    if (patientList.isEmpty()) return ResponseEntity.badRequest().body(new MessageResponse("There are no patients admitted, you must admit patients first before you can add symptoms."));
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

        if (id != Integer.MAX_VALUE && id != Integer.parseInt(row.getCell(rowNumbers.get("id")).toString().replace(".0", ""))) {
          for (Patient a : patientList)
            if (a.getSubjectId() == id)
              patientRepository.save(a);
        }

        id = Integer.parseInt(row.getCell(rowNumbers.get("id")).toString().replace(".0", ""));

        if (!patientId.contains(id)) {
          System.out.println("patient with that id not found");
          continue;
        }

        for (Patient a : patientList)
          if (a.getSubjectId() == id)
            patient = a;


        if (row.getCell(rowNumbers.get("symptom")) == null || row.getCell(rowNumbers.get("grade")) == null || row.getCell(rowNumbers.get("grade")).toString().equals(""))
          continue;
        else {
          symptoms.setSymptom(row.getCell(rowNumbers.get("symptom")).toString());
          symptoms.setSeverity(Integer.parseInt(row.getCell(rowNumbers.get("grade")).toString().replace(".0", "")));
        }
        symptomsNode(symptoms, patient, symptomsList);
      } else {
        rowNumbers = setRowNumbers(worksheet, index);
      }
    }
    return ResponseEntity.ok(new MessageResponse("Successfully added new symptons"));
  }

  private static void addToMap(HashMap<Integer, List<String>> map, Integer key, String value) {
    // If the key is already in the map, add the value to its set
    if (map.containsKey(key)) {
      List<String> set = map.get(key);
      set.add(value);
    }
    // Otherwise, create a new set with the value and add it to the map
    else {
      List<String> set = new ArrayList<>();
      if (value.contains(",")) {
        String[] parts = value.split(",", 4);
        for (int i = 0; i < parts.length; i++)
          set.add(parts[i]);
        map.put(key, set);
      } else {
        set.add(value);
        map.put(key, set);
      }
    }
  }

  private HashMap<String, Integer> setRowNumbers(XSSFSheet worksheet, int index) {
    HashMap<String, Integer> rowNumbers = new HashMap<>();
    XSSFRow row = worksheet.getRow(index);
    for (Cell r : row) {
      switch (r.toString()) {
        case "PHATOM_ID", "SUBJID" -> rowNumbers.put("id", r.getColumnIndex());//id
        case "GENDER", "SEX" -> rowNumbers.put("gender", r.getColumnIndex());//gender
        case "AGE" -> rowNumbers.put("age", r.getColumnIndex());//age (years)
        case "RACE" -> rowNumbers.put("ethnicity", r.getColumnIndex());//race
        case "PD" -> rowNumbers.put("relapse", r.getColumnIndex());//relapse time
        case "OS_TIME" -> rowNumbers.put("overall survival time", r.getColumnIndex());//overall survival time (months)
        case "PD_TIME" -> rowNumbers.put("relapse time", r.getColumnIndex());//relapse time (months)
        case "PFS_STATUS" -> rowNumbers.put("failure free survival", r.getColumnIndex());//failure free survival
        case "PFS_TIME" ->
          rowNumbers.put("failure free survival time", r.getColumnIndex());//failure free survival time (months)
        case "TRT_ARM_LABEL", "CHPTERM" -> rowNumbers.put("treatment drug", r.getColumnIndex());//treatment drug
        case "STATUS", "DTH" -> rowNumbers.put("overall survival status", r.getColumnIndex());//overall survival status
        case "CAUSEDTH" -> rowNumbers.put("cause of death", r.getColumnIndex());//cause of death
        case "NEW_MALIG" -> rowNumbers.put("new malignancy", r.getColumnIndex());//new malignancy
        case "AE_NAME", "AEPTERM" -> rowNumbers.put("symptom", r.getColumnIndex());//symptom
        case "AE_GRADE", "SEVRCD" -> rowNumbers.put("grade", r.getColumnIndex());//severity grade
      }
    }
    return rowNumbers;
  }

  private void datasetNode(ArrayList<String> datasetName, Patient patient, Dataset dataset, ArrayList<Dataset> datasetList){
    if (!datasetName.contains(dataset.getDatasetName())){
      datasetRepository.save(dataset);
      datasetName.add(dataset.getDatasetName());
      datasetList.add(dataset);
      patient.setDataset(dataset);
    } else
      for (Dataset a : datasetList)
        if (a.getDatasetName().equals(dataset.getDatasetName()))
          patient.setDataset(a);
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

  private void symptomsNode(Symptoms symptoms, Patient patient, ArrayList<Symptoms> symptomsList) {
    boolean flag = true;
    for (Symptoms value : symptomsList) {
      if (value.getSymptom().equals(symptoms.getSymptom()) && value.getSeverity() == symptoms.getSeverity()) {
        flag = false;
        patient.setSymptoms(value);
      }
    }
    if (flag) {
      symptomsRepository.save(symptoms);
      patient.setSymptoms(symptoms);
      symptomsList.add(symptoms);
    }
  }

}
