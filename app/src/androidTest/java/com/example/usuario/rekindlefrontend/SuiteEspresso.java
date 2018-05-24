package com.example.usuario.rekindlefrontend;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by ORION on 11/04/2018.
 */

// Runs all unit tests.
@RunWith(Suite.class)
@Suite.SuiteClasses({
                        EspressoCrearServicioAlojamiento.class,
                        EspressoCrearServicioCursoEducativo.class,
                        EspressoCrearServicioDonacion.class,
                        EspressoCrearServicioOfertaEmpleo.class,
                        EspressoEditarServicioAlojamiento.class,
                        EspressoEditarServicioCursoEducativo.class,
                        EspressoEditarServicioDonacion.class,
                        EspressoEditarServicioOfertaEmpleo.class,
                        EspressoLogin.class,
                        EspressoRegistroUsuario.class, // testea registro voluntario y refugiado
                        })
public class SuiteEspresso {}
