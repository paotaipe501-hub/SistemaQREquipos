# Sistema Informático de Lectura y Procesamiento de Códigos QR para Configuración de Equipos

Aplicación de escritorio en Java que identifica y gestiona equipos mediante códigos QR.
Cada equipo tiene un **ID** y una etiqueta QR que guarda ese ID. Una **cámara web USB externa**
lee la etiqueta y el sistema muestra en pantalla todos los datos y la configuración del equipo;
desde ahí se puede sacar su **reporte** (JasperReports).

**Universidad Técnica de Ambato · FISEI · Carrera de Software · Cuarto "A"**

## Integrantes

| Integrante | Módulo |
|---|---|
| Paola Alexandra Taipe Tixilema | Estructura del proyecto, ventana principal y lectura con la cámara web USB |
| Diana Pamela Tituaña Calapiña | Base de datos, conexión, modelo y acceso a datos (DAO) |
| Alina Ortiz | Gestión de equipos: ingresar, modificar y dar de baja |
| Julio Zurita | Generación y lectura de códigos QR, etiquetas QR |
| Robert M | Historial de lecturas y reporte de equipo por ID |

## Funciones

- **CRUD de equipos:** ingresar, modificar y dar de baja (con motivo; el equipo no se borra y se puede reactivar).
- **Lectura con cámara web USB:** al mostrar la etiqueta QR se despliegan todos los datos del equipo.
- **Reporte por ID:** se escribe el ID del equipo y se ve su reporte en JasperViewer o se exporta a PDF.
- **Etiquetas QR:** generar, guardar en PNG e imprimir la etiqueta de cada equipo.
- **Historial:** todas las lecturas realizadas, con búsqueda.

## Tecnologías

Java · NetBeans · Swing (JFrame Form) · MySQL (XAMPP) · JasperReports (iReport) · webcam-capture · ZXing

## Cómo ejecutarlo

1. Iniciar **Apache** y **MySQL** en XAMPP.
2. En phpMyAdmin → **Importar**, cargar `basedatos/script_bd.sql` (crea la base `bd_equipos`).
3. Abrir el proyecto en NetBeans (*File → Open Project*). Las librerías ya están en la carpeta `lib`.
4. Conectar la cámara web USB y ejecutar con **Run Project**.
5. Para probar, usar las etiquetas de la carpeta `etiquetas_qr/`.