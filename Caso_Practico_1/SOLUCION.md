# SOLUCION.md — Caso Práctico 1: EventApp

1. Endpoints implementados

Endpoints que se agregaron:
- `GET /eventos/categoria/{categoria}` esto es para que se muestre el nombre en el URL en la categoria en la que estamos, si estamos en musica sale en la URL /musica, con la parte de @pathvariable que toma esa categoria y se la pasa al service y ya este busca en la base de datos solo los eventos que coincidan 
- `GET /eventos/nuevo` esta seria la ruta que activa el nuevo evento con la vista de form.html para que el usuario digite las caracteristicas del nuevo evento
- `POST /eventos` cuando el usuario llena el formulario y le dio guardar el navegador manda la info a Evento con los datos y ese evento sin id tiene que pasar las validaciones y si no hay errores se guardan mientras se le asigna un id 
- `GET /eventos/{id}/editar` cuando le damos en editar sobre una card, carga el id de la card con los datos que tiene guardada para editarla 
- `POST /eventos/{id}` este se llama cuando el usuario edita un datos de un formulario, se mandan lo datos que el usuario escribio pero se validan los datos si estan en el formato correcto
- `POST /eventos/{id}/eliminar` se llama cuadndo el usuario confirma el borrado de una card, agarra el id del evento y con el service le pide que lo elimine de la base de datos 


2. Validaciones en la entidad Evento

se elegieron como validaciones por ejemplo el @NotBlank para que el campo no sea valido como vacio o null o que venga solo con espacios en blanco, es mas estricto que el "empty", por el otro lado "nombre" y "categoria" porque pense que un evento seria mas facil de identificar y estrictos, en cambio lugar y organizador probablemente puedan quedar pendientes o por cofirmar o algun otro tipo de contratiempo por permisos para el evento, 

el @NotNull para la fecha porque el notblack aplica solo para strings ademas de que aplica la restriccion de los espacios en blanco, al igual utilice @Future por que vi que es especificamente para usar fechas adelantadas a la de hoy para que no se creen eventos por error en 5 años atras 

agregue el @size(max) para limitar y reforzar el lenght que ya habia en las lineas de nombre, descripcion, lugar, categoria, organizador

el @positive significa mayor que cero estrictamente, y la idea del enunciado dice "cupo máximo > 0" — un evento con cupo 0 no tendría sentido, eso mismo se usa para CupoMaximo porque se necesita escribir una cantidad de cupos o se venderian infinitos o ninguno y tambien @PositiveOrZero que significa "cero o mayor" permite el 0 al igual que Precio y para cuposVendidos tiene sentido porque un evento que se fue creado y que todavía no vendió ninguna entrada

---

3. Modal de confirmación de borrado

por ejemplo con eliminar si se tiene 10 eventos se tendrian que hacer un modal para cada uno, puse una linea de codigo data-bs-toggle="modal" y ese modal ya lo incorpora al Html a todas las cards, a diferencia de los atributos data-bs-* que ya vienen de Bootstrap, data-id y data-nombre son atributos de html ya html con el id y con data-nombre se conecta con el modalEliminar y asi se integran en todas las cards, con el boton.getAttribute de data-id y data nombre se toma los dos valores del voton y los usa para rellenar el modal, ya luego que los lee el resultado final muestra el nombre correcto y el formulario de "eliminar" dentro del modal apuntta al endpoint de eliminar y el show.bs.modal es el que da la orden al modal para que se haga visible en la pantalla, el event.relatedtarget es la parte que dice que el elemento que causó el evento fue el boton "eliminar" y por eso el modal sabe de cual card vino la orden 

---

4. Decisiones técnicas propias

para el localdate se utilizo para las fechas de los eventos, asi no se admiten strings, lo dicho anteriormente sobre "lugar y organizador" que solo puse @size sin notblank porque consideraba que podian quedar en blanco, elegí tambien el endpoint por categoria porque es mas especifica ademas de que en Service ya tenian escrito un metodo para buscar por categoria asi era menos trabajo por hacer que lo hacia por nombre o fechas


---

5. Problemas que encontraste y cómo los resolviste

tuve problemas con todo, no entendia nada, me tome toda la semana para hacer este trabajo pero con tutoriales y viendo videos y reviendo videos de clases anteriores, utilice IA pero meramente para que me explicara conceptos, porque es mas facil aprender con el contexto del trabajo a un video de como hacer tal cosa en general a alguien que este personalmente ensima del trabajo, aparte de que tuve que ver varias veces repeticiones de la clase asi que tuve que agilizar el tiempo, ademas de que le pedi ayuda para surbi el archivo en github con codigo porque yo no se usar github pero pude lograr crear la carpeta y subir el archivo y los commits, lo que mas me costó integrar fue el R4 porque no sabia como hacer el form.html, al principio hice form-crear.html y form-editar.html pero al final lo pude integrar en uno solo form ademas de que me confundi en donde iba, tuve errores con SQL pero eso ya fue personal que se borro la contraseña del root localhost y tuve que configurarla de nuevo 
