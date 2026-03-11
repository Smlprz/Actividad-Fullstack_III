package com.shopmart.inventario_servicio.service;

import com.shopmart.inventario_servicio.model.Producto;
import com.shopmart.inventario_servicio.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductoService {
    
    @Autowired
    private ProductoRepository productoRepository;
    
    public List<Producto> obtenerTodosLosProductos() {
        return productoRepository.findAll();
    }
    
    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto);
    }
    
    public Producto obtenerProductoPorId(Long id) {
        return productoRepository.findById(id).orElse(null);
    }
    
    public List<Producto> obtenerProductosPorCategoria(String categoria) {
        return productoRepository.findByCategoria(categoria);
    }
    
    public List<Producto> obtenerProductosBajoStock(Integer umbral) {
        return productoRepository.findByStockLessThan(umbral);
    }
    
    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }
}