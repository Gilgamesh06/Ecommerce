# Proyecto de Software 2: `Ecommerce`

* El proyecto es un Ecomerce para una tienda de ropa al por menor para ello el proyecto se estructuro en:

    * [**Backend**](/Backend/)
    * **Frontend**

    ## Arquitectura

    * ![Diagrama Arquitectura](/Diagramas/UML/Arquitectura/Arquitectura.png)


    ## Despliege
    
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
        