package http;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class Rest {

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/test";
        UriComponentsBuilder builder =UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("id", "39");

        ResponseEntity<String> exchange = restTemplate.exchange(builder.build().toUri(), HttpMethod.GET, null, String.class);
        System.out.println(exchange);
    }
}
