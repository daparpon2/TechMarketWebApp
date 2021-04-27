/*
 * Copyright (C) 2021 Daniel Pardo Pont
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package es.conselleria.daparpon.techmarket.dao;

/**
 *
 * @author Daniel Pardo Pont
 */
public class DBParams {
    /* Configuración de las conexiones a base de datos remota */
    public static final String DB_URL = "jdbc:mysql://techmarketawsdb.csdv5n6tajtd.eu-west-3.rds.amazonaws.com:3306";
    public static final String DB_NAME = "techmarket";
    public static final String DB_USER = "rootAdminAWS";
    public static final String DB_PASSWORD = "$vOWOll#8hCrJ3nd";
    
    /* Configuración de las conexiones a base de datos en entorno local */
    /*
    public static final String DB_URL = "jdbc:mysql://localhost:3306";
    public static final String DB_NAME = "techmarket";
    public static final String DB_USER = "root";
    public static final String DB_PASSWORD = "Y7H6yRMj3mudEZmC";
    */
}
