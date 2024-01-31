package Model;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class Conexion {
    
    DB baseDatos;
    DBCollection coleccion;
    BasicDBObject documento = new BasicDBObject();
    
    
    public Conexion(){
        MongoClient mongo = new MongoClient("localhost", 27017);
        baseDatos = mongo.getDB("AutosLabDB");
        coleccion = baseDatos.getCollection("automoviles");
        System.out.println("Conexion Exitosa");
    }
    
    public DB getBaseDatos() {
        return this.baseDatos;
    }

    public DBCollection getColeccion() {
        return this.coleccion;
    }
}