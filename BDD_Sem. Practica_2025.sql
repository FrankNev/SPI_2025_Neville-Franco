CREATE DATABASE BDD_SPI_2025;

USE BDD_SPI_2025;


/* Tablas para Gestión de Películas*/
CREATE TABLE Pelicula (
    id_pelicula INT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(100) NOT NULL,
    duracion VARCHAR(15) NOT NULL,
    director VARCHAR(50) NOT NULL,
    genero VARCHAR(30) NOT NULL,
    fecha_estreno VARCHAR(20) NOT NULL,
    url_img VARCHAR(255) NOT NULL
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
    tipo_formato VARCHAR(20) NOT NULL,
    valor_entrada INT NOT NULL
);

CREATE TABLE Horario (
    id_horario INT PRIMARY KEY AUTO_INCREMENT,
    hora_funcion TIME NOT NULL
);

CREATE TABLE FuncionHorario (
    id_funcion_horario INT PRIMARY KEY AUTO_INCREMENT,
    id_funcion INT NOT NULL,
    id_formato INT NOT NULL,
    id_horario INT NOT NULL,
    ocupacion INT NOT NULL,
    esta_disponible BOOLEAN NOT NULL,
    FOREIGN KEY (id_funcion) REFERENCES Funcion(id_funcion),
    FOREIGN KEY (id_formato) REFERENCES Formato(id_formato),
    FOREIGN KEY (id_horario) REFERENCES Horario(id_horario)
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
INSERT INTO Pelicula (titulo, duracion, director, genero, fecha_estreno, url_img) 
VALUES 
('Los 4 Fantásticos: Primeros Pasos', '115 min', 'Matt Shakman', 'Accion/Ciencia Ficción', '2025-07-24', 'https://palomaynacho.com/wp-content/uploads/2025/06/Los-4-fantasticos-2025-poster-scaled.jpg'),
('Teléfono Negro 2', '114 min', 'Scott Derrickson', 'Terror/Suspenso', '2025-10-17', 'https://m.media-amazon.com/images/M/MV5BMTVjMzNmZGYtOWU5NS00NDYzLThhZTktZGNlODIwYWVhMDRmXkEyXkFqcGc@._V1_FMjpg_UX1000_.jpg'),
('Tron: Ares', '119 min', 'Joachim Ronning', 'Ciencia Ficción/Accion', '2025-10-10', 'https://m.media-amazon.com/images/M/MV5BMmRlMzYzZDMtYTQ0Yy00YTJiLWFiMTItYWIzODdlZTc3ZTU4XkEyXkFqcGc@._V1_FMjpg_UX1000_.jpg'),
('La Máquina: The Smashing Machine', '123 min', 'Benny Safdie', 'Drama/Deporte', '2025-10-09', 'https://m.media-amazon.com/images/M/MV5BNGMxNDgxZTYtN2IzYi00Mzg2LWE0YTUtNjk3OTM3ZTgwYmMwXkEyXkFqcGc@._V1_FMjpg_UX1000_.jpg'),
('El Conjuro 4: Ultimos Ritos', '135 min', 'Michael Chaves', 'Terror', '2025-09-05', 'https://m.media-amazon.com/images/M/MV5BOGRhNWVhZWUtNWUzMi00ZWQ0LWI3YmUtNTk5YTNiNDRlNDEwXkEyXkFqcGc@._V1_FMjpg_UX1000_.jpg'),
('Una Batalla Tras Otra', '162 min', 'Paul Thomas Anderson', 'Comedia/Drama', '2025-09-26', 'https://m.media-amazon.com/images/M/MV5BNGZhYzQ5MjgtYzg0NC00NWE4LTk2YjYtMDNlNTQ3NjU2MmMwXkEyXkFqcGc@._V1_FMjpg_UX1000_.jpg');

INSERT INTO Funcion (id_pelicula, fecha, esta_disponible) VALUES
(1, '2025-10-28', TRUE), -- Los 4 Fantásticos
(2, '2025-10-29', TRUE), -- Teléfono Negro 2
(3, '2025-10-30', TRUE), -- Tron: Ares
(4, '2025-10-31', TRUE), -- La Máquina
(5, '2025-11-01', TRUE), -- El Conjuro 4
(6, '2025-11-02', TRUE); -- Una Batalla Tras Otra

INSERT INTO Formato (tipo_formato, valor_entrada) 
VALUES ('2D', 8000), ('3D', 12000), ('XD', 15000);

INSERT INTO Horario (hora_funcion) 
VALUES ('18:00:00'), ('20:00:00'), ('22:00:00');

INSERT INTO FuncionHorario (id_funcion, id_formato, id_horario, ocupacion, esta_disponible) VALUES
-- Los 4 Fantásticos (funcion 1, formatos 2D, 3D y XD)
(1, 1, 1, 0, TRUE),
(1, 1, 2, 0, TRUE),
(1, 1, 3, 0, TRUE),
(1, 2, 1, 0, TRUE),
(1, 2, 2, 0, TRUE),
(1, 2, 3, 0, TRUE),
(1, 3, 1, 0, TRUE),
(1, 3, 2, 0, TRUE),
(1, 3, 3, 0, TRUE),

-- Teléfono Negro 2 (funcion 2, formatos 2D y XD)
(2, 1, 1, 0, TRUE),
(2, 1, 2, 0, TRUE),
(2, 1, 3, 0, TRUE),
(2, 3, 1, 0, TRUE),
(2, 3, 2, 0, TRUE),
(2, 3, 3, 0, TRUE),

-- Tron: Ares (funcion 3, formatos 3D y XD)
(3, 2, 1, 0, TRUE),
(3, 2, 2, 0, TRUE),
(3, 2, 3, 0, TRUE),
(3, 3, 1, 0, TRUE),
(3, 3, 2, 0, TRUE),
(3, 3, 3, 0, TRUE),

-- La Máquina (funcion 4, formatos 2D y XD)
(4, 1, 1, 0, TRUE),
(4, 1, 2, 0, TRUE),
(4, 1, 3, 0, TRUE),
(4, 3, 1, 0, TRUE),
(4, 3, 2, 0, TRUE),
(4, 3, 3, 0, TRUE),

-- El Conjuro 4 (funcion 5, formatos 2D y XD)
(5, 1, 1, 0, TRUE),
(5, 1, 2, 0, TRUE),
(5, 1, 3, 0, TRUE),
(5, 3, 1, 0, TRUE),
(5, 3, 2, 0, TRUE),
(5, 3, 3, 0, TRUE),

-- Una Batalla Tras Otra (funcion 6, formatos 2D y XD)
(6, 1, 1, 0, TRUE),
(6, 1, 2, 0, TRUE),
(6, 1, 3, 0, TRUE),
(6, 3, 1, 0, TRUE),
(6, 3, 2, 0, TRUE),
(6, 3, 3, 0, TRUE);

INSERT INTO Usuario (id_usuario, nombre_usuario, contrasena, tipo_usuario) 
VALUES (1, 'admin_cinemotion', 'admin456', 'Admin'), (2, 'user_test_cliente', 'pass1234', 'Cliente') , (3, 'user_test_2', 'contraseña1234', 'Cliente');


/*Consulta de datos*/
SELECT 
    p.titulo AS Pelicula,
    f.fecha AS Fecha,
    fo.tipo_formato AS Formato,
    h.hora_funcion AS Horario,
    CASE 
        WHEN fh.ocupacion = 0 THEN 'Vacío'
        WHEN fh.ocupacion = 100 THEN 'Lleno'
        ELSE CONCAT(fh.ocupacion, ' ocupados')
    END AS Ocupacion,
    CASE 
        WHEN fh.esta_disponible = 1 THEN 'Disponible'
        ELSE 'No disponible'
    END AS Estado
FROM FuncionHorario fh
INNER JOIN Funcion f ON fh.id_funcion = f.id_funcion
INNER JOIN Pelicula p ON f.id_pelicula = p.id_pelicula
INNER JOIN Formato fo ON fh.id_formato = fo.id_formato
INNER JOIN Horario h ON fh.id_horario = h.id_horario
ORDER BY f.fecha, p.titulo, h.hora_funcion;


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
