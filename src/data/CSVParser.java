package data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe CSVParser est utilisée pour analyser les données CSV.
 */
public class CSVParser {

    /**
     * Cette méthode est utilisée pour analyser une chaîne de caractères contenant des données CSV.
     * Elle parcourt la chaîne de caractères et crée une liste de tableaux de chaînes de caractères,
     * chaque tableau représentant une ligne de données et chaque élément du tableau représentant un champ de données.
     *
     * @param content - la chaîne de caractères contenant les données CSV.
     * @return List<String[]> - une liste de tableaux de chaînes de caractères représentant les données CSV.
     */
    public static List<String[]> parseCSV(String content) {
        List<String[]> result = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder field = new StringBuilder();
        List<String> fields = new ArrayList<>();

        for (int i = 0; i < content.length(); i++) {
            char c = content.charAt(i);

            if (c == '"') {
                if (inQuotes && i < content.length() - 1 && content.charAt(i + 1) == '"') {
                    field.append('"');
                    i++; // Skip the next quote
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                fields.add(field.toString().trim());
                field.setLength(0); // Reset the field
            } else if (c == '\n' && !inQuotes) {
                fields.add(field.toString().trim());
                result.add(fields.toArray(new String[0]));
                fields.clear();
                field.setLength(0); // Reset the field
            } else {
                field.append(c);
            }
        }

        // Add the last field if it exists
        if (field.length() > 0) {
            fields.add(field.toString().trim());
        }

        // Add the last line if it hasn't been added
        if (!fields.isEmpty()) {
            result.add(fields.toArray(new String[0]));
        }

        return result;
    }
}