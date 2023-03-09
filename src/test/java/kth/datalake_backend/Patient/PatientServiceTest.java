package kth.datalake_backend.Patient;

import kth.datalake_backend.Entity.Nodes.*;
import kth.datalake_backend.Repository.Nodes.*;

import kth.datalake_backend.Service.PatientService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.neo4j.core.Neo4jTemplate;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

import static kth.datalake_backend.Entity.Nodes.Gender.*;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class PatientServiceTest {

    @Autowired
    CauseOfDeathRepository causeOfDeathRepository;

    @Autowired
    NewMalignancyRepository newMalignancyRepository;

    @Autowired
    OverallSurvivalStatusRepository overallSurvivalStatusRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    SymptomsRepository symptomsRepository;

    @Autowired
    TreatmentRepository treatmentRepository;

    @Autowired
    PatientService patientService;

    @Autowired
    Neo4jTemplate neo4jTemplate;

    @Autowired
    PasswordEncoder encoder;

    Patient patient = null;

    Patient patient1 = null;

    @BeforeEach
    public void init() {
        causeOfDeathRepository.deleteAll();
        newMalignancyRepository.deleteAll();
        overallSurvivalStatusRepository.deleteAll();
        patientRepository.deleteAll();
        symptomsRepository.deleteAll();
        treatmentRepository.deleteAll();

        patient = new Patient(1l,51,FEMALE,"White", 12727, "No",10000,10000,"Survived",9999,"261");

        patient.setTreatment(new Treatment("CISPLATIN, ETOPOSIDE"));

        CauseOfDeath causeOfDeath = new CauseOfDeath();
        causeOfDeath.setCauseOfDeath("1");
        patient.setCauseOfDeath(causeOfDeath);

        NewMalignancy newMalignancy = new NewMalignancy();
        newMalignancy.setNewMalignancy("1");
        patient.setNewMalignancy(newMalignancy);

        OverAllSurvivalStatus overAllSurvivalStatus = new OverAllSurvivalStatus();
        overAllSurvivalStatus.setOverAllSurvivalStatus("1", "261");
        patient.setOverAllSurvivalStatus(overAllSurvivalStatus);

        Symptoms symptoms1 = new Symptoms();
        symptoms1.setSymptom("symptom1");
        symptoms1.setSeverity(1);

        Symptoms symptoms2 = new Symptoms();
        symptoms2.setSymptom("symptom2");
        symptoms2.setSeverity(2);

        patient.setSymptoms(symptoms1);
        patient.setSymptoms(symptoms2);

        patientRepository.save(patient);


        patient1 = new Patient(2l,52,MALE,"Asian", 32717, "Yes",10000,10000,"Death",9999,"266");

        patient1.setTreatment(new Treatment("CISPLATIN, ETOPOSIDE"));

        causeOfDeath = new CauseOfDeath();
        causeOfDeath.setCauseOfDeath("2");
        patient1.setCauseOfDeath(causeOfDeath);

        newMalignancy = new NewMalignancy();
        newMalignancy.setNewMalignancy("2");
        patient1.setNewMalignancy(newMalignancy);

        overAllSurvivalStatus = new OverAllSurvivalStatus();
        overAllSurvivalStatus.setOverAllSurvivalStatus("1", "266");
        patient1.setOverAllSurvivalStatus(overAllSurvivalStatus);

        symptoms1 = new Symptoms();
        symptoms1.setSymptom("symptom3");
        symptoms1.setSeverity(1);

        patient1.setSymptoms(symptoms1);
        patient1.setSymptoms(symptoms2);

        patientRepository.save(patient1);
    }

    @Test
    @DisplayName("getDataSets should retrieve All datasets")
    public void shouldGetDataSets() {
        assertEquals(2, patientService.getDataSets().size());
        Patient patient3 = new Patient(10l,51,FEMALE,"Black", 17272, "Yes",1000,1000,"Survived",999,"210");

        patientRepository.save(patient3);
        assertEquals(3, patientService.getDataSets().size());
    }

    @Test
    @DisplayName("getPatientsAsJson retrieving correct patient as json")
    public void shouldGetPatientsAsJson(){
        assertEquals(1, patientService.getPatientsAsJson("261").size());

        Patient patient4 = new Patient(10l,51,FEMALE,"Black", 17272, "Yes",1000,1000,"Survived",999,"266");
        patientRepository.save(patient4);
        assertEquals(2, patientService.getPatientsAsJson("266").size());

        assertEquals(0, patientService.getPatientsAsJson("111").size());
    }

    @Test
    @DisplayName("getAllPatients retrieving all patient")
    public void shouldGetAllPatients(){
        assertEquals(2, patientService.getAllPatients().size());
    }

    @Test
    @DisplayName("loadData missing cell values inputs results in default values")
    public void missingHeaderValuesShouldImport() throws IOException {
        XSSFWorkbook file = createWorkbookPatient();
        Sheet sheet = file.getSheet("Persons");
        Row headerRow = sheet.getRow(0);
        Row row = sheet.getRow(1);
        Cell headerCell;
        Cell cell = row.createCell(0);

        //  headers empty
        for(int i = 0; i < 12; i++){
            headerCell = headerRow.createCell(1+i);
            headerCell.setCellValue("");
            cell.setCellValue(10000+i);
            patientService.loadData(creatMultiPartFile(file), String.valueOf(267+i));
            List<Patient> patients = patientRepository.findAll();
            assertEquals(3+i, patients.size());
        }
        List<Patient> patients = patientRepository.findAllByDataset("278");
        Patient patient = patients.get(0);
        assertEquals(-1, patient.getAge());
        assertEquals(UNKNOWN, patient.getGender());
        assertEquals("Unknown", patient.getEthnicity());
        assertEquals("Unknown", patient.getRelapse());
        assertEquals(-1, patient.getSurvivalTime());
        assertEquals(-1, patient.getRelapseTime());
        assertEquals("Unknown", patient.getFailureFreeSurvivalStatus());
        assertEquals(-1, patient.getFailureFreeSurvivalTime());
        assertEquals("Unknown", patient.getOverAllSurvivalStatus().getOverAllSurvivalStatus());
        assertNull(patient.getCauseOfDeath());
        assertEquals("Unknown", patient.getNewMalignancy().getNewMalignancy());
        assertNull(patient.getTreatment());
    }

    @Test
    @DisplayName("loadData valid inputs")
    public void validInputLoadData() throws IOException {
        XSSFWorkbook file = createWorkbookPatient();

        patientService.loadData(creatMultiPartFile(file), "278");
        List<Patient> patients = patientRepository.findAllByDataset("278");
        Patient patient = patients.get(0);
        assertEquals(47, patient.getAge());
        assertEquals(MALE, patient.getGender());
        assertEquals("Oriental", patient.getEthnicity());
        assertEquals("Yes", patient.getRelapse());
        assertEquals(1000, patient.getSurvivalTime());
        assertEquals(970, patient.getRelapseTime());
        assertEquals("Deceased", patient.getFailureFreeSurvivalStatus());
        assertEquals(1370, patient.getFailureFreeSurvivalTime());
        assertEquals("Death", patient.getOverAllSurvivalStatus().getOverAllSurvivalStatus());
        assertEquals("Protocol treatment related",patient.getCauseOfDeath().getCauseOfDeath());
        assertEquals("No", patient.getNewMalignancy().getNewMalignancy());
        assertEquals("GIGASICK", patient.getTreatment().getTreatment());
    }

    @Test
    @DisplayName("loadData invalid File type and missing ID column")
    public void invalidInputLoadData() throws IOException{
        XSSFWorkbook file = createWorkbookPatient();
        Sheet sheet = file.getSheet("Persons");
        Row headerRow = sheet.getRow(0);

        Cell headerCell = headerRow.createCell(0);
        headerCell.setCellValue("");
        assertEquals("File has missing id", patientService.loadData(creatMultiPartFile(file), String.valueOf(267)).getBody().toString());

        assertEquals("File type not supported", patientService.loadData(creatMultiPartFileWord(file), String.valueOf(267)).getBody().toString());
    }

    @Test
    @DisplayName("loadData multiple treatments")
    public void validMultipleTreatmentsLoadData() throws IOException {
        XSSFWorkbook file = createWorkbookPatient();
        Sheet sheet = file.getSheet("Persons");
        Row row2 = sheet.createRow(2);

        Cell cell = row2.createCell(0);
        cell.setCellValue(9999);
        cell = row2.createCell(1);
        cell.setCellValue(1);
        cell = row2.createCell(2);
        cell.setCellValue(47);
        cell = row2.createCell(3);
        cell.setCellValue(4);
        cell = row2.createCell(4);
        cell.setCellValue(2);
        cell = row2.createCell(5);
        cell.setCellValue(1000);
        cell = row2.createCell(6);
        cell.setCellValue(970);
        cell = row2.createCell(7);
        cell.setCellValue(2);
        cell = row2.createCell(8);
        cell.setCellValue(1370);
        cell = row2.createCell(9);
        cell.setCellValue("stillsick");
        cell = row2.createCell(10);
        cell.setCellValue(2);
        cell = row2.createCell(11);
        cell.setCellValue(1);
        cell = row2.createCell(12);
        cell.setCellValue(1);

        patientService.loadData(creatMultiPartFile(file),"300");
        List<Patient> patients = patientRepository.findAllByDataset("300");
        Patient patient3 = patients.get(0);
        assertEquals("GIGASICK, STILLSICK", patient3.getTreatment().getTreatment());

        file = createWorkbookPatient();
        sheet = file.getSheet("Persons");
        Row row1 = sheet.getRow(1);
        row2 = sheet.createRow(2);

        cell = row1.getCell(0);
        cell.setCellValue(10000);
        cell = row2.createCell(0);
        cell.setCellValue(10000);
        cell = row2.createCell(1);
        cell.setCellValue(1);
        cell = row2.createCell(2);
        cell.setCellValue(47);
        cell = row2.createCell(3);
        cell.setCellValue(4);
        cell = row2.createCell(4);
        cell.setCellValue(2);
        cell = row2.createCell(5);
        cell.setCellValue(1000);
        cell = row2.createCell(6);
        cell.setCellValue(970);
        cell = row2.createCell(7);
        cell.setCellValue(2);
        cell = row2.createCell(8);
        cell.setCellValue(1370);
        cell = row2.createCell(9);
        cell.setCellValue("stillsick");
        cell = row2.createCell(10);
        cell.setCellValue(2);
        cell = row2.createCell(11);
        cell.setCellValue(1);
        cell = row2.createCell(12);
        cell.setCellValue(1);

        patientService.loadData(creatMultiPartFile(file),"301");
        patients = patientRepository.findAllByDataset("301");
        Patient patient4 = patients.get(0);
        assertEquals(patient3.getTreatment().getTreatment(), patient4.getTreatment().getTreatment());
    }

    @Test
    @DisplayName("loadData  multiple patients on same file")
    public void loadDataMultiplePatients() throws IOException {
        XSSFWorkbook file = createWorkbookPatient();
        Sheet sheet = file.getSheet("Persons");
        Row row2 = sheet.createRow(2);

        Cell cell = row2.createCell(0);
        cell.setCellValue(10000);
        cell = row2.createCell(1);
        cell.setCellValue(1);
        cell = row2.createCell(2);
        cell.setCellValue(57);
        cell = row2.createCell(3);
        cell.setCellValue(4);
        cell = row2.createCell(4);
        cell.setCellValue(2);
        cell = row2.createCell(5);
        cell.setCellValue(10);
        cell = row2.createCell(6);
        cell.setCellValue(90);
        cell = row2.createCell(7);
        cell.setCellValue(2);
        cell = row2.createCell(8);
        cell.setCellValue(170);
        cell = row2.createCell(9);
        cell.setCellValue("dead");
        cell = row2.createCell(10);
        cell.setCellValue(2);
        cell = row2.createCell(11);
        cell.setCellValue(1);
        cell = row2.createCell(12);
        cell.setCellValue(1);

        patientService.loadData(creatMultiPartFile(file),("300"));

        assertEquals(2, patientRepository.findAllByDataset("300").size());
    }

    @Test
    @DisplayName("loadSymptoms values")
    public void validValuesLoadSymptom()throws IOException{
        XSSFWorkbook file = createWorkbookSymptom();

        patientService.loadSymptoms(creatMultiPartFile(file), "261").getBody();
        List<Patient> patients = patientRepository.findAllByDataset("261");
        Patient patient = patients.get(0);
        assertEquals(5, patient.getSymptoms().size());
    }

    @Test
    @DisplayName("loadSymptoms missing headers")
    public void missingHeadersLoadSymptom() throws IOException{
        XSSFWorkbook file = createWorkbookSymptom();
        Sheet sheet = file.getSheet("Symptoms");
        Row headerRow = sheet.getRow(0);
        Row row = sheet.getRow(1);
        Cell headerCell;
        Cell cell = row.createCell(0);

        headerCell = headerRow.createCell(0);
        headerCell.setCellValue("");
        assertEquals("File has missing id", patientService.loadSymptoms(creatMultiPartFile(file), "261").getBody().toString());
        headerCell.setCellValue("PHATOM_ID");

        headerCell = headerRow.createCell(1);
        headerCell.setCellValue("");
        assertEquals("File has missing symptoms", patientService.loadSymptoms(creatMultiPartFile(file), "261").getBody().toString());
        headerCell.setCellValue("AE_NAME");

        headerCell = headerRow.createCell(2);
        headerCell.setCellValue("");
        assertEquals("File has missing grade", patientService.loadSymptoms(creatMultiPartFile(file), "261").getBody().toString());
    }

    @Test
    @DisplayName("LoadSymptoms missing values")
    public void missingValuesLoadSymptoms() throws IOException {
        XSSFWorkbook file = createWorkbookSymptom();
        Sheet sheet = file.getSheet("Symptoms");
        Row row1 = sheet.getRow(0);
        Row row2 = sheet.getRow(1);
        Row row3 = sheet.getRow(2);

        Cell cell = row2.getCell(1);
        cell.setCellValue("");

        cell = row3.getCell(2);
        cell.setCellValue("");

        cell = row1.getCell(0);
        cell.setCellValue("");

        patientService.loadSymptoms(creatMultiPartFile(file),"261");
        List<Patient> patients = patientRepository.findAllByDataset("261");
        assertEquals(2, patients.get(0).getSymptoms().size());
    }

    @Test
    @DisplayName("LoadSymptoms unable to load in multiplee of same symptom")
    public void sameFileLoadSymptoms() throws IOException {
    XSSFWorkbook file = createWorkbookSymptom();
    patientService.loadSymptoms(creatMultiPartFile(file),"261");
    List<Patient> patients = patientRepository.findAllByDataset("261");
    assertEquals(5,patients.get(0).getSymptoms().size());

    patientService.loadSymptoms(creatMultiPartFile(file),"261");
    patients = patientRepository.findAllByDataset("261");
    assertEquals(5,patients.get(0).getSymptoms().size());
    }

    @Test
    @DisplayName("LoadSymptoms invalid File type")
    public void invalidInputLoadSymptoms() throws IOException{
        XSSFWorkbook file = createWorkbookSymptom();
        Sheet sheet = file.getSheet("Symptoms");
        assertEquals("File type not supported", patientService.loadData(creatMultiPartFileWord(file), String.valueOf(267)).getBody().toString());
    }

    private XSSFWorkbook createWorkbookPatient(){
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Persons");
        Row header = sheet.createRow(0);
        Row row = sheet.createRow(1);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("PHATOM_ID");
        headerCell.setCellStyle(headerStyle);
        Cell cell = row.createCell(0);
        cell.setCellValue(9999);
        cell.setCellStyle(style);

        headerCell = header.createCell(1);
        headerCell.setCellValue("GENDER");
        headerCell.setCellStyle(headerStyle);
        cell = row.createCell(1);
        cell.setCellValue(1);
        cell.setCellStyle(style);

        headerCell = header.createCell(2);
        headerCell.setCellValue("AGE");
        headerCell.setCellStyle(headerStyle);
        cell = row.createCell(2);
        cell.setCellValue(47);
        cell.setCellStyle(style);

        headerCell = header.createCell(3);
        headerCell.setCellValue("RACE");
        headerCell.setCellStyle(headerStyle);
        cell = row.createCell(3);
        cell.setCellValue(4);
        cell.setCellStyle(style);

        headerCell = header.createCell(4);
        headerCell.setCellValue("PD");
        headerCell.setCellStyle(headerStyle);
        cell = row.createCell(4);
        cell.setCellValue(2);
        cell.setCellStyle(style);

        headerCell = header.createCell(5);
        headerCell.setCellValue("OS_TIME");
        headerCell.setCellStyle(headerStyle);
        cell = row.createCell(5);
        cell.setCellValue(1000);
        cell.setCellStyle(style);

        headerCell = header.createCell(6);
        headerCell.setCellValue("PD_TIME");
        headerCell.setCellStyle(headerStyle);
        cell = row.createCell(6);
        cell.setCellValue(970);
        cell.setCellStyle(style);

        headerCell = header.createCell(7);
        headerCell.setCellValue("PFS_STATUS");
        headerCell.setCellStyle(headerStyle);
        cell = row.createCell(7);
        cell.setCellValue(2);
        cell.setCellStyle(style);

        headerCell = header.createCell(8);
        headerCell.setCellValue("PFS_TIME");
        headerCell.setCellStyle(headerStyle);
        cell = row.createCell(8);
        cell.setCellValue(1370);
        cell.setCellStyle(style);

        headerCell = header.createCell(9);
        headerCell.setCellValue("TRT_ARM_LABEL");
        headerCell.setCellStyle(headerStyle);
        cell = row.createCell(9);
        cell.setCellValue("gigasick");
        cell.setCellStyle(style);

        headerCell = header.createCell(10);
        headerCell.setCellValue("STATUS");
        headerCell.setCellStyle(headerStyle);
        cell = row.createCell(10);
        cell.setCellValue(2);
        cell.setCellStyle(style);

        headerCell = header.createCell(11);
        headerCell.setCellValue("CAUSEDTH");
        headerCell.setCellStyle(headerStyle);
        cell = row.createCell(11);
        cell.setCellValue(1);
        cell.setCellStyle(style);

        headerCell = header.createCell(12);
        headerCell.setCellValue("NEW_MALIG");
        headerCell.setCellStyle(headerStyle);
        cell = row.createCell(12);
        cell.setCellValue(1);
        cell.setCellStyle(style);

        return workbook;
    }

    private XSSFWorkbook createWorkbookSymptom() throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Symptoms");
        Row header = sheet.createRow(0);
        Row row1 = sheet.createRow(1);
        Row row2 = sheet.createRow(2);
        Row row3 = sheet.createRow(3);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("PHATOM_ID");
        headerCell.setCellStyle(headerStyle);
        Cell cell = row1.createCell(0);
        cell.setCellValue(12727);
        cell = row2.createCell(0);
        cell.setCellValue(12727);
        cell = row3.createCell(0);
        cell.setCellValue(12727);
        cell.setCellStyle(style);

        headerCell = header.createCell(1);
        headerCell.setCellValue("AE_NAME");
        headerCell.setCellStyle(headerStyle);
        cell = row1.createCell(1);
        cell.setCellValue("pain");
        cell.setCellStyle(style);
        cell = row2.createCell(1);
        cell.setCellValue("nausea");
        cell.setCellStyle(style);
        cell = row3.createCell(1);
        cell.setCellValue("headache");
        cell.setCellStyle(style);

        headerCell = header.createCell(2);
        headerCell.setCellValue("AE_GRADE");
        headerCell.setCellStyle(headerStyle);
        cell = row1.createCell(2);
        cell.setCellValue(1);
        cell.setCellStyle(style);
        cell = row2.createCell(2);
        cell.setCellValue(5);
        cell.setCellStyle(style);
        cell = row3.createCell(2);
        cell.setCellValue(3);
        cell.setCellStyle(style);

        return workbook;
    }

    private MultipartFile creatMultiPartFile(XSSFWorkbook workbook)throws IOException {
        String filename = "666.xlsx";
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        byte[] bytes = outputStream.toByteArray();
        InputStream inputStream = new ByteArrayInputStream(bytes);
        return new MockMultipartFile(filename, filename,
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", inputStream);
    }

    private MultipartFile creatMultiPartFileWord(XSSFWorkbook workbook)throws IOException {
        String filename = "666.doc";
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        byte[] bytes = outputStream.toByteArray();
        InputStream inputStream = new ByteArrayInputStream(bytes);
        MultipartFile multipartFile = new MockMultipartFile(filename, filename,
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", inputStream);
        return multipartFile;
    }
}
