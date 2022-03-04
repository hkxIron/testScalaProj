package old;

import java.util.*;

class B{
    private String name;
    public double score;
    public String slot;
    public B(String name, double score, String slot){
       this.name = name;
       this.score = score;
       this.slot = slot;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return "name is: "+name+" score is:"+score +" slot:"+slot+"\n";
    }
}

class A implements Comparable<A>{
    private String name;
    private Integer order;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrder() {
        return order;
    }
    public void setOrder(Integer order) {
        this.order = order;
    }
    @Override
    public String toString() {
        return "name is "+name+" order is "+order;
    }
    @Override
    public int compareTo(A a) {
        return this.order.compareTo(a.getOrder());
    }

}

public class SortTest {

    public static void main(String[] args) {
        List<String> lists = new ArrayList<String>();
        List<A> list = new ArrayList<A>();
        List<B> listB = new ArrayList<B>();
        lists.add("5");
        lists.add("2");
        lists.add("9");
        //lists中的对象String 本身含有compareTo方法，所以可以直接调用sort方法，按自然顺序排序，即升序排序
        Collections.sort(lists);

        A aa = new A();
        aa.setName("aa");
        aa.setOrder(1);
        A bb = new A();
        bb.setName("bb");
        bb.setOrder(2);
        list.add(bb);
        list.add(aa);
        //list中的对象A实现Comparable接口
        Collections.sort(list);

        Map<String, Integer> prior = new HashMap<String, Integer>(){{
            put("trivia",0);
            put("no_sound",1);
        }};
        listB.add(new B("a2", 0.5, "trivia"));
        listB.add(new B("a3", 0.5, "no_sound"));
        listB.add(new B("a5", 0.5, "trivia"));
        listB.add(new B("a1", 1.0, "trivia"));
        listB.add(new B("a4", 0.7, "no_sound"));

        //根据Collections.sort重载方法来实现
        Collections.sort(listB, new Comparator<B>(){
            @Override
            public int compare(B b1, B b2) {
                int res=0;
                if(b1.score<b2.score) {
                    res = 0;
                }else if(b1.score== b2.score){
                    if(prior.containsKey(b1.slot)&&prior.containsKey(b2.slot)) {
                        res = -prior.get(b1.slot).compareTo(prior.get(b2.slot));
                    }
                }else{
                    res = -1;
                }
                return res;
                //return b1.score<b2.score?0:-1;
                //return b1.score<b2.score?1:0;
                //return b1.getName().compareTo(b2.getName());
            }

        });

        System.out.println(lists);
        System.out.println(list);
        System.out.println("sort old.B:");
        System.out.println(listB);

    }
}

