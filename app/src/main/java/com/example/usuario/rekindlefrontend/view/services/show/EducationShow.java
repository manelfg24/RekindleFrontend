package com.example.usuario.rekindlefrontend.view.services.show;


import android.app.Fragment;
import android.content.DialogInterface;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usuario.rekindlefrontend.R;
import com.example.usuario.rekindlefrontend.data.entity.service.Education;
import com.example.usuario.rekindlefrontend.data.entity.user.User;
import com.example.usuario.rekindlefrontend.data.remote.APIService;
import com.example.usuario.rekindlefrontend.data.remote.APIUtils;
import com.example.usuario.rekindlefrontend.utils.Consistency;
import com.example.usuario.rekindlefrontend.utils.Maps;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Marker;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class EducationShow extends Maps implements OnMapReadyCallback {

    public EducationShow() {
        // Required empty public constructor
    }

    TextView title, description, adress, ambit, requirements, schedule, price, phoneNumber;
    AppCompatButton chat, enroll;

    public Education service;
    public MapFragment mMapView;
    public GoogleMap mGoogleMap;
    public Marker myMarker;
    private APIService mAPIService = APIUtils.getAPIService();
    private final String TYPE = "Education";

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
            Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_mostrar_curso_educativo, container,
                false);

        super.onCreate(savedInstanceState);

        service = (Education) getArguments().getSerializable("servicioFrag");

        title = view.findViewById(R.id.titulo_curso_educativo);
        description = view.findViewById(R.id.descripcion_curso_educativo);
        adress = view.findViewById(R.id.direccion_curso_educativo);
        ambit = view.findViewById(R.id.ambito_curso_educativo);
        requirements = view.findViewById(R.id.requisitos_curso_educativo);
        schedule = view.findViewById(R.id.horario_curso_educativo);
        price = view.findViewById(R.id.precio_curso_educativo);
        mMapView = (MapFragment) getChildFragmentManager().findFragmentById(R.id.google_mapView);
        phoneNumber = view.findViewById(R.id.numero_contacto_servicio);
        chat = view.findViewById(R.id.chat);
        enroll = view.findViewById(R.id.inscribirse);

        title.setText(service.getName());
        description.setText(service.getDescription());
        adress.setText(service.getAdress());
        ambit.setText(service.getAmbit());
        requirements.setText(service.getRequirements());
        schedule.setText(service.getSchedule());
        price.setText(service.getPrice());
        phoneNumber.setText(service.getPhoneNumber());

        mMapView.getMapAsync(this);

        enroll.setClickable(false);

        User user = Consistency.getUser(container.getContext());
        final String mail = user.getMail();
        String type = user.getUserType();

        if (type.equals("Refugee")) {

            mAPIService.isUserSubscribed(mail, service.getId(), TYPE).enqueue(
                    new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            if (response.isSuccessful()) {
                                enroll.setClickable(true);
                                if (response.body()) {
                                    enroll.setText(R.string.unsubscribe);
                                } else {
                                    enroll.setText(R.string.inscribir);
                                }
                            } else {
                                System.out.println("CODIGO " + response.code());
                                failure();
                            }
                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            Log.e("on Failure", t.toString());
                            failure();
                        }
                    });

            enroll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    if (enroll.getText().toString().equals(R.string.inscribir)) {
                        mAPIService.subscribeService(mail,
                                service.getId(), TYPE);
                        enroll.setText(R.string.unsubscribe);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(
                                view.getContext());

                        builder.setMessage(R.string.unsubscribe_confirmation);
                        builder.setCancelable(false);
                        builder.setPositiveButton(R.string.yes,
                                new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog,
                                            int which) {
                                        mAPIService.unsubscribeService(mail, service.getId(),
                                                TYPE);
                                    }
                                });

                        builder.setNegativeButton(R.string.no,
                                new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog,
                                            int which) {
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alert = builder.create();
                        alert.show();
                        enroll.setText(R.string.inscribir);
                    }
                }
            });
        } else {
            enroll.setText(R.string.not_available);
        }

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mGoogleMap = googleMap;

        NetworkInfo network = getNetworkInfo();

        if (network != null && network.isConnectedOrConnecting()) {
            try {
                myMarker = setMarker(service.getAdress(), myMarker, mGoogleMap, service.getName());
            } catch (Exception e) // Conectats però sense internet (p.e. falta logejar-nos)
            {
                Toast.makeText(getActivity().getApplicationContext(), getString(R.string
                        .nointernet), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getActivity().getApplicationContext(), getString(R.string.nomap),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void failure() {
        Toast.makeText(getActivity(), getResources().getString(R
                .string.error), Toast.LENGTH_SHORT).show();
    }
}
