package id.hana.mandorin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class recycler_view_data_kontrak extends RecyclerView.Adapter<recycler_view_data_kontrak.ViewHolder> {

    Context context;

    List<GetDataKontrakAdapter> getDataKontrakAdapter;

    public recycler_view_data_kontrak(List<GetDataKontrakAdapter> getDataKontrakAdapter, Context context){

        super();
        this.getDataKontrakAdapter = getDataKontrakAdapter;
        this.context = context;
    }

    @Override
    public recycler_view_data_kontrak.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_data_kontrak, parent, false);

        recycler_view_data_kontrak.ViewHolder viewHolder = new recycler_view_data_kontrak.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final recycler_view_data_kontrak.ViewHolder Viewholder, int position) {

        GetDataKontrakAdapter getStatusRenovasiAdapter1 = getDataKontrakAdapter.get(position);

        Viewholder.nama_pemesan.setText(getStatusRenovasiAdapter1.getNama_pemesan());
        Viewholder.nomor_kontrak.setText(getStatusRenovasiAdapter1.getNomor_kontrak());
        Viewholder.nomor_kontrak.setTypeface(Viewholder.nomor_kontrak.getTypeface(), Typeface.BOLD);
        Viewholder.presentase.setText(getStatusRenovasiAdapter1.getPresentase() + "%");
        Viewholder.alamat_pekerjaan.setText(getStatusRenovasiAdapter1.getAlamat_pekerjaan());
    }

    @Override
    public int getItemCount() {

        return getDataKontrakAdapter.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView nomor_kontrak, nama_pemesan, alamat_pekerjaan, presentase;
        public CardView cv_head;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setClickable(true);
            itemView.setOnClickListener(this);

            nama_pemesan = itemView.findViewById(R.id.text_id_status_kontrak_1);
            nomor_kontrak = itemView.findViewById(R.id.nomor_kontrak_status_kontrak_1);
            alamat_pekerjaan = itemView.findViewById(R.id.alamat_status_kontrak_1);
            presentase = itemView.findViewById(R.id.presentasi_status_kontrak_1);
        }


        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, activity_layanan_kontrak.class);
            intent.putExtra("id", getDataKontrakAdapter.get(getAdapterPosition()).getId());
            intent.putExtra("nomor_kontrak", getDataKontrakAdapter.get(getAdapterPosition()).getNomor_kontrak());
            intent.putExtra("nama_pemesan", getDataKontrakAdapter.get(getAdapterPosition()).getNama_pemesan());
            intent.putExtra("email", getDataKontrakAdapter.get(getAdapterPosition()).getEmail());
            intent.putExtra("no_telp", getDataKontrakAdapter.get(getAdapterPosition()).getNo_telp());
            intent.putExtra("alamat_pekerjaan", getDataKontrakAdapter.get(getAdapterPosition()).getAlamat_pekerjaan());
            intent.putExtra("status_pekerjaan", getDataKontrakAdapter.get(getAdapterPosition()).getStatus_pekerjaan());
            intent.putExtra("presentase", getDataKontrakAdapter.get(getAdapterPosition()).getPresentase());
            intent.putExtra("waktu_mulai", getDataKontrakAdapter.get(getAdapterPosition()).getWaktu_mulai());
            intent.putExtra("waktu_akhir", getDataKontrakAdapter.get(getAdapterPosition()).getWaktu_akhir());
            intent.putExtra("data_desain", getDataKontrakAdapter.get(getAdapterPosition()).getData_desain());
            intent.putExtra("data_rekap", getDataKontrakAdapter.get(getAdapterPosition()).getData_rekap());
            intent.putExtra("surat_kontrak", getDataKontrakAdapter.get(getAdapterPosition()).getSurat_kontrak());
            intent.putExtra("role_kontrak", getDataKontrakAdapter.get(getAdapterPosition()).getRole_kontrak());
            intent.putExtra("role_komplain", getDataKontrakAdapter.get(getAdapterPosition()).getRole_komplain());
            intent.putExtra("rekap_admin", getDataKontrakAdapter.get(getAdapterPosition()).getRekap_admin());
            intent.putExtra("nama_mandor", getDataKontrakAdapter.get(getAdapterPosition()).getNama_mandor());
            context.startActivity(intent);
        }
    }
}