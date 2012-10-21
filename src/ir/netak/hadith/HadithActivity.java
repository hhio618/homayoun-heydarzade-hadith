package ir.netak.hadith;

import java.util.List;



import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HadithActivity extends MyCustomActivity {
    /** Called when the activity is first created. */

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        fa = SP.getString("FaMethod", "2");
        fonts = SP.getString("Font","none");
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		final Window window = getWindow();
		window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main);
        final TextView T1=(TextView) findViewById(R.id.textView1);
        final Button B1=(Button) findViewById(R.id.button1);
        final Button B2=(Button) findViewById(R.id.button2);
        final Button B3=(Button) findViewById(R.id.button3);
        final Button B4=(Button) findViewById(R.id.button4);
      final Button B5=(Button) findViewById(R.id.button5);
        setFace();
            B1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // Perform action on clicks
           
                Intent intent = new Intent(HadithActivity.this,Hadithlist.class);
                intent.putExtra("q", "");
                 startActivity(intent);
        
                 
                 
            }
        });
        
        B2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // Perform action on clicks
                Intent intent = new Intent(HadithActivity.this,aeme.class);
                 startActivity(intent);
            }
        });
        B3.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // Perform action on clicks
                Intent intent = new Intent(HadithActivity.this,Setting.class);
                 startActivity(intent);
            }
        });
        B4.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // Perform action on clicks
                Intent intent = new Intent(HadithActivity.this,about.class);
                 startActivity(intent);
            }
        });
      B5.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // Perform action on clicks
                Intent intent = new Intent(HadithActivity.this,Search.class);
                intent.putExtra("q", "");
                 startActivity(intent);
            }
        });
        

    }
    public void setFace()
    {
         TextView T1=(TextView) findViewById(R.id.textView1);
         Button B1=(Button) findViewById(R.id.button1);
         Button B2=(Button) findViewById(R.id.button2);
         Button B3=(Button) findViewById(R.id.button3);
         Button B4=(Button) findViewById(R.id.button4);
        Button B5=(Button) findViewById(R.id.button5);
     	B1.setTypeface(stock);
     	B2.setTypeface(stock); 
     	B3.setTypeface(stock);
		B4.setTypeface(stock);
     B5.setTypeface(stock);
     	T1.setTypeface(stock);
     	String str_T1=(String)T1.getText().toString();
     	T1.setText(farsi(str_T1));
        String str_B1 = (String) B1.getText().toString();
        String str_B2 = (String) B2.getText().toString();
        String str_B3 = (String) B3.getText().toString();
        String str_B4 = (String) B4.getText().toString();
       String str_B5 = (String) B5.getText().toString();
        B1.setText(farsi(str_B1)); 
        B2.setText(farsi(str_B2)); 
        B3.setText(farsi(str_B3)); 
        B4.setText(farsi(str_B4)); 
     B5.setText(farsi(str_B5));
    }
@Override
public void onBackPressed() {
	// TODO Auto-generated method stub
	super.onBackPressed();

	finish();
	android.os.Process.killProcess(android.os.Process.myPid()) ;
}

}