package cl.cutiko.geodata.adapters;

import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseIndexRecyclerAdapter;

import cl.cutiko.geodata.data.Nodes;
import cl.cutiko.geodata.models.GeoPod;

/**
 * Created by cutiko on 17-08-17.
 */

public class GeoPodsAdapter extends FirebaseIndexRecyclerAdapter<GeoPod, GeoPodsAdapter.PodsHolder> {


    public GeoPodsAdapter(TelephonyManager telephonyManager) {
        super(
                GeoPod.class,
                android.R.layout.simple_list_item_1,
                PodsHolder.class,
                new Nodes().locationsByIndex(telephonyManager.getSimCountryIso()),
                new Nodes().locations(telephonyManager.getNetworkCountryIso())
        );
    }

    @Override
    protected void populateViewHolder(PodsHolder viewHolder, GeoPod model, int position) {
        TextView textView = (TextView) viewHolder.itemView;
        textView.setText(model.getName());
    }

    public static class PodsHolder extends RecyclerView.ViewHolder {
        public PodsHolder(View itemView) {
            super(itemView);
        }
    }

}
