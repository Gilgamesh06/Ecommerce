# Microservicio: `Venta`

* Este microservicio es el encargado:

    * Realizar la venta
    * Enviar la solicitud a l Microservicio `Pedido` 
    * Enviar la informacion para actualizar la cantidad del `Inventario`
    * Listar Venta por referencia
    * Listar todas las Ventas

    
    ## EndPonits

    * **`POST`** -> **`/api/v1/ventas/realiza-venta`:**  Endpoint para realizar una venta recibe un dto con el formato:

        ```json
        [
            {
                "productoId":2,
                "cantidad":1
            },
            {
                "productoId":4,
                "cantidad":2   
            }
        ]
        ``` 
        * Retorna un Objeto de tipo `VentaResponseDTO`

            ```json
            {
            "referencia": "REF-3",
            "documentoCliente": null,
            "fecha": "2025-05-21T02:52:38.083190679",
            "valorVenta": 230,
            "estado": "Exitosa",
            "detalleVenta": [
                {
                "producto": {
                    "nombre": "Pantalón Deportivo",
                    "referencia": "REF002",
                    "talla": "L",
                    "color": "Negro",
                    "precioVenta": 40
                },
                "cantidad": 1,
                "precioTotal": 40
                },
                {
                "producto": {
                    "nombre": "Chaqueta de Invierno",
                    "referencia": "REF004",
                    "talla": "S",
                    "color": "Rojo",
                    "precioVenta": 95
                },
                "cantidad": 2,
                "precioTotal": 190
                }
            ]
            }
            ```
    
    * **`GET`** -> **`/api/v1/obtener-venta/{referencia}`:** Obtiene la venta que tengo la misma referencia que se pasa por medio de `{referencia}`

        * Retorna un Objeto de tipo `VentaResponseDTO`
        
            ```json
            {
            "referencia": "REF-3",
            "documentoCliente": null,
            "fecha": "2025-05-21T02:52:38.083191",
            "valorVenta": 230,
            "estado": "Exitosa",
            "detalleVenta": [
                {
                "producto": {
                    "nombre": "Pantalón Deportivo",
                    "referencia": "REF002",
                    "talla": "L",
                    "color": "Negro",
                    "precioVenta": 40
                },
                "cantidad": 1,
                "precioTotal": 40
                },
                {
                "producto": {
                    "nombre": "Chaqueta de Invierno",
                    "referencia": "REF004",
                    "talla": "S",
                    "color": "Rojo",
                    "precioVenta": 95
                },
                "cantidad": 2,
                "precioTotal": 190
                }
            ]
            }        
            ``` 
    * **`GET`** -> **`/api/v1/ventas/listar-ventas`:** Obtiene una lista que contiene todas las ventas realizadas

        * Retorna un  `List<VentaResponseDTO>` 
    
            ```json
            [
            {
                "referencia": "REF-1",
                "documentoCliente": null,
                "fecha": "2025-05-21T00:46:40.116536",
                "valorVenta": 40,
                "estado": "Exitosa",
                "detalleVenta": [
                {
                    "producto": {
                    "nombre": "Pantalón Deportivo",
                    "referencia": "REF002",
                    "talla": "L",
                    "color": "Negro",
                    "precioVenta": 40
                    },
                    "cantidad": 1,
                    "precioTotal": 40
                }
                ]
            },
            {
                "referencia": "REF-2",
                "documentoCliente": null,
                "fecha": "2025-05-21T00:47:47.324817",
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
            },
            {
                "referencia": "REF-3",
                "documentoCliente": null,
                "fecha": "2025-05-21T02:52:38.083191",
                "valorVenta": 230,
                "estado": "Exitosa",
                "detalleVenta": [
                {
                    "producto": {
                    "nombre": "Pantalón Deportivo",
                    "referencia": "REF002",
                    "talla": "L",
                    "color": "Negro",
                    "precioVenta": 40
                    },
                    "cantidad": 1,
                    "precioTotal": 40
                },
                {
                    "producto": {
                    "nombre": "Chaqueta de Invierno",
                    "referencia": "REF004",
                    "talla": "S",
                    "color": "Rojo",
                    "precioVenta": 95
                    },
                    "cantidad": 2,
                    "precioTotal": 190
                }
                ]
            }
            ]
            ```

    * **`GET`** -> **`/api/v1/ventas/obtener-detalles/{referencia}`:** Obtiene una lista con los detalles de la venta que tiene la referencia `{referencia}` 

        * Retorna un `List<DetalleVentaResponseDTO>`

            ```json
            [
                {
                    "producto": {
                    "nombre": "Pantalón Deportivo",
                    "referencia": "REF002",
                    "talla": "L",
                    "color": "Negro",
                    "precioVenta": 40
                    },
                    "cantidad": 1,
                    "precioTotal": 40
                },
                {
                    "producto": {
                    "nombre": "Chaqueta de Invierno",
                    "referencia": "REF004",
                    "talla": "S",
                    "color": "Rojo",
                    "precioVenta": 95
                    },
                    "cantidad": 2,
                    "precioTotal": 190
                }
                ]
            ```

        > **Nota:** Este Endpoint es usado por el Microservicio `Pedido`
    

    * **`GET`** -> **`/api/v1/ventas/obtener-detalles/all`:** Obtiene una lista de listas con los detalles de la ventas 

        * Retorna un `List<DetalleVentaResponseDTO>`

            ```json
            ]
                [
                    {
                        "producto": {
                        "nombre": "Pantalón Deportivo",
                        "referencia": "REF002",
                        "talla": "L",
                        "color": "Negro",
                        "precioVenta": 40
                        },
                        "cantidad": 1,
                        "precioTotal": 40
                    },
                    {
                        "producto": {
                        "nombre": "Chaqueta de Invierno",
                        "referencia": "REF004",
                        "talla": "S",
                        "color": "Rojo",
                        "precioVenta": 95
                        },
                        "cantidad": 2,
                        "precioTotal": 190
                    }
                ],
                [
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
            ]
            ```

        > **Nota:** Este Endpoint es usado por el Microservicio `Pedido`

        