package Model;

import java.util.Scanner;

public class Automovil implements Comparable<Automovil> {
    
    private String matricula;
    private String marca;
    private String modelo;
    private int km;

    public Automovil() {
    }

    public Automovil(String matricula, String marca, String modelo, int km) {
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
        this.km = km;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public int getKm() {
        return km;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setKm(int km) {
        this.km = km;
    }
    
    public Automovil ingresarInfoAuto() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese la matricula del Auto: ");
        this.matricula = scanner.nextLine();

        System.out.print("Ingrese la marca del Auto: ");
        this.marca = scanner.nextLine();

        System.out.print("Ingrese el modelo del Auto: ");
        this.modelo = scanner.nextLine();

        while(true){
            try {
                System.out.print("Ingrese la cantidad de Km (entre 1 y 1000): ");
                this.km = Integer.parseInt(scanner.nextLine());
                validarKm(this.km);
                break;  // Salir si la validación es exitosa
                
            } catch (KmFueraDeRangoException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un valor numérico válido.");
            }
        }
        return this;
    } 

    private void validarKm(int km) throws KmFueraDeRangoException {
        if (km < 1 || km > 1000) {
            throw new KmFueraDeRangoException("La cantidad de Km debe estar entre 1 y 1000.");
        }
    }
    
    @Override
    public String toString() {
        return "Automovil{" + "matricula: " + matricula + ", marca: " + marca + ", modelo: " + modelo + ", km: " + km + '}';
    }
    
   @Override
    public int compareTo(Automovil otroAuto) {
        if (this.km == otroAuto.km) {
            return 0;
    } else if (this.km < otroAuto.km) {
        return -1;
    } else {
        return 1;
        }
    }
}
