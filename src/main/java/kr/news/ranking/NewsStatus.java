package kr.news.ranking;

public class NewsStatus {
    private int num;
    private String title;
    private String img;

    public NewsStatus() {
    }

    public NewsStatus(int num, String title, String img) {
        this.num = num;
        this.title = title;
        this.img = img;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "NewsStatus{" +
                "num=" + num +
                ", title='" + title + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
