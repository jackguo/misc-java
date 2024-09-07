
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * An example of exhausting default connection pool
 */
public class ApacheHttpClientConnPool {

  public static void main(String[] args) throws Exception {
    final HttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(RequestConfig.custom()
            .setConnectionRequestTimeout(2000).setSocketTimeout(2000).build()).build();

    for (int i = 0; i < 10; ++i) {
      HttpResponse response
              = httpClient.execute(new HttpGet("https://dl.fedoraproject.org/pub/epel/10/COMPOSE_ID"));

      System.out.println(response.getStatusLine().getStatusCode());
      /* reading the response body and close the InputStream would also release the connection back to pool
      HttpEntity entity = response.getEntity();
      if (entity != null) {
        InputStream in = entity.getContent();
        System.out.println(IOUtils.toString(in, StandardCharsets.UTF_8));
        in.close();
      }
      */
    }
  }
}
