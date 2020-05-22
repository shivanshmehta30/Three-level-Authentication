package com.example.is;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class activity_rgb extends AppCompatActivity {
    int f=0;
    int b[]=new int[3];
    int c=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rgb);

        Intent i=getIntent();
        final int d=i.getIntExtra("d",0);
        final Button red=(Button)findViewById(R.id.RED);
        final Button green=(Button)findViewById(R.id.GREEN);
        final Button blue=(Button)findViewById(R.id.BLUE);
        Button Start=(Button)findViewById(R.id.button1);
        Button Signup=(Button)findViewById(R.id.SignUp);
        if(d==0)
        {
            Signup.setText("Save Pattern");
        }
        else
        {
            Signup.setText("Verify");
        }
        final SharedPreferences sp=this.getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                f=1;
                c=0;
                for(int i=0;i<3;i++)
                {
                    b[i]=0;
                }

                red.setEnabled(true);
                green.setEnabled(true);
                blue.setEnabled(true);
                Toast.makeText(getApplicationContext(),"Type the RGB Sequence",Toast.LENGTH_LONG).show();
            }
        });
        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(f==1 &&b[0]==0)
                {
                    b[0]=c+1;
                    c=c+1;
                }
                red.setEnabled(false);
            }
        });
        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(f==1 &&b[1]==0)
                {
                    b[1]=c+1;
                    c=c+1;
                }
                green.setEnabled(false);
            }
        });
        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(f==1 &&b[2]==0)
                {   b[2]=c+1;
                    c=c+1;
                }
                blue.setEnabled(false);
            }
        });
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                f=0;
                if(d==0)
                {
                    sp.edit().putInt("red",b[0]).apply();
                    sp.edit().putInt("green",b[1]).apply();
                    sp.edit().putInt("blue",b[2]).apply();
                    Toast.makeText(getApplicationContext(),"Pattern Saved",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(activity_rgb.this,MainActivity.class));
                }
                else
                {
                    int r=sp.getInt("red",-1);
                    int g=sp.getInt("green",-1);
                    int bl=sp.getInt("blue",-1);
                    if(r==b[0]&&g==b[1]&&bl==b[2])
                    {
                        Toast.makeText(getApplicationContext(),"Matched",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(activity_rgb.this,activity_fingerprint.class));
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext()," NOt Matched", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });


    }
}