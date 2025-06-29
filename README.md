# Desafío - API de Procesamiento de Pedidos

## Descripción

API REST desarrollada en Spring Boot para procesar pedidos de forma asíncrona usando un ThreadPoolExecutor personalizado.

---

## Informe de rendimiento y optimización

### Análisis general

- Uso de un ThreadPoolExecutor con hasta 100 hilos y cola de 500 pedidos para alta concurrencia.
- Procesamiento asíncrono con CompletableFuture para no bloquear el servidor HTTP.
- Uso de ConcurrentHashMap para almacenar pedidos procesados de forma segura y eficiente.
- Simulación de latencia con espera aleatoria en el procesamiento sin afectar la respuesta inmediata.

### Mejoras propuestas

- Añadir manejo avanzado de errores y reintentos.
- Incorporar métricas y monitoreo con Spring Actuator.
- Configurar dinámicamente el ThreadPoolExecutor vía propiedades externas.
- Persistencia real de pedidos en base de datos.
- Validaciones con anotaciones estándar (JSR-380).

---

## Despliegue

### Requisitos

- Java 17 o superior
- Maven o Gradle
- Puerto 8080 libre (o configurar otro puerto)

### Pasos

1. Clonar el repositorio o descargar el código fuente.
2. Desde la raíz del proyecto, construir el jar:

bash
./mvnw clean package


### cURL portman:

curl --location 'http://localhost:8080/processOrder' \
--header 'Content-Type: application/json' \
--data '{"customerId": "Example",
"orderAmount": 20,
"orderItems": ["1","2"],
"orderId": "001"
}'

Enpoint:
POST /processOrder
Content-Type: application/json

Respuesta esperada
HTTP 201 Created "Pedido en procesamiento"



3. ### Prueba de Concurrencia para API /processOrder

Este documento explica cómo ejecutar un test de concurrencia para la API REST /processOrder de tu proyecto Java Spring Boot, simulando múltiples solicitudes concurrentes para evaluar el rendimiento.

---

## Requisitos Previos

- Tener ApacheBench (ab) instalado. En sistemas basados en Debian/Ubuntu:

bash
sudo apt-get install apache2-utils


Tener corriendo la aplicación en localhost en el puerto 8080 (configurable según tu proyecto).

Archivo JSON con el payload del pedido (por ejemplo order.json), con contenido como:

{
  "orderId": "12345",
  "customerId": "clienteX",
  "orderAmount": 150.0,
  "orderItems": ["item1", "item2"]
}

(ya esta incluido en el proyecto)


# Comando para ejecutar la prueba

ab -n 1000 -c 100 -p order.json -T application/json http://localhost:8080/processOrder
-n 1000 : Total de solicitudes a enviar (1000 pedidos).

-c 100 : Número de solicitudes concurrentes simultáneas (100).

-p order.json : Archivo con el JSON a enviar en cada POST.

-T application/json : Content-Type de las solicitudes.

URL : Endpoint REST a testear.



# Interpretación de resultados

- *Failed requests:* 0 (indica que no hubo fallos)
- *Requests per second:* Indica la capacidad de procesamiento del servidor bajo carga.
- *Time per request:* Tiempo promedio que toma procesar cada solicitud.
- *Connection Times:* Estadísticas de latencia para la conexión y procesamiento.

# Recomendaciones

- Ajustar -n y -c para probar diferentes niveles de carga.
- Modificar el archivo order.json para simular diferentes pedidos.
- Monitorear la consola o logs de la aplicación para verificar que no haya errores.
