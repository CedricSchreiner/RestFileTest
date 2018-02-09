import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClientBuilder;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import javax.ws.rs.client.*;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;

public class Main {

    private Client client;

    public static void main(String[] args) {
        Main test = new Main();
        //test.upload();
        //test.rename();
        //test.delete();
        //test.move();
        //test.deleteDirectory();
        //test.createDirectory();
    }

    private Main() {
        HttpAuthenticationFeature authDetails = HttpAuthenticationFeature.basic("test@test.de", "Passwort1!");
        org.glassfish.jersey.client.ClientConfig config = new org.glassfish.jersey.client.ClientConfig(authDetails);
        client = ClientBuilder.newClient(config);
        client.register(authDetails);
    }

    private void upload() {
        File file = new File("C:/Users/Cedric/Desktop/testfilme.txt");
        String BASE_URL="http://localhost:8080/api/files/upload";
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost postRequest = new HttpPost(BASE_URL);


        try
        {
            //Set various attributes
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();

            FileBody fileBody = new FileBody(file, "application/octect-stream") ;
            //Prepare payload
            builder.addPart("attachment", fileBody);
            builder.addTextBody("attachment", "test/nochmal/egal/testfilme.txt");
            builder.addTextBody("attachment", "test@test.de|Passwort1!|User|0|1|-1");
            //multiPartEntity.addPart("attachment", fileBody) ;

            //Set to request body
            //postRequest.setEntity(multiPartEntity) ;
            postRequest.setEntity(builder.build());

            //Send request
            HttpResponse response = client.execute(postRequest) ;

            //Verify response if any
            if (response != null)
            {
                System.out.println("upload: " + response.getStatusLine().getStatusCode());
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace() ;
        }
    }

    private void rename() {
        WebTarget webTarget = client.target("http://localhost:8080/api/files/rename")
                .queryParam("path", "test\\nochmal\\egal\\testfilme.txt");
        Invocation.Builder builder = webTarget.request(MediaType.TEXT_PLAIN);
        Response r = builder.post(Entity.entity("test.txt", MediaType.TEXT_PLAIN));
        System.out.println("rename: " + r.getStatus());
    }

    private void delete() {
        WebTarget webTarget = client.target("http://localhost:8080/api/files/delete");
        Invocation.Builder builder = webTarget.request(MediaType.TEXT_PLAIN);
        Response r = builder.post(Entity.entity("test\\nochmal\\egal", MediaType.TEXT_PLAIN));
        System.out.println("delete: " + r.getStatus());
    }

    private void move() {
        WebTarget webTarget = client.target("http://localhost:8080/api/files/move")
                .queryParam("path", "test\\nochmal\\egal\\testfilme.txt");
        Invocation.Builder builder = webTarget.request(MediaType.TEXT_PLAIN);
        Response r = builder.post(Entity.entity("test\\nochmal", MediaType.TEXT_PLAIN));
        System.out.println("move: " + r.getStatus());
    }

    private void deleteDirectory() {
        WebTarget webTarget = client.target("http://localhost:8080/api/files/removeDirectoryOnly");
        Invocation.Builder builder = webTarget.request(MediaType.TEXT_PLAIN);
        Response r = builder.post(Entity.entity("test\\nochmal", MediaType.TEXT_PLAIN));
        System.out.println("delete Directory only: " + r.getStatus());
    }

    private void createDirectory() {
        WebTarget webTarget = client.target("http://localhost:8080/api/files/createDirectory");
        Invocation.Builder builder = webTarget.request(MediaType.TEXT_PLAIN);
        Response r = builder.post(Entity.entity("test\\nochmal\\neu", MediaType.TEXT_PLAIN));
        System.out.println("create Directory: " + r.getStatus());
    }
}