# Backend

* El backend esta compuesto por 5 microservicios los cuales son:

    * [**Inventario**](/Backend/Microservicio-inventario/)
    * [**Venta**](/Backend/Microservicio-ventas/)
    * [**Pedidos**](/Backend/Microservicio-pedido/)
    * **Carrito de Compra**
    * **Reporte**

* Los Microservicios esta hechos en SpringBoot y se comunican con Http (`RestTemplate`)
    * Excepto **carrito de Compra** y **Venta** que se comunican de forma asincrona usando `RabbitMQ`

* Todos los microservicios tiene bases de datos

    * **Inventario**

        ![Inventario](/Diagramas/DB/Inventario.png)
    
    * **Ventas**

        ![Venta](/Diagramas/DB/Ventas.png)

    * **Pedidos**

        ![Pedido](/Diagramas/DB/Pedidos.png)
    
    ## Diagrama de Actividades

    * El proceso de realizacion de un pedido esta dado por el diagrama:

        ![Actividades](/Diagramas/UML/Actividades/Pedidos.png)