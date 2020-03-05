package com.azizutku.hipo;

import android.content.Intent;
import android.os.Bundle;

import com.azizutku.hipo.adapters.MemberAdapter;
import com.azizutku.hipo.core.App;
import com.azizutku.hipo.models.Company;
import com.azizutku.hipo.rest.ApiClient;
import com.azizutku.hipo.rest.ApiInterface;
import com.azizutku.hipo.views.MessageDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StartActivity extends AppCompatActivity {

    private static final String TAG = "StartActivity";

    private boolean isAnimationEnd = false;
    private boolean isCompanyRetrieved = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        ImageView imgLogo = findViewById(R.id.start_img_logo);

        // Set animation with bounce effect to Hipo logo.
        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                isAnimationEnd = true;
                openMainActivity();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        imgLogo.startAnimation(anim);
        getCompany();
    }

    private void openMainActivity() {
        if (isAnimationEnd && isCompanyRetrieved) {
            Intent mainIntent = new Intent(StartActivity.this, MainActivity.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(mainIntent);
        }
    }

    // Retrieve all members.
    private void getCompany() {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<Company> call = apiService.getCompany();
        call.enqueue(new Callback<Company>() {
            @Override
            public void onResponse(Call<Company> call, Response<Company> response) {
                if (response.body() != null) {
                    Log.i(TAG, "NET: getCompany: RESPONSE: " + response.body().toString());
                    App.company = response.body();
                    isCompanyRetrieved = true;
                    openMainActivity();
                } else {
                    Log.i(TAG, "NET: getCompany: ERR: " + "Response null");
                    showMessageDialog(getString(R.string.error), getString(R.string.unknown_error));
                }
            }

            @Override
            public void onFailure(Call<Company> call, Throwable t) {
                Log.e(TAG, "NET: getCompany: "  + t.toString());
                showMessageDialog(getString(R.string.error), getString(R.string.connection_error));
            }
        });
    }

    private void showMessageDialog(String title, String message) {
        MessageDialog anchorDialog = new MessageDialog(StartActivity.this);
        anchorDialog.setTitle(title);
        anchorDialog.setMessage(message);
        anchorDialog.setPositiveButton(StartActivity.this.getString(R.string.okay), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });
        anchorDialog.show();
    }

}
