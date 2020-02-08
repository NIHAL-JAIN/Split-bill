package com.example.almighty.navigationdrawer;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;

public class FinanceManagement extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String url = "http://androindian.com/apps/fm/api.php";
    public static final String MYPreferences="MyPrefs";
    TextView name,email_id,chooseimage;
    LinearLayout l1,l2,l3,l4,l5,l6;
    private  RoundView imageViewRound;
    LinearLayout linearLayout;
    int SELECT_PICTURE=1;
    String selectedImagePath;
    public static final String Image="imageKey";
    String s11,s12,s13;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finance_management2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Split Bill");

        l1 = (LinearLayout) findViewById(R.id.insertsavings);
        l2 = (LinearLayout) findViewById(R.id.showsavings);
        l3 = (LinearLayout) findViewById(R.id.addgroups);
        l4 = (LinearLayout) findViewById(R.id.groupinfo);
        l5 = (LinearLayout) findViewById(R.id.addpersontogrouop);
        l6 = (LinearLayout) findViewById(R.id.inserdialytrans);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {


        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View im = navigationView.getHeaderView(0);
        imageViewRound = (RoundView) im.findViewById(R.id.imageView_round);
        name = (TextView) im.findViewById(R.id.textView2);
        email_id = (TextView) im.findViewById(R.id.emailid);
        SharedPreferences sharedPreferences = getSharedPreferences(MYPreferences, Context.MODE_PRIVATE);
        s11 = sharedPreferences.getString("imageKey", null);
        s12 = sharedPreferences.getString("username", null);
        s13 = sharedPreferences.getString("useremail", null);
        final Dialog d = new Dialog(FinanceManagement.this);
        d.setContentView(R.layout.image);
        // (That new View is just there to have something inside the dialog that can grow big enough to cover the whole screen.)
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(d.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height=WindowManager.LayoutParams.WRAP_CONTENT;
        /*lp.height = WindowManager.LayoutParams.WRAP_CONTENT;*/
        d.getWindow().setAttributes(lp);
        d.setTitle("Pick Image From Gallery");
        d.setContentView(R.layout.image);
        imageView= (ImageView) d.findViewById(R.id.image2);
        name.setText(s12);
        email_id.setText(s13);
        if (s11 != null) {
            File imgFile = new File(s11);
            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                imageViewRound.setImageBitmap(myBitmap);
                imageView.setImageBitmap(myBitmap);
            }
        }
        navigationView.setNavigationItemSelectedListener(this);


            l1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(FinanceManagement.this, Savings.class);
                    startActivity(intent);
                }
            });
            l2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(FinanceManagement.this, Show_Savings.class);
                    startActivity(intent);
                }
            });
            l3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(FinanceManagement.this, CreateNewGroup.class);
                    startActivity(intent);
                }
            });
            l4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(FinanceManagement.this, GroupsDetails.class);
                    startActivity(intent);
                }
            });
            l5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(FinanceManagement.this, CreateNewGroup.class);
                    startActivity(intent);
                }
            });
            l6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(FinanceManagement.this, AddTransAsSelf.class);
                    startActivity(intent);
                }
            });

        imageViewRound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences=getSharedPreferences(MYPreferences,Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.clear();
                editor.apply();
               imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"),SELECT_PICTURE);
                        d.dismiss();
                    }
                });
                d.show();
            }
        });
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                imageViewRound.setImageURI(selectedImageUri);
                imageView.setImageURI(selectedImageUri);
                selectedImagePath = getPath(selectedImageUri);
                Log.v("IMAGE PATH====>>>> ",selectedImagePath);
                SharedPreferences sharedPreferences = getSharedPreferences(MYPreferences, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String s1 = selectedImagePath.trim();
                editor.putString(Image, s1);
                editor.apply();
            }
        }
    }

    public String getPath(Uri uri)
    {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index =cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s=cursor.getString(column_index);
        return s;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }
*/

   /* @Override
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
*/
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Savings) {
            Intent intent=new Intent(FinanceManagement.this,Show_Savings.class);
            startActivity(intent);

        } else if (id == R.id.Self) {
            Intent intent=new Intent(FinanceManagement.this,Self.class);
            startActivity(intent);
        } else if (id == R.id.Groups) {
            Intent intent=new Intent(FinanceManagement.this,FMGroups.class);
            startActivity(intent);
        } else if (id == R.id.Getgroups) {
            Intent intent=new Intent(FinanceManagement.this,Show_Savings.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, url);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        } else if (id == R.id.nav_send) {


        }else if(id==R.id.logout){
            SharedPreferences sharedPreferences = getSharedPreferences(MYPreferences, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.clear();
                    editor.apply();
                    Intent intent=new Intent(FinanceManagement.this,Loginpage.class);
                    startActivity(intent);


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    }
