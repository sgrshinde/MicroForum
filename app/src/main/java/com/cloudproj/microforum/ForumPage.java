package com.cloudproj.microforum;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class ForumPage extends AppCompatActivity {

    //forum page
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_page);

        Intent intent = getIntent();
        Log.d("Janhavi", intent.getStringExtra("Question"));
        Toast.makeText(getApplicationContext(), intent.getStringExtra("ForumName"), Toast.LENGTH_LONG);
        ((TextView)findViewById(R.id.tvFPQuestion)).setText(intent.getStringExtra("Question"));


        /*Button btnPost = (Button)findViewById(R.id.btnPost);
        TextView rowTextView = new TextView(this);
        rowTextView.setHint("Comment");
        // add the textview to the linearlayout
        myLinearLayout.addView(rowTextView);*/

    }
}
