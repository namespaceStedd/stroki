-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Хост: 127.0.0.1:3306
-- Время создания: Сен 24 2023 г., 19:52
-- Версия сервера: 8.0.31
-- Версия PHP: 8.0.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База данных: `stroki`
--

-- --------------------------------------------------------

--
-- Структура таблицы `log`
--

DROP TABLE IF EXISTS `log`;
CREATE TABLE IF NOT EXISTS `log` (
  `id` int NOT NULL AUTO_INCREMENT,
  `short_url` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Дамп данных таблицы `log`
--

INSERT INTO `log` (`id`, `short_url`, `date`) VALUES
(6, 'http://alex.stroki/mFSE40', '2023-09-24 15:15:25'),
(7, 'http://alex.stroki/mFSE40', '2023-09-24 15:16:06'),
(8, 'http://alex.stroki/mFSE40', '2023-09-24 15:35:01'),
(9, 'http://alex.stroki/mFSE40', '2023-09-24 15:35:10'),
(10, 'http://alex.stroki/mFSE40', '2023-09-24 15:35:49'),
(11, 'http://alex.stroki/mFSE40', '2023-09-24 15:35:57'),
(12, 'http://alex.stroki/kFSE40', '2023-09-24 15:36:24'),
(13, 'http://alex.stroki/kFSE40', '2023-09-24 15:36:39'),
(14, 'http://alex.stroki/kFSE40', '2023-09-24 15:36:39'),
(15, 'http://alex.stroki/mFSE40', '2023-09-24 15:37:15'),
(16, 'http://alex.stroki/lFSE40', '2023-09-24 15:37:30'),
(17, 'http://alex.stroki/nFSE40', '2023-09-24 15:51:23'),
(18, 'http://alex.stroki/mFSE40', '2023-09-24 18:50:49'),
(19, 'http://alex.stroki/mFSE40', '2023-09-24 22:40:56'),
(20, 'http://alex.stroki/mFSE40', '2023-09-25 00:40:11'),
(21, 'http://alex.stroki/mFSE40', '2023-09-25 00:44:19'),
(23, 'http://alex.stroki/nFSE40', '2023-09-25 00:47:36'),
(24, 'http://alex.stroki/mFSE40', '2023-09-25 00:47:54');

-- --------------------------------------------------------

--
-- Структура таблицы `url`
--

DROP TABLE IF EXISTS `url`;
CREATE TABLE IF NOT EXISTS `url` (
  `id` int NOT NULL,
  `url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `short_url` varchar(255) DEFAULT NULL,
  `tranzit` int NOT NULL,
  `user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Дамп данных таблицы `url`
--

INSERT INTO `url` (`id`, `url`, `short_url`, `tranzit`, `user`) VALUES
(1000000008, 'https://yandex.ru/maps', 'http://alex.stroki/nFSE40', 2, 'Море'),
(1000000000, 'http://localhost/phpmyadmin/index.php?route=/sql&pos=0&db=stroki&table=url', 'http://alex.stroki/fFSE40', 0, 'user'),
(1000000004, 'https://uchi.ru/otvety/questions/sostavte-tekst-iz-10-predlozheniy-ispolzuya-slova-s-cheredovaniem-glasnih-v-korne', 'http://alex.stroki/jFSE40', 0, 'Море'),
(1000000001, 'https://vk.com/feed', 'http://alex.stroki/kFSE40', 3, 'newuser'),
(1000000006, 'https://web.telegram.org/k/', 'http://alex.stroki/lFSE40', 1, 'newuser'),
(1000000007, 'https://stroki.ru/', 'http://alex.stroki/mFSE40', 12, 'alex');

-- --------------------------------------------------------

--
-- Структура таблицы `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `login` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  UNIQUE KEY `login` (`login`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Дамп данных таблицы `user`
--

INSERT INTO `user` (`login`, `password`) VALUES
('user', 'password'),
('Море', 'волнуется раз'),
('alex', 'stroki'),
('newuser', 'pass');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
