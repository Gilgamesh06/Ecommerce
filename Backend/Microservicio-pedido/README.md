# Microservicio: `Pedido`

* Este microservicio es el encargado:

    * Realizar el pedido
    * Listar Pedido por referencia
    * Listar Todo los pedidos

    
    ## EndPonits

    * **`GET`** -> **`/api/v1/pedido/listar/{referencia}`:**  Endpoint para listar pedido por referencia

        * Retorna un Objeto de tipo `PedidoResponseCompleteDTO`

            ```json
            {
            "referencia": "REF-1",
            "estado": "EN PREPARACION",
            "fechaRealizacion": "2025-05-21T00:46:40.583405",
            "fechaEntrega": null,
            "precioTotal": 40,
            "detallesPedido": [
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
            }
            ```
    * **`GET`** -> **`/api/v1/pedido/listar-pedidos`:**  Endpoint para listar todos los pedidos por 

        * Retorna un Objeto de tipo `List<PedidoResponseSimpleDTO>`

            ```json
            [
            {
                "referencia": "REF-1",
                "estado": "EN PREPARACION",
                "fechaRealizacion": "2025-05-21T00:46:40.583405",
                "fechaEntrega": null,
                "precioTotal": 40
            },
            {
                "referencia": "REF-2",
                "estado": "EN PREPARACION",
                "fechaRealizacion": "2025-05-21T00:47:47.35601",
                "fechaEntrega": null,
                "precioTotal": 212.97
            },
            {
                "referencia": "REF-3",
                "estado": "EN PREPARACION",
                "fechaRealizacion": "2025-05-21T02:52:38.571165",
                "fechaEntrega": null,
                "precioTotal": 230
            }
            ]
            ```

    * **`GET`** -> **`/api/v1/pedido/listar-completo`:**  Endpoint para listar todos los pedidos por 

        * Retorna un Objeto de tipo `List<PedidoResponseCompleteDTO>`

            ```json
            [
                {
                    "referencia": "REF-1",
                    "estado": "EN PREPARACION",
                    "fechaRealizacion": "2025-05-21T00:46:40.583405",
                    "fechaEntrega": null,
                    "precioTotal": 40,
                    "detallesPedido": [
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
                    "estado": "EN PREPARACION",
                    "fechaRealizacion": "2025-05-21T00:47:47.35601",
                    "fechaEntrega": null,
                    "precioTotal": 212.97,
                    "detallesPedido": [
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
                    "estado": "EN PREPARACION",
                    "fechaRealizacion": "2025-05-21T02:52:38.571165",
                    "fechaEntrega": null,
                    "precioTotal": 230,
                    "detallesPedido": [
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