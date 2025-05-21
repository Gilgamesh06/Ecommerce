# Microservicio: `Carrito`

* Este microservicio es el encargado:

    * Agregar Producto al carrito
    * Incrementar Cantidad de Producto
    * Disminuir Cantidad de Producto
    * Actualizar Cantidad de Producto
    * Eliminar Producto
    * Listar Todo los Productos de carrito
    * Realizar Compra

    ## EndPonits

    * **`POST`** -> **`/api/v1/carrito/{sessionId}/agregar/{productId}`:**  Endpoint para agregar Producto al carrito 

        ```json
        {
            "nombre": "Camisa Casual",
            "talla": "M",
            "precio": 30.99,
            "cantidad": 3
        }
        ```
    
    * **`DELETE`** -> **`/api/v1/carrito/{sessionId}/eliminar/{productId}`:**  Endpoint para eliminar un Producto del carrito  por medio del `{productId}`

    * **`PUT`** -> **`/api/v1/carrito/{sessionId}/actualizar/{productId}/{cantidad}`:**  Endpoint para actualizar un Producto del carrito identificado con `{productId}` y actualiza la cantidad suministrada en `{cantidad}`

    * **`PUT`** -> **`/api/v1/carrito/{sessionId}/aumentar/{productId}/`:**  Endpoint para aumentar en uno la cantidad de un Producto del carrito identificado con `{productId}`

    * **`PUT`** -> **`/api/v1/carrito/{sessionId}/disminuir/{productId}/`:**  Endpoint para disminuir en uno la cantidad de un Producto del carrito identificado con `{productId}`

    * **`GET`** -> **`/api/v1/carrito/{sessionId}`:**  Genera la cookie de Session

        * Retorna un Objeto de tipo `Map<String, Producto`

            ```json
                Carrito iniciado con sessionId: 1b38ac5b-232b-4a95-9b97-0fde65420d24
            ```

    * **`GET`** -> **`/api/v1/carrito/{sessionId}/comprar`:**  Endpoint para realizar la  compra de los productos que esten en el carrito.

        * Retorna un `VentaResponseDTO`

            ```json
            {
            "referencia": "REF-2",
            "documentoCliente": null,
            "fecha": "2025-05-21T00:47:47.324817088",
            "valorVenta": 212.97,
            "estado": "Exitosa",
            "detalleVenta": [
                {
                "producto": {
                    "nombre": "Camisa Casual",
                    "referencia": "REF001",
                    "talla": "M",
                    "color": "Azul",
                    "precioVenta": 30.99
                },
                "cantidad": 3,
                "precioTotal": 92.97
                },
                {
                "producto": {
                    "nombre": "Pantalón Deportivo",
                    "referencia": "REF002",
                    "talla": "L",
                    "color": "Negro",
                    "precioVenta": 40
                },
                "cantidad": 3,
                "precioTotal": 120
                }
            ]
            }
            ```