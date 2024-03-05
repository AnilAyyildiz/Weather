package com.an.l.weather.controler;

import com.an.l.weather.controler.validation.CityNameConstraint;
import com.an.l.weather.dto.WeatherDto;
import com.an.l.weather.service.WeatherService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/v1/api/weather")
@Validated
public class WeatherAPI {

    private final WeatherService weatherService;

    public WeatherAPI(WeatherService weatherService) {
        this.weatherService = weatherService;
    }
    @GetMapping("/{city}")
    public ResponseEntity<WeatherDto> getWeather(@PathVariable("city") @CityNameConstraint @NotBlank String city){
        return ResponseEntity.ok(weatherService.getWeatherByCityName(city));
 //(weatherService.getWeatherByCityName(city)): Bu kısım, weatherService isimli bir servis sınıfının getWeatherByCityName
        // metodunu çağırarak hava durumu bilgilerini elde eder. Bu metotun sonucu, HTTP yanıtının gövdesinde yer alacak veriyi temsil eder.
    }
    //reposneENTİTY içerisinde weatherDTO dönücez
}
