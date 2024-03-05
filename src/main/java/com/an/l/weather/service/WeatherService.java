package com.an.l.weather.service;

import com.an.l.weather.constants.Constants;
import com.an.l.weather.dto.WeatherDto;
import com.an.l.weather.dto.WeatherResponse;
import com.an.l.weather.model.WeatherEntity;
import com.an.l.weather.repository.WeatherRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class WeatherService {

//    private static final String API_URL ="http://api.weatherstack.com/current?access_key=a36f86657a2da49a3d0075641e0d61c4&query="; BUNA GEREK KALAMDI
    //BUNUN YERİNE getWeatherStackUrl METHODU YAZILDI
    //http://api.weatherstack.com/current? access_key = a36f86657a2da49a3d0075641e0d61c4& query = New York
    private final WeatherRepository weatherRepository;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper=new ObjectMapper();

    public WeatherService(WeatherRepository weatherRepository, RestTemplateBuilder restTemplateBuilder) {
        this.weatherRepository = weatherRepository;
        this.restTemplate = restTemplateBuilder.build();
    }
    public WeatherDto getWeatherByCityName(String city){
        Optional<WeatherEntity> weatherEntityOptional =weatherRepository.findFirstByRequestCityNameOrderByUpdateTimeDesc(city);
//        if (!weatherEntityOptional.isPresent()){
//y weatherEntityOptional optionalin içinde değer varmı yokmu kontrolünü yapıyorsa varsa true dönecek
//            return WeatherDto.convert(getWeatherFronWeatherStack(city));
//        }
//        //return WeatherDto.convert(getWeatherFronWeatherStack(city));
//         if(weatherEntityOptional.get().getUpdateTime().isBefore(LocalDateTime.now().minusMinutes(30))){
//y         //weatherEntityOptional.get().getUpdateTime() verisi  LocalDateTime.now().minusMinutes(30) verisiden önce mi geliyor öyleyse true
//y             //30 dk dan daha önceyse daha öncesinde diyelin 12.00 da istek attım ve 12.15 tekrara istek attım 11.45 temsil edicek ve
//y             // önce değil false olarak ikinci sıradakı returne girecek
//            return WeatherDto.convert(getWeatherFronWeatherStack(city));
//        }
//              return WeatherDto.convert(weatherEntityOptional.get());


         return weatherEntityOptional.map(weather ->{
             if (weather.getUpdateTime().isBefore(LocalDateTime.now().minusMinutes(30))){
                 return WeatherDto.convert(getWeatherFronWeatherStack(city));
             }
             return WeatherDto.convert(weather);
         })
                 .orElseGet(() ->WeatherDto.convert(getWeatherFronWeatherStack(city)));
    }
    private WeatherEntity getWeatherFronWeatherStack(String city){
        //neden private yaptık sadece ilgili service ilgilendirien bir method dışardından erişilmesine gerek yok
        ResponseEntity<String> responseEntity=restTemplate.getForEntity(getWeatherStackUrl(city),String.class); //geri dönüş olarak
        // string.class dedik ve string tipinde dönüş yapıcak
        //WeatherResponse dan Weaterentitiy çevirecem daha sonra kayıtedicem daha sonra kayıt ettiğimi geri dönücem
        try {
            WeatherResponse weatherResponse=objectMapper.readValue(responseEntity.getBody(),WeatherResponse.class);
            //Bu kodun amacı, API'den alınan JSON formatındaki veriyi WeatherResponse
            // sınıfına dönüştürerek bu veriyi daha sonra kullanmak için Java nesnesine çevirmektir.
            return saveWeatherEntity(city,weatherResponse);
        }
        catch (JsonProcessingException e){
            throw new RuntimeException();
        }

    }

    private String getWeatherStackUrl(String city){
        return Constants.API_URL + Constants.ACCESS_KEY_PARAM  + Constants.QUERY_KEY_PARAM + city;
    }
    //WeatherResponse WeatherEntity dönüştürmek istiyorum bunun için aşağıdaki methodu yazıyorum
    private WeatherEntity saveWeatherEntity(String city, WeatherResponse weatherResponse) {
        //Bu metodun temel amacı, verilen şehir adı ve API'den gelen hava durumu bilgileri ile yeni bir WeatherEntity nesnesi oluşturmak
        // ve bu nesneyi veritabanına kaydetmektir.
        DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        WeatherEntity weatherEntity=new WeatherEntity(city,
                weatherResponse.location().name()
                ,weatherResponse.location().country()
                ,weatherResponse.current().temperature()
                ,LocalDateTime.now()
                ,LocalDateTime.parse(weatherResponse.location().localtime(),dateTimeFormatter ));

        return weatherRepository.save(weatherEntity);
        //bu return amacı weatherENTİTY nesnesini vt kayıtetmek için kullanılır
        //return weatherRepository denilmesindeki sebeep ise weatherRepository vt temsil eder benim amacım weatherENTİTY vt kayıtetmek
    }

    //isteğimizi yapıcaz
}
