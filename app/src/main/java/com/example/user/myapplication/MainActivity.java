package com.example.user.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView taskListView;
    List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();
    DataSource dataSource = new DataSource();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setTitle("TASKS LIST");

        aList = dataSource.getData();
        taskListView = (ListView) findViewById(R.id.taskList);

        String[] from = {"listview_image", "listview_title", "listview_discription"};
        int[] to = {R.id.taskImage, R.id.taskTitle, R.id.taskDescription};
        SimpleAdapter simpleAdapter = new SimpleAdapter(MainActivity.this, aList,  R.layout.activity_main_listview, from, to);

        taskListView.setAdapter(simpleAdapter);

        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                aList.get(position).get("listview_discription");
                Toast.makeText(getApplicationContext(),"Position : "+ aList.get(position).get("listview_title").toString() , Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, TaskGuideActivity.class);
                intent.putExtra("taskID", position);
                startActivity(intent);
            }
        });
    }
}
