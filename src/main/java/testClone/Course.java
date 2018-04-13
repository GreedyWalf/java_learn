package testClone;

public class Course implements Cloneable {

    private Detail detail;
    private String courseName;
    private int count;

    public Detail getDetail() {
        return detail;
    }

    public void setDetail(Detail detail) {
        this.detail = detail;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Course(Detail detail, String courseName, int count) {
        this.detail = detail;
        this.courseName = courseName;
        this.count = count;
    }

    public Course(){}


    @Override
    protected Object clone() throws CloneNotSupportedException {
        Course course = (Course) super.clone();
        Detail detail = (Detail) course.getDetail().clone();
        course.setDetail(detail);
        return course;
    }
}

class Detail implements Cloneable{
    public int price;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Detail(int price) {
        this.price = price;
    }

    public Detail(){}


    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
