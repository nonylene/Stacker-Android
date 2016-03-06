package net.nonylene.stacker;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class QuestionsRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Question> mQuestionList = new ArrayList<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View questionView = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_item, parent, false);
        return new QuestionViewHolder(questionView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((QuestionViewHolder) holder).bindView(mQuestionList.get(position));
    }

    public void setQuestionList(List<Question> questionList) {
        mQuestionList = questionList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mQuestionList.size();
    }

    private class QuestionViewHolder extends RecyclerView.ViewHolder {

        private final TextView mCountView;
        private final ImageView mCheckView;
        private final TextView mTitleView;
        private final TextView mTagView;
        private final TextView mOwnerView;
        private final TextView mDateView;
        private final TextView mBodyView;

        public QuestionViewHolder(View itemView) {
            super(itemView);
            mCountView = (TextView) itemView.findViewById(R.id.count);
            mCheckView = (ImageView) itemView.findViewById(R.id.check);
            mTitleView = (TextView) itemView.findViewById(R.id.title);
            mTagView = (TextView) itemView.findViewById(R.id.tags);
            mOwnerView = (TextView) itemView.findViewById(R.id.owner);
            mDateView = (TextView) itemView.findViewById(R.id.date);
            mBodyView = (TextView) itemView.findViewById(R.id.body);
        }

        public void bindView(final Question question) {
            mCountView.setText(String.valueOf(question.upVoteCount));
            mCheckView.setVisibility(question.isAnswered ? View.VISIBLE : View.GONE);
            mTitleView.setText(question.title);

            StringBuilder builder = new StringBuilder();
            for (String tag : question.tags) {
                builder.append(tag).append(" ");
            }
            mTagView.setText(builder.toString());

            mOwnerView.setText(question.ownerName);
            mDateView.setText(new SimpleDateFormat("yyyy/MM/dd", Locale.JAPAN).format(question.creationDate));
            mBodyView.setText(Html.fromHtml(question.body).toString());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(question.link));
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
