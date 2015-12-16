package com.cloudproj.microforum;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CustomListAdapter extends BaseAdapter{
    String [] users;
    Context context;
    String [] comments;

    private static LayoutInflater inflater=null;

    public CustomListAdapter(ForumPage forumPage, String[] prgmNameList, String[] prgmImages) {
        // TODO Auto-generated constructor stub
        users=prgmNameList;
        context=forumPage;
        comments=prgmImages;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return users.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView user;
        TextView comment;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.commentslist, null);
        holder.user=(TextView) rowView.findViewById(R.id.commentUser);
        holder.comment=(TextView) rowView.findViewById(R.id.commentText);
        holder.user.setText(users[position]);
        holder.comment.setText(comments[position]);
        rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "You Clicked "+users[position], Toast.LENGTH_LONG).show();
            }
        });
        return rowView;
    }

}