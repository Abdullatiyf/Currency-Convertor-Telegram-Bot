package com.company.files;

import com.company.db.Database;
import com.company.entity.Currency;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class WorkWithFiles {
    public static File toPdf() {
        java.io.File file = new java.io.File(Database.BASE_PATH, "curr.pdf");
        List<Currency> currencyList = Database.currencyList;
        try (PdfWriter pdfWriter = new PdfWriter(file);
             PdfDocument pdfDocument = new PdfDocument(pdfWriter);
             Document document = new Document(pdfDocument)) {

            Paragraph paragraph = new Paragraph("Exchange rates to Uzbekistan Sum").setBold();
            paragraph.setTextAlignment(TextAlignment.CENTER);
            document.add(paragraph);
            float[] columns = {40, 40, 50, 70, 70, 70};
            Table table = new Table(columns);
            String[] headers = {"Id", "Currency Code", "Currency Name", "Rate", "Diff", "Date"};
            for (String header : headers) {
                table.addCell(header);
            }
            for (Currency currency : currencyList) {
                table.addCell(String.valueOf(currency.getId()));
                table.addCell(currency.getCcy());
                table.addCell(currency.getCcyNmEN());
                table.addCell(currency.getRate());
                table.addCell(currency.getDiff());
                table.addCell(currency.getDate());
            }
            Paragraph paragraph1 = new Paragraph(" as of " + Database.currencyList.get(0).getDate());
            paragraph1.setTextAlignment(TextAlignment.RIGHT);
            paragraph1.setBold();

            document.add(table);
            document.add(paragraph1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public static File toExcel() {
        File file = new File(Database.BASE_PATH, "curr.xlsx");
        file.getParentFile().mkdirs();
        try (XSSFWorkbook workbook = new XSSFWorkbook();
             FileOutputStream out = new FileOutputStream(file)) {
            XSSFSheet sheet = workbook.createSheet("Currency");
            List<Currency> currencyList = Database.currencyList;
            XSSFRow headRow = sheet.createRow(0);
            headRow.createCell(0).setCellValue("ID");
            headRow.createCell(1).setCellValue("Code");
            headRow.createCell(2).setCellValue("CcyNm_UZC");
            headRow.createCell(3).setCellValue("CcyNm_EN");
            headRow.createCell(4).setCellValue("CcyNm_RU");
            headRow.createCell(5).setCellValue("Rate");
            headRow.createCell(6).setCellValue("Nominal");
            headRow.createCell(7).setCellValue("Diff");
            headRow.createCell(8).setCellValue("Date");
            int rowIndex = 0;
            for (Currency currency : currencyList) {
                XSSFRow loopRow = sheet.createRow(++rowIndex);
                loopRow.createCell(0).setCellValue(currency.getId());
                loopRow.createCell(1).setCellValue(currency.getCode());
                loopRow.createCell(2).setCellValue(currency.getCcyNmUZC());
                loopRow.createCell(3).setCellValue(currency.getCcyNmEN());
                loopRow.createCell(4).setCellValue(currency.getCcyNmRU());
                loopRow.createCell(5).setCellValue(currency.getRate());
                loopRow.createCell(6).setCellValue(currency.getNominal());
                loopRow.createCell(7).setCellValue(currency.getDiff());
                loopRow.createCell(8).setCellValue(currency.getDate());
            }
            for (int i = 0; i < 8; i++) {
                sheet.autoSizeColumn(i);
            }
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public static File toWord() {
        File file = new File(Database.BASE_PATH, "curr.docx");
        file.getParentFile().mkdirs();
        try (XWPFDocument document = new XWPFDocument();
             FileOutputStream out = new FileOutputStream(file)) {
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun run = paragraph.createRun();
            LocalDate date = LocalDate.now();
            run.setText("Currency Information as of " + date.format(DateTimeFormatter.ISO_DATE).toString());
            run.setBold(true);
            run.setCapitalized(true);
            run.setFontSize(24);

            XWPFTable table = document.createTable();
            table.setWidth("100%");

            XWPFTableRow row = table.getRow(0);
            XWPFTableCell cell1 = row.getCell(0);
            cell1.setText("ID");
            cell1.setWidth("20%");

            XWPFTableCell cell2 = row.createCell();
            cell2.setText("Name");
            cell2.setWidth("20%");

            XWPFTableCell cell3 = row.createCell();
            cell3.setText("Rate");
            cell3.setWidth("20%");

            XWPFTableCell cell4 = row.createCell();
            cell4.setText("Diff");
            cell4.setWidth("20%");

            XWPFTableCell cell5 = row.createCell();
            cell5.setText("Date");
            cell5.setWidth("20%");

            List<Currency> currencyList = Database.currencyList;
            for (Currency currency : currencyList) {
                XWPFTableRow loopRow = table.createRow();
                loopRow.getCell(0).setText(String.valueOf(currency.getId()));
                loopRow.getCell(1).setText(currency.getCcyNmEN());
                loopRow.getCell(2).setText(currency.getRate());
                loopRow.getCell(3).setText(currency.getDiff());
                loopRow.getCell(4).setText(currency.getDate());
            }

            XWPFParagraph footerParagraph = document.createParagraph();
            footerParagraph.setAlignment(ParagraphAlignment.RIGHT);
            footerParagraph.createRun().setText(
                    "\n" + " as of " + LocalDateTime.now()
            );
            document.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
