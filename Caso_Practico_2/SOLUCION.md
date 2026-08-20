# SOLUCION.md — Caso Práctico 2: biblioapp

### 1. Cómo modelaste la relación Prestamo → Libro y por qué.

tuve que modelar la relacion de muchos a uno ya que un mismo libro puede ser prestado varias veces a lo largo del tiempo y en la base de datos esto se traduce como una clave foranea como libro_id pero en la tabla prestamos para que exista la relacion y da sentido a usar id en libro para que se use en muchos prestamos, ya luego la entidad prestamos guarda la referencia del objeto libro y esto hace uno pueda revisar mas facil sin consultar mucho en la base de datos usan como ejemplo prestamo.getLibro(), tambien esa entidad se marca como NOT NULL por que no pueden existir los prestamos sin libros

###  2. hace cada @PreAuthorize que agregaste y por qué esa regla y no otra.

se usó esa porque hace que el usuario y el administrador esten separados, asi los usuarios normales no tienen acceso a contenido del programa que queremos proteger o que ellos no manupulen, lo use en el formulario para crear un prestamo nuevo "prestamosController.formularioNuevo()" por que prestar un libro es una operacion del administrador o de los administradores y que hace la biblioteca, si un lector usa esa URL le salta el error 403 

tambien se usó en .registrar() y este guarda el prestamo en la base de datos y es tal vez el mas importanten por esa razon 

el .devolver() igual manera cambia los datos cuando se marca una devolucion, devulve el numero de libros de stock y eso es una operacion administrativa 

en librorestcontroller.crear() el cual es un endpoint para crear libros nuevos para el catalogo tambien tiene el seguro de autorizacion e¿pero solo para que el digamos bibliotecario pueda modificarlo, por que el GET del API es publico y se puede consultar 

y ya por ultimo a nivel de usuario para la plataforma ningun usuario comun puede ni crear, editar, eliminar, prestar o devolver, eso solo el bibliotecario, estan limitados a consultar el catalogo y hacer la peticion de pedir 

### 3. Cómo escribiste tu propia consulta JPQL del Requisito 5.3 (explicá el razonamiento, no solo pegues el código).

la consulta se tenia que hacer con 2 variables porque tenia que tener la fecha de devolucion y la fecha limite y si seguimos esa linea para que un libro cuente como atrasado las 2 tienen que cumplirse a la vez ya que si se entrega tarde la fecha limite sabra que esta tarde y la devolucion igual, tambien tuve que aprender a pesar de que es un lenguaje similar al de sql,  y el query tuve fallas y fue el paso mas tardio en fechaDevolucion debe quedar IS NULL y la fecha limite vencida de quedar < CURRENT_DATE y unidos por AND para que se cumplan y toma de la lista de prestamo la info de los libros y el resultado lo deja en prestamosAtrasados() y el "p" es una forma temporal de llamar a los objetos en prestamo y con la que inicia cada variable

### 4. Qué endpoints de tu API implementaste y qué código de estado devuelve cada uno.

se usaron el /api/libors para consultar la lista de libros y es publico, 
tambien se puede consultar los libros por id y ambos son GET. ese devuelve codigo 200 ok pero el de id es solo para admins y devolveria 404 si no es admin

la diferencia con /api/libros con metodo POST es que ese es para los bibliotecarios, salta el login.html y para los admins da 201

el de /api/prestamos/atrasados con GET devuelve 200 como en la captura de evidencias 

### 5. Cualquier decisión técnica adicional (ej. cómo calculaste la fecha límite del préstamo)

el calculo se hizo con .registrarPrestamos(), en prestamosService usando el LocalDate.now para que cuenta de hoy hasta 14 dias como decia el documento en R2 de usar 14 dias 

tambien tuve problemas con el ROL asi que lo saque de Usuario y lo puse fuera en entity ya que me estaba dando una redundancia en securityConfig por que el rol se usa tambien ahi y si rol estuviera dentro de usuario todas las clases tenian que llamarlo como usuario.rol.lector y fue mas facil solo como rol.lector 

ademas tuve problemas con la base de datos, con el seed-data.sql ya que al querer iniciar login con los parametros del README no me dejaba por el hash Bcrypt entonces tuve que generar un usuario que tuviera la contraseña del README ya encriptada y que la leyera como la correcta 