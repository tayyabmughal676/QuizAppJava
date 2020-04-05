package com.example.quizappjava;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.quizappjava.R.id.start_feedback;


/**
 * A simple {@link Fragment} subclass.
 */
public class StartFragment extends Fragment {

    private ProgressBar mStartProgressBar;
    private TextView mStartFeedbackText;
    private FirebaseAuth mAuth;
    private static final String START_TAG ="Start Log";
    private NavController mNavController;

    public StartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_start, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//      Firebase
        mAuth = FirebaseAuth.getInstance();
//        NavController
        mNavController = Navigation.findNavController(view);
//        Starts
        mStartProgressBar = view.findViewById(R.id.start_progress);
        mStartFeedbackText = view.findViewById(start_feedback);

        mStartFeedbackText.setText("Checking User Account...");

    }

    @Override
    public void onStart() {
        super.onStart();
//        Check if user logged in
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
//            create new user
            mStartFeedbackText.setText("Creating Account...");

            mAuth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
//                        Navigate to HomePage
                        mStartFeedbackText.setText("Account Created...");
                        mNavController.navigate(R.id.action_startFragment_to_listFragment);
                    }else{
                        Log.d(START_TAG, "Start Log: " + task.getException());
                    }
                }
            });

        }else{
//            Navigate to HomePage
            mStartFeedbackText.setText("Logged in...");
            mNavController.navigate(R.id.action_startFragment_to_listFragment);
        }

    }
}

