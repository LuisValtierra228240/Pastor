package mx.itson.pastor.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import mx.itson.pastor.entidades.Cliente;
import mx.itson.pastor.entidades.Cuenta;

public class CuentaDAO {
    public static List<Cuenta> obtenerTodos() {
        List<Cuenta> cuentas = new ArrayList<>();
        try {
            Connection connection = Conexion.obtener();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT id, numero, idCliente FROM cuenta;");
            while (resultSet.next()) {
                Cuenta c = new Cuenta();

                c.setId(resultSet.getInt(1));
                c.setNumero(resultSet.getString(2));
                c.setCliente(ClienteDAO.obtenerPorID(resultSet.getInt(3)));

                cuentas.add(c);
            }
        } catch (Exception e) {
            System.err.print("Ocurrió un error: " + e.getMessage());
        }
        return cuentas;
    }
    public static boolean guardar(String numero, Cliente cliente) {
        boolean resultado = false;
        try {
            Connection connection = Conexion.obtener();
            String consulta = "INSERT INTO cuenta (numero, idCliente) values (?, ?);";
            PreparedStatement statement = connection.prepareStatement(consulta);
            statement.setString(1, numero);
            statement.setInt(2, cliente.getId());
            
            statement.execute();

            resultado = statement.getUpdateCount() ==1;
        } catch (Exception e) {
            System.err.println("Ocurrió un error: " + e.getMessage());
        }
        return resultado;
    }
}


