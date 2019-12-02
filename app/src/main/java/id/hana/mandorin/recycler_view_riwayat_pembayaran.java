package id.hana.mandorin;

import android.content.Context;
import android.icu.text.DecimalFormat;
import android.icu.text.NumberFormat;
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
        Viewholder.Nama.setText(getRenovasiDataAdapter1.getNama_pemesan());
        int result = Integer.parseInt(getRenovasiDataAdapter1.getBiaya_desain());
        NumberFormat formatter = new DecimalFormat("#,###");
        double myNumber = result;
        String formattedNumber = formatter.format(myNumber);
        Viewholder.Biaya_Desain.setText(formattedNumber);
        int result_2 = Integer.parseInt(getRenovasiDataAdapter1.getBiaya_desain());
        NumberFormat formatter2 = new DecimalFormat("#,###");
        double myNumber2 = result_2;
        String formattedNumber2 = formatter2.format(myNumber2);
        Viewholder.Biaya_Konstruksi.setText(formattedNumber2);
    }

    @Override
    public int getItemCount() {
        return getRiwayatPembayaranAdapter.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView Nik, Alamat, Nama, Biaya_Desain, Biaya_Konstruksi;
        public CardView cv_head;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setClickable(true);
            itemView.setOnClickListener(this);

            Nik = itemView.findViewById(R.id.text_nomor_kontrak_riwayat_pembayaran_1);
            Alamat = itemView.findViewById(R.id.text_alamat_riwayat_pembayaran_1);
            Nama = itemView.findViewById(R.id.text_nama_riwayat_pembayaran_1);
            Biaya_Desain = itemView.findViewById(R.id.text_biaya_desain_pembayaran_1);
            Biaya_Konstruksi = itemView.findViewById(R.id.text_biaya_konstruksi_1);
        }


        @Override
        public void onClick(View v) {
        }
    }
}