package crux.russia.e_school.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;

import crux.russia.e_school.R;
import crux.russia.e_school.model.Exam;
import crux.russia.e_school.model.Question;

public class QuestionAdapter extends BaseAdapter {
    private List<Question> originalData = null;
    private LayoutInflater mInflater;
    private Context context;

    public QuestionAdapter(Context context, List<Question> data) {
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
            convertView = mInflater.inflate(R.layout.question_model, null);
            holder = new ViewHolder();

            holder.questionBody = (TextView) convertView.findViewById(R.id.tv_question_model_body);
            holder.radioGroupQuestion = (RadioGroup) convertView.findViewById(R.id.rg_question_answers);

            holder.radioButtonTrue = (RadioButton) convertView.findViewById(R.id.rb_question_true);
            holder.radioButtonFalse = (RadioButton) convertView.findViewById(R.id.rb_question_false);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.questionBody.setText(originalData.get(position).getQuestionBody());

        if (originalData.get(position).isCorrectAnswer()) {
            holder.radioButtonTrue.setChecked(true);
        } else {
            holder.radioButtonFalse.setChecked(true);
        }
        return convertView;
    }

    static class ViewHolder {
        TextView questionBody;

        RadioGroup radioGroupQuestion;
        RadioButton radioButtonTrue;
        RadioButton radioButtonFalse;

    }


}