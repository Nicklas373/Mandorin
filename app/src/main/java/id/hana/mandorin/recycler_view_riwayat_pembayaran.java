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

public class recycler_view_riwayat_pembayaran extends RecyclerView.Adapter<recycler_view_riwayat_pembayaran.ViewHolder> {

    Context context;

    List<GetRiwayatPembayaranAdapter> getRiwayatPembayaranAdapter;

    public recycler_view_riwayat_pembayaran(List<GetRiwayatPembayaranAdapter> getRiwayatPembayaranAdapter, Context context){

        super();
        this.getRiwayatPembayaranAdapter = getRiwayatPembayaranAdapter;
        this.context = context;
    }

    @Override
    public recycler_view_riwayat_pembayaran.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_riwayat_pembayaran, parent, false);

        recycler_view_riwayat_pembayaran.ViewHolder viewHolder = new recycler_view_riwayat_pembayaran.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final recycler_view_riwayat_pembayaran.ViewHolder Viewholder, int position) {

        GetRiwayatPembayaranAdapter getRenovasiDataAdapter1 =  getRiwayatPembayaranAdapter.get(position);

        Viewholder.Nik.setText(getRenovasiDataAdapter1.getNomor_kontrak());
        Viewholder.Alamat.setText(getRenovasiDataAdapter1.getAlamat());
        Viewholder.Status.setText(getRenovasiDataAdapter1.getStatus());
    }

    @Override
    public int getItemCount() {
        return getRiwayatPembayaranAdapter.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView Nik, Alamat, Status;
        public CardView cv_head;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setClickable(true);
            itemView.setOnClickListener(this);

            Nik = itemView.findViewById(R.id.text_nomor_kontrak_riwayat_pembayaran_1);
            Alamat = itemView.findViewById(R.id.text_alamat_riwayat_pembayaran_1);
            Status = itemView.findViewById(R.id.text_status_riwayat_pembayaran_1);
        }


        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, activity_data_riwayat_pembayaran.class);
            intent.putExtra("id", getRiwayatPembayaranAdapter.get(getAdapterPosition()).getId());
            intent.putExtra("nomor_kontrak", getRiwayatPembayaranAdapter.get(getAdapterPosition()).getNomor_kontrak());
            intent.putExtra("nama_pemesan", getRiwayatPembayaranAdapter.get(getAdapterPosition()).getNama_pemesan());
            intent.putExtra("email", getRiwayatPembayaranAdapter.get(getAdapterPosition()).getEmail());
            intent.putExtra("alamat", getRiwayatPembayaranAdapter.get(getAdapterPosition()).getAlamat());
            intent.putExtra("status", getRiwayatPembayaranAdapter.get(getAdapterPosition()).getStatus());
            intent.putExtra("data", getRiwayatPembayaranAdapter.get(getAdapterPosition()).getData());
            context.startActivity(intent);
        }
    }
}