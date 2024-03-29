package kth.datalake_backend.Service;

import com.epam.parso.SasFileReader;
import com.epam.parso.impl.SasFileReaderImpl;
import kth.datalake_backend.Entity.Nodes.*;
import kth.datalake_backend.Payload.Response.MessageResponse;
import kth.datalake_backend.Repository.Nodes.*;
import kth.datalake_backend.Repository.User.AdminRepository;
import kth.datalake_backend.Service.Util.SasToXlsxConverter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Patient service for business logic and call to the database
 */
@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final TreatmentRepository treatmentRepository;
    private final CauseOfDeathRepository causeOfDeathRepository;
    private final OverallSurvivalStatusRepository overallSurvivalStatusRepository;
    private final NewMalignancyRepository newMalignancyRepository;
    private final SymptomsRepository symptomsRepository;
    private final AdminRepository adminRepository;

    public PatientService(PatientRepository patientRepository, TreatmentRepository treatmentRepository, CauseOfDeathRepository causeOfDeathRepository, OverallSurvivalStatusRepository overallSurvivalStatusRepository, NewMalignancyRepository newMalignancyRepository, SymptomsRepository symptomsRepository, AdminRepository adminRepository) {
        this.patientRepository = patientRepository;
        this.treatmentRepository = treatmentRepository;
        this.causeOfDeathRepository = causeOfDeathRepository;
        this.overallSurvivalStatusRepository = overallSurvivalStatusRepository;
        this.newMalignancyRepository = newMalignancyRepository;
        this.symptomsRepository = symptomsRepository;
        this.adminRepository = adminRepository;
    }

    /**
     * Get a list of the different dataset type exist in the database
     *
     * @return a list of all datasets
     */
    public List<String> getDataSets() {
        return patientRepository.findByDatabase();
    }

    /**
     * Get all patient of a specific dataset type
     *
     * @param name the dataset type to get all patients of
     * @return a list of all patient of a specific dataset
     */
    public List<Patient> getPatientsAsJson(String name) {
        return patientRepository.findAllByDataset(name);
    }

    /**
     * Get all patient in the database
     *
     * @return a list of all patients
     */
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    /**
     * Function to insert new datasets of patient into the database, accepted input variables are,
     * gender, age, pd, os_time, pd_time, pfs_status, pfs_time, trt_arm_label, status, causedth, new_malig
     *
     * @param file accepted types are xlsx and sas7bdat
     * @param name dataset number of the inserted file
     * @return bad request if load fail and successful if accepted
     * @throws IOException any IOE exception
     */
    //https://por-porkaew15.medium.com/how-to-import-excel-by-spring-boot-2624367c8468
    public ResponseEntity<?> loadData(MultipartFile file, String name) throws IOException {

        if (!file.getOriginalFilename().contains(".xlsx") && !file.getOriginalFilename().contains(".sas7bdat"))
            return ResponseEntity.badRequest().body(new MessageResponse("File type not supported"));
        XSSFSheet worksheet = convertToXSSFSheet(file);

        //Get all existing patients from database with connection to the dataset
        List<Patient> patientList = patientRepository.findAllByDataset(name);

        //Adding all existing treatments to avoid creating new ones in the database
        ArrayList<Treatment> treatmentList = treatmentRepository.findAll();
        ArrayList<String> treatmentName = new ArrayList<>();
        for (Treatment t : treatmentList) treatmentName.add(t.getTreatment());

        //Adding all existing cause of death to avoid creating new ones in the database
        ArrayList<CauseOfDeath> causeOfDeathList = causeOfDeathRepository.findAll();
        ArrayList<String> causeOfDeathName = new ArrayList<>();
        for (CauseOfDeath t : causeOfDeathList) causeOfDeathName.add(t.getCauseOfDeath());

        //Adding all existing overall survival status to avoid creating new ones in the database
        ArrayList<OverAllSurvivalStatus> overallSurvivalStatusList = overallSurvivalStatusRepository.findAll();
        ArrayList<String> overallSurvivalStatusName = new ArrayList<>();
        for (OverAllSurvivalStatus t : overallSurvivalStatusList) overallSurvivalStatusName.add(t.getOverAllSurvivalStatus());

        //Adding all existing malignancy to avoid creating new ones in the database
        ArrayList<NewMalignancy> newMalignancyList = newMalignancyRepository.findAll();
        ArrayList<String> newMalignancyName = new ArrayList<>();
        for (NewMalignancy t : newMalignancyList) newMalignancyName.add(t.getNewMalignancy());

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

                if (row.getCell(0) == null) continue;

                int currentID = Integer.parseInt(row.getCell(rowNumbers.get("id")).toString().replace(".0", ""));
                if (previousID != currentID) {
                    Patient patient = new Patient();
                    for (Patient a : patientList) {
                        if (a.getSubjectId() == currentID) patient = a;
                    }

                    patient.setSubjectId(currentID);
                    patient.setDataset(name);

                    if (!rowNumbers.containsKey("age") || row.getCell(rowNumbers.get("age")) == null) patient.setAge(-1);
                    else patient.setAge(Integer.parseInt(row.getCell(rowNumbers.get("age")).toString().replace(".0", "")));

                    if (!rowNumbers.containsKey("gender") || row.getCell(rowNumbers.get("gender")) == null) patient.setGender("Unknown");
                    else patient.setGender(row.getCell(rowNumbers.get("gender")).toString());

                    if (!rowNumbers.containsKey("ethnicity") || row.getCell(rowNumbers.get("ethnicity")) == null) patient.setEthnicity("Unknown");
                    else patient.setEthnicity(row.getCell(rowNumbers.get("ethnicity")).toString());

                    if (!rowNumbers.containsKey("relapse") || row.getCell(rowNumbers.get("relapse")) == null) patient.setRelapse("Unknown");
                    else patient.setRelapse(row.getCell(rowNumbers.get("relapse")).toString());

                    if (!rowNumbers.containsKey("overall survival time") || row.getCell(rowNumbers.get("overall survival time")) == null)
                        patient.setSurvivalTime(-1);
                    else patient.setSurvivalTime(Double.parseDouble(row.getCell(rowNumbers.get("overall survival time")).toString()));

                    if (!rowNumbers.containsKey("relapse time") || row.getCell(rowNumbers.get("relapse time")) == null || row.getCell(rowNumbers.get("relapse time")).toString().equals(""))
                        patient.setRelapseTime(-1);
                    else patient.setRelapseTime(Double.parseDouble(row.getCell(rowNumbers.get("relapse time")).toString()));

                    if (!rowNumbers.containsKey("failure free survival") || row.getCell(rowNumbers.get("failure free survival")) == null)
                        patient.setFailureFreeSurvivalStatus("Unknown");
                    else patient.setFailureFreeSurvivalStatus(row.getCell(rowNumbers.get("failure free survival")).toString());

                    if (!rowNumbers.containsKey("failure free survival time") || row.getCell(rowNumbers.get("failure free survival time")) == null)
                        patient.setFailureFreeSurvivalTime(-1);
                    else patient.setFailureFreeSurvivalTime(Double.parseDouble(row.getCell(rowNumbers.get("failure free survival time")).toString()));

                    if (!rowNumbers.containsKey("overall survival status") || row.getCell(rowNumbers.get("overall survival status")) == null)
                        overallSurvivalStatus.setOverAllSurvivalStatus("6", name);
                    else overallSurvivalStatus.setOverAllSurvivalStatus(row.getCell(rowNumbers.get("overall survival status")).toString(), name);
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


                    if (rowNumbers.containsKey("treatment drug") && row.getCell(rowNumbers.get("treatment drug")) != null)
                        addToMap(Patientmap, Integer.parseInt(row.getCell(rowNumbers.get("id")).toString().replace(".0", "")), row.getCell(rowNumbers.get("treatment drug")).toString());

                    patients.add(patient);
                    previousID = currentID;
                } else {
                    if (Patientmap.containsKey(currentID))
                        if (rowNumbers.containsKey("treatment drug") && row.getCell(rowNumbers.get("treatment drug")) != null) {
                            String med = row.getCell(rowNumbers.get("treatment drug")).toString();
                            if (!Patientmap.get(currentID).contains(med)) addToMap(Patientmap, currentID, med);
                        }
                }
            } else {
                rowNumbers = setRowNumbersPatients(worksheet, index);
                if (rowNumbers.get("id") == null) return ResponseEntity.badRequest().body(new MessageResponse("File has missing id"));
            }
        }

        for (Patient p : patients) {
            if (!Patientmap.isEmpty()) {
                List<String> treatmentsInHashMap = Patientmap.get(p.getSubjectId());
                Collections.sort(treatmentsInHashMap);
                treatment = new Treatment(treatmentsInHashMap.toString().replace("[", "").replace("]", ""));
                treatmentNode(treatmentName, p, treatment, treatmentList);
            }
            patientRepository.save(p);
        }

        adminRepository.addDatabaseToAdminUsers(name);
        return ResponseEntity.ok(new MessageResponse("Successfully added new patients"));
    }

    /**
     * Function to insert new datasets of symptoms into the database on an already existing sheet of patient, accepted input variables are,
     * phatom_id, ae_name, ae_grade
     *
     * @param file accepted types are xlsx and sas7bdat
     * @param name dataset number of the inserted file must be the same as existing patient dataset number
     * @return bad request if load fail and successful if accepted
     * @throws IOException any IOE exception
     */
    public ResponseEntity<?> loadSymptoms(MultipartFile file, String name) throws IOException {

        if (!file.getOriginalFilename().contains(".xlsx") && !file.getOriginalFilename().contains(".sas7bdat"))
            return ResponseEntity.badRequest().body(new MessageResponse("File type not supported"));
        XSSFSheet worksheet = convertToXSSFSheet(file);

        //Get all existing patients from the existing dataset from the database
        List<Patient> patientList = patientRepository.findAllByDataset(name);
        List<Integer> patientId = new ArrayList<>();

        //Get all existing symptoms from the database inorder to avoid creating new nodes
        ArrayList<Symptoms> symptomsList = symptomsRepository.findAll();
        if (patientList.isEmpty()) return ResponseEntity.badRequest().body(new MessageResponse("There are no patients admitted, you must admit patients first before you can add symptoms."));
        for (Patient t : patientList) patientId.add(t.getSubjectId());

        HashMap<String, Integer> rowNumbers = null;
        int id = Integer.MAX_VALUE;

        for (int index = 0; index < worksheet.getPhysicalNumberOfRows(); index++) {
            if (index > 0) {
                Patient patient = new Patient();
                Symptoms symptoms = new Symptoms();
                XSSFRow row = worksheet.getRow(index);

                if (row.getCell(rowNumbers.get("id")) == null) continue;

                if (id != Integer.MAX_VALUE && id != Integer.parseInt(row.getCell(rowNumbers.get("id")).toString().replace(".0", "")))
                    for (Patient a : patientList) if (a.getSubjectId() == id) patientRepository.save(a);

                id = Integer.parseInt(row.getCell(rowNumbers.get("id")).toString().replace(".0", ""));
                if (!patientId.contains(id)) continue;

                for (Patient a : patientList)
                    if (a.getSubjectId() == id) patient = a;

                if (row.getCell(rowNumbers.get("symptom")) == null || row.getCell(rowNumbers.get("symptom")).toString().equals("") || row.getCell(rowNumbers.get("grade")) == null || row.getCell(rowNumbers.get("grade")).toString().equals(""))
                    continue;

                symptoms.setSymptom(row.getCell(rowNumbers.get("symptom")).toString());
                symptoms.setSeverity(Integer.parseInt(row.getCell(rowNumbers.get("grade")).toString().replace(".0", "")));
                symptomsNode(symptoms, patient, symptomsList);
            } else {
                rowNumbers = setRowNumbersSymptoms(worksheet, index);
                if (rowNumbers.get("id") == null)
                    return ResponseEntity.badRequest().body(new MessageResponse("File has missing id"));
                if (rowNumbers.get("symptom") == null)
                    return ResponseEntity.badRequest().body(new MessageResponse("File has missing symptoms"));
                if (rowNumbers.get("grade") == null)
                    return ResponseEntity.badRequest().body(new MessageResponse("File has missing grade"));
            }
        }

        if (id != Integer.MAX_VALUE)
            for (Patient a : patientList)
                if (a.getSubjectId() == id) patientRepository.save(a);

        return ResponseEntity.ok(new MessageResponse("Successfully added new symptoms"));
    }

    private static void addToMap(HashMap<Integer, List<String>> map, Integer key, String value) {
        value = value.toUpperCase(Locale.ROOT);
        List<String> set = new ArrayList<>();
        // If the key is already in the map, add the value to its set
        if (map.containsKey(key)) {
            set = map.get(key);
            set.add(value);
            map.put(key, set);
        }
        // Otherwise, create a new set with the value and add it to the map
        else {
            if (value.contains(",")) {
                String[] parts = value.split(",", 4);
                set.addAll(Arrays.asList(parts));
                map.put(key, set);
            } else {
                set.add(value);
                map.put(key, set);
            }
        }
    }

    private HashMap<String, Integer> setRowNumbersPatients(XSSFSheet worksheet, int index) {
        HashMap<String, Integer> rowNumbers = new HashMap<>();
        XSSFRow row = worksheet.getRow(index);
        //Searches for the headers and connect those index number to the hashmap location
        for (Cell r : row) {
            //String rowValue = r.toString().toUpperCase();
            switch (r.toString()) {
                case "PHATOM_ID", "SUBJID" -> rowNumbers.put("id", r.getColumnIndex());//id
                case "GENDER", "SEX" -> rowNumbers.put("gender", r.getColumnIndex());//gender
                case "AGE" -> rowNumbers.put("age", r.getColumnIndex());//age (years)
                case "RACE" -> rowNumbers.put("ethnicity", r.getColumnIndex());//race
                case "PD" -> rowNumbers.put("relapse", r.getColumnIndex());//relapse time
                case "OS_TIME" -> rowNumbers.put("overall survival time", r.getColumnIndex());//overall survival time (months)
                case "PD_TIME" -> rowNumbers.put("relapse time", r.getColumnIndex());//relapse time (months)
                case "PFS_STATUS" -> rowNumbers.put("failure free survival", r.getColumnIndex());//failure free survival
                case "PFS_TIME" -> rowNumbers.put("failure free survival time", r.getColumnIndex());//failure free survival time (months)
                case "TRT_ARM_LABEL", "CHPTERM" -> rowNumbers.put("treatment drug", r.getColumnIndex());//treatment drug
                case "STATUS", "DTH" -> rowNumbers.put("overall survival status", r.getColumnIndex());//overall survival status
                case "CAUSEDTH" -> rowNumbers.put("cause of death", r.getColumnIndex());//cause of death
                case "NEW_MALIG" -> rowNumbers.put("new malignancy", r.getColumnIndex());//new malignancy
            }
        }
        return rowNumbers;
    }

    private HashMap<String, Integer> setRowNumbersSymptoms(XSSFSheet worksheet, int index) {
        HashMap<String, Integer> rowNumbers = new HashMap<>();
        XSSFRow row = worksheet.getRow(index);
        //Searches for the headers and connect those index number to the hashmap location
        for (Cell r : row) {
            //String rowValue = r.toString().toUpperCase();
            switch (r.toString()) {
                case "PHATOM_ID", "SUBJID" -> rowNumbers.put("id", r.getColumnIndex());//id
                case "AE_NAME", "AEPTERM" -> rowNumbers.put("symptom", r.getColumnIndex());//symptom
                case "AE_GRADE", "SEVRCD" -> rowNumbers.put("grade", r.getColumnIndex());//severity grade
            }
        }
        return rowNumbers;
    }

    private XSSFSheet convertToXSSFSheet(MultipartFile file) throws IOException {
        XSSFSheet worksheet = null;
        if (file.getOriginalFilename().contains(".sas7bdat")) {
            SasToXlsxConverter converter = new SasToXlsxConverter();
            // Get the input stream from the MultipartFile object
            InputStream inputStream = file.getInputStream();

            // Create a SasFileReader object and read the file
            SasFileReader sasFileReader = new SasFileReaderImpl(inputStream);
            worksheet = converter.convertSasToXlsx(sasFileReader);
        } else if (file.getOriginalFilename().contains(".xlsx")) {
            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
            worksheet = workbook.getSheetAt(0);
        }
        return worksheet;
    }

    /**
     * Creates new node in database for treatment if not on already exist and creates a relationship from the node to patient node
     * @param treatmentName list of all treatment name in the database
     * @param patient the patient whom will get a connection to the treatment node
     * @param treatment the name of the treatment that the patient received
     * @param treatmentList list of all the treatment nodes in the database
     */
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

    /**
     * Creates new node in database for cause of death if not on already exist and creates a relationship from the node to patient node
     * @param causeOfDeathName list of all cause of death name in the database
     * @param patient the patient whom will get a connection to the cause of death node
     * @param causeOfDeath the name of the cause of death that happend to the patient
     * @param causeOfDeathsList list of all the cause of death nodes in the database
     */
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

    /**
     * Creates new node in database for over all survival status if not on already exist and creates a relationship from the node to patient node
     * @param overallSurvivalStatusName list of all over all survival status name in the database
     * @param patient the patient whom will get a connection to the all over all survival status node
     * @param overallSurvivalStatus the name of the all over all survival status that of the patient
     * @param overallSurvivalStatusList list of alla the all over all survival status nodes in the database
     */
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

    /**
     * Creates new node in database for new malignancy if not on already exist and creates a relationship from the node to patient node
     * @param newMalignancyName list of all new malignancy name in the database
     * @param patient the patient whom will get a connection to the new malignancy node
     * @param newMalignancy the name of the new malignancy that occurred to the patient
     * @param newMalignancyList list of all the new malignancy nodes in the database
     */
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

    /**
     * Creates new node in database for symptoms if not on already exist and creates a relationship from the node to patient node
     * @param symptoms the symptoms that the patient has a connection to
     * @param patient the patient whom will get a connection to the symptoms node
     * @param symptomsList list of all the new symptoms nodes in the database
     */
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
