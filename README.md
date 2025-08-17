# Sistema de Inventario (Entradas y Salidas)

Este proyecto es un **sistema de control de inventario** desarrollado en **Java (JSP y Servlets)**.  
Permite registrar productos, gestionar entradas y salidas de inventario y mantener un historial de movimientos.

---

## Tecnologías utilizadas

- **IDE**: NetBeans 25  
- **Lenguaje de programación**: Java SE 8  
- **Servidor de aplicaciones**: Apache Tomcat 9.0  
- **DBMS**: SQLServer  
- **Gestor de dependencias**: JDBC

---

## Pasos para ejecutar la aplicación

1. **Clonar el repositorio**
   git clone https://github.com/IDGS-803-20001577/MiProyecto.git

2. **Configurar la base de datos**

    Crear una base de datos en SQLServer:
    CREATE DATABASE PRUEBATECNICA;
    
    Tablas principales:
    - `productos` → Catálogo de productos.  
    - `inventario` → Cantidad actual de productos.  
    - `historico_inventario` → Registra entradas y salidas.  
    - los scripts se encuentran dentro de la tabla llamada SCRIPTS como se indica en el punto 4.2 

3.**Configurar la conexión JDBC**

    Editar el archivo C:\Users\gayta\Documents\MiProyecto\src\main\java\com\ejemplo\proyecto\config\Conexion.java y actualizar con tus credenciales:

    Actualmente esta configurado de esta manera

    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=PRUEBATECNICA;encrypt=false";
    private static final String USER = "sa";
    private static final String PASSWORD = "12345";

