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

    public static List<Rash> GetRentPoints(double left, double right, double top, double bottom) throws IOException {
        Rash.Coordinates leftTop = new Rash.Coordinates();
        Rash.Coordinates rightBottom = new Rash.Coordinates();
        leftTop.setLat(top);
        leftTop.setLng(left);
        rightBottom.setLat(bottom);
        rightBottom.setLng(right);
        String json = Handler.CreateRequest(leftTop, rightBottom);
        return parseRashes(json);
    }
}
