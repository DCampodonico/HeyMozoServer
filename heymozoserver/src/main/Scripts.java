package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class Scripts {

	public static final MongoClient mongoClient = new MongoClient(new ServerAddress("localhost", 27017));
	public static final MongoDatabase db = mongoClient.getDatabase("heymozo");

	public static void main(String[] args) {
		init();
		agregarImagenesRestaurantes();
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
		String path = "./src/imagenes";

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

	private static void init() {
		db.getCollection("restaurantes").drop();
		db.getCollection("restaurantes").insertOne(Document.parse("{\n" +
				"\t\t            \"id\": 1,\n" +
				"\t\t            \"nombre\": \"Bar-Resto 1980\",\n" +
				"\t\t            \"moneda\": {\n" +
				"\t\t                \"moneda\": \"$\"\n" +
				"\t\t            },\n" +
				"\t\t            \"latitud\": -31.6366443,\n" +
				"\t\t            \"longitud\": -60.699605,\n" +
				"\t\t\t    \"direccion\": \"Blvd. Galvez 2281, S3000ABA Santa Fe\",\n" +
				"\t\t\t    \"telefono\": \"0342 452-0309\",\n" +
				"\t\t\t    \"pagina\": \"https://www.facebook.com/1980boulevard/\",\n" +
				"\t\t\t    \"rating\": 4.1\n" +
				"\t\t        }"));
		db.getCollection("restaurantes").insertOne(Document.parse("{\n" +
				"\t\t            \"id\": 2,\n" +
				"\t\t            \"nombre\": \"Paladar Negro\",\n" +
				"\t\t            \"moneda\": {\n" +
				"\t\t                \"moneda\": \"$\"\n" +
				"\t\t            },\n" +
				"\t\t            \"latitud\": -31.6387288,\n" +
				"\t\t            \"longitud\": -60.6936089,\n" +
				"\t\t\t    \"direccion\": \"Sarmiento 3398, S3000 Santa Fe\",\n" +
				"\t\t\t    \"telefono\": \"0342 456-2868\",\n" +
				"\t\t\t    \"pagina\": \"https://www.facebook.com/paladaroriginal/\",\n" +
				"\t\t\t    \"rating\": 3.9\n" +
				"\t\t        }"));
		db.getCollection("cartas").drop();
		db.getCollection("cartas").insertOne(Document.parse("{\n" +
				"\t\t            \"id\": 1,\n" +
				"\t\t            \"nombre_restaurant\": {\n" +
				"\t\t                \"nombre\": \"Bar-Resto 1980\",\n" +
				"\t\t                \"moneda\": {\n" +
				"\t\t                    \"moneda\": \"$\"\n" +
				"\t\t                }\n" +
				"\t\t            },\n" +
				"\t\t            \"secciones\": [\n" +
				"\t\t                {\n" +
				"\t\t                    \"id\": 1,\n" +
				"\t\t                    \"nombre\": \"Entradas\",\n" +
				"\t\t                    \"productos\": [\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 1,\n" +
				"\t\t                            \"nombre\": \"Empanadas de carne\",\n" +
				"\t\t                            \"precio\": 12\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 2,\n" +
				"\t\t                            \"nombre\": \"Empanadas de verdura\",\n" +
				"\t\t                            \"precio\": 23\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 3,\n" +
				"\t\t                            \"nombre\": \"Empanadas árabes\",\n" +
				"\t\t                            \"precio\": 34\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 4,\n" +
				"\t\t                            \"nombre\": \"Ensalada\",\n" +
				"\t\t                            \"precio\": 38\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 5,\n" +
				"\t\t                            \"nombre\": \"Papas fritas\",\n" +
				"\t\t                            \"precio\": 86\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 6,\n" +
				"\t\t                            \"nombre\": \"Empanadas de carne\",\n" +
				"\t\t                            \"precio\": 12\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 7,\n" +
				"\t\t                            \"nombre\": \"Empanadas de verdura\",\n" +
				"\t\t                            \"precio\": 23\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 8,\n" +
				"\t\t                            \"nombre\": \"Empanadas árabes\",\n" +
				"\t\t                            \"precio\": 34\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 9,\n" +
				"\t\t                            \"nombre\": \"Ensalada\",\n" +
				"\t\t                            \"precio\": 38\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 10,\n" +
				"\t\t                            \"nombre\": \"Papas fritas\",\n" +
				"\t\t                            \"precio\": 86\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 11,\n" +
				"\t\t                            \"nombre\": \"Empanadas de carne\",\n" +
				"\t\t                            \"precio\": 12\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 12,\n" +
				"\t\t                            \"nombre\": \"Empanadas de verdura\",\n" +
				"\t\t                            \"precio\": 23\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 13,\n" +
				"\t\t                            \"nombre\": \"Empanadas árabes\",\n" +
				"\t\t                            \"precio\": 34\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 14,\n" +
				"\t\t                            \"nombre\": \"Ensalada\",\n" +
				"\t\t                            \"precio\": 38\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 15,\n" +
				"\t\t                            \"nombre\": \"Papas fritas\",\n" +
				"\t\t                            \"precio\": 86\n" +
				"\t\t                        }\n" +
				"\t\t                    ]\n" +
				"\t\t                },\n" +
				"\t\t                {\n" +
				"\t\t                    \"id\": 2,\n" +
				"\t\t                    \"nombre\": \"Sandwiches\",\n" +
				"\t\t                    \"productos\": [\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 1,\n" +
				"\t\t                            \"nombre\": \"Sandwiches\",\n" +
				"\t\t                            \"precio\": 12\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 2,\n" +
				"\t\t                            \"nombre\": \"Triples\",\n" +
				"\t\t                            \"precio\": 67\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 3,\n" +
				"\t\t                            \"nombre\": \"Jamon cocido\",\n" +
				"\t\t                            \"precio\": 24\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 4,\n" +
				"\t\t                            \"nombre\": \"Sandwich con ananá\",\n" +
				"\t\t                            \"precio\": 56\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 5,\n" +
				"\t\t                            \"nombre\": \"Sandwiches\",\n" +
				"\t\t                            \"precio\": 65\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 6,\n" +
				"\t\t                            \"nombre\": \"Triples\",\n" +
				"\t\t                            \"precio\": 67\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 7,\n" +
				"\t\t                            \"nombre\": \"Jamón cocido\",\n" +
				"\t\t                            \"precio\": 24\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 8,\n" +
				"\t\t                            \"nombre\": \"Sandwich con ananá\",\n" +
				"\t\t                            \"precio\": 24\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 9,\n" +
				"\t\t                            \"nombre\": \"Sandwiches\",\n" +
				"\t\t                            \"precio\": 14\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 10,\n" +
				"\t\t                            \"nombre\": \"Triples\",\n" +
				"\t\t                            \"precio\": 67\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 11,\n" +
				"\t\t                            \"nombre\": \"Jamón cocido\",\n" +
				"\t\t                            \"precio\": 24\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 12,\n" +
				"\t\t                            \"nombre\": \"Sandwich con ananá\",\n" +
				"\t\t                            \"precio\": 24\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 13,\n" +
				"\t\t                            \"nombre\": \"Sandwiches\",\n" +
				"\t\t                            \"precio\": 12\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 14,\n" +
				"\t\t                            \"nombre\": \"Triples\",\n" +
				"\t\t                            \"precio\": 67\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 15,\n" +
				"\t\t                            \"nombre\": \"Jamón cocido\",\n" +
				"\t\t                            \"precio\": 24\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 16,\n" +
				"\t\t                            \"nombre\": \"Sandwich con ananá\",\n" +
				"\t\t                            \"precio\": 78\n" +
				"\t\t                        }\n" +
				"\t\t                    ]\n" +
				"\t\t                },\n" +
				"\t\t                {\n" +
				"\t\t                    \"id\": 3,\n" +
				"\t\t                    \"nombre\": \"Pizzas\",\n" +
				"\t\t                    \"productos\": [\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 1,\n" +
				"\t\t                            \"nombre\": \"Especial\",\n" +
				"\t\t                            \"precio\": 122\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 2,\n" +
				"\t\t                            \"nombre\": \"Napolitana\",\n" +
				"\t\t                            \"precio\": 190\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 3,\n" +
				"\t\t                            \"nombre\": \"Cebollada\",\n" +
				"\t\t                            \"precio\": 180\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 4,\n" +
				"\t\t                            \"nombre\": \"4 quesos\",\n" +
				"\t\t                            \"precio\": 172\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 5,\n" +
				"\t\t                            \"nombre\": \"Especial\",\n" +
				"\t\t                            \"precio\": 122\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 6,\n" +
				"\t\t                            \"nombre\": \"Napolitana\",\n" +
				"\t\t                            \"precio\": 190\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 7,\n" +
				"\t\t                            \"nombre\": \"Cebollada\",\n" +
				"\t\t                            \"precio\": 180\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 8,\n" +
				"\t\t                            \"nombre\": \"4 quesos\",\n" +
				"\t\t                            \"precio\": 172\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 9,\n" +
				"\t\t                            \"nombre\": \"Especial\",\n" +
				"\t\t                            \"precio\": 122\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 10,\n" +
				"\t\t                            \"nombre\": \"Napolitana\",\n" +
				"\t\t                            \"precio\": 190\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 11,\n" +
				"\t\t                            \"nombre\": \"Cebollada\",\n" +
				"\t\t                            \"precio\": 180\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 12,\n" +
				"\t\t                            \"nombre\": \"4 quesos\",\n" +
				"\t\t                            \"precio\": 172\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 13,\n" +
				"\t\t                            \"nombre\": \"Especial\",\n" +
				"\t\t                            \"precio\": 122\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 14,\n" +
				"\t\t                            \"nombre\": \"Napolitana\",\n" +
				"\t\t                            \"precio\": 190\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 15,\n" +
				"\t\t                            \"nombre\": \"Cebollada\",\n" +
				"\t\t                            \"precio\": 180\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 16,\n" +
				"\t\t                            \"nombre\": \"4 quesos\",\n" +
				"\t\t                            \"precio\": 172\n" +
				"\t\t                        }\n" +
				"\t\t                    ]\n" +
				"\t\t                },\n" +
				"\t\t                {\n" +
				"\t\t                    \"id\": 4,\n" +
				"\t\t                    \"nombre\": \"Postres\",\n" +
				"\t\t                    \"productos\": [\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 1,\n" +
				"\t\t                            \"nombre\": \"Helado\",\n" +
				"\t\t                            \"precio\": 56\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 2,\n" +
				"\t\t                            \"nombre\": \"Torta alemana\",\n" +
				"\t\t                            \"precio\": 56\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 3,\n" +
				"\t\t                            \"nombre\": \"Tiramisú\",\n" +
				"\t\t                            \"precio\": 56\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 4,\n" +
				"\t\t                            \"nombre\": \"Frutas\",\n" +
				"\t\t                            \"precio\": 56\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 5,\n" +
				"\t\t                            \"nombre\": \"Helado\",\n" +
				"\t\t                            \"precio\": 56\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 6,\n" +
				"\t\t                            \"nombre\": \"Torta alemana\",\n" +
				"\t\t                            \"precio\": 56\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 7,\n" +
				"\t\t                            \"nombre\": \"Tiramisú\",\n" +
				"\t\t                            \"precio\": 56\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 8,\n" +
				"\t\t                            \"nombre\": \"Frutas\",\n" +
				"\t\t                            \"precio\": 56\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 9,\n" +
				"\t\t                            \"nombre\": \"Helado\",\n" +
				"\t\t                            \"precio\": 56\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 10,\n" +
				"\t\t                            \"nombre\": \"Torta alemana\",\n" +
				"\t\t                            \"precio\": 56\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 11,\n" +
				"\t\t                            \"nombre\": \"Tiramisú\",\n" +
				"\t\t                            \"precio\": 56\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 12,\n" +
				"\t\t                            \"nombre\": \"Frutas\",\n" +
				"\t\t                            \"precio\": 56\n" +
				"\t\t                        }\n" +
				"\t\t                    ]\n" +
				"\t\t                },\n" +
				"\t\t                {\n" +
				"\t\t                    \"id\": 5,\n" +
				"\t\t                    \"nombre\": \"Bebidas\",\n" +
				"\t\t                    \"productos\": [\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 1,\n" +
				"\t\t                            \"nombre\": \"Fernet\",\n" +
				"\t\t                            \"precio\": 122\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 2,\n" +
				"\t\t                            \"nombre\": \"Gancia\",\n" +
				"\t\t                            \"precio\": 122\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 3,\n" +
				"\t\t                            \"nombre\": \"Whisky\",\n" +
				"\t\t                            \"precio\": 190\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 4,\n" +
				"\t\t                            \"nombre\": \"Martini\",\n" +
				"\t\t                            \"precio\": 142\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 5,\n" +
				"\t\t                            \"nombre\": \"Jarra loca\",\n" +
				"\t\t                            \"precio\": 200\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 6,\n" +
				"\t\t                            \"nombre\": \"Fernet\",\n" +
				"\t\t                            \"precio\": 122\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 7,\n" +
				"\t\t                            \"nombre\": \"Gancia\",\n" +
				"\t\t                            \"precio\": 122\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 8,\n" +
				"\t\t                            \"nombre\": \"Whisky\",\n" +
				"\t\t                            \"precio\": 190\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 9,\n" +
				"\t\t                            \"nombre\": \"Martini\",\n" +
				"\t\t                            \"precio\": 142\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 10,\n" +
				"\t\t                            \"nombre\": \"Jarra loca\",\n" +
				"\t\t                            \"precio\": 200\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 11,\n" +
				"\t\t                            \"nombre\": \"Fernet\",\n" +
				"\t\t                            \"precio\": 122\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 12,\n" +
				"\t\t                            \"nombre\": \"Gancia\",\n" +
				"\t\t                            \"precio\": 122\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 13,\n" +
				"\t\t                            \"nombre\": \"Whisky\",\n" +
				"\t\t                            \"precio\": 190\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 14,\n" +
				"\t\t                            \"nombre\": \"Martini\",\n" +
				"\t\t                            \"precio\": 142\n" +
				"\t\t                        },\n" +
				"\t\t                        {\n" +
				"\t\t                            \"id\": 15,\n" +
				"\t\t                            \"nombre\": \"Jarra loca\",\n" +
				"\t\t                            \"precio\": 200\n" +
				"\t\t                        }\n" +
				"\t\t                    ]\n" +
				"\t\t                }\n" +
				"\t\t            ]\n" +
				"\t\t        }"));
		db.getCollection("pedidos").drop();
	}
}
