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
@AllArgsConstructor
@NoArgsConstructor
@TypeDefs({ @TypeDef(name = "string-array", typeClass = StringArrayType.class) })
public class  TotalAndMonthlyEmailAndListingReportEntity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2285072705870180729L;
    @Id
    private Long id;
    private Integer totalListing;
    private String bestEmail;
    @Column(name = "monthly", columnDefinition = "text[]")
    @Type(type = "string-array")
    private String[] monthly;
}