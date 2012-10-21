package ir.netak.hadith;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.text.ClipboardManager;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ShowContent extends MyCustomActivity {
	String cat2=new String();
	String tmp0=new String();
	String tmp1=new String();
    String cat1=new String();
    String cat=new String();
    Boolean hasKey;
    Boolean isSrch;
    String key=new String();
    String emam=new String();
    ArrayList<Integer> allId= new ArrayList<Integer>();
int id;
int pos;
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
		setContentView(R.layout.showcnt);
		setFace();
		Bundle extras = getIntent().getExtras(); 
		hasKey=extras.containsKey("key");
		if(hasKey)
		{
		      key=extras.getString("key");
		      emam=extras.getString("emam");
		}
		else
		{
			cat=extras.getString("cat");
			cat1=extras.getString("cat1");
			cat2=extras.getString("cat2");
		}
isSrch=extras.containsKey("srch");
pos=extras.getInt("pos");
allId=extras.getIntegerArrayList("allId");
id=allId.get(pos);


		setContent();
		TextView T1=(TextView) findViewById(R.id.cnt);
		registerForContextMenu(T1);
        Button b1=(Button) findViewById(R.id.next);
        Button b2=(Button) findViewById(R.id.prev);

        b1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // Perform action on clicks
            	if(pos!=(allId.size()-1))
            	{
            	pos++;
            	id=allId.get(pos);
                setContent();
            }
            }
        });
        b2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // Perform action on clicks
            	if(pos!=0)
            	{
            	pos--;
            	id=allId.get(pos);
                setContent();
            }
            }
        });

       

  }
    public void setFace()
    {
        TextView T1=(TextView) findViewById(R.id.cnt);
        Button b1=(Button) findViewById(R.id.next);
        Button b2=(Button) findViewById(R.id.prev);
        String st1=(String)b1.getText();
        String st2=(String)b2.getText();
        b1.setText(farsi(st1));
        b2.setText(farsi(st2));
        b1.setTypeface(face);
        b2.setTypeface(face);
     	T1.setTypeface(face);

    }
    void setContent()
    {
        DataBaseHelper myDbHelper = new DataBaseHelper(this);
    	 try {
 
 	    	myDbHelper.open();
 
 	     } catch(SQLException sqle){
 
 		    throw sqle;
 
 	     }
         SQLiteDatabase db=myDbHelper.getReadableDatabase();
         Cursor curser=db.rawQuery("SELECT * FROM \"etic\" where _id="+"\""+id+"\" order by src ASC", null);

         curser.moveToFirst();
         if(curser!=null){
             TextView t1=(TextView) findViewById(R.id.cnt);
             t1.setText("");
             tmp0=curser.getString(3)+" می فرمایند :"+"\n\n"+curser.getString(1)+"\n\n"+curser.getString(2)+"\n\n"+curser.getString(4);
             tmp1=curser.getString(3)+" می فرمایند :"+"\n\n"+curser.getString(2)+"\n\n"+curser.getString(4);
             t1.setText(farsi(tmp0));
             
	     }
         db.close();
         myDbHelper.close();
    }
    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
      super.onActivityResult(reqCode, resultCode, data);
      
      String name=new String();
      String no=new String();
      switch (reqCode) {
        case (1) :
          if (resultCode == Activity.RESULT_OK) {
            Uri contactData = data.getData();
            Cursor c =  managedQuery(contactData, null, null, null, null);
            if (c.moveToFirst()) {
            	String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

            	Cursor phoneCur = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[] { id }, null);

                if (phoneCur.moveToFirst()) {
                    name = phoneCur.getString(phoneCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    no = phoneCur.getString(phoneCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                }

              // TODO Whatever you want to do with the selected contact name.
              Intent smsIntent = new Intent(Intent.ACTION_VIEW);
              smsIntent.setType("vnd.android-dir/mms-sms");
              smsIntent.putExtra("address", no);
   
              smsIntent.putExtra("sms_body",tmp0);
              startActivity(smsIntent);
              

            }
            
          }
        case (2):
            if (resultCode == Activity.RESULT_OK) {
                Uri contactData = data.getData();
                Cursor c =  managedQuery(contactData, null, null, null, null);
                if (c.moveToFirst()) {
                	String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

                	Cursor phoneCur = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[] { id }, null);

                    if (phoneCur.moveToFirst()) {
                        name = phoneCur.getString(phoneCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        no = phoneCur.getString(phoneCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    }

                  // TODO Whatever you want to do with the selected contact name.
                  Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                  smsIntent.setType("vnd.android-dir/mms-sms");
                  smsIntent.putExtra("address", no);
       
                  smsIntent.putExtra("sms_body",tmp1);
                  startActivity(smsIntent);
                  

                }
                
              }
          break;
      }
    }

    @Override
    public void onBackPressed() {
    	// TODO Auto-generated method stub
    	super.onBackPressed();
    	Intent intent = new Intent(this,rawHadith.class);
        if(!hasKey && !isSrch)
        {
       intent.putExtra("cat",cat);
       intent.putExtra("cat1", cat1);
       intent.putExtra("cat2", cat2);
       startActivity(intent);
        }
        else if (isSrch)
        {
        	
        	Intent i = new Intent(this,Search.class);
        	
        	startActivity(i);
        }
        else
        {
       	 intent.putExtra("key", key);
       	intent.putExtra("emam", emam);
       	startActivity(intent);
        }
        
        finish();
    }
	/* Creates the menu items */
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, farsi("تنظیمات"));
        menu.add(0, 1, 0, farsi("رونوشت برداشتن از تمام متن"));
        menu.add(0, 2, 0, farsi("رونوشت برداشتن از متن فارسی"));
        menu.add(0, 3, 0, farsi("فرستادن  تمام متن با پیام کوتاه"));
        menu.add(0, 4, 0, farsi("فرستادن  متن فارسی پیام کوتاه"));

     

        return true;
    }
 
    /* Handles item selections */
    public boolean onOptionsItemSelected(MenuItem item) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE); 
        switch (item.getItemId()) {
    	case 0:
            Intent intent=new Intent(this,Setting.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return true;
    	case 1:
            clipboard.setText(tmp0);

            return true;
    	case 2:
            clipboard.setText(tmp1);

            return true;
    	case 3:
    		Intent i1 = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
    		startActivityForResult(i1, 1);
    	case 4:
    		Intent i2 = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
    		startActivityForResult(i2, 2);
      return true;
        }
        return false;
    }

}
