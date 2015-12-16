package com.cloudproj.microforum;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class ForumPage extends AppCompatActivity {

    public String [] usersList = {"Janhavi","Sagar","Uttu","Shweta"};
    public String [] commentList = {"Let Us C","c++","JAVA","Jsp"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ListView comments;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_page);


        Intent intent = getIntent();
        Log.d("Janhavi", intent.getStringExtra("Question"));
        Toast.makeText(getApplicationContext(), intent.getStringExtra("ForumName"), Toast.LENGTH_LONG);
        ((TextView)findViewById(R.id.tvFPQuestion)).setText(intent.getStringExtra("Question"));

        comments=(ListView) findViewById(R.id.CommentsList);
        comments.setAdapter(new CustomListAdapter(this, usersList, commentList));

    }
}
