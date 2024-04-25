package kr.news.ranking;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public class PdfExporter {
    public static void exportToPdf(String date, List<NewsStatus> newsStatuses, String fileName) throws FileNotFoundException  {

        try{
            PdfFont font = PdfFontFactory.createFont("Pretendard-SemiBold.ttf", PdfEncodings.IDENTITY_H, true);

            PdfDocument pdfDoc = new PdfDocument(new PdfWriter(fileName));
            Document doc = new Document(pdfDoc);

            Paragraph title = new Paragraph("대전뉴스 TOP20("+ date +")");
            doc.add(title.setFont(font));

            float[] columnWidths = {50, 100, 50};
            Table table = new Table(UnitValue.createPercentArray(columnWidths));
            String[] headers = {"No.", "제목", "IMG"};

            for(String header : headers){
                Cell cell = new Cell();
                cell.add(new Paragraph(header).setFont(font));
                cell.setTextAlignment(TextAlignment.CENTER);
                table.addHeaderCell(cell);
            }

            for(NewsStatus newsStatus : newsStatuses){
                table.addCell(new Cell().add(new Paragraph(String.valueOf(newsStatus.getNum())).setFont(font)));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(newsStatus.getTitle()))).setFont(font));

                //이미지 추가
                ImageData imageData = ImageDataFactory.create(newsStatus.getImg());
                Image image = new Image(imageData);
                table.addCell(new Cell().add(image.setAutoScale(true))); // 이미지 셀에 이미지 추가
            }


            doc.add(table);
            doc.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
