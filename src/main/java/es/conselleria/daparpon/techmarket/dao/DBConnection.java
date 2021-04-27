package es.conselleria.daparpon.techmarket.dao;

import java.sql.*;

public class DBConnection {
    
    private Connection conn = null;

   /**
    * Constructor de la clase. Se llama constructor porque tiene el mismo
    * nombre que la clase y cuando se crea un nuevo objeto de esta clase es como
    * se va a inicializar al crear un nuevo objeto de este tipo.
    *
    */
   public DBConnection() {
      try {
         //obtenemos el driver para mysql
         Class.forName("com.mysql.cj.jdbc.Driver");
         //obtenemos una conexion con los parametros especificados anteriormente 
         conn = DriverManager.getConnection(DBParams.DB_URL + "/" + DBParams.DB_NAME, DBParams.DB_USER, DBParams.DB_PASSWORD);
         // Si conn no es nulo, significa que pidimos conectarnos
         if (conn != null) {
            System.out.println("Connecting database [" + conn + "] OK");
         }
      } catch (SQLException e) // Excepcion ocurrida por la conexion 
      {
         System.out.println("Excepcion conexion: " + e.getMessage());         
      } catch (ClassNotFoundException e) // Excepcion ocurrida por no encontrar el driver
      {
         System.out.println("Excepcion driver: " + e.getMessage());         
      }
   }

   /**
    * Permite retornar la instancia de la conexion
    */
   public Connection getConnection() {      
      return conn;
   }

   // Quitamos de memoria la conexion
   public void disconnect() {
      System.out.println("Closing database: [" + conn + "] OK");
      if (conn != null) {
         try {
            // System.out.println("Desconectado de " + bd + " OK");
            conn.close();
         } catch (SQLException e) {
            System.out.println(e);
         }
      }
   }
}
