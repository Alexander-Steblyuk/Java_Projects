package com.epam.knight.view;

import com.epam.knight.model.ammunition.Ammunition;
import com.epam.knight.model.ammunition.armor.AbstractArmor;
import com.epam.knight.model.ammunition.weapon.AbstractWeapon;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Implements all application input and output.
 */
public class ConsoleView {
    private static final String ARMOR_STATS_OUTPUT_PATTERN = "%s{protection=%d, weight=%d, cost=%d}\n";
    private static final String WEAPON_STATS_OUTPUT_PATTERN = "%s{damage=%d, weight=%d, cost=%d}\n";
    private static final String WEIGHT_INPUT_PATTERN = "Input %s weight:\n";
    private static final String COST_INPUT_PATTERN = "Input %s cost:\n";
    private static final String DAMAGE_INPUT_PATTERN = "Input %s damage:\n";
    private static final String PROTECTION_INPUT_PATTERN = "Input %s protection:\n";
    private static final String MIN_PARAM_INPUT_PATTERN = "Input min %s:\n";
    private static final String MAX_PARAM_INPUT_PATTERN = "Input max %s:\n";

    private static final String INCORRECT_OPTION_MESSAGE = "Please, write a correct number of option!";
    private static final String NEGATIVE_INPUT_MESSAGE = "Please, write a positive number:";
    private static final String EXIT_MESSAGE = "Bye!";

    private static final int WEIGHT_INDEX = 0;
    private static final int COST_INDEX = 1;
    private static final int DAMAGE_INDEX = 2;
    private static final int PROTECTION_INDEX = 3;
    private static final int COOL_GRADE_INDEX = 4;
    private static final int SPEED_INDEX = 5;

    private static final int MIN_OPTION_NUMBER = 1;
    private static final int MAX_MAIN_MENU_OPTION_NUMBER = 6;
    private static final int MAX_EQUIP_MENU_OPTION_NUMBER = 5;
    private static final int MAX_SORT_AND_SEARCH_MENU_OPTION_NUMBER = 2;

    private final Scanner scanner;


    public ConsoleView() {
        scanner = new Scanner(System.in, StandardCharsets.UTF_8);
    }

    public void showMainMenu() {
        System.out.println(Menu.MAIN.getText());
    }

    public void showEquipMenu() {
        System.out.println(Menu.EQUIP.getText());
    }

    public void showSortMenu() {
        System.out.println(Menu.SORT.getText());
    }

    public void showSearchMenu() {
        System.out.println(Menu.SEARCH.getText());
    }

    public void showExitMessage() {
        System.out.println(EXIT_MESSAGE);
    }

    public void showIncorrectOptionMessage() {
        System.out.println(INCORRECT_OPTION_MESSAGE);
    }

    public int getUserInput() {
        int inputValue;

        while (true) {
            inputValue = scanner.nextInt();

            if (inputValue >= 0) {
                break;
            }

            System.out.println(NEGATIVE_INPUT_MESSAGE);
        }


        return inputValue;
    }

    public MainMenuOption getMainMenuOption() {
        MainMenuOption inputOption;
        int inputValue;

        while (true) {
            inputValue = getUserInput();

            if (inputValue >= MIN_OPTION_NUMBER && inputValue <= MAX_MAIN_MENU_OPTION_NUMBER) {
                inputOption = MainMenuOption.values()[inputValue - 1];
                break;
            } else {
                showIncorrectOptionMessage();
            }
        }

        return inputOption;
    }

    public EquipMenuOption getEquipMenuOption() {
        EquipMenuOption inputOption;
        int inputValue;

        while (true) {
            inputValue = getUserInput();

            if (inputValue >= MIN_OPTION_NUMBER && inputValue <= MAX_EQUIP_MENU_OPTION_NUMBER) {
                inputOption = EquipMenuOption.values()[inputValue - 1];
                break;
            } else {
                showIncorrectOptionMessage();
            }
        }

        return inputOption;
    }

    public SortParameter getSortMenuOption() {
        SortParameter inputOption;
        int inputValue;

        while (true) {
            inputValue = getUserInput();

            if (inputValue >= MIN_OPTION_NUMBER && inputValue <= MAX_SORT_AND_SEARCH_MENU_OPTION_NUMBER) {
                inputOption = SortParameter.values()[inputValue - 1];
                break;
            } else {
                showIncorrectOptionMessage();
            }
        }

        return inputOption;
    }

    public SearchParameter getSearchMenuOption() {
        SearchParameter inputOption;
        int inputValue;

        while (true) {
            inputValue = getUserInput();

            if (inputValue >= MIN_OPTION_NUMBER && inputValue <= MAX_SORT_AND_SEARCH_MENU_OPTION_NUMBER) {
                inputOption = SearchParameter.values()[inputValue - 1];
                break;
            } else {
                showIncorrectOptionMessage();
            }
        }

        return inputOption;
    }

    public void printKnightStats(int[] knightStats) {
        System.out.println("Ammunition weight: " + knightStats[WEIGHT_INDEX]);
        System.out.println("Ammunition cost: " + knightStats[COST_INDEX]);
        System.out.println("Ammunition damage: " + knightStats[DAMAGE_INDEX]);
        System.out.println("Ammunition protection: " + knightStats[PROTECTION_INDEX]);
        System.out.println("Ammunition cool grade: " + knightStats[COOL_GRADE_INDEX]);
        System.out.println("Ammunition speed: " + knightStats[SPEED_INDEX]);

    }

    public void printKnightAmmunition(Ammunition[] knightAmmunition) {
        for (Ammunition ammunition : knightAmmunition) {
            if (ammunition instanceof AbstractWeapon) {
                System.out.printf(WEAPON_STATS_OUTPUT_PATTERN, ammunition.getType().toString(),
                        ((AbstractWeapon) ammunition).getDamage(), ammunition.getWeight(), ammunition.getCost());
            } else {
                System.out.printf(ARMOR_STATS_OUTPUT_PATTERN, ammunition.getType().toString(),
                        ((AbstractArmor) ammunition).getProtection(), ammunition.getWeight(), ammunition.getCost());
            }
        }

    }

    public int[] readWeaponStats(EquipMenuOption option) {
        System.out.printf(WEIGHT_INPUT_PATTERN, option);
        int weight = getUserInput();

        System.out.printf(COST_INPUT_PATTERN, option);
        int cost = getUserInput();

        System.out.printf(DAMAGE_INPUT_PATTERN, option);
        int damage = getUserInput();

        return new int[] {weight, cost, damage};
    }

    public int[] readArmorStats(EquipMenuOption option) {
        System.out.printf(WEIGHT_INPUT_PATTERN, option);
        int weight = getUserInput();

        System.out.printf(COST_INPUT_PATTERN, option);
        int cost = getUserInput();

        System.out.printf(PROTECTION_INPUT_PATTERN, option);
        int protection = getUserInput();

        return new int[] {weight, cost, protection};
    }

    public int[] reedSearchParams(SearchParameter option) {
        System.out.printf(MIN_PARAM_INPUT_PATTERN, option);
        int min = getUserInput();

        System.out.printf(MAX_PARAM_INPUT_PATTERN, option);
        int max = getUserInput();

        return new int[] {min, max};
    }

}
