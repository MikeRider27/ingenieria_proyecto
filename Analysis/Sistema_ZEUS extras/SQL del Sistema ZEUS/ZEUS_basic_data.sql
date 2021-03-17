-------------------------------------------LISTADO DE INSERTS BASICOS PARA EL FUNCIONAMIENTO DEL SISTEMA VERSION PRE TESIS-----------------------------------------------------------------
--estados
INSERT INTO estado VALUES (0,'ANULADO',false),(1,'VIGENTE',false),('PEN','PENDIENTE',false),('APR','APROBADO',false),('O.P','ORD. PARCIAL',false),('O.T','ORD. TOTAL',false),('C.P','COM. PARCIAL',false),('C.T','COM. TOTAL',false),('PRV','PROVEIDO',false);
--nacionalidad
select * from nacionalidad
INSERT INTO nacionalidad VALUES (1,1,'PARAGUAYA');
INSERT INTO nacionalidad VALUES (2,1,'ARGENTINA');
INSERT INTO nacionalidad VALUES (3,1,'BRASILERA');
INSERT INTO nacionalidad VALUES (4,1,'PERUANA');
INSERT INTO nacionalidad VALUES (5,1,'BOLIVIANA');
INSERT INTO nacionalidad VALUES (6,1,'COLOMBIANA');
INSERT INTO nacionalidad VALUES (7,1,'ALEMANA');
INSERT INTO nacionalidad VALUES (8,1,'URUGUAYA');
INSERT INTO nacionalidad VALUES (9,1,'ITALIANA');
INSERT INTO nacionalidad VALUES (10,1,'AUSTRIACA');
SELECT id_nac,descrip FROM nacionalidad where id_est=1;
 --cargos
--INSERT INTO cargo VALUES(0,1,'ROOT','hMqOYrKktT4=','qVqjBtL5IdQ=');
--INSERT INTO cargo VALUES(1,1,'GERENTE','hMqOYrKktT4=','qVqjBtL5IdQ=');
--INSERT INTO cargo VALUES(2,1,'J. TALLER','hMqOYrKktT4=','qVqjBtL5IdQ=');--SELECT * FROM CARGO
CREATE TABLE cargo
(
  id_car integer NOT NULL,
  descrip character varying(20),
  est_cod character varying NOT NULL,
  usr_car boolean,
INSERT INTO cargo VALUES(0,'ROOT','VIG',true);
INSERT INTO cargo VALUES(1,'GERENTE','VIG',true);
INSERT INTO cargo VALUES(2,'J. TALLER','VIG',true);
INSERT INTO cargo VALUES(3,'CONTABILIDAD','VIG',true);
INSERT INTO cargo VALUES(4,'CHAPISTA','VIG',false);
INSERT INTO cargo VALUES(5,'PINTOR','VIG',false);
INSERT INTO cargo VALUES(6,'MECANICO','VIG',false);
INSERT INTO cargo VALUES(7,'PULIDOR','VIG',false);
INSERT INTO cargo VALUES(8,'SECRETARIO','VIG',true);
INSERT INTO cargo VALUES(9,'TESORERO','VIG',true);
--ciudades
INSERT INTO ciudad VALUES(1,'ASUNCION','VIG');
INSERT INTO ciudad VALUES(2,'LAMBARE','VIG');
INSERT INTO ciudad VALUES(3,'LUQUE','VIG');
INSERT INTO ciudad VALUES(4,'MARIANO R. ALONSO','VIG');
INSERT INTO ciudad VALUES(5,'ÑEMBY','VIG');
INSERT INTO ciudad VALUES(6,'SAN LORENZO','VIG');
INSERT INTO ciudad VALUES(7,'CAPIATA','VIG');
INSERT INTO ciudad VALUES(8,'ITAUGUA','VIG');
INSERT INTO ciudad VALUES(9,'SAN ANTONIO','VIG');
INSERT INTO ciudad VALUES(10,'GUARAMBARE','VIG');
INSERT INTO ciudad VALUES(11,'J. A. SALDIVAR','VIG');
INSERT INTO ciudad VALUES(12,'YPACARAI','VIG');
INSERT INTO ciudad VALUES(13,'AREGUA','VIG');
INSERT INTO ciudad VALUES(14,'ITA','VIG');
INSERT INTO ciudad VALUES(15,'VILLETA','VIG');
INSERT INTO ciudad VALUES(16,'NUEVA ITALIA','VIG');
INSERT INTO ciudad VALUES(17,'YPANE','VIG');
INSERT INTO ciudad VALUES(18,'VILLA ELISA','VIG');
INSERT INTO ciudad VALUES(19,'FDO. DE LA MORA','VIG');
INSERT INTO ciudad VALUES(20,'LIMPIO','VIG');
--barrios
INSERT INTO barrio VALUES(1,1,'VIG','NAZARETH');
INSERT INTO barrio VALUES(2,1,'VIG','TERMINAL');
INSERT INTO barrio VALUES(3,1,'VIG','SAN PABLO');
INSERT INTO barrio VALUES(4,1,'VIG','PINOZA');
INSERT INTO barrio VALUES(5,1,'VIG','SAJONIA');
INSERT INTO barrio VALUES(6,1,'VIG','VISTA ALEGRE');
INSERT INTO barrio VALUES(7,1,'VIG','TACUMBU');
INSERT INTO barrio VALUES(8,1,'VIG','RICARDO BRUGADA');
INSERT INTO barrio VALUES(9,1,'VIG','LAS MERCEDES');
INSERT INTO barrio VALUES(10,1,'VIG','R. L. PETIT');
INSERT INTO barrio VALUES(11,1,'VIG','SAN JOSE');
INSERT INTO barrio VALUES(12,1,'VIG','CARMELITAS');
INSERT INTO barrio VALUES(13,1,'VIG','SAN VICENTE');
INSERT INTO barrio VALUES(14,1,'VIG','VILLA MORRA');
--suc
id_suc integer NOT NULL,
  descrip character varying(20),
  est_cod character varying NOT NULL,
  telef integer,
  direcc character varying,
  email character varying,
INSERT INTO sucursal VALUES(1,'CASA MATRIZ','VIG',21512368,'HIPOLITO SANCHEZ QUELL 222 C/ CARIOS','shadown10sombra@gmail.com');
INSERT INTO sucursal VALUES(2,'SUC. CDE','VIG',61576390,'AVDA. SAN JOSE KM. 6 1/2','cde@shadown.com');
INSERT INTO sucursal VALUES(3,'SUC. ITAUGUA','VIG',984280298,'RUTA 2 KM. 29 1/2','itaugua@shadown.com');
--seg
INSERT INTO seguro VALUES(1,'VIG','TAJY','ALGUN LUGAR',000,'ALGO@ALGO.com');
INSERT INTO seguro VALUES(2,'VIG','LA CONSOLIDADA','ALGUN LUGAR',000,'ALGO@ALGO.com');
INSERT INTO seguro VALUES(3,'VIG','MAPFRE','ALGUN LUGAR',000,'ALGO@ALGO.com');
INSERT INTO seguro VALUES(4,'VIG','SANCOR SEGUROS','ALGUN LUGAR',000,'ALGO@ALGO.com');
INSERT INTO seguro VALUES(5,'VIG','RIOS SEGUROS','ALGUN LUGAR',000,'ALGO@ALGO.com');
INSERT INTO seguro VALUES(6,'VIG','ASEGURADORA DEL ESTE','ALGUN LUGAR',000,'ALGO@ALGO.com');
INSERT INTO seguro VALUES(7,'VIG','CENIT','ALGUN LUGAR',000,'ALGO@ALGO.com');
INSERT INTO seguro VALUES(8,'VIG','YACYRETA','ALGUN LUGAR',000,'ALGO@ALGO.com');
INSERT INTO seguro VALUES(9,'VIG','RUMBOS','ALGUN LUGAR',000,'ALGO@ALGO.com');
INSERT INTO seguro VALUES(10,'VIG','SEGUROS CHACO','ALGUN LUGAR',000,'ALGO@ALGO.com');
INSERT INTO seguro VALUES(11,'VIG','SEGURIDAD SEGUROS','ALGUN LUGAR',000,'ALGO@ALGO.com');
INSERT INTO seguro VALUES(12,'VIG','CENTRAL DE SEGUROS','ALGUN LUGAR',000,'ALGO@ALGO.com');
INSERT INTO seguro VALUES(13,'VIG','LA RURAL','ALGUN LUGAR',000,'ALGO@ALGO.com');
INSERT INTO seguro VALUES(14,'VIG','GARANTIA S.A.','ALGUN LUGAR',000,'ALGO@ALGO.com');
INSERT INTO seguro VALUES(15,'VIG','ASEGURADORA DEL SUR','ALGUN LUGAR',000,'ALGO@ALGO.com');
INSERT INTO seguro VALUES(16,'VIG','ASEGURADORA PY','ALGUN LUGAR',000,'ALGO@ALGO.com');
INSERT INTO seguro VALUES(17,'VIG','ATALAYA DE SEGUROS','ALGUN LUGAR',000,'ALGO@ALGO.com');
INSERT INTO seguro VALUES(18,'VIG','EL COMERCIO PY','ALGUN LUGAR',000,'ALGO@ALGO.com');
INSERT INTO seguro VALUES(19,'VIG','EL PRODUCTOR','ALGUN LUGAR',000,'ALGO@ALGO.com');
INSERT INTO seguro VALUES(20,'VIG','EL SOL DEL PARAGUAY','ALGUN LUGAR',000,'ALGO@ALGO.com');
INSERT INTO seguro VALUES(21,'VIG','FENIX','ALGUN LUGAR',000,'ALGO@ALGO.com');
INSERT INTO seguro VALUES(22,'VIG','GRUPO GENERAL','ALGUN LUGAR',000,'ALGO@ALGO.com');
INSERT INTO seguro VALUES(23,'VIG','IMPERIO','ALGUN LUGAR',000,'ALGO@ALGO.com');
INSERT INTO seguro VALUES(24,'VIG','INTERCONTINENTAL','ALGUN LUGAR',000,'ALGO@ALGO.com');
INSERT INTO seguro VALUES(25,'VIG','LA AGRICOLA','ALGUN LUGAR',000,'ALGO@ALGO.com');
INSERT INTO seguro VALUES(26,'VIG','LA INDEPENDENCIA','ALGUN LUGAR',000,'ALGO@ALGO.com');
INSERT INTO seguro VALUES(27,'VIG','LA MERIDIONAL PY','ALGUN LUGAR',000,'ALGO@ALGO.com');
INSERT INTO seguro VALUES(28,'VIG','LA PARAGUAYA','ALGUN LUGAR',000,'ALGO@ALGO.com');
INSERT INTO seguro VALUES(29,'VIG','PANAL','ALGUN LUGAR',000,'ALGO@ALGO.com');
INSERT INTO seguro VALUES(30,'VIG','PATRIA','ALGUN LUGAR',000,'ALGO@ALGO.com');
INSERT INTO seguro VALUES(31,'VIG','REGIONAL','ALGUN LUGAR',000,'ALGO@ALGO.com');
INSERT INTO seguro VALUES(32,'VIG','ROYAL','ALGUN LUGAR',000,'ALGO@ALGO.com');
INSERT INTO seguro VALUES(33,'VIG','SEGUROS CHACO','ALGUN LUGAR',000,'ALGO@ALGO.com');
INSERT INTO seguro VALUES(34,'VIG','SEGUROS GENERALES','ALGUN LUGAR',000,'ALGO@ALGO.com');
INSERT INTO seguro VALUES(35,'VIG','SEGUROS PATRIA','ALGUN LUGAR',000,'ALGO@ALGO.com');
--mar
INSERT INTO marca VALUES (1,'TOYOTA','VIG');
INSERT INTO marca VALUES (2,'NISSAN','VIG');
INSERT INTO marca VALUES (3,'MITSUBISHI','VIG');
INSERT INTO marca VALUES (4,'KIA','VIG');
INSERT INTO marca VALUES (5,'HYUNDAI','VIG');
INSERT INTO marca VALUES (6,'DAIHATSU','VIG');
INSERT INTO marca VALUES (7,'MERCEDES-BENZ','VIG');
INSERT INTO marca VALUES (8,'AUDI','VIG');
INSERT INTO marca VALUES (9,'BMW','VIG');
INSERT INTO marca VALUES (10,'ISUZU','VIG');
INSERT INTO marca VALUES (11,'MAHINDRA','VIG');
INSERT INTO marca VALUES (12,'DODGE','VIG');
INSERT INTO marca VALUES (13,'CHEVROLET','VIG');
INSERT INTO marca VALUES (14,'FORD','VIG');
INSERT INTO marca VALUES (15,'PEUGEOT','VIG');
INSERT INTO marca VALUES (16,'RENAULT','VIG');
INSERT INTO marca VALUES (17,'HONDA','VIG');
INSERT INTO marca VALUES (18,'VOLKSWAGEN','VIG');
INSERT INTO marca VALUES (19,'CHERY','VIG');
INSERT INTO marca VALUES (20,'ALFA ROMEO','VIG');
INSERT INTO marca VALUES (21,'SUZUKI','VIG');
INSERT INTO marca VALUES (22,'DAEWOO','VIG');
INSERT INTO marca VALUES (23,'DFSK','VIG');
INSERT INTO marca VALUES (24,'JEEP','VIG');
INSERT INTO marca VALUES (25,'ZNA','VIG');
INSERT INTO marca VALUES (26,'ZOTYE','VIG');
INSERT INTO marca VALUES (27,'JAC','VIG');
INSERT INTO marca VALUES (28,'MAZDA','VIG');
INSERT INTO marca VALUES (29,'CRHYSLER','VIG');
INSERT INTO marca VALUES (30,'STAR','VIG');
INSERT INTO marca VALUES (31,'SUBARU','VIG');
INSERT INTO marca VALUES (32,'FOTON','VIG');
INSERT INTO marca VALUES (33,'GREAT WALL','VIG');
INSERT INTO marca VALUES (34,'LAND ROVER','VIG');
INSERT INTO marca VALUES (35,'SSANG YONG','VIG');
INSERT INTO marca VALUES (36,'LADA','VIG');
INSERT INTO marca VALUES (37,'FIAT','VIG');
INSERT INTO marca VALUES (38,'VOLVO','VIG');
INSERT INTO marca VALUES (39,'CITRÖEN','VIG');
INSERT INTO marca VALUES (40,'OPEL','VIG');
--mod
INSERT INTO modelo VALUES(1,1,'VIG','COROLLA');
INSERT INTO modelo VALUES(2,1,'VIG','TERCEL');
INSERT INTO modelo VALUES(3,1,'VIG','CORONA');
INSERT INTO modelo VALUES(4,1,'VIG','CAMRY');
INSERT INTO modelo VALUES(5,1,'VIG','CHASER');
INSERT INTO modelo VALUES(6,1,'VIG','CALDINA');
INSERT INTO modelo VALUES(7,1,'VIG','STARLET');
INSERT INTO modelo VALUES(8,1,'VIG','ALLEX');
INSERT INTO modelo VALUES(9,1,'VIG','RUNX');
INSERT INTO modelo VALUES(10,1,'VIG','SPACIO');
INSERT INTO modelo VALUES(11,1,'VIG','FUNCARGO');
INSERT INTO modelo VALUES(12,1,'VIG','STARLET GLANZA');
INSERT INTO modelo VALUES(13,1,'VIG','CORSA');
INSERT INTO modelo VALUES(14,1,'VIG','HIACE');
INSERT INTO modelo VALUES(15,1,'VIG','RUNNER');
INSERT INTO modelo VALUES(16,1,'VIG','SURF');
INSERT INTO modelo VALUES(17,1,'VIG','4TUNER');
INSERT INTO modelo VALUES(18,1,'VIG','RAV4');
INSERT INTO modelo VALUES(19,1,'VIG','LAND CRUISER');
INSERT INTO modelo VALUES(20,1,'VIG','FJ CRUISER');
INSERT INTO modelo VALUES(21,1,'VIG','HILUX');
--col
INSERT INTO color VALUES (1,'VIG','NEGRO');
INSERT INTO color VALUES (2,'VIG','BLANCO');
INSERT INTO color VALUES (3,'VIG','ROJO');
INSERT INTO color VALUES (4,'VIG','AZUL');
INSERT INTO color VALUES (5,'VIG','VERDE');
INSERT INTO color VALUES (6,'VIG','AMARILLO');
INSERT INTO color VALUES (7,'VIG','NARANJA');
INSERT INTO color VALUES (8,'VIG','GRIS');
INSERT INTO color VALUES (9,'VIG','CELESTE');
INSERT INTO color VALUES (10,'VIG','BORDO');
INSERT INTO color VALUES (11,'VIG','ROSADO');
INSERT INTO color VALUES (12,'VIG','CREMA');
INSERT INTO color VALUES (13,'VIG','CHAMPAGNE');
INSERT INTO color VALUES (14,'VIG','MARRON');
INSERT INTO color VALUES (15,'VIG','MORADO');
INSERT INTO color VALUES (16,'VIG','LILA');
INSERT INTO color VALUES (17,'VIG','FUXIA');
--un
INSERT INTO un_de_medida VALUES('UND','VIG','UNIDAD');
INSERT INTO un_de_medida VALUES('LTS','VIG','LITRO');
INSERT INTO un_de_medida VALUES('KGR','VIG','KGRAMO');
INSERT INTO un_de_medida VALUES('MTR','VIG','METRO');
--tipos de vehiculos
INSERT INTO tipo_veh VALUES(1,'VIG','SEDAN');
INSERT INTO tipo_veh VALUES(2,'VIG','HATCHBACK');
INSERT INTO tipo_veh VALUES(3,'VIG','FAMILIAR');
INSERT INTO tipo_veh VALUES(4,'VIG','FULGON DE CARGA');
INSERT INTO tipo_veh VALUES(5,'VIG','FULGON DE PASAJEROS');
INSERT INTO tipo_veh VALUES(6,'VIG','CABINA SIMPLE');
INSERT INTO tipo_veh VALUES(7,'VIG','DOBLE CABINA');
INSERT INTO tipo_veh VALUES(8,'VIG','CABINA PLUS');
INSERT INTO tipo_veh VALUES(9,'VIG','CAMIONETA');
--depositos
insert into deposito values (1,1,'VIG','1A'),(2,1,'VIG','1B'),(3,1,'VIG','1C'),(4,1,'VIG','1D'),(5,1,'VIG','1E'),(6,1,'VIG','1F'),(7,1,'VIG','1G'),(8,1,'VIG','1H')--casa matriz
			,(9,3,'VIG','1A'),(10,3,'VIG','1B'),(11,3,'VIG','1C'),(12,3,'VIG','1D'),(13,3,'VIG','1E'),(14,3,'VIG','1F'),(15,3,'VIG','1G'),(16,3,'VIG','1H')--itagua
			,(17,2,'VIG','1A'),(18,2,'VIG','1B'),(19,2,'VIG','1C'),(20,2,'VIG','1D'),(21,2,'VIG','1E'),(22,2,'VIG','1F'),(23,2,'VIG','1G'),(24,2,'VIG','1H');--cde;
--prov
INSERT INTO proveedor VALUES(1,'VIG','SCHAFLER DESIGN','ECUADOR 133 C/ GRAL. SANTOS',961777004,'harder2725@hotmail.com','3567414-0');
INSERT INTO proveedor VALUES(2,'VIG','IDICON','RCA. ARGENTINA 2240 C/CARIOS',000,'a definir','a definir');
--permisos
insert into permiso values (0,'VIG','CONSULT'),(1,'VIG','FULL ACCESS'),(2,'VIG','JUST ADD'),(3,'VIG','ADD & SEE'),(4,'VIG','ADD & EDIT');
--modulo
insert into modulo values (1,'COMPRA'),(2,'SERVICIO'),(3,'COBRO'),(4,'REF. ESPECIALES');
--gui
insert into gui values        
---------------------------------MOD COMPRA.
				(1,'VIG',1,'PEDIDO'),(2,'VIG',1,'ORDEN'),(3,'VIG',1,'REG. FACT.'),(4,'VIG',1,'AJUSTE'),(5,'VIG',1,'REMISION'),--compra mat
				(6,'VIG',1,'PEDIDO_R'),(7,'VIG',1,'ORDEN_R'),(8,'VIG',1,'REG. FACT_R.'),(9,'VIG',1,'AJUSTE_R'),(10,'VIG',1,'REMISION_R'),--compra rep

				(12,'VIG',1,'PROVEEDOR'),(13,'VIG',1,'MOTIVO'),(14,'VIG',1,'DEPOSITO'),--ref del modulo
---------------------------------MOD SERVICIO.
				(15,'VIG',2,'PRESUPUESTO'),(16,'VIG',2,'RECEPCION'),(17,'VIG',2,'REG. ORD.'),(18,'VIG',2,'EMITIR ORD.'),(19,'VIG',2,'GEST. MAT.'),(20,'VIG',2,'Q_CONTROL'),(27,'VIG',2,'GARANTIA'),

				(21,'VIG',2,'ASEGURADO'),(22,'VIG',2,'VEHICULO'),(23,'VIG',2,'SEGURO'),(24,'VIG',2,'TRABAJO'),(25,'VIG',2,'MARCA'),(26,'VIG',2,'MODELO'),(28,'VIG',2,'COLOR'),
---------------------------------MOD COBRO.
				(29,'VIG',3,'FACTURACION'),(30,'VIG',3,'GEST. CAJA'),(31,'VIG',3,'GEST. COBRO'),
---------------------------------REF. ESPECIALES.
				(32,'VIG',4,'FUNCIONARIOS'),(33,'VIG',4,'TIMBRADO'),(34,'VIG',4,'CIUDAD'),(35,'VIG',4,'BARRIO'),(36,'VIG',4,'PIEZAS'),(37,'VIG',4,'BANCO'),(38,'VIG',4,'CAJA'),(39,'VIG',4,'CARGO'),
                                (40,'VIG',4,'MOT. AJUSTE'),(41,'VIG',4,'MOT. GARANTIA'),(42,'VIG',4,'NACIONALIDAD'),(43,'VIG',4,'PROV. SERVICIO'),(44,'VIG',4,'ROL'),(45,'VIG',4,'SUCURSAL'),(46,'VIG',4,'USUARIO'),(11,'VIG',4,'MATERIAL');
--rol y detalle--
insert into rol values (1,'VIG','GODLIKE');
		insert into detalle_rol values  (1,1,1),--ped
						(1,2,1),--ord
						(1,3,1),--reg. fact
						(1,4,1),--ajuste
						(1,5,1),--remision
						(1,6,1),--ped_r
						(1,7,1),--ord_r
						(1,8,1),--reg. fact_r
						(1,9,1),--ajuste_r
						(1,10,1),--remision_r
						(1,11,1),--mat
						(1,12,1),--prov
						(1,13,1),--mot
						(1,14,1),--dep
						(1,15,1),--prep
						(1,16,1),--recepcion
						(1,17,1),--reg. ord.
						(1,18,1),--emi. ord.
						(1,19,1),--gestMat
						(1,20,1),--qControl
						(1,21,1),--asegurado
						(1,22,1),--veh
						(1,23,1),--seg
						(1,24,1),--trab
						(1,25,1),--mar
						(1,26,1),--mod
						(1,27,1),--Garantia
						(1,28,1),--color
						(1,29,1),--fact
						(1,30,1),--gestCaja
						(1,31,1),--gestCobro
						(1,32,1),--func
						(1,33,1),--tim
						(1,34,1),--ciu
						(1,35,1),--bar
						(1,36,1),--pzas
						(1,37,1),--ban
						(1,38,1),--caj
						(1,39,1),--car
						(1,40,1),--mot
						(1,41,1),--mot_g
						(1,42,1),--nac
						(1,43,1),--prov_s
						(1,44,1),--rol
						(1,45,1),--suc
						(1,46,1)--usr
						;
--usuario y detalle
insert into usuario values (0,'VIG','ROOT','202cb962ac59075b964b07152d234b70');
insert into detalle_usuario values (0,1,'VIG');
--emp con su detalle
INSERT INTO empleado VALUES(3567414,0,'VIG',1,0,'HELMUT DANIEL','SCHAFLER VALENZUELA',0961777004,'DR. LUIS DE GASPERI 3177 C/ UNIV. DEL CHACO','1992-11-30');
insert into detalle_emp values (1,3567414,current_date,null);
--material-select * from material
INSERT INTO material VALUES(1,'VIG','UND','LIJA 100');
INSERT INTO material VALUES(2,'VIG','UND','LIJA 1000');
INSERT INTO material VALUES(3,'VIG','UND','LIJA 500');
INSERT INTO material VALUES(4,'VIG','KGR','MASILLA DUDU');
INSERT INTO material VALUES(5,'VIG','UND','ESTOPA');
INSERT INTO material VALUES(6,'VIG','LTS','DILUYENTE');
INSERT INTO material VALUES(7,'VIG','LTS','BARNIZ 5100'),
                     (8,'VIG','LTS','BARNIZ 1100'),
                     (9,'VIG','KGR','ESTOPA SINTETICA'),
                     (10,'VIG','KGR','MASILLA POLIESTER'),
                     (11,'VIG','LTS','DILUYENTE POLIESTER'),
                     (12,'VIG','LTS','THINER COMUN'),
                     (13,'VIG','MTR','CINTA SEPARADORA'),
                     (14,'VIG','UND','DISCO FLAP'),
                     (15,'VIG','UND','DISCO DE CATE'),
                     (16,'VIG','UND','LIJA 1500'),
                     (17,'VIG','UND','LIJA 2000'),
                     (18,'VIG','UND','LIJA 3000'),
                     (19,'VIG','UND','LIJA 80'),
                     (20,'VIG','UND','LIJA 180'),
                     (21,'VIG','UND','LIJA 220'),
                     (22,'VIG','UND','LIJA 360'),
                     (23,'VIG','UND','LIJA 600'),
                     (24,'VIG','LTS','ABRILLANTADOR'),
                     (25,'VIG','UND','P. DE PULIR MAXI'),
                     (26,'VIG','UND','AUTO BRILLO'),
                     (27,'VIG','UND','GORRO P/ PULIDO'),
                     (28,'VIG','UND','POXIPOL'),
                     (29,'VIG','UND','LIJA 80 DISCO'),
                     (30,'VIG','LTS','DESENGRASANTE');
--detalle_material-select * from detalle_material
	/*main*/	insert into detalle_material values (1,1,20),(1,2,20),(1,3,20),(1,4,20),(1,5,20),(1,6,20),(1,7,20),(1,8,20),(1,9,20),(1,10,20),(1,11,20),(1,12,20),(1,13,20),
		(1,14,20),(1,15,20),(1,16,20),(1,17,20),(1,18,20),(1,19,20),(1,20,20),(1,21,20),(1,22,20),(1,23,20),(1,24,20),(1,25,20),(1,26,20),(1,27,20),(1,28,20),(1,29,20),(1,30,20);
--delete from detalle_material where suc_cod=2
	/*cde*/		insert into detalle_material values (2,1,20),(2,2,20),(2,3,20),(2,4,20),(2,5,20),(2,6,20),(2,7,20),(2,8,20),(2,9,20),(2,10,20),(2,11,20),(2,12,20),(2,13,20),
		(2,14,20),(2,15,20),(2,16,20),(2,17,20),(2,18,20),(2,19,20),(2,20,20),(2,21,20),(2,22,20),(2,23,20),(2,24,20),(2,25,20),(2,26,20),(2,27,20),(2,28,20),(2,29,20),(2,30,20);

	/*itagua*/	insert into detalle_material values (3,1,20),(3,2,20),(3,3,20),(3,4,20),(3,5,20),(3,6,20),(3,7,20),(3,8,20),(3,9,20),(3,10,20),(3,11,20),(3,12,20),(3,13,20),
		(3,14,20),(3,15,20),(3,16,20),(3,17,20),(3,18,20),(3,19,20),(3,20,20),(3,21,20),(3,22,20),(3,23,20),(3,24,20),(3,25,20),(3,26,20),(3,27,20),(3,28,20),(3,29,20),(3,30,20);

--detalle_mat
	
--tipos de pago
INSERT INTO tipo_de_pago VALUES(1,'VIG','CONTADO');
INSERT INTO tipo_de_pago VALUES(2,'VIG','CRÉDITO');
--tipos de motivo de ajuste
INSERT INTO tipo_mot_aju VALUES(1,'VIG','NEGATIVO');
INSERT INTO tipo_mot_aju VALUES(2,'VIG','POSITIVO');
--tipos de ajuste
INSERT INTO motivo_ajuste VALUES(1,1,'VIG','DESPERDICIO'),(2,1,'VIG','PERDIDA'),(3,1,'VIG','HURTO'),(4,1,'VIG','VENCIMIENTO')
			,(5,1,'VIG','PROD. DEFECTUOSO'),(6,2,'VIG','REEMBOLSO'),(7,2,'VIG','DEVUELTO'),(8,2,'VIG','CAMBIADO');
--motivo de garantia
INSERT INTO motivo_garantia VALUES (1,'VIG','BURBUJAS');
--comb
INSERT INTO combustible VALUES(1,'VIG','LLENO');
INSERT INTO combustible VALUES(2,'VIG','VACIO');
INSERT INTO combustible VALUES(3,'VIG','MENOS DE 1/4');
INSERT INTO combustible VALUES(4,'VIG','1/4');
INSERT INTO combustible VALUES(5,'VIG','MAS DE 1/4');
INSERT INTO combustible VALUES(6,'VIG','MENOS DE 1/2');
INSERT INTO combustible VALUES(8,'VIG','1/2');
INSERT INTO combustible VALUES(9,'VIG','MAS DE 1/2');
INSERT INTO combustible VALUES(10,'VIG','MENOS DE 3/4');
INSERT INTO combustible VALUES(11,'VIG','3/4');
INSERT INTO combustible VALUES(12,'VIG','MAS DE 3/4');
--tipo de repuestos
/*INSERT INTO tipo_rep VALUES(1,1,'CARROCERIA');
INSERT INTO tipo_rep VALUES(2,1,'CRISTALES');
INSERT INTO tipo_rep VALUES(3,1,'FAROS');
INSERT INTO tipo_rep VALUES(4,1,'INTERIOR');
INSERT INTO tipo_rep VALUES(5,1,'PARTE MECANICA');
INSERT INTO tipo_rep VALUES(6,1,'PARTE ELECTRICA');
INSERT INTO tipo_rep VALUES(7,1,'ACCESORIOS');*/
--repuestos
/*INSERT INTO repuesto VALUES (1,1,2,'PARABRISAS DEL.'),(2,1,2,'PARABRISAS TRAS.'),(3,1,2,'VIDRIO DEL. IZQ.'),(4,1,2,'VIDRIO DEL. DER.'),(5,1,2,'VIDRIO TRAS. IZQ.'),(6,1,2,'VIDRIO TRAS. DER.'),(7,1,2,'TECHITO'),(8,1,2,'VENTILETE DEL. IZQ.'),(9,1,2,'VENTILETE DEL. DER.'),(10,1,2,'VENTILETE TRAS. IZQ.'),(11,1,2,'VENTILETE TRAS. DER.');
INSERT INTO repuesto VALUES (12,1,3,'FARO DEL. IZQ.'),(13,1,3,'FARO DEL. DER.'),(14,1,3,'SEÑALERO DEL. IZQ.'),(15,1,3,'SEÑALERO DEL. DER.'),(16,1,3,'BUSCAHUELLAS IZQ.'),(17,1,3,'BUSCAHUELLAS DER.'),(18,1,3,'SEÑALERITO IZQ.'),(19,1,3,'SEÑALERITO DER.'),(20,1,3,'TERCERA LUZ DE FRENO'),(21,1,3,'FARO ESQ. TRAS. IZQ.'),(22,1,3,'FARO ESQ. TRAS. DER.'),
(23,1,3,'FARO INT. TRAS. DER.'),(24,1,3,'FARO INT. TRAS. DER.');*/
--piezas
--INSERT INTO pieza VALUES(1,'VIG','TECHO',2),(2,'VIG','CAPOT',2),(3,'VIG','TECHO FULGON',4),(4,'VIG','PUERTA DEL. IZQ.',1),(5,1,'PUERTA DEL. DER.',1),(6,1,'PUERTA TRAS. IZQ.',1),(7,1,'PUERTA TRAS. DER.',1),(8,1,'GUARDABARRO DEL. IZQ.',1),
--(9,1,'GUADABARRO DEL. DER.',1),(10,1,'ZOCALO IZQ.',1),(11,1,'ZOCALO DER.',1),(12,1,'PARAGOLPES DEL.',1),(13,1,'PARAGOLPES TRAS.',1);
--etapas
INSERT INTO etapa VALUES (1,'VIG','PREPARADO'),(2,'VIG','PINTURA'),(3,'VIG','FINALIZADO');
--cajas
INSERT INTO caja VALUES (1,'VIG','CAJ. MATRIZ'),(2,'VIG','CAJ. CDE'),(3,'VIG','CAJ. ITAUGUA');
