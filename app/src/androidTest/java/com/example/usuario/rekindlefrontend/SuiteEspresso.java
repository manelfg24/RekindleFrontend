package com.example.usuario.rekindlefrontend;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by ORION on 11/04/2018.
 */

// Runs all unit tests.
@RunWith(Suite.class)
@Suite.SuiteClasses({

        EspressoCrearServiceLodge.class,
        EspressoCrearServiceEducation.class,
        EspressoCrearServiceDonation.class,
        EspressoCrearServiceJob.class,
        EspressoEditarServiceLodge.class,
        EspressoEditarServiceEducation.class,
        EspressoEditarServiceDonation.class,
        EspressoEditarServiceJob.class,
        EspressoLogin.class,
        EspressoRegistroUser.class, // testea registro voluntario y refugiado
})
public class SuiteEspresso {
}
