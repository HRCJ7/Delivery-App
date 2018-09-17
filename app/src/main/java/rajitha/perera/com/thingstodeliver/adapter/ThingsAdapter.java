package rajitha.perera.com.thingstodeliver.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import rajitha.perera.com.thingstodeliver.R;
import rajitha.perera.com.thingstodeliver.activity.DetailActivity;
import rajitha.perera.com.thingstodeliver.model.Thing;

/**
 * Created by Rajitha on 9/15/2018.
 */

public class ThingsAdapter extends RecyclerView.Adapter<ThingsAdapter.ThingsViewHolder> {

    private final List<Thing> dataList;
    private final Context context;


    public ThingsAdapter(List<Thing> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public ThingsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_things, parent, false);
        return new ThingsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ThingsViewHolder holder, int position) {
        int position1 = position;
        Picasso.get().load(dataList.get(position).getImageUrl()).into(holder.things_image);
        holder.txt_things_description.setText(dataList.get(position).getDescription());
        holder.txt_things_address.setText(dataList.get(position).getLocation().getAddress());
        final Thing thing = dataList.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("Thing", thing);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {

        return dataList.size();

    }

    class ThingsViewHolder extends RecyclerView.ViewHolder {

        final TextView txt_things_description;
        final TextView txt_things_address;
        final CircleImageView things_image;

        ThingsViewHolder(View itemView) {
            super(itemView);
            txt_things_description = itemView.findViewById(R.id.txt_things_description);
            txt_things_address = itemView.findViewById(R.id.txt_things_address);
            things_image = itemView.findViewById(R.id.things_image);

        }
    }
}