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

public class recycler_view_status_pembayaran_renovasi extends RecyclerView.Adapter<recycler_view_status_pembayaran_renovasi.ViewHolder> {

    Context context;

    List<GetDataPembayaranRenovasiAdapter> getDataPembayaranRenovasiAdapter;

    public recycler_view_status_pembayaran_renovasi(List<GetDataPembayaranRenovasiAdapter> getDataPembayaranRenovasiAdapter, Context context){

        super();
        this.getDataPembayaranRenovasiAdapter = getDataPembayaranRenovasiAdapter;
        this.context = context;
    }

    @Override
    public recycler_view_status_pembayaran_renovasi.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_status_pembayaran_renovasi, parent, false);

        recycler_view_status_pembayaran_renovasi.ViewHolder viewHolder = new recycler_view_status_pembayaran_renovasi.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final recycler_view_status_pembayaran_renovasi.ViewHolder Viewholder, int position) {

        GetDataPembayaranRenovasiAdapter getDataPembayaranRenovasiAdapter1 = getDataPembayaranRenovasiAdapter.get(position);

        Viewholder.nomor_kontrak.setText(getDataPembayaranRenovasiAdapter1.getNomor_kontrak());
        Viewholder.total_pembayaran.setText(getDataPembayaranRenovasiAdapter1.getTotal_pembayaran());
        int result = Integer.parseInt(getDataPembayaranRenovasiAdapter1.getTotal_pembayaran());
        NumberFormat formatter = new DecimalFormat("#,###");
        double myNumber = result;
        String formattedNumber = formatter.format(myNumber);
        Viewholder.total_pembayaran.setText("Rp." + formattedNumber);
        Viewholder.alamat.setText(getDataPembayaranRenovasiAdapter1.getAlamat());
    }

    @Override
    public int getItemCount() {

        return getDataPembayaranRenovasiAdapter.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView id, nomor_kontrak, nama_pemesan, email, no_telp, alamat, total_pembayaran, no_rekening, status_satu, status_dua, status_tiga, total_satu, total_dua, total_tiga, bukti_satu, bukti_dua, bukti_tiga;
        public CardView cv_head;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setClickable(true);
            itemView.setOnClickListener(this);

            id = itemView.findViewById(R.id.text_id_pembayaran_renovasi);
            nomor_kontrak = itemView.findViewById(R.id.text_nomor_kontrak_status_pembayaran_renovasi_1);
            nama_pemesan = itemView.findViewById(R.id.text_nama_pemesan_pembayaran_renovasi);
            email = itemView.findViewById(R.id.text_email_pembayaran_renovasi);
            no_telp = itemView.findViewById(R.id.text_no_telp_pembayaran_renovasi);
            alamat = itemView.findViewById(R.id.text_alamat_status_pembayaran_renovasi_1);
            total_pembayaran = itemView.findViewById(R.id.text_biaya_status_pembayaran_renovasi_1);
            no_rekening = itemView.findViewById(R.id.text_no_rekening_pembayaran_renovasi);
            status_satu = itemView.findViewById(R.id.text_status_satu_pembayaran_renovasi);
            status_dua = itemView.findViewById(R.id.text_status_dua_pembayaran_renovasi);
            status_tiga = itemView.findViewById(R.id.text_status_tiga_pembayaran_renovasi);
            total_satu = itemView.findViewById(R.id.text_total_satu_pembayaran_renovasi);
            total_dua = itemView.findViewById(R.id.text_total_dua_pembayaran_renovasi);
            total_tiga = itemView.findViewById(R.id.text_total_tiga_pembayaran_renovasi);
            bukti_satu = itemView.findViewById(R.id.text_bukti_satu_pembayaran_renovasi);
            bukti_dua = itemView.findViewById(R.id.text_bukti_dua_pembayaran_renovasi);
            bukti_tiga = itemView.findViewById(R.id.text_bukti_tiga_pembayaran_renovasi);
        }


        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, activity_data_pembayaran_renovasi.class);
            intent.putExtra("id", getDataPembayaranRenovasiAdapter.get(getAdapterPosition()).getId());
            intent.putExtra("nomor_kontrak", getDataPembayaranRenovasiAdapter.get(getAdapterPosition()).getNomor_kontrak());
            intent.putExtra("nama_pemesan", getDataPembayaranRenovasiAdapter.get(getAdapterPosition()).getNama_pemesan());
            intent.putExtra("email", getDataPembayaranRenovasiAdapter.get(getAdapterPosition()).getEmail());
            intent.putExtra("no_telp", getDataPembayaranRenovasiAdapter.get(getAdapterPosition()).getNo_telp());
            intent.putExtra("total_pembayaran", getDataPembayaranRenovasiAdapter.get(getAdapterPosition()).getTotal_pembayaran());
            intent.putExtra("no_rekening", getDataPembayaranRenovasiAdapter.get(getAdapterPosition()).getNo_rekening());
            intent.putExtra("status_satu", getDataPembayaranRenovasiAdapter.get(getAdapterPosition()).getStatus_satu());
            intent.putExtra("status_dua", getDataPembayaranRenovasiAdapter.get(getAdapterPosition()).getStatus_dua());
            intent.putExtra("status_tiga", getDataPembayaranRenovasiAdapter.get(getAdapterPosition()).getStatus_tiga());
            intent.putExtra("total_satu", getDataPembayaranRenovasiAdapter.get(getAdapterPosition()).getTotal_satu());
            intent.putExtra("total_dua", getDataPembayaranRenovasiAdapter.get(getAdapterPosition()).getTotal_dua());
            intent.putExtra("total_tiga", getDataPembayaranRenovasiAdapter.get(getAdapterPosition()).getTotal_tiga());
            intent.putExtra("bukti_satu", getDataPembayaranRenovasiAdapter.get(getAdapterPosition()).getBukti_satu());
            intent.putExtra("bukti_dua", getDataPembayaranRenovasiAdapter.get(getAdapterPosition()).getBukti_dua());
            intent.putExtra("bukti_tiga", getDataPembayaranRenovasiAdapter.get(getAdapterPosition()).getBukti_tiga());
            context.startActivity(intent);
        }
    }
}