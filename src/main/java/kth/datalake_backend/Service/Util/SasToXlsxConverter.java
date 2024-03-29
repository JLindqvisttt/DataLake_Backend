package kth.datalake_backend.Service.Util;

import com.epam.parso.Column;
import com.epam.parso.SasFileReader;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;

/**
 * The SasToXlsxConverter class is responsible for converting a SAS file to an XLSX file.
 */
public class SasToXlsxConverter {

    /**
     * Converts a SAS file to an XLSX sheet.
     *
     * @param sasFileReader the SAS file reader containing the data to be converted
     * @return the converted XLSX sheet
     * @throws IOException if an I/O error occurs during the conversion process
     */
    public XSSFSheet convertSasToXlsx(SasFileReader sasFileReader) throws IOException {
        // Create a new XSSFWorkbook
        XSSFWorkbook workbook = new XSSFWorkbook();
        // Create a new sheet in the workbook
        XSSFSheet sheet = workbook.createSheet();

        // Get the column names from the SasFileReader
        String[] columnNames = sasFileReader.getColumns().stream()
                .map(Column::getName)
                .toArray(String[]::new);

        // Add the column names to the first row of the sheet
        int rowNum = 0;
        int colNum = 0;
        sheet.createRow(rowNum++);
        for (String columnName : columnNames) {
            sheet.getRow(rowNum - 1).createCell(colNum++).setCellValue(columnName);
        }

        // Add the data from the SasFileReader to the sheet
        Object[] rowValues;
        while ((rowValues = sasFileReader.readNext()) != null) {
            sheet.createRow(rowNum++);
            colNum = 0;
            for (Object rowValue : rowValues) {
                if (rowValue != null) {
                    sheet.getRow(rowNum - 1).createCell(colNum++).setCellValue(rowValue.toString());
                } else {
                    sheet.getRow(rowNum - 1).createCell(colNum++).setCellValue("");
                }
            }
        }

        return sheet;
    }
}
