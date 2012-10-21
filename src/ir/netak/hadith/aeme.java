package ir.netak.hadith;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;



public class aeme extends MyCustomListActivity {
    /** Called when the activity is first created. */
	ArrayList <String> value=new ArrayList<String>();
	HashMap map = new HashMap();
    ArrayList mylist=new ArrayList();
	String cat2=new String();
    String cat1=new String();
    String cat=new String();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        fa = SP.getString("FaMethod", "2");
        fonts = SP.getString("Font","none");
        
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		final Window window = getWindow();
		window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.list);
        ListView list=getListView();
setNavigator2();
        setContent();

    }
    public void setNavigator2()
    {
            Button btnHome=(Button) findViewById(R.id.button1);
            btnHome.setText(farsi("خانه"));
            btnHome.setVisibility(View.VISIBLE);
            Button btnMozo=(Button) findViewById(R.id.button15);
            btnMozo.setText(farsi("ائمه"));
            btnMozo.setVisibility(View.VISIBLE);
            btnHome.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				    Intent HA = new Intent(getApplicationContext(),HadithActivity.class);
					HA.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(HA);	
					finish();
				}
			});
            btnMozo.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(IsNotCurrent(aeme.class))
					{
	                Intent intent = new Intent(getApplicationContext(),aeme.class);
	                intent.putExtra("q", "");
	                 startActivity(intent);
					}
				}
			});
            
  
    }
    public Boolean IsNotCurrent(Object ac)
    {
    	Boolean tmp=new Boolean(true);
    	if(this.getClass()==ac)
    	{
    		tmp=false;
    	}
    	return tmp;
    }
    
    public void setContent()
    {        OrderAdapter m_adapter;
        DataBaseHelper myDbHelper = new DataBaseHelper(this);
    	 try {
 
 	    	myDbHelper.open();
 
 	     } catch(SQLException sqle){
 
 		    throw sqle;
 
 	     }
    	
        SQLiteDatabase db=myDbHelper.getReadableDatabase();
        Cursor curser=db.rawQuery("SELECT * FROM \"etic\" order by az ASC", null);
        curser.moveToFirst();
        String temp = "nothing";
        int i=0;
        if(curser!=null){
        	do {
        		map = new HashMap();
        		
        		if(temp.compareTo(curser.getString(3))!=0)
        		{
                  value.add(curser.getString(3));
                    i++;
        		map.put("head","");
                map.put("text",farsi(curser.getString(3)));
                map.put("icon", R.drawable.tazhib);
        		mylist.add(map);
        		}
  		

        		temp=curser.getString(3);

			} while (curser.moveToNext());
        	m_adapter=new OrderAdapter(this, R.layout.listview1, mylist);
        	setListAdapter(m_adapter);
        }
        db.close();
        myDbHelper.close();
        curser.close();
    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String q=new String();
        q="where az=\""+value.get(position)+"\"";
        
         Intent intent = new Intent(this,rawHadith.class);
         intent.putExtra("key", q);
         intent.putExtra("emam", value.get(position));
         startActivity(intent);
        }
@Override
public void onBackPressed() {
	// TODO Auto-generated method stub
	super.onBackPressed();
    Intent intent = new Intent(getApplicationContext(),HadithActivity.class);
	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    startActivity(intent);
    finish();
}

private class OrderAdapter extends ArrayAdapter {
    private ArrayList items;
     public OrderAdapter(Context context, int textViewResourceId, ArrayList items) {
            super(context, textViewResourceId, items);
            this.items = items;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            
            if (v == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.listview1, null);
            }
HashMap map=new HashMap();
map=(HashMap) items.get(position);
                    TextView tt = (TextView) v.findViewById(R.id.title);
                    TextView bt = (TextView) v.findViewById(R.id.about);
                    ImageView im = (ImageView) v.findViewById(R.id.thumbnail);
                    tt.setText((CharSequence) map.get("head"));
                    bt.setText((CharSequence) map.get("text"));
                    im.setImageResource(R.drawable.tazhib);
                    tt.setTypeface(face);
                    bt.setTypeface(face);
                    


            return v;
    }
}
}
