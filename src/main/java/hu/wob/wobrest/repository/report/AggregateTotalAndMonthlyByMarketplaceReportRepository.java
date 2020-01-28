package hu.wob.wobrest.repository.report;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hu.wob.wobrest.model.entity.view.AggregateTotalAndMonthlyByMarketplaceReportEntity;

@Repository
public interface AggregateTotalAndMonthlyByMarketplaceReportRepository extends JpaRepository<AggregateTotalAndMonthlyByMarketplaceReportEntity, Long> {

    @Query(value = "SELECT row_number() over () as id ,l.marketplace as marketplace_id ,COUNT(l.listing_price) as count ,SUM(l.listing_price) as sum ,AVG(l.listing_price) as avg ,(ARRAY (SELECT CAST(ROW(extract(MONTH FROM l2.upload_time) \\:\\:TEXT, 'Count: '|| COUNT(l2.listing_price) \\:\\:TEXT, 'Sum: '|| SUM(l2.listing_price) \\:\\:TEXT, 'Avg: '|| AVG(l2.listing_price) \\:\\:TEXT) AS report_monthly_marketplace) FROM public.listing l2 WHERE l2.marketplace = l.marketplace GROUP BY l2.marketplace ,extract(MONTH FROM l2.upload_time) ORDER BY extract(MONTH FROM l2.upload_time) asc))\\:\\:text[] AS report_monthly_marketplace FROM public.listing l GROUP BY l.marketplace", nativeQuery = true)
    public List<AggregateTotalAndMonthlyByMarketplaceReportEntity> findAll();

}