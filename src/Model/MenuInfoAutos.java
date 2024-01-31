package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class MenuInfoAutos {

    private final ListaAutos listaAutos;  
    private final ComparadorAutomoviles comparador = new ComparadorAutomoviles();

    public MenuInfoAutos(List<Automovil> autos) {
        this.listaAutos = new ListaAutos(autos);  
    }


    public void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("Menu de Informacion de Autos:");
            System.out.println("1. Informacion del auto con mayor km");
            System.out.println("2. Informacion de autos que superan un kilometraje ingresado");
            System.out.println("3. Informacion de autos de una marca especifica");
            System.out.println("4. Informacion de todos los autos ordenados por kilómetraje");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1:
                    Automovil autoConMayorKm = listaAutos.encontrarMayorKm();
                    if (autoConMayorKm != null) {
                        System.out.println("El automovil con mayor kilometraje es: " + autoConMayorKm);
                    } else {
                        System.out.println("La lista de autos está vacía.");
                    }
                    break;

                case 2:
                    int kmIngresado = obtenerKmIngresado(scanner);
                    List<Automovil> autosSuperanKm = filtrarAutosSuperanKm(listaAutos.getListaAutos(), kmIngresado);

                    Collections.sort(autosSuperanKm, comparador);

                    for (Automovil auto : autosSuperanKm) {
                        System.out.println(auto);
                    }
                    break;

                case 3:
                    System.out.print("Ingrese la marca del auto: ");
                    String marcaIngresada = scanner.nextLine();

                    List<Automovil> autosPorMarca = listaAutos.filtrarAutosPorMarca(marcaIngresada);

                    if (!autosPorMarca.isEmpty()) {
                        System.out.println("Autos de la marca " + marcaIngresada + ":");
                        for (Automovil auto : autosPorMarca) {
                            System.out.println(auto);
                        }
                    } else { System.out.println("No se encontraron autos de la marca " + marcaIngresada + "."); }
                    break;

                case 4:
                    List<Automovil> autosOrdenadosPorKm = listaAutos.getListaAutos();
                    Collections.sort(autosOrdenadosPorKm);
                    
                    System.out.println("Autos en orden de kilometraje ascendente:");
                    for (Automovil auto : autosOrdenadosPorKm) {
                        System.out.println(auto);
                    }
                    break;

                case 5:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
                    break;
            }
        } while (opcion != 5);
    }
    
    private int obtenerKmIngresado(Scanner scanner) {
        System.out.print("Ingrese el kilometraje a comparar: ");
        return Integer.parseInt(scanner.nextLine());
    }

    private List<Automovil> filtrarAutosSuperanKm(List<Automovil> autos, int kmIngresado) {
        // Filtrar los autos que superan el kilometraje
        List<Automovil> autosFiltrados = new ArrayList<>();

        for (Automovil auto : autos) {
            if (auto.getKm() > kmIngresado) {
                autosFiltrados.add(auto);
            }
        }

        return autosFiltrados;
    }
}
