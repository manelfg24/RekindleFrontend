package com.example.usuario.rekindlefrontend.view.usuarios;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.usuario.rekindlefrontend.comunicacion.ComunicacionUsuarios;
import com.example.usuario.rekindlefrontend.R;
import com.example.usuario.rekindlefrontend.data.entity.Refugiado;
import com.example.usuario.rekindlefrontend.data.remote.APIService;
import com.example.usuario.rekindlefrontend.data.remote.APIUtils;
import com.example.usuario.rekindlefrontend.utils.AbstractFormatChecker;
import com.example.usuario.rekindlefrontend.view.CambiarPassword;
import com.example.usuario.rekindlefrontend.view.menu.PantallaInicio;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ORION on 27/04/2018.
 */

public class EditarPerfilRefugiado extends AbstractFormatChecker{

    private Refugiado refugiado;
    ArrayAdapter<CharSequence> adapter1, adapter2, adapter3;

    private EditText eNombre;
    private EditText eEmail;
    private EditText ePrimer_apellido;
    private EditText eSegundo_apellido;
    private EditText eTelefono;
    private EditText eNacimiento;
    private Spinner sSexo;
    private EditText eProcedencia;
    private EditText ePueblo;
    private EditText eEtnia;
    private Spinner sGrupo_sanguineo;
    private Spinner sOjos;


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_editar_perfil_refugiado, container,
                false);
        try {

            setVistas(view);

            refugiado = new EditarPerfilRefugiado.AsyncTaskCall().execute().get();

            initializeData(view);

        }catch (Exception e){

            e.printStackTrace();
        }

        AppCompatButton b = (AppCompatButton) view.findViewById(R.id.guardar_editar_perfil);
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                try{
                    checkCampos(view);
                }
                catch (Exception e){
                    Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
//TODO: llamar para editar el perfil con los nuevos datos
            }

        });

        AppCompatButton b2 = (AppCompatButton) view.findViewById(R.id.cambiar_password);
        b2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(), CambiarPassword.class);
                i.putExtra("Refugiado", refugiado);
                startActivity(i);
            }

        });

        return view;
    }

    public void setVistas(View view) {

        eNombre = view.findViewById(R.id.nombre_usuario_perfil);
        eEmail = view.findViewById(R.id.email_usuario_perfil);
        ePrimer_apellido = view.findViewById(R.id.apellido1_usuario_perfil);
        eSegundo_apellido = view.findViewById(R.id.apellido2_usuario_perfil);
        eTelefono = view.findViewById(R.id.telefono_usuario_perfil);
        eNacimiento = view.findViewById(R.id.naciminento_usuario_perfil);
        sSexo = view.findViewById(R.id.sexo_usuario_perfil);
        ArrayAdapter<CharSequence> adapter_sexo = ArrayAdapter.createFromResource(getActivity()
                .getApplicationContext(), R.array.lista_sexo, R.layout.spinner_item);

        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_sexo.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);

        sSexo.setAdapter(adapter_sexo);
        eProcedencia = view.findViewById(R.id.pais_usuario_perfil);
        ePueblo = view.findViewById(R.id.pueblo_usuario_perfil);
        eEtnia = view.findViewById(R.id.etnia_usuario_perfil);

        sGrupo_sanguineo = view.findViewById(R.id.sangre_usuario_perfil);

        ArrayAdapter<CharSequence> adapter_gs = ArrayAdapter.createFromResource(getActivity()
                .getApplicationContext(), R.array.lista_grupo_sanguineo, R.layout.spinner_item);

        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_gs.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);

        sGrupo_sanguineo.setAdapter(adapter_gs);

        sOjos = view.findViewById(R.id.ojos_usuario_perfil);

        ArrayAdapter<CharSequence> adapter_ojos = ArrayAdapter.createFromResource(getActivity()
                .getApplicationContext(), R.array.lista_color_ojos, R.layout.spinner_item);

        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_ojos.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);

        sOjos.setAdapter(adapter_ojos);

    }


    public void initializeData(View view){

        eNombre.setText(refugiado.getName());
        eEmail.setText(refugiado.getMail());
        ePrimer_apellido.setText(refugiado.getSurname1());
        eSegundo_apellido.setText(refugiado.getSurname2());
        eTelefono.setText(refugiado.getPhoneNumber());
        eNacimiento.setText(refugiado.getBirthDate());

        int selectionPosition = adapter1.getPosition(refugiado.getSex());
        sSexo.setSelection(selectionPosition);

        eProcedencia.setText(refugiado.getCountry());
        ePueblo.setText(refugiado.getTown());
        eEtnia.setText(refugiado.getEthnic());

        selectionPosition = adapter2.getPosition(refugiado.getBloodType());
        sGrupo_sanguineo.setSelection(selectionPosition);

        selectionPosition = adapter3.getPosition(refugiado.getEyeColor());
        sOjos.setSelection(selectionPosition);
    }

    public void checkCampos(View view) throws Exception {

        checkNombre(eNombre.getText().toString());
        checkEmail(eEmail.getText().toString());
        checkPrimer_apellido(ePrimer_apellido.getText().toString());
        checkSegundo_apellido(eSegundo_apellido.getText().toString());
        checkTelefono(eTelefono.getText().toString());
        checkProcedencia(eProcedencia.getText().toString());
        checkPueblo(ePueblo.getText().toString());
        checkEtnia(eEtnia.getText().toString());
    }

    private class AsyncTaskCall extends AsyncTask<String, Void, Refugiado> {

        protected void onPreExecute() {
            //showProgress(true);
        }

        protected Refugiado doInBackground(String... urls) {

            String url = getResources().getString(R.string.url_server);
            System.out.println("url servidor: " + url);
            Refugiado result = new Refugiado();
            try {
                SharedPreferences datos = PreferenceManager.getDefaultSharedPreferences
                        (getActivity().getApplicationContext());
                String param = datos.getString("email", "email");
//TODO: Es esto correcto?
                result = ComunicacionUsuarios.verPerfilRefugiado(url, param);
            } catch (Exception e) {

                e.printStackTrace();
            }

            return result;
        }
    }

}
