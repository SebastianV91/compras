package com.web.compra.controller;

import com.web.compra.dto.Mensaje;
import com.web.compra.dto.ProductoDto;
import com.web.compra.entity.Producto;
import com.web.compra.repository.ProductoRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping("/productos")
    public ResponseEntity<List<Producto>> listaProductos(){
        List<Producto> list = productoRepository.findAll();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping("/productos/{id}")
    public ResponseEntity<Producto> buscarPorId(@PathVariable("id") Long id){
        Producto producto = productoRepository.findById(id).get();
        return new ResponseEntity(producto, HttpStatus.OK);
    }

    @PostMapping("/productos")
    public ResponseEntity<?> crearProducto(@RequestBody ProductoDto productoDto){

        
        Producto producto = new Producto(productoDto.getNombre(), productoDto.getPrecio());
        productoRepository.save(producto);
        return new ResponseEntity(new Mensaje("Producto creado"), HttpStatus.OK);
    }

    @PutMapping("/productos/{id}")
    public ResponseEntity<?> modificarProducto(@PathVariable("id") Long id, @RequestBody ProductoDto productoDto){

        Producto producto = productoRepository.findById(id).get();
        producto.setNombre(productoDto.getNombre());
        producto.setPrecio(productoDto.getPrecio());
        productoRepository.save(producto);
        return new ResponseEntity(new Mensaje("Producto Actualizado"), HttpStatus.OK);
    }

    @DeleteMapping("/productos/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable("id") Long id){
        productoRepository.deleteById(id);
        return new ResponseEntity(new Mensaje("Producto Eliminado"), HttpStatus.OK);
    }

}
