package com.cepeda.trsv2.model.entities;

import com.cepeda.trsv2.util.LocalDateTimeAttributeConverter;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Clase base para todas las entidades del sistema.
 * Proporciona campos comunes y funcionalidad básica para el seguimiento de
 * auditoría.
 * 
 * @author CyborgK27
 */
@MappedSuperclass
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public abstract class BaseEntity<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public T id;

    @Column(name = "audit_create_user_id")
    public Integer auditCreateUserId;

    @Column(name = "audit_create_date")
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    public LocalDateTime auditCreateDate;

    @Column(name = "audit_update_user_id")
    public Integer auditUpdateUserId;

    @Column(name = "audit_update_date")
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    public LocalDateTime auditUpdateDate;

    @Column(name = "audit_remove_user_id")
    public Integer auditRemoveUserId;

    @Column(name = "audit_remove_date")
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    public LocalDateTime auditRemoveDate;

    @Column(name = "state")
    public Integer state;
}
