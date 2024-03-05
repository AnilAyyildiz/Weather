package com.an.l.weather.repository;

import com.an.l.weather.model.WeatherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WeatherRepository extends JpaRepository<WeatherEntity,String> {
      //select * from entity where requestetecityname order by updateTime desc limit 1 jpa karşılığı budur
    //neden böyle bir sorgu yaptık  çünkü benim şöyle bir şeye ihtiiyacım var istanbu için 100 tane kayıt olmuş olabilir benim en son kayıtı bulup
    //ne zaman o kayıtın atıldığını bulmam lazım
    Optional<WeatherEntity> findFirstByRequestCityNameOrderByUpdateTimeDesc(String city);
    //Bu metot, veritabanında requestCityName alanına göre sıralanmış olan kayıtlar arasında en yüksek güncelleme zamanına sahip olanı bulmayı amaçlar.
    // city parametresi,
    // bu işlemi hangi şehir adı için gerçekleştireceğimizi belirlemek için kullanılır.
            //bana en son requestcity name ile oluşturulan  kayıdı geri döndür

    //bu şekilde de listeyebilirdim List<WeatherEntity> findAllByRequestCityNameOrderByUpdateTimeDesc(String city);
    //üsteki list de istanbulu veridk ve istanbul için 1000 tane datam var benim 999 ile işim yokki en son atılan kayıtla işim var niye 1000 tane datanın
    //datayı uygunun memorysinde barındarayım ki
}
