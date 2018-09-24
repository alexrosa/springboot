package com.carecru.reservation.configuration;

import com.carecru.reservation.domain.convertor.ReservationRequestToReservationConverter;
import com.carecru.reservation.domain.convertor.ReservationToReservationResponseConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.ConversionService;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class ConversionConfig {

    private Set<Converter> getConverters(){
        Set<Converter> converters = new HashSet<Converter>();
        converters.add(new ReservationToReservationResponseConverter());
        converters.add(new ReservationRequestToReservationConverter());
        return converters;
    }

    @Bean
    public ConversionService conversionService(){
        ConversionServiceFactoryBean bean = new ConversionServiceFactoryBean();
        bean.setConverters(getConverters());
        bean.afterPropertiesSet();

        return bean.getObject();
    }


}
