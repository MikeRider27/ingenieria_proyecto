-----------------------------------------------------------------------------------BANCO-------------------------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION abm_ban(ban integer,cod integer,des character varying)RETURNS int AS
$BODY$
declare
find int=0;
existe_cod int=0;
begin
CASE ban
        WHEN 1 THEN 
select count(1) into find from banco where ban_des=des;
if find >0 then
		raise notice '%','Ya existe un Banco con esa Descripción';
		return 1;
else
select count(1) into existe_cod from banco where ban_cod=cod;
	if existe_cod>0 then
select coalesce(max(ban_cod),0)+1 into cod from banco;
raise notice '%','Se designó el Codigo '||cod||' para evitar conflictos';
end if;
      INSERT INTO banco VALUES (cod,upper(des));
      raise notice '%','NOPE';
      return 0;
end if;
      WHEN 2 THEN
select count(1) into find from banco where ban_des=des and ban_cod!=cod;	
if find >0 then
		raise notice '%','Ya existe un Banco con esa Descripción';
		return 1;
else
             UPDATE banco SET ban_des=upper(des) WHERE ban_cod=cod;
             raise notice '%','NOPE';
             return 0;
end if;
      WHEN 3 THEN DELETE FROM banco WHERE ban_cod=cod;
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
existe_cod int=0;
begin
CASE ban
        WHEN 1 THEN 
select count(1) into find from nacionalidad where descrip=des;
if find >0 then
		raise notice '%','Ya existe una Nacionalidad con esa Descripción';
		return 1;
else
select count(1) into existe_cod from nacionalidad where id_nac=cod;
	if existe_cod>0 then
select coalesce(max(id_nac),0)+1 into cod from nacionalidad;
raise notice '%','Se designó el Codigo '||cod||' para evitar conflictos';
end if;
      INSERT INTO nacionalidad VALUES (cod,upper(des));
raise notice '%','NOPE';
      return 0;
end if;
      WHEN 2 THEN
select count(1) into find from nacionalidad where descrip=des and id_nac!=cod;	
if find >0 then
		raise notice '%','Ya existe una Nacionalidad con esa Descripción';
		return 1;
else
             UPDATE nacionalidad SET descrip=upper(des) WHERE id_nac=cod;
             raise notice '%','NOPE';
             return 0;
end if;
      WHEN 3 THEN DELETE FROM nacionalidad WHERE id_nac=cod;
      raise notice '%','NOPE';
      return 0;
end case;
    end;
    $BODY$
  LANGUAGE plpgsql;
  SELECT id_nac,descrip FROM v_nac WHERE descrip
-----------------------------------------------------------------------------------MARCAS-------------------------------------------------------------------------------------------------
  CREATE OR REPLACE FUNCTION abm_mar(ban integer,cod integer,des character varying)RETURNS int AS
$BODY$
declare
find int=0;
existe_cod int=0;
begin
CASE ban
        WHEN 1 THEN 
select count(1) into find from marca where mar_des=des;
if find >0 then
		raise notice '%','Ya existe una Marca de Vehículo con esa Descripción';
		return 1;
else
select count(1) into existe_cod from marca where mar_cod=cod;
	if existe_cod>0 then
select coalesce(max(mar_cod),0)+1 into cod from marca;
raise notice '%','Se designó el Codigo '||cod||' para evitar conflictos';
end if;
      INSERT INTO marca VALUES (cod,upper(des));
      raise notice '%','NOPE';
      return 0;
end if;
      WHEN 2 THEN
select count(1) into find from marca where mar_des=des and mar_cod!=cod;	
if find >0 then
		raise notice '%','Ya existe una Marca de Vehículo con esa Descripción';
		return 1;
else
             UPDATE marca SET mar_des=upper(des) WHERE mar_cod=cod;
             raise notice '%','NOPE';
             return 0;
end if;
      WHEN 3 THEN DELETE FROM marca WHERE mar_cod=cod;
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
existe_cod int=0;
begin
CASE ban
        WHEN 1 THEN 
select count(1) into find from color where col_des=des;
if find >0 then
		raise notice '%','Ya existe un Color con esa Descripción';
		return 1;
else
select count(1) into existe_cod from color where col_cod=cod;
	if existe_cod>0 then
select coalesce(max(col_cod),0)+1 into cod from color;
raise notice '%','Se designó el Codigo '||cod||' para evitar conflictos';
end if;
      INSERT INTO color VALUES (cod,upper(des));
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
      WHEN 3 THEN DELETE FROM color WHERE col_cod=cod;
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
existe_cod int=0;
begin
CASE ban
        WHEN 1 THEN 
select count(1) into find from ciudad where ciu_des=des;
if find >0 then
		raise notice '%','Ya existe una Ciudad con esa Descripción';
		return 1;
else
select count(1) into existe_cod from ciudad where ciu_cod=cod;
	if existe_cod>0 then
select coalesce(max(ciu_cod),0)+1 into cod from ciudad;
raise notice '%','Se designó el Codigo '||cod||' para evitar conflictos';
end if;
      INSERT INTO ciudad VALUES (cod,upper(des));
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
      WHEN 3 THEN DELETE FROM ciudad WHERE ciu_cod=cod;
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
existe_cod int=0;
begin
CASE ban
        WHEN 1 THEN 
select count(1) into find from trabajos_terciarios where trab_des=des;
if find >0 then
		raise notice '%','Ya existe un Trabajo Terciario con esa Descripción';
		return 1;
else
select count(1) into existe_cod from trabajos_terciarios where trab_cod=cod;
	if existe_cod>0 then
select coalesce(max(trab_cod),0)+1 into cod from trabajos_terciarios;
raise notice '%','Se designó el Codigo '||cod||' para evitar conflictos';
end if;
      INSERT INTO trabajos_terciarios VALUES (cod,upper(des));
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
      WHEN 3 THEN DELETE FROM banco WHERE ban_cod=cod;
      raise notice '%','NOPE';
      return 0;
end case;
    end;
    $BODY$
  LANGUAGE plpgsql;
-----------------------------------------------------------------------------------TIPO DE REPUESTOS-------------------------------------------------------------------------------------------------
  CREATE OR REPLACE FUNCTION abm_trep(ban integer,cod integer,des character varying)RETURNS int AS
$BODY$
declare
find int=0;
existe_cod int=0;
begin
CASE ban
        WHEN 1 THEN 
select count(1) into find from tipo_rep where trep_des=des;
if find >0 then
		raise notice '%','Ya existe un Tipo de Repuesto con esa Descripción';
		return 1;
else
select count(1) into existe_cod from tipo_rep where trep_cod=cod;
	if existe_cod>0 then
select coalesce(max(trep_cod),0)+1 into cod from tipo_rep;
raise notice '%','Se designó el Codigo '||cod||' para evitar conflictos';
end if;
      INSERT INTO tipo_rep VALUES (cod,upper(des));
      raise notice '%','NOPE';
      return 0;
end if;
      WHEN 2 THEN
select count(1) into find from tipo_rep where trep_des=des and trep_cod!=cod;	
if find >0 then
		raise notice '%','Ya existe un Tipo de Repuesto con esa Descripción';
		return 1;
else
             UPDATE tipo_rep SET trep_des=upper(des) WHERE trep_cod=cod;
             raise notice '%','NOPE';
             return 0;
end if;
      WHEN 3 THEN DELETE FROM tipo_rep WHERE trep_cod=cod;
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
existe_cod int=0;
begin
CASE ban
        WHEN 1 THEN 
select count(1) into find from caja where caj_des=des;
if find >0 then
		raise notice '%','Ya existe una Caja con esa Descripción';
		return 1;
else
select count(1) into existe_cod from caja where caj_cod=cod;
	if existe_cod>0 then
select coalesce(max(caj_cod),0)+1 into cod from caja;
raise notice '%','Se designó el Codigo '||cod||' para evitar conflictos';
end if;
      INSERT INTO caja VALUES (cod,upper(des),false);
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
      WHEN 3 THEN UPDATE caja SET caj_null=true WHERE caj_cod=cod;
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
existe_cod int=0;
begin
CASE ban
        WHEN 1 THEN 
select count(1) into find from motivo_ajuste where mot_des=des;
if find >0 then
		raise notice '%','Ya existe un Motivo de Ajuste con esa Descripción';
		return 1;
else
select count(1) into existe_cod from motivo_ajuste where mot_cod=cod;
	if existe_cod>0 then
select coalesce(max(mot_cod),0)+1 into cod from motivo_ajuste;
raise notice '%','Se designó el Codigo '||cod||' para evitar conflictos';
end if;
      INSERT INTO motivo_ajuste VALUES (cod,upper(des),tmot,false);
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
      WHEN 3 THEN UPDATE motivo_ajuste SET mot_null=true WHERE mot_cod=cod;
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
existe_cod int=0;
begin
CASE ban
        WHEN 1 THEN 
select count(1) into find from motivo_garantia where mot_des=des;
if find >0 then
		raise notice '%','Ya existe un Motivo de Garantia con esa Descripción';
		return 1;
else
select count(1) into existe_cod from motivo_garantia where mot_cod=cod;
	if existe_cod>0 then
select coalesce(max(mot_cod),0)+1 into cod from motivo_garantia;
raise notice '%','Se designó el Codigo '||cod||' para evitar conflictos';
end if;
      INSERT INTO motivo_garantia VALUES (cod,upper(des),false);
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
      WHEN 3 THEN UPDATE motivo_garantia SET mot_null=true WHERE mot_cod=cod;
      raise notice '%','NOPE';
      return 0;
end case;
    end;
    $BODY$
  LANGUAGE plpgsql;

 -----------------------------------------------------------------------------------ETAPAS------------------------------------------------------------------------------------------------ 
CREATE OR REPLACE FUNCTION abm_eta(ban integer,cod integer,des character varying)RETURNS int AS
$BODY$
declare
find int=0;
existe_cod int=0;
begin
CASE ban
        WHEN 1 THEN 
select count(1) into find from etapa where eta_des=des;
if find >0 then
		raise notice '%','Ya existe una Etapa con esa Descripción';
		return 1;
else
select count(1) into existe_cod from etapa where eta_cod=cod;
	if existe_cod>0 then
select coalesce(max(eta_cod),0)+1 into cod from etapa;
raise notice '%','Se designó el Codigo '||cod||' para evitar conflictos';
end if;
      INSERT INTO etapa VALUES (cod,upper(des),false);
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
      WHEN 3 THEN UPDATE etapa SET eta_null=true WHERE eta_cod=cod;
      raise notice '%','NOPE';
      return 0;
end case;
    end;
    $BODY$
  LANGUAGE plpgsql;

-----------------------------------------------------------------------------------DEPOSITOS-------------------------------------------------------------------------------------------------  
CREATE OR REPLACE FUNCTION abm_dep(ban integer,cod integer,suc integer,des character varying)RETURNS int AS
$BODY$
declare
find int=0;
existe_cod int=0;
begin
CASE ban
        WHEN 1 THEN 
select count(1) into find from deposito where dep_des=des and suc_cod=suc;
if find >0 then
		raise notice '%','Ya existe un Depósito con esa Descripción en dicha Sucursal';
		return 1;
else
select count(1) into existe_cod from deposito where dep_cod=cod;
	if existe_cod>0 then
select coalesce(max(dep_cod),0)+1 into cod from deposito;
raise notice '%','Se designó el Codigo '||cod||' para evitar conflictos';
end if;
      INSERT INTO deposito VALUES (cod,suc,upper(des),false);
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
      WHEN 3 THEN UPDATE deposito SET dep_null=true WHERE dep_cod=cod;
      raise notice '%','NOPE';
      return 0;
end case;
    end;
    $BODY$
  LANGUAGE plpgsql;
 -----------------------------------------------------------------------------------PIEZAS------------------------------------------------------------------------------------------------ 
CREATE OR REPLACE FUNCTION abm_pza(ban integer,cod integer,des character varying,cant numeric)RETURNS int AS
$BODY$
declare
find int=0;
existe_cod int=0;
begin
CASE ban
        WHEN 1 THEN 
select count(1) into find from pieza where pie_des=des;
if find >0 then
		raise notice '%','Ya existe un Material con esa Descripción';
		return 1;
else
select count(1) into existe_cod from pieza where pie_cod=cod;
	if existe_cod>0 then
select coalesce(max(pie_cod),0)+1 into cod from pieza;
raise notice '%','Se designó el Codigo '||cod||' para evitar conflictos';
end if;
      INSERT INTO pieza VALUES (cod,upper(des),cant);
      raise notice '%','NOPE';
      return 0;
end if;
      WHEN 2 THEN
select count(1) into find from pieza where pie_des=des and pie_cod!=cod;	
if find >0 then
		raise notice '%','Ya existe un Material con esa Descripción';
		return 1;
else
             UPDATE pieza SET pie_des=upper(des),pie_cant=cant WHERE pie_cod=cod;
             raise notice '%','NOPE';
             return 0;
end if;
      WHEN 3 THEN delete from pieza WHERE pie_cod=cod;
      raise notice '%','NOPE';
      return 0;
end case;
    end;
    $BODY$
  LANGUAGE plpgsql;

 -----------------------------------------------------------------------------------BARRIOS------------------------------------------------------------------------------------------------- 
CREATE OR REPLACE FUNCTION abm_bar(ban integer,cod integer,bar integer,des character varying)RETURNS int AS
$BODY$
declare
find int=0;
existe_cod int=0;
begin
CASE ban
        WHEN 1 THEN 
select count(1) into find from barrio where bar_des=des and ciu_cod=ciu;
if find >0 then
		raise notice '%','Ya existe un Barrio con esa Descripción en dicha Ciudad';
		return 1;
else
select count(1) into existe_cod from barrio where bar_cod=cod;
	if existe_cod>0 then
select coalesce(max(bar_cod),0)+1 into cod from barrio;
raise notice '%','Se designó el Codigo '||cod||' para evitar conflictos';
end if;
      INSERT INTO barrio VALUES (cod,ciu,upper(des));
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
      WHEN 3 THEN delete from barrio WHERE bar_cod=cod;
      raise notice '%','NOPE';
      return 0;
end case;
    end;
    $BODY$
  LANGUAGE plpgsql;
-----------------------------------------------------------------------------------CARGOS-------------------------------------------------------------------------------------------------
  CREATE OR REPLACE FUNCTION abm_car(ban integer,cod integer,des character varying,usr character varying,pass character varying)RETURNS int AS
$BODY$
declare
find int=0;
existe_cod int=0;
begin
CASE ban
        WHEN 1 THEN 
select count(1) into find from cargo where car_des=des;
if find >0 then
		raise notice '%','Ya existe un Cargo con esa Descripción';
		return 1;
else
select count(1) into existe_cod from cargo where car_cod=cod;
	if existe_cod>0 then
select coalesce(max(car_cod),0)+1 into cod from cargo;
raise notice '%','Se designó el Codigo '||cod||' para evitar conflictos';
end if;
      INSERT INTO cargo VALUES (cod,upper(des),usr,pass,false);
      raise notice '%','NOPE';
      return 0;
end if;
      WHEN 2 THEN
select count(1) into find from cargo where car_des=des and car_cod!=cod;	
if find >0 then
		raise notice '%','Ya existe un Cargo con esa Descripción';
		return 1;
else
             UPDATE cargo SET car_des=upper(des),car_user=usr,car_pass=pass WHERE car_cod=cod;
             raise notice '%','NOPE';
             return 0;
end if;
      WHEN 3 THEN update cargo set car_null=true WHERE bar_cod=cod;
      raise notice '%','NOPE';
      return 0;
end case;
    end;
    $BODY$
  LANGUAGE plpgsql;
-----------------------------------------------------------------------------------CLIENTES-------------------------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION abm_cli(ban integer,cod integer,nac integer,ciu integer,bar integer,nom character varying,ape character varying,cel integer,dir character varying, fnac date)RETURNS int AS
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
      INSERT INTO cliente VALUES (cod,nac,ciu,bar,upper(nom),upper(ape),cel,upper(dir),false,fnac);
      raise notice '%','NOPE';
      return 0;
end if;
      WHEN 2 THEN
select count(1) into find from cliente where cli_ci=cod;	
if find >0 then
		raise notice '%','Ya existe un Asegurado con ese Nº de Cedula';
		return 1;
else
             UPDATE cliente SET nac_cod=nac,ciu_cod=ciu,bar_cod=bar,cli_nom=nom,cli_ape=ape,cli_cel=cel,cli_dir=dir,cli_fnac=fnac WHERE cli_ci=cod;
             raise notice '%','NOPE';
             return 0;
end if;
      WHEN 3 THEN update cliente set cli_null=true WHERE cli_ci=cod;
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
existe_cod int=0;
begin
CASE ban
        WHEN 1 THEN 
select count(1) into find from seguro where seg_des=des;
if find >0 then
		raise notice '%','Ya existe una Cia. de Seguro con esa Descripción';
		return 1;
else
select count(1) into existe_cod from seguro where seg_cod=cod;
	if existe_cod>0 then
select coalesce(max(seg_cod),0)+1 into cod from seguro;
raise notice '%','Se designó el Codigo '||cod||' para evitar conflictos';
end if;
      INSERT INTO seguro VALUES (cod,upper(des),upper(dir),tel,mail,false);
      raise notice '%','NOPE';
      return 0;
end if;
      WHEN 2 THEN
select count(1) into find from seguro where seg_des=des and seg_cod!=cod;	
if find >0 then
		raise notice '%','Ya existe una Cia. de Seguro con esa Descripción';
		return 1;
else
             UPDATE seguro SET seg_des=des,seg_dir=dir,seg_tel=tel,seg_mail=mail WHERE seg_cod=cod;
             raise notice '%','NOPE';
             return 0;
end if;
      WHEN 3 THEN update seguro set seg_null=true WHERE seg_cod=cod;
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
existe_cod int=0;
begin
CASE ban
        WHEN 1 THEN 
select count(1) into find from sucursal where suc_des=des;
if find >0 then
		raise notice '%','Ya existe una Sucursal con esa Descripción';
		return 1;
else
select count(1) into existe_cod from sucursal where suc_cod=cod;
	if existe_cod>0 then
select coalesce(max(suc_cod),0)+1 into cod from sucursal;
raise notice '%','Se designó el Codigo '||cod||' para evitar conflictos';
end if;
      INSERT INTO sucursal VALUES (cod,upper(des),tel,upper(dir),mail,false);
      raise notice '%','NOPE';
      return 0;
end if;
      WHEN 2 THEN
select count(1) into find from sucursal where suc_des=des and suc_cod!=cod;	
if find >0 then
		raise notice '%','Ya existe una Sucursal con esa Descripción';
		return 1;
else
             UPDATE sucursal SET suc_des=des,suc_tel=tel,suc_dir=dir,suc_mail=mail WHERE suc_cod=cod;
             raise notice '%','NOPE';
             return 0;
end if;
      WHEN 3 THEN update sucursal set suc_null=true WHERE suc_cod=cod;
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
existe_cod int=0;
begin
CASE ban
        WHEN 1 THEN 
select count(1) into find from proveedor where prov_ruc=ruc;
if find >0 then
		raise notice '%','Ya existe un Proveedor con ese Nº de RUC';
		return 1;
else
select count(1) into existe_cod from proveedor where prov_cod=cod;
	if existe_cod>0 then
select coalesce(max(prov_cod),0)+1 into cod from proveedor;
raise notice '%','Se designó el Codigo '||cod||' para evitar conflictos';
end if;
      INSERT INTO proveedor VALUES (cod,upper(des),upper(dir),tel,mail,ruc,false);
      raise notice '%','NOPE';
      return 0;
end if;
      WHEN 2 THEN
select count(1) into find from proveedor where prov_ruc=ruc and prov_cod!=cod;	
if find >0 then
		raise notice '%','Ya existe un Proveedor con ese Nº de RUC';
		return 1;
else
             UPDATE proveedor SET prov_des=des,prov_dir=dir,prov_tel=tel,prov_mail=mail,prov_ruc=ruc WHERE prov_cod=cod;
             raise notice '%','NOPE';
             return 0;
end if;
      WHEN 3 THEN update proveedor set prov_null=true WHERE prov_cod=cod;
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
existe_cod int=0;
begin
CASE ban
        WHEN 1 THEN 
select count(1) into find from proveedor_serv where prov_ruc=ruc;
if find >0 then
		raise notice '%','Ya existe un Proveedor de Servicios con ese Nº de RUC';
		return 1;
else
select count(1) into existe_cod from proveedor_serv where prov_cod=cod;
	if existe_cod>0 then
select coalesce(max(prov_cod),0)+1 into cod from proveedor_serv;
raise notice '%','Se designó el Codigo '||cod||' para evitar conflictos';
end if;
      INSERT INTO proveedor_serv VALUES (cod,upper(des),upper(dir),tel,mail,ruc,false);
      raise notice '%','NOPE';
      return 0;
end if;
      WHEN 2 THEN
select count(1) into find from proveedor_serv where prov_ruc=ruc and prov_cod!=cod;	
if find >0 then
		raise notice '%','Ya existe un Proveedor de Servicios con ese Nº de RUC';
		return 1;
else
             UPDATE proveedor_serv SET prov_des=des,prov_dir=dir,prov_tel=tel,prov_mail=mail,prov_ruc=ruc WHERE prov_cod=cod;
             raise notice '%','NOPE';
             return 0;
end if;
      WHEN 3 THEN update proveedor_serv set prov_null=true WHERE prov_cod=cod;
      raise notice '%','NOPE';
      return 0;
end case;
    end;
    $BODY$
  LANGUAGE plpgsql;
-----------------------------------------------------------------------------------REPUESTO-------------------------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION abm_rep(ban integer,cod integer,trep integer,des character varying)RETURNS int AS
$BODY$
declare
find int=0;
existe_cod int=0;
begin
CASE ban
        WHEN 1 THEN 
select count(1) into find from repuesto where rep_des=des and trep_cod=cod;
if find >0 then
		raise notice '%','Ya existe un Repuesto con esa Descripción para dicho Tipo de Repuesto';
		return 1;
else
select count(1) into existe_cod from repuesto where rep_cod=cod;
	if existe_cod>0 then
select coalesce(max(rep_cod),0)+1 into cod from repuesto;
raise notice '%','Se designó el Codigo '||cod||' para evitar conflictos';
end if;
      INSERT INTO repuesto VALUES (cod,trep,upper(des));
      raise notice '%','NOPE';
      return 0;
end if;
      WHEN 2 THEN
select count(1) into find from repuesto where rep_des=des and rep_cod!=cod and trep_cod=cod;	
if find >0 then
		raise notice '%','Ya existe un Repuesto con esa Descripción para dicho Tipo de Repuesto';
		return 1;
else
             UPDATE repuesto SET trep_cod=trep,rep_des=upper(des) WHERE rep_cod=cod;
             raise notice '%','NOPE';
             return 0;
end if;
      WHEN 3 THEN DELETE FROM repuesto WHERE rep_cod=cod;
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
existe_cod int=0;
begin
CASE ban
        WHEN 1 THEN 
select count(1) into find from modelo where mod_des=des and mar_cod=mar;
if find >0 then
		raise notice '%','Ya existe un Modelo con esa Descripción para dicha Marca';
		return 1;
else
select count(1) into existe_cod from modelo where mod_cod=cod;
	if existe_cod>0 then
select coalesce(max(mod_cod),0)+1 into cod from modelo;
raise notice '%','Se designó el Codigo '||cod||' para evitar conflictos';
end if;
      INSERT INTO modelo VALUES (cod,mar,upper(des));
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
      WHEN 3 THEN DELETE FROM modelo WHERE mod_cod=cod;
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
      INSERT INTO detalle_modelo VALUES (mar,cod,tveh);
      raise notice '%','NOPE';
      return 0;
end if;
      WHEN 3 THEN delete from cargo where mar_cod=mar and mod_cod=cod and tveh_cod=tveh;
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
      INSERT INTO detalle_prov VALUES (cod,trab);
      raise notice '%','NOPE';
      return 0;
end if;
      WHEN 3 THEN delete from detalle_prov where prov_cod=cod and trab_cod=trab;
      raise notice '%','NOPE';
      return 0;
end case;
    end;
    $BODY$
  LANGUAGE plpgsql;

-----------------------------------------------------------------------------------FUNCIONARIOS-------------------------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION abm_func(ban integer,cod integer,nac integer,car integer,nom character varying,ape character varying,cel integer,dir character varying,usr character varying,pass character varying,fnac date,suc integer)RETURNS int AS
$BODY$
declare
find int=0;
block boolean=false;
suc_old int=0;
begin
CASE ban
        WHEN 1 THEN 
select count(1) into find from empleado where emp_ci=cod;
if find >0 then
		raise notice '%','Ya existe un Funcionario con ese Nº de Cedula';
		return 1;
else
if usr isnull then
block=true;
end if;--para doblemente evitar que funcionarios sin user (null) no inicien sesion con campos en blanco
      INSERT INTO empleado VALUES (cod,nac,car,upper(nom),upper(ape),cel,upper(dir),false,upper(usr),lower(pass),block,fnac);
      raise notice '%','NOPE';
      return 0;
end if;
      WHEN 2 THEN
      select suc_cod into suc_old from empleado where emp_ci=cod;
             UPDATE empleado SET nac_cod=nac,car_cod=car,emp_nom=nom,emp_ape=ape,emp_cel=cel,emp_dir=dir,emp_user=usr,emp_pass=pass,emp_fnac=fnac WHERE emp_ci=cod;
             if(suc_cod!=suc)then --cambio la sucursal
update detalle_emp set date_egr=current_date  where emp_ci=cod and suc_cod=suc_old and dte_egr isnull;
insert into detalle_emp values(suc,cod,current_date,null);
             end if;
             raise notice '%','NOPE';
             return 0;
      WHEN 3 THEN update empleado set emp_null=true WHERE emp_ci=cod;
      raise notice '%','NOPE';
      return 0;
      WHEN 4 THEN update empleado set emp_block=true WHERE emp_ci=cod;
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
select (date(tim_venc) - current_date) into find from timbrado where tim_null=false order by tim_cod desc limit 1;
if find >0 then
		raise notice '%','Existe un Timbrado aún Vigente';
		return 1;
else
      INSERT INTO timbrado VALUES (cod,nro,tim_venc,false);
      raise notice '%','NOPE';
      return 0;
end if;
      WHEN 3 THEN update timbrado set tim_null=true where tim_cod=cod;
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
