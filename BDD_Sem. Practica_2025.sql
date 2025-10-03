CREATE DATABASE BDD_SPI_2025;

USE BDD_SPI_2025;


/* Tablas para Gestión de Películas*/
CREATE TABLE Pelicula (
    id_pelicula INT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(100) NOT NULL,
    duracion VARCHAR(15) NOT NULL,
    director VARCHAR(50) NOT NULL,
    genero VARCHAR(30) NOT NULL,
    fecha_estreno VARCHAR(20) NOT NULL
);

CREATE TABLE Funcion (
    id_funcion INT PRIMARY KEY AUTO_INCREMENT,
    id_pelicula INT NOT NULL,
    fecha DATE NOT NULL,
    esta_disponible BOOLEAN NOT NULL,
    FOREIGN KEY (id_pelicula) REFERENCES Pelicula(id_pelicula)
);

CREATE TABLE Formato (
    id_formato INT PRIMARY KEY AUTO_INCREMENT,
    id_funcion INT NOT NULL,
    tipo_formato VARCHAR(20) NOT NULL,
    valor_entrada INT NOT NULL,
    esta_disponible BOOLEAN NOT NULL,
    FOREIGN KEY (id_funcion) REFERENCES Funcion(id_funcion)
);

CREATE TABLE Horario (
    id_horario INT PRIMARY KEY AUTO_INCREMENT,
    id_formato INT NOT NULL,
    hora_funcion TIME NOT NULL,
    ocupacion INT NOT NULL,
    esta_disponible BOOLEAN NOT NULL,
    FOREIGN KEY (id_formato) REFERENCES Formato(id_formato)
);

/* Tabla para Gestión de Usuario*/
CREATE TABLE Usuario (
	id_usuario INT PRIMARY KEY AUTO_INCREMENT,
    nombre_usuario VARCHAR(50) NOT NULL,
    contrasena VARCHAR(50) NOT NULL,
    tipo_usuario VARCHAR(15) NOT NULL
);


/* Inserción, consulta y borrado de registro*/
/*Insercion de datos*/
INSERT INTO Pelicula (titulo, duracion, director, genero, fecha_estreno) 
VALUES ('Los 4 Fantásticos: Primeros Pasos', '115 min', 'Matt Shakman', 'Accion/Ciencia Ficción', '2025-07-24');

INSERT INTO Funcion (id_pelicula, fecha, esta_disponible) 
VALUES (1, '2025-10-10', TRUE), (1, '2025-10-11', TRUE), (1, '2025-10-12', TRUE);

INSERT INTO Formato (id_funcion, tipo_formato, valor_entrada, esta_disponible) 
VALUES (1, '2D', 8000, TRUE), (2, '3D', 12000, TRUE), (3, 'XD', 15000, TRUE);

INSERT INTO Horario (id_formato, hora_funcion, ocupacion, esta_disponible) 
VALUES (1, '18:00:00', 0, TRUE), (2, '20:00:00', 0, TRUE), (3, '22:00:00', 0, TRUE);

INSERT INTO Usuario (id_usuario, nombre_usuario, contrasena, tipo_usuario) 
VALUES (1, 'admin_cinemotion', 'admin456', 'Admin'), (2, 'user_test_cliente', 'pass1234', 'Cliente') , (3, 'user_test_2', 'contraseña1234', 'Cliente');


/*Consulta de datos*/
SELECT 
    p.titulo AS 'Titulo',
    p.duracion AS 'Duracion',
    p.director AS 'Director',
    p.genero AS 'Género',
    p.fecha_estreno AS 'Fecha de Estreno',
    f.fecha AS 'Fecha de la Funcion',
    fo.tipo_formato AS 'Formato',
    fo.valor_entrada AS 'Valor Entrada',
    h.hora_funcion AS 'Hora de Funcion',
    h.ocupacion AS 'Ocupación'
FROM Pelicula p
JOIN Funcion f ON p.id_pelicula = f.id_pelicula
JOIN Formato fo ON f.id_funcion = fo.id_funcion
JOIN Horario h ON fo.id_formato = h.id_formato
ORDER BY p.titulo, f.fecha, fo.tipo_formato, h.hora_funcion;


SELECT 
    id_usuario AS 'ID Usuario',
    nombre_usuario AS 'Nombre Usuario',
    contrasena AS 'Contraseña',
    tipo_usuario AS 'Rol en Sistema'
FROM
    Usuario;

/*Borrado de datos*/
DELETE FROM Usuario WHERE id_usuario = '3';
SELECT * FROM Usuario;