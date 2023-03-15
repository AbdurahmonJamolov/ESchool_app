package crux.russia.e_school.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import crux.russia.e_school.R;
import crux.russia.e_school.model.ClassRoom;

public class ClassRoomAdapter extends BaseAdapter {
    private List<ClassRoom> originalData = null;
    private LayoutInflater mInflater;
    private Context context;

    public ClassRoomAdapter(Context context, List<ClassRoom> data) {
        this.context = context;
        this.originalData = data;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return originalData.size();
    }

    public Object getItem(int position) {
        return originalData.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.class_room_model, null);
            holder = new ViewHolder();

            holder.name = (TextView) convertView.findViewById(R.id.tv_cr_model_name);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(originalData.get(position).getClassName());

        return convertView;
    }

    static class ViewHolder {
        TextView name;
    }


}