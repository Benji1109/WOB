package hu.wob.wobrest.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.module.SimpleModule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hu.wob.wobrest.deseralize.ReportJsonDeserializer;
import hu.wob.wobrest.model.dto.MonthlyReportDTO;
import hu.wob.wobrest.model.dto.ReportDTO;
import hu.wob.wobrest.model.entity.view.AggregateTotalAndMonthlyByMarketplaceReportEntity;
import hu.wob.wobrest.model.entity.view.TotalAndMonthlyEmailAndListingReportEntity;
import hu.wob.wobrest.repository.report.AggregateTotalAndMonthlyByMarketplaceReportRepository;
import hu.wob.wobrest.repository.report.TotalAndMonthlyEmailAndListingReportRepository;

@Component
public class MapViewEntitiesToReportDTO {
    Logger logger = LoggerFactory.getLogger(MapViewEntitiesToReportDTO.class);

    @Autowired
    private TotalAndMonthlyEmailAndListingReportRepository enums;
    @Autowired
    private AggregateTotalAndMonthlyByMarketplaceReportRepository agg;

    public ReportDTO reportInJson() {
        TotalAndMonthlyEmailAndListingReportEntity dtoApplications = this.enums.getOne();
        ReportDTO reportDTO = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            SimpleModule module = new SimpleModule();
            module.addDeserializer(ReportDTO.class, new ReportJsonDeserializer());
            mapper.registerModule(module);
            mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
            mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
            reportDTO = mapper.convertValue(dtoApplications, new TypeReference<ReportDTO>() {
            });
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return this.mergeReportElements(this.viewReportByMarketplace(), reportDTO);
    }

    private List<ReportDTO> viewReportByMarketplace() {
        List<AggregateTotalAndMonthlyByMarketplaceReportEntity> dtoApplications = this.agg.findAll();
        List<ReportDTO> reportDTOs = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        try {
            for (AggregateTotalAndMonthlyByMarketplaceReportEntity aEntity : dtoApplications) {
                module.addDeserializer(ReportDTO.class, new ReportJsonDeserializer());
                mapper.registerModule(module);
                mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
                mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
                reportDTOs.add(mapper.convertValue(aEntity, new TypeReference<ReportDTO>() {
                }));
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return reportDTOs;
    }

    private ReportDTO mergeReportElements(List<ReportDTO> aggregates, ReportDTO totalAndEMail) {
        ReportDTO a = aggregates.get(0);
        ReportDTO b = aggregates.get(1);
        if (a.getAmazon() == null) {
            a.setAmazon(b.getAmazon());
            for (Entry<Integer, MonthlyReportDTO> reportDTO : a.getMonthlyReports().entrySet()) {
                MonthlyReportDTO g = b.getMonthlyReports().get(reportDTO.getKey());
                if (g != null) {
                    reportDTO.getValue().setAmazonReports(g.getAmazonReports());
                }
            }
        } else {
            a.setEbay(b.getEbay());
            for (Entry<Integer, MonthlyReportDTO> reportDTO : a.getMonthlyReports().entrySet()) {
                MonthlyReportDTO g = b.getMonthlyReports().get(reportDTO.getKey());
                if (g != null) {
                    reportDTO.getValue().setEbayReports(g.getEbayReports());
                }
            }
        }
        if (totalAndEMail != null) {
            a.setTotalListingCount(totalAndEMail.getTotalListingCount());
            a.setBestListerEmailAdress(totalAndEMail.getBestListerEmailAdress());
            for (Entry<Integer, MonthlyReportDTO> reportDTO : a.getMonthlyReports().entrySet()) {
                reportDTO.getValue().setBestListerEmailAdress(
                        totalAndEMail.getMonthlyReports().get(reportDTO.getKey()).getBestListerEmailAdress());
            }
        }
        return a;
    }
}
