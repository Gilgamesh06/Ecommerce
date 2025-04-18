#!/bin/bash

# Cambiar al directorio del microservicio de inventario
cd Microservicio-inventario/inventario || exit

# Dar permisos de ejecución al script mvnw
chmod +x mvnw

# Ejecutar mvnw
./mvnw package

# Cambiar al directorio del microservicio de venta
cd ../../Microservicio-venta/venta || exit

# Dar permisos de ejecución al script mvnw
chmod +x mvnw

# Ejecutar mvnw
./mvnw package

# Cambiar al directorio del microservicio de pedido
cd ../../Microservicio-pedido/pedido || exit

# Dar permisos de ejecución al script mvnw
chmod +x mvnw

# Ejecutar mvnw
./mvnw package

# Cambiar al directorio del microservicio de pedido
cd ../../Microservicio-carrito/carrito || exit

# Dar permisos de ejecución al script mvnw
chmod +x mvnw

# Ejecutar mvnw
./mvnw package

# dar permisos
chmod +x wait-for-redis.sh


# Volver al directorio anterior
cd ../../ || exit
 
