# metodologia 

principio utilizado 

SOLID

patron creacional

BUILDER

# estuctura del codigo

- Desguace express: programa principal

CONTROLLER: recibe los llamados http y da respuestas positivas

DTO: estructuras de clases auxiliares para usos varios

ENTITIES: clases que sirven de estructura para la base de datos

EXCEPTION: administra los problemas que ocurren a lo largo del codigo para generar mensajes 400

FUNCTIONALITIES funcionalidades extra que se usan para un mejor funcionamiento

Beans se encarga de habilitar la coneccion mediante RestTemplate para conectar a servicio externo

REPOSITORIES gestiona las peticiones a la base de datos, en este caso postgreSQL mediante HQL

SERVICES contiene la logica del programa

ServiceDesguace administra el contenido de las solicitudes para hacer solicitudes a la bd o llamar a SendEmail

ServiceSendEmail se encarga de enviar mensajes a un servicio externo mediante HTTP

- sendEmail

microservicio que se encuarga de recibir solicitudes HTTP, hace la simulacion de enviar el mensaje y da una respuesta

# observaciones para ejecutar el codigo

crear una base de datos en PostgreSQL DesguaceExpress (las tablas se crean automaticamente)

asimismo en palication.properties 

ejustes para la coneccion con la base de datos DesguaceExpress

- usuario postgres
- clave admin
- puerto 5432

ejustes para la coneccion con la aplicacion mediante HTTP

- puerto 8082 (DesguaceExpress)
- puerto 8081 (sendEmail)

tomar en cuenta para algun ajuste en las propiedades

dentro de DesguaceExpress se encuentra sendEmail que es el microservicio



