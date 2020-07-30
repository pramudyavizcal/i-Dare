package com.pramu.idare.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.pramu.idare.R;
import com.pramu.idare.Utils.GameModel;

import java.util.ArrayList;

public class TruthAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> truth;
    private GameModel gameModel;

    public TruthAdapter(GameModel gameModel, Context context){
        this.context = context;
        this.gameModel= gameModel;
        this.truth= gameModel.getTruthList();
    }

    public int getCount(){
        return truth.size();
    }

    @Override
    public Object getItem(int i){
        return truth.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View v, ViewGroup viewGroup) {
        View view=v;
        if (view==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_lv, null);
        }
        TextView dareText = view.findViewById(R.id.list_item_string);
        dareText.setText(truth.get(i));
        Button deletebtn = view.findViewById(R.id.delete_btn);
        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                truth.remove(i);
                gameModel.updateTruthDB(truth);
                notifyDataSetChanged();
            }
        });
        return view;
    }
}
