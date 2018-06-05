package com.example.julian.apppersocis;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import asyncTasks.AsyncTaskLogin;
import clases.Soci;

public class LoginActivity extends AppCompatActivity {

    private EditText txtNif;
    private EditText txtPassword;
    private Button btnLogin;
    private ProgressBar pgbCargando;
    public static SharedPreferences pref = null;
    public static SharedPreferences.Editor editor = null;
    public static final String USER_DATA = "data_user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtNif = findViewById(R.id.txtNIF);
        txtPassword = findViewById(R.id.txtPassword);
        pgbCargando = findViewById(R.id.progressBar);
        btnLogin = findViewById(R.id.button);

        pref = getApplicationContext().getSharedPreferences(USER_DATA, 0);
        editor = pref.edit();

        /*
        // PROVES
        editor.remove("nif");
        editor.remove("password");
        editor.remove("session_id");
        editor.commit();
        */

        String nif = pref.getString("nif", null);
        String password = pref.getString("password", null);
        String sessionId = pref.getString("session_id", null);

        if (nif != null && password != null && sessionId != null) {
            pgbCargando.setVisibility(View.VISIBLE);
            login(nif, password);
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pgbCargando.setVisibility(View.VISIBLE);
                btnLogin.setEnabled(false);

                String NIF = txtNif.getText().toString();
                String Password = null;

                Password = getPasswordHash(txtPassword.getText().toString());

                login(NIF, Password);
            }
        });
    }

    public void login(String nif, String password) {
        AsyncTaskLogin atLogin = new AsyncTaskLogin(this);
        atLogin.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, nif, password);
    }

    public static String getPasswordHash(String pass) {
        String hash = "";

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            md.update(pass.getBytes());
            byte byteData[] = md.digest();
            StringBuffer sb = new StringBuffer();
            for (byte aByteData : byteData) {
                sb.append(Integer.toString((aByteData & 0xff) + 0x100, 16).substring(1));
            }

            hash = sb.toString();
        } catch (NoSuchAlgorithmException ignored) {}

        return hash;
    }

    public void intentoLogin(Soci soci) {
        if (soci != null) {
            editor.putString("nif", soci.getNif());
            editor.putString("password", soci.getPasswordHash());
            editor.commit();

            Intent mainActivityIntent = new Intent(getApplicationContext(), MainActivity.class);
            mainActivityIntent.putExtra(MainActivity.SESSION_ID, pref.getString("session_id", null));
            mainActivityIntent.putExtra(MainActivity.SOCI, soci);
            startActivity(mainActivityIntent);
            pgbCargando.setVisibility(View.INVISIBLE);
        }else{
            Toast.makeText(getBaseContext(), "Login incorrecte", Toast.LENGTH_LONG).show();
            pgbCargando.setVisibility(View.INVISIBLE);
            btnLogin.setEnabled(true);

        }


    }
}
