
public class Demo {
    public String name;
    public long timestamp;
    public Demo(String name, long timestamp){
       this.name = name;
       this.timestamp = timestamp;
    }
    @Override
    public String toString() {
        return "name:"+name+" timestamp:" + timestamp;
    }
}