package Model;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.WriteResult;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AutomovilDAO {
    private DBCollection coleccion;

    public AutomovilDAO() {
        Conexion objCon = new Conexion();
        this.coleccion = objCon.getColeccion();
    }

    public void insertarAutomovil(Automovil automovil) throws KmFueraDeRangoException {
         if (automovil.getKm() < 1 || automovil.getKm() > 1000) {
            throw new KmFueraDeRangoException("La cantidad de kilómetros debe estar entre 1 y 1000 km.");
        }
        BasicDBObject documento = new BasicDBObject();
        documento.put("matricula", automovil.getMatricula());
        documento.put("marca", automovil.getMarca());
        documento.put("modelo", automovil.getModelo());
        documento.put("km", automovil.getKm());
        coleccion.insert(documento);
    }

    public void eliminarAutomovil(String matricula) {
        BasicDBObject documento = new BasicDBObject();
        documento.put("matricula", matricula);
        coleccion.remove(documento);
    }

    public void modificarAutomovil(String matricula, String nuevaMarca, String nuevoModelo, int nuevoKm) {
        BasicDBObject filtro = new BasicDBObject("matricula", matricula);
        BasicDBObject actualizacion = new BasicDBObject();
        actualizacion.put("$set", new BasicDBObject("marca", nuevaMarca).append("modelo", nuevoModelo).append("km", nuevoKm));

        WriteResult result = coleccion.update(filtro, actualizacion);

        if (result.getN() > 0) {
            System.out.println("Automóvil modificado exitosamente");
        } else {
            System.out.println("Error al modificar automóvil. Puede que el automóvil no exista.");
        }
    }

    public ArrayList<Automovil> obtenerAutomoviles() {
        ArrayList<Automovil> listaAutomoviles = new ArrayList<>();
        Conexion objCon = new Conexion();
        DBCursor cursor = objCon.coleccion.find();

        while (cursor.hasNext()) {
            BasicDBObject documento = (BasicDBObject) cursor.next();
            Automovil automovil = new Automovil(
                    documento.getString("matricula"),
                    documento.getString("marca"),
                    documento.getString("modelo"),
                    documento.getInt("km")
            );
            listaAutomoviles.add(automovil);
        }

        return listaAutomoviles;
    }

    // Nuevo método para obtener automóviles ordenados por kilómetro
    public List<Automovil> obtenerAutomovilesOrdenadosPorKm() {
        List<Automovil> listaAutomoviles = obtenerAutomoviles();
        Collections.sort(listaAutomoviles, new ComparadorAutomoviles());
        return listaAutomoviles;
    }

    // Nuevo método para filtrar autos por marca
    public List<Automovil> filtrarAutosPorMarca(String marca) {
        ArrayList<Automovil> listaFiltrada = new ArrayList<>();
        ArrayList<Automovil> listaAutomoviles = obtenerAutomoviles();

        for (Automovil auto : listaAutomoviles) {
            if (auto.getMarca().equalsIgnoreCase(marca)) {
                listaFiltrada.add(auto);
            }
        }

        return listaFiltrada;
    }

    // Nuevo método para encontrar el automóvil con mayor kilometraje
    public Automovil encontrarMayorKm() {
        List<Automovil> listaAutomoviles = obtenerAutomovilesOrdenadosPorKm();
        if (listaAutomoviles.isEmpty()) {
            return null;
        }
        return listaAutomoviles.get(listaAutomoviles.size() - 1);
    }
    
   

    public List<Automovil> obtenerAutosSuperanKm(int kmIngresado) {
        List<Automovil> autosSuperanKm = new ArrayList<>();
        for (Automovil auto : obtenerAutomoviles()) {
            if (auto.getKm() > kmIngresado) {
                autosSuperanKm.add(auto);
            }
        }
        return autosSuperanKm;
    }

    public List<Automovil> obtenerAutosPorMarca(String marca) {
        List<Automovil> autosMarcaEspecifica = new ArrayList<>();
        for (Automovil auto : obtenerAutomoviles()) {
            if (auto.getMarca().equalsIgnoreCase(marca)) {
                autosMarcaEspecifica.add(auto);
            }
        }
        return autosMarcaEspecifica;
    }
}
