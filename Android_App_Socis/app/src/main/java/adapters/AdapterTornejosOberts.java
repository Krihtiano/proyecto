package adapters;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.julian.apppersocis.R;

import java.text.SimpleDateFormat;
import java.util.List;

import asyncTasks.AsyncTaskFerInscripcio;
import clases.Soci;
import clases.Torneig;

public class AdapterTornejosOberts  extends RecyclerView.Adapter<AdapterTornejosOberts.Holder> {

    private Torneig mTorneigSel = null;
    private String mSessionId;
    private Soci soci;
    private List<Torneig> mLlistaTorneig; //Obligatorio
    SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
    private View v = null;

    public AdapterTornejosOberts(String pSessionId, Soci s, List<Torneig> pLlistaTorneig) {
        mSessionId = pSessionId;
        mLlistaTorneig = pLlistaTorneig;
        soci = s;
    }

    @Override
    public AdapterTornejosOberts.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fila_torneig_obert, parent, false);
        return new AdapterTornejosOberts.Holder(v);
    }

    @Override
    public void onBindViewHolder(AdapterTornejosOberts.Holder holder, int position) {
        Torneig t = mLlistaTorneig.get(position);

        holder.mTxvNom.setText(t.getNom());
        holder.mTxvModalitat.setText(t.getModalitat().getDescription());
        //holder.mTxvData.setText(sdf.format(t.getDataInici()) + " / " + sdf.format(t.getDataFi()));

    }

    @Override
    public int getItemCount() {
        return (mLlistaTorneig!=null) ? mLlistaTorneig.size() : 0;
    }

    public void ferInscripcioRequest(Boolean bInscripcioOK) {
        if (bInscripcioOK && mTorneigSel != null) {
            mLlistaTorneig.remove(mTorneigSel);
            this.notifyDataSetChanged();
        }
    }

    public class Holder extends RecyclerView.ViewHolder {
        private TextView mTxvNom;
        private TextView mTxvModalitat;
        private TextView mTxvData;
        private Button mBtInscriu;

        public Holder(View itemView) {
            super(itemView);
            mTxvNom = itemView.findViewById(R.id.txvNom);
            mTxvModalitat = itemView.findViewById(R.id.txvModalitat);
            mTxvData = itemView.findViewById(R.id.txvData);
            mBtInscriu = itemView.findViewById(R.id.btInscriu);

            mBtInscriu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mTorneigSel = mLlistaTorneig.get(getLayoutPosition());
                    v = view;

                    AsyncTaskFerInscripcio connect = new AsyncTaskFerInscripcio(AdapterTornejosOberts.this);
                    connect.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, AdapterTornejosOberts.this.mSessionId, AdapterTornejosOberts.this.soci.getId() + "", mTorneigSel.getId()+"");
                }
            });
        }
    }
    
}
