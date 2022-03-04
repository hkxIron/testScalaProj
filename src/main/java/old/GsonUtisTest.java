package old;

import org.junit.Before;
import org.junit.Test;

public class GsonUtisTest {
    @Before
    public void setup() throws Exception {}

    @Test
    public void testGson() throws Exception {
        Demo demo = new Demo("test", 1567508590083L);
        String json = GsonUtils.gson().toJson(demo);
        System.out.println("json:" + json); // 即使用转成了string,但反序列化时值仍然正确
        Demo demo2 = GsonUtils.gson().fromJson(json, Demo.class);
        assert demo2.timestamp == 1567508590083L;
        assert demo2.name.equals("test");
        System.out.println("demo2:"+ demo2.toString());
    }
}