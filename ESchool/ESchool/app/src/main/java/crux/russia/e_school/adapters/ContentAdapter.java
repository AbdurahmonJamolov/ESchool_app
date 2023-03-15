package crux.russia.e_school.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import crux.russia.e_school.R;
import crux.russia.e_school.model.ClassRoom;
import crux.russia.e_school.model.MaterialsContant;

public class ContentAdapter extends BaseAdapter {
    private List<MaterialsContant> originalData = null;
    private LayoutInflater mInflater;
    private Context context;

    public ContentAdapter(Context context, List<MaterialsContant> data) {
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
            convertView = mInflater.inflate(R.layout.content_model, null);
            holder = new ViewHolder();

            holder.materialName = (TextView) convertView.findViewById(R.id.tv_content_model_material_name);
            holder.title = (TextView) convertView.findViewById(R.id.tv_content_model_title);
            holder.body = (TextView) convertView.findViewById(R.id.tv_content_model_body);
            holder.link = (TextView) convertView.findViewById(R.id.tv_content_model_link);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.materialName.setText(originalData.get(position).getMaterialName());
        holder.title.setText(originalData.get(position).getTitle());
        holder.body.setText(originalData.get(position).getBody());
        holder.link.setText(originalData.get(position).getLink());

        return convertView;
    }

    static class ViewHolder {
        TextView materialName;
        TextView title;
        TextView body;
        TextView link;

    }


}