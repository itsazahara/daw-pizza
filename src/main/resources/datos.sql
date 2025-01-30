INSERT INTO cliente (direccion, email, nombre, telefono) VALUES
('Calle Mayor 1, Madrid', 'juan@email.com', 'Juan Pérez', '600111222'),
('Avenida Principal 23, Barcelona', 'maria@email.com', 'María García', '611222333'),
('Plaza Central 5, Valencia', 'carlos@email.com', 'Carlos Rodríguez', '622333444'),
('Calle del Sol 12, Sevilla', 'ana@email.com', 'Ana Martínez', '633444555'),
('Paseo de la Luna 8, Bilbao', 'pedro@email.com', 'Pedro Sánchez', '644555666');

-- Insertar datos en la tabla pizza
INSERT INTO pizza (descripcion, disponible, nombre, precio, vegana, vegetariana) VALUES
('Clásica pizza con tomate y mozzarella', 1, 'Margherita', 10.50, 0, 1),
('Pizza con pepperoni y queso extra', 1, 'Pepperoni', 12.00, 0, 0),
('Pizza vegetariana con variedad de verduras', 1, 'Vegetariana', 11.50, 0, 1),
('Pizza con jamón y piña', 1, 'Hawaiana', 11.00, 0, 0),
('Pizza vegana con queso vegano y verduras', 1, 'Vegana Especial', 13.50, 1, 1),
('Pizza con cuatro tipos de queso', 1, 'Cuatro Quesos', 12.50, 0, 1),
('Pizza con carne y verduras', 1, 'Suprema', 14.00, 0, 0),
('Pizza con pollo y champiñones', 1, 'Pollo Champiñón', 12.00, 0, 0),
('Pizza picante con jalapeños y pepperoni', 1, 'Picante Jalapeño', 12.50, 0, 0),
('Pizza con espinacas y queso feta', 1, 'Espinacas Feta', 11.00, 0, 1),
('Pizza de atún con cebolla y aceitunas', 1, 'Atún Especial', 13.00, 0, 0),
('Pizza BBQ con pollo y cebolla caramelizada', 1, 'BBQ Pollo', 14.00, 0, 0),
('Pizza de mariscos con gambas y mejillones', 1, 'Mariscos Deliciosos', 15.00, 0, 0),
('Pizza de calabacín y pimientos asados', 1, 'Vegetal Asado', 11.50, 1, 1),
('Pizza de pesto con tomate seco y mozzarella', 1, 'Pesto Mozzarella', 12.50, 1, 1),
('Pizza con carne picada y salsa de tomate', 1, 'Carne Picada', 13.50, 0, 0);

-- Insertar datos en la tabla pedido
INSERT INTO pedido (fecha, id_cliente, metodo, notas, total) VALUES
('2024-11-19 12:30:00', 1, 'D', 'Entregar en la puerta principal', 22.50),
('2024-11-19 13:45:00', 2, 'R', NULL, 24.00),
('2024-11-19 18:00:00', 3, 'D', 'Llamar al llegar', 35.50),
('2024-11-19 19:15:00', 4, 'R', NULL, 11.00),
('2024-11-19 20:30:00', 5, 'D', 'Sin cebolla, por favor', 26.00);

-- Insertar datos en la tabla pizza_pedido
INSERT INTO pizza_pedido (cantidad, id_pedido, id_pizza, precio) VALUES
(1, 1, 1, 10.50),
(1, 1, 3, 11.50),
(2, 2, 2, 12.00),
(1, 3, 4, 11.00),
(1, 3, 5, 13.50),
(1, 3, 6, 12.50),
(1, 4, 1, 10.50),
(1, 5, 7, 14.00),
(1, 5, 3, 11.50);