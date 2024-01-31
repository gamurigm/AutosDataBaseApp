
package Model;

import java.util.Comparator;


public class ComparadorAutomoviles implements Comparator<Automovil> {

    @Override
    public int compare(Automovil auto1, Automovil auto2) {
        // Comparar por kilómetro de forma ascendente
        return Integer.compare(auto1.getKm(), auto2.getKm());
    }
}
