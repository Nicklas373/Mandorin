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

public class recycler_view_status_pembayaran_bangun_dari_awal extends RecyclerView.Adapter<recycler_view_status_pembayaran_bangun_dari_awal .ViewHolder> {

    Context context;

    List<GetDataPembayaranBangunDariAwalAdapter> getDataPembayaranBangunDariAwalAdapter;

    public recycler_view_status_pembayaran_bangun_dari_awal (List<GetDataPembayaranBangunDariAwalAdapter> getDataPembayaranBangunDariAwalAdapter, Context context){

        super();
        this.getDataPembayaranBangunDariAwalAdapter= getDataPembayaranBangunDariAwalAdapter;
        this.context = context;
    }

    @Override
    public recycler_view_status_pembayaran_bangun_dari_awal.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_status_pembayaran_bangun_dari_awal, parent, false);

        recycler_view_status_pembayaran_bangun_dari_awal.ViewHolder viewHolder = new recycler_view_status_pembayaran_bangun_dari_awal.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final recycler_view_status_pembayaran_bangun_dari_awal.ViewHolder Viewholder, int position) {

        GetDataPembayaranBangunDariAwalAdapter getDataPembayaranBangunDariAwalAdapter1 = getDataPembayaranBangunDariAwalAdapter.get(position);

        Viewholder.nomor_kontrak.setText(getDataPembayaranBangunDariAwalAdapter1.getNomor_kontrak());
        Viewholder.total_pembayaran.setText(getDataPembayaranBangunDariAwalAdapter1.getTotal_pembayaran());
        int result = Integer.parseInt(getDataPembayaranBangunDariAwalAdapter1.getTotal_pembayaran());
        NumberFormat formatter = new DecimalFormat("#,###");
        double myNumber = result;
        String formattedNumber = formatter.format(myNumber);
        Viewholder.total_pembayaran.setText("Rp." + formattedNumber);
        Viewholder.alamat.setText(getDataPembayaranBangunDariAwalAdapter1.getAlamat());
    }

    @Override
    public int getItemCount() {

        return getDataPembayaranBangunDariAwalAdapter.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView id, nomor_kontrak, nama_pemesan, email, no_telp, alamat, total_pembayaran, no_rekening, status_satu, status_dua, status_tiga, total_satu, total_dua, total_tiga, tgl_1, tgl_2, tgl_3, bukti_satu, bukti_dua, bukti_tiga;
        public CardView cv_head;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setClickable(true);
            itemView.setOnClickListener(this);

            id = itemView.findViewById(R.id.text_id_pembayaran_bangun_dari_awal);
            nomor_kontrak = itemView.findViewById(R.id.text_nomor_kontrak_status_pembayaran_bangun_dari_awal_1);
            nama_pemesan = itemView.findViewById(R.id.text_nama_pemesan_pembayaran_bangun_dari_awal);
            email = itemView.findViewById(R.id.text_email_pembayaran_bangun_dari_awal);
            no_telp = itemView.findViewById(R.id.text_no_telp_pembayaran_bangun_dari_awal);
            alamat = itemView.findViewById(R.id.text_alamat_status_pembayaran_bangun_dari_awal_1);
            total_pembayaran = itemView.findViewById(R.id.text_biaya_status_pembayaran_bangun_dari_awal_1);
            no_rekening = itemView.findViewById(R.id.text_no_rekening_pembayaran_bangun_dari_awal);
            status_satu = itemView.findViewById(R.id.text_status_satu_pembayaran_bangun_dari_awal);
            status_dua = itemView.findViewById(R.id.text_status_dua_pembayaran_bangun_dari_awal);
            status_tiga = itemView.findViewById(R.id.text_status_tiga_pembayaran_bangun_dari_awal);
            total_satu = itemView.findViewById(R.id.text_total_satu_pembayaran_bangun_dari_awal);
            total_dua = itemView.findViewById(R.id.text_total_dua_pembayaran_bangun_dari_awal);
            total_tiga = itemView.findViewById(R.id.text_total_tiga_pembayaran_bangun_dari_awal);
            tgl_1 = itemView.findViewById(R.id.text_tgl_1_pembayaran_bangun_dari_awal);
            tgl_2 = itemView.findViewById(R.id.text_tgl_2_pembayaran_bangun_dari_awal);
            tgl_3 = itemView.findViewById(R.id.text_tgl_3_pembayaran_bangun_dari_awal);
            bukti_satu = itemView.findViewById(R.id.text_bukti_satu_pembayaran_bangun_dari_awal);
            bukti_dua = itemView.findViewById(R.id.text_bukti_dua_pembayaran_bangun_dari_awal);
            bukti_tiga = itemView.findViewById(R.id.text_bukti_tiga_pembayaran_bangun_dari_awal);
        }


        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, activity_data_pembayaran_bangun_dari_awal.class);
            intent.putExtra("id", getDataPembayaranBangunDariAwalAdapter.get(getAdapterPosition()).getId());
            intent.putExtra("nomor_kontrak", getDataPembayaranBangunDariAwalAdapter.get(getAdapterPosition()).getNomor_kontrak());
            intent.putExtra("nama_pemesan", getDataPembayaranBangunDariAwalAdapter.get(getAdapterPosition()).getNama_pemesan());
            intent.putExtra("email", getDataPembayaranBangunDariAwalAdapter.get(getAdapterPosition()).getEmail());
            intent.putExtra("alamat", getDataPembayaranBangunDariAwalAdapter.get(getAdapterPosition()).getAlamat());
            intent.putExtra("no_telp", getDataPembayaranBangunDariAwalAdapter.get(getAdapterPosition()).getNo_telp());
            intent.putExtra("total_pembayaran", getDataPembayaranBangunDariAwalAdapter.get(getAdapterPosition()).getTotal_pembayaran());
            intent.putExtra("no_rekening", getDataPembayaranBangunDariAwalAdapter.get(getAdapterPosition()).getNo_rekening());
            intent.putExtra("status_satu", getDataPembayaranBangunDariAwalAdapter.get(getAdapterPosition()).getStatus_satu());
            intent.putExtra("status_dua", getDataPembayaranBangunDariAwalAdapter.get(getAdapterPosition()).getStatus_dua());
            intent.putExtra("status_tiga", getDataPembayaranBangunDariAwalAdapter.get(getAdapterPosition()).getStatus_tiga());
            intent.putExtra("total_satu", getDataPembayaranBangunDariAwalAdapter.get(getAdapterPosition()).getTotal_satu());
            intent.putExtra("total_dua", getDataPembayaranBangunDariAwalAdapter.get(getAdapterPosition()).getTotal_dua());
            intent.putExtra("total_tiga", getDataPembayaranBangunDariAwalAdapter.get(getAdapterPosition()).getTotal_tiga());
            intent.putExtra("tgl_input_satu", getDataPembayaranBangunDariAwalAdapter.get(getAdapterPosition()).getTgl_input_satu());
            intent.putExtra("tgl_input_dua", getDataPembayaranBangunDariAwalAdapter.get(getAdapterPosition()).getTgl_input_dua());
            intent.putExtra("tgl_input_tiga", getDataPembayaranBangunDariAwalAdapter.get(getAdapterPosition()).getTgl_input_tiga());
            intent.putExtra("bukti_satu", getDataPembayaranBangunDariAwalAdapter.get(getAdapterPosition()).getBukti_satu());
            intent.putExtra("bukti_dua", getDataPembayaranBangunDariAwalAdapter.get(getAdapterPosition()).getBukti_dua());
            intent.putExtra("bukti_tiga", getDataPembayaranBangunDariAwalAdapter.get(getAdapterPosition()).getBukti_tiga());
            context.startActivity(intent);
        }
    }
}