package br.com.heiderlopes.voicez.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.heiderlopes.voicez.R;
import br.com.heiderlopes.voicez.model.People;

/**
 * Created by heiderlopes on 11/09/14.
 */
public class NameListAdapter extends ArrayAdapter<People> {

    private final Activity context;
    private final List<People> alPeople;

    static class ViewHolder {
        public TextView label;
        public ImageView image;
    }

    public NameListAdapter(Activity context, List<People> alPeople) {
        super(context, R.layout.item_name_list, alPeople);
        this.context = context;
        this.alPeople = alPeople;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.item_name_list, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.label = (TextView) convertView.findViewById(R.id.label);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.icon);
            convertView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.label.setText(alPeople.get(position).getName());

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.shake);
        animation.setDuration(500);
        convertView.startAnimation(animation);
        return convertView;
    }
}
