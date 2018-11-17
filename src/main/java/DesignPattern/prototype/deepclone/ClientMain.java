package DesignPattern.prototype.deepclone;

import java.io.*;

/**
 * 原型模式：深克隆（实现Serializable接口，序列化）
 */
public class ClientMain {
    public static void main(String[] args) {
        WeeklyLog previous = new WeeklyLog();
        //附件
        Attachment attachment = new Attachment("xxx.jpg");
        previous.setAttachment(attachment);
        previous.setContent("这周有点闲。。");

        WeeklyLog logNew = previous.clone();
        //false
        System.out.println("周报是否相同：" + (previous == logNew));
        //false
        System.out.println("附件是否相同：" + (attachment == logNew.getAttachment()));
    }
}

/**
 * 附件类，实现序列化接口
 */
class Attachment implements Serializable {
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
class WeeklyLog implements Cloneable, Serializable {

    //引用附件类
    private Attachment attachment;

    private String content;

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }


    /**
     * 深拷贝：该类中的引用类型成员所在类需要实现Serializable接口，clone()方法使用对象流将当前对象序列化后，
     * 在反序列化后，获取到的克隆实例，该克隆实例数据完全独立；
     */
    public WeeklyLog clone() {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(this);

            byte[] bytes = bos.toByteArray();
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bis);
            Object o = ois.readObject();
            return (WeeklyLog) o;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
