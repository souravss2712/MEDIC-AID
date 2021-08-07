package in.codepredators.vedanta.converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import in.codepredators.vedanta.models.Medicine;

public class DataTypeConverter {
    private static Gson gson = new Gson();
    @TypeConverter
    public static List<Medicine> stringToList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Medicine>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String ListToString(List<Medicine> someObjects) {
        return gson.toJson(someObjects);
    }


}
