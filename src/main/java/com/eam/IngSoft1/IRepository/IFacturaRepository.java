
package com.eam.IngSoft1.IRepository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eam.IngSoft1.domain.Detallefactura;
import com.eam.IngSoft1.domain.Factura;
import com.eam.IngSoft1.domain.Pedido;
import com.eam.IngSoft1.domain.Producto;

@Repository
public interface IFacturaRepository extends CrudRepository<Factura, Integer> {

	// ----------Consulta para saber A cual factura Agregar los productos

	/*
	 * @Query("SELECT fac.idFactura FROM Factura fac JOIN fac.pedido pe WHERE pe.activo = 0 "
	 * + "and pe.cliente.dni = ?1") public int codigoFactura(int dniUsuario);
	 */
	@Query("SELECT fac.idFactura FROM Factura fac JOIN Pedido pe ON fac.pedido.idPedido = pe.idPedido WHERE pe.activo = true "
			+ "and pe.cliente.dni = ?1")
	public int codigoFactura(int dniUsuario);
	
	
	@Query("SELECT fac FROM Factura fac JOIN Pedido pe ON fac.pedido.idPedido = pe.idPedido WHERE pe.activo = false "
			+ "and pe.cliente.dni = ?1")
	public Iterable<Factura> facturasNoActivas(int dniUsuario);

	public Factura findByidFactura(int idFactura);

	// ------------------------- factura por cliente --------------
	@Query("SELECT f FROM Factura f JOIN f.pedido p JOIN p.cliente c WHERE p.activo=0 and c.dni= ?1 ")
	public Iterable<Factura> mostrarFacturaFiltroCliente(int busqueda);
	
	
	//----------------------------mostrar los pedido del cliente-----
	@Query("SELECT p FROM Pedido p JOIN p.cliente c WHERE c.dni= ?1 ") 
	public ArrayList<Pedido> mostrarPedidos(int busqueda);
	
	
	//---------------------------------------
	@Query("SELECT p FROM Pedido p JOIN p.cliente c WHERE c.dni= ?1 AND p.activo = true") 
	public ArrayList<Pedido> mostrarPedidosActivos(int busqueda);
	
	
	//--------------------------mostrar todas las facturas que el activo sea 0 -------------
	@Query("SELECT f FROM Factura f JOIN f.pedido p JOIN p.cliente c WHERE p.activo=0") 
	public ArrayList<Factura> mostrarFacturas();
	
	
	//---------------------------------------
	@Query("SELECT fac FROM Factura fac JOIN Pedido pe ON fac.pedido.idPedido = pe.idPedido WHERE pe.activo = 1") 
	public ArrayList<Factura> mostrarFacturasActivas();
	
	//--------------------------mostrar la factura correspondiente a cierto pedido-----------
	@Query("SELECT f FROM Factura f JOIN f.pedido p WHERE p.idPedido= ?1") 
	public Factura traerFacturaPorIdPedido(int idPedido);
		
	
}







