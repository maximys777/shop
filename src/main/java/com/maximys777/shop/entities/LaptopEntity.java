package com.maximys777.shop.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "laptops")
public class LaptopEntity extends  ProductEntity {
    @Column(name = "laptop_model")
    private String laptopModel;

    @Column(name = "laptop_diagonal")
    private Double laptopDiagonal;

    @Column(name = "laptop_processor")
    private String laptopProcessor;

    @Column(name = "laptop_os")
    private String laptopOs;

    @Column(name = "laptop_ram")
    private Integer laptopRam;

    @Column(name = "laptop_storage")
    private String laptopStorage;

    @Column(name = "laptop_graph_card")
    private String laptopGraphCard;

    @Column(name = "laptop_color")
    private String laptopColor;

    @Column(name = "laptop_battery")
    private String laptopBattery;

    @Column(name = "laptop_model_year")
    private Integer laptopModelYear;

}
