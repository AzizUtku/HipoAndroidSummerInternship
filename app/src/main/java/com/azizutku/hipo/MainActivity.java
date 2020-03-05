package com.azizutku.hipo;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;

import com.azizutku.hipo.adapters.MemberAdapter;
import com.azizutku.hipo.core.App;
import com.azizutku.hipo.interfaces.OnSortButtonClickedListener;
import com.azizutku.hipo.models.Member;
import com.azizutku.hipo.utils.Constants;
import com.azizutku.hipo.views.SortDialog;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ListPopupWindow;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";


    private ArrayList<Member> mMembers = (ArrayList<Member>) App.company.getMembers().clone();
    private RecyclerView mRecyclerMembers;
    private Button mBtnAddMe;
    private EditText mEdtSearch;
    private ImageView mImgClear;

    private SortDialog mSortDialog;

    private ListPopupWindow mPopupSuggestion;
    private String[] mSuggestions = new String[5];

    private int mSortSelected = -1;
    private boolean mSortIsAscending = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set toolbar
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        // Initialize sort dialog
        mSortDialog = new SortDialog(MainActivity.this, new OnSortButtonClickedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSortButtonClicked(int actionId, boolean isAscending) {
                mSortSelected = actionId;
                mSortIsAscending = isAscending;

                // Get all members with original order.
                mMembers = (ArrayList<Member>) App.company.getMembers().clone();

                // Sort them
                sortMembers(actionId, isAscending);
            }
        });

        mMembers = new ArrayList<>();
        mMembers = (ArrayList<Member>) App.company.getMembers().clone();

        mRecyclerMembers = findViewById(R.id.main_recycler_members);
        mEdtSearch = findViewById(R.id.main_edt_search);
        mImgClear = findViewById(R.id.main_img_clear);
        mBtnAddMe = findViewById(R.id.main_btn_add_me);

        mRecyclerMembers.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL , false));
        updateRecycler();

        mBtnAddMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Member member = new Member("Aziz Utku Kagitci", 22, "Ankara", "azizutku");
                member.setHipo("Intern Candidate", 0);
                App.company.getMembers().add(member);
                mMembers = (ArrayList<Member>) App.company.getMembers().clone();

                // Sort again after adding new member
                if (mSortSelected >= 0 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    sortMembers(mSortSelected, mSortIsAscending);
                }
                updateRecycler();
            }
        });

        // Initialize suggestions popup
        initSuggestions();

        // Search part
        mImgClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEdtSearch.setText("");
                updateRecycler();
            }
        });

        mEdtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    search(v.getText().toString());
                    return true;
                }
                return false;
            }
        });

        mEdtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // If searched string is empty refresh the recyclerview
                if (s.length() == 0) {
                    mImgClear.setVisibility(View.GONE);
                    mPopupSuggestion.dismiss();
                    updateRecycler();
                    return;
                }

                // If searched string is not empty show the clear image
                mImgClear.setVisibility(View.VISIBLE);

                ArrayList<String> suggestions = new ArrayList<>();

                // Filter suggestions
                for (int i = 0; i < mMembers.size() && suggestions.size() <= 5; ++i) {
                    String name = mMembers.get(i).getName();
                    if (mMembers.get(i).getName().toLowerCase().trim().contains(s.toString().trim().toLowerCase())) {
                        suggestions.add(name);
                    }
                }

                if (suggestions.size() == 0) {
                    mPopupSuggestion.dismiss();
                    return;
                }

                mSuggestions = suggestions.toArray(new String[0]);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.row_spinner, mSuggestions);
                mPopupSuggestion.setAdapter(adapter);
                mPopupSuggestion.setWidth(mEdtSearch.getWidth());
                mPopupSuggestion.dismiss();
                if (mSuggestions.length == 0) {
                    mPopupSuggestion.dismiss();
                }
                if (!mPopupSuggestion.isShowing()) {
                    mPopupSuggestion.show();
                }


                synchronized (mPopupSuggestion) {
                    mPopupSuggestion.notifyAll();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void initSuggestions() {
        mPopupSuggestion = new ListPopupWindow(MainActivity.this);
        mPopupSuggestion.setAnchorView(mEdtSearch);
        mPopupSuggestion.setModal(false);
        mPopupSuggestion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                search(mSuggestions[position]);
                mEdtSearch.setText(mSuggestions[position]);
                mEdtSearch.setSelection(mEdtSearch.getText().length());
                mPopupSuggestion.dismiss();
            }
        });
    }

    // Sort members
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void sortMembers(int actionId, boolean isAscending) {
        switch (actionId) {
            case Constants.SORT_NAME:
                mMembers.sort(new Comparator<Member>() {
                    @Override
                    public int compare(Member o1, Member o2) {
                        return o1.getName().compareTo(o2.getName());
                    }
                });
                break;
            case Constants.SORT_AGE:
                mMembers.sort(new Comparator<Member>() {
                    @Override
                    public int compare(Member o1, Member o2) {
                        if (o1.getAge() > o2.getAge()) {
                            return 1;
                        } else if (o1.getAge() == o2.getAge()) {
                            return 0;
                        }

                        return -1;
                    }
                });
                break;
            case Constants.SORT_LOCATION:
                mMembers.sort(new Comparator<Member>() {
                    @Override
                    public int compare(Member o1, Member o2) {
                        return o1.getLocation().compareTo(o2.getLocation());
                    }
                });
                break;
        }
        if (!isAscending) {
            Collections.reverse(mMembers);
        }
        updateRecycler();
    }

    // Update recyclerview
    private void updateRecycler() {
        mRecyclerMembers.setAdapter(new MemberAdapter(MainActivity.this, mMembers));
        synchronized (mRecyclerMembers) {
            mRecyclerMembers.notifyAll();
        }
    }


    private void search(String s) {
        // Hide keyboard after searching
        hideKeyboard();
        ArrayList<Member> searchedMembers = new ArrayList<>();

        // Get all members that matched.
        for (int i = 0; i < mMembers.size(); ++i) {
            if (mMembers.get(i).getName().toLowerCase().trim().contains(s.trim().toLowerCase())) {
                searchedMembers.add(mMembers.get(i));
            }
        }

        // Update recyclerview
        mRecyclerMembers.setAdapter(new MemberAdapter(MainActivity.this, searchedMembers));
        synchronized (mRecyclerMembers) {
            mRecyclerMembers.notifyAll();
        }
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) MainActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = MainActivity.this.getCurrentFocus();
        if (view == null) {
            view = new View(MainActivity.this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_main_sort) {
            mSortDialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mSortDialog.hide();
    }
}
