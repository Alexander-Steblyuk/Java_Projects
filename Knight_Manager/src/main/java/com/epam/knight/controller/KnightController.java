package com.epam.knight.controller;

import com.epam.knight.model.Knight;
import com.epam.knight.model.KnightAmmunitionManager;
import com.epam.knight.model.ammunition.Ammunition;
import com.epam.knight.model.ammunition.AmmunitionType;
import com.epam.knight.view.ConsoleView;
import com.epam.knight.view.EquipMenuOption;
import com.epam.knight.view.SearchParameter;
import com.epam.knight.view.SortParameter;

/**
 * Handles main menu and all operations with knight.
 */
public class KnightController {
    private final Knight knight;
    private final KnightAmmunitionManager ammunitionManager;
    private final AmmunitionGenerator ammunitionGenerator;
    private final ConsoleView consoleView;

    public KnightController() {
        knight = KnightGenerator.generateKnight();
        ammunitionManager = new KnightAmmunitionManager(knight);
        ammunitionGenerator = new AmmunitionGenerator();
        consoleView = new ConsoleView();
    }

    public void run() {
        boolean exit = false;

        generateDefaultKnightAmmunition();

        while (!exit) {
            consoleView.showMainMenu();

            switch (consoleView.getMainMenuOption()) {
                case PRINT_STATS:
                    printKnightStats();
                    break;
                case SHOW_AMMUNITION:
                    consoleView.printKnightAmmunition(knight.getAmmunition());
                    break;
                case EQUIP:
                    equipNewAmmunition();
                    break;
                case SORT:
                    sortKnightAmmunition();
                    break;
                case SEARCH:
                    searchAmmunition();
                    break;
                case EXIT:
                    exit = true;
                    consoleView.showExitMessage();
                    break;
                default:
                    consoleView.showIncorrectOptionMessage();
                    break;
            }
        }
    }

    private void generateDefaultKnightAmmunition() {
        ammunitionManager.equipAmmunitionToKnight(ammunitionGenerator.generateAmmunition(AmmunitionType.HELMET));
        ammunitionManager.equipAmmunitionToKnight(ammunitionGenerator.generateAmmunition(AmmunitionType.SWORD));
    }

    private void printKnightStats() {
        consoleView.printKnightStats(new int[] {knight.getWeight(), knight.getCost(), knight.getDamage(),
                knight.getProtection(), knight.getCoolGrade(), knight.getSpeed()});
    }

    public void equipNewAmmunition() {
        EquipMenuOption option;
        int[] stats;
        AmmunitionType type;

        consoleView.showEquipMenu();
        option = consoleView.getEquipMenuOption();
        type = option.getAmmunitionType();

        if (type.isArmor()) {
            stats = consoleView.readArmorStats(option);
        } else {
            stats = consoleView.readWeaponStats(option);
        }

        ammunitionManager.equipAmmunitionToKnight(ammunitionGenerator.generateAmmunition(type, stats));
    }

    public void sortKnightAmmunition() {
        SortParameter option;

        consoleView.showSortMenu();
        option = consoleView.getSortMenuOption();

        ammunitionManager.sortAmmunition(option);
        consoleView.printKnightAmmunition(knight.getAmmunition());
    }

    public void searchAmmunition() {
        SearchParameter option;
        int[] searchParams;

        consoleView.showSearchMenu();
        option = consoleView.getSearchMenuOption();
        searchParams = consoleView.reedSearchParams(option);

        Ammunition[] searchResults;

        searchResults = ammunitionManager.searchAmmunition(option, searchParams);
        consoleView.printKnightAmmunition(searchResults);
    }

}
