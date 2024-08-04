package com.example.assignment_and103.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.assignment_and103.databinding.ActivityLoginBinding;
import com.example.assignment_and103.model.Response;
import com.example.assignment_and103.model.User;
import com.example.assignment_and103.services.HttpRequest;

import retrofit2.Call;
import retrofit2.Callback;


public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    private HttpRequest httpRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        httpRequest = new HttpRequest();

        userListener();

        binding.tvDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

    }

    private void userListener() {
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _username = binding.edUsername.getText().toString().trim();
                String _password = binding.edPassword.getText().toString().trim();
                User user = new User();
                user.setUsername(_username);
                user.setPassword(_password);
                Call<Response<User>> call = httpRequest.callAPI().login(user);
                call.enqueue(new Callback<Response<User>>() {
                    @Override
                    public void onResponse(Call<Response<User>> call, retrofit2.Response<Response<User>> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getStatus() ==200) {
                                Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                SharedPreferences sharedPreferences = getSharedPreferences("INFO",MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("token", response.body().getToken());
                                editor.putString("refreshToken", response.body().getRefreshToken());
                                editor.putString("id", response.body().getData().get_id());
                                editor.apply();
//                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));

                                if ("admin".equalsIgnoreCase(_username)) {
                                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                } else {
                                    startActivity(new Intent(LoginActivity.this, HomeUserActivity.class));
                                }
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "Đăng nhập thất bại: ", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Response<User>> call, Throwable t) {
                        t.getMessage();
                    }
                });

            }
        });
    }
}