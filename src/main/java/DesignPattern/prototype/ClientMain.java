package DesignPattern.prototype;

/**
 * 原型模式：
 * 抽象原型类：Object，提供clone()方法，能生成克隆对象；
 * 具体原型类：WeeklyLog
 * 客户类：ClientMain：提供WeeklyLog的实例，调用clone()方法产生新的对象；
 */
public class ClientMain {
    public static void main(String[] args) {
        WeeklyLog previous = new WeeklyLog();
        //附件
        Attachment attachment = new Attachment("xxx.jpg");
        previous.setAttachment(attachment);
        previous.setContent("这周有点闲。。");

        WeeklyLog logNew = previous.clone();
        System.out.println("周报是否相同：" + (previous == logNew));
        //浅克隆，引用类型成员变量，将地址应用复制一份给克隆对象，在堆内存中共用一个实例对象；
        System.out.println("附件是否相同：" + (attachment == logNew.getAttachment()));
    }
}


/**
 * 附件类
 */
class Attachment {
    //附件名
    private String name;

    public Attachment() {

    }

    public Attachment(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void download() {
        System.out.println("下载附件，文件名为：" + name);
    }
}


/**
 * 具体原型类
 */
class WeeklyLog implements Cloneable {

    //引用附件类
    private Attachment attachment;

    private String date;

    private String content;

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }


    //子类重写Object类的clone方法（浅克隆）
    @Override
    public WeeklyLog clone() {
        try {
            Object obj = super.clone();
            return (WeeklyLog) obj;
        } catch (CloneNotSupportedException e) {
            System.out.println("不支持克隆。。");
            e.printStackTrace();
        }

        return null;
    }
}