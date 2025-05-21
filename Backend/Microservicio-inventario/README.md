# Microservicio: `Inventario`

* Este microservicio es el encargado:

    * Agregar Producto
    * Listar Producto 
    * Listar Todo los Productos
    * Listar Productos Por Ids

    ## EndPonits: `InventarioController`

    * **`POST`** -> **`/api/v1/inventario/agregar`:**  Endpoint para agregar un producto al inventario recibe un Objeto de tipo `InventarioAddDTO` con el formato:

        ```json
        {
            "cantidad": 10,
            "productoAddDTO": {
                "referencia": "REF001",
                "nombre": "Camisa Casual",
                "talla": "M",
                "tipo": "Ropa",
                "subtipo": "Camisa",
                "target": "Hombre",
                "color": "Azul",
                "precioUnid": 25.99,
                "precioVenta": 30.99,
                "detalleProductoAddDTO": {
                    "descripcion": "Camisa de algodón de manga larga.",
                    "composicion": "100% algodón",
                    "pais": "España"
                }
            }
        }
        ```

        * Retorna un Objeto de tipo `InventarioResponseDTO`

            ```json
            {
                "cantidad": 7,
                "productoInfoDTO": {
                    "referencia": "REF001",
                    "nombre": "Camisa Casual",
                    "talla": "M",
                    "subtipo": "Camisa",
                    "target": "Hombre",
                    "color": "Azul",
                    "precioUnid": 25.99,
                    "precioVenta": 30.99
                }
            }
            ```
    
    * **`GET`** -> **`/api/v1/inventario/listar`:**  Endpoint para buscar un producto del inventario recibe un Objeto de tipo `ProductoSearchDTO` con el formato:

        ```json
        {
        "referencia": "REF001",
        "talla": "M",
        "color": "Azul"
        }
        ```
    
        * Retorna un Objeto de tipo `InventarioResponseDTO`

            ```json
            {
                "cantidad": 7,
                "productoInfoDTO": {
                    "referencia": "REF001",
                    "nombre": "Camisa Casual",
                    "talla": "M",
                    "subtipo": "Camisa",
                    "target": "Hombre",
                    "color": "Azul",
                    "precioUnid": 25.99,
                    "precioVenta": 30.99
                }
            }
            ```
    
    * **`GET`** -> **`/api/v1/inventario/listar-todos`:**  Endpoint para listar todo los productos del inventario 

        ```json
        [
            {
                "cantidad": 7,
                "productoInfoDTO": {
                "referencia": "REF001",
                "nombre": "Camisa Casual",
                "talla": "M",
                "subtipo": "Camisa",
                "target": "Hombre",
                "color": "Azul",
                "precioUnid": 25.99,
                "precioVenta": 30.99
                }
            },
            {
                "cantidad": 0,
                "productoInfoDTO": {
                "referencia": "REF002",
                "nombre": "Pantalón Deportivo",
                "talla": "L",
                "subtipo": "Pantalon",
                "target": "Mujer",
                "color": "Negro",
                "precioUnid": 35.5,
                "precioVenta": 40
                }
            }
        ]
        ```
    
        
    * **`GET`** -> **`/api/v1/inventario/listar-productos`:**  Endpoint para listar todo los productos que tenga el id dado en Objeto `List<Long>` 

        * Example List<Long> = List.of(1L);

            ```json
            [
                {
                    "cantidad": 7,
                    "productoInfoDTO": {
                    "referencia": "REF001",
                    "nombre": "Camisa Casual",
                    "talla": "M",
                    "subtipo": "Camisa",
                    "target": "Hombre",
                    "color": "Azul",
                    "precioUnid": 25.99,
                    "precioVenta": 30.99
                    }
                }
            ]
            ```
        > **Nota:** Este es un EndPoint que utiliza el Microservicio `Pedido`

    ## EndPonits: `CatalogoController`

    * **`GET`** -> **`/api/v1/catalogo/filtros`:**  Endpoint que me retorna un Objeto de tipo `Map<String, List<String>>` el cual la key es el atributo tipo y el value es el atributo es una lista de los subtipo que tiene la key.

        ```json
        {
            "Calzado": [
                "Zapatillas"
            ],
            "Ropa": [
                "Camisa",
                "Chaqueta",
                "Pantalon"
            ],
            "Accesorios": [
                "Gorra"
            ]
        }
        ```
    
    * **`GET`** -> **`/api/v1/catalogo/{target}`:**  Endpoint que me retorna un Objeto de tipo `Page<ProductoAgrupadoDTO>` el cual recibe como parametros el targety como `@RequestParam` tipo y subtipo (Opcionales).

        ```json
        {
        "content": [
            {
            "referencia": "REF004",
            "nombre": "Chaqueta de Invierno",
            "id": 4,
            "talla": "S",
            "color": "Rojo",
            "precio": 95,
            "tallas": [
                "S"
            ],
            "colores": [
                "Rojo"
            ],
            "variantes": [
                {
                "id": 4,
                "talla": "S",
                "color": "Rojo",
                "precio": 95,
                "detalle": {
                    "descripcion": "Chaqueta de invierno con aislamiento térmico.",
                    "composicion": "100% poliéster",
                    "pais": "Francia"
                }
                }
            ]
            }
        ],
        "pageable": {
            "pageNumber": 0,
            "pageSize": 10,
            "sort": {
            "sorted": true,
            "empty": false,
            "unsorted": false
            },
            "offset": 0,
            "paged": true,
            "unpaged": false
        },
        "last": true,
        "totalElements": 1,
        "totalPages": 1,
        "size": 10,
        "number": 0,
        "sort": {
            "sorted": true,
            "empty": false,
            "unsorted": false
        },
        "first": true,
        "numberOfElements": 1,
        "empty": false
        }
        ``` 
