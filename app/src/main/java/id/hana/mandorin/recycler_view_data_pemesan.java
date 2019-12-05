package id.hana.mandorin;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class recycler_view_data_pemesan extends RecyclerView.Adapter<recycler_view_data_pemesan.ViewHolder> {

    Context context;

    List<GetDataPemesanAdapter> getDataPemesanAdapter;

    public recycler_view_data_pemesan(List<GetDataPemesanAdapter> getDataPemesanAdapter, Context context){

        super();
        this.getDataPemesanAdapter= getDataPemesanAdapter;
        this.context = context;
    }

    @Override
    public recycler_view_data_pemesan.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_data_pemesan, parent, false);

        recycler_view_data_pemesan.ViewHolder viewHolder = new recycler_view_data_pemesan.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final recycler_view_data_pemesan.ViewHolder Viewholder, int position) {

        GetDataPemesanAdapter getRenovasiDataAdapter1 =  getDataPemesanAdapter.get(position);

        Viewholder.Nik.setText(getRenovasiDataAdapter1.getNik());
        Viewholder.Alamat.setText(getRenovasiDataAdapter1.getAlamat());
        Viewholder.Nama.setText(getRenovasiDataAdapter1.getNama());
        Viewholder.Alamat.setText(getRenovasiDataAdapter1.getAlamat());
        Viewholder.Status.setText(getRenovasiDataAdapter1.getStatus());
        Viewholder.Tgl_survey.setText(getRenovasiDataAdapter1.getTgl_survey());
    }

    @Override
    public int getItemCount() {
        return getDataPemesanAdapter.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView Nik, Alamat, Nama, Status, Tgl_survey;
        public CardView cv_head;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setClickable(true);
            itemView.setOnClickListener(this);

            Nik = itemView.findViewById(R.id.text_id_data_pemesan_1);
            Alamat = itemView.findViewById(R.id.alamat_pemesan_1);
            Nama = itemView.findViewById(R.id.nomor_pemesan_data_pemesan_1);
            Status = itemView.findViewById(R.id.status_pemesan_1);
            Tgl_survey = itemView.findViewById(R.id.tgl_survey_pemesan_1);
        }


        @Override
        public void onClick(View v) {

        }
    }
}