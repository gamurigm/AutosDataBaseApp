package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ListaAutos {
    
    private List <Automovil> listaAutos;

    public ListaAutos(List<Automovil> listaAutos) {
        this.listaAutos = listaAutos;
    }

    public List<Automovil> getListaAutos() {
        return listaAutos;
    }

    public void setListaAutos(List<Automovil> listaAutos) {
        this.listaAutos = listaAutos;
    }

    @Override
    public String toString() {
        return "ListaAutos{" + "listaAutos: " + listaAutos + '}';
    }
    
    public void mostrarLista(){
        Iterator it =listaAutos.iterator();
        while(it.hasNext()){
            Automovil a1 = (Automovil)it.next();
            System.out.println(a1.toString());
        }
        System.out.println();
    }  
    
     public Automovil encontrarMayorKm() {
    if (listaAutos == null || listaAutos.isEmpty()) {
        return null;
    }

    return Collections.max(listaAutos);
    }
     
    public List<Automovil> filtrarAutosPorMarca(String marca) {
        List<Automovil> autosFiltrados = new ArrayList<>();

        for (Automovil auto : listaAutos) {
            if (auto.getMarca().equalsIgnoreCase(marca)) {
                autosFiltrados.add(auto);
            }
        }
        
        return autosFiltrados;
    } 
}
    

