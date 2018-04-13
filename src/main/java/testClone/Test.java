package testClone;

public class Test {

    public static void main(String[] args) throws CloneNotSupportedException {
        Course course = new Course(new Detail(12),"ceshi",10);
        System.out.println(course.getDetail().getPrice());
        System.out.println(course.getCount());
        System.out.println(course.getCourseName());


        Course course2 = (Course) course.clone();
        System.out.println(course2.getDetail().getPrice());
        System.out.println(course2.getCount());
        System.out.println(course2.getCourseName());

        System.out.println(course.getCourseName()==course2.getCourseName());
        System.out.println(course.getDetail() == course.getDetail());


        System.out.println("-------");
        course2.setCourseName("course2Name");
        System.out.println(course.getCourseName());
        System.out.println(course2.getCourseName());

        System.out.println("-----");
        course2.getDetail().setPrice(20);
        System.out.println(course.getDetail().getPrice());
        System.out.println(course2.getDetail().getPrice());
    }
}
