package com.zeido.mohannad.timer.tea.teatimer.Database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SampleDataProvider {
    public static List<Tea> teaList;
    public static Map<String, Tea> teaMap;

    static {
        teaList = new ArrayList<>();
        teaMap = new HashMap<>();

        addItem(new Tea( "Black Tea", null, 2, 100, ""));
        addItem(new Tea( "Green Tea", null, 2, 80, ""));
        addItem(new Tea( "Oolong Tea", null, 2, 100, ""));
        addItem(new Tea( "White Tea", null, 2, 100, ""));
        addItem(new Tea( "Earl Grey", null, 2, 100, ""));
        addItem(new Tea( "Lady Earl Grey", null, 2, 100, ""));
    }

    private static void addItem(Tea item) {
        teaList.add(item);
        teaMap.put(item.getTeaID(), item);
    }

}