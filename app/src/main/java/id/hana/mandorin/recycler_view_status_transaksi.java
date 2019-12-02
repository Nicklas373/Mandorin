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

public class recycler_view_status_transaksi extends RecyclerView.Adapter<recycler_view_status_transaksi.ViewHolder> {

    Context context;

    List<GetDataTransaksiAdapter> getDataTransaksiAdapter;

    public recycler_view_status_transaksi (List<GetDataTransaksiAdapter> getDataTransaksiAdapter, Context context){

        super();
        this.getDataTransaksiAdapter = getDataTransaksiAdapter;
        this.context = context;
    }

    @Override
    public recycler_view_status_transaksi.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_status_transaksi, parent, false);

        recycler_view_status_transaksi.ViewHolder viewHolder = new recycler_view_status_transaksi.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final recycler_view_status_transaksi.ViewHolder Viewholder, int position) {

        GetDataTransaksiAdapter getDataPembayaranBangunDariAwalAdapter1 = getDataTransaksiAdapter.get(position);

        Viewholder.nomor_kontrak.setText(getDataPembayaranBangunDariAwalAdapter1.getNomor_kontrak());
        Viewholder.biaya_konstruksi.setText(getDataPembayaranBangunDariAwalAdapter1.getBiaya_konstruksi());
        int result = Integer.parseInt(getDataPembayaranBangunDariAwalAdapter1.getBiaya_konstruksi());
        NumberFormat formatter = new DecimalFormat("#,###");
        double myNumber = result;
        String formattedNumber = formatter.format(myNumber);
        Viewholder.biaya_konstruksi.setText("Rp." + formattedNumber);
        Viewholder.alamat.setText(getDataPembayaranBangunDariAwalAdapter1.getAlamat());
    }

    @Override
    public int getItemCount() {

        return getDataTransaksiAdapter.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView nomor_kontrak, alamat, biaya_konstruksi;
        public CardView cv_head;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setClickable(true);
            itemView.setOnClickListener(this);

            nomor_kontrak = itemView.findViewById(R.id.text_nomor_kontrak_status_transaksi_1);
            alamat = itemView.findViewById(R.id.text_alamat_status_transaksi_1);
            biaya_konstruksi = itemView.findViewById(R.id.text_biaya_status_transaksi_1);
        }


        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, activity_data_transaksi.class);
            intent.putExtra("id", getDataTransaksiAdapter.get(getAdapterPosition()).getId());
            intent.putExtra("nomor_kontrak", getDataTransaksiAdapter.get(getAdapterPosition()).getNomor_kontrak());
            intent.putExtra("nama_pemesan", getDataTransaksiAdapter.get(getAdapterPosition()).getNama_pemesan());
            intent.putExtra("email", getDataTransaksiAdapter.get(getAdapterPosition()).getEmail());
            intent.putExtra("alamat", getDataTransaksiAdapter.get(getAdapterPosition()).getAlamat());
            intent.putExtra("no_telp", getDataTransaksiAdapter.get(getAdapterPosition()).getNo_telp());
            intent.putExtra("biaya_desain", getDataTransaksiAdapter.get(getAdapterPosition()).getBiaya_desain());
            intent.putExtra("biaya_konstruksi", getDataTransaksiAdapter.get(getAdapterPosition()).getBiaya_konstruksi());
            intent.putExtra("no_rekening", getDataTransaksiAdapter.get(getAdapterPosition()).getNo_rekening());
            intent.putExtra("status_desain", getDataTransaksiAdapter.get(getAdapterPosition()).getStatus_desain());
            intent.putExtra("status_satu", getDataTransaksiAdapter.get(getAdapterPosition()).getStatus_satu());
            intent.putExtra("status_dua", getDataTransaksiAdapter.get(getAdapterPosition()).getStatus_dua());
            intent.putExtra("status_tiga", getDataTransaksiAdapter.get(getAdapterPosition()).getStatus_tiga());
            intent.putExtra("status_empat", getDataTransaksiAdapter.get(getAdapterPosition()).getStatus_empat());
            intent.putExtra("total_satu", getDataTransaksiAdapter.get(getAdapterPosition()).getTotal_satu());
            intent.putExtra("total_dua", getDataTransaksiAdapter.get(getAdapterPosition()).getTotal_dua());
            intent.putExtra("total_tiga", getDataTransaksiAdapter.get(getAdapterPosition()).getTotal_tiga());
            intent.putExtra("total_empat", getDataTransaksiAdapter.get(getAdapterPosition()).getTotal_empat());
            intent.putExtra("tgl_input_satu", getDataTransaksiAdapter.get(getAdapterPosition()).getTgl_input_satu());
            intent.putExtra("tgl_input_dua", getDataTransaksiAdapter.get(getAdapterPosition()).getTgl_input_dua());
            intent.putExtra("tgl_input_tiga", getDataTransaksiAdapter.get(getAdapterPosition()).getTgl_input_tiga());
            intent.putExtra("tgl_input_empat", getDataTransaksiAdapter.get(getAdapterPosition()).getTgl_input_empat());
            intent.putExtra("tgl_input_desain", getDataTransaksiAdapter.get(getAdapterPosition()).getTgl_input_desain());
            intent.putExtra("bukti_satu", getDataTransaksiAdapter.get(getAdapterPosition()).getBukti_satu());
            intent.putExtra("bukti_dua", getDataTransaksiAdapter.get(getAdapterPosition()).getBukti_dua());
            intent.putExtra("bukti_tiga", getDataTransaksiAdapter.get(getAdapterPosition()).getBukti_tiga());
            intent.putExtra("bukti_empat", getDataTransaksiAdapter.get(getAdapterPosition()).getBukti_empat());
            intent.putExtra("bukti_desain", getDataTransaksiAdapter.get(getAdapterPosition()).getBukti_desain());
            intent.putExtra("presentase", getDataTransaksiAdapter.get(getAdapterPosition()).getPresentase());
            context.startActivity(intent);
        }
    }
}