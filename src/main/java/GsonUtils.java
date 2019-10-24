import com.google.gson.Gson;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;

public class GsonUtils {
    private static final Gson instance = createGson();

    public static Gson gson() {
        return instance;
    }

    private static Gson createGson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setLongSerializationPolicy(LongSerializationPolicy.STRING)
                .create();
    }
}