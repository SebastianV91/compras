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
        if(!productoRepository.existsById(id))
            return new ResponseEntity(new Mensaje("El id del producto buscado no existe"), HttpStatus.NOT_FOUND);

        Producto producto = productoRepository.findById(id).get();
        return new ResponseEntity(producto, HttpStatus.OK);
    }

    @PostMapping("/productos")
    public ResponseEntity<?> crearProducto(@RequestBody ProductoDto productoDto){
        if(StringUtils.isBlank(productoDto.getNombre()))
            return new ResponseEntity(new Mensaje("El nombre del producto es obligatorio"), HttpStatus.BAD_REQUEST);
        if(productoDto.getPrecio()==null || productoDto.getPrecio()<0 )
            return new ResponseEntity(new Mensaje("El precio del producto debe ser mayor que 0"), HttpStatus.BAD_REQUEST);
        if(productoRepository.existsByNombre(productoDto.getNombre()))
            return new ResponseEntity(new Mensaje("El nombre de ese producto ya existe"), HttpStatus.BAD_REQUEST);
        
        Producto producto = new Producto(productoDto.getNombre(), productoDto.getPrecio());
        productoRepository.save(producto);
        return new ResponseEntity(new Mensaje("Producto creado"), HttpStatus.OK);
    }

    @PutMapping("/productos/{id}")
    public ResponseEntity<?> modificarProducto(@PathVariable("id") Long id, @RequestBody ProductoDto productoDto){
        if(StringUtils.isBlank(productoDto.getNombre()))
            return new ResponseEntity(new Mensaje("El nombre del producto es obligatorio"), HttpStatus.BAD_REQUEST);
        if(productoRepository.existsByNombre(productoDto.getNombre()))
            return new ResponseEntity(new Mensaje("El nombre de ese producto ya existe"), HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(productoDto.getNombre()))
            return new ResponseEntity(new Mensaje("El nombre del producto es obligatorio"), HttpStatus.BAD_REQUEST);
        if(productoDto.getPrecio()==null || productoDto.getPrecio()<0 )
            return new ResponseEntity(new Mensaje("El precio del producto debe ser mayor que 0"), HttpStatus.BAD_REQUEST);

        Producto producto = productoRepository.findById(id).get();
        producto.setNombre(productoDto.getNombre());
        producto.setPrecio(productoDto.getPrecio());
        productoRepository.save(producto);
        return new ResponseEntity(new Mensaje("Producto Actualizado"), HttpStatus.OK);
    }

    @DeleteMapping("/productos/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable("id") Long id){
        if(!productoRepository.existsById(id))
            return new ResponseEntity(new Mensaje("El id del producto buscado no existe para eliminar"), HttpStatus.NOT_FOUND);

        productoRepository.deleteById(id);
        return new ResponseEntity(new Mensaje("Producto Eliminado"), HttpStatus.OK);
    }

}
