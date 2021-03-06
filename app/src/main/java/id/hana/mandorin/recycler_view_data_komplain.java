package id.hana.mandorin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class recycler_view_data_komplain extends RecyclerView.Adapter<recycler_view_data_komplain.ViewHolder> {

    Context context;

    List<GetDataKomplainAdapter> getDataKomplainAdapter;

    public recycler_view_data_komplain(List<GetDataKomplainAdapter> getDataKomplainAdapter, Context context){

        super();
        this.getDataKomplainAdapter= getDataKomplainAdapter;
        this.context = context;
    }

    @Override
    public recycler_view_data_komplain.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_data_komplain, parent, false);

        recycler_view_data_komplain.ViewHolder viewHolder = new recycler_view_data_komplain.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final recycler_view_data_komplain.ViewHolder Viewholder, int position) {

        GetDataKomplainAdapter getDataKomplainAdapter1 = getDataKomplainAdapter.get(position);

        Viewholder.nomor_kontrak.setText(getDataKomplainAdapter1.getNomor_kontrak());
        Viewholder.status_kontrak.setText(getDataKomplainAdapter1.getStatus_komplain());
        String status = Viewholder.status_kontrak.getText().toString();
        if (status.equalsIgnoreCase("Konfirmasi")){
            Viewholder.status_kontrak.setTextColor((ContextCompat.getColor(context, R.color.colorPrimaryDark)));
            Viewholder.status_kontrak.setTypeface(Viewholder.status_kontrak.getTypeface(), Typeface.BOLD);
        } else if (status.equalsIgnoreCase("Pending"))
        {
            Viewholder.status_kontrak.setTextColor((ContextCompat.getColor(context, R.color.color_unconfirmed)));
            Viewholder.status_kontrak.setTypeface(Viewholder.status_kontrak.getTypeface(), Typeface.BOLD);
        } else if (status.equalsIgnoreCase("pending")){
            Viewholder.status_kontrak.setTextColor((ContextCompat.getColor(context, R.color.color_unconfirmed)));
            Viewholder.status_kontrak.setTypeface(Viewholder.status_kontrak.getTypeface(), Typeface.BOLD);
        }
        Viewholder.alamat_komplain.setText(getDataKomplainAdapter1.getAlamat());
    }

    @Override
    public int getItemCount() {

        return getDataKomplainAdapter.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView nomor_kontrak, status_kontrak, alamat_komplain;
        public CardView cv_head;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setClickable(true);
            itemView.setOnClickListener(this);

            nomor_kontrak = itemView.findViewById(R.id.text_id_kontrak_komplain_1);
            status_kontrak = itemView.findViewById(R.id.text_status_komplain_1);
            alamat_komplain = itemView.findViewById(R.id.text_alamat_komplain_1);
        }


        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, activity_data_komplain_user.class);
            intent.putExtra("id", getDataKomplainAdapter.get(getAdapterPosition()).getId());
            intent.putExtra("nomor_kontrak", getDataKomplainAdapter.get(getAdapterPosition()).getNomor_kontrak());
            intent.putExtra("alamat", getDataKomplainAdapter.get(getAdapterPosition()).getAlamat());
            intent.putExtra("komplain", getDataKomplainAdapter.get(getAdapterPosition()).getKomplain());
            intent.putExtra("status_komplain", getDataKomplainAdapter.get(getAdapterPosition()).getStatus_komplain());
            context.startActivity(intent);
        }
    }
}