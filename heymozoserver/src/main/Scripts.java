package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;

import org.bson.Document;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.util.JSON;

public class Scripts {

	public static final MongoClient mongoClient = new MongoClient(new ServerAddress("localhost", 27017));
	public static final MongoDatabase db = mongoClient.getDatabase("heymozo");

	public static void main(String[] args) {
		init();
		setearIdImagenAProductosCarta(1);
		cargarImagenes();
		cambiarImagenRestaurante();
		cambiarImagenRestauranteEnCarta();
	}

	private static void cambiarImagenRestauranteEnCarta() {
		int i = 1;
		Document carta = db.getCollection("cartas").find(Filters.eq("id", 1)).first();
		Document restaurante = (Document) carta.get("nombre_restaurant");

		Document imagenDoc = new Document();
		imagenDoc.append("id", 1);
		restaurante.append("imagen", imagenDoc);

		db.getCollection("cartas").drop();
		db.getCollection("cartas").insertOne(carta);
	}

	public static void setearIdImagenAProductosCarta(int idCarta) {
		Document carta = db.getCollection("cartas").find(Filters.eq("id", idCarta)).first();
		for(Document seccion: (Collection<Document>) carta.get("secciones")){
			int idSeccion = (int) seccion.get("id");
			for(Document producto: (Collection<Document>) seccion.get("productos")){
				int idProducto = (int) producto.get("id");
				Document imagenDoc = new Document();
				imagenDoc.put("id", idCarta + "." + idSeccion + "." + idProducto);
				producto.put("imagen", imagenDoc);
			}
		}
		db.getCollection("cartas").drop();
		db.getCollection("cartas").insertOne(carta);
	}

	public static void cambiarImagenRestaurante() {
		ArrayList<Document> restaurantes = new ArrayList<>();
		db.getCollection("restaurantes").find().forEach((Document restaurante) -> {
			restaurantes.add(restaurante);

			Document imagenDoc = new Document();
			imagenDoc.append("id", Double.valueOf(restaurante.get("id") + "").intValue());
			restaurante.append("imagen", imagenDoc);
			restaurante.remove("imagen64");
		});
		db.getCollection("restaurantes").drop();
		db.getCollection("restaurantes").insertMany(restaurantes);
	}

	private static void cargarImagenes() {
		db.getCollection("imagenes").drop();	
		String path = "./src/datos/imagenes";

        String nombreImagen;
        File carpeta = new File(path);
        File[] imagenes = carpeta.listFiles();

        for (File i: imagenes){

            if (i.isFile()){
            	nombreImagen = i.getName().substring(0, i.getName().lastIndexOf('.'));
				FileInputStream imagenInputStream;
				try {
					imagenInputStream = new FileInputStream(i);
					
		            byte imagenByteArray[] = new byte[(int) i.length()];
		            imagenInputStream.read(imagenByteArray);
		 
					String imagenBase64 = Base64.getEncoder().encodeToString(imagenByteArray);
					
					Document imagen = new Document();
					imagen.put("id", nombreImagen);
					imagen.put("imagen", imagenBase64);
					db.getCollection("imagenes").insertOne(imagen);
					
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
        }
	}
	
	private static void fileToDB(String filePath, String collection){
		
		JSONParser parser = new JSONParser();
		JSONObject a;
		try {
			a = (JSONObject) parser.parse(new FileReader(filePath));
			
			//for (Object o : a){
				db.getCollection(collection).insertOne(Document.parse(a.toString()));
				//}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void init() {
		db.getCollection("restaurantes").drop();
		fileToDB(new File("src/datos/restaurantes.json").getAbsolutePath(),"restaurantes");
		
		db.getCollection("cartas").drop();
		fileToDB(new File("src/datos/cartas.json").getAbsolutePath(),"cartas");
		
		db.getCollection("pedidos").drop();
	}
}
