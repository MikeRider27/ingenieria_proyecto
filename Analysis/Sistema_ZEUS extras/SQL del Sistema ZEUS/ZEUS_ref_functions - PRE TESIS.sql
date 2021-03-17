-----------------------------------------------------------------------------------BANCO-------------------------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION abm_ban(ban integer,cod integer,des character varying)RETURNS int AS
$BODY$
declare
find int=0;
begin
CASE ban
        WHEN 1 THEN -----------------------------------------------------------------
select count(1) into find from banco where ban_des=des;
if find >0 then
		raise notice '%','Ya existe un Banco con esa Descripción';
		return 1;
else
select count(1) into find from banco where ban_cod=cod;
	if find>0 then
select coalesce(max(ban_cod),0)+1 into cod from banco;
raise notice '%','Se designó el Codigo '||cod||' para evitar conflictos';
end if;
      INSERT INTO banco VALUES (cod,'VIG',upper(des));
      raise notice '%','NOPE';
      return 0;
end if;
      WHEN 2 THEN -------------------------------------------------------------------SELECT est_cod,prov_cod,prov_des,prov_dir,prov_tel,prov_mail,prov_ruc FROM v_prov  WHERE est_cod='VIG' AND prov_des LIKE '%%%%' ORDER BY 2 ASC
select count(1) into find from banco where ban_des=des and ban_cod!=cod;	
if find >0 then
		raise notice '%','Ya existe un Banco con esa Descripción';
		return 1;
else
             UPDATE banco SET ban_des=upper(des) WHERE ban_cod=cod;
             raise notice '%','NOPE';
             return 0;
end if;
      WHEN 3 THEN -----------------------------------------------------------------
update banco set est_cod='ANU' WHERE ban_cod=cod;
      raise notice '%','NOPE';
      return 0;
end case;
    end;
    $BODY$
  LANGUAGE plpgsql;
-----------------------------------------------------------------------------------NACIONALIDADES-------------------------------------------------------------------------------------------------
  CREATE OR REPLACE FUNCTION abm_nac(ban integer,cod integer,des character varying)RETURNS int AS
$BODY$
declare
find int=0;
begin
CASE ban
        WHEN 1 THEN 
select count(1) into find from nacionalidad where nac_des=des;
if find >0 then
		raise notice '%','Ya existe una Nacionalidad con esa Descripción';
		return 1;
else
select count(1) into find from nacionalidad where nac_cod=cod;
	if find>0 then
select coalesce(max(nac_cod),0)+1 into cod from nacionalidad;
raise notice '%','Se designó el Codigo '||cod||' para evitar conflictos';
end if;
      INSERT INTO nacionalidad VALUES (cod,'VIG',upper(des));
raise notice '%','NOPE';
      return 0;
end if;
      WHEN 2 THEN
select count(1) into find from nacionalidad where nac_des=des and nac_cod!=cod;	
if find >0 then
		raise notice '%','Ya existe una Nacionalidad con esa Descripción';
		return 1;
else
             UPDATE nacionalidad SET nac_des=upper(des) WHERE nac_cod=cod;
             raise notice '%','NOPE';
             return 0;
end if;
      WHEN 3 THEN UPDATE nacionalidad SET est_cod='ANU' WHERE nac_cod=cod;
      raise notice '%','NOPE';
      return 0;
end case;
    end;
    $BODY$
  LANGUAGE plpgsql;
-----------------------------------------------------------------------------------MARCAS-------------------------------------------------------------------------------------------------
id_mar integer NOT NULL,
  descrip character varying(30),
  est_cod 
  CREATE OR REPLACE FUNCTION abm_mar(ban integer,cod integer,des character varying)RETURNS int AS
$BODY$
declare
find int=0;
begin
CASE ban
        WHEN 1 THEN 
select count(1) into find from marca where descrip=des;
if find >0 then
		raise notice '%','Ya existe una Marca de Vehículo con esa Descripción';
		return 1;
else
select count(1) into find from marca where id_mar=cod;
	if find>0 then
select coalesce(max(id_mar),0)+1 into cod from marca;
raise notice '%','Se designó el Codigo '||cod||' para evitar conflictos';
end if;
      INSERT INTO marca VALUES (cod,upper(des),'VIG');
      raise notice '%','NOPE';
      return 0;
end if;
      WHEN 2 THEN
select count(1) into find from marca where descrip=upper(des) and id_mar!=cod;	
if find >0 then
		raise notice '%','Ya existe una Marca de Vehículo con esa Descripción';
		return 1;
else
             UPDATE marca SET descrip=upper(des) WHERE id_mar=cod;
             raise notice '%','NOPE';
             return 0;
end if;
      WHEN 3 THEN UPDATE marca SET est_cod='ANU' WHERE id_mar=cod;
      raise notice '%','NOPE';
      return 0;
end case;
    end;
    $BODY$
  LANGUAGE plpgsql;
-----------------------------------------------------------------------------------COLORES-------------------------------------------------------------------------------------------------
  CREATE OR REPLACE FUNCTION abm_col(ban integer,cod integer,des character varying)RETURNS int AS
$BODY$
declare
find int=0;
begin
CASE ban
        WHEN 1 THEN 
select count(1) into find from color where col_des=des;
if find >0 then
		raise notice '%','Ya existe un Color con esa Descripción';
		return 1;
else
select count(1) into find from color where col_cod=cod;
	if find>0 then
select coalesce(max(col_cod),0)+1 into cod from color;
raise notice '%','Se designó el Codigo '||cod||' para evitar conflictos';
end if;
      INSERT INTO color VALUES (cod,'VIG',upper(des));
      raise notice '%','NOPE';
      return 0;
end if;
      WHEN 2 THEN
select count(1) into find from color where col_des=des and col_cod!=cod;	
if find >0 then
		raise notice '%','Ya existe un Color con esa Descripción';
		return 1;
else
             UPDATE color SET col_des=upper(des) WHERE col_cod=cod;
             raise notice '%','NOPE';
             return 0;
end if;
      WHEN 3 THEN UPDATE color SET est_cod='ANU' WHERE col_cod=cod;
      raise notice '%','NOPE';
      return 0;
end case;
    end;
    $BODY$
  LANGUAGE plpgsql;
-----------------------------------------------------------------------------------CIUDADES-------------------------------------------------------------------------------------------------
  CREATE OR REPLACE FUNCTION abm_ciu(ban integer,cod integer,des character varying)RETURNS int AS
$BODY$
declare
find int=0;
begin
CASE ban
        WHEN 1 THEN 
select count(1) into find from ciudad where ciu_des=des;
if find >0 then
		raise notice '%','Ya existe una Ciudad con esa Descripción';
		return 1;
else
select count(1) into find from ciudad where ciu_cod=cod;
	if find>0 then
select coalesce(max(ciu_cod),0)+1 into cod from ciudad;
raise notice '%','Se designó el Codigo '||cod||' para evitar conflictos';
end if;
      INSERT INTO ciudad VALUES (cod,'VIG',upper(des));
      raise notice '%','NOPE';
      return 0;
end if;
      WHEN 2 THEN
select count(1) into find from ciudad where ciu_des=des and ciu_cod!=cod;	
if find >0 then
		raise notice '%','Ya existe una Ciudad con esa Descripción';
		return 1;
else
             UPDATE ciudad SET ciu_des=upper(des) WHERE ciu_cod=cod;
             raise notice '%','NOPE';
             return 0;
end if;
      WHEN 3 THEN UPDATE ciudad SET est_cod='ANU' WHERE ciu_cod=cod;
      raise notice '%','NOPE';
      return 0;
end case;
    end;
    $BODY$
  LANGUAGE plpgsql;
-----------------------------------------------------------------------------------TRABAJOS DE TERCEROS-------------------------------------------------------------------------------------------------
  CREATE OR REPLACE FUNCTION abm_trab(ban integer,cod integer,des character varying)RETURNS int AS
$BODY$
declare
find int=0;
begin
CASE ban
        WHEN 1 THEN 
select count(1) into find from trabajos_terciarios where trab_des=des;
if find >0 then
		raise notice '%','Ya existe un Trabajo Terciario con esa Descripción';
		return 1;
else
select count(1) into find from trabajos_terciarios where trab_cod=cod;
	if find>0 then
select coalesce(max(trab_cod),0)+1 into cod from trabajos_terciarios;
raise notice '%','Se designó el Codigo '||cod||' para evitar conflictos';
end if;
      INSERT INTO trabajos_terciarios VALUES (cod,'VIG',upper(des));
      raise notice '%','NOPE';
      return 0;
end if;
      WHEN 2 THEN
select count(1) into find from trabajos_terciarios where trab_des=des and trab_cod!=cod;	
if find >0 then
		raise notice '%','Ya existe un Trabajo Terciario con esa Descripción';
		return 1;
else
             UPDATE trabajos_terciarios SET trab_des=upper(des) WHERE trab_cod=cod;
             raise notice '%','NOPE';
             return 0;
end if;
      WHEN 3 THEN UPDATE trabajos_terciarios SET est_cod='ANU' WHERE trab_cod=cod;
      raise notice '%','NOPE';
      return 0;
end case;
    end;
    $BODY$
  LANGUAGE plpgsql;
  
-----------------------------------------------------------------------------------CAJAS-------------------------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION abm_caj(ban integer,cod integer,des character varying)RETURNS int AS
$BODY$
declare
find int=0;
begin
CASE ban
        WHEN 1 THEN 
select count(1) into find from caja where caj_des=des;
if find >0 then
		raise notice '%','Ya existe una Caja con esa Descripción';
		return 1;
else
select count(1) into find from caja where caj_cod=cod;
	if find>0 then
select coalesce(max(caj_cod),0)+1 into cod from caja;
raise notice '%','Se designó el Codigo '||cod||' para evitar conflictos';
end if;
      INSERT INTO caja VALUES (cod,'VIG',upper(des));
      raise notice '%','NOPE';
      return 0;
end if;
      WHEN 2 THEN
select count(1) into find from caja where caj_des=des and caj_cod!=cod;	
if find >0 then
		raise notice '%','Ya existe una Caja con esa Descripción';
		return 1;
else
             UPDATE caja SET caj_des=upper(des) WHERE caj_cod=cod;
             raise notice '%','NOPE';
             return 0;
end if;
      WHEN 3 THEN UPDATE caja SET est_cod='ANU' WHERE caj_cod=cod;
      raise notice '%','NOPE';
      return 0;
end case;
    end;
    $BODY$
  LANGUAGE plpgsql;

 -----------------------------------------------------------------------------------MOTIVO------------------------------------------------------------------------------------------------- 
CREATE OR REPLACE FUNCTION abm_mot(ban integer,cod integer,des character varying,tmot integer)RETURNS int AS
$BODY$
declare
find int=0;
begin
CASE ban
        WHEN 1 THEN 
select count(1) into find from motivo_ajuste where mot_des=des;
if find >0 then
		raise notice '%','Ya existe un Motivo de Ajuste con esa Descripción';
		return 1;
else
select count(1) into find from motivo_ajuste where mot_cod=cod;
	if find>0 then
select coalesce(max(mot_cod),0)+1 into cod from motivo_ajuste;
raise notice '%','Se designó el Codigo '||cod||' para evitar conflictos';
end if;
      INSERT INTO motivo_ajuste VALUES (cod,tmot,'VIG',upper(des));
      raise notice '%','NOPE';
      return 0;
end if;
      WHEN 2 THEN
select count(1) into find from motivo_ajuste where mot_des=des and mot_cod!=cod;	
if find >0 then
		raise notice '%','Ya existe un Motivo de Ajuste con esa Descripción';
		return 1;
else
             UPDATE motivo_ajuste SET mot_des=upper(des),tmot_cod=tmot WHERE mot_cod=cod;
             raise notice '%','NOPE';
             return 0;
end if;
      WHEN 3 THEN UPDATE motivo_ajuste SET est_cod='ANU' WHERE mot_cod=cod;
      raise notice '%','NOPE';
      return 0;
end case;
    end;
    $BODY$
  LANGUAGE plpgsql;

-----------------------------------------------------------------------------------MOTIVOS DE GARANTIA-----------------------------------------------------------------------------------------------  
CREATE OR REPLACE FUNCTION abm_motg(ban integer,cod integer,des character varying)RETURNS int AS
$BODY$
declare
find int=0;
begin
CASE ban
        WHEN 1 THEN 
select count(1) into find from motivo_garantia where mot_des=des;
if find >0 then
		raise notice '%','Ya existe un Motivo de Garantia con esa Descripción';
		return 1;
else
select count(1) into find from motivo_garantia where mot_cod=cod;
	if find>0 then
select coalesce(max(mot_cod),0)+1 into cod from motivo_garantia;
raise notice '%','Se designó el Codigo '||cod||' para evitar conflictos';
end if;
      INSERT INTO motivo_garantia VALUES (cod,'VIG',upper(des));
      raise notice '%','NOPE';
      return 0;
end if;
      WHEN 2 THEN
select count(1) into find from motivo_garantia where mot_des=des and mot_cod!=cod;	
if find >0 then
		raise notice '%','Ya existe un Motivo de Garantia con esa Descripción';
		return 1;
else
             UPDATE motivo_garantia SET mot_des=upper(des) WHERE mot_cod=cod;
             raise notice '%','NOPE';
             return 0;
end if;
      WHEN 3 THEN UPDATE motivo_garantia SET est_cod='ANU' WHERE mot_cod=cod;
      raise notice '%','NOPE';
      return 0;
end case;
    end;
    $BODY$
  LANGUAGE plpgsql;

 -----------------------------------------------------------------------------------ETAPAS------------------------------------------------------------------------------------------------ 
/*CREATE OR REPLACE FUNCTION abm_eta(ban integer,cod integer,des character varying)RETURNS int AS
$BODY$
declare
find int=0;
begin
CASE ban
        WHEN 1 THEN 
select count(1) into find from etapa where eta_des=des;
if find >0 then
		raise notice '%','Ya existe una Etapa con esa Descripción';
		return 1;
else
select count(1) into find from etapa where eta_cod=cod;
	if find>0 then
select coalesce(max(eta_cod),0)+1 into cod from etapa;
raise notice '%','Se designó el Codigo '||cod||' para evitar conflictos';
end if;
      INSERT INTO etapa VALUES (cod,'VIG',upper(des));
      raise notice '%','NOPE';
      return 0;
end if;
      WHEN 2 THEN
select count(1) into find from etapa where eta_des=des and eta_cod!=cod;	
if find >0 then
		raise notice '%','Ya existe una Etapa con esa Descripción';
		return 1;
else
             UPDATE etapa SET eta_des=upper(des) WHERE eta_cod=cod;
             raise notice '%','NOPE';
             return 0;
end if;
      WHEN 3 THEN UPDATE etapa SET est_cod='ANU' WHERE eta_cod=cod;
      raise notice '%','NOPE';
      return 0;
end case;
    end;
    $BODY$
  LANGUAGE plpgsql;*/

-----------------------------------------------------------------------------------DEPOSITOS-------------------------------------------------------------------------------------------------  
CREATE OR REPLACE FUNCTION abm_dep(ban integer,cod integer,suc integer,des character varying)RETURNS int AS
$BODY$
declare
find int=0;
begin
CASE ban
        WHEN 1 THEN 
select count(1) into find from deposito where dep_des=des and suc_cod=suc;
if find >0 then
		raise notice '%','Ya existe un Depósito con esa Descripción en dicha Sucursal';
		return 1;
else
select count(1) into find from deposito where dep_cod=cod;
	if find>0 then
select coalesce(max(dep_cod),0)+1 into cod from deposito;
raise notice '%','Se designó el Codigo '||cod||' para evitar conflictos';
end if;
      INSERT INTO deposito VALUES (cod,suc,'VIG',upper(des));
      raise notice '%','NOPE';
      return 0;
end if;
      WHEN 2 THEN
select count(1) into find from deposito where dep_des=des and dep_cod!=cod and suc_cod=suc;	
if find >0 then
		raise notice '%','Ya existe un Depósito con esa Descripción en dicha Sucursal';
		return 1;
else
             UPDATE deposito SET dep_des=upper(des),suc_cod=suc WHERE dep_cod=cod;
             raise notice '%','NOPE';
             return 0;
end if;
      WHEN 3 THEN UPDATE deposito SET est_cod='ANU' WHERE dep_cod=cod;
      raise notice '%','NOPE';
      return 0;
end case;
    end;
    $BODY$
  LANGUAGE plpgsql;
 -----------------------------------------------------------------------------------PIEZAS------------------------------------------------------------------------------------------------ 
CREATE OR REPLACE FUNCTION abm_pza(ban integer,cod integer,tveh integer,des character varying,cant numeric)RETURNS int AS--select * from pieza
$BODY$
declare
find int=0;
begin
CASE ban
        WHEN 1 THEN 
select count(1) into find from pieza where pie_des=des;
if find >0 then
		raise notice '%','Ya existe una Pieza con esa Descripción';
		return 1;
else
select count(1) into find from pieza where pie_cod=cod;
	if find>0 then
select coalesce(max(pie_cod),0)+1 into cod from pieza;
raise notice '%','Se designó el Codigo '||cod||' para evitar conflictos';
end if;
      INSERT INTO pieza VALUES (cod,tveh,'VIG',upper(des),cant);
      raise notice '%','NOPE';
      return 0;
end if;
      WHEN 2 THEN
select count(1) into find from pieza where pie_des=des and pie_cod!=cod;	
if find >0 then
		raise notice '%','Ya existe una Pieza con esa Descripción';
		return 1;
else
             UPDATE pieza SET pie_des=upper(des),pie_cant=cant,tveh_cod=tveh WHERE pie_cod=cod;
             raise notice '%','NOPE';
             return 0;
end if;
      WHEN 3 THEN UPDATE pieza SET est_cod='ANU' WHERE pie_cod=cod;
      raise notice '%','NOPE';
      return 0;
end case;
    end;
    $BODY$
  LANGUAGE plpgsql;

 -----------------------------------------------------------------------------------BARRIOS------------------------------------------------------------------------------------------------- 
CREATE OR REPLACE FUNCTION abm_bar(ban integer,cod integer,ciu integer,des character varying)RETURNS int AS
$BODY$
declare
find int=0;
begin
CASE ban
        WHEN 1 THEN
select count(1) into find from barrio where bar_des=des and ciu_cod=ciu;
if find >0 then
		raise notice '%','Ya existe un Barrio con esa Descripción en dicha Ciudad';
		return 1;
else
select count(1) into find from barrio where bar_cod=cod;
	if find>0 then
select coalesce(max(bar_cod),0)+1 into cod from barrio;
raise notice '%','Se designó el Codigo '||cod||' para evitar conflictos';
end if;
      INSERT INTO barrio VALUES (cod,ciu,'VIG',upper(des));
      raise notice '%','NOPE';
      return 0;
end if;
      WHEN 2 THEN
select count(1) into find from barrio where bar_des=des and bar_cod!=cod and ciu_cod=ciu;	
if find >0 then
		raise notice '%','Ya existe un Barrio con esa Descripción en dicha Ciudad';
		return 1;
else
             UPDATE barrio SET bar_des=upper(des),ciu_cod=ciu WHERE bar_cod=cod;
             raise notice '%','NOPE';
             return 0;
end if;
      WHEN 3 THEN UPDATE barrio SET est_cod='ANU' WHERE bar_cod=cod;
      raise notice '%','NOPE';
      return 0;
end case;
    end;
    $BODY$
  LANGUAGE plpgsql;
-----------------------------------------------------------------------------------CARGOS-------------------------------------------------------------------------------------------------
  CREATE OR REPLACE FUNCTION abm_car(ban integer,cod integer,des character varying,usr boolean)RETURNS int AS
$BODY$
declare
find int=0;
begin
CASE ban
        WHEN 1 THEN 
select count(1) into find from cargo where car_des=des;
if find >0 then
		raise notice '%','Ya existe un Cargo con esa Descripción';
		return 1;
else
select count(1) into find from cargo where car_cod=cod;
	if find>0 then
select coalesce(max(car_cod),0)+1 into cod from cargo;
raise notice '%','Se designó el Codigo '||cod||' para evitar conflictos';--select * from cargo
end if;
      INSERT INTO cargo VALUES (cod,'VIG',upper(des),usr);
      raise notice '%','NOPE';
      return 0;
end if;
      WHEN 2 THEN
select count(1) into find from cargo where car_des=des and car_cod!=cod;	
if find >0 then
		raise notice '%','Ya existe un Cargo con esa Descripción';
		return 1;
else
             UPDATE cargo SET car_des=upper(des),car_usr=usr WHERE car_cod=cod;
             raise notice '%','NOPE';
             return 0;
end if;
      WHEN 3 THEN update cargo set est_cod='ANU' WHERE car_cod=cod;
      raise notice '%','NOPE';
      return 0;
end case;
    end;
    $BODY$
  LANGUAGE plpgsql;
-----------------------------------------------------------------------------------CLIENTES-------------------------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION abm_cli(ban integer,cod integer,nac integer,ciu integer,bar integer,nom character varying,
ape character varying,cel integer,dir character varying, fnac date)RETURNS int AS
$BODY$
declare
find int=0;
begin
CASE ban
        WHEN 1 THEN 
select count(1) into find from cliente where cli_ci=cod;
if find >0 then
		raise notice '%','Ya existe un Asegurado con ese Nº de Cedula';
		return 1;
else
      INSERT INTO cliente VALUES (cod,'VIG',nac,ciu,bar,upper(nom),upper(ape),cel,upper(dir),fnac);--SELECT * FROM CLIENTE
      raise notice '%','NOPE';
      return 0;
end if;
      WHEN 2 THEN
select count(1) into find from cliente where cli_ci=cod;	
if find >0 then
		raise notice '%','Ya existe un Asegurado con ese Nº de Cedula';
		return 1;
else
             UPDATE cliente SET nac_cod=nac,ciu_cod=ciu,bar_cod=bar,cli_nom=upper(nom),cli_ape=upper(ape),cli_cel=cel,cli_dir=upper(dir),cli_fnac=fnac WHERE cli_ci=cod;
             raise notice '%','NOPE';
             return 0;
end if;
      WHEN 3 THEN update cliente set est_cod='ANU' WHERE cli_ci=cod;
      raise notice '%','NOPE';
      return 0;
end case;
    end;
    $BODY$
  LANGUAGE plpgsql;
-----------------------------------------------------------------------------------SEGURO-------------------------------------------------------------------------------------------------
  CREATE OR REPLACE FUNCTION abm_seg(ban integer,cod integer,des character varying,dir character varying,tel integer,mail character varying)RETURNS int AS
$BODY$
declare
find int=0;
begin
CASE ban
        WHEN 1 THEN 
select count(1) into find from seguro where seg_des=des;
if find >0 then
		raise notice '%','Ya existe una Cia. de Seguro con esa Descripción';
		return 1;
else
select count(1) into find from seguro where seg_cod=cod;
	if find>0 then
select coalesce(max(seg_cod),0)+1 into cod from seguro;
raise notice '%','Se designó el Codigo '||cod||' para evitar conflictos';
end if;
      INSERT INTO seguro VALUES (cod,'VIG',upper(des),upper(dir),tel,mail);
      raise notice '%','NOPE';
      return 0;
end if;
      WHEN 2 THEN
select count(1) into find from seguro where seg_des=des and seg_cod!=cod;	
if find >0 then
		raise notice '%','Ya existe una Cia. de Seguro con esa Descripción';
		return 1;
else
             UPDATE seguro SET seg_des=upper(des),seg_dir=upper(dir),seg_tel=tel,seg_mail=mail WHERE seg_cod=cod;
             raise notice '%','NOPE';
             return 0;
end if;
      WHEN 3 THEN update seguro set est_cod='ANU' WHERE seg_cod=cod;
      raise notice '%','NOPE';
      return 0;
end case;
    end;
    $BODY$
  LANGUAGE plpgsql;
----------------------------------------------------------------------------------SUCURSAL------------------------------------------------------------------------------------------------
  CREATE OR REPLACE FUNCTION abm_suc(ban integer,cod integer,des character varying,tel integer,dir character varying,mail character varying)RETURNS int AS
$BODY$
declare
find int=0;
begin
CASE ban
        WHEN 1 THEN 
select count(1) into find from sucursal where suc_des=des;
if find >0 then
		raise notice '%','Ya existe una Sucursal con esa Descripción';
		return 1;
else
select count(1) into find from sucursal where suc_cod=cod;
	if find>0 then
select coalesce(max(suc_cod),0)+1 into cod from sucursal;
raise notice '%','Se designó el Codigo '||cod||' para evitar conflictos';
end if;
      INSERT INTO sucursal VALUES (cod,'VIG',upper(des),tel,upper(dir),mail);
      raise notice '%','NOPE';
      return 0;
end if;
      WHEN 2 THEN
select count(1) into find from sucursal where suc_des=des and suc_cod!=cod;	
if find >0 then
		raise notice '%','Ya existe una Sucursal con esa Descripción';
		return 1;
else
             UPDATE sucursal SET suc_des=upper(des),suc_tel=tel,suc_dir=upper(dir),suc_mail=mail WHERE suc_cod=cod;
             raise notice '%','NOPE';
             return 0;
end if;
      WHEN 3 THEN update sucursal set est_cod='ANU' WHERE suc_cod=cod;
      raise notice '%','NOPE';
      return 0;
end case;
    end;
    $BODY$
  LANGUAGE plpgsql;

-----------------------------------------------------------------------------------PROVEEDOR-------------------------------------------------------------------------------------------------
  CREATE OR REPLACE FUNCTION abm_prov(ban integer,cod integer,des character varying,dir character varying,tel integer,mail character varying,ruc character varying)RETURNS int AS
$BODY$
declare
find int=0;
begin
CASE ban
        WHEN 1 THEN 
select count(1) into find from proveedor where prov_ruc=ruc;
if find >0 then
		raise notice '%','Ya existe un Proveedor con ese Nº de RUC';
		return 1;
else
select count(1) into find from proveedor where prov_cod=cod;
	if find>0 then
select coalesce(max(prov_cod),0)+1 into cod from proveedor;
raise notice '%','Se designó el Codigo '||cod||' para evitar conflictos';
end if;
      INSERT INTO proveedor VALUES (cod,'VIG',upper(des),upper(dir),tel,mail,ruc);
      raise notice '%','NOPE';
      return 0;
end if;
      WHEN 2 THEN
select count(1) into find from proveedor where prov_ruc=ruc and prov_cod!=cod;	
if find >0 then
		raise notice '%','Ya existe un Proveedor con ese Nº de RUC';
		return 1;
else
             UPDATE proveedor SET prov_des=upper(des),prov_dir=upper(dir),prov_tel=tel,prov_mail=mail,prov_ruc=ruc WHERE prov_cod=cod;
             raise notice '%','NOPE';
             return 0;
end if;
      WHEN 3 THEN update proveedor set est_cod='ANU' WHERE prov_cod=cod;
      raise notice '%','NOPE';
      return 0;
end case;
    end;
    $BODY$
  LANGUAGE plpgsql;

-----------------------------------------------------------------------------------PROVEEDOR DE SERVICIO-------------------------------------------------------------------------------------------------
  CREATE OR REPLACE FUNCTION abm_provs(ban integer,cod integer,des character varying,dir character varying,tel integer,mail character varying,ruc character varying)RETURNS int AS
$BODY$
declare
find int=0;
begin
CASE ban
        WHEN 1 THEN 
select count(1) into find from proveedor_serv where prov_ruc=ruc;
if find >0 then
		raise notice '%','Ya existe un Proveedor de Servicios con ese Nº de RUC';
		return 1;
else
select count(1) into find from proveedor_serv where prov_cod=cod;
	if find>0 then
select coalesce(max(prov_cod),0)+1 into cod from proveedor_serv;
raise notice '%','Se designó el Codigo '||cod||' para evitar conflictos';
end if;
      INSERT INTO proveedor_serv VALUES (cod,'VIG',upper(des),upper(dir),tel,mail,ruc);
      raise notice '%','NOPE';
      return 0;
end if;
      WHEN 2 THEN
select count(1) into find from proveedor_serv where prov_ruc=ruc and prov_cod!=cod;	
if find >0 then
		raise notice '%','Ya existe un Proveedor de Servicios con ese Nº de RUC';
		return 1;
else
             UPDATE proveedor_serv SET prov_des=upper(des),prov_dir=upper(dir),prov_tel=tel,prov_mail=mail,prov_ruc=ruc WHERE prov_cod=cod;
             raise notice '%','NOPE';
             return 0;
end if;
      WHEN 3 THEN update proveedor_serv set est_cod='ANU' WHERE prov_cod=cod;
      raise notice '%','NOPE';
      return 0;
end case;
    end;
    $BODY$
  LANGUAGE plpgsql;
  
-----------------------------------------------------------------------------------MODELO-------------------------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION abm_mod(ban integer,cod integer,mar integer,des character varying)RETURNS int AS
$BODY$
declare
find int=0;
begin
CASE ban
        WHEN 1 THEN 
select count(1) into find from modelo where mod_des=des and mar_cod=mar;
if find >0 then
		raise notice '%','Ya existe un Modelo con esa Descripción para dicha Marca';
		return 1;
else
select count(1) into find from modelo where mod_cod=cod;
	if find>0 then
select coalesce(max(mod_cod),0)+1 into cod from modelo;
raise notice '%','Se designó el Codigo '||cod||' para evitar conflictos';
end if;
      INSERT INTO modelo VALUES (cod,mar,1,upper(des));
      raise notice '%','NOPE';
      return 0;
end if;
      WHEN 2 THEN
select count(1) into find from modelo where mod_des=des and mod_cod!=cod and mar_cod=mar;	
if find >0 then
		raise notice '%','Ya existe un Modelo con esa Descripción para dicha Marca';
		return 1;
else
             UPDATE modelo SET mar_cod=mar,mod_des=upper(des) WHERE mod_cod=cod and mar_cod=mar;
             raise notice '%','NOPE';
             return 0;
end if;
      WHEN 3 THEN UPDATE modelo SET est_cod='ANU' WHERE mod_cod=cod;
      raise notice '%','NOPE';
      return 0;
end case;
    end;
    $BODY$
  LANGUAGE plpgsql;
-----------------------------------------------------------------------------------DETALLE DE MODELO-------------------------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION abm_mod2(ban integer,mar integer,cod integer,tveh integer)RETURNS int AS
$BODY$
declare
find int=0;
begin
CASE ban
       WHEN 1 THEN 
select count(1) into find from detalle_modelo where mar_cod=mar and mod_cod=cod and tveh_cod=tveh;
if find >0 then
		raise notice '%','Ya existe un Tipo de Vehiculo con esa Descripción para dicha Marca y Modelo';
		return 1;
else
      INSERT INTO detalle_modelo VALUES (mar,cod,tveh,1);
      raise notice '%','NOPE';
      return 0;
end if;
      WHEN 3 THEN UPDATE detalle_modelo	SET est_cod='ANU' where mar_cod=mar and mod_cod=cod and tveh_cod=tveh;
      raise notice '%','NOPE';
      return 0;
end case;
    end;
    $BODY$
  LANGUAGE plpgsql;
  
-----------------------------------------------------------------------------------DETALLE DE PROVEEDOR-------------------------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION abm_dprov(ban integer,cod integer,trab integer)RETURNS int AS
$BODY$
declare
find int=0;
begin
CASE ban
       WHEN 1 THEN 
select count(1) into find from detalle_prov where prov_cod=cod and trab_cod=trab;
if find >0 then
		raise notice '%','Ya existe un Trabajo de Tercero con esa Descripción para dicho Proveedor de Servicios';
		return 1;
else
      INSERT INTO detalle_prov VALUES (cod,trab,'VIG');--select * from detalle_prov
      raise notice '%','NOPE';
      return 0;
end if;
      WHEN 3 THEN UPDATE detalle_prov SET est_cod='ANU' where prov_cod=cod and trab_cod=trab;
      raise notice '%','NOPE';
      return 0;
end case;
    end;
    $BODY$
  LANGUAGE plpgsql;

-----------------------------------------------------------------------------------FUNCIONARIOS-------------------------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION abm_func(ban integer,cod integer,usr integer,nac integer,car integer,nom character varying,
ape character varying,cel integer,dir character varying,fnac date,suc integer)RETURNS int AS
$BODY$
declare
find int=0;
suc_old int=0;--para determinar si cambio de suc, asi hace el update correspondiente en la tabla detalle_emp
begin
CASE ban
        WHEN 1 THEN ----------------------------------------------------------------------------------------------------------------------------------------------
select count(1) into find from empleado where emp_ci=cod;
if find >0 then
		raise notice '%','Ya existe un Funcionario con ese Nº de Cedula';
		return 1;
else
--if usr isnull then
--block=true;--select * from detalle_emp
--select * from empleado
--end if;--para doblemente evitar que funcionarios sin user (null) no inicien sesion con campos en blanco
      INSERT INTO empleado VALUES (cod,usr,'VIG',nac,car,upper(nom),upper(ape),cel,upper(dir),fnac);
      INSERT INTO detalle_emp values(suc,cod,current_date,null);
      raise notice '%','NOPE';
      return 0;
end if;
      WHEN 2 THEN 
      select suc_cod into suc_old from empleado where emp_ci=cod;
      select count(1) into find from empleado where emp_ci=cod;

                   if(suc_cod!=suc)then --actualiza datos de la sucursal---------------------------------------------------------------
update detalle_emp set date_egr=current_date where emp_ci=cod and suc_cod=suc_old and dte_egr isnull;
insert into detalle_emp values(suc,cod,current_date,null);
                   end if; -----------------------fin actualizacion de la sucursal-----------------------------------------------------

if find >0 then
		raise notice '%','Ya existe un Funcionario con ese Nº de Cedula';
		return 1;
else
      UPDATE empleado SET usr_cod=usr,nac_cod=nac,car_cod=car,emp_nom=upper(nom),emp_ape=upper(ape),emp_cel=cel,emp_dir=upper(dir),emp_fnac=fnac WHERE emp_ci=cod;
      raise notice '%','NOPE';
      return 0;
end if;

      WHEN 3 THEN -------------------------------------------------------------------------------------------------------------------------------------------------
      update empleado set est_cod='ANU' WHERE emp_ci=cod;
	update detalle_emp set dat_egr=current_date where suc_cod=suc and emp_ci=cod and date_egr isnull;
      raise notice '%','NOPE';
      return 0;
     /* WHEN 4 THEN -------------------------------------------------------------------------------------------------------------------------------------------------
      update empleado set emp_block=true WHERE emp_ci=cod;
      raise notice '%','NOPE';
      return 0;*/
end case;
    end;
    $BODY$
  LANGUAGE plpgsql;


-----------------------------------------------------------------------------------USUARIO-------------------------------------------------------------------------------------------------
  CREATE OR REPLACE FUNCTION abm_usr(ban integer,cod integer,des character varying,pass character varying)RETURNS int AS
$BODY$
declare
find int=0;--select * from usuario
begin
CASE ban
        WHEN 1 THEN -----------------------------------------------------------------
select count(1) into find from usuario where usr_des=des;
if find >0 then
		raise notice '%','Ya existe un Usuario con esa Descripción';
		return 1;
else
select count(1) into find from usuario where usr_cod=cod;
	if find>0 then
select coalesce(max(usr_cod),0)+1 into cod from usuario;
raise notice '%','Se designó el Codigo '||cod||' para evitar conflictos';
end if;
      INSERT INTO usuario VALUES (cod,'VIG',upper(des),lower(pass));
      raise notice '%','NOPE';
      return 0;
end if;
      WHEN 2 THEN -----------------------------------------------------------------
select count(1) into find from usuario where usr_des=des and usr_cod!=cod;	
if find >0 then
		raise notice '%','Ya existe un Usuario con esa Descripción';
		return 1;
else
             UPDATE usuario SET usr_des=upper(des),usr_pass=lower(pass) WHERE usr_cod=cod;
             raise notice '%','NOPE';
             return 0;
end if;
      WHEN 3 THEN -----------------------------------------------------------------
update usuario set est_cod='ANU' WHERE usr_cod=cod;
      raise notice '%','NOPE';
      return 0;
end case;
    end;
    $BODY$
  LANGUAGE plpgsql;
-----------------------------------------------------------------------------------detalle_usuario-------------------------------------------------------------------------------------------------
  CREATE OR REPLACE FUNCTION abm_dusr(ban integer,cod integer,rol integer)RETURNS int AS--select * from detalle_usuario
$BODY$
declare
find int=0;
begin
CASE ban
        WHEN 1 THEN -----------------------------------------------------------------
select count(1) into find from detalle_usuario where usr_cod=cod and rol_cod=rol;
if find >0 then
		raise notice '%','Ya existe un Rol con esa Descripción para dicho Usuario';
		return 1;
else
      INSERT INTO detalle_mat VALUES (cod,rol);
      raise notice '%','NOPE';
      return 0;
end if;
    --  WHEN 2 THEN -----------------------------------------------------------------
       --      UPDATE detalle_mat SET cant=cant_ WHERE mat_cod=cod and dam_cod=dam;
      ----       raise notice '%','NOPE';
       --      return 0;
      WHEN 3 THEN -----------------------------------------------------------------
--update detalle_mat set est_cod='ANU' WHERE mat_cod=cod and dam_cod=dam;
delete from detalle_usuario where usr_cod=cod and rol_cod=rol;
      raise notice '%','NOPE';
      return 0;
end case;
    end;
    $BODY$
  LANGUAGE plpgsql;


-----------------------------------------------------------------------------------TIMBRADO-------------------------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION abm_tim(ban integer,cod integer,nro integer,venc date)RETURNS int AS
$BODY$
declare
find int=0;
begin
CASE ban
       WHEN 1 THEN 
--SELECT age(date (tim_venc),current_date)from timbrado;
--select extract(days from (timestamp '2015-11-30' - current_date));
select (date(tim_venc) - current_date) into find from timbrado where est_cod=1 order by tim_cod desc limit 1;
if find >0 then
		raise notice '%','Existe un Timbrado aún Vigente';
		return 1;
else
      INSERT INTO timbrado VALUES (cod,'VIG',nro,venc);
      raise notice '%','NOPE';
      return 0;
end if;
      WHEN 3 THEN update timbrado set est_cod='ANU' where tim_cod=cod;
      raise notice '%','NOPE';
      return 0;
end case;
    end;
    $BODY$
  LANGUAGE plpgsql;--delete from timbrado where tim_cod>=0
-----------------------------------------------------------------------------------MATERIALES-------------------------------------------------------------------------------------------------
  CREATE OR REPLACE FUNCTION abm_mat(ban integer,cod integer,un character varying,des character varying)RETURNS int AS
$BODY$
declare
find int=0;
begin
CASE ban
        WHEN 1 THEN -----------------------------------------------------------------
select count(1) into find from material where mat_des=des;
if find >0 then
		raise notice '%','Ya existe un Material con esa Descripción';
		return 1;
else
select count(1) into find from material where mat_cod=cod;
	if find>0 then
select coalesce(max(mat_cod),0)+1 into cod from material;
raise notice '%','Se designó el Codigo '||cod||' para evitar conflictos';
end if;
      INSERT INTO material VALUES (cod,'VIG',un,upper(des));
      raise notice '%','NOPE';
      return 0;
end if;
      WHEN 2 THEN -----------------------------------------------------------------
select count(1) into find from material where mat_des=des and mat_cod!=cod;	
if find >0 then
		raise notice '%','Ya existe un Material con esa Descripción';
		return 1;
else
             UPDATE material SET un_cod=un,mat_des=upper(des)WHERE mat_cod=cod;
             raise notice '%','NOPE';
             return 0;
end if;
      WHEN 3 THEN -----------------------------------------------------------------
update material set est_cod='ANU' WHERE mat_cod=cod;
      raise notice '%','NOPE';
      return 0;
end case;
    end;
    $BODY$
  LANGUAGE plpgsql;
-----------------------------------------------------------------------------------rol-------------------------------------------------------------------------------------------------
  CREATE OR REPLACE FUNCTION abm_rol(ban integer,cod integer,des character varying)RETURNS int AS--select * from rol
$BODY$
declare
find int=0;
begin
CASE ban
        WHEN 1 THEN -----------------------------------------------------------------
select count(1) into find from rol where rol_des=des;
if find >0 then
		raise notice '%','Ya existe un Rol con esa Descripción';
		return 1;
else
select count(1) into find from rol where rol_cod=cod;
	if find>0 then
select coalesce(max(rol_cod),0)+1 into cod from rol;
raise notice '%','Se designó el Codigo '||cod||' para evitar conflictos';
end if;
      INSERT INTO rol VALUES (cod,'VIG',upper(des));
      raise notice '%','NOPE';
      return 0;
end if;
      WHEN 2 THEN -----------------------------------------------------------------
select count(1) into find from rol where rol_des=des and rol_cod!=cod;	
if find >0 then
		raise notice '%','Ya existe un Rol con esa Descripción';
		return 1;
else
             UPDATE rol SET rol_des=upper(des)WHERE rol_cod=cod;
             raise notice '%','NOPE';
             return 0;
end if;
      WHEN 3 THEN -----------------------------------------------------------------
update rol set est_cod='ANU' WHERE rol_cod=cod;
      raise notice '%','NOPE';
      return 0;
end case;
    end;
    $BODY$
  LANGUAGE plpgsql;

-----------------------------------------------------------------------------------MATERIALES PARAM-------------------------------------------------------------------------------------------------
  CREATE OR REPLACE FUNCTION abm_dmat(ban integer,dam integer,cod integer,cant_ numeric)RETURNS int AS
$BODY$
declare
find int=0;
begin
CASE ban
        WHEN 1 THEN -----------------------------------------------------------------
select count(1) into find from detalle_mat where mat_cod=cod and dam_cod=dam and est_cod='VIG';
if find >0 then
		raise notice '%','Ya existe un Daño con esa Descripción para dicho Material';
		return 1;
else
      INSERT INTO detalle_mat VALUES (dam,cod,'VIG',cant_);
      raise notice '%','NOPE';
      return 0;
end if;
      WHEN 2 THEN -----------------------------------------------------------------
             UPDATE detalle_mat SET cant=cant_ WHERE mat_cod=cod and dam_cod=dam;
             raise notice '%','NOPE';
             return 0;
      WHEN 3 THEN -----------------------------------------------------------------
update detalle_mat set est_cod='ANU' WHERE mat_cod=cod and dam_cod=dam;
      raise notice '%','NOPE';
      return 0;
end case;
    end;
    $BODY$
  LANGUAGE plpgsql;
--select current_timestamp
--select now()
--SET TIME ZONE 'America/Asuncion';
--set timezone TO 'GMT -4';
--select * from pg_timezone_names
--SELECT tablename FROM pg_catalog.pg_tables where schemaname = 'public' order by 1 asc