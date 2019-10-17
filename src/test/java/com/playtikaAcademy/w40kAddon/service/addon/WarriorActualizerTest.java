package com.playtikaAcademy.w40kAddon.service.addon;

import com.playtikaAcademy.w40kAddon.entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 15.10.2019 19:21
 *
 * @author Edward
 */
//@ExtendWith(SpringExtension.class)
//@DataJpaTest cannot integrate with current db
class WarriorActualizerTest {

    private WarriorActualizer warriorActualizer;
    private Warrior testWarrior;

    @BeforeEach
    void setUp() {
        warriorActualizer = new WarriorActualizer();
        testWarrior = Warrior.builder()
                .warriorName("Dest")
                .warriorSpeciality(WarriorSpeciality.APOTHECARY)
                .level(5)
                .attack(5.0)
                .defence(9.0)
                .weapons(Arrays.asList(Weapon.builder().name("Axe").power(10.0).build(),
                        Weapon.builder().name("Bow").power(50.0).build()))
                .armors(Arrays.asList(Armor.builder().name("Rock").power(100.0).build(),
                        Armor.builder().name("Imperial").power(30.0).build()))
                .build();
    }

    /**
     * level can be still low but warrior can obtain so much experience that could pass few levels at once
     */
    @ParameterizedTest
    @CsvSource({"900, 1", "4500, 2", "10000, 3",  "14050, 4", "16100, 4", "30005, 5", "55100, 6"})
    void shouldUpdatedLevelMustCalcAccordingSecondPowerOfLevelMultipliedBy1000(long experience, int expectedLevel) {
        int actualUpdatedLevel = warriorActualizer.getUpdatedLevel(experience, 1);

        assertEquals(expectedLevel, actualUpdatedLevel);
    }

    @Test
    void shouldGetUpdatedAttackReturnDefaultValueIfWarriorHaveNoWeapon(){
        double expectedAttackWithoutWeapon = testWarrior.getAttack();

        double actualAttackWithoutWeapon = warriorActualizer.getUpdatedAttack(testWarrior.withWeapons(new ArrayList<>()), "Axe");

        assertEquals(expectedAttackWithoutWeapon, actualAttackWithoutWeapon);
    }

    @Test
    void shouldGetUpdatedAttackReturnSumOfDefaultAndPercentOfDefaultAttackAccordingToWeaponPower() {
        double expectedAttackFirstWeapon = 5.5;
        double expectedAttackSecondWeapon = 7.5;

        double actualAttackFirstWeapon = warriorActualizer.getUpdatedAttack(testWarrior, "Axe");
        double actualAttackSecondWeapon = warriorActualizer.getUpdatedAttack(testWarrior, "Bow");

        assertEquals(expectedAttackFirstWeapon, actualAttackFirstWeapon);
        assertEquals(expectedAttackSecondWeapon, actualAttackSecondWeapon);
    }

    @Test
    void shouldGetUpdatedDefenceReturnDefaultValueIfWarriorHaveNoArmor(){
        double expectedDefenceWithoutArmor = testWarrior.getDefence();

        double actualDefenceWithoutArmor = warriorActualizer.getUpdatedDefence(testWarrior.withArmors(new ArrayList<>()), "Axe");

        assertEquals(expectedDefenceWithoutArmor, actualDefenceWithoutArmor);
    }

    @Test
    void shouldGetUpdatedDefenceReturnSumOfDefaultAndPercentOfDefaultDefenceAccordingToArmorPower() {
        double expectedDefenceFirstArmor = 18.0;
        double expectedDefenceSecondArmor = 11.7;

        double actualDefenceFirstArmor = warriorActualizer.getUpdatedDefence(testWarrior, "Rock");
        double actualDefenceSecondArmor = warriorActualizer.getUpdatedDefence(testWarrior, "Imperial");

        assertEquals(expectedDefenceFirstArmor, actualDefenceFirstArmor);
        assertEquals(expectedDefenceSecondArmor, actualDefenceSecondArmor);
    }


    @ParameterizedTest
    @CsvSource({"APOTHECARY, 5.0", "ASSAULT, 22.2", "LIBRARIAN, 11.5", "HEAVY_WEAPON, 34.7", "TACTICAL, 15.4"})
    void shouldDefaultAttackReturnCorrectValue(WarriorSpeciality warriorSpeciality, double expectedAttack) {
        double actualAttack = warriorActualizer.getDefaultAttack(warriorSpeciality);

        assertEquals(expectedAttack, actualAttack);
    }

    @ParameterizedTest
    @CsvSource({"APOTHECARY, 9.0", "ASSAULT, 22.0", "LIBRARIAN, 15.5", "HEAVY_WEAPON, 55.5", "TACTICAL, 18.4"})
    void shouldDefaultDefenceReturnCorrectValue(WarriorSpeciality warriorSpeciality, double expectedDefence) {
        double actualDefence = warriorActualizer.getDefaultDefence(warriorSpeciality);

        assertEquals(expectedDefence, actualDefence);
    }
}