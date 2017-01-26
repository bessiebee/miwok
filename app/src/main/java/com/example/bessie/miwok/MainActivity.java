package com.example.bessie.miwok;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView numbers = (TextView) findViewById(R.id.numbers);
        TextView family = (TextView) findViewById(R.id.family);
        TextView colors = (TextView) findViewById(R.id.colorss);
        TextView phrases = (TextView) findViewById(R.id.phrases);

        assert numbers != null;
        numbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent numbersIntent = new Intent(MainActivity.this, numbers.class);
                startActivity(numbersIntent);
            }
        });

        assert family != null;
        family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent familyIntent = new Intent(MainActivity.this,FamilyMembers.class);
                startActivity(familyIntent);
            }
        });

        assert colors != null;
        colors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent colorsIntent = new Intent(MainActivity.this, colors.class);
                startActivity(colorsIntent);
            }
        });

        assert phrases != null;
        phrases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent phrasesIntent = new Intent(MainActivity.this, phrases .class);
                startActivity(phrasesIntent);
            }
        });

    }

}
