package com.elkhamitech.studentmanagerr.view.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.elkhamitech.studentmanagerr.R;
import com.elkhamitech.studentmanagerr.data.model.FinalModel;
import com.elkhamitech.studentmanagerr.data.model.OralModel;
import com.elkhamitech.studentmanagerr.data.sqlitehelper.DatabaseHelper;
import com.elkhamitech.studentmanagerr.view.recycleradapters.ProgressExamsAdapter;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FinalExamFragment extends Fragment {

    private FinalModel finalModel;
    private long id;
    private EditText textGrade;
    private EditText textFrom;
    private Button addGrade;
    private List<FinalModel> finalModelList;
    private ProgressExamsAdapter examsAdapter;
    private DatabaseHelper db;


    public FinalExamFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_final_exam, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RecyclerView mRecyclerView = getView().findViewById(R.id.final_list);
        textGrade = getView().findViewById(R.id.final_edittxt);
        textFrom = getView().findViewById(R.id.final_edittxt2);
        addGrade = getView().findViewById(R.id.final_add_btn);

        db = new DatabaseHelper(getActivity());

        finalModel = new FinalModel();

        id = getActivity().getIntent().getLongExtra("subjectRowId",0);

        finalModelList = db.getFinal(id);


        examsAdapter = new ProgressExamsAdapter<FinalModel>(finalModelList, getActivity()) {
            @Override
            public RecyclerView.ViewHolder setViewHolder(ViewGroup parent) {

                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.exams_list_item,parent,false);
                myViewHolder itemViewHolder = new myViewHolder(itemView);
                return itemViewHolder;
            }

            @Override
            public void onBindData(RecyclerView.ViewHolder holder1, FinalModel val) {
                final FinalModel finalModel = val;
                final myViewHolder holder = (myViewHolder) holder1;

                holder.gradeText.setText(finalModel.getValue());
                holder.fromText.setText(finalModel.getValueFrom());

                holder.moreButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //Creating the instance of PopupMenu
                        PopupMenu popup = new PopupMenu(getActivity(), holder.moreButton);
                        //Inflating the Popup using xml file
                        popup.getMenuInflater().inflate(R.menu.item_menu, popup.getMenu());

                        //registering popup with OnMenuItemClickListener
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            public boolean onMenuItemClick(MenuItem item) {

                                switch (item.getItemId()){
                                    case R.id.edit_item:

                                        return true;
                                    case R.id.delete_item:

                                        db.deleteFinal(finalModel.getRow_id());
                                        finalModelList.remove(holder.getAdapterPosition());
                                        notifyDataSetChanged();

                                        return true;
                                    default:
                                        return false;
                                }

                            }
                        });

                        popup.show();//showing popup menu

                    }
                });

            }
        };


        mRecyclerView.setAdapter(examsAdapter);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        examsAdapter.notifyDataSetChanged();

        addGrade();
    }

    private void addGrade() {

        addGrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String grade = textGrade.getText().toString();
                String gradeFrom = textFrom.getText().toString();
                if(grade.equals("") || gradeFrom.equals("")){

                }else {

                    finalModel.setValue(grade);
                    finalModel.setValueFrom(gradeFrom);
                    finalModel.setSubjectFinal(id);

                    finalModelList.add(finalModel);

                    db.createFinal(finalModel);

                    examsAdapter.notifyDataSetChanged();

                    textGrade.getText().clear();
                    textFrom.getText().clear();

                }
            }
        });
    }

}
