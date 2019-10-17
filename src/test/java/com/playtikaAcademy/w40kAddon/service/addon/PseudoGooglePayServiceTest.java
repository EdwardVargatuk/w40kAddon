package com.playtikaAcademy.w40kAddon.service.addon;

import com.playtikaAcademy.w40kAddon.addon.utils.PseudoGooglePay;
import com.playtikaAcademy.w40kAddon.exceptions.EntityNotFoundByParameterException;
import com.playtikaAcademy.w40kAddon.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 14.10.2019 10:45
 *
 * @author Edward
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest
@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@ContextConfiguration(classes = GameContext.class)
class PseudoGooglePayServiceTest {

//    @Autowired
//    MainGameService mainGameService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PseudoGooglePayService pseudoGooglePayService;

    @Autowired
    PseudoGooglePay pseudoGooglePay;

    @BeforeEach
    void setUp() {
//        pseudoGooglePayService = new PseudoGooglePayService();
    }

    //TODO: integrate DataJpaTest with current db
    @ParameterizedTest
    @CsvSource({"joke,someWarrior,Jon", "someWarrior,war,wdw"})
    void shouldThrowExceptionIfObjectNotFoundInDb(String skin, String warrior, String user) {
        assertThrows(EntityNotFoundByParameterException.class, () -> pseudoGooglePayService.buySkin(skin, warrior, user));
    }
}