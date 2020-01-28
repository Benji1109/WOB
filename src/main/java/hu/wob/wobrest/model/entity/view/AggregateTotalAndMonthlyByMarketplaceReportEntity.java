package hu.wob.wobrest.model.entity.view;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.vladmihalcea.hibernate.type.array.StringArrayType;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@TypeDefs({ @TypeDef(name = "string-array", typeClass = StringArrayType.class) })
public class AggregateTotalAndMonthlyByMarketplaceReportEntity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -4860665610891449162L;
    @Id
    private Long id;
    private Long marketplaceId;
    private Long count;
    private Long sum;
    private Float avg;
    // monthnum, count, sum, avg
    @Column(name = "report_monthly_marketplace", columnDefinition = "text[]")
    @Type(type = "string-array")
    private String[] reportMonthlyMarketplace;
}
