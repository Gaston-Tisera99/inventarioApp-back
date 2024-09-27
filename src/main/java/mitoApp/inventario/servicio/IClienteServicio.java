package mitoApp.inventario.servicio;

import mitoApp.inventario.modelo.Cliente;

import java.util.List;

public interface IClienteServicio {
    public List<Cliente> listarCliente();

    public Cliente buscarClientePorId(Integer idCliente);

    public Cliente guardarCliente(Cliente cliente);

    public void eliminarClientePorId(Integer idCliente);
}
