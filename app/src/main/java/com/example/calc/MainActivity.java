package com.example.calc;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {
    EditText editText;
    Button c;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.textView);
        c = findViewById(R.id.button21);

        c.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                deleteAll();
                return true;
            }
        });
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete();
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            editText.setShowSoftInputOnFocus(false);
        } else {
            InputMethodManager im = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
        getSupportActionBar().hide();

    }

    @SuppressLint("SetTextI18n")
    public void click(View view) {
        try {
        String p = ((Button)view).getText().toString();
        if (p.equals("X")) p="*";

        if (view.getId()==R.id.left) {
            moveRight();
        }
         else {
            if (view.getId() == R.id.right) {
                moveLeft();
            } else {
                if (view.getId() == R.id.button24) {
                    try {
                        String h = Calc.main(format(editText.getText().toString()));
                        System.out.println(h);
                        editText.setText(h);
                    } catch (Exception e){
                        editText.setText("error");
                    }
                } else {
                    int i = editText.getSelectionStart();
                    Toast t = Toast.makeText(this, String.valueOf(i), Toast.LENGTH_SHORT);
                    t.show();

                    editText.getText().insert(i, p);
                }
            }
        }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    void deleteAll(){
        editText.getText().clear();
    }
    void delete(){
        int i = editText.getSelectionStart();
        int o = editText.getSelectionEnd();
        try {
            if (i == o) {
                editText.getText().delete(i - 1, i);
            } else
                editText.getText().delete(i, o);
            System.out.println(i + " " + o);
        } catch (Exception ignored){}



    }
    void moveLeft(){
        Toast t = Toast.makeText(this, "влево", Toast.LENGTH_SHORT);
        t.show();
        try {
            editText.setSelection(editText.getSelectionStart() - 1);
        }catch (Exception ignored){}
    }
    void moveRight(){
        Toast t = Toast.makeText(this, "вправо", Toast.LENGTH_SHORT);
        t.show();
        try {
            editText.setSelection(editText.getSelectionStart() + 1);
        }catch (Exception ignored){}
    }
    String format(String s){
        if (s.charAt(0)=='-') s = "0-" + s;
        return s.replaceAll("\\s+","");
    }
}
