package asyncTasks;

import android.os.AsyncTask;

import com.example.julian.apppersocis.LoginActivity;
import com.example.julian.apppersocis.MainActivity;

import java.util.List;

import BBDD.TorneigBD;
import clases.Soci;
import clases.Torneig;

public class AsyncTaskTornejosOberts extends AsyncTask<String, String, List<Torneig>> {

    private MainActivity activity;

    public AsyncTaskTornejosOberts(MainActivity mActivity){
        activity = mActivity;
    }

    @Override
    protected List<Torneig> doInBackground(String... strings) {

        List<Torneig> llt = TorneigBD.selectTornejosOberts(strings[0]);
        return llt;
    }

    @Override
    protected void onPostExecute(List<Torneig> tornejos) {
        super.onPostExecute(tornejos);
        activity.setTornejosObertsInscripcio(tornejos);
    }
}
