package com.example.usuario.rekindlefrontend;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.init;
import static android.support.test.espresso.intent.Intents.release;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;

import com.example.usuario.rekindlefrontend.view.menu.menuPrincipal.MenuPrincipal;
import com.example.usuario.rekindlefrontend.view.servicios.crear.CrearServicio;
import com.example.usuario.rekindlefrontend.view.servicios.listar.ListarServicios;
import com.example.usuario.rekindlefrontend.view.usuarios.verPerfil.VerPerfilRefugiado;


@RunWith(AndroidJUnit4.class)
public class EspressoMenuPrincipal {

    @Rule
    public ActivityTestRule<MenuPrincipal> pantalla = new ActivityTestRule<MenuPrincipal>
            (MenuPrincipal.class);

    @BeforeClass
    public static void setup(){
        init();
    }

    @AfterClass
    public static void end(){
        release();
    }

//    @Test
//    public void testListarServicios() {
//
//        onView(withId(R.id.listar_servicios_MenuPrincipal)).perform(click());
//        intended(hasComponent(ListarServicios.class.getName()));
//
//    }
//
//    @Test
//    public void testBotonCrearServicios(){
//
//        onView(withId(R.id.crear_servicio_MenuPrincipal)).perform(click());
//        intended(hasComponent(CrearServicio.class.getName()));
//    }
//
//    @Test
//    public void testBotonVerPerfil(){
//
//        onView(withId(R.id.ver_perfil_MenuPrincipal)).perform(click());
//        intended(hasComponent(VerPerfilRefugiado.class.getName()));
//    }

    @Test
    public void testAtras(){

        //TODO: Testear cuando tengamos boton atras

    }

}
