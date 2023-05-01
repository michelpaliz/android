package com.example.caminoalba.ui.menuItems.publication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caminoalba.R;
import com.example.caminoalba.models.Profile;
import com.example.caminoalba.models.Publication;
import com.example.caminoalba.models.dto.Comment;
import com.example.caminoalba.ui.menuItems.publication.recyclers.RecyclerAdapterComments;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FragmentComment extends Fragment {


    private RecyclerView recyclerView;
    private Button btnSendComment;
    private Publication publication;
    private Profile profile;
    private EditText etComment;
    private SharedPreferences sharedPreferences;


    public FragmentComment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comment, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        etComment = view.findViewById(R.id.etComment);
        btnSendComment = view.findViewById(R.id.btnComment);
        recyclerView = view.findViewById(R.id.recycler_view_comments);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Bundle bundle = getArguments();
        if (getArguments() != null) {
            publication = (Publication) bundle.getSerializable("publication");
//            profile = (Profile) bundle.getSerializable("profile");
        }
        Gson gson = new Gson();
        String profileStr = sharedPreferences.getString("profile", "");
        profile = gson.fromJson(profileStr, Profile.class);

        // Get a reference to the comments node in the database
        DatabaseReference commentsRef = FirebaseDatabase.getInstance().getReference().child("comments");

        // Set up the button click listener to add a new comment to the database
        btnSendComment.setOnClickListener(v ->{
            String commentText = etComment.getText().toString();

            // Generate a unique ID for the comment and save it to the database
            String commentId = commentsRef.push().getKey();

            // Save the comment to the Firebase database
            Comment comment = new Comment();
            comment.setPublicationId(publication.getPublication_id());
            comment.setPublication(publication);
            comment.setProfile(profile);
            comment.setId(commentId);
            comment.setCommentText(commentText);
            LocalDateTime datePublished = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formattedDate = datePublished.format(formatter);
            comment.setDatePublished(formattedDate);
            assert commentId != null;
            commentsRef.child(commentId).setValue(comment);

            // Notify the user that the comment was successfully posted
            Toast.makeText(getContext(), "Comment posted!", Toast.LENGTH_SHORT).show();

            // Clear the comment text field
            etComment.setText("");
        });

        // Query the comments node and filter by the publication ID to fetch the comments for this publication
        Query query = commentsRef.orderByChild("publicationId").equalTo(publication.getPublication_id());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Comment> comments = new ArrayList<>();
                for (DataSnapshot commentSnapshot : snapshot.getChildren()) {
                    Comment comment = commentSnapshot.getValue(Comment.class);
                    comments.add(comment);
                }

                System.out.println("Esto es la lista " + comments);
                // Create and set the adapter for the RecyclerView
                RecyclerAdapterComments adapter = new RecyclerAdapterComments(comments, getContext());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle any errors
            }
        });

    }



}