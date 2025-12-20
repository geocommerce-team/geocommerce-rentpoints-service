package ru.geocommerce.rentpoints;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParserRents {
    private static List<Rash> parseRashes(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(json);

        List<Rash> rashList = new ArrayList<>();

        if (rootNode.has("rash")) {
            for (JsonNode node : rootNode.get("rash")) {
                Rash rash = mapper.treeToValue(node, Rash.class);
                rashList.add(rash);
            }
        }

        return rashList;
    }

    public static List<Rash> GetRentPoints() throws IOException {
        String json = Handler.CreateRequest();
        return parseRashes(json);
    }
}
