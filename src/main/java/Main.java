
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        new Main().start();
    }

    private Main() {}

    private void start() {
        File file = new File("C:\\Users\\Cedric\\Desktop\\testfilme.txt");
        String BASE_URL="http://localhost:8080/server/api/files/upload";
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost postRequest = new HttpPost(BASE_URL);
        try
        {

            //Set various attributes
            //MultipartEntity multiPartEntity = new MultipartEntity () ;
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();

            FileBody fileBody = new FileBody(file, "application/octect-stream") ;
            //Prepare payload
            builder.addPart("attachment", fileBody);
            builder.addTextBody("attachment", "cedric.schreiner@gmail.com\\testfilme.txt");
            //multiPartEntity.addPart("attachment", fileBody) ;

            //Set to request body
            //postRequest.setEntity(multiPartEntity) ;
            postRequest.setEntity(builder.build());

            //Send request
            HttpResponse response = client.execute(postRequest) ;

            //Verify response if any
            if (response != null)
            {
                System.out.println(response.getStatusLine().getStatusCode());
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace() ;
        }
    }
}
