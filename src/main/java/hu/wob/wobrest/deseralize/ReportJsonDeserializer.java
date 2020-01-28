package hu.wob.wobrest.deseralize;

import java.io.IOException;
import java.util.Iterator;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import hu.wob.wobrest.model.dto.MonthlyReportDTO;
import hu.wob.wobrest.model.dto.ReportDTO;
import hu.wob.wobrest.model.dto.StatisticDTO;

public class ReportJsonDeserializer extends JsonDeserializer<ReportDTO> {
    @Override
    public ReportDTO deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        ReportDTO reportDTO = new ReportDTO();
        JsonNode node = jp.readValueAsTree();
        if (node.get("marketplace_id") != null) {
            // MarketplaceViewReport
            Iterator<JsonNode> reportMonthlyMarketplace = node.get("report_monthly_marketplace").elements();
            while (reportMonthlyMarketplace.hasNext()) {
                String n = reportMonthlyMarketplace.next().asText().replaceAll("\\s+", "").replaceAll("\"", "")
                        .replaceAll("[()]", "");
                Integer valueOf = Integer.valueOf(n.substring(0, n.indexOf(",")));
                MonthlyReportDTO m = new MonthlyReportDTO();
                String nameTemplate = node.get("marketplace_id").asInt() == 1 ? "amazon" : "ebay";
                StatisticDTO s = StatisticJsonDeseralizer.deserialize(node);
                if (nameTemplate == "amazon") {
                    reportDTO.setAmazon(s);
                    m.setAmazonReports(n.substring(n.indexOf(",") + 1));
                } else {
                    reportDTO.setEbay(s);
                    m.setEbayReports(n.substring(n.indexOf(",") + 1));
                }
                MonthlyReportDTO monthlyReportDTO = reportDTO.getMonthlyReports().get(valueOf);
                if (monthlyReportDTO != null) {
                    monthlyReportDTO.setAmazonReports(m.getAmazonReports());
                    monthlyReportDTO.setEbayReports(m.getEbayReports());
                } else {
                    reportDTO.getMonthlyReports().put(valueOf, m);
                }
            }
        } else {
            // TotalAndEmailViewReport
            reportDTO.setTotalListingCount(node.get("total_listing").asInt());
            reportDTO.setBestListerEmailAdress(node.get("best_email").asText());
            Iterator<JsonNode> monthly = node.findValues("monthly").get(0).elements();
            while (monthly.hasNext()) {
                String jo = monthly.next().asText().replaceAll("\\s+", "").replaceAll("\"", "").replaceAll("[()]", "");
                MonthlyReportDTO m = new MonthlyReportDTO();
                m.setBestListerEmailAdress(jo.substring(jo.indexOf(",") + 1));
                reportDTO.getMonthlyReports().put(Integer.valueOf(jo.substring(0, jo.indexOf(","))), m);
            }
        }
        return reportDTO;
    }
}
