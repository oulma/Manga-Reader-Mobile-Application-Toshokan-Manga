package com.example.toshokan_manga.ui.mangalist;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.toshokan_manga.MangaC;
import com.example.toshokan_manga.MyAdapter;
import com.example.toshokan_manga.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MangaListFragment extends Fragment {

    View v;
    DatabaseReference reference;


    public JsonArrayRequest request;
    public RequestQueue requestQueue;
    boolean scrolling = false;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ProgressBar progressBar;


    private List<MangaC> mangaCS;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





    }

    @Override
    public void onStart() {
        super.onStart();



    }

    private MangaListViewModel mViewModel;

    public static MangaListFragment newInstance() {
        return new MangaListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.manga_list_fragment,container,false);
        recyclerView = v.findViewById(R.id.rv_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        reference = FirebaseDatabase.getInstance().getReference().child("Manga_list");
        progressBar = v.findViewById(R.id.p_b);
        mangaCS = new ArrayList<MangaC>();

        progressBar.setVisibility(View.VISIBLE);
           reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mangaCS.clear();
               progressBar.setVisibility(View.GONE);

                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    MangaC manga = dataSnapshot1.getValue(MangaC.class);
                      mangaCS.add(manga);

                }
                adapter = new MyAdapter(mangaCS,getActivity());
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        return v;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MangaListViewModel.class);
        // TODO: Use the ViewModel


    }

}