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

# Volver al directorio anterior
cd ../../ || exit
 
