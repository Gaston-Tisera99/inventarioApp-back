package mitoApp.inventario.servicio;

import mitoApp.inventario.modelo.Cliente;
import mitoApp.inventario.repositorio.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServicio implements IClienteServicio {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Override
    public List<Cliente> listarCliente() {
        return this.clienteRepositorio.findAll();
    }

    @Override
    public Cliente buscarClientePorId(Integer idCliente) {
        Cliente cliente = this.clienteRepositorio.findById(idCliente).orElse(null);
        return cliente;
    }

    @Override
    public Cliente guardarCliente(Cliente cliente) {
        return this.clienteRepositorio.save(cliente);  // Guarda o actualiza el cliente
    }

    @Override
    public void eliminarClientePorId(Integer idCliente) {
        this.clienteRepositorio.deleteById(idCliente);  // Elimina el cliente por ID
    }
}

