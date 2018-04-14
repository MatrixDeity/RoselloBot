package matrixdeity.rosello.core;

import matrixdeity.rosello.util.Loader;

import java.io.IOException;
import java.util.*;

import static matrixdeity.rosello.util.Consts.*;

public class Interlocuter {

    private HashMap<String, List<String>> answers;
    private HashMap<String, List<String>> keys;
    private List<String> gags;
    private List<String> welcomes;
    private List<String> greetings;

    public Interlocuter() throws IOException {
        answers = Loader.loadMapFrom(ANSWERS_PATH);
        keys = Loader.loadMapFrom(KEYS_PATH);
        gags = Loader.loadListFrom(GAGS_PATH);
        welcomes = Loader.loadListFrom(WELCOMES_PATH);
        greetings = Loader.loadListFrom(GREETINGS_PATH);
    }

    public String generateAnswer(String name, String content) {
        String ans = null;
        for (Map.Entry<String, List<String>> entry : keys.entrySet()) {
            if (hasWords(content, entry.getValue())) {
                ans = getRandomAnswerFrom(entry.getKey());
                break;
            }
        }
        if (ans == null) {
            ans = getRandomAnswerFrom(DEFAULT_ANSWERS);
        }
        ans = insertTokens(ans, name);
        return ans;
    }

    public String generateGag(String name) {
        String gag = gags.get(GENERATOR.nextInt(gags.size()));
        return insertTokens(gag, name);
    }

    public String generateWelcome(String name) {
        String welcome = welcomes.get(GENERATOR.nextInt(welcomes.size()));
        return insertTokens(welcome, name);
    }

    public String generateGreetings() {
        String greeting = greetings.get(GENERATOR.nextInt(greetings.size()));
        return insertTokens(greeting.replaceAll(USER_TOKEN, ALL_TOKEN), "");
    }

    private boolean hasWords(String content, List<String> words) {
        for (String word : words) {
            if (content.contains(word)) {
                return true;
            }
        }
        return false;
    }

    private String getRandomAnswerFrom(String type) {
        List<String> list = answers.get(type);
        return list.get(GENERATOR.nextInt(list.size()));
    }

    private String insertTokens(String message, String name) {
        message = message.replaceAll(USER_TOKEN, name);
        message = message.replaceAll(ALL_TOKEN, ALL_VALUE);
        return message;
    }

}
