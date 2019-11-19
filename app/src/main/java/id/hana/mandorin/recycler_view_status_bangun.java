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

public class recycler_view_status_bangun extends RecyclerView.Adapter<recycler_view_status_bangun.ViewHolder> {

    Context context;

    List<GetStatusBangunAdapter> getStatusBangunAdapter;

    public recycler_view_status_bangun(List<GetStatusBangunAdapter> getStatusBangunAdapter, Context context){

        super();
        this.getStatusBangunAdapter = getStatusBangunAdapter;
        this.context = context;
    }

    @Override
    public recycler_view_status_bangun.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_status_bangun_dari_awal, parent, false);

        recycler_view_status_bangun.ViewHolder viewHolder = new recycler_view_status_bangun.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final recycler_view_status_bangun.ViewHolder Viewholder, int position) {

        GetStatusBangunAdapter getStatusBangunAdapter1 = getStatusBangunAdapter.get(position);

        Viewholder.Id.setText(getStatusBangunAdapter1.getNama_pemesan());
        Viewholder.nomor_kontrak.setText(getStatusBangunAdapter1.getNomor_kontrak());
        int result = Integer.parseInt(getStatusBangunAdapter1.getTotal_biaya());
        NumberFormat formatter = new DecimalFormat("#,###");
        double myNumber = result;
        String formattedNumber = formatter.format(myNumber);
        Viewholder.total_biaya.setText("Rp." + formattedNumber);
        Viewholder.presentase.setText(getStatusBangunAdapter1.getPresentase() + "%");
        Viewholder.alamat_pekerjaan.setText(getStatusBangunAdapter1.getAlamat_pekerjaan());
    }

    @Override
    public int getItemCount() {

        return getStatusBangunAdapter.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView Id, nomor_kontrak, email, no_telp, alamat_pekerjaan, status_pekerjaan, total_biaya, presentase, estimasi_waktu, data_pemesan, desain_rumah, rekap_data, surat_kontrak;
        public CardView cv_head;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setClickable(true);
            itemView.setOnClickListener(this);

            Id = itemView.findViewById(R.id.text_id_status_bangun_dari_awal_1);
            nomor_kontrak = itemView.findViewById(R.id.nomor_kontrak_status_bangun_dari_awal_1);
            email = itemView.findViewById(R.id.text_email_pemesan);
            no_telp = itemView.findViewById(R.id.text_no_hp_pemesan);
            alamat_pekerjaan = itemView.findViewById(R.id.alamat_status_bangun_dari_awal_1);
            status_pekerjaan = itemView.findViewById(R.id.text_jenis_bangun_awal_1);
            total_biaya = itemView.findViewById(R.id.total_biaya_status_bangun_dari_awal_1);
            presentase = itemView.findViewById(R.id.presentasi_status_bangun_dari_awal_1);
            estimasi_waktu = itemView.findViewById(R.id.text_estimasi_pemesan);
            data_pemesan = itemView.findViewById(R.id.text_data_pemesan);
            surat_kontrak = itemView.findViewById(R.id.text_surat_kontrak_pemesan);
            rekap_data = itemView.findViewById(R.id.text_rekap_data_pemesan);
            desain_rumah = itemView.findViewById(R.id.text_desain_rumah_pemesan);
        }


        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, activity_status_bangun.class);
            intent.putExtra("id", getStatusBangunAdapter.get(getAdapterPosition()).getId());
            intent.putExtra("nomor_kontrak", getStatusBangunAdapter.get(getAdapterPosition()).getNomor_kontrak());
            intent.putExtra("nama_pemesan", getStatusBangunAdapter.get(getAdapterPosition()).getNama_pemesan());
            intent.putExtra("email", getStatusBangunAdapter.get(getAdapterPosition()).getEmail());
            intent.putExtra("no_telp", getStatusBangunAdapter.get(getAdapterPosition()).getNo_telp());
            intent.putExtra("alamat_pekerjaan", getStatusBangunAdapter.get(getAdapterPosition()).getAlamat_pekerjaan());
            intent.putExtra("status_pekerjaan", getStatusBangunAdapter.get(getAdapterPosition()).getStatus_pekerjaan());
            intent.putExtra("total_biaya", getStatusBangunAdapter.get(getAdapterPosition()).getTotal_biaya());
            intent.putExtra("presentase", getStatusBangunAdapter.get(getAdapterPosition()).getPresentase());
            intent.putExtra("estimasi_waktu", getStatusBangunAdapter.get(getAdapterPosition()).getEstimasi_waktu());
            intent.putExtra("data_pesanan", getStatusBangunAdapter.get(getAdapterPosition()).getData_pemesan());
            intent.putExtra("rekap_data", getStatusBangunAdapter.get(getAdapterPosition()).getRekap_data());
            intent.putExtra("desain_rumah", getStatusBangunAdapter.get(getAdapterPosition()).getDesain_rumah());
            intent.putExtra("surat_kontrak", getStatusBangunAdapter.get(getAdapterPosition()).getSurat_kontrak());
            context.startActivity(intent);
        }
    }
}