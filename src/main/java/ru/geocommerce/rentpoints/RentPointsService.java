// ru.geocommerce.rentpoints.RentPointsService
package ru.geocommerce.rentpoints;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RentPointsService {

    private static final Logger log = LoggerFactory.getLogger(RentPointsService.class);

    private final AvitoClient avitoClient;
    private final ObjectMapper objectMapper;

    public RentPointsService(AvitoClient avitoClient, ObjectMapper objectMapper) {
        this.avitoClient = avitoClient;
        this.objectMapper = objectMapper;
    }

    public List<Rash> getRentPoints(double left, double right, double top, double bottom) {
        log.info("Fetching rent points for bbox: left={}, right={}, top={}, bottom={}", left, right, top, bottom);

        Rash.Coordinates leftTop = new Rash.Coordinates();
        Rash.Coordinates rightBottom = new Rash.Coordinates();
        leftTop.setLat(top);
        leftTop.setLng(left);
        rightBottom.setLat(bottom);
        rightBottom.setLng(right);

        String jsonResponse = avitoClient.fetchMarkers(leftTop, rightBottom);

        if (jsonResponse == null || jsonResponse.isBlank()) {
            log.warn("Received empty response from Avito");
            return List.of();
        }

        try {
            return parseRashes(jsonResponse);
        } catch (IOException e) {
            log.error("Failed to parse JSON from Avito", e);
            throw new RuntimeException("Failed to parse Avito response", e);
        }
    }

    private List<Rash> parseRashes(String json) throws IOException {
        JsonNode rootNode = objectMapper.readTree(json);

        List<Rash> rashList = new ArrayList<>();
        if (rootNode.has("rash")) {
            for (JsonNode node : rootNode.get("rash")) {
                Rash rash = objectMapper.treeToValue(node, Rash.class);
                rashList.add(rash);
            }
        }
        return rashList;
    }
}