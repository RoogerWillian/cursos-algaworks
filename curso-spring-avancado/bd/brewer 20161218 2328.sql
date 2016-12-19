-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.7.15-log


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema brewer
--

CREATE DATABASE IF NOT EXISTS brewer;
USE brewer;

--
-- Definition of table `cerveja`
--

DROP TABLE IF EXISTS `cerveja`;
CREATE TABLE `cerveja` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `sku` varchar(50) NOT NULL,
  `nome` varchar(80) NOT NULL,
  `descricao` text NOT NULL,
  `quantidade_estoque` int(11) NOT NULL,
  `valor` decimal(10,2) NOT NULL,
  `teor_alcoolico` decimal(10,2) NOT NULL,
  `comissao` decimal(10,2) NOT NULL,
  `sabor` varchar(50) NOT NULL,
  `origem` varchar(50) NOT NULL,
  `codigo_estilo` bigint(20) NOT NULL,
  `foto` varchar(100) DEFAULT NULL,
  `content_type` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `codigo_estilo` (`codigo_estilo`),
  CONSTRAINT `cerveja_ibfk_1` FOREIGN KEY (`codigo_estilo`) REFERENCES `estilo` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `cerveja`
--

/*!40000 ALTER TABLE `cerveja` DISABLE KEYS */;
INSERT INTO `cerveja` (`codigo`,`sku`,`nome`,`descricao`,`quantidade_estoque`,`valor`,`teor_alcoolico`,`comissao`,`sabor`,`origem`,`codigo_estilo`,`foto`,`content_type`) VALUES 
 (5,'AA1111','Cerveja Negra','Boa pra tudo',80,'8.50','5.00','0.20','ADOCICADA','NACIONAL',2,'',''),
 (6,'AA2222','Cerveja Skol','Boa pra mais ainda',95,'6.52','50.00','4.60','FORTE','NACIONAL',1,'',''),
 (7,'AA3333','Cerveja Becks Long Neck','Boa ',565,'5.00','50.00','0.50','FORTE','INTERNACIONAL',3,'','');
/*!40000 ALTER TABLE `cerveja` ENABLE KEYS */;


--
-- Definition of table `cidade`
--

DROP TABLE IF EXISTS `cidade`;
CREATE TABLE `cidade` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) NOT NULL,
  `codigo_estado` bigint(20) NOT NULL,
  PRIMARY KEY (`codigo`),
  KEY `codigo_estado` (`codigo_estado`),
  CONSTRAINT `cidade_ibfk_1` FOREIGN KEY (`codigo_estado`) REFERENCES `estado` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `cidade`
--

/*!40000 ALTER TABLE `cidade` DISABLE KEYS */;
INSERT INTO `cidade` (`codigo`,`nome`,`codigo_estado`) VALUES 
 (1,'Rio Branco',1),
 (2,'Cruzeiro do Sul',1),
 (3,'Salvador',2),
 (4,'Porto Seguro',2),
 (5,'Santana',2),
 (6,'Goiânia',3),
 (7,'Itumbiara',3),
 (8,'Novo Brasil',3),
 (9,'Belo Horizonte',4),
 (10,'Uberlândia',4),
 (11,'Montes Claros',4),
 (12,'Florianópolis',5),
 (13,'Criciúma',5),
 (14,'Camboriú',5),
 (15,'Lages',5),
 (16,'São Paulo',6),
 (17,'Ribeirão Preto',6),
 (18,'Campinas',6),
 (19,'Santos',6);
/*!40000 ALTER TABLE `cidade` ENABLE KEYS */;


--
-- Definition of table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
CREATE TABLE `cliente` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `nome` varchar(80) NOT NULL,
  `tipo_pessoa` varchar(15) NOT NULL,
  `cpf_cnpj` varchar(30) NOT NULL,
  `telefone` varchar(20) DEFAULT NULL,
  `email` varchar(50) NOT NULL,
  `logradouro` varchar(50) DEFAULT NULL,
  `numero` varchar(15) DEFAULT NULL,
  `complemento` varchar(20) DEFAULT NULL,
  `cep` varchar(15) DEFAULT NULL,
  `codigo_cidade` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `codigo_cidade` (`codigo_cidade`),
  CONSTRAINT `cliente_ibfk_1` FOREIGN KEY (`codigo_cidade`) REFERENCES `cidade` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `cliente`
--

/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` (`codigo`,`nome`,`tipo_pessoa`,`cpf_cnpj`,`telefone`,`email`,`logradouro`,`numero`,`complemento`,`cep`,`codigo_cidade`) VALUES 
 (1,'Roger Willian Nizoli Rocha','FISICA','43590362804','(14) 99845-7145','roger.nizoli@gmail.com','Rua Elza Pires Correa Pedro','10','','17.047-281',16),
 (2,'Natalia Pereira','FISICA','31163392529','(14) 99662-7214','naty_ipa@hotmail.com','Rua Rafael Urtado','52','','18.950-000',7),
 (3,'Enes Rocha Filho','FISICA','23664216130','(14) 99555-3646','rochaenes@gmail.com','Rua Elza Pires Correa Pedro','100','','17.047-281',19);
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;


--
-- Definition of table `estado`
--

DROP TABLE IF EXISTS `estado`;
CREATE TABLE `estado` (
  `codigo` bigint(20) NOT NULL,
  `nome` varchar(50) NOT NULL,
  `sigla` varchar(2) NOT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `estado`
--

/*!40000 ALTER TABLE `estado` DISABLE KEYS */;
INSERT INTO `estado` (`codigo`,`nome`,`sigla`) VALUES 
 (1,'Acre','AC'),
 (2,'Bahia','BA'),
 (3,'Goiás','GO'),
 (4,'Minas Gerais','MG'),
 (5,'Santa Catarina','SC'),
 (6,'São Paulo','SP');
/*!40000 ALTER TABLE `estado` ENABLE KEYS */;


--
-- Definition of table `estilo`
--

DROP TABLE IF EXISTS `estilo`;
CREATE TABLE `estilo` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) NOT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `estilo`
--

/*!40000 ALTER TABLE `estilo` DISABLE KEYS */;
INSERT INTO `estilo` (`codigo`,`nome`) VALUES 
 (1,'Amber Lager'),
 (2,'Dark Lager'),
 (3,'Pale Lager'),
 (4,'Pilsner');
/*!40000 ALTER TABLE `estilo` ENABLE KEYS */;


--
-- Definition of table `grupo`
--

DROP TABLE IF EXISTS `grupo`;
CREATE TABLE `grupo` (
  `codigo` bigint(20) NOT NULL,
  `nome` varchar(50) NOT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `grupo`
--

/*!40000 ALTER TABLE `grupo` DISABLE KEYS */;
INSERT INTO `grupo` (`codigo`,`nome`) VALUES 
 (1,'Administrador'),
 (2,'Vendedor');
/*!40000 ALTER TABLE `grupo` ENABLE KEYS */;


--
-- Definition of table `grupo_permissao`
--

DROP TABLE IF EXISTS `grupo_permissao`;
CREATE TABLE `grupo_permissao` (
  `codigo_grupo` bigint(20) NOT NULL,
  `codigo_permissao` bigint(20) NOT NULL,
  PRIMARY KEY (`codigo_grupo`,`codigo_permissao`),
  KEY `codigo_permissao` (`codigo_permissao`),
  CONSTRAINT `grupo_permissao_ibfk_1` FOREIGN KEY (`codigo_grupo`) REFERENCES `grupo` (`codigo`),
  CONSTRAINT `grupo_permissao_ibfk_2` FOREIGN KEY (`codigo_permissao`) REFERENCES `permissao` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `grupo_permissao`
--

/*!40000 ALTER TABLE `grupo_permissao` DISABLE KEYS */;
INSERT INTO `grupo_permissao` (`codigo_grupo`,`codigo_permissao`) VALUES 
 (1,1),
 (1,2);
/*!40000 ALTER TABLE `grupo_permissao` ENABLE KEYS */;


--
-- Definition of table `item_venda`
--

DROP TABLE IF EXISTS `item_venda`;
CREATE TABLE `item_venda` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `quantidade` int(11) NOT NULL,
  `valor_unitario` decimal(10,2) NOT NULL,
  `codigo_cerveja` bigint(20) NOT NULL,
  `codigo_venda` bigint(20) NOT NULL,
  PRIMARY KEY (`codigo`),
  KEY `codigo_cerveja` (`codigo_cerveja`),
  KEY `codigo_venda` (`codigo_venda`),
  CONSTRAINT `item_venda_ibfk_1` FOREIGN KEY (`codigo_cerveja`) REFERENCES `cerveja` (`codigo`),
  CONSTRAINT `item_venda_ibfk_2` FOREIGN KEY (`codigo_venda`) REFERENCES `venda` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `item_venda`
--

/*!40000 ALTER TABLE `item_venda` DISABLE KEYS */;
/*!40000 ALTER TABLE `item_venda` ENABLE KEYS */;


--
-- Definition of table `permissao`
--

DROP TABLE IF EXISTS `permissao`;
CREATE TABLE `permissao` (
  `codigo` bigint(20) NOT NULL,
  `nome` varchar(50) NOT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `permissao`
--

/*!40000 ALTER TABLE `permissao` DISABLE KEYS */;
INSERT INTO `permissao` (`codigo`,`nome`) VALUES 
 (1,'ROLE_CADASTRAR_CIDADE'),
 (2,'ROLE_CADASTRAR_USUARIO');
/*!40000 ALTER TABLE `permissao` ENABLE KEYS */;


--
-- Definition of table `schema_version`
--

DROP TABLE IF EXISTS `schema_version`;
CREATE TABLE `schema_version` (
  `installed_rank` int(11) NOT NULL,
  `version` varchar(50) DEFAULT NULL,
  `description` varchar(200) NOT NULL,
  `type` varchar(20) NOT NULL,
  `script` varchar(1000) NOT NULL,
  `checksum` int(11) DEFAULT NULL,
  `installed_by` varchar(100) NOT NULL,
  `installed_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `execution_time` int(11) NOT NULL,
  `success` tinyint(1) NOT NULL,
  PRIMARY KEY (`installed_rank`),
  KEY `schema_version_s_idx` (`success`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `schema_version`
--

/*!40000 ALTER TABLE `schema_version` DISABLE KEYS */;
INSERT INTO `schema_version` (`installed_rank`,`version`,`description`,`type`,`script`,`checksum`,`installed_by`,`installed_on`,`execution_time`,`success`) VALUES 
 (1,'01','criar tabelas estilo e cerveja','SQL','V01__criar_tabelas_estilo_e_cerveja.sql',-1626336615,'root','2016-09-23 23:46:10',881,1),
 (2,'02','alteracao tabela estilo','SQL','V02__alteracao_tabela_estilo.sql',767433692,'root','2016-09-23 23:46:10',8,1),
 (3,'03','criar foto contentType cerveja','SQL','V03__criar_foto_contentType_cerveja.sql',852113891,'root','2016-09-23 23:46:42',734,1),
 (4,'04','criar estado e cidade','SQL','V04__criar_estado_e_cidade.sql',-318392381,'root','2016-09-23 23:46:43',996,1),
 (5,'05','criando cliente','SQL','V05__criando_cliente.sql',1598459453,'root','2016-09-23 23:46:43',467,1),
 (6,'06','criando usuario grupo permissao','SQL','V06__criando_usuario_grupo_permissao.sql',-812092727,'root','2016-09-23 23:46:45',1934,1),
 (7,'07','alterando cpf cnpj not null','SQL','V07__alterando_cpf_cnpj_not_null.sql',469491319,'root','2016-09-23 23:46:46',903,1),
 (8,'08','alterando status not null2','SQL','V08__alterando_status_not_null2.sql',-255901085,'root','2016-09-23 23:46:47',768,1),
 (9,'09','inserindo grupos','SQL','V09__inserindo_grupos.sql',-937607972,'root','2016-09-23 23:46:47',43,1),
 (10,'10','inserindo usuario administrator','SQL','V10__inserindo_usuario_administrator.sql',394480566,'root','2016-09-23 23:46:47',44,1),
 (11,'11','inserindo permissoes relacionando admin','SQL','V11__inserindo_permissoes_relacionando_admin.sql',1667867869,'root','2016-09-23 23:46:48',177,1),
 (12,'12','criar tabela venda item venda','SQL','V12__criar_tabela_venda_item_venda.sql',1763602382,'root','2016-09-23 23:48:45',903,1);
/*!40000 ALTER TABLE `schema_version` ENABLE KEYS */;


--
-- Definition of table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
CREATE TABLE `usuario` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `senha` varchar(120) NOT NULL,
  `ativo` tinyint(1) NOT NULL DEFAULT '1',
  `data_nascimento` date DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `usuario`
--

/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` (`codigo`,`nome`,`email`,`senha`,`ativo`,`data_nascimento`) VALUES 
 (1,'Admin','admin@brewer.com','$2a$10$g.wT4R0Wnfel1jc/k84OXuwZE02BlACSLfWy6TycGPvvEKvIm86SG',1,NULL);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;


--
-- Definition of table `usuario_grupo`
--

DROP TABLE IF EXISTS `usuario_grupo`;
CREATE TABLE `usuario_grupo` (
  `codigo_usuario` bigint(20) NOT NULL,
  `codigo_grupo` bigint(20) NOT NULL,
  PRIMARY KEY (`codigo_usuario`,`codigo_grupo`),
  KEY `codigo_grupo` (`codigo_grupo`),
  CONSTRAINT `usuario_grupo_ibfk_1` FOREIGN KEY (`codigo_usuario`) REFERENCES `usuario` (`codigo`),
  CONSTRAINT `usuario_grupo_ibfk_2` FOREIGN KEY (`codigo_grupo`) REFERENCES `grupo` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `usuario_grupo`
--

/*!40000 ALTER TABLE `usuario_grupo` DISABLE KEYS */;
INSERT INTO `usuario_grupo` (`codigo_usuario`,`codigo_grupo`) VALUES 
 (1,1);
/*!40000 ALTER TABLE `usuario_grupo` ENABLE KEYS */;


--
-- Definition of table `venda`
--

DROP TABLE IF EXISTS `venda`;
CREATE TABLE `venda` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `data_criacao` datetime NOT NULL,
  `valor_frete` decimal(10,2) DEFAULT NULL,
  `valor_desconto` decimal(10,2) DEFAULT NULL,
  `valor_total` decimal(10,2) NOT NULL,
  `status` varchar(30) NOT NULL,
  `observacao` varchar(200) DEFAULT NULL,
  `data_hora_entrega` datetime DEFAULT NULL,
  `codigo_cliente` bigint(20) NOT NULL,
  `codigo_usuario` bigint(20) NOT NULL,
  PRIMARY KEY (`codigo`),
  KEY `codigo_cliente` (`codigo_cliente`),
  KEY `codigo_usuario` (`codigo_usuario`),
  CONSTRAINT `venda_ibfk_1` FOREIGN KEY (`codigo_cliente`) REFERENCES `cliente` (`codigo`),
  CONSTRAINT `venda_ibfk_2` FOREIGN KEY (`codigo_usuario`) REFERENCES `usuario` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `venda`
--

/*!40000 ALTER TABLE `venda` DISABLE KEYS */;
/*!40000 ALTER TABLE `venda` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
