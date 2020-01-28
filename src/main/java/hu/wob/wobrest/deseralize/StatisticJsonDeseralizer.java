package hu.wob.wobrest.deseralize;

import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;

import hu.wob.wobrest.model.dto.StatisticDTO;

public class StatisticJsonDeseralizer {
    public static StatisticDTO deserialize(JsonNode node) throws IOException {
        StatisticDTO statisticDTO = new StatisticDTO();

        statisticDTO.setAvgListingPrice(Float.valueOf(node.get("avg").asText()));
        statisticDTO.setTotalListingCount(node.get("count").asInt());
        statisticDTO.setTotalListingPrice(node.get("sum").asInt());

        return statisticDTO;
    }
}
