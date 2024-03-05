package com.an.l.weather.controler.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component //YAPIYORUZ Kİ otomatik olarak bunu yakalaybilirsin injecte edebilsin
//constraintValidatörde implement edip constraintini vermem lazım o constarint de hata mesajı aslında
public class CityNameValidator implements ConstraintValidator<CityNameConstraint, String> {
    @Override
    public void initialize(CityNameConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        //otomatik intialize edicek super classından dolayı
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        //isVALİD FALSE DNERSE BU VALİDASYONDAN DÖNMEDİĞİ ANLAMINA GELMEKTEDİR
        // CİTY DEĞERİNİN BENİM İÇİN VALİD OLMADIĞI DURUM NE ONU BULMAM LAZIM
        //istanbul-ankara  çıktı olarak istanbulanakara vericek
        //replacement ise regexten seçilen karaterleri boş bir dizide döndürür yalnızca harf ve rakamları alıcak
        //123456 = false  Utah1=true
    value =value.replaceAll("[^a-zA-Z0-9]","");
    // aslında biz regex de sayıları çıkar demedik şehir isimlerinde sayı olur diye
    boolean isValid =!StringUtils.isNumeric(value) && !StringUtils.isAllBlank(value);
        return isValid;
    }
}
