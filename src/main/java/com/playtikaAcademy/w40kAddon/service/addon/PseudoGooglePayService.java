package com.playtikaAcademy.w40kAddon.service.addon;

import com.playtikaAcademy.w40kAddon.addon.utils.PseudoGooglePay;
import com.playtikaAcademy.w40kAddon.entities.Skin;
import com.playtikaAcademy.w40kAddon.entities.User;
import com.playtikaAcademy.w40kAddon.entities.Warrior;
import com.playtikaAcademy.w40kAddon.repository.SkinRepository;
import com.playtikaAcademy.w40kAddon.repository.UserRepository;
import com.playtikaAcademy.w40kAddon.service.MainGameService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

/**
 * 09.10.2019 21:05
 *
 * @author Edward
 */

public class PseudoGooglePayService {


    @Autowired
    MainGameService mainGameService;

    @Autowired
    UserRepository userRepository;


    @Autowired
    SkinRepository skinRepository;

    @Autowired
    PseudoGooglePay pseudoGooglePay;

    @Autowired
    WarriorService warriorService;


    public User buySkin(String skinName, String warriorName, String userName) {
        Optional<User> optionalUser = userRepository.findByUserName(userName);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Optional<Warrior> warriorOptional = mainGameService.getWarriorByNameForUser(optionalUser.get(), warriorName);
            Optional<Skin> skinOptional = skinRepository.findByName(skinName);
            if (skinOptional.isPresent() && warriorOptional.isPresent()) {
                Skin skin = skinOptional.get();
                Warrior warrior = warriorOptional.get();
                String result = interactWithClient(user, skin);
                if (!StringUtils.isEmpty(result) && result.equals("OK")) {
                    Warrior newWarrior = warrior.withSkin(skin);

                    List<Warrior> userWarriors = user.getWarriors();
                    userWarriors.set(userWarriors.indexOf(warrior), newWarrior);

                    User newUser = user.withBalance(user.getBalance() - skin.getPrice()).withWarriors(userWarriors);

                    return userRepository.save(newUser);
                }
            }
        }
        return User.builder().build();
    }

    private String interactWithClient(User user, Skin skin) {
        try {
            Optional<JSONObject> isReadyToPayRequest = pseudoGooglePay.getIsReadyToPayRequest();
            if (isReadyToPayRequest.isPresent()) {
                JSONObject paymentDataResult = pseudoGooglePay.requestPayment(skin.getPrice());
                JSONObject result = pseudoGooglePay.validateRequestResult(42, paymentDataResult.put("userBalance", user.getBalance()));
                return (String) result.get("result");
            }
        } catch (JSONException e) {

            e.printStackTrace();
        }
        return "";
    }
}
