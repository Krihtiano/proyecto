package com.example.julian.apppersocis;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adapters.AdapterTornejosOberts;
import asyncTasks.AsyncTaskTornejosOberts;
import clases.Soci;
import clases.Torneig;

public class MainActivity extends AppCompatActivity {

    public  static String SESSION_ID = "session_id";
    public  static String SOCI = "soci";
    private TextView txtNomSoci;

    private String sessionId;
    private Soci soci;

    private List<Torneig> llistaTornejos = new ArrayList<>();

    private RecyclerView rcvTornejosOberts;
    private AdapterTornejosOberts adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getIntent().getExtras()!=null) {
            sessionId = (String) getIntent().getExtras().get(SESSION_ID);
            soci = (Soci) getIntent().getExtras().get(SOCI);
        }

        rcvTornejosOberts = findViewById(R.id.rcvTornejosOberts);

        getTornejosObertsInscripcio();
        Toast.makeText(getBaseContext(), "Soy el puto amo", Toast.LENGTH_LONG).show();
    }

    public void getTornejosObertsInscripcio() {
        AsyncTaskTornejosOberts atLogin = new AsyncTaskTornejosOberts(this);
        atLogin.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, sessionId);
    }

    public void setTornejosObertsInscripcio(List<Torneig> llista) {
        if (llista!=null) llistaTornejos = llista;

        if (rcvTornejosOberts != null) {
            if(rcvTornejosOberts.getAdapter()==null) {
                adapter = new AdapterTornejosOberts(sessionId,soci, llistaTornejos);
                rcvTornejosOberts.setLayoutManager(new LinearLayoutManager(this));
                rcvTornejosOberts.setAdapter(adapter);
            }
        }

    }

}
