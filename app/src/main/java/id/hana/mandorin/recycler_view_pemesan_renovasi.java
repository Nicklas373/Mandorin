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

public class recycler_view_pemesan_renovasi  extends RecyclerView.Adapter<recycler_view_pemesan_renovasi.ViewHolder> {

    Context context;

    List<GetRenovasiDataAdapter> getRenovasiDataAdapter;

    public recycler_view_pemesan_renovasi(List<GetRenovasiDataAdapter> getRenovasiDataAdapter, Context context){

        super();
        this.getRenovasiDataAdapter = getRenovasiDataAdapter;
        this.context = context;
    }

    @Override
    public recycler_view_pemesan_renovasi.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_pemesan_renovasi, parent, false);

        recycler_view_pemesan_renovasi.ViewHolder viewHolder = new recycler_view_pemesan_renovasi.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final recycler_view_pemesan_renovasi.ViewHolder Viewholder, int position) {

        GetRenovasiDataAdapter getRenovasiDataAdapter1 =  getRenovasiDataAdapter.get(position);

        Viewholder.Id.setText(getRenovasiDataAdapter1.getId());
        Viewholder.Nama.setText(getRenovasiDataAdapter1.getNama());
        Viewholder.Nik.setText(getRenovasiDataAdapter1.getNik());
        Viewholder.Detail_Pesanan.setText(getRenovasiDataAdapter1.getStatus());
        Viewholder.Jenis_Borongan.setText(getRenovasiDataAdapter1.getJenis_borongan());
    }

    @Override
    public int getItemCount() {

        return getRenovasiDataAdapter.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView Id, Nik, Nama, Email, No_HP, Alamat, TGL_SURVEY, Jenis_Borongan, Data_Renovasi, Detail_Pesanan;
        public CardView cv_head;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setClickable(true);
            itemView.setOnClickListener(this);

            Id = itemView.findViewById(R.id.text_id_pemesan_renovasi_1);
            Nik = itemView.findViewById(R.id.text_nik_pemesan_renovasi_1);
            Nama = itemView.findViewById(R.id.text_nama_pemesan_renovasi_1);
            Email = itemView.findViewById(R.id.text_email_pemesan);
            Alamat = itemView.findViewById(R.id.text_alamat_pemesan);
            Jenis_Borongan = itemView.findViewById(R.id.text_jenis_pemesan_renovasi_1);
            Data_Renovasi = itemView.findViewById(R.id.text_data_renovasi_pemesan);
            Detail_Pesanan = itemView.findViewById(R.id.text_status_pemesan_renovasi_1);
        }


        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, activity_data_pemesan_renovasi.class);
            intent.putExtra("id", getRenovasiDataAdapter.get(getAdapterPosition()).getId());
            intent.putExtra("nik", getRenovasiDataAdapter.get(getAdapterPosition()).getNik());
            intent.putExtra("nama", getRenovasiDataAdapter.get(getAdapterPosition()).getNama());
            intent.putExtra("email", getRenovasiDataAdapter.get(getAdapterPosition()).getEmail());
            intent.putExtra("alamat", getRenovasiDataAdapter.get(getAdapterPosition()).getAlamat());
            intent.putExtra("jenis_borongan", getRenovasiDataAdapter.get(getAdapterPosition()).getJenis_borongan());
            intent.putExtra("data_renovasi", getRenovasiDataAdapter.get(getAdapterPosition()).getData_renovasi());
            intent.putExtra("status", getRenovasiDataAdapter.get(getAdapterPosition()).getStatus());
            context.startActivity(intent);
        }
    }
}