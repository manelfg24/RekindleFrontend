package com.example.usuario.rekindlefrontend.view.menu.mainMenu;

import static com.example.usuario.rekindlefrontend.utils.Consistency.getUser;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.usuario.rekindlefrontend.AppBaseActivity;
import com.example.usuario.rekindlefrontend.R;
import com.example.usuario.rekindlefrontend.data.entity.user.User;
import com.example.usuario.rekindlefrontend.view.menu.login.Login;

import java.util.HashMap;

public class MainMenu extends AppBaseActivity {

    HashMap<String, Fragment> mainMenuTypes;
    private int backpress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        getSupportActionBar().setTitle(R.string.titulo_menu_principal);

        mainMenuTypes = new HashMap<>();


        mainMenuTypes.put("Refugee", new MainMenuRefugee());
        mainMenuTypes.put("Volunteer", new MainMenuVolunteer());
        mainMenuTypes.put("Admin", new MainMenuAdmin());

        User user = getUser(this);

        System.out.println(user.toString());

        String userType = user.getUserType();

        System.out.println("tipo: " + userType);

        setMainMenuType(userType);

    }

    public void setMainMenuType(String userType) {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.tipo_menu_principal, mainMenuTypes.get(userType));
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        backpress = (backpress + 1);
        Toast.makeText(getApplicationContext(), getString(R.string.back_exit),
                Toast.LENGTH_SHORT).show();

        if (backpress > 1) {
            moveTaskToBack(true);
        }
    }

    @Override
    protected void gotoLaunch() {
        Intent i = new Intent(MainMenu.this, Login.class);
        startActivity(i);
    }
}
