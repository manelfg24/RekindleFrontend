package com.example.usuario.rekindlefrontend.view.menu.login;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usuario.rekindlefrontend.R;
import com.example.usuario.rekindlefrontend.comunicacion.ComunicacionUsuarios;
import com.example.usuario.rekindlefrontend.view.menu.menuPrincipal.MenuPrincipal;

public class RecuperarPassword extends AppCompatActivity {

    Button _changePassword;
    EditText _passwordText;
    EditText _confirmPasswordText;
    EditText _codeText;
    TextView _back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_password);

        final String email = getIntent().getExtras().getString("email");
        final String codeSystem = getIntent().getExtras().getString("code");

        _passwordText = findViewById(R.id.contrasena);
        _confirmPasswordText = findViewById(R.id.confirmar_contrasena);
        _codeText = findViewById(R.id.codigo);
        _changePassword = findViewById(R.id.cambiar_contrasena);

        _changePassword.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String code = _codeText.getText().toString();
                String password = _passwordText.getText().toString();
                String confirmPassword = _confirmPasswordText.getText().toString();

                if(!code.isEmpty() && codeSystem.equals(code)) {
                    if (!password.equals(confirmPassword)) {
                        Toast.makeText(getApplicationContext(), " Passwords do not match ",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        boolean result = true;
                        try {
                            result = new AsyncTaskCall().execute(email, password).get();
                            if (result) {
                                Toast.makeText(getApplicationContext(), " Password changed ",
                                        Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), MenuPrincipal.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        " Could not change password ",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }else{
                    Toast.makeText(getApplicationContext(),
                            " Wrong code ",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        _back = findViewById(R.id.back_to_menu);
        _back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), CodePasswordRequest.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private class AsyncTaskCall extends AsyncTask<String, Void, Boolean> {

        protected void onPreExecute() {
            //showProgress(true);
        }

        protected Boolean doInBackground(String... params) {

            String url = getResources().getString(R.string.url_server);
            System.out.println("url servidor: " + url);
            boolean result = false;
            boolean result2 = false;
            try {
                //TODO: descomentar
                //result = ComunicacionUsuarios.setPassword(url, params[0], params[1]);

                if (result){
                    result2 = ComunicacionUsuarios.iniciarSesion(url, params[0], params[1]);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return result;
        }
    }
}