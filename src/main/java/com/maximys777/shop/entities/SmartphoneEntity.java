package com.maximys777.shop.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "smartphones")
@AllArgsConstructor
@NoArgsConstructor
public class SmartphoneEntity extends ProductEntity {

    @Column(name = "smartphone_model")
    private String smartphoneModel;

    @Column(name = "smartphone_os")
    private String smartphoneOs;

    @Column(name = "smartphone_storage")
    private Integer smartphoneStorage;

    @Column(name = "smartphone_ram")
    private Integer smartphoneRam;

    @Column(name = "smartphone_color")
    private String smartphoneColor;

    @Column(name = "smartphone_battery_capacity")
    private Integer batteryCapacity;

    @Column(name = "smartphone_battery_unit")
    private String batteryUnit;

    @Column(name = "smartphone_model_year")
    private Integer smartphoneModelYear;
}
