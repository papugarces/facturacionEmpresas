package com.eam.IngSoft1.Controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.eam.IngSoft1.IRepository.IDetalleFacturaRepository;
import com.eam.IngSoft1.IRepository.IFacturaRepository;
import com.eam.IngSoft1.IRepository.IPedidoRepository;
import com.eam.IngSoft1.IRepository.IProductoRepository;
import com.eam.IngSoft1.IRepository.IUsuarioRepository;
import com.eam.IngSoft1.domain.Bodega;
import com.eam.IngSoft1.domain.Categoriaproducto;
import com.eam.IngSoft1.domain.Detallefactura;
import com.eam.IngSoft1.domain.Factura;
import com.eam.IngSoft1.domain.Pedido;
import com.eam.IngSoft1.domain.Producto;
import com.eam.IngSoft1.domain.Proveedor;
import com.eam.IngSoft1.domain.Usuario;

@Controller
public class FacturaController {

	private final IFacturaRepository repositorioFactura;
	private final IPedidoRepository repositorioPedido;
	private final IDetalleFacturaRepository repositorioDetalle;
	private final IUsuarioRepository repositorioUsuario;
	private final IProductoRepository repositorioProducto;

	@Autowired
	public FacturaController(IFacturaRepository repositorioFactura, IPedidoRepository repositorioPedido,
			IDetalleFacturaRepository repositorioDetalle, IUsuarioRepository repositorioUsuario,
			IProductoRepository repositorioProducto) {
		this.repositorioPedido = repositorioPedido;
		this.repositorioFactura = repositorioFactura;
		this.repositorioDetalle = repositorioDetalle;
		this.repositorioUsuario = repositorioUsuario;
		this.repositorioProducto = repositorioProducto;
	}

	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping("/user/agregar-al-carrito")
	public String crearFactura(@RequestParam(value = "username") String username,
			@RequestParam(value="idProducto") int idProducto, @RequestParam(value = "cantidad") int cantidad) {

		System.out.print(idProducto);
		System.out.print(username);
		System.out.print(cantidad);
		// <input type="text" id="tiempo" th:name="tiempo" placeholder="Tiempo en
		// minutos"></input>

		Usuario user = repositorioUsuario.mostrarUsuario(username);

		int idFactura = repositorioFactura.codigoFactura(user.getDni());

		Detallefactura detallefactura = new Detallefactura();

		if (idFactura != 0) {
			detallefactura.setFactura(repositorioFactura.findByidFactura(idFactura));

		} else {
			// Se crea un nuevo pedido en el sistema y se le asocia una factura
			Pedido pedido = new Pedido();
			pedido.setActivo(true);
			pedido.setDespachado(false);
			pedido.setCliente(repositorioUsuario.findByDni(user.getDni()));
			java.util.Date fecha = new Date();
			// Captura La Fecha del Sistema
			pedido.setFechaPedido(fecha);

			// Guardamos el nuevo pedido en la BD
			repositorioPedido.save(pedido);

			Factura factura = new Factura();
			factura.setPedido(pedido);

			// Guardamos la nueva factura en la BD
			repositorioFactura.save(factura);

			// Obtenemos la factura con el ID generado
			int idFacturaNueva = repositorioFactura.codigoFactura(user.getDni());
			detallefactura.setFactura(repositorioFactura.findByidFactura(idFacturaNueva));
		}

		Producto producto = repositorioProducto.findByidProducto(idProducto);

		if (producto.getCantidadActual() >= cantidad) {

			detallefactura.setProducto(producto);
			detallefactura.setCantidadProducto(cantidad);

			int valor = producto.getValorUnitario() * cantidad;
			detallefactura.setValorTotal(valor);

		}
		return "/homePageUsuario";
	}

	// metodo para listado factura
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EMPLEADO')")
	@GetMapping("/admin/listadofactura")
	public String list(Factura factura, Model model) {
		model.addAttribute("facturas", repositorioFactura.findAll());
		return "Factura/listaFactura";
	}

	// metodo para listado facturas del usuario
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/listfactura")
	public String list(@RequestParam(value = "dniUsuario") int dniUsuario, Factura factura, Model model) {
		model.addAttribute("facturas", repositorioFactura.mostrarFacturaFiltroCliente(dniUsuario));
		return "Factura/listaFactura";
	}

}