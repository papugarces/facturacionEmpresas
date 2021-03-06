package com.eam.IngSoft1.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import lombok.Data;

import java.util.List;


/**
 * The persistent class for the bodega database table.
 * 
 */
@Entity
@Data
@NamedQuery(name="Bodega.findAll", query="SELECT b FROM Bodega b")
public class Bodega implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_bodega")
	private int idBodega;

	@NotBlank(message = "El nombre es obligatorio")
	@Column(name="nombre_bodega")
	private String nombreBodega;

	@NotBlank(message = "La dirección es obligatoria")
	private String ubicacion;

	//bi-directional many-to-one association to Producto
	@OneToMany(mappedBy="bodega")
	private List<Producto> productos;
	
	public Bodega() {
	}
	
	public Bodega(@NotBlank(message = "El nombre es obligatorio") String nombreBodega,
			@NotBlank(message = "La dirección es obligatoria") String ubicacion, List<Producto> productos) {
		super();
		this.nombreBodega = nombreBodega;
		this.ubicacion = ubicacion;
		this.productos = productos;
	}


	public int getIdBodega() {
		return this.idBodega;
	}

	public void setIdBodega(int idBodega) {
		this.idBodega = idBodega;
	}

	public String getNombreBodega() {
		return this.nombreBodega;
	}

	public void setNombreBodega(String nombreBodega) {
		this.nombreBodega = nombreBodega;
	}

	public String getUbicacion() {
		return this.ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public List<Producto> getProductos() {
		return this.productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}


	public Producto addProducto(Producto producto) {
		getProductos().add(producto);
		producto.setBodega(this);

		return producto;
	}

	public Producto removeProducto(Producto producto) {
		getProductos().remove(producto);
		producto.setBodega(null);

		return producto;
	}

	


	

}