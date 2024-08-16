package com.cepeda.trsv2.model.entities;

import com.cepeda.trsv2.util.LocalDateTimeAttributeConverter;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.JoinColumn;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clase que representa un viaje en el sistema.
 * Cada viaje está asociado a un usuario.
 * 
 * @author CyborgK27
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "travels")
public class Travel extends BaseEntity<Integer> {

    @Column(name = "code_travel", nullable = false)
    private String codeTravel;

    @Column(name = "destination", nullable = false)
    private String destination;

    @Column(name = "travel_date", nullable = false)
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime travelDate;

    @Column(name = "return_date", nullable = false)
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime returnDate;

    @Column(name = "available_places", nullable = false)
    private int availablePlaces;

    @Column(name = "price", nullable = false)
    private float price;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
