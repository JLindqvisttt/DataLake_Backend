package kth.datalake_backend.Service;

import com.epam.parso.Column;
import com.epam.parso.SasFileReader;
import com.epam.parso.impl.SasFileReaderImpl;
import jdk.dynalink.linker.LinkerServices;
import kth.datalake_backend.Entity.Nodes.*;
import kth.datalake_backend.Payload.Response.MessageResponse;
import kth.datalake_backend.Repository.Nodes.*;

import kth.datalake_backend.Service.Util.SasToXlsxConverter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

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

        //TODO bör det inte finnas en for each för varje node?
        for (Treatment t : treatmentList)
            treatmentName.add(t.getTreatment());


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

                patient.setSubjectId(Integer.parseInt(row.getCell(0).toString().replace(".0", "")));

                patient.setDataset(name);

                if (row.getCell(2) == null) patient.setAge(-1);
                else patient.setAge(Integer.parseInt(row.getCell(2).toString().replace(".0", "")));

                if (row.getCell(1) == null) patient.setGender("null");
                else patient.setGender(row.getCell(1).toString());

                if (row.getCell(3) == null) patient.setEthnicity("null");
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
                if (row.getCell(10) == null) treatment = new Treatment("Unknown");
                else treatment = new Treatment(row.getCell(10).toString());
                treatmentNode(treatmentName, patient, treatment, treatmentList);

                if (row.getCell(15) == null) overallSurvivalStatus.setOverAllSurvivalStatus("Unknown");
                else overallSurvivalStatus.setOverAllSurvivalStatus(row.getCell(15).toString());
                overallSurvivalStatusNode(overallSurvivalStatusName, patient, overallSurvivalStatus, overallSurvivalStatusList);

                if (!overallSurvivalStatus.getOverAllSurvivalStatus().equals("Alive")) {
                    if (row.getCell(16) == null) causeOfDeath.setCauseOfDeath("Unknown");
                    else causeOfDeath.setCauseOfDeath(row.getCell(16).toString());
                    causeOfDeathNode(causeOfDeathName, patient, causeOfDeath, causeOfDeathList);
                }

                if (row.getCell(18) == null) newMalignancy.setNewMalignancy("Unknown");
                else newMalignancy.setNewMalignancy(row.getCell(18).toString());
                newMalignancyNode(newMalignancyName, patient, newMalignancy, newMalignancyList);

                patientRepository.save(patient);
            }
        }

        return ResponseEntity.ok(new MessageResponse("testing"));
    }

    public ResponseEntity<?> loadFileSAS(MultipartFile file, String name) throws IOException {
        SasToXlsxConverter converter = new SasToXlsxConverter();
        // Get the input stream from the MultipartFile object
        InputStream inputStream = file.getInputStream();

        // Create a SasFileReader object and read the file
        SasFileReader sasFileReader = new SasFileReaderImpl(inputStream);
        XSSFSheet worksheet = converter.convertSasToXlsx(sasFileReader);


        // Get treatment list
        ArrayList<Treatment> treatmentList = treatmentRepository.findAll();
        ArrayList<String> treatmentName = new ArrayList<>();

        // Add treatments from list to treatmentName
        for (Treatment t : treatmentList)
            treatmentName.add(t.getTreatment());

        HashMap<String, Integer> rowNumbers = null;
        Treatment treatment;
        List<Patient> patients = new ArrayList<>();
        HashMap<Integer, List<String>> Patientmap = new HashMap<>();
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
                
                if (previousID != Integer.parseInt(row.getCell(rowNumbers.get("id")).toString().replace(".0", ""))) {
                    Patient patient = new Patient();
                    //ID
                    patient.setSubjectId(Integer.parseInt(row.getCell(rowNumbers.get("id")).toString().replace(".0", "")));
                    //AGE
                    if (!rowNumbers.containsKey("age") || row.getCell(rowNumbers.get("age")) == null ) patient.setAge(-1);
                    else patient.setAge(Integer.parseInt(row.getCell(rowNumbers.get("age")).toString().replace(".0", "")));
                    //GENDER
                    if (!rowNumbers.containsKey("gender") || row.getCell(rowNumbers.get("gender")) == null) patient.setGender("null");
                    else patient.setGender(row.getCell(rowNumbers.get("gender")).toString());
                    //ETHNICITY
                    if (!rowNumbers.containsKey("ethnicity") || row.getCell(rowNumbers.get("ethnicity")) == null) patient.setEthnicity("null");
                    else patient.setEthnicity(row.getCell(rowNumbers.get("ethnicity")).toString());

                    addToMap(Patientmap, Integer.parseInt(row.getCell(rowNumbers.get("id")).toString().replace(".0", "")),
                            row.getCell(rowNumbers.get("treatment drug")).toString());
                    patients.add(patient);
                    previousID = Integer.parseInt(row.getCell(rowNumbers.get("id")).toString().replace(".0", ""));
                } else {
                    if (Patientmap.containsKey(Integer.parseInt(row.getCell(rowNumbers.get("id")).toString().replace(".0", "")))) {
                        int id = Integer.parseInt(row.getCell(rowNumbers.get("id")).toString().replace(".0", ""));
                        String med =  row.getCell(rowNumbers.get("treatment drug")).toString();
                        if (!Patientmap.get(id).contains(med))
                            addToMap(Patientmap, id, med);
                    }
                }



            } else {
                rowNumbers = setRowNumbers(worksheet, index);
            }
        }
        for (Patient p : patients) {
            List<String> treatmentset = Patientmap.get(p.getSubjectId());
            Collections.sort(treatmentset);
            treatment = new Treatment(treatmentset.toString());
            treatmentNode(treatmentName, p, treatment, treatmentList);
            patientRepository.save(p);
        }
        return ResponseEntity.ok(new MessageResponse("testing"));
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
            set.add(value);
            map.put(key, set);
        }
    }

    private HashMap<String, Integer> setRowNumbers(XSSFSheet worksheet, int index) {
        HashMap<String, Integer> rowNumbers = new HashMap<>();
        XSSFRow row = worksheet.getRow(index);
        for (Cell r : row) {
            switch (r.toString()) {
                case "PHATOM_ID", "SUBJID":
                    rowNumbers.put("id", r.getColumnIndex());
                    break; //id
                case "GENDER", "SEX":
                    rowNumbers.put("gender", r.getColumnIndex());
                    break; //gender
                case "AGE":
                    rowNumbers.put("age", r.getColumnIndex());
                    break; //age (years)
                case "RACE":
                    rowNumbers.put("ethnicity", r.getColumnIndex());
                    break; //race
                case "PD":
                    rowNumbers.put("relapse", r.getColumnIndex());
                    break; //relapse time
                case "OS_TIME":
                    rowNumbers.put("overall survival time", r.getColumnIndex());
                    break; //overall survival time (months)
                case "PD_TIME":
                    rowNumbers.put("relapse time", r.getColumnIndex());
                    break; //relapse time (months)
                case "PFS_STATUS":
                    rowNumbers.put("failure free survival", r.getColumnIndex());
                    break; //failure free survival
                case "PFS_TIME":
                    rowNumbers.put("failure free survival time", r.getColumnIndex());
                    break; //failure free survival time (months)
                case "TRT_ARM_LABEL", "CHPTERM":
                    rowNumbers.put("treatment drug", r.getColumnIndex());
                    break; //treatment drug
                case "STATUS":
                    rowNumbers.put("overall survival status", r.getColumnIndex());
                    break; //overall survival status
                case "CAUSEDTH":
                    rowNumbers.put("cause of death", r.getColumnIndex());
                    break; //cause of death
                case "NEW_MALIG":
                    rowNumbers.put("new malignancy", r.getColumnIndex());
                    break; //new malignancy
            }
        }
        return rowNumbers;
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
        ArrayList<String> symptomsName = new ArrayList<>();

        for (Patient t : patientList)
            patientId.add(t.getSubjectId());

    /*
    for (Symptoms t:symptomsList)
      symptomsName.add(t.getSymptom());
    */


        List<Patient> patiens;

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
                if (!patientId.contains(Integer.parseInt(row.getCell(0).toString().replace(".0", "")))) {
                    System.out.println("patient with that id not found");
                }
                int id = Integer.parseInt(row.getCell(0).toString().replace(".0", ""));
                for (Patient a : patientList)
                    if (a.getSubjectId() == id)
                        patient = a;

                if (row.getCell(1) == null || row.getCell(2) == null) continue;
                else {
                    symptoms.setSymptom(row.getCell(1).toString());
                    symptoms.setSeverity(Integer.parseInt(row.getCell(2).toString().replace(".0", "")));
                }

                symptomsNode(symptoms, patient, symptomsList);


            }
        }
        return ResponseEntity.ok(new MessageResponse("testing"));
    }

    private void symptomsNode(Symptoms symptoms, Patient patient, ArrayList<Symptoms> symptomsList) {
        boolean flag = true;
        for (int i = 0; i < symptomsList.size(); i++) {
            if (symptomsList.get(i).getSymptom().equals(symptoms.getSymptom()) && symptomsList.get(i).getSeverity() == symptoms.getSeverity()) {
                System.out.println("exist");
                flag = false;
                patient.setSymptoms(symptomsList.get(i));
            }
        }
        if (flag) {
            symptomsRepository.save(symptoms);
            patient.setSymptoms(symptoms);
            symptomsList.add(symptoms);
        }
    }

}
