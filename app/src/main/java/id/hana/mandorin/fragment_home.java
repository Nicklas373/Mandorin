package id.hana.mandorin;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class fragment_home extends Fragment {

    public static final String TITLE = "Menu";

    private CardView menu_1, menu_2, menu_3, menu_5, menu_6;

    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    FirebaseUser firebaseUser;

    public static fragment_home newInstance() {

        return new fragment_home();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@Nullable View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Fragment locked in portrait screen orientation
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        /*
         * Begin firebase authorization
         */
        auth = FirebaseAuth.getInstance();

        menu_1 = view.findViewById(R.id.cv_menu_1);
        menu_2 = view.findViewById(R.id.cv_menu_2);
        menu_3 = view.findViewById(R.id.cv_menu_3);
        menu_5 = view.findViewById(R.id.cv_menu_5);
        menu_6 = view.findViewById(R.id.cv_menu_6);

        menu_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), activity_mandor.class);
                getActivity().startActivity(intent);

            }
        });

        menu_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (auth.getCurrentUser() != null) {
                    auth.getCurrentUser().reload();
                    if (auth.getCurrentUser().isEmailVerified()) {
                        Intent intent = new Intent(getActivity(), activity_kontrak.class);
                        getActivity().startActivity(intent);
                    } else {
                        ver_acc_dialog();
                    }
                } else {
                    AlertDialog.Builder alertDialogBuilder= new AlertDialog.Builder(getActivity());

                    // set title dialog
                    alertDialogBuilder.setTitle("Menu Kontrak");

                    // set pesan dari dialog
                    alertDialogBuilder
                            .setMessage("Harap Login Untuk Melanjutkan")
                            .setIcon(R.mipmap.ic_launcher)
                            .setCancelable(false)
                            .setPositiveButton("Login",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    dialog = ProgressDialog.show(getActivity(), "Menu Login", "Memproses...", true);
                                    Intent intent = new Intent(getActivity(), activity_login.class);
                                    getActivity().startActivity(intent);
                                }
                            })
                            .setNegativeButton("Batal",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    // membuat alert dialog dari builder
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // menampilkan alert dialog
                    alertDialog.show();
                }

            }
        });

        menu_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (auth.getCurrentUser() != null) {
                    auth.getCurrentUser().reload();
                    if (auth.getCurrentUser().isEmailVerified()) {
                        Intent intent = new Intent(getActivity(), activity_transaksi.class);
                        getActivity().startActivity(intent);
                    } else {
                        ver_acc_dialog();
                    }
                } else {
                    AlertDialog.Builder alertDialogBuilder= new AlertDialog.Builder(getActivity());

                    // set title dialog
                    alertDialogBuilder.setTitle("Menu Pembayaran");

                    // set pesan dari dialog
                    alertDialogBuilder
                            .setMessage("Harap Login Untuk Melanjutkan")
                            .setIcon(R.mipmap.ic_launcher)
                            .setCancelable(false)
                            .setPositiveButton("Login",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    dialog = ProgressDialog.show(getActivity(), "Menu Login", "Memproses...", true);
                                    Intent intent = new Intent(getActivity(), activity_login.class);
                                    getActivity().startActivity(intent);
                                }
                            })
                            .setNegativeButton("Batal",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    // membuat alert dialog dari builder
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // menampilkan alert dialog
                    alertDialog.show();
                }
            }
        });

        menu_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), activity_bantuan.class);
                getActivity().startActivity(intent);
            }
        });

        menu_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), activity_akun.class);
                    getActivity().startActivity(intent);
            }
        });
    }

    private void ver_acc_dialog(){
        AlertDialog.Builder alertDialogBuilder= new AlertDialog.Builder(getActivity());

        // set title dialog
        alertDialogBuilder.setTitle("Verifikasi E-Mail");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Verifikasi e-mail di perlukan untuk mengakses menu ini, verifikasi ?")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        auth.getCurrentUser().sendEmailVerification();
                        Toast.makeText(getActivity(), "E-mail verifikasi telah dikirim ke " + auth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(getActivity(), "Harap verifikasi e-mail anda", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
    }
}