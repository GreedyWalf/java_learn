package base.nomal;

import java.util.HashMap;
import java.util.Map;

public class TestExtend {
    public static void main(String[] args) {
//        B b = new B("zhangsan");
//        System.out.println(b.getName());

//        Map<String, String> map = new HashMap<>(10);

//        HashMap<String, Integer> hashMap = new HashMap<>(8);
        HashMap<String, Integer> hashMap = new HashMap<>();
    }
}


class A{
    String name;

    A(String name){
        this.name = name;
    }
}

class B extends A{

    B(String name){
        super(name);
    }

    String getName(){
        return name;
    }
}
