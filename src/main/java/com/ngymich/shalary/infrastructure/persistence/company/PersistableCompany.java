package com.ngymich.shalary.infrastructure.persistence.company;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ngymich.shalary.infrastructure.persistence.salary.PersistableSalaryInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "company")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersistableCompany {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonBackReference
    @OneToOne(mappedBy = "company", fetch = FetchType.EAGER)
    private PersistableSalaryInfo salaryInfo;

    @Column(name = "name")
    private String name;

    @Column(name = "sector")
    private String sector;

    @Column(name = "size")
    private String size;
}
