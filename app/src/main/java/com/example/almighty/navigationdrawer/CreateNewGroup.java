package com.example.almighty.navigationdrawer;


        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.database.Cursor;
        import android.net.Uri;
        import android.os.AsyncTask;
        import android.provider.ContactsContract;
        import androidx.appcompat.app.AppCompatActivity;
        import android.os.Bundle;
        import androidx.appcompat.widget.Toolbar;
        import android.util.Log;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageButton;
        import android.widget.Toast;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.ArrayList;

public class CreateNewGroup extends AppCompatActivity {
    ArrayList<String> numbers;
    private String url="http://androindian.com/apps/fm/api.php";
    public static final String MYPreferences="MyPrefs";
    private static final int REQUEST_CODE = 1;
    private static final String TAG = "ContactsAccessing";
    Button button;
    EditText gname,groupmembers;
    ImageButton addperson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_group);
        button = (Button) findViewById(R.id.cgbutton);
        gname= (EditText) findViewById(R.id.edit5);
        groupmembers= (EditText) findViewById(R.id.edit6);
        addperson= (ImageButton) findViewById(R.id.addperson);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Creat a New Group");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences sharedPreferences = getSharedPreferences(MYPreferences, Context.MODE_PRIVATE);
        final String s10 = sharedPreferences.getString("phoneKey",null);;
       /* addperson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("content://contacts");
                Intent intent = new Intent(Intent.ACTION_PICK, uri);
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });*/
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Gname=gname.getText().toString().trim();
                numbers = new ArrayList<>();
                numbers.add(s10);
                JSONObject object = new JSONObject();
                try {
                    object.put("admin_id", s10);

                    //array
                    object.put("members", new JSONArray(numbers));

                    object.put("gname", Gname);
                    object.put("action", "create_group");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                new Action_array().execute(object.toString());
                Log.e("object", "" + object);
            }
        });
    }
//access contacts
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Uri uri = intent.getData();
                String[] projection = { ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME };

                Cursor cursor = getContentResolver().query(uri, projection,
                        null, null, null);
                cursor.moveToFirst();

                int numberColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(numberColumnIndex);
                groupmembers.setText(groupmembers.getText().toString()+","+number);
                numbers.add(number);
                int nameColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                String name = cursor.getString(nameColumnIndex);

                Log.d(TAG, "ZZZ number : " + number +" , name : "+name);

            }
        }
    }

    private class Action_array extends AsyncTask<String,String,String> {
        JSONObject jsonObject;
        @Override
        protected String doInBackground(String... params) {
            jsonObject=JsonFunction.getJsonFromUrlparam(url,params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                if (jsonObject.getString("response").equals("success")){
                    Toast.makeText(getApplicationContext(),jsonObject.getString("call_back"),Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // I do not want this...
                // Home as up button is to navigate to Home-Activity not previous acitivity
                super.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
