package id.hana.mandorin;
import android.util.Log;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class FirebaseInstance extends FirebaseInstanceIdService {
    private static final String ID = "test";
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(ID,"Refreshed token: " + token);
    }
    public void caritoken(){
        onTokenRefresh();
    }
}