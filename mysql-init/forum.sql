-- phpMyAdmin SQL Dump
-- version 5.2.3
-- https://www.phpmyadmin.net/
--
-- Servidor: db
-- Tiempo de generación: 26-02-2026 a las 17:11:40
-- Versión del servidor: 8.0.44
-- Versión de PHP: 8.3.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `forum`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `category`
--

CREATE TABLE `category` (
  `id` varchar(255) NOT NULL,
  `__v` int NOT NULL,
  `color` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `slug` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `category`
--

INSERT INTO `category` (`id`, `__v`, `color`, `description`, `slug`, `title`, `user_id`) VALUES
('5f207eb6-09c8-4eec-8273-e52bfd109e20', 0, '#84fb60', 'a', '5f207eb6-09c8-4eec-8273-e52bfd109e20-a', 'a', NULL),
('ff551406-dad0-4e9f-9533-a7ff9976c82a', 0, '#ca418f', 'a', 'ff551406-dad0-4e9f-9533-a7ff9976c82a-a', 'a', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `replies`
--

CREATE TABLE `replies` (
  `id` varchar(255) NOT NULL,
  `__v` int NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `topic_id` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `replies`
--

INSERT INTO `replies` (`id`, `__v`, `content`, `created_at`, `updated_at`, `topic_id`, `user_id`) VALUES
('875ffc49-64bf-48ac-9f2d-6415782f2a4a', 0, '1234', '2026-02-26 17:51:49.750000', '2026-02-26 17:51:49.750000', '53177263-8eb9-46ef-a46d-5643d8940b41', '1f49896e-fbb8-486b-9cfe-5a67262f1386');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `topics`
--

CREATE TABLE `topics` (
  `id` varchar(255) NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `created_at` date DEFAULT NULL,
  `number_of_replies` int NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `updated_at` date DEFAULT NULL,
  `views` int NOT NULL,
  `category_id` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `topics`
--

INSERT INTO `topics` (`id`, `content`, `created_at`, `number_of_replies`, `title`, `updated_at`, `views`, `category_id`, `user_id`) VALUES
('53177263-8eb9-46ef-a46d-5643d8940b41', 'qqq', '2026-02-26', 0, 'qq', '2026-02-26', 0, 'ff551406-dad0-4e9f-9533-a7ff9976c82a', '27ada993-131b-474a-8e54-65119f1cf396');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `users`
--

CREATE TABLE `users` (
  `_id` varchar(255) NOT NULL,
  `avatar_url` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `moderate_category` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(255) NOT NULL,
  `__v` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `users`
--

INSERT INTO `users` (`_id`, `avatar_url`, `email`, `moderate_category`, `name`, `password`, `role`, `__v`) VALUES
('1f49896e-fbb8-486b-9cfe-5a67262f1386', NULL, 'admin2@gmail.com', '', 'riu', '$2a$10$rNFVgWauRf0YhUJuv0jc6u5eYsE93a5MMN/BZ0iBxAHezoUu6JT0S', 'admin', 2),
('27ada993-131b-474a-8e54-65119f1cf396', NULL, 'admin@gmail.com', '', 'admin111111111111', '$2a$10$rEB/a0AGvShqr518iWPZpO6sffKdNtzL0HKG0XQKrChRw04s6ZRDi', 'admin', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user_permissions`
--

CREATE TABLE `user_permissions` (
  `user_id` varchar(255) NOT NULL,
  `permission` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK7ffrpnxaflomhdh0qfk2jcndo` (`user_id`);

--
-- Indices de la tabla `replies`
--
ALTER TABLE `replies`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKlej8qoe1wpmuh15xsdsyhl70m` (`topic_id`),
  ADD KEY `FKn60t7po8l0rllye52xx25q4xx` (`user_id`);

--
-- Indices de la tabla `topics`
--
ALTER TABLE `topics`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKa1iux1e9lh0a94xhg1k7hb54l` (`category_id`),
  ADD KEY `FKoc3papwmjontq89fcia02ag1h` (`user_id`);

--
-- Indices de la tabla `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`_id`),
  ADD UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`);

--
-- Indices de la tabla `user_permissions`
--
ALTER TABLE `user_permissions`
  ADD KEY `FKkowxl8b2bngrxd1gafh13005u` (`user_id`);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `category`
--
ALTER TABLE `category`
  ADD CONSTRAINT `FK7ffrpnxaflomhdh0qfk2jcndo` FOREIGN KEY (`user_id`) REFERENCES `users` (`_id`);

--
-- Filtros para la tabla `replies`
--
ALTER TABLE `replies`
  ADD CONSTRAINT `FKlej8qoe1wpmuh15xsdsyhl70m` FOREIGN KEY (`topic_id`) REFERENCES `topics` (`id`),
  ADD CONSTRAINT `FKn60t7po8l0rllye52xx25q4xx` FOREIGN KEY (`user_id`) REFERENCES `users` (`_id`);

--
-- Filtros para la tabla `topics`
--
ALTER TABLE `topics`
  ADD CONSTRAINT `FKa1iux1e9lh0a94xhg1k7hb54l` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`),
  ADD CONSTRAINT `FKoc3papwmjontq89fcia02ag1h` FOREIGN KEY (`user_id`) REFERENCES `users` (`_id`);

--
-- Filtros para la tabla `user_permissions`
--
ALTER TABLE `user_permissions`
  ADD CONSTRAINT `FKkowxl8b2bngrxd1gafh13005u` FOREIGN KEY (`user_id`) REFERENCES `users` (`_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
