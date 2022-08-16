package com.epam.knight.view;

public enum Menu {
    MAIN {
        @Override
        public String getText() {
            return "Main menu:\n" +
                    "1. Print knight stats\n" +
                    "2. Show ammunition\n" +
                    "3. Equip ammunition\n" +
                    "4. Sort ammunition\n" +
                    "5. Search ammunition\n" +
                    "6. Exit\n" +
                    CHOOSE_OPTION_MESSAGE;
        }
    },
    EQUIP {
        @Override
        public String getText() {
            return "What kind of ammunition do you want to equip?\n" +
                    "1. Sword\n" +
                    "2. Shield\n" +
                    "3. Helmet\n" +
                    "4. Cuirass\n" +
                    "5. Boots\n" +
                    CHOOSE_OPTION_MESSAGE;
        }
    },
    SORT {
        @Override
        public String getText() {
            return "Choose sort type:\n" +
                    SEARCH_AND_SORT_OPTIONS +
                    CHOOSE_OPTION_MESSAGE;
        }
    },
    SEARCH {
        @Override
        public String getText() {
            return "Choose search field:\n" +
                    SEARCH_AND_SORT_OPTIONS +
                    CHOOSE_OPTION_MESSAGE;
        }
    };

    private static final String CHOOSE_OPTION_MESSAGE = "Choose option:";
    private static final String SEARCH_AND_SORT_OPTIONS = "1. Cost\n" + "2. Weight\n";

    public abstract String getText();
}
