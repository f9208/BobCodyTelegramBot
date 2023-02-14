package ru.bobcody.services;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Getter
@PropertySource(value = {"classpath:touchBot.properties",
        "classpath:additional.properties",
        "classpath:onetwothree.properties",
        "classpath:commands.properties"},
        encoding = "UTF-8")
public class DirectiveService {

    @Value("${slap.command}")
    private List<String> slap;

    @Value("${amd.command}")
    private List<String> amd;

    @Value("${oldMan.command}")
    private List<String> oldMan;

    @Value("#{${slap.phrases}}")
    private List<String> slapPhrases;

    @Value("${course.command}")
    private List<String> course;

    @Value("${friday.command}")
    private List<String> friday;

    @Value("${fga.command}")
    private List<String> fga;

    @Value("${help.command}")
    private List<String> help;

    @Value("${onetwothree.command}")
    private List<String> oneTwoThree;

    @Value("#{${onetwothree.phrases}}")
    private List<String> answerPhrases;

    @Value("${pies.command}")
    private List<String> pies;

    @Value("${qu.command}")
    private List<String> qu;

    @Value("#{${qu.answer}}")
    private List<String> quAnswer;

    @Value("${today.is.command}")
    private List<String> today;

    @Value("${weather.command}")
    private List<String> forecast;

    @Value("${city.command}")
    private List<String> city;

    @Value("${top.command}")
    private List<String> top;
}
