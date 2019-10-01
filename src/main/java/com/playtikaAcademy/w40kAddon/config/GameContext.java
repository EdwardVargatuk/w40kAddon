package com.playtikaAcademy.w40kAddon.config;

import com.playtikaAcademy.w40kAddon.addon.games.Quest;
import com.playtikaAcademy.w40kAddon.service.MainGameService;
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
    public MainGameService getMainGameService(){
        return new MainGameService();
    }
}
