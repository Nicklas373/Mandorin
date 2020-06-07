package id.hana.mandorin;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class recycler_view_riwayat_kontrak extends RecyclerView.Adapter<recycler_view_riwayat_kontrak .ViewHolder> {

    Context context;

    List<GetRiwayatKontrakAdapter> getRiwayatKontrakAdapter;

    public recycler_view_riwayat_kontrak (List<GetRiwayatKontrakAdapter> getRiwayatKontrakAdapter, Context context){

        super();
        this.getRiwayatKontrakAdapter = getRiwayatKontrakAdapter;
        this.context = context;
    }

    @Override
    public recycler_view_riwayat_kontrak .ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_riwayat_status_kontrak, parent, false);

        recycler_view_riwayat_kontrak .ViewHolder viewHolder = new recycler_view_riwayat_kontrak .ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final recycler_view_riwayat_kontrak .ViewHolder Viewholder, int position) {

        GetRiwayatKontrakAdapter getRenovasiDataAdapter1 =  getRiwayatKontrakAdapter.get(position);

        Viewholder.Nik.setText(getRenovasiDataAdapter1.getNomor_kontrak());
        Viewholder.Alamat.setText(getRenovasiDataAdapter1.getAlamat());
        Viewholder.Nama.setText(getRenovasiDataAdapter1.getNama_pemesan());
        Viewholder.Estimasi.setText((getRenovasiDataAdapter1.getWaktu_mulai()) + " sd " + (getRenovasiDataAdapter1.getWaktu_akhir()));
        Viewholder.Nama_Mandor.setText(getRenovasiDataAdapter1.getNama_mandor());
    }

    @Override
    public int getItemCount() {
        return getRiwayatKontrakAdapter.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView Nik, Alamat, Nama, Estimasi, Nama_Mandor;
        public CardView cv_head;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setClickable(true);
            itemView.setOnClickListener(this);

            Nik = itemView.findViewById(R.id.text_id_riwayat_kontrak_1);
            Alamat = itemView.findViewById(R.id.alamat_riwayat_kontrak_1);
            Nama = itemView.findViewById(R.id.nama_riwayat_kontrak_1);
            Estimasi = itemView.findViewById(R.id.estimasi_riwayat_kontrak_1);
            Nama_Mandor = itemView.findViewById(R.id.mandor_riwayat_kontrak_1);
        }


        @Override
        public void onClick(View v) {

        }
    }
}