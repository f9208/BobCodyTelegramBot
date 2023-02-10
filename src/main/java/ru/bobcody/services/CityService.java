package ru.bobcody.services;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bobcody.domain.City;
import ru.bobcody.repository.CityRepository;

@Service
public class CityService {
    @Autowired
    private CityRepository cityRepository;

    public City getCityByName(String cityName) {
        return cityRepository.findByNameIgnoreCase(cityName)
                .orElseGet(() -> {
                    City newCity = new City();
                    newCity.setName(StringUtils.capitalize(cityName));
                    return newCity;
                });
    }
}