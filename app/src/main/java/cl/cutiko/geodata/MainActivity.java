package cl.cutiko.geodata;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;

import cl.cutiko.geodata.models.GeoPod;

public class MainActivity extends AppCompatActivity implements PodsCallback {

    private PopulatePods populatePods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populatePods = new PopulatePods(this);
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        populatePods.getNearPods(telephonyManager);

        Button button = (Button) findViewById(R.id.nextBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, IndexedListActivity.class));
            }
        });
    }

    @Override
    public void addPod(GeoPod geoPod) {
        //Toast.makeText(this, geoPod.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        populatePods.clean();
        super.onDestroy();
    }
}
