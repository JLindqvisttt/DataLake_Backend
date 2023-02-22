package kth.datalake_backend.Controller;

import kth.datalake_backend.Payload.Response.MessageResponse;
import kth.datalake_backend.Service.PatientService;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/patient")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {this.patientService = patientService;}

    @PostMapping("/input")
    public ResponseEntity inputData(@RequestParam("file") MultipartFile files) throws IOException {

        XSSFWorkbook workbook = new XSSFWorkbook(files.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);

        for (int index = 0; index < worksheet.getPhysicalNumberOfRows(); index++) {
            if (index > 0) {


                XSSFRow row = worksheet.getRow(index);
                //Integer id = (int) row.getCell(0).getNumericCellValue();
                System.out.println("new patient");
                for(int i = 0; i <23; i++){
                    System.out.println(row.getCell(i));
                }

            }
        }

        return ResponseEntity.ok(new MessageResponse("print"));
    }
}
