package hu.wob.wobrest.service.consume;

import java.util.Arrays;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ConsumeService<T> {

    private Class<T> typeParameterClass;

    public ConsumeService(Class<T> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
    }

    protected ResponseEntity<T> getResponse(String endpoint) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<T> result = restTemplate.exchange(endpoint, HttpMethod.GET, entity, typeParameterClass);

        return result;
    }

}