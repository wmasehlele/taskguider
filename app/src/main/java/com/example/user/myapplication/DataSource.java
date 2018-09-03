package com.example.user.myapplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataSource {

    List<HashMap<String, String>> aList;

    String[] listviewTitle = new String[]{
        "Making a coffee",
        "Frying eggs",
    };

    int[] listviewImage = new int[]{
        R.drawable.ic_android_black_24dp,
        R.drawable.ic_android_black_24dp,
    };

    String[] listviewShortDescription = new String[]{
        "How to prepare a good cup of coffee",
        "How to prepare terrible fried eggs",
    };

    String[] taskSteps = new String[]{
        "Step 1. Use an electric kattle to boil water.;"
        +"Step 2. Get a cup, and tea spoon.;"
        +"Step 3. Get sugar, milk and coffee.;"
        +"Step 4. Put 1 spoon of coffee into the cup.;"
        +"Step 5. Put 1 spoon of sugar into the cup.;"
        +"Step 6. Pour 2 liter milk in the cup.;"
        +"Step 7. Pour boiled water in the cup and stair;"
        +"Step 8. Serve coffee with smile;",

        "Step 1. Put a frying pan on the stove.;"
        +"Step 2. Switch on the stove to heat the stove plate.;"
        +"Step 3. Poor just enough oil in the pan.;"
        +"Step 4. Get 6 eggs and break them into a bowl.;"
        +"Step 5. Stair the eggs and put in some salt if you want.;"
        +"Step 6. Allow the oil to pre-heat.;"
        +"Step 7. Pour the eggs in the pan;"
        +"Step 8. Boom you have fried eggs;"
        +"Step 9. Serve eggs with smile;",
    };

    String[] taskTimer = new String[]{
            "7 Minutes",
            "9 Minutes",
    };

    public List<HashMap<String, String>> getData (){
        aList = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < 2; i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("listview_title", listviewTitle[i]);
            hm.put("listview_discription", listviewShortDescription[i]);
            hm.put("listview_image", Integer.toString(listviewImage[i]));
            hm.put("listview_task_steps", taskSteps[i]);
            hm.put("listview_task_timer", taskTimer[i]);
            aList.add(hm);
        }

        return aList;
    }
}
