public class TestThread {

    public static void main(String[] args) throws InterruptedException {
        User user = new User("zhangsan", 10);
        T r = new T(user);
        Thread thread = new Thread(r,"thread-1");
        Thread thread2 = new Thread(r,"thread-2");

        thread.start();
        thread2.start();

        thread.join();
        thread2.join();
        System.out.println("user.getScore=" + user.getScore());
    }
}

class User {
    private String userId;

    private Integer score;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public User(String userId, Integer score) {
        this.userId = userId;
        this.score = score;
    }

    public User() {

    }
}


class T implements Runnable {
    private User user;

    public T() {

    }

    public T(User user) {
        this.user = user;
    }

    @Override
    public synchronized void run() {
        Integer score = user.getScore();
        if (score == null) {
            score = 0;
        }

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        score++;
        System.out.println("score=" + score);
        user.setScore(score);
    }

}