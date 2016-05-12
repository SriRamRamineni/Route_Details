package com.example.sriram.route_details;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import javax.microedition.khronos.egl.EGLDisplay;

public class MainActivity extends AppCompatActivity {
    EditText editText1;
    EditText editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         editText1 = (EditText) findViewById(R.id.nameoffromplace);
         editText2 = (EditText) findViewById(R.id.nameoftoplace);
        Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText1.getText().toString().trim().length()==0 ||editText2.getText().toString().trim().length()==0){
                    if(editText1.getText().toString().trim().length()==0 && editText2.getText().toString().trim().length()==0)
                        Toast.makeText(getApplicationContext(),"Please Enter Your Credentials",Toast.LENGTH_SHORT).show();
                    else if(editText1.getText().toString().trim().length()==0)
                        Toast.makeText(getApplicationContext(),"Please Enter Your Email ID ",Toast.LENGTH_SHORT).show();
                    else if(editText2.getText().toString().trim().length()==0)
                        Toast.makeText(getApplicationContext(),"Please Enter Your Password",Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(getApplication(),mapActivity.class);
                    startActivity(intent);

                }
            }
        });
        Button button = (Button) findViewById(R.id.Register);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplication(),Register.class);
                startActivity(intent1);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
