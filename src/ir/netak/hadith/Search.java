package ir.netak.hadith;


import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



public class Search extends MyCustomListActivity {
    /** Called when the activity is first created. */
	ArrayList<Integer> value=new ArrayList<Integer>();
	HashMap map = new HashMap();
    ArrayList mylist=new ArrayList();
    int id;
    DataBaseHelper myDbHelper = new DataBaseHelper(this);
    SQLiteDatabase db;
    String key=new String();
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        fa = SP.getString("FaMethod", "2");
        fonts = SP.getString("Font","none");

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		final Window window = getWindow();
		window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.search);

        
        final EditText srch=(EditText) findViewById(R.id.search);
        srch.setHint(farsi("جستجو..."));
   	 try {
   		
	    	this.myDbHelper.open();

	     } catch(SQLException sqle){

		    throw sqle;

	     }
        this.db=this.myDbHelper.getReadableDatabase();
        srch.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				key=srch.getText().toString();
				setListAdapter(null);
				setContent();

			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
		key=srch.getText().toString();
		setListAdapter(null);
		setContent();
    }
    @Override
    protected void onDestroy() {
    	// TODO Auto-generated method stub
    	super.onDestroy();
        this.db.close();
        this.myDbHelper.close();
    }
    public void setContent()
    
    {   mylist.clear();
    	value.clear();
    	OrderAdapter m_adapter;
        String query=new String();
       	query="SELECT * FROM \"etic\" where fabody like \"%"+key+"%\" order by src ASC";
       	
        Cursor curser=db.rawQuery(query, null);
       if(curser.moveToFirst())
    		   {
        String temp = new String();
        temp = "nothing";
        String tmp=new String();
        if(curser!=null){
        	do {
        		map = new HashMap();
        		if(temp.compareTo(curser.getString(4))!=0)
        		{   value.add(curser.getInt(0));
                    map.put("head",farsi(curser.getString(3)));
        		try
        		{
        		tmp=curser.getString(2).substring(0, 20)+"...";
        		}
        		catch (Exception e) {
					// TODO: handle exception
        			tmp=curser.getString(2).substring(0, 10)+"...";
				}
                map.put("text",farsi(tmp));
                map.put("icon", R.drawable.tazhib);
        		mylist.add(map);
        		}
        		temp=curser.getString(4);
			} while (curser.moveToNext());
        	m_adapter=new OrderAdapter(this, R.layout.listview1, mylist);
        	setListAdapter(m_adapter);
        }
    		   }
        curser.close();
    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
         Intent intent = new Intent(this,ShowContent.class);
        intent.putIntegerArrayListExtra("allId", value);
        intent.putExtra("srch", "no");
         intent.putExtra("pos", position);
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
