package com.an.l.weather.controler.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Documented
@Constraint(
        validatedBy = {CityNameValidator.class}
)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CityNameConstraint {
    //@interface, Java'da özel bir tür olan "annotation" (bildirim) oluşturmak için kullanılan bir kelime.
    String message() default "Invalid city Name";
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    //constraint olması için @interface dedik bir anotation yaratmış olduk
}
