package kr.news.ranking;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewsScraper {

    public static void main(String[] args) {
        String url = "https://media.naver.com/press/656/ranking";

        try {
            // 웹 페이지에서 HTML 문서를 가져온다.
            Document doc = Jsoup.connect(url).get();

            Date date = new Date();
            DateFormat df = new SimpleDateFormat("yyyy.MM.dd.EEE");
            String today = df.format(date);

            Elements rankingNums = doc.select(".list_ranking_num"); //랭킹 가져오기
            Elements newsTitles = doc.select(".list_title"); // 제목 가져오기
            Elements imgs = doc.select(".list_img img"); // 이미지들 가져오기

            List<NewsStatus> newsStatuses = new ArrayList<>();

            for (int i = 0; i < rankingNums.size(); i++) {
                int num = Integer.parseInt(rankingNums.get(i).text());
                String title = newsTitles.get(i).text();
                String imgSrc = imgs.get(i).attr("src");

                // 이미지 다운로드 및 저장
                String imgFileName = "image_" + num + ".png";
                String imgFilePath = "./images/" + imgFileName;
                downloadImage(imgSrc, imgFilePath);

                newsStatuses.add(new NewsStatus(num, title, imgFilePath));
            }


            System.out.println("오늘의 대전일보 뉴스(" + today + ")");
            System.out.println("순번 | 제목 | 이미지");

            for (NewsStatus newsStatus : newsStatuses) {
                System.out.println(newsStatus);
            }

            //엑셀 파일로 저장
            String excellFileName = "NewsRanking_" + today.replace(".", "_") + ".xlsx";
            ExcelExporter.exportToExcel(today, newsStatuses, excellFileName);
            System.out.println("엑셀 파일로 저장 완료 : " + excellFileName);
            //PDF 파일로 저장
            String pdfFileName = "NewsRanking_" + today.replace(".", "_") + ".pdf";
            PdfExporter.exportToPdf(today, newsStatuses, pdfFileName);
            System.out.println("PDF 파일로 저장 완료 : " + pdfFileName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void downloadImage(String imageUrl, String destinationPath) {
        try {
            URL url = new URL(imageUrl);
            BufferedImage image = ImageIO.read(url);
            File outputFile = new File(destinationPath);
            ImageIO.write(image, "png", outputFile);
            System.out.println("이미지 다운로드 완료: " + destinationPath);
        } catch (IOException e) {
            System.out.println("이미지 다운로드 실패: " + e.getMessage());
        }
    }
}

