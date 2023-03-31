package com.ngymich.shalary.infrastructure.persistence.company;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ngymich.shalary.infrastructure.persistence.salary.PersistableSalaryInfo;
import lombok.*;

import javax.persistence.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@Entity
@Table(name = "company")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersistableCompany {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Long id;

    @JsonBackReference
    @OneToOne(mappedBy = "company", fetch = FetchType.LAZY)
    private PersistableSalaryInfo salaryInfo;

    @Column(name = "name")
    private String name;

    @Column(name = "sector")
    private String sector;

    @Column(name = "size")
    private String size;
}
