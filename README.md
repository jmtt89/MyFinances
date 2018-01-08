# Proyecto final de curso  <br/>  Android: Fundamentos de Programación  <br/> (septiembre-diciembre 2017)


## Nombre de la aplicación: 
My Finances

## Autor: 
Torres Tovar, Jesus M

## Qué hace la aplicación
La aplicación es un manejador de finanzas personal que simplifica la visualización de entradas y gastos de dinero en una interfaz sencilla y cómoda, también permite agregar “presupuesto” por categorias y notifica al usuario en caso de excederlo. Las categorías de gastos e ingresos son extensibles por el usuario. También permite visualizar de manera directa el balance diario así como los presupuestos o límites de gastos mensuales. 
Para simplificar la visualización de los datos se utilizan gráficos de Pie, Barras y Líneas que que permiten ver de una manera directa el comportamiento mensual de las finanzas. 
La interfaz de la aplicación sigue las guias de Material Design propuestas por Google por lo que se presenta una interfaz limpia y “enfocada en lo que importa” que es el manejo de las finanzas, dejando de lado todas las 
Esta aplicación está pensada para todas las personas que buscan una manera directa y no intrusiva de llevar registro de los gastos e ingresos que realizan para poder manejar mejor las finanzas personales. 

## A destacar
 - El uso de Gráficos para representar la información.
 - El diseño intuitivo de la aplicación.
 - El uso de Material Design para presentar la información de una manera ordenada. 
 - Es una aplicación completamente offline.
 - No requiere permisos de los usuarios para realizar ninguna acción. 
 - Todos los recursos de la aplicación son Open Source
 - Uso de múltiples librerías 
 - Código legible y extensible por uso de Arquitecturas y Patrones.

## Cómo lo hace
 - La aplicación utiliza una Arquitectura MVC que separa el acceso a los datos de la interfaz del usuario, la capa de datos se mantiene separada utilizando una capa de controlador que permite el acceso a los datos, esta capa a su vez utiliza el patrón Singleton para su difusión a través de toda la aplicación. Las capas están separadas en cuatro paquetes: model,  dataBase, controller, view. 
 - El paquete model almacena todos los objetos que se utilizan en la aplicación.
 - El paquete dataBase es el que tiene permitido acceder a las tablas y realizar queries.
 - El paquete controller almacena el objeto singleton Controller que permite comunicar las vistas con la base de datos
 - El paquete view mantiene todas las actividades, fragments y adapters de la aplicación, en esta seccion es donde la mayor cantidad del código exclusivo de android se utiliza. 
 - El manejo y almacenamiento de datos se realiza mediante la biblioteca SugarORM esta absorbe gran parte de la complejidad del uso de SQL  y la generación de tablas y queries, aunque los más complejos (basados en tiempo principalmente) require realizarlos manualmente. Fue necesario estudiar bien el API ya que la documentación es bastante básica y no explica detalles importantes para realizar querys complejos y filtros sobre los datos. 
 - Los gráficos son generados utilizando la biblioteca MPAndroidChart para este fin, esta biblioteca no se encarga del manejo de datos solo de la representación gráfica de los mismos. Por lo que luego de consultar el API oficial para la generación de los gráficos fue necesario realizar el mapeo de los objetos a las estructuras de datos que esta utiliza.
 - Para la generación del contenido periodico, contenido que se debe agregar mensual o semanalmente de manera automatizada, se utilizó la librera Android-job se decidió utilizar una biblioteca porque esto ha cambiado bastante en las últimas versiones de android y para proveer una mayor compatiblidad lo mejor es utilizar una biblioteca que automáticamente utilice los API indicados dependiendo de la versión de Android en la que se esté instalando. 
 - Para ayudar con la el desarrollo de la interfaz y la conexión con el código se hace uso de la biblioteca Butterknife esta biblioteca simplifica enormemente la escritura de código repetitivo que es necesario para hacer las conexiones y los eventos de click en las interfaces. 
 - Se hacen uso también de dos bibliotecas menores que solo son para generar un selector de monedas (currency-picker-android) y un selector de colores (colorpicker), estas solo simplifican la creación de los dialog indicados. 
 - Se hace uso de una biblioteca que simplifican la generación del recycler view con headers y footer y agregar múltiples secciones en la misma lista SectionedRecyclerViewAdapter  esta biblioteca solo fue utilizada en la sección de “Balance” donde se agregan los gastos e ingresos ya que estos son agrupados en secciones por el dia en que son registrados. Esta es una de las ventanas más importantes de la aplicación ya que es la que permite agregar la información.  
 - Por último se utiliza la biblioteca Android-fab para generar un botón circular extensible que al hacerle click muestra dos botones mas, esta adición ayuda enormemente en la agilidad de la aplicación ya que con solo dos touch es posible agregar un ingreso o un gasto de manera intuitiva.
 - Cuando el app se inicializa la primera vez carga ciertas categorías predefinidas con sus iconos y colores, estas son extensibles posteriormente. . 

## Descripción de funcionamiento
	Cuando el app se inicializa la primera vez cargan las categorías predefinidas pero todos los datos de Ingresos y egresos estan vacios, por lo que es necesario recien al abrir la aplicación ir a la pestaña de “Balance” y proceder a agregar las transacciones. Las transacciones se muestran agrupadas por el dia en el que se llevaron a cabo, mostrando además información de cantidad de la transacción y categoría a la que pertenece. 
  
  Luego de alimentar el app con múltiples transacciones puede revisar la pestaña de “Budget” en esta se crean los presupuestos mensuales por categoría o si lo desea de manera global. Estos presupuestos son agregados a una lista que los muestra todos en formato de “tarjeta” con una línea de progreso que indica cuanto porcentaje queda hasta pasar el limite.
  
  Por último la pestaña mas importante pero también la que tiene menos sentido hasta que se han agregado múltiples entradas, la pestaña de Dashboard, esta muestra los gráficos que permiten visualizar de manera rápida el estado actual de nuestras finanzas. Son en total 5 tarjetas que contienen la siguiente información:
    - Overview. El Overview es muestra los ingresos y egresos globales que se han registrado en el app
    - Expenses by Category. Muestra las tres categorías con más gastos de las registradas en un gráfico de tarta con su leyenda a la derecha.
    - Monthly Budget, Muestra el máximo presupuesto mensual junto con el gasto total hasta el momento, también agrega un progessbar que se encuentra llena porcentualmente y muestra debajo de la misma cuanto dinero queda para alcanzar el presupuesto.
    - Daily Balance, el balance diario en una gráfico de barras que muestra si está disminuyendo o aumentando en relacion a dias anteriores. 
    - Monthly Balance, a manera de resumen muestra la cantidad de ingresos y egresos en el mes actual y representa esto con un gráfico de barras. 

Adicionalmente de toda esta información, se mantiene el balance global en la parte superior de esta manera con un simple vistazo se puede saber el estado de finanzas actual. 

## Extensiones, Complementos y Bugs

Por motivos de tiempo me deje en planificación bastante funcionalidades inicialmente previstas como agregar la posibilidad de manejar diferentes cuentas, posibilidad de cambiar de moneda y agregar la conversión al momento y editar las posibilidades de tarjetas en el “overview” así como agregar accesos rápidos para versiones nuevas de android para agregar gastos o ingresos. 

Tampoco pude cumplir las traducciones del app a Español, aunque el app es bastante intuitivo ya que para casi todo busca utilizar iconos. 

En cuanto a bugs, en algunas resoluciones muy pequeñas puede dar problemas de espacio ya que la sección del encabezado ocupa mucho espacio de la interfaz.


## Captura de pantallas:

https://docs.google.com/drawings/d/17tR4jrq8gaGRg2ToyXVym4-YKzmKFqJs_8THFkYXg-k/edit?usp=sharing

## Licencia
MIT
