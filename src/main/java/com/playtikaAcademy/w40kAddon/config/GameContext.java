package com.playtikaAcademy.w40kAddon.config;

import com.playtikaAcademy.w40kAddon.addon.games.Quest;
import com.playtikaAcademy.w40kAddon.addon.games.TwentyOne;
import com.playtikaAcademy.w40kAddon.service.MainGameService;
import com.playtikaAcademy.w40kAddon.addon.utils.PseudoGooglePay;

import com.playtikaAcademy.w40kAddon.service.addon.IntroductionDescriptor;
import com.playtikaAcademy.w40kAddon.service.addon.PseudoGooglePayService;
import com.playtikaAcademy.w40kAddon.service.addon.TwentyOneGameService;
import com.playtikaAcademy.w40kAddon.service.addon.WarriorActualizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 30.09.2019 20:19
 *
 * @author Edward
 */
@PropertySource("classpath:addon.properties")
@Configuration
public class GameContext {

    @Bean
    public Quest getQuest() {
        return new Quest();
    }

    @Bean
    public MainGameService getMainGameService() {
        return new MainGameService();
    }

    @Bean
    public PseudoGooglePay getPseudoGooglePay() {
        return new PseudoGooglePay();
    }

    @Bean
    public PseudoGooglePayService getPseudoGooglePayService() {
        return new PseudoGooglePayService();
    }

    @Bean
    public WarriorActualizer getWarriorService() {
        return new WarriorActualizer();
    }

    @Bean
    public IntroductionDescriptor getIntroductionDescriptor(@Value("${introduction.chapter.first}") String firstChapter,
                                                            @Value("${introduction.chapter.second}") String secondChapter,
                                                            @Value("${introduction.chapter.third}") String thirdChapter) {
        return new IntroductionDescriptor(firstChapter, secondChapter, thirdChapter);
    }

    @Bean
    public TwentyOneGameService getTwentyOneGameService() {
        return new TwentyOneGameService();
    }

    @Bean
    public TwentyOne getTwentyOne() {
        return new TwentyOne();
    }
}
