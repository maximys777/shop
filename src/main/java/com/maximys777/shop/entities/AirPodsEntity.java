package com.maximys777.shop.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "airpods")
public class AirPodsEntity extends ProductEntity {

    @Column(name = "airpods_model")
    private String AirPodsModel;

    @Column(name = "airpods_color")
    private String AirPodsColor;

    @Column(name = "airpods_model_year")
    private Integer AirPodsModelYear;

    @Column(name = "airpods_work_time")
    private Integer AirPodsWorkTime;
}
