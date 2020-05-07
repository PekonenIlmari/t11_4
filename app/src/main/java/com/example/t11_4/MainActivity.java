package com.example.t11_4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SettingsFragment.SettingsFragmentListener {
    private DrawerLayout drawer;
    NavigationView navigationView;
    TextView editableTextMain, helloText;
    EditText writable;
    private int tempH, editValue = 1, round = 0;;
    private ViewGroup.LayoutParams writableLp, editableLp;
    FragmentManager fm = getSupportFragmentManager();
    SettingsFragment sf = new SettingsFragment(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        editableTextMain = findViewById(R.id.editableTextMain);
        helloText = findViewById(R.id.helloText);
        writable = findViewById(R.id.writableTextMain);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        editableLp = editableTextMain.getLayoutParams();
        writableLp = writable.getLayoutParams();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setCheckedItem(R.id.nav_frontpage);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_frontpage:
                if (fm != null) {
                    for (int i = 0; i < fm.getBackStackEntryCount(); i++) {
                        fm.popBackStack();
                    }
                }
                fm.findFragmentById(R.id.nav_frontpage);
                navigationView.setCheckedItem(R.id.nav_frontpage);
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_settings:
                fm.beginTransaction().replace(R.id.fragment_container,
                        sf).addToBackStack("settings").commit();
                navigationView.setCheckedItem(R.id.nav_settings);
                drawer.closeDrawer(GravityCompat.START);
                Bundle bundle = new Bundle();
                bundle.putInt("key", editValue);
                sf.setArguments(bundle);
                break;
            case R.id.font_bigger:
                editFont(1);
                break;
            case R.id.font_smaller:
                editFont(2);
                break;
            case R.id.width_bigger:
                editWidth(1);
                break;
            case R.id.width_smaller:
                editWidth(2);
                break;
            case R.id.height_bigger:
                editHeight(1);
                break;
            case R.id.height_smaller:
                editHeight(2);
                break;
            case R.id.spacing_bigger:
                editRows(1);
                break;
            case R.id.spacing_smaller:
                editRows(2);
                break;
        }
        return true;
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void editFont(int a) {
        if (editValue != 0) {
            float px = writable.getTextSize();
            float sp = px / getResources().getDisplayMetrics().scaledDensity;
            if (a == 1) {
                sp += 1;
            } else {
                sp -= 1;
            }
            writable.setTextSize(TypedValue.COMPLEX_UNIT_SP, sp);
        } else {
            float px = editableTextMain.getTextSize();
            float sp = px / getResources().getDisplayMetrics().scaledDensity;
            if (a == 1) {
                sp += 1;
            } else {
                sp -= 1;
            }
            editableTextMain.setTextSize(TypedValue.COMPLEX_UNIT_SP, sp);
        }
    }

    private void editWidth(int b) {
        if (editValue != 0) {
            if (b == 1) {
                writableLp.width += 25;
            } else {
                writableLp.width -= 25;
            }
            writable.setLayoutParams(writableLp);
        } else {
            if (b == 1) {
                editableLp.width += 25;
            } else {
                editableLp.width -= 25;
            }
            editableTextMain.setLayoutParams(editableLp);
        }
    }

    private void editHeight(int c) {
        if (editValue != 0) {
            int heightRound = 0;
            if (heightRound == 0) {
                writable.measure(0, 0);
                tempH = writable.getHeight();
                writableLp.height = tempH;
            }
            if (c == 1) {
                writableLp.height += 10;
            } else {
                writableLp.height -= 10;
            }
            heightRound++;
            writable.setLayoutParams(writableLp);
        } else {
            int heightRound = 0;
            if (heightRound == 0) {
                editableTextMain.measure(0 ,0);
                tempH = editableTextMain.getHeight();
                editableLp.height = tempH;
            }
            if (c == 1) {
                editableLp.height += 10;
            } else {
                editableLp.height -= 10;
            }
            heightRound++;
            editableTextMain.setLayoutParams(editableLp);
        }
    }

    private void editRows(int d) {
        if (editValue != 0) {
            if (d == 1) {
                writable.setLetterSpacing(writable.getLetterSpacing() + 1);
            } else {
                writable.setLetterSpacing(writable.getLetterSpacing() - 1);
            }
        } else {
            if (d == 1) {
                editableTextMain.setLetterSpacing(editableTextMain.getLetterSpacing() + 1);
            } else {
                editableTextMain.setLetterSpacing(editableTextMain.getLetterSpacing() - 1);
            }
        }
    }

    public void writingEnabler(View v) {
        if (round % 2 == 0) {
            editValue = 0;
            sf.button1.setText("Ei käytössä");
            writable.setEnabled(false);
            passToBox();
            round++;
        } else {
            editValue = 1;
            sf.button1.setText("Käytössä");
            writable.setEnabled(true);
            round++;
        }

        System.out.println(editValue);
    }

    private void passToBox() {
        writable.measure(0, 0);
        editableTextMain.setTextSize(TypedValue.COMPLEX_UNIT_SP, writable.getTextSize() /
                getResources().getDisplayMetrics().scaledDensity);
        editableLp.width = writable.getWidth();
        editableLp.height = writable.getHeight();
        editableTextMain.setLetterSpacing(writable.getLetterSpacing());
        editableTextMain.setText(writable.getText().toString());
    }

    @Override
    public void onInputSent(String input) {
        helloText.setText(input);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }
}
