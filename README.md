# Customer Info REST

Customer Info REST es un servicio RESTful desarrollado en **Spring Boot** que permite consultar información básica de clientes. Este proyecto implementa una arquitectura **hexagonal basada en dominio**, lo que garantiza una separación clara de responsabilidades y facilita el mantenimiento y escalabilidad del sistema.

---

## **Características Principales**
- Consultar información básica de clientes.
- Validación estricta de entradas.
- Manejo de excepciones personalizadas.
- Implementación de arquitectura hexagonal.
- Tests unitarios y de integración.
- Gestión de errores y respuestas estándar.

---

## **Tecnologías Utilizadas**
- **Java 21**
- **Spring Boot 3.4.0**
- **Mockito** para pruebas unitarias.
- **Maven** para gestión de dependencias.
- **MockMvc** para pruebas de integración.

---

## **Estructura del Proyecto**
El proyecto sigue una arquitectura **hexagonal**, separando las capas en:

1. **Dominio**:
   - Entidades principales (`Customer`).
   - Excepciones (`CustomerNotFoundException`, `InvalidArgumentException`).
   - Contratos (`CustomerRepository`).

2. **Aplicación**:
   - Servicios (`CustomerQueryService`) que implementan la lógica de negocio.
   - Orquestan las interacciones entre dominio e infraestructura.

3. **Infraestructura**:
   - Controlador REST (`CustomerController`).
   - Manejador global de excepciones (`GlobalExceptionHandler`).
   - Interfaces externas y adaptadores.

---

## **Endpoints**
### **1. Obtener Información del Cliente**
- **URL:** `/api/v1/customers`
- **Método:** `GET`
- **Parámetros de Entrada:**
  - `documentType`: Tipo de documento (`C` para cédula, `P` para pasaporte).
  - `documentNumber`: Número de documento.
- **Respuestas:**
  - **200 OK:** Cliente encontrado.
  - **400 Bad Request:** Tipo de documento inválido.
  - **404 Not Found:** Cliente no encontrado.
  - **500 Internal Server Error:** Error inesperado.

#### **Ejemplo de Respuesta Exitosa**
```json
{
  "success": true,
  "message": "Operation successful",
  "data": {
    "firstName": "Pepe",
    "lastName": "Pérez",
    "phone": "123456789",
    "address": "Calle Falsa 123",
    "city": "Cali"
  }
}
