package id.hana.mandorin;

import android.content.Context;
import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.icu.text.NumberFormat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class recycler_view_status_renovasi  extends RecyclerView.Adapter<recycler_view_status_renovasi.ViewHolder> {

    Context context;

    List<GetStatusRenovasiAdapter> getStatusRenovasiAdapter;

    public recycler_view_status_renovasi(List<GetStatusRenovasiAdapter> getStatusRenovasiAdapter, Context context){

        super();
        this.getStatusRenovasiAdapter = getStatusRenovasiAdapter;
        this.context = context;
    }

    @Override
    public recycler_view_status_renovasi.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_status_renovasi, parent, false);

        recycler_view_status_renovasi.ViewHolder viewHolder = new recycler_view_status_renovasi.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final recycler_view_status_renovasi.ViewHolder Viewholder, int position) {

        GetStatusRenovasiAdapter getStatusRenovasiAdapter1 = getStatusRenovasiAdapter.get(position);

        Viewholder.Id.setText(getStatusRenovasiAdapter1.getNama_pemesan());
        Viewholder.nomor_kontrak.setText(getStatusRenovasiAdapter1.getNomor_kontrak());
        int result = Integer.parseInt(getStatusRenovasiAdapter1.getTotal_biaya());
        NumberFormat formatter = new DecimalFormat("#,###");
        double myNumber = result;
        String formattedNumber = formatter.format(myNumber);
        Viewholder.total_biaya.setText("Rp." + formattedNumber);
        Viewholder.presentase.setText(getStatusRenovasiAdapter1.getPresentase() + "%");
        Viewholder.alamat_pekerjaan.setText(getStatusRenovasiAdapter1.getAlamat_pekerjaan());
    }

    @Override
    public int getItemCount() {

        return getStatusRenovasiAdapter.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView Id, nomor_kontrak, nama_pemesan, email, no_telp, alamat_pekerjaan, status_pekerjaan, total_biaya, presentase, estimasi_waktu, data_pemesan, rekap_data, surat_kontrak;
        public CardView cv_head;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setClickable(true);
            itemView.setOnClickListener(this);

            Id = itemView.findViewById(R.id.text_id_status_renovasi_1);
            nomor_kontrak = itemView.findViewById(R.id.nomor_kontrak_status_renovasi_1);
            nama_pemesan = itemView.findViewById(R.id.text_nama_pemesan_renovasi_1);
            email = itemView.findViewById(R.id.text_email_pemesan);
            no_telp = itemView.findViewById(R.id.text_no_hp_pemesan);
            alamat_pekerjaan = itemView.findViewById(R.id.alamat_status_renovasi_1);
            status_pekerjaan = itemView.findViewById(R.id.text_jenis_pemesan_renovasi_1);
            total_biaya = itemView.findViewById(R.id.total_biaya_status_renovasi_1);
            presentase = itemView.findViewById(R.id.presentasi_status_renovasi_1);
            estimasi_waktu = itemView.findViewById(R.id.text_estimasi_pemesan);
            data_pemesan = itemView.findViewById(R.id.text_data_pemesan);
            surat_kontrak = itemView.findViewById(R.id.text_surat_kontrak_pemesan);
            rekap_data = itemView.findViewById(R.id.text_rekap_data_pemesan);
        }


        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, activity_status_renovasi.class);
            intent.putExtra("id", getStatusRenovasiAdapter.get(getAdapterPosition()).getId());
            intent.putExtra("nomor_kontrak", getStatusRenovasiAdapter.get(getAdapterPosition()).getNomor_kontrak());
            intent.putExtra("nama_pemesan", getStatusRenovasiAdapter.get(getAdapterPosition()).getNama_pemesan());
            intent.putExtra("email", getStatusRenovasiAdapter.get(getAdapterPosition()).getEmail());
            intent.putExtra("no_telp", getStatusRenovasiAdapter.get(getAdapterPosition()).getNo_telp());
            intent.putExtra("alamat_pekerjaan", getStatusRenovasiAdapter.get(getAdapterPosition()).getAlamat_pekerjaan());
            intent.putExtra("status_pekerjaan", getStatusRenovasiAdapter.get(getAdapterPosition()).getStatus_pekerjaan());
            intent.putExtra("total_biaya", getStatusRenovasiAdapter.get(getAdapterPosition()).getTotal_biaya());
            intent.putExtra("presentase", getStatusRenovasiAdapter.get(getAdapterPosition()).getPresentase());
            intent.putExtra("waktu_mulai", getStatusRenovasiAdapter.get(getAdapterPosition()).getWaktu_mulai());
            intent.putExtra("waktu_akhir", getStatusRenovasiAdapter.get(getAdapterPosition()).getWaktu_akhir());
            intent.putExtra("data_pesanan", getStatusRenovasiAdapter.get(getAdapterPosition()).getData_pemesan());
            intent.putExtra("rekap_data", getStatusRenovasiAdapter.get(getAdapterPosition()).getRekap_data());
            intent.putExtra("surat_kontrak", getStatusRenovasiAdapter.get(getAdapterPosition()).getSurat_kontrak());
            context.startActivity(intent);
        }
    }
}