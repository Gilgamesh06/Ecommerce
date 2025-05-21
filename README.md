# Proyecto de Software 2: `Ecommerce`

* El proyecto es un Ecomerce para una tienda de ropa al por menor para ello el proyecto esta dividido en:

    [![Frontend](/Images/Frontend.png)](https://github.com/Nbryan11/FrontendEcommerceSpring)
    [![Backend](/Images/Backend.png)](/Backend/)


    ## EDT

    ![EDT](/Diagramas/UML/EDT/EDT.png)

    ## Historias de usuario

    * **`US-01`:** Registro de nuevos productos
    * **`US-02`:** Catálogo de Productos
    * **`US-03`:** Filtrar Productos del Catálogo por tipo 
    * **`US-04`:** Vista de Productos por Targets
    * **`US-05`:** Agregar productos al carrito de compras
    * **`US-06`:** Eliminar productos del carrito de compras
    * **`US-07`:** Realizar Venta
    * **`US-08`:** Realizar Pedido
    * **`US-09`:** Realizar Actualizacion de Inventario
    * **`US-10`:** Visualizar Pedidos
    * **`US-11`:** Visualizar Pedido Detallado
    * **`US-12`:** Reporte de pedidos



    ## Arquitectura

    ![Diagrama Arquitectura](/Diagramas/UML/Arquitectura/Arquitectura.png)


    ## Despliege

    [![Diagrama Arquitectura](/Images/docker.png)](/Backend/docker-compose.yml)
    
    * La aplicacion esta empaquetada en docker para desplegarla se deben ejecutar los siguientes comandos

        1. **Ejecutar script.sh**

            ```bash
                # Entramos en el directorio Backend
                cd Backend
                # Damos permisos de ejecucion al script que me genera los jar de los microservicios
                sudo chmod +x script.sh
                # Ejecutamos el Script
                ./script.sh
            ```
        2. **Crear y levantar Contenedores**

            ```bash
                # Creamos y levantamos los contenedores con docker-compose
                sudo docker-compose up --build
            ```
        
        ### Requisitos

        * Tener instalado java 21
        * Tener instalado docker y docker compose
        * Usar sistema linux 
            * Tener instalado bash

        > **Nota:** En caso de usar Windows puede generar los jar de las app ya sea usado un editor o manualmente
