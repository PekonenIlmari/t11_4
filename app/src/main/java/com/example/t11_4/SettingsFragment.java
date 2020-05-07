package com.example.t11_4;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class SettingsFragment extends Fragment {
    private SettingsFragmentListener listener;
    Button button1, button2;
    EditText description;
    Spinner languageSpinner;
    View v;
    Context context;

    public interface SettingsFragmentListener {
        void onInputSent(String input);
    }

    public SettingsFragment(Context c) {
        context = c;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_settings, container, false);
        button1 = v.findViewById(R.id.editButton);
        button2 = v.findViewById(R.id.saveButton);
        description = v.findViewById(R.id.description);
        languageSpinner = v.findViewById(R.id.languageSpinner);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = description.getText().toString();
                listener.onInputSent(input);
                description.setText("");
                Toast.makeText(getContext(), "Teksti muutettu", Toast.LENGTH_SHORT).show();
            }
        });

        setSpinner();

        return v;
    }

    private void setSpinner() {
        List<String> languageList = new ArrayList<>();
        languageList.add("Valitse kieli");
        languageList.add("Suomi");
        languageList.add("Englanti");
        languageList.add("Ruotsi");

        ArrayAdapter<String> languageAdapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, languageList);
        languageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(languageAdapter);

        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    System.out.println("MOI");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        int arg = getArguments().getInt("key");
        if (arg == 1) {
            button1.setText("Käytössä");
        } else {
            button1.setText("Ei käytössä");
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof SettingsFragmentListener) {
            listener = (SettingsFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement SettingsFragmentListener");
        }
    }


}
