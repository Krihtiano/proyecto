package asyncTasks;

import android.os.AsyncTask;
import android.view.View;
import com.example.julian.apppersocis.LoginActivity;

import BBDD.TorneigBD;
import clases.Soci;

public class AsyncTaskLogin extends AsyncTask<String, String, Soci> {

    private LoginActivity activity;

    public AsyncTaskLogin(LoginActivity mActivity){
        activity = mActivity;
    }

    @Override
    protected Soci doInBackground(String... strings) {

        Soci s = TorneigBD.login(strings[0], strings[1].toUpperCase());
        return s;
    }

    @Override
    protected void onPostExecute(Soci soci) {
        super.onPostExecute(soci);
        activity.intentoLogin(soci);
    }
}
