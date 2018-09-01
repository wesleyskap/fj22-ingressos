package br.com.caelum.ingresso.rest;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import br.com.caelum.ingresso.model.Filme;

@Component
public class ImdbClient {
	
	
	public <T> Optional<T> request(Filme filme, Class<T> clazz){
		RestTemplate client = new RestTemplate();
		String titulo = filme.getNome().replaceAll(" ", "+");
		String url = String.format("https://imdb-fj22.herokuapp.com/imdb?title=%s", titulo);
		try{
			return Optional.of(client.getForObject(url, clazz));
		}catch(RestClientException e){
			return Optional.empty();
		}
	}

}
