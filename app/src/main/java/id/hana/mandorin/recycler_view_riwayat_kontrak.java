package id.hana.mandorin;

import android.content.Context;
import android.content.Intent;
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
        Viewholder.Status.setText(getRenovasiDataAdapter1.getStatus());
    }

    @Override
    public int getItemCount() {
        return getRiwayatKontrakAdapter.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView Nik, Alamat, Status;
        public CardView cv_head;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setClickable(true);
            itemView.setOnClickListener(this);

            Nik = itemView.findViewById(R.id.text_id_riwayat_kontrak_1);
            Alamat = itemView.findViewById(R.id.alamat_riwayat_kontrak_1);
            Status = itemView.findViewById(R.id.status_riwayat_kontrak_1);
        }


        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, activity_data_riwayat_kontrak.class);
            intent.putExtra("id", getRiwayatKontrakAdapter.get(getAdapterPosition()).getId());
            intent.putExtra("nomor_kontrak", getRiwayatKontrakAdapter.get(getAdapterPosition()).getNomor_kontrak());
            intent.putExtra("nama_pemesan", getRiwayatKontrakAdapter.get(getAdapterPosition()).getNama_pemesan());
            intent.putExtra("email", getRiwayatKontrakAdapter.get(getAdapterPosition()).getEmail());
            intent.putExtra("alamat", getRiwayatKontrakAdapter.get(getAdapterPosition()).getAlamat());
            intent.putExtra("status", getRiwayatKontrakAdapter.get(getAdapterPosition()).getStatus());
            intent.putExtra("data_pemesan", getRiwayatKontrakAdapter.get(getAdapterPosition()).getData_pemesan());
            intent.putExtra("rekap_data", getRiwayatKontrakAdapter.get(getAdapterPosition()).getRekap_data());
            intent.putExtra("surat_kontrak", getRiwayatKontrakAdapter.get(getAdapterPosition()).getSurat_kontrak());
            intent.putExtra("desain_rumah", getRiwayatKontrakAdapter.get(getAdapterPosition()).getDesain_rumah());
            context.startActivity(intent);
        }
    }
}