package com.example.me.sevenminutes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.io.IOException;

public class LearnPageFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.learn_page, container, false);

        GridView gridView= (GridView) rootView.findViewById(R.id.gridview);


        MyDBHandler dbHandler=new MyDBHandler(getContext(),null,null,1);
        try {
            dbHandler.createDataBase();
            dbHandler.openDataBase();

        }
        catch (IOException e){
            e.printStackTrace();
        }

        LearnAdapter learnAdapter=new LearnAdapter(getContext(),dbHandler);
        gridView.setAdapter(learnAdapter);

        Log.d("debug","number of tables="+String.valueOf(dbHandler.countTables()));
        //Log.d("debug","hi  "+dbHandler.getTestData().getString(1));
        //Log.d("debug","he  "+dbHandler.loadHandler());
        //Log.d("debug","ho"+dbHandler.findWorkoutHandler(1).getName());


        /*Log.d("debug","workouts:"+workouts.size());
        Log.d("debug","adapter:"+learnAdapter.getCount());*/
        return rootView;
    }
}
