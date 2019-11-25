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

public class recycler_view_pemesan_bangun_dari_awal extends RecyclerView.Adapter<recycler_view_pemesan_bangun_dari_awal.ViewHolder> {

    Context context;

    List<GetBangunDariAwalAdapter> getBangunDariAwalAdapter;

    public recycler_view_pemesan_bangun_dari_awal(List<GetBangunDariAwalAdapter> getBangunDariAwalAdapter, Context context){

        super();
        this.getBangunDariAwalAdapter= getBangunDariAwalAdapter;
        this.context = context;
    }

    @Override
    public recycler_view_pemesan_bangun_dari_awal.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_bangun_dari_awal, parent, false);

        recycler_view_pemesan_bangun_dari_awal.ViewHolder viewHolder = new recycler_view_pemesan_bangun_dari_awal.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final recycler_view_pemesan_bangun_dari_awal.ViewHolder Viewholder, int position) {

        GetBangunDariAwalAdapter getBangunDariAwalAdapter1 =  getBangunDariAwalAdapter.get(position);

        Viewholder.Id.setText(getBangunDariAwalAdapter1.getId());
        Viewholder.Nama.setText(getBangunDariAwalAdapter1.getNama());
        Viewholder.Nik.setText(getBangunDariAwalAdapter1.getNik());
        Viewholder.Status.setText(getBangunDariAwalAdapter1.getStatus());
        Viewholder.Jenis_Borongan.setText(getBangunDariAwalAdapter1.getJenis_borongan());
    }

    @Override
    public int getItemCount() {

        return getBangunDariAwalAdapter.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView Id, Nik, Nama, Email, Alamat, Jenis_Borongan, Luas_Tanah, Status;
        public CardView cv_head;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setClickable(true);
            itemView.setOnClickListener(this);

            Id = itemView.findViewById(R.id.text_id_pemesan_bangun_dari_awal_1);
            Nik = itemView.findViewById(R.id.text_nik_pemesan_bangun_dari_awal_1);
            Nama = itemView.findViewById(R.id.text_nama_pemesan_bangun_dari_awal_1);
            Email = itemView.findViewById(R.id.text_email_bangun_awal);
            Alamat = itemView.findViewById(R.id.text_alamat_bangun_awal);
            Jenis_Borongan = itemView.findViewById(R.id.text_jenis_borongan_bangun_awal);
            Luas_Tanah = itemView.findViewById(R.id.text_luas_bangun_awal);
            Status = itemView.findViewById(R.id.text_status_pemesan_bangun_dari_awal_1);
        }


        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, activity_data_pemesan_bangun_dari_awal.class);
            intent.putExtra("id", getBangunDariAwalAdapter.get(getAdapterPosition()).getId());
            intent.putExtra("nik", getBangunDariAwalAdapter.get(getAdapterPosition()).getNik());
            intent.putExtra("nama", getBangunDariAwalAdapter.get(getAdapterPosition()).getNama());
            intent.putExtra("email", getBangunDariAwalAdapter.get(getAdapterPosition()).getEmail());
            intent.putExtra("alamat", getBangunDariAwalAdapter.get(getAdapterPosition()).getAlamat());
            intent.putExtra("jenis_borongan", getBangunDariAwalAdapter.get(getAdapterPosition()).getJenis_borongan());
            intent.putExtra("luas_tanah", getBangunDariAwalAdapter.get(getAdapterPosition()).getLuas_tanah());
            intent.putExtra("desain_rumah", getBangunDariAwalAdapter.get(getAdapterPosition()).getDesain_rumah());
            intent.putExtra("status", getBangunDariAwalAdapter.get(getAdapterPosition()).getStatus());
            context.startActivity(intent);
        }
    }
}
