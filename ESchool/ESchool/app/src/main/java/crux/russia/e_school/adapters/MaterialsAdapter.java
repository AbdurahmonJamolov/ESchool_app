package crux.russia.e_school.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import crux.russia.e_school.R;
import crux.russia.e_school.model.Materials;
import crux.russia.e_school.model.MaterialsContant;

public class MaterialsAdapter extends BaseAdapter {
    private List<Materials> originalData = null;
    private LayoutInflater mInflater;
    private Context context;

    public MaterialsAdapter(Context context, List<Materials> data) {
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
            convertView = mInflater.inflate(R.layout.materials_model, null);
            holder = new ViewHolder();

            holder.className = (TextView) convertView.findViewById(R.id.tv_material_model_class_name);
            holder.materialName = (TextView) convertView.findViewById(R.id.tv_material_model_name);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.className.setText(originalData.get(position).getClassName());
        holder.materialName.setText(originalData.get(position).getMaterialName());

        return convertView;
    }

    static class ViewHolder {
        TextView className;
        TextView materialName;

    }


}