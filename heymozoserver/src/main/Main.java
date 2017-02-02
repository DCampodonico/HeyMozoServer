package main;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Date;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.util.JSON;

public class Main {

	public static final MongoClient mongoClient = new MongoClient(new ServerAddress("localhost", 27017));
	public static final MongoDatabase db = mongoClient.getDatabase("heymozo");
	public static final Random ran = new Random(new Date().getTime());

	public enum EstadoPedido {
		Procesando, Preparando, Sirviendo, Adeudado, Terminado, Cancelado
	}

	public static void main(String[] args) {
		try{
			sendNotification("f99Kgx8_0T0:APA91bFAAmN5gjn0BBaAydhuBbi4HL6jrNmb5twlw3HqXDMUzWAFMm3HRzHPg1KSjdzjpXofO7KyFfCK4d3SLD-nhyafArb7lsw7EyBV8r7CFfJA0h1fxMMdWc8UJPt6IkzkS6bpxbfj");
		} catch(IOException e){
			e.printStackTrace();
		}

		port(3000);
		get("/restaurantes", (request, response) -> {
			response.status(200);
			return JSON.serialize(db.getCollection("restaurantes").find());
		});

		get("/cartas/:id", (request, response) -> {
			response.status(200);
			return JSON.serialize(db.getCollection("cartas").find(Filters.eq("id", new Integer(request.params(":id")))).first());
		});

		get("/pedidos/:uid", (request, response) -> {
			response.status(200);
			return JSON.serialize(db.getCollection("pedidos").find(Filters.eq("usuario_id", request.params(":uid"))));
		});

		post("/pedido", (request, response) -> {
			//Guardar pedido
			response.status(200);
			db.getCollection("pedidos").insertOne(Document.parse(request.body()));

			//Esperar un tiempo y simular una atención
			final String pedido = request.body();
			new Thread(() -> {
				try{
					Thread.sleep(1000);
				} catch(InterruptedException e){
				}
				if(ran.nextDouble() < 0.5){
					procesarPedido(Document.parse(pedido));
				}
				else{
					cancelarPedido(Document.parse(pedido));
				}
			}).start();

			return response;
		});
	}

	private static void procesarPedido(Document pedidoDoc) {
		String usuarioId = "usuario_id";
		String fechaPedido = "fecha_pedido";
		for(Object o: pedidoDoc.keySet()){
			if(o.equals(usuarioId)){
				usuarioId = (String) o;
			}
			if(o.equals(fechaPedido)){
				fechaPedido = (String) o;
			}
		}
		Bson filtro = Filters.and(Filters.eq("usuario_id", pedidoDoc.get(usuarioId)), Filters.eq("fecha_pedido", pedidoDoc.get(fechaPedido)));
		db.getCollection("pedidos").updateOne(filtro, new Document("$set", new Document("estado", EstadoPedido.Procesando.toString())));
		final Long futuro = 120000L;
		db.getCollection("pedidos").updateOne(filtro, new Document("$set", new Document("finaliza", new Long(new Date().getTime() + futuro))));
		try{
			sendNotification(pedidoDoc.getString(usuarioId), "Procesando pedido", "Su pedido ha sido aceptado y estara listo en unos minutos.");
		} catch(IOException e1){
			e1.printStackTrace();
		}

		//Esperar un tiempo y simular una atención
		new Thread(() -> {
			try{
				Thread.sleep(futuro + 5000);
			} catch(InterruptedException e){
			}
			servirPedido(pedidoDoc);
		}).start();
	}

	private static void cancelarPedido(Document pedidoDoc) {
		String usuarioId = "usuario_id";
		String fechaPedido = "fecha_pedido";
		for(Object o: pedidoDoc.keySet()){
			if(o.equals(usuarioId)){
				usuarioId = (String) o;
			}
			if(o.equals(fechaPedido)){
				fechaPedido = (String) o;
			}
		}
		Bson filtro = Filters.and(Filters.eq("usuario_id", pedidoDoc.get(usuarioId)), Filters.eq("fecha_pedido", pedidoDoc.get(fechaPedido)));
		db.getCollection("pedidos").updateOne(filtro, new Document("$set", new Document("estado", EstadoPedido.Cancelado.toString())));
		db.getCollection("pedidos").updateOne(filtro, new Document("$unset", new Document("finaliza", "")));
		try{
			sendNotification(pedidoDoc.get(usuarioId).toString(), "Pedido cancelado", "Su pedido ha sido cancelado.");
		} catch(IOException e1){
			e1.printStackTrace();
		}
	}

	private static void servirPedido(Document pedidoDoc) {
		String usuarioId = "usuario_id";
		String fechaPedido = "fecha_pedido";
		for(Object o: pedidoDoc.keySet()){
			if(o.equals(usuarioId)){
				usuarioId = (String) o;
			}
			if(o.equals(fechaPedido)){
				fechaPedido = (String) o;
			}
		}
		Bson filtro = Filters.and(Filters.eq("usuario_id", pedidoDoc.get(usuarioId)), Filters.eq("fecha_pedido", pedidoDoc.get(fechaPedido)));
		db.getCollection("pedidos").updateOne(filtro, new Document("$set", new Document("estado", EstadoPedido.Sirviendo.toString())));
		db.getCollection("pedidos").updateOne(filtro, new Document("$unset", new Document("finaliza", "")));
		try{
			sendNotification(pedidoDoc.get(usuarioId).toString(), "Sirviendo pedido", "¡Su pedido esta por llegar a su mesa!.");
		} catch(IOException e1){
			e1.printStackTrace();
		}

		//Esperar un tiempo y simular una atención
		new Thread(() -> {
			try{
				Thread.sleep(60000);
			} catch(InterruptedException e){
			}
			adeudarPedido(pedidoDoc);
		}).start();
	}

	private static void adeudarPedido(Document pedidoDoc) {
		String usuarioId = "usuario_id";
		String fechaPedido = "fecha_pedido";
		for(Object o: pedidoDoc.keySet()){
			if(o.equals(usuarioId)){
				usuarioId = (String) o;
			}
			if(o.equals(fechaPedido)){
				fechaPedido = (String) o;
			}
		}
		Bson filtro = Filters.and(Filters.eq("usuario_id", pedidoDoc.get(usuarioId)), Filters.eq("fecha_pedido", pedidoDoc.get(fechaPedido)));
		db.getCollection("pedidos").updateOne(filtro, new Document("$set", new Document("estado", EstadoPedido.Adeudado.toString())));
		try{
			sendNotification(pedidoDoc.get(usuarioId).toString(), "Disfrute su pedido", "¡Bon Appetit!.");
		} catch(IOException e1){
			e1.printStackTrace();
		}

		//Esperar un tiempo y simular una atención
		new Thread(() -> {
			try{
				Thread.sleep(60000);
			} catch(InterruptedException e){
			}
			finalizarPedido(pedidoDoc);
		}).start();
	}

	private static void finalizarPedido(Document pedidoDoc) {
		String usuarioId = "usuario_id";
		String fechaPedido = "fecha_pedido";
		for(Object o: pedidoDoc.keySet()){
			if(o.equals(usuarioId)){
				usuarioId = (String) o;
			}
			if(o.equals(fechaPedido)){
				fechaPedido = (String) o;
			}
		}
		Bson filtro = Filters.and(Filters.eq("usuario_id", pedidoDoc.get(usuarioId)), Filters.eq("fecha_pedido", pedidoDoc.get(fechaPedido)));
		db.getCollection("pedidos").updateOne(filtro, new Document("$set", new Document("estado", EstadoPedido.Terminado.toString())));
		try{
			sendNotification(pedidoDoc.get(usuarioId).toString(), "Gracias por elegirnos!", "Su pago ha sido procesado correctamente.");
		} catch(IOException e1){
			e1.printStackTrace();
		}
	}

	private static void sendNotification(String firebaseID, String titulo, String cuerpo) throws IOException {
		String url = "https://fcm.googleapis.com/fcm/send";

		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

		//add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Authorization", "key=AAAAfWR4TnA:APA91bGxNTEcsQilBOh2CFKVTceyN-nFasp2_qcClfxMa8ekx4YNV-N9oJWAb-hs-L41H3CIjCoFTTgh9GFcanJ2oLgjxh1cHZrteGSO5qldEYfnqmULsq1mrhvvn1A3JG8wzrN7ann4");

		String urlParameters = "{"
				+ "\"data\":{"
				+ "\"title\":\"" + titulo + "\","
				+ " \"body\":\"" + cuerpo + "\"},"
				+ "\"registration_ids\": [\"" + firebaseID + "\"]"
				+ "}";

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while((inputLine = in.readLine()) != null){
			response.append(inputLine);
		}
		in.close();

		//print result
		System.out.println(response.toString());
	}

	private static void sendNotification(String firebaseID) throws IOException {
		sendNotification(firebaseID, "Inicio correcto", "Servidor de notificaciones inicializado correctamente.");
	}

}
