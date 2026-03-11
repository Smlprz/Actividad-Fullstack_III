package com.shopmart.inventario_servicio.controller;

import com.shopmart.inventario_servicio.model.Producto;
import com.shopmart.inventario_servicio.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@Tag(name = "Inventario", description = "API de gestión de inventario de productos")
@CrossOrigin(origins = "*")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    @Operation(summary = "Obtener todos los productos", 
               description = "Retorna una lista completa de todos los productos en inventario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<Producto>> obtenerTodosLosProductos() {
        List<Producto> productos = productoService.obtenerTodosLosProductos();
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo producto", 
               description = "Agrega un nuevo producto al inventario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos del producto inválidos")
    })
    public ResponseEntity<Producto> crearProducto(
            @Parameter(description = "Datos del producto a crear", required = true)
            @RequestBody Producto producto) {
        Producto nuevoProducto = productoService.guardarProducto(producto);
        return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID", 
               description = "Retorna un producto específico según su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto encontrado"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Long id) {
        Producto producto = productoService.obtenerProductoPorId(id);
        if (producto != null) {
            return new ResponseEntity<>(producto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/categoria/{categoria}")
    @Operation(summary = "Obtener productos por categoría", 
               description = "Filtra productos según su categoría")
    public ResponseEntity<List<Producto>> obtenerProductosPorCategoria(@PathVariable String categoria) {
        List<Producto> productos = productoService.obtenerProductosPorCategoria(categoria);
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    @GetMapping("/bajo-stock")
    @Operation(summary = "Obtener productos con bajo stock", 
               description = "Retorna productos con stock menor al umbral especificado")
    public ResponseEntity<List<Producto>> obtenerProductosBajoStock(
            @RequestParam(defaultValue = "10") Integer umbral) {
        List<Producto> productos = productoService.obtenerProductosBajoStock(umbral);
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar producto", 
               description = "Elimina un producto del inventario")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}