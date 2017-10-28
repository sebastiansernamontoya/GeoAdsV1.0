package com.example.admin.geoads;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.ProfilePictureView;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.json.JSONException;
import org.json.JSONObject;

import android.support.v4.app.FragmentPagerAdapter;


public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private String correoR, contrasenaR,type="0";
    JSONObject response;
    int control=0;
    private GoogleApiClient googleApiClient;
    private SignInButton signInButton;
    TextView tnom;
    static final int NUMBER_OF_TABS =3;
    ViewPager mPager;
   // private PagerAdapter adapter;
    FragmentManager fm;
    FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        tnom= (TextView) findViewById(R.id.tNombre);

          Intent intent = getIntent();
           String jsondata = intent.getStringExtra("userProfile");
          try {
               response = new JSONObject(jsondata);
               type=response.get("type").toString();
              if(type.equals("registro_local")){
                  control=0;


              }

              if(type.equals("facebook")){
                  control=1;
                  if(AccessToken.getCurrentAccessToken()==null){
                            goLoginActivity();
                       }

           }
              if(type.equals("google")){
                  control=2;

                  GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                          .requestEmail()
                          .build();
                  googleApiClient = new GoogleApiClient.Builder(this)
                          .enableAutoManage(this, this)
                          .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                          .build();
              }
         } catch(Exception e){
               e.printStackTrace();
           }
      //  fm = getSupportFragmentManager();
     //   ft = fm.beginTransaction();

     //   NewsFragment fragment = new NewsFragment();
     //   ft.add(R.id.frame, fragment).commit();
      // }
       // Bundle extras = getIntent().getExtras();
       // correoR = extras.getString("correo");
       // contrasenaR = extras.getString("contrasena");

     //   fm = getSupportFragmentManager();
       // ft = fm.beginTransaction();

        //NewsFragment fragment = new NewsFragment();
        //ft.add(R.id.frame, fragment).commit();

      //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      //  setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
       // PagerAdapter adapter= new PagerAdapter(getSupportFragmentManager());
       // mPager= (ViewPager) findViewById(R.id.pager);
      //  mPager.setAdapter(adapter);

       //  TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
         // tabLayout.setupWithViewPager(mPager);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
        public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;

        switch(id){
            case R.id.mPerfil:
                intent =  new Intent(MainActivity.this,
                        PerfilActivity.class);
                intent.putExtra("userProfile", response.toString());
              //  intent.putExtra("contrasena",contrasenaR);
                startActivity(intent);
                finish();
                break;
            case R.id.mCerrar:
                SharedPreferences settings = getSharedPreferences("perfil",MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.clear();
                editor.commit();
                if(control==0){
                intent =  new Intent(MainActivity.this,
                        LoginActivity.class);
                intent.putExtra("correo",correoR);
                intent.putExtra("contrasena",contrasenaR);
                setResult(RESULT_OK, intent);
                finish();
                     }
               if(control==1){
                LoginManager.getInstance().logOut();
                goLoginActivity();}
                if(control==2){
                    Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(@NonNull Status status) {
                            if(status.isSuccess()){
                                Intent intent = new Intent(MainActivity.this,
                                        LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        }
                    });
                }

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void  goLoginActivity(){
        Intent intent=new Intent(MainActivity.this,LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
