# Lab 7 Arep 
En este taller implementaremos una arquitectura segura, donde se garantice autenticación, autorización e integridad de los servicios,
para probar estos atributos se implemento un servicio de login donde se guardaron los los usuarios y los hashes de las claves

### Iniciando
Antes de poder ejcutar y correr el codigo toca tener los siguientes prerequisitos

### Prerrequisitos
* Git 
* Java
* Maven

### Instalando el proyecto

Lo primero será traer del repositorio remoto el proyecto a la máquina local, para esto ejecutamos el siguiente comando por medio de consola.

```
git clone https://github.com/santiagoOsp01/LabArep7.git
```

luego de realizar esto compilamos en poyecto con el siguiente comando para poder ejecutarlo

```
mvn package
```
una vez ya este compilado podemos ya ejecutar nuestros dos servicios ya sea en la maquina local o en las maquinas virtuales,
pero no la podemos correr en maquinas asta que creemos los nuevos certificados qe tienen que tener el nombre de la
maquina a la que queremos comunicarnos, en este momento el certificado esta para que funcione de manera local

### Corriendo Localmente y corriendo los test de integracion

ahorra que ya tenemos nuestro proyecto podemos ejecutarlo con los siguientes comandos, pero primero debemos de estar adentro del directorio donde se encuentra nuestro proyecto y ya despues podemos ejecutar los comandos

```
java -cp "target/classes:target/dependency/*" edu.eci.co.m1webpage.WebServer
```

```
java -cp "target/classes:target/dependency/*" edu.eci.co.m2loginservices.loginServices
```
o tambien si contamos con un ide podemos correrlo ejecutando el main de las siguientes clases

WebServer

![image](https://github.com/santiagoOsp01/LabArep7/assets/111186366/030e619c-44ef-4ec1-b618-588bf9169d37)

loginServices

![image](https://github.com/santiagoOsp01/LabArep7/assets/111186366/1f876dde-4234-4582-8649-1bb1bf417d23)

Con esto ya estara corriendo ambos servicios de manera local y podemos ver en la siguiente url https://localhost:5000/

![image](https://github.com/santiagoOsp01/LabArep7/assets/111186366/8a73d691-68f9-4f03-a38e-7d3e8a45c2df)

y vemos que si ponemos la clave mal no nos dara entrada

![image](https://github.com/santiagoOsp01/LabArep7/assets/111186366/630a942a-0a95-4892-969e-5a30d968a67c)

y si porbamos con el usuario que ya esta quemado nos manda

![image](https://github.com/santiagoOsp01/LabArep7/assets/111186366/b112b132-767d-4c19-8a15-4b3f431b897e)

y si el servicio de la maquina que contiene el login seguro esta apagado nos sale el siguiente error

![image](https://github.com/santiagoOsp01/LabArep7/assets/111186366/d077a546-73dd-4b1b-b406-894954a3ec42)

en el codigo vemos los usuarios que estan quemados

![image](https://github.com/santiagoOsp01/LabArep7/assets/111186366/4163c6dd-ebd9-4dde-97d7-7fda4cb4e007)

### Corriendo AWS
primero creamos dos instancias de ec2 donde en cada una de ellas vamos a correr uno de los 2 servicios que tenemos 
en el siguiente video podemos ver el funcionamiento en la nube

https://github.com/santiagoOsp01/LabArep7/assets/111186366/43399fb2-7522-45e3-8359-b77e5b01ca9d

## Diseño

para poder realizar la arquitectura propuesta y tener 2 tipos de servicios se separaron los servicios en diferentes componenetes donde cada uno contiene un main que se debe correr individuamente, por eso tenemos uno que contiene la pagina del login, y la pagina cuando el login sea aceptado, y tambien el servicio de login seguro que se encarga de toda la logica del login.

### Arquitectura

La arquitectura que se realizo fue la siguiente.

![image](https://github.com/santiagoOsp01/LabArep7/assets/111186366/9dbbf50f-5159-465b-8c03-c313c772bd27)

en esta la primera maquina contiene las pagina web y el lector de url, aqui es donde el usuario pondra su usuario y clave y este con el lector seguro url, va a la segunda maquina es la que contiene el login seguro donde esta le lega el usuario y clave ingresados en la pagina y el verifica que eistan y sean correctos y da el bueno del login, podemos observar que la comunicacion entre las maquinas al igual entre usuario y maquina se realizan con https, y esto como ya lo mencionamos lo logramos con los certificados que nosotros mismos firmamos, esto para el el servicio expuesto sea seguro

### Extensibilidad

como ya tenemos los certificados, es relativamente sencillo agregar los servicios ya que despues del login podemos implementar diferentes tipos de servicios, ya que no tenemosuna restriccion, entonces con los certificados se pueden acceder a diferentes paginas y apis, y estas no nos deberian de generar algun problema

### Version

1.0-SNAPSHOT

### Autores

Santiago Ospina Mejia

### Licencia

GNU General Public License family

### Agradecimientos

* Luis Daniel Benavides Navarro



