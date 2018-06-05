package asyncTasks;

import android.os.AsyncTask;

import com.example.julian.apppersocis.MainActivity;

import java.util.List;

import BBDD.TorneigBD;
import adapters.AdapterTornejosOberts;
import clases.Torneig;

public class AsyncTaskFerInscripcio extends AsyncTask<String, String, Boolean> {

    private AdapterTornejosOberts activity;

    public AsyncTaskFerInscripcio(AdapterTornejosOberts mActivity){
        activity = mActivity;
    }

    @Override
    protected Boolean doInBackground(String... strings) {

        Boolean ok = TorneigBD.ferInscripcio(strings[0], strings[1], strings[2]);
        return ok;
    }

    @Override
    protected void onPostExecute(Boolean ok) {
        super.onPostExecute(ok);
        activity.ferInscripcioRequest(ok);
    }
}
