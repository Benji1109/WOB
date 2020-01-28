package hu.wob.wobrest.repository.report;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hu.wob.wobrest.model.entity.view.TotalAndMonthlyEmailAndListingReportEntity;

@Repository
public interface TotalAndMonthlyEmailAndListingReportRepository
        extends JpaRepository<TotalAndMonthlyEmailAndListingReportEntity, Long> {

    @Query(value = "select 1\\:\\:integer as id, count(l2.id)\\:\\:integer as total_listing, ( select string_agg(tabla.owner_email_address, ',') as best_email from ( select l2.owner_email_address from listing l2 group by l2.owner_email_address having count(*) = ( select max(inside.count_l3) from ( select distinct count(*) as count_l3 from listing l3 group by l3.owner_email_address) as inside)) as tabla)\\:\\:text as best_email, array( select cast(row(t.monthly\\:\\:char(2), string_agg(t.email, ',' order by t.email)) as ReportMonthlyEmail) from ( select extract(month from l.upload_time) as monthly, l.owner_email_address as email, count(*) as ee, max(count(*)) over ( partition by (extract(month from l.upload_time)) order by extract(month from l.upload_time) ) as max_val from listing l group by l.owner_email_address, extract(month from l.upload_time)) t where t.ee = t.max_val group by t.monthly)\\:\\:text[] as monthly from listing l2", nativeQuery = true)
    public TotalAndMonthlyEmailAndListingReportEntity getOne();

}