package kr.news.ranking;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ExcelExporter {
    public static void exportToExcel(String date, List<NewsStatus> newsStatuses, String fileName) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("대전뉴스");

        //헤더 행 생성
        Row HeaderRow = sheet.createRow(0);
        String[] headers = {"No.", "제목", "IMG"};

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);


        for (int i = 0; i < headers.length; i++) {
            Cell cell = HeaderRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(cellStyle);
        }

        //데이터 행 추가
        for (int i = 0; i < newsStatuses.size(); i++) {
            NewsStatus newsStatus = newsStatuses.get(i);
            Row row = sheet.createRow(i + 1);
            row.setHeightInPoints(50);

            row.createCell(0).setCellValue(newsStatus.getNum());
            row.createCell(1).setCellValue(newsStatus.getTitle());

            String photoName = newsStatus.getImg();
            FileInputStream is = new FileInputStream(photoName);
            byte[] bytes = IOUtils.toByteArray(is);
            int pictureldx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
            is.close();

            XSSFCreationHelper helper = (XSSFCreationHelper) workbook.getCreationHelper();
            XSSFDrawing drawing = (XSSFDrawing) sheet.createDrawingPatriarch();

            XSSFClientAnchor anchor = helper.createClientAnchor();
            anchor.setCol1(2);
            anchor.setRow1(i + 1);

            XSSFPicture pic = drawing.createPicture(anchor, pictureldx);

            pic.resize(0.4);
        }
        sheet.setColumnWidth(0, 1000);
        sheet.setColumnWidth(1, 18000);
        sheet.setColumnWidth(2, 3500);




        try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
            workbook.write(fileOut);
        }

        workbook.close();
    }
}
