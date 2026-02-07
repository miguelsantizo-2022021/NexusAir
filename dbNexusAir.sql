drop database if exists dbnexus_air_in5cm;
create database dbnexus_air_in5cm;
use dbnexus_air_in5cm;

-- =============================================
-- estructura de tablas
-- =============================================

create table proveedores(
    id_proveedor int auto_increment not null,
    nombre_proveedor varchar(60) not null,
    telefono_proveedor int not null,
    direccion varchar(100) not null,
    email_proveedor varchar(100) not null,
    primary key pk_id_proveedor(id_proveedor)
);

create table empleados(
    id_empleado int auto_increment not null,
    nombre_empleado varchar(60) not null,
    apellido_empleado varchar(60) not null,
    puesto_empleado varchar(20) null,
    email_empleado varchar(100) not null,
    primary key pk_id_empleado(id_empleado)
);

create table productos(
    id_producto int auto_increment not null,
    nombre_producto varchar(60) not null,
    modelo varchar(50) not null,
    categoria enum('calefactor', 'aire acondicionado') not null,
    precio_compra double not null,
    precio_venta double not null,
    stock int not null, 
    id_proveedor int not null,
    primary key pk_id_producto(id_producto),
    constraint fk_producto_proveedor foreign key (id_proveedor)
    references proveedores(id_proveedor) on delete cascade
);

create table ventas(
    id_venta int auto_increment not null,
    fecha_venta date not null,
    cantidad int not null,
    total double not null,
    id_empleado int not null,
    id_producto int not null,
    primary key pk_id_venta(id_venta),
    constraint fk_ventas_empleado foreign key (id_empleado)
    references empleados(id_empleado) on delete cascade,
    constraint fk_ventas_productos foreign key (id_producto)
    references productos(id_producto) on delete cascade
);

-- =============================================
-- procedimientos almacenados
-- =============================================

-- crear proveedor
delimiter $$
create procedure sp_agregarproveedor(
    in _nombre varchar(60), in _tel int, in _dir varchar(100), in _email varchar(100)
)
begin
    insert into proveedores (nombre_proveedor, telefono_proveedor, direccion, email_proveedor)
    values (_nombre, _tel, _dir, _email);
end $$
delimiter ;

-- crear empleado
delimiter $$
create procedure sp_agregarempleado(
    in _nombre varchar(60), in _apellido varchar(60), in _puesto varchar(20), in _email varchar(100)
)
begin
    insert into empleados (nombre_empleado, apellido_empleado, puesto_empleado, email_empleado)
    values (_nombre, _apellido, _puesto, _email);
end $$
delimiter ;

-- crear producto
delimiter $$
create procedure sp_agregarproducto(
    in _nombre varchar(60), in _modelo varchar(50), in _cat enum('calefactor', 'aire acondicionado'),
    in _p_compra double, in _p_venta double, in _stock int, in _id_prov int
)
begin
    if not exists (select 1 from productos where modelo = _modelo) then
        insert into productos (nombre_producto, modelo, categoria, precio_compra, precio_venta, stock, id_proveedor)
        values (_nombre, _modelo, _cat, _p_compra, _p_venta, _stock, _id_prov);
    end if;
end $$
delimiter ;

-- crear venta
delimiter $$
create procedure sp_registrarventa(
    in _fecha date, in _cantidad int, in _total double, in _id_emp int, in _id_prod int
)
begin
    insert into ventas (fecha_venta, cantidad, total, id_empleado, id_producto)
    values (_fecha, _cantidad, _total, _id_emp, _id_prod);
end $$
delimiter ;

-- listar proveedores
delimiter $$
create procedure sp_listarproveedores()
begin
    select * from proveedores;
end $$
delimiter ;

-- listar empleados
delimiter $$
create procedure sp_listarempleados()
begin
    select * from empleados;
end $$
delimiter ;

-- listar productos
delimiter $$
create procedure sp_listarproductos()
begin
    select p.*, prov.nombre_proveedor 
    from productos p 
    inner join proveedores prov on p.id_proveedor = prov.id_proveedor;
end $$
delimiter ;

-- actualizar producto
delimiter $$
create procedure sp_actualizarproducto(
    in _id int, in _nuevo_nombre varchar(60), in _nuevo_precio double, in _nuevo_stock int
)
begin
    update productos 
    set 
        nombre_producto = coalesce(_nuevo_nombre, nombre_producto), 
        precio_venta = coalesce(_nuevo_precio, precio_venta), 
        stock = coalesce(_nuevo_stock, stock)
    where id_producto = _id;
end $$
delimiter ;

-- borrar producto
delimiter $$
create procedure sp_eliminarproducto(in _id int)
begin
    delete from productos where id_producto = _id;
end $$
delimiter ;

-- =============================================
-- carga de datos
-- =============================================

call sp_agregarproveedor('carrier global', 5551010, 'florida, usa', 'ventas@carrier.com');
call sp_agregarproveedor('samsung hvac', 5552020, 'seoul, korea', 'support@samsungclima.com');
call sp_agregarproveedor('daikin industries', 5553030, 'osaka, japan', 'info@daikin.com');
call sp_agregarproveedor('lennox international', 5554040, 'texas, usa', 'contacto@lennox.com');
call sp_agregarproveedor('mitsubishi electric', 5555050, 'tokyo, japan', 'hvac@mitsubishi.com');
call sp_agregarproveedor('trane technologies', 5556060, 'dublin, ireland', 'sales@trane.com');
call sp_agregarproveedor('honeywell', 5557070, 'north carolina, usa', 'parts@honeywell.com');
call sp_agregarproveedor('rheem manufacturing', 5558080, 'georgia, usa', 'distribucion@rheem.com');
call sp_agregarproveedor('york international', 5559090, 'pennsylvania, usa', 'soporte@york.com');
call sp_agregarproveedor('lg electronics', 5550011, 'seoul, korea', 'clima@lg.com');

call sp_agregarempleado('carlos', 'mendoza', 'gerente', 'c.mendoza@nexusair.com');
call sp_agregarempleado('ana', 'lucia', 'ventas', 'a.lucia@nexusair.com');
call sp_agregarempleado('roberto', 'gomez', 'tecnico', 'r.gomez@nexusair.com');
call sp_agregarempleado('sofia', 'reyes', 'ventas', 's.reyes@nexusair.com');
call sp_agregarempleado('luis', 'torres', 'tecnico', 'l.torres@nexusair.com');
call sp_agregarempleado('elena', 'perez', 'administracion', 'e.perez@nexusair.com');
call sp_agregarempleado('mario', 'estrada', 'logistica', 'm.estrada@nexusair.com');
call sp_agregarempleado('beatriz', 'solares', 'ventas', 'b.solares@nexusair.com');
call sp_agregarempleado('jorge', 'lopez', 'tecnico', 'j.lopez@nexusair.com');
call sp_agregarempleado('carmen', 'ortiz', 'recepcion', 'c.ortiz@nexusair.com');

call sp_agregarproducto('split inverter', 's-1000', 'aire acondicionado', 350.00, 550.00, 20, 2);
call sp_agregarproducto('calefactor ceramico', 'heat-x', 'calefactor', 45.00, 85.00, 50, 7);
call sp_agregarproducto('central air unit', 'v-force', 'aire acondicionado', 1200.00, 1850.00, 5, 1);
call sp_agregarproducto('calefactor de torre', 't-200', 'calefactor', 60.00, 110.00, 30, 8);
call sp_agregarproducto('mini split premium', 'ms-88', 'aire acondicionado', 400.00, 625.00, 15, 10);
call sp_agregarproducto('cassette unit', 'c-99', 'aire acondicionado', 800.00, 1300.00, 8, 3);
call sp_agregarproducto('calefactor industrial', 'ind-heat', 'calefactor', 250.00, 450.00, 10, 4);
call sp_agregarproducto('portable ac', 'go-cool', 'aire acondicionado', 200.00, 375.00, 25, 5);
call sp_agregarproducto('termostato inteligente', 'smart-t', 'calefactor', 30.00, 75.00, 100, 7);
call sp_agregarproducto('fan coil unit', 'fc-12', 'aire acondicionado', 500.00, 890.00, 12, 6);

call sp_registrarventa('2026-01-15', 2, 1100.00, 2, 1);
call sp_registrarventa('2026-01-16', 5, 425.00, 4, 2);
call sp_registrarventa('2026-01-18', 1, 1850.00, 8, 3);
call sp_registrarventa('2026-01-20', 3, 330.00, 2, 4);
call sp_registrarventa('2026-01-22', 2, 1250.00, 4, 5);
call sp_registrarventa('2026-01-25', 1, 1300.00, 8, 6);
call sp_registrarventa('2026-01-28', 4, 300.00, 2, 9);
call sp_registrarventa('2026-02-01', 1, 450.00, 4, 7);
call sp_registrarventa('2026-02-02', 2, 750.00, 8, 8);
call sp_registrarventa('2026-02-03', 1, 890.00, 2, 10);