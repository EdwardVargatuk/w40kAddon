package com.playtikaAcademy.w40kAddon.config;

import com.playtikaAcademy.w40kAddon.addon.games.Quest;
import com.playtikaAcademy.w40kAddon.service.MainGameService;
import com.playtikaAcademy.w40kAddon.service.addon.PseudoGooglePayService;
import com.playtikaAcademy.w40kAddon.service.addon.WarriorActualizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 30.09.2019 20:19
 *
 * @author Edward
 */

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
    public PseudoGooglePayService getPseudoGooglePayService() {
        return new PseudoGooglePayService();
    }

    @Bean
    public WarriorActualizer getWarriorService(){
        return new WarriorActualizer();
    }
}
