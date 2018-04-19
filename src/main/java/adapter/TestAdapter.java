package adapter;

/**
 * 适配器模式（Adapter Pattern）是作为两个不兼容的接口之间的桥梁；属于结构型模式，结合了两个对立接口的功能；（原来不能在一起工作的那些类可以在一起工作））
 *
 * 特点: 这种模式涉及到一个单一的类，该类负责加入独立的或不兼容的接口功能；
 *
 * 例子：读卡器作为内存卡和笔记本之间的适配器，将内存卡插入读卡器，再将读卡器插入笔记本，这样就可以
 * 通过笔记本来去读取内存卡；
 *
 * 注意：是胚子不是在详细设计时添加的，而是解决正在服役的项目问题，系统中不宜过多使用适配器，结合java类单继承的特性，会使得项目结构变得复杂；
 */
public class TestAdapter {
    public static void main(String[] args) {
        AudioPlayer audioPlayer = new AudioPlayer();

        audioPlayer.play("mp3", "beyond the horizon.mp3");
        audioPlayer.play("mp4", "alone.mp4");
        audioPlayer.play("vlc", "far far away.vlc");
        audioPlayer.play("avi", "mind me.avi");
    }
}


interface MediaPlayer{
    void play(String audioType,String fileName);
}


interface AdvancedMediaPlayer{
    void playVlc(String fileName);
    void playMp4(String fileName);
}


class VlcPlayer implements AdvancedMediaPlayer{

    @Override
    public void playVlc(String fileName) {
        System.out.println("Playing vlc file name: " + fileName);
    }

    @Override
    public void playMp4(String fileName) {
        //什么都不做
    }
}


class Mp4Player implements AdvancedMediaPlayer{

    @Override
    public void playVlc(String fileName) {
        //什么也不做
    }

    @Override
    public void playMp4(String fileName) {
        System.out.println("playing mp4 file name: " + fileName);
    }
}


class MediaAdapter implements MediaPlayer{

    AdvancedMediaPlayer advancedMediaPlayer;


    public MediaAdapter(String audioType){
        if(audioType.equals("vlc")){
            advancedMediaPlayer = new VlcPlayer();
        }else if(audioType.equalsIgnoreCase("mp4")){
            advancedMediaPlayer = new Mp4Player();
        }
    }

    @Override
    public void play(String audioType, String fileName) {
        if(audioType.equalsIgnoreCase("vlc")){
            advancedMediaPlayer.playVlc(fileName);
        }else if(audioType.equalsIgnoreCase("mp4")){
            advancedMediaPlayer.playMp4(fileName);
        }
    }
}


class AudioPlayer implements MediaPlayer{

    MediaAdapter mediaAdapter;

    @Override
    public void play(String audioType, String fileName) {
        //播放 mp3音乐文件的内置支持
        if(audioType.equalsIgnoreCase("mp3")){
            System.out.println("playing mp3 file name: " + fileName);
        }
        //mediaAdapter提供了播放其他文件格式的支持
        else if(audioType.equalsIgnoreCase("vlc") || audioType.equalsIgnoreCase("mp4")){
            mediaAdapter = new MediaAdapter(audioType);
            mediaAdapter.play(audioType, fileName);
        }else{
            System.out.println("Invalid media." + audioType + " format not supported");
        }
    }
}



