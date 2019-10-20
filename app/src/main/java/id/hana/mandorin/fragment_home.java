package id.hana.mandorin;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class fragment_home extends Fragment {

    public static final String TITLE = "Menu";

    private CardView menu_1, menu_6;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;

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

        auth = FirebaseAuth.getInstance();

        menu_1 = view.findViewById(R.id.cv_menu_1);
        menu_6 = view.findViewById(R.id.cv_menu_6);

        menu_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (auth.getCurrentUser() != null) {
                    Intent intent = new Intent(getActivity(), activity_mandor.class);
                    getActivity().startActivity(intent);
                } else {
                    Toast.makeText(getActivity(),"Harap Login di Menu Akun untuk melanjutkan", Toast.LENGTH_SHORT).show();
                }

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
}