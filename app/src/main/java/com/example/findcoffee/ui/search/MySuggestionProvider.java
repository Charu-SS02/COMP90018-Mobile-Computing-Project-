package com.example.findcoffee.ui.search;

public class MySuggestionProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = "com.example.MySuggestionProvider";
    private static final int DATABASE_MODE_QUERIES = 0;
    public final static int MODE = DATABASE_MODE_QUERIES;

    public MySuggestionProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }

    private void setupSuggestions(String authority, int mode) {
    }
}
