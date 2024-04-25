package kr.news.ranking;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelExporter {
    public static void exportToExcel(String date, List<NewsStatus> newsStatuses, String fileName) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("대전뉴스");

        //헤더 행 생성
        Row HeaderRow = sheet.createRow(0);
        String[] headers = {"No.", "제목", "IMG"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = HeaderRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        //데이터 행 추가
        for (int i = 0; i < newsStatuses.size(); i++) {
            NewsStatus newsStatus = newsStatuses.get(i);
            Row row = sheet.createRow(i + 1);

            row.createCell(0).setCellValue(newsStatus.getNum());
            row.createCell(1).setCellValue(newsStatus.getTitle());
            row.createCell(2).setCellValue(newsStatus.getImg());


        }

        try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
            workbook.write(fileOut);
        }

        workbook.close();
    }
}
