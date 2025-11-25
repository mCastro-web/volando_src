package ui.utils;

import java.util.List;
import java.util.Map;


public class DatosPrueba {

    // Aerolíneas
    public static final List<String> AEROLINEAS = List.of(
            "Aerolínea A", "Aerolínea B", "Aerolínea C"
    );

    // Rutas por aerolínea
    public static final Map<String, List<String>> RUTAS_POR_AEROLINEA = Map.of(
            "Aerolínea A", List.of("Ruta 1", "Ruta 2"),
            "Aerolínea B", List.of("Ruta 3"),
            "Aerolínea C", List.of("Ruta 4", "Ruta 5", "Ruta 6")
    );

    // Vuelos por ruta
    public static final Map<String, List<String>> VUELOS_POR_RUTA = Map.of(
            "Ruta 1", List.of("Vuelo 101", "Vuelo 102"),
            "Ruta 2", List.of("Vuelo 103"),
            "Ruta 3", List.of("Vuelo 201", "Vuelo 202"),
            "Ruta 4", List.of("Vuelo 301"),
            "Ruta 5", List.of("Vuelo 302", "Vuelo 303"),
            "Ruta 6", List.of("Vuelo 304")
    );

    // Clientes
    public static final List<String> CLIENTES = List.of(
            "Juan Pérez", "María López", "Carlos González"
    );

    // Tipos de asiento
    public static final List<String> TIPOS_ASIENTO = List.of(
            "Turista", "Ejecutivo"
    );

    // Paquetes
    public static final List<String> PAQUETES = List.of(
            "Paquete Familiar", "Paquete Ejecutivo", "Paquete Estudiantil"
    );


  /*public static void cargarPaquetesDePrueba() {
        Sistema sistema = Sistema.getInstance();
        ManejadorPaqueteVuelo mp = sistema.getManejadorPaqueteVuelo();

        // Paquete 1
        List<DtItemPaquete> items1 = new ArrayList<>();
        items1.add(new DtItemPaquete(
                2, TURISTA,
                new DtRutaVuelo("Ruta 1", "Descripción Ruta 1", LocalDate.now(), 50f, 100f, 50f, null, null, null, null, null),
                null
        ));
        items1.add(new DtItemPaquete(
                1, EJECUTIVO,
                new DtRutaVuelo("Ruta 2", "Descripción Ruta 2", LocalDate.now(), 70f, 2000f, 80f, null, null, null, null, null),
                null
        ));
        DtPaqueteVuelo paquete1 = new DtPaqueteVuelo(
                "Paquete A", "Paquete turístico", 10, 5f, 500f,
                LocalDate.now(), items1, new ArrayList<>()
        );
        mp.guardarPaquete(paquete1); // <-- método en tu manejador para agregar paquetes

        // Paquete 2
        List<DtItemPaquete> items2 = new ArrayList<>();
        items2.add(new DtItemPaquete(
                3, TURISTA,
                new DtRutaVuelo("Ruta 3", "Descripción Ruta 3", LocalDate.now(),  70f, 2000f, 80f, null, null, null, null, null),
                null
        ));
        DtPaqueteVuelo paquete2 = new DtPaqueteVuelo(
                "Paquete B", "Paquete ejecutivo", 7, 10f, 700f,
                LocalDate.now(), items2, new ArrayList<>()
        );
        mp.guardarPaquete(paquete2);

        System.out.println("Paquetes de prueba cargados.");
    }*/
}
