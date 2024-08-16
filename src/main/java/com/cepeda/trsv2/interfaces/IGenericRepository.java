package com.cepeda.trsv2.interfaces;

import com.cepeda.trsv2.model.entities.BaseEntity;
import java.util.HashSet;
import java.util.concurrent.CompletableFuture;

/**
 * Interfaz para operaciones genéricas de repositorio con métodos asíncronos.
 * Proporciona operaciones básicas CRUD para entidades.
 * 
 * @param <T> El tipo de la entidad, que debe extender {@link BaseEntity}.
 * @param <ID> El tipo del identificador de la entidad.
 * 
 * @author CyborgK27
 */
public interface IGenericRepository<T extends BaseEntity<ID>, ID> {
    
    /**
     * Recupera todas las entidades de manera asíncrona.
     * 
     * @return Un {@link CompletableFuture} que resuelve a un {@link HashSet} 
     * de entidades.
     */
    CompletableFuture<HashSet<T>> getAllAsync();
    
    /**
     * Recupera una entidad por su ID de manera asíncrona.
     * 
     * @param id El ID de la entidad a recuperar.
     * @return Un {@link CompletableFuture} que resuelve a la entidad con el 
     * ID dado.
     */
    CompletableFuture<T> getByIdAsync(ID id);
    
    /**
     * Crea una nueva entidad de manera asíncrona.
     * 
     * @param entity La entidad a crear.
     * @return Un {@link CompletableFuture} que resuelve a {@code true} si la 
     * entidad fue creada exitosamente, {@code false} en caso contrario.
     */
    CompletableFuture<Boolean> createAsync(T entity);
    
    /**
     * Actualiza una entidad existente de manera asíncrona.
     * 
     * @param entity La entidad a actualizar.
     * @return Un {@link CompletableFuture} que resuelve a {@code true} si la 
     * entidad fue actualizada exitosamente, {@code false} en caso contrario.
     */
    CompletableFuture<Boolean> updateAsync(T entity);
    
    /**
     * Elimina una entidad por su ID de manera asíncrona.
     * 
     * @param id El ID de la entidad a eliminar.
     * @return Un {@link CompletableFuture} que resuelve a {@code true} si la 
     * entidad fue eliminada exitosamente, {@code false} en caso contrario.
     */
    CompletableFuture<Boolean> removeAsync(ID id);
}
