package main;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.util.JSON;

public class Main {

	public static void main(String[] args) {
		final MongoClient mongoClient = new MongoClient(new ServerAddress("localhost", 27017));
		final MongoDatabase db = mongoClient.getDatabase("heymozo");

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
			response.status(200);
			db.getCollection("pedidos").insertOne(Document.parse(request.body()));
			return response;
		});
	}

	private static void sendNotification(String firebaseID) throws IOException {
		String url = "https://fcm.googleapis.com/fcm/send";

		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

		//add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Authorization", "key=AAAAfWR4TnA:APA91bGxNTEcsQilBOh2CFKVTceyN-nFasp2_qcClfxMa8ekx4YNV-N9oJWAb-hs-L41H3CIjCoFTTgh9GFcanJ2oLgjxh1cHZrteGSO5qldEYfnqmULsq1mrhvvn1A3JG8wzrN7ann4");

		String urlParameters = "{"
				+ "\"data\":{"
				+ "\"title\":\"Procesando pedido\","
				+ " \"body\":\"Su pedido ha sido aceptado. 5 minutos.\"},"
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

}
