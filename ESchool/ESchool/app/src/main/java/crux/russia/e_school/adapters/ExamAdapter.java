package crux.russia.e_school.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import crux.russia.e_school.R;
import crux.russia.e_school.model.Exam;
import crux.russia.e_school.model.Materials;

public class ExamAdapter extends BaseAdapter {
    private List<Exam> originalData = null;
    private LayoutInflater mInflater;
    private Context context;

    public ExamAdapter(Context context, List<Exam> data) {
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
            convertView = mInflater.inflate(R.layout.exam_model, null);
            holder = new ViewHolder();

            holder.materialName = (TextView) convertView.findViewById(R.id.tv_exam_model_material_name);
            holder.examTitle = (TextView) convertView.findViewById(R.id.tv_exam_model_title);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.materialName.setText(originalData.get(position).getMaterialsName());
        holder.examTitle.setText(originalData.get(position).getExamTitl());

        return convertView;
    }

    static class ViewHolder {
        TextView examTitle;
        TextView materialName;

    }


}