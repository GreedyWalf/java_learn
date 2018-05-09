package testSort;

import java.util.*;

/**
 *  总结：
 *      TreeMap，TreeSet集合类构造函数中支持定义比较器，实现集合中元素的排序；（一般使用匿名内部类的方式，构建比较器，实现compare(o1,o2)方法）；
 *
 *
 */
public class TestSort {
    public static void main(String[] args) {
        testTreeSet();
//        testTreeMap();
//        testTreeMap2();

//        testCollectionSort();
    }


    /**
     * Set中存储的对象，其类需要实现comparable接口，实现接口中定义的compareTo方法
     */
    private static void testTreeSet(){
        Set<Person> personSet = new TreeSet<>();
        personSet.add(new Person("aaa", 1));
        personSet.add(new Person("bbb", 2));
        personSet.add(new Person("ccc", 4));
        personSet.add(new Person("ddd", 3));
        System.out.println(personSet);


//        Set<String> treeSet = new TreeSet<>(new Comparator<String>() {
//            @Override
//            public int compare(String o1, String o2) {
//                return o1.compareTo(o2);
//            }
//        });
//
//
//        Set<Person> treeSet2 = new TreeSet<>(new Comparator<Person>() {
//            @Override
//            public int compare(Person o1, Person o2) {
//                return o1.getAge().compareTo(o2.getAge());
//            }
//        });

    }

    /**
     * map的键所在的类实现了Comparable接口，实现接口的compareTo方法，将数据插入后，按照键的顺序排列；
     */
    private static void testTreeMap(){
        Map<Person, String> treeMap = new TreeMap<>();
        treeMap.put(new Person("aaa", 1), "001");
        treeMap.put(new Person("bbb", 2), "001");
        treeMap.put(new Person("ddd", 4), "004");
        treeMap.put(new Person("ccc", 3), "003");
        System.out.println(treeMap);
    }


    private static void testTreeMap2(){
        Map<String, Person> treeMap = new TreeMap<>(new Comparator<String>() {
            /**
             * 默认为降序排序
             */
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });


        treeMap.put("11", new Person("aaa", 1));
        treeMap.put("112", new Person("bb", 2));
        treeMap.put("113", new Person("cc", 2));
        treeMap.put("2", new Person("dd", 3));
        System.out.println(treeMap);
    }

    /**
     * 针对list集合将集合中的元素排序；
     */
    private static void testCollectionSort(){
        //Person类实现了Comparable接口，使用Collections.sort方法会默认使用类中定义的比较规则排序；
        List<Person> arrayList = new ArrayList<>();
        arrayList.add(new Person("aaa", 1));
        arrayList.add(new Person("bbb", 2));
        arrayList.add(new Person("ccc", 4));
        arrayList.add(new Person("ddd", 3));
        Collections.sort(arrayList);
        System.out.println(arrayList);


        //在Collections类的sort方法中重新定义比较器
        Collections.sort(arrayList, new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o2.getAge().compareTo(o1.getAge());
            }
        });

        System.out.println(arrayList);
    }
}


/**
 * 自定义类，实现Comparable接口
 */
class Person implements Comparable<Person>{
    private String name;
    private Integer age;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }


    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public Person() {
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public int compareTo(Person o) {
        return o.age-this.age;
    }
}
