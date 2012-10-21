package ir.netak.hadith;

import java.util.ArrayList;

import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class about extends MyCustomActivity {
    ArrayList<Integer> inf= new ArrayList<Integer>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        fa = SP.getString("FaMethod", "2");
        fonts = SP.getString("Font","none");
 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		final Window window = getWindow();
		window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.about);
		setFace();
	}
    public ArrayList<Integer> getCount()
    {
    	ArrayList<Integer> num=new ArrayList<Integer>();
        DataBaseHelper myDbHelper = new DataBaseHelper(this);
    	 try {
 
 	    	myDbHelper.open();
 
 	     } catch(SQLException sqle){
 
 		    throw sqle;
 
 	     }
    	
        SQLiteDatabase db=myDbHelper.getReadableDatabase();
        num.add(db.getVersion());
        Cursor curser=db.rawQuery("SELECT * FROM \"etic\"", null);
        curser.moveToFirst();
        String temp = "nothing";
        int i=0;
        if(curser!=null){
num.add(curser.getCount());
        }
        db.close();
        myDbHelper.close();
        curser.close();
        return num;
    }
    public void setFace()
    {
    	String tmp=new String();
    	String db=new String();
		inf=getCount();
		TextView t=(TextView) findViewById(R.id.txtAbout);
        db="2"+"\nتعداد احادیث:\n"+inf.get(1).toString();
 	
    	PackageInfo pinfo = null;
		try {
			pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    	String versionName = pinfo.versionName;
        tmp=(String) t.getText();
        tmp=tmp.replace("[db]", farsi(db));
        tmp=tmp.replace("[ver]", versionName);
        t.setText(farsi(tmp));
     	t.setTypeface(face);

    }
}
