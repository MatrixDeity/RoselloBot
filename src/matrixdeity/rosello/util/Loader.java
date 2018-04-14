package matrixdeity.rosello.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static matrixdeity.rosello.util.Consts.*;

public class Loader {

    public static HashMap<String, List<String>> loadMapFrom(String path) throws IOException {
        List<String> data = loadListFrom(path);
        HashMap<String, List<String>> res = new HashMap<>();
        List<String> rec = null;
        for (String line : data) {
            if (line.startsWith(SECTION_PREFIX)) {
                line = line.replaceAll(" ", "");
                rec = new ArrayList<>();
                res.put(line.replace(SECTION_PREFIX, ""), rec);
            } else if (rec != null) {
                rec.add(line);
            }
        }
        return res;
    }

    public static List<String> loadListFrom(String path) throws IOException {
        List<String> data = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_16);
        List<String> res = new ArrayList<>();
        for (String line : data) {
            if (!line.matches("\\s*")) {
                res.add(line);
            }
        }
        return res;
    }

    private Loader() {
    }

}
