package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

@SuppressWarnings("unchecked")
public class Scripts {

	public static final MongoClient mongoClient = new MongoClient(new ServerAddress("localhost", 27017));
	public static final MongoDatabase db = mongoClient.getDatabase("heymozo");

	public static void main(String[] args) {
		init();
		setearIdImagenAProductosCarta(1);
		cargarImagenes();
		cambiarImagenRestaurante();
		cambiarImagenRestauranteEnCarta(1);
		agregarDescripcionesLargas();
	}

	private static void agregarDescripcionesLargas() {
		String path = "./src/datos/descripciones";

		File carpeta = new File(path);
		File[] descripciones = carpeta.listFiles();
		for(File archivoDescripcion: descripciones){
			if(archivoDescripcion.isFile()){
				String[] nombreDescripcion = archivoDescripcion.getName().split("\\.");
				String descripcion = fileToString(archivoDescripcion.getAbsolutePath());
				int idCarta = Integer.valueOf(nombreDescripcion[0]);
				int idSeccion = Integer.valueOf(nombreDescripcion[1]);
				int idProducto = Integer.valueOf(nombreDescripcion[2]);
				Document carta = db.getCollection("cartas").find(Filters.eq("id", idCarta)).first();
				for(Document seccion: (Collection<Document>) carta.get("secciones")){
					if(idSeccion == (int) seccion.get("id")){
						for(Document producto: (Collection<Document>) seccion.get("productos")){
							if(idProducto == (int) producto.get("id")){
								producto.put("descripcion_larga", descripcion);
							}
						}
					}
				}
				db.getCollection("cartas").replaceOne(Filters.eq("id", idCarta), carta);
			}
		}
	}

	private static void cambiarImagenRestauranteEnCarta(int idCarta) {
		Document carta = db.getCollection("cartas").find(Filters.eq("id", idCarta)).first();
		Document restaurante = (Document) carta.get("restaurante");

		Document imagenDoc = new Document();
		imagenDoc.append("id", idCarta + "");
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
				if(existeImagen(idCarta + "." + idSeccion + "." + idProducto)){
					producto.put("imagen", imagenDoc);
				}
			}
		}
		db.getCollection("cartas").drop();
		db.getCollection("cartas").insertOne(carta);
	}

	public static Boolean existeImagen(String imagenName) {
		File folder = new File("./src/datos/imagenes/");
		File[] listOfFiles = folder.listFiles();
		for(File file: listOfFiles){
			if(file.isFile()){
				String[] filename = file.getName().split("\\.(?=[^\\.]+$)");
				if(filename[0].equalsIgnoreCase(imagenName)){
					return true;
				}
			}
		}
		return false;
	}

	public static void cambiarImagenRestaurante() {
		ArrayList<Document> restaurantes = new ArrayList<>();
		db.getCollection("restaurantes").find().forEach((Document restaurante) -> {
			restaurantes.add(restaurante);

			Document imagenDoc = new Document();
			imagenDoc.append("id", restaurante.get("id") + "");
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

		for(File i: imagenes){

			if(i.isFile()){
				nombreImagen = i.getName().substring(0, i.getName().lastIndexOf('.'));
				FileInputStream imagenInputStream;
				try{
					imagenInputStream = new FileInputStream(i);

					byte imagenByteArray[] = new byte[(int) i.length()];
					imagenInputStream.read(imagenByteArray);

					String imagenBase64 = Base64.getEncoder().encodeToString(imagenByteArray);

					Document imagen = new Document();
					imagen.put("id", nombreImagen);
					imagen.put("imagen", imagenBase64);
					db.getCollection("imagenes").insertOne(imagen);

				} catch(IOException e){
					e.printStackTrace();
				}
			}
		}
	}

	private static String fileToString(String filePath) {
		try{
			byte[] encoded = Files.readAllBytes(Paths.get(filePath));
			return new String(encoded, StandardCharsets.UTF_8);
		} catch(IOException e){
			e.printStackTrace();
		}
		return null;
	}

	private static void init() {
		db.getCollection("restaurantes").drop();
		db.getCollection("restaurantes").insertMany(Document.parse(fileToString(new File("src/datos/restaurantes.json").getAbsolutePath())).get("restaurantes", new ArrayList<Document>().getClass()));

		db.getCollection("cartas").drop();
		db.getCollection("cartas").insertMany(Document.parse(fileToString(new File("src/datos/cartas.json").getAbsolutePath())).get("cartas", new ArrayList<Document>().getClass()));

		db.getCollection("pedidos").drop();
	}
}
