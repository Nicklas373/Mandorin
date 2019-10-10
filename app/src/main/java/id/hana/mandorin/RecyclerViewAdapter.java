package id.hana.mandorin;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Created by JUNED on 6/16/2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;

    List<GetDataAdapter> getDataAdapter;

    ImageLoader imageLoader1;

    public RecyclerViewAdapter(List<GetDataAdapter> getDataAdapter, Context context){

        super();
        this.getDataAdapter = getDataAdapter;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_items, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder Viewholder, int position) {

        GetDataAdapter getDataAdapter1 =  getDataAdapter.get(position);

        imageLoader1 = ServerImageParseAdapter.getInstance(context).getImageLoader();

        imageLoader1.get(getDataAdapter1.getFoto_mandor(),
                ImageLoader.getImageListener(
                        Viewholder.networkImageView,//Server Image
                        R.mipmap.ic_launcher,//Before loading server image the default showing image.
                        android.R.drawable.ic_dialog_alert //Error image if requested image dose not found on server.
                )
        );

        Viewholder.networkImageView.setImageUrl(getDataAdapter1.getFoto_mandor(), imageLoader1);

        Viewholder.Nik.setText(getDataAdapter1.getNik_mandor());
        Viewholder.Nama.setText(getDataAdapter1.getNama_mandor());
        Viewholder.Umur.setText(getDataAdapter1.getUmur_mandor());
        Viewholder.Alamat.setText(getDataAdapter1.getAlamat_mandor());
    }

    @Override
    public int getItemCount() {

        return getDataAdapter.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView Nik, Nama, Umur, Alamat;
        public NetworkImageView networkImageView ;
        public CardView cv_head;

            public ViewHolder(View itemView) {
                super(itemView);

                itemView.setClickable(true);
                itemView.setOnClickListener(this);

                cv_head= (CardView) itemView.findViewById(R.id.cv_head);
                Nik = (TextView) itemView.findViewById(R.id.textViewNik1) ;
                Nama = (TextView) itemView.findViewById(R.id.textViewNama1) ;
                Umur = (TextView) itemView.findViewById(R.id.textViewUmur1) ;
                Alamat = (TextView) itemView.findViewById(R.id.textViewAlamat1) ;
                networkImageView = (NetworkImageView) itemView.findViewById(R.id.VollyNetworkImageView1) ;
            }


            @Override
            public void onClick(View v) {
                Drawable drawable=networkImageView.getDrawable();
                Bitmap bitmap= ((BitmapDrawable)drawable).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] b = baos.toByteArray();
                Intent intent = new Intent(context, activity_profile_mandor.class);
                intent.putExtra("nik", getDataAdapter.get(getAdapterPosition()).getNik_mandor());
                intent.putExtra("nama", getDataAdapter.get(getAdapterPosition()).getNama_mandor());
                intent.putExtra("umur", getDataAdapter.get(getAdapterPosition()).getUmur_mandor());
                intent.putExtra("alamat", getDataAdapter.get(getAdapterPosition()).getAlamat_mandor());
                intent.putExtra("foto", b);
                context.startActivity(intent);
            }
        }
    }